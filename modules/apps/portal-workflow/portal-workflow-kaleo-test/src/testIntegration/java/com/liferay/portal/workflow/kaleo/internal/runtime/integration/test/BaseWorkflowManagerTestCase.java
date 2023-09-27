/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseWorkflowManagerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	protected InputStream getResourceInputStream(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/workflow/kaleo/dependencies/" + name);
	}

	protected ServiceRegistrationHolder registryWorkflowHandler() {
		return registryWorkflowHandler("Single Approver");
	}

	protected ServiceRegistrationHolder registryWorkflowHandler(
		String workflowDefinitionName) {

		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		BundleContext bundleContext = bundle.getBundleContext();

		return new ServiceRegistrationHolder(
			bundleContext.registerService(
				(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
				(WorkflowHandler)ProxyUtil.newProxyInstance(
					WorkflowHandler.class.getClassLoader(),
					new Class<?>[] {WorkflowHandler.class},
					(proxy, method, args) -> {
						if (Objects.equals(method.getName(), "getClassName")) {
							return clazz.getName();
						}

						if (Objects.equals(method.getName(), "getListType") ||
							Objects.equals(method.getName(), "getType")) {

							return StringPool.BLANK;
						}

						if (Objects.equals(method.getName(), "isScopeable")) {
							return false;
						}

						if (Objects.equals(
								method.getName(),
								"getWorkflowDefinitionLink")) {

							return workflowDefinitionLinkLocalService.
								updateWorkflowDefinitionLink(
									TestPropsValues.getUserId(),
									TestPropsValues.getCompanyId(), 0,
									clazz.getName(), 0, 0,
									workflowDefinitionName, 1);
						}

						if (Objects.equals(
								method.getName(), "startWorkflowInstance")) {

							workflowInstanceLinkLocalService.
								startWorkflowInstance(
									TestPropsValues.getCompanyId(), 0,
									(Long)args[2], clazz.getName(), 1,
									(Map<String, Serializable>)args[5]);
						}

						return null;
					}),
				HashMapDictionaryBuilder.put(
					"model.class.name=", clazz.getName()
				).build()));
	}

	protected ServiceRegistrationHolder registryWorkflowHandler(
		WorkflowDefinition workflowDefinition) {

		return registryWorkflowHandler(workflowDefinition.getName());
	}

	@Inject
	protected WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

	@Inject
	protected WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;

	@Inject
	protected WorkflowInstanceManager workflowInstanceManager;

	protected class ServiceRegistrationHolder implements AutoCloseable {

		public ServiceRegistrationHolder(
			ServiceRegistration<WorkflowHandler<?>> serviceRegistration) {

			_serviceRegistration = serviceRegistration;
		}

		@Override
		public void close() {
			_serviceRegistration.unregister();
		}

		private final ServiceRegistration<WorkflowHandler<?>>
			_serviceRegistration;

	}

}