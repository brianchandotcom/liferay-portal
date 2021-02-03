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

package com.liferay.headless.admin.workflow.resource.v1_0.test.util;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rafael Praxedes
 */
public class WorkflowHandlerRegistryTestUtil {

	public static void registerWorkflowHandler() {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowHandlerRegistryTestUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			(Class<WorkflowHandler<ObjectReviewed>>)
				(Class<?>)WorkflowHandler.class,
			new BaseWorkflowHandler<ObjectReviewed>() {

				@Override
				public String getClassName() {
					return ObjectReviewed.class.getName();
				}

				@Override
				public String getTitle(long classPK, Locale locale) {
					ObjectReviewed objectReviewed =
						ObjectReviewedTestUtil.getObjectReviewed(classPK);

					return objectReviewed.getAssetTitle();
				}

				@Override
				public String getType(Locale locale) {
					return "ObjectReviewed";
				}

				@Override
				public ObjectReviewed updateStatus(
						int status, Map<String, Serializable> workflowContext)
					throws PortalException {

					return null;
				}

			},
			null);
	}

	public static void unregisterWorkflowHandler() {
		if (_serviceRegistration == null) {
			return;
		}

		_serviceRegistration.unregister();

		_serviceRegistration = null;
	}

	private static ServiceRegistration<WorkflowHandler<ObjectReviewed>>
		_serviceRegistration;

}