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

package com.liferay.commerce.organization.web.internal.portlet.action;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.web.internal.constants.CommerceOrganizationPortletKeys;
import com.liferay.portal.kernel.exception.DuplicateOrganizationException;
import com.liferay.portal.kernel.exception.OrganizationNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceOrganizationPortletKeys.COMMERCE_ORGANIZATION,
		"mvc.command.name=addBranch"
	},
	service = MVCActionCommand.class
)
public class AddBranchMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			long organizationId = ParamUtil.getLong(
				actionRequest, "organizationId");

			String name = ParamUtil.getString(actionRequest, "name");
			String redirect = ParamUtil.getString(actionRequest, "redirect");
			String type = ParamUtil.getString(actionRequest, "type");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Organization.class.getName(), actionRequest);

			_commerceOrganizationService.addOrganization(
				organizationId, name, type, serviceContext);

			jsonObject.put("redirectURL", redirect);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			String error;

			if (pe instanceof DuplicateOrganizationException) {
				error = LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-organization-name-is-already-taken");
			}
			else if (pe instanceof OrganizationNameException) {
				error = LanguageUtil.format(
					themeDisplay.getLocale(),
					"the-x-cannot-be-x-or-a-reserved-word-such-as-x",
					new String[] {
						OrganizationConstants.NAME_LABEL,
						OrganizationConstants.NAME_GENERAL_RESTRICTIONS,
						OrganizationConstants.NAME_RESERVED_WORDS
					});
			}
			else {
				error = LanguageUtil.get(
					themeDisplay.getLocale(), "an-unexpected-error-occurred");
			}

			jsonObject.put("error", error);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddBranchMVCActionCommand.class);

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

}