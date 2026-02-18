/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.sql.Connection;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class ResourceActionPostUpgradeDataCleanupProcessTest
	extends BasePostUpgradeDataCleanupProcessTestCase {

	@Test
	public void testNonliferayResourceActionIsNotDeleted() throws Exception {
		AtomicReference<ResourceAction> resourceActionAtomicReference =
			new AtomicReference<>();
		String resourceActionActionId = "VIEW";
		String resourceActionName =
			"com.test.not.liferay." + RandomTestUtil.randomString();

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(messages.toString(), messages.isEmpty());

				ResourceAction resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						resourceActionName, resourceActionActionId);

				Assert.assertNotNull(resourceAction);
			},
			() -> {
				if (resourceActionAtomicReference.get() != null) {
					_resourceActionLocalService.deleteResourceAction(
						resourceActionAtomicReference.get());
				}
			},
			() -> resourceActionAtomicReference.set(
				_resourceActionLocalService.addResourceAction(
					resourceActionName, resourceActionActionId, 1)));
	}

	@Test
	public void testNotFoundLiferayResourceActionUnusedIsDeleted()
		throws Exception {

		AtomicReference<ResourceAction> resourceActionAtomicReference1 =
			new AtomicReference<>();
		AtomicReference<ResourceAction> resourceActionAtomicReference2 =
			new AtomicReference<>();
		String resourceActionActionId1 = "EDIT";
		String resourceActionActionId2 = "VIEW";
		String resourceActionName =
			"com.liferay.test." + RandomTestUtil.randomString();

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"Table ",
							dbInspector.normalizeName("ResourceAction"),
							", 2 rows deleted because '", resourceActionName,
							"' is not defined in any deployed module and is ",
							"not in use")));

				ResourceAction resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						resourceActionName, resourceActionActionId1);

				Assert.assertNull(resourceAction);

				resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						resourceActionName, resourceActionActionId2);

				Assert.assertNull(resourceAction);
			},
			() -> {
				if (resourceActionAtomicReference1.get() != null) {
					_resourceActionLocalService.deleteResourceAction(
						resourceActionAtomicReference1.get());
				}

				if (resourceActionAtomicReference2.get() != null) {
					_resourceActionLocalService.deleteResourceAction(
						resourceActionAtomicReference2.get());
				}
			},
			() -> {
				resourceActionAtomicReference1.set(
					_resourceActionLocalService.addResourceAction(
						resourceActionName, resourceActionActionId1, 1));
				resourceActionAtomicReference2.set(
					_resourceActionLocalService.addResourceAction(
						resourceActionName, resourceActionActionId2, 2));
			});
	}

	@Test
	public void testNotFoundLiferayResourceActionUsedIsNotDeleted()
		throws Exception {

		AtomicReference<ResourceAction> resourceActionAtomicReference =
			new AtomicReference<>();
		String resourceActionActionId = "VIEW";
		String resourceActionName =
			"com.liferay.test." + RandomTestUtil.randomString();

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						StringBundler.concat(
							"Resource action ", resourceActionName,
							" is not defined in any deployed module but is ",
							"referenced in ",
							dbInspector.normalizeName("ResourcePermission"),
							" table")));

				ResourceAction resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						resourceActionName, resourceActionActionId);

				Assert.assertNotNull(resourceAction);
			},
			() -> {
				if (resourceActionAtomicReference.get() != null) {
					_resourceActionLocalService.deleteResourceAction(
						resourceActionAtomicReference.get());

					_resourcePermissionLocalService.deleteResourcePermissions(
						resourceActionName);
				}
			},
			() -> {
				resourceActionAtomicReference.set(
					_resourceActionLocalService.addResourceAction(
						resourceActionName, resourceActionActionId, 1));

				Role role = _roleLocalService.getRole(
					TestPropsValues.getCompanyId(), RoleConstants.GUEST);

				_resourcePermissionLocalService.addResourcePermission(
					CompanyThreadLocal.getCompanyId(), resourceActionName,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(CompanyThreadLocal.getCompanyId()),
					role.getRoleId(), resourceActionActionId);
			},
			getPostUpgradeDataCleanupProcessClassName(), LoggerTestUtil.DEBUG);
	}

	@Test
	public void testVerifyDoesNotRunIfModulesNotStarted() throws Exception {
		AtomicReference<Bundle> bundleAtomicReference = new AtomicReference<>();

		test(
			logCapture -> {
				List<String> messages = logCapture.getMessages();

				Assert.assertTrue(
					messages.toString(),
					messages.contains(
						"ResourceActionPostUpgradeDataCleanupProcess cannot " +
							"be executed because there are modules with " +
								"unsatisfied references"));
			},
			() -> {
				Bundle bundle = bundleAtomicReference.get();

				if (bundle != null) {
					installBundle(bundle, SystemBundleUtil.getBundleContext());
				}
			},
			() -> bundleAtomicReference.set(
				uninstallBundle(
					SystemBundleUtil.getBundleContext(),
					"com.liferay.dynamic.data.mapping.service")));
	}

	@Override
	protected Object[] getPostUpgradeDataCleanupProcessArguments() {
		return new Object[] {connection, _resourceActionLocalService};
	}

	@Override
	protected Class<?>[] getPostUpgradeDataCleanupProcessArgumentTypes() {
		return new Class<?>[] {
			Connection.class, ResourceActionLocalService.class
		};
	}

	@Override
	protected String getPostUpgradeDataCleanupProcessClassName() {
		return "com.liferay.data.cleanup.internal.verify." +
			"ResourceActionPostUpgradeDataCleanupProcess";
	}

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}