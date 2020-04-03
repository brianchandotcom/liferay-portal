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

package com.liferay.flags.web.internal.portlet.action;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.flags.service.FlagsEntryService;
import com.liferay.flags.web.internal.constants.FlagsPortletKeys;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + FlagsPortletKeys.FLAGS,
		"mvc.command.name=/flags/edit_entry"
	},
	service = MVCActionCommand.class
)
public class EditEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			CaptchaUtil.check(actionRequest);

			String className = ParamUtil.getString(actionRequest, "className");
			long classPK = ParamUtil.getLong(actionRequest, "classPK");
			String reporterEmailAddress = ParamUtil.getString(
				actionRequest, "reporterEmailAddress");
			long reportedUserId = ParamUtil.getLong(
				actionRequest, "reportedUserId");
			String contentTitle = ParamUtil.getString(
				actionRequest, "contentTitle");
			String contentURL = ParamUtil.getString(
				actionRequest, "contentURL");
			String reason = ParamUtil.getString(actionRequest, "reason");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				"com.liferay.portlet.flags.model.FlagsEntry", actionRequest);

			_flagsEntryService.addEntry(
				className, classPK, reporterEmailAddress, reportedUserId,
				contentTitle, contentURL, reason, serviceContext);
		}
		catch (CaptchaException captchaException) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(),
					_getCaptchaExceptionErrorMessageKey(captchaException)));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Reference(unbind = "-")
	protected void setFlagsEntryService(FlagsEntryService flagsEntryService) {
		_flagsEntryService = flagsEntryService;
	}

	private String _getCaptchaExceptionErrorMessageKey(
		CaptchaException captchaException) {

		if (captchaException instanceof CaptchaTextException) {
			return "text-verification-failed";
		}

		return "captcha-verification-failed";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditEntryMVCActionCommand.class);

	private FlagsEntryService _flagsEntryService;

}