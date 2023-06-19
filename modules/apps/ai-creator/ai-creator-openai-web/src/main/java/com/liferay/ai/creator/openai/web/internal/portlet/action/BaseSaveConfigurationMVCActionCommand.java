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

package com.liferay.ai.creator.openai.web.internal.portlet.action;

import com.liferay.ai.creator.openai.web.internal.client.AICreatorOpenAIClient;
import com.liferay.ai.creator.openai.web.internal.exception.AICreatorOpenAIClientException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Lourdes Fernández Besada
 */
public abstract class BaseSaveConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	protected abstract void checkPermission(ThemeDisplay themeDisplay)
		throws PortalException, PortletException;

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		checkPermission(themeDisplay);

		String successMessageKey = "your-request-completed-successfully";

		String apiKey = ParamUtil.getString(actionRequest, "apiKey");

		if (Validator.isNotNull(apiKey)) {
			try {
				aiCreatorOpenAIClient.validateAPIKey(apiKey);

				successMessageKey = "your-api-key-was-successfully-added";
			}
			catch (AICreatorOpenAIClientException
						aiCreatorOpenAIClientException) {

				SessionErrors.add(
					actionRequest, aiCreatorOpenAIClientException.getClass(),
					aiCreatorOpenAIClientException);

				hideDefaultErrorMessage(actionRequest);

				sendRedirect(actionRequest, actionResponse);

				return;
			}
		}

		saveAICreatorOpenAIConfiguration(
			apiKey, ParamUtil.getBoolean(actionRequest, "enableOpenAI"),
			themeDisplay);

		SessionMessages.add(
			actionRequest, "requestProcessed",
			language.get(themeDisplay.getLocale(), successMessageKey));

		sendRedirect(actionRequest, actionResponse);
	}

	protected abstract void saveAICreatorOpenAIConfiguration(
			String apiKey, boolean enableOpenAI, ThemeDisplay themeDisplay)
		throws ConfigurationException;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile AICreatorOpenAIClient aiCreatorOpenAIClient;

	@Reference
	protected Language language;

}