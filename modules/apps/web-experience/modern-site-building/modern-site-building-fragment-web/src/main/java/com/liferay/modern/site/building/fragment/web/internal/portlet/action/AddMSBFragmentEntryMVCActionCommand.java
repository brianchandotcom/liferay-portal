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

package com.liferay.modern.site.building.fragment.web.internal.portlet.action;

import com.liferay.modern.site.building.fragment.constants.MSBFragmentPortletKeys;
import com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentEntryException;
import com.liferay.modern.site.building.fragment.exception.MSBFragmentEntryNameException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MSBFragmentPortletKeys.MODERN_SITE_BUILDING_FRAGMENT,
		"mvc.command.name=addMSBFragmentEntry"
	},
	service = MVCActionCommand.class
)
public class AddMSBFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long msbFragmentCollectionId = ParamUtil.getLong(
			actionRequest, "msbFragmentCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			MSBFragmentEntry msbFragmentEntry =
				_msbFragmentEntryService.addMSBFragmentEntry(
					serviceContext.getScopeGroupId(), msbFragmentCollectionId,
					name, css, html, js, serviceContext);

			jsonObject.put(
				"msbFragmentEntryId", msbFragmentEntry.getMsbFragmentEntryId());
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			HttpServletRequest request = _portal.getHttpServletRequest(
				actionRequest);

			if (pe instanceof MSBFragmentEntryNameException) {
				jsonObject.put(
					"error",
					LanguageUtil.get(request, "this-field-is-required"));
			}
			else if (pe instanceof DuplicateMSBFragmentEntryException) {
				jsonObject.put(
					"error",
					LanguageUtil.get(
						request,
						"this-name-already-exists-please-try-another-one"));
			}
			else {
				jsonObject.put(
					"error", LanguageUtil.get(request, "unknown-error"));
			}
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddMSBFragmentEntryMVCActionCommand.class);

	@Reference
	private MSBFragmentEntryService _msbFragmentEntryService;

	@Reference
	private Portal _portal;

}