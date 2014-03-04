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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Bruno Farache
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateDisplaySettings(actionRequest);
		validateEmailFrom(actionRequest);
		validateEmailPageAdded(actionRequest);
		validateEmailPageUpdated(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void validateDisplaySettings(ActionRequest actionRequest)
		throws Exception {

		String visibleNodes = getParameter(actionRequest, "visibleNodes");

		if (Validator.isNull(visibleNodes)) {
			SessionErrors.add(actionRequest, "visibleNodesCount");
		}
	}

	protected void validateEmailFrom(ActionRequest actionRequest)
		throws Exception {

		String emailFromName = getParameter(actionRequest, "emailFromName");
		String emailFromAddress = getParameter(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress) &&
				 !Validator.isVariableTerm(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailFromAddress");
		}
	}

	protected void validateEmailPageAdded(ActionRequest actionRequest)
		throws Exception {

		boolean emailPageAddedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailPageAddedEnabled"));
		String emailPageAddedSubject = getParameter(
			actionRequest, "emailPageAddedSubject");
		String emailPageAddedBody = getParameter(
			actionRequest, "emailPageAddedBody");

		if (emailPageAddedEnabled) {
			if (Validator.isNull(emailPageAddedSubject)) {
				SessionErrors.add(actionRequest, "emailPageAddedSubject");
			}
			else if (Validator.isNull(emailPageAddedBody)) {
				SessionErrors.add(actionRequest, "emailPageAddedBody");
			}
		}
	}

	protected void validateEmailPageUpdated(ActionRequest actionRequest)
		throws Exception {

		boolean emailPageUpdatedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailPageUpdatedEnabled"));
		String emailPageUpdatedSubject = getParameter(
			actionRequest, "emailPageUpdatedSubject");
		String emailPageUpdatedBody = getParameter(
			actionRequest, "emailPageUpdatedBody");

		if (emailPageUpdatedEnabled) {
			if (Validator.isNull(emailPageUpdatedSubject)) {
				SessionErrors.add(actionRequest, "emailPageUpdatedSubject");
			}
			else if (Validator.isNull(emailPageUpdatedBody)) {
				SessionErrors.add(actionRequest, "emailPageUpdatedBody");
			}
		}
	}

}