/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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