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

package com.liferay.headless.admin.workflow.resource.v1_0.test.workflow;

import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed",
	service = WorkflowHandler.class
)
public class ObjectReviewedWorkflowHandler
	extends BaseWorkflowHandler<ObjectReviewed> {

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

	@Deactivate
	protected void deactivate() {
		ObjectReviewedTestUtil.clear();
	}

}