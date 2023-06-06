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

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.web.internal.client.AICreatorOpenAIClient;
import com.liferay.ai.creator.openai.web.internal.constants.AICreatorOpenAIPortletKeys;
import com.liferay.ai.creator.openai.web.internal.exception.AICreatorOpenAIClientException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + AICreatorOpenAIPortletKeys.AI_CREATOR_OPENAI,
		"mvc.command.name=/ai_creator_openai/get_completion"
	},
	service = MVCResourceCommand.class
)
public class GetCompletionMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"completion",
					JSONUtil.put(
						"content",
						aiCreatorOpenAIClient.getCompletion(
							_aiCreatorOpenAIConfigurationManager.
								getAICreatorOpenAIGroupAPIKey(
									themeDisplay.getCompanyId(),
									themeDisplay.getScopeGroupId()),
							ParamUtil.getString(resourceRequest, "content"),
							themeDisplay.getLocale(),
							ParamUtil.getString(
								resourceRequest, "tone", "formal"),
							ParamUtil.getInteger(
								resourceRequest, "words")))));
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					aiCreatorOpenAIClientException.getLocalizedMessage(
						themeDisplay.getLocale())));
		}
	}

	@Reference
	protected AICreatorOpenAIClient aiCreatorOpenAIClient;

	@Reference
	private AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;

}