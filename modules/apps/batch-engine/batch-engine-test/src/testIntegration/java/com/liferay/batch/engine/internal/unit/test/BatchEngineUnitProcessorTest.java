/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.unit.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Magdalena Jedraszak
 */
@RunWith(Arquillian.class)
public class BatchEngineUnitProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void test() throws Exception {
		User defaultOmniAdminUser = _userLocalService.getUser(
			TestPropsValues.getUserId());

		int originalStatus = defaultOmniAdminUser.getStatus();

		User newOmniAdminUser = UserTestUtil.addUser();

		try {
			Role role = _roleLocalService.getRole(
				newOmniAdminUser.getCompanyId(), RoleConstants.ADMINISTRATOR);

			_userLocalService.addRoleUser(role.getRoleId(), newOmniAdminUser);

			Queue<AuditMessage> auditMessages = new LinkedList<>();

			AuditRouter originalAuditRouter = ReflectionTestUtil.getFieldValue(
				_objectDefinitionModelListener, "_auditRouter");

			AuditRouter spyingAuditRouter =
				(AuditRouter)ProxyUtil.newProxyInstance(
					AuditRouter.class.getClassLoader(),
					new Class<?>[] {AuditRouter.class},
					(proxy, method, arguments) -> {
						if (arguments[0] instanceof AuditMessage) {
							auditMessages.add((AuditMessage)arguments[0]);
						}

						return method.invoke(originalAuditRouter, arguments);
					});

			ReflectionTestUtil.setFieldValue(
				_objectDefinitionModelListener, "_auditRouter",
				spyingAuditRouter);

			_userLocalService.updateStatus(
				defaultOmniAdminUser.getUserId(),
				WorkflowConstants.STATUS_INACTIVE, new ServiceContext());

			Assert.assertFalse(
				_userLocalService.getUser(
					defaultOmniAdminUser.getUserId()
				).isActive());
			Assert.assertTrue(newOmniAdminUser.isActive());

			List<Bundle> bundles = _stopAndUninstallBundles();

			_startBundles(bundles);

			AuditMessage auditMessage = auditMessages.stream(
			).filter(
				msg -> msg.getClassName(
				).contains(
					"ObjectDefinition"
				)
			).findFirst(
			).orElse(
				null
			);

			Assert.assertNotNull(
				"Expected ObjectDefinition audit message", auditMessage);
			Assert.assertNotEquals(
				defaultOmniAdminUser.getUserId(), auditMessage.getUserId());
		}
		finally {
			_userLocalService.updateStatus(
				defaultOmniAdminUser.getUserId(), originalStatus,
				new ServiceContext());

			if (newOmniAdminUser != null) {
				_userLocalService.deleteUser(newOmniAdminUser);
			}
		}
	}

	private void _startBundles(List<Bundle> bundles) throws BundleException {
		long timeout = 5000;
		long pollInterval = 100;

		for (Bundle bundle : bundles) {
			bundle.start();

			long startTime = System.currentTimeMillis();

			while (bundle.getState() != Bundle.ACTIVE) {
				if ((System.currentTimeMillis() - startTime) > timeout) {
					throw new RuntimeException(
						"Timeout waiting for bundle " +
							bundle.getSymbolicName() + " to become ACTIVE");
				}

				try {
					Thread.sleep(pollInterval);
				}
				catch (InterruptedException interruptedException) {
					Thread.currentThread(
					).interrupt();

					throw new RuntimeException(
						"Interrupted while waiting for bundle to start",
						interruptedException);
				}
			}
		}
	}

	private List<Bundle> _stopAndUninstallBundles() throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = currentBundle.getBundleContext();

		List<String> bundleLocations = new ArrayList<>();

		for (Bundle bundle : bundleContext.getBundles()) {
			String symbolicName = bundle.getSymbolicName();

			if ((symbolicName != null) &&
				symbolicName.startsWith("com.liferay.cookies.impl") &&
				(bundle.getState() == Bundle.ACTIVE)) {

				bundle.stop();

				bundleLocations.add(bundle.getLocation());

				bundle.uninstall();
			}
		}

		List<Bundle> reinstalledBundles = new ArrayList<>();

		for (String location : bundleLocations) {
			Bundle reinstalledBundle = bundleContext.installBundle(location);

			reinstalledBundles.add(reinstalledBundle);
		}

		return reinstalledBundles;
	}

	@Inject(
		filter = "component.name=com.liferay.object.internal.model.listener.ObjectDefinitionModelListener"
	)
	private ModelListener<ObjectDefinition> _objectDefinitionModelListener;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}