/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.display.context;

import com.liferay.ai.creator.openai.web.internal.constants.AICreatorOpenAIPortletKeys;
import com.liferay.learn.LearnMessageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Map;

import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class AICreatorOpenAIDisplayContext {

	public AICreatorOpenAIDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public Map<String, Object> getProps() {
		if (isGenerations()) {
			return HashMapBuilder.<String, Object>put(
				"getGenerationsURL",
				() -> {
					RequestBackedPortletURLFactory
						requestBackedPortletURLFactory =
							RequestBackedPortletURLFactoryUtil.create(
								_httpServletRequest);

					return ResourceURLBuilder.createResourceURL(
						(ResourceURL)
							requestBackedPortletURLFactory.createResourceURL(
								AICreatorOpenAIPortletKeys.AI_CREATOR_OPENAI)
					).setResourceID(
						"/ai_creator_openai/get_generations"
					).buildString();
				}
			).put(
				"learnResources",
				LearnMessageUtil.getReactDataJSONObject("ai-creator-openai-web")
			).build();
		}

		return HashMapBuilder.<String, Object>put(
			"getCompletionURL",
			() -> {
				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(
						_httpServletRequest);

				return ResourceURLBuilder.createResourceURL(
					(ResourceURL)
						requestBackedPortletURLFactory.createResourceURL(
							AICreatorOpenAIPortletKeys.AI_CREATOR_OPENAI)
				).setResourceID(
					"/ai_creator_openai/get_completion"
				).buildString();
			}
		).put(
			"learnResources",
			LearnMessageUtil.getReactDataJSONObject("ai-creator-openai-web")
		).build();
	}

	public boolean isGenerations() {
		if (_generations != null) {
			return _generations;
		}

		_generations = ParamUtil.getBoolean(_httpServletRequest, "generations");

		return _generations;
	}

	private Boolean _generations;
	private final HttpServletRequest _httpServletRequest;

}