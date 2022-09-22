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

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;

import java.util.LinkedHashMap;
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
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"mvc.command.name=/redirect_settings/edit_redirect_patterns"
	},
	service = MVCActionCommand.class
)
public class EditRedirectPatternsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_redirectPatternConfigurationProvider.updateRedirectionPatterns(
				ParamUtil.getLong(actionRequest, "scopePK"),
				_getRedirectionPatterns(actionRequest));
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			SessionErrors.add(
				actionRequest, configurationModelListenerException.getClass());

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	private Map<String, String> _getRedirectionPatterns(
		ActionRequest actionRequest) {

		Map<String, String> redirectPatterns = new LinkedHashMap<>();

		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (int i = 0; parameterMap.containsKey("pattern_" + i); i++) {
			String pattern = null;

			String[] patterns = parameterMap.get("pattern_" + i);

			if ((patterns.length != 0) && Validator.isNotNull(patterns[0])) {
				pattern = patterns[0];
			}

			String destinationURL = null;

			String[] destinationURLs = parameterMap.get("destinationURL_" + i);

			if ((destinationURLs.length != 0) &&
				Validator.isNotNull(destinationURLs[0])) {

				destinationURL = destinationURLs[0];
			}

			if ((pattern != null) || (destinationURL != null)) {
				redirectPatterns.put(pattern, destinationURL);
			}
		}

		return redirectPatterns;
	}

	@Reference
	private RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;

}