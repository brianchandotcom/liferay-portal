/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

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

	protected ServiceRegistration<WorkflowHandler<?>>
		registryWorkflowHandler() {

		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
			(WorkflowHandler)ProxyUtil.newProxyInstance(
				WorkflowHandler.class.getClassLoader(),
				new Class<?>[] {WorkflowHandler.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getClassName")) {
						return clazz.getName();
					}

					if (Objects.equals(method.getName(), "getType")) {
						return StringPool.BLANK;
					}

					if (Objects.equals(method.getName(), "isScopeable")) {
						return false;
					}

					if (Objects.equals(
							method.getName(), "getWorkflowDefinitionLink")) {

						return workflowDefinitionLinkLocalService.
							updateWorkflowDefinitionLink(
								TestPropsValues.getUserId(),
								TestPropsValues.getCompanyId(), 0,
								clazz.getName(), 0, 0, "Single Approver", 1);
					}

					if (Objects.equals(
							method.getName(), "startWorkflowInstance")) {

						workflowInstanceLinkLocalService.startWorkflowInstance(
							TestPropsValues.getCompanyId(), 0,
							TestPropsValues.getUserId(), clazz.getName(), 1,
							(Map<String, Serializable>)args[5]);
					}

					return null;
				}),
			HashMapDictionaryBuilder.put(
				"model.class.name=", clazz.getName()
			).build());
	}

	@Inject
	protected WorkflowDefinitionLinkLocalService
		workflowDefinitionLinkLocalService;

	@Inject
	protected WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;

	@Inject
	protected WorkflowInstanceManager workflowInstanceManager;

}