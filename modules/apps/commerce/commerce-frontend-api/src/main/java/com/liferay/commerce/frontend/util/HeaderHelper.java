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

package com.liferay.commerce.frontend.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowTask;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Alec Sloan
 */
@ProviderType
public interface HeaderHelper {

	public WorkflowTask getReviewWorkflowTask(
			long companyId, long userId, long beanId, String className)
		throws PortalException;

	public List<HeaderActionModel> getWorkflowTransitionHeaderActionModels(
			long userId, long companyId, String className, long beanId,
			PortletURL transitionPortletURL)
		throws PortalException;

}