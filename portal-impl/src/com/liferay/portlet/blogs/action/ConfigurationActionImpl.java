/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Jorge Ferrer
 * @author Thiago Moreira
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");

		if (Validator.isNotNull(cmd)) {
			if (tabs2.equals("email-from")) {
				validateEmailFrom(actionRequest);
			}
			else if (tabs2.equals("entry-added-email")) {
				validateEmailEntryAdded(actionRequest);
			}
			else if (tabs2.equals("entry-updated-email")) {
				validateEmailEntryUpdated(actionRequest);
			}
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void validateEmailEntryAdded(ActionRequest actionRequest)
		throws Exception {

		String emailEntryAddedSubject = getLocalizedParameter(
			actionRequest, "emailEntryAddedSubject");
		String emailEntryAddedBody = getLocalizedParameter(
			actionRequest, "emailEntryAddedBody");

		if (Validator.isNull(emailEntryAddedSubject)) {
			SessionErrors.add(actionRequest, "emailEntryAddedSubject");
		}
		else if (Validator.isNull(emailEntryAddedBody)) {
			SessionErrors.add(actionRequest, "emailEntryAddedBody");
		}
	}

	protected void validateEmailEntryUpdated(ActionRequest actionRequest)
		throws Exception {

		String emailEntryUpdatedSubject = getLocalizedParameter(
			actionRequest, "emailEntryUpdatedSubject");
		String emailEntryUpdatedBody = getLocalizedParameter(
			actionRequest, "emailEntryUpdatedBody");

		if (Validator.isNull(emailEntryUpdatedSubject)) {
			SessionErrors.add(actionRequest, "emailEntryUpdatedSubject");
		}
		else if (Validator.isNull(emailEntryUpdatedBody)) {
			SessionErrors.add(actionRequest, "emailEntryUpdatedBody");
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

}