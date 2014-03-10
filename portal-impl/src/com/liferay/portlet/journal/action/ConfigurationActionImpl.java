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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

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

		boolean emailArticleAddedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailArticleAddedEnabled"));
		String emailArticleAddedSubject = getParameter(
			actionRequest, "emailArticleAddedSubject");
		String emailArticleAddedBody = getParameter(
			actionRequest, "emailArticleAddedBody");

		if (emailArticleAddedEnabled) {
			if (Validator.isNull(emailArticleAddedSubject)) {
				SessionErrors.add(actionRequest, "emailArticleAddedSubject");
			}
			else if (Validator.isNull(emailArticleAddedBody)) {
				SessionErrors.add(actionRequest, "emailArticleAddedBody");
			}
		}
	}

	protected void validateEmailArticleApprovalDenied(
			ActionRequest actionRequest)
		throws Exception {

		boolean emailArticleApprovalDeniedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailArticleApprovalDeniedEnabled"));
		String emailArticleApprovalDeniedSubject = getParameter(
			actionRequest, "emailArticleApprovalDeniedSubject");
		String emailArticleApprovalDeniedBody = getParameter(
			actionRequest, "emailArticleApprovalDeniedBody");

		if (emailArticleApprovalDeniedEnabled) {
			if (Validator.isNull(emailArticleApprovalDeniedSubject)) {
				SessionErrors.add(
					actionRequest, "emailArticleApprovalDeniedSubject");
			}
			else if (Validator.isNull(emailArticleApprovalDeniedBody)) {
				SessionErrors.add(
					actionRequest, "emailArticleApprovalDeniedBody");
			}
		}
	}

	protected void validateEmailArticleApprovalGranted(
			ActionRequest actionRequest)
		throws Exception {

		boolean emailArticleApprovalGrantedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailArticleApprovalGrantedEnabled"));
		String emailArticleApprovalGrantedSubject = getParameter(
			actionRequest, "emailArticleApprovalGrantedSubject");
		String emailArticleApprovalGrantedBody = getParameter(
			actionRequest, "emailArticleApprovalGrantedBody");

		if (emailArticleApprovalGrantedEnabled) {
			if (Validator.isNull(emailArticleApprovalGrantedSubject)) {
				SessionErrors.add(
					actionRequest, "emailArticleApprovalGrantedSubject");
			}
			else if (Validator.isNull(emailArticleApprovalGrantedBody)) {
				SessionErrors.add(
					actionRequest, "emailArticleApprovalGrantedBody");
			}
		}
	}

	protected void validateEmailArticleApprovalRequested(
			ActionRequest actionRequest)
		throws Exception {

		boolean emailArticleApprovalRequestedEnabled =
			GetterUtil.getBoolean(
				getParameter(
					actionRequest, "emailArticleApprovalRequestedEnabled"));
		String emailArticleApprovalRequestedSubject = getParameter(
			actionRequest, "emailArticleApprovalRequestedSubject");
		String emailArticleApprovalRequestedBody = getParameter(
			actionRequest, "emailArticleApprovalRequestedBody");

		if (emailArticleApprovalRequestedEnabled) {
			if (Validator.isNull(emailArticleApprovalRequestedSubject)) {
				SessionErrors.add(
					actionRequest, "emailArticleApprovalRequestedSubject");
			}
			else if (Validator.isNull(emailArticleApprovalRequestedBody)) {
				SessionErrors.add(
					actionRequest, "emailArticleApprovalRequestedBody");
			}
		}
	}

	protected void validateEmailArticleReview(ActionRequest actionRequest)
		throws Exception {

		boolean emailArticleReviewEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailArticleReviewEnabled"));
		String emailArticleReviewSubject = getParameter(
			actionRequest, "emailArticleReviewSubject");
		String emailArticleReviewBody = getParameter(
			actionRequest, "emailArticleReviewBody");

		if (emailArticleReviewEnabled) {
			if (Validator.isNull(emailArticleReviewSubject)) {
				SessionErrors.add(actionRequest, "emailArticleReviewSubject");
			}
			else if (Validator.isNull(emailArticleReviewBody)) {
				SessionErrors.add(actionRequest, "emailArticleReviewBody");
			}
		}
	}

	protected void validateEmailArticleUpdated(ActionRequest actionRequest)
		throws Exception {

		boolean emailArticleUpdatedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailArticleUpdatedEnabled"));
		String emailArticleUpdatedSubject = getParameter(
			actionRequest, "emailArticleUpdatedSubject");
		String emailArticleUpdatedBody = getParameter(
			actionRequest, "emailArticleUpdatedBody");

		if (emailArticleUpdatedEnabled) {
			if (Validator.isNull(emailArticleUpdatedSubject)) {
				SessionErrors.add(actionRequest, "emailArticleUpdatedSubject");
			}
			else if (Validator.isNull(emailArticleUpdatedBody)) {
				SessionErrors.add(actionRequest, "emailArticleUpdatedBody");
			}
		}
	}

}