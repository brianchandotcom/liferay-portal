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

package com.liferay.redirect.web.internal.portlet.action;

import com.google.re2j.Pattern;
import com.google.re2j.PatternSyntaxException;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectPatternEntry;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"mvc.command.name=/redirect/edit_redirect_patterns"
	},
	service = MVCActionCommand.class
)
public class EditRedirectPatternsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_redirectPatternConfigurationProvider.updatePatternStrings(
				themeDisplay.getScopeGroupId(),
				_getRedirectPatternEntries(actionRequest));
		}
		catch (ConfigurationModelListenerException | PatternSyntaxException
					exception) {

			_log.error(exception);

			SessionErrors.add(actionRequest, "redirectPatternInvalid");

			hideDefaultErrorMessage(actionRequest);

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	private List<RedirectPatternEntry> _getRedirectPatternEntries(
		ActionRequest actionRequest) {

		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (int i = 0; parameterMap.containsKey("pattern_" + i); i++) {
			String patternString = null;

			String[] patterStrings = parameterMap.get("pattern_" + i);

			if ((patterStrings.length != 0) &&
				Validator.isNotNull(patterStrings[0])) {

				patternString = patterStrings[0];
			}

			String destinationURL = null;

			String[] destinationURLs = parameterMap.get("destinationURL_" + i);

			if ((destinationURLs.length != 0) &&
				Validator.isNotNull(destinationURLs[0])) {

				destinationURL = destinationURLs[0];
			}

			String userAgent = _getUserAgent(parameterMap, i);

			if ((patternString != null) && (destinationURL != null) &&
				(userAgent != null)) {

				redirectPatternEntries.add(
					new RedirectPatternEntry(
						Pattern.compile(patternString), destinationURL,
						userAgent));
			}
		}

		return redirectPatternEntries;
	}

	private String _getUserAgent(Map<String, String[]> parameterMap, int i) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-175850")) {
			return RedirectConstants.USER_AGENT_ALL;
		}

		String[] userAgents = parameterMap.get("userAgent_" + i);

		if ((userAgents.length != 0) && Validator.isNotNull(userAgents[0])) {
			return userAgents[0];
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditRedirectPatternsMVCActionCommand.class);

	@Reference
	private RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;

}