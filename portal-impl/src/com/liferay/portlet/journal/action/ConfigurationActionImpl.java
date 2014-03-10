/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmailFrom(actionRequest);
		validateEmailArticleAdded(actionRequest);
		validateEmailArticleApprovalDenied(actionRequest);
		validateEmailArticleApprovalGranted(actionRequest);
		validateEmailArticleApprovalRequested(actionRequest);
		validateEmailArticleReview(actionRequest);
		validateEmailArticleUpdated(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void validateEmailArticleAdded(ActionRequest actionRequest)
		throws Exception {

		validateEmail(actionRequest, "emailArticleAdded");
	}

	protected void validateEmailArticleApprovalDenied(
			ActionRequest actionRequest)
		throws Exception {

		validateEmail(actionRequest, "emailArticleApprovalDenied");
	}

	protected void validateEmailArticleApprovalGranted(
			ActionRequest actionRequest)
		throws Exception {

		validateEmail(actionRequest, "emailArticleApprovalGranted");
	}

	protected void validateEmailArticleApprovalRequested(
			ActionRequest actionRequest)
		throws Exception {

		validateEmail(actionRequest, "emailArticleApprovalRequested");
	}

	protected void validateEmailArticleReview(ActionRequest actionRequest)
		throws Exception {

		validateEmail(actionRequest, "emailArticleReview");
	}

	protected void validateEmailArticleUpdated(ActionRequest actionRequest)
		throws Exception {

		validateEmail(actionRequest, "emailArticleUpdated");
	}

}