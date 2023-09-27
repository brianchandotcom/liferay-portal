/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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