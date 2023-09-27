/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.web.internal.portlet.action;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.web.internal.client.AICreatorOpenAIClient;
import com.liferay.ai.creator.openai.web.internal.constants.AICreatorOpenAIPortletKeys;
import com.liferay.ai.creator.openai.web.internal.exception.AICreatorOpenAIClientException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

		if (!_aiCreatorOpenAIConfigurationManager.isAICreatorOpenAIGroupEnabled(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					JSONUtil.put(
						"message",
						_language.get(
							themeDisplay.getLocale(),
							"openai-is-disabled.-enable-openai-from-the-" +
								"settings-page-or-contact-your-administrator")
					).put(
						"retry", false
					)));

			return;
		}

		String apiKey =
			_aiCreatorOpenAIConfigurationManager.getAICreatorOpenAIGroupAPIKey(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId());

		if (Validator.isNull(apiKey)) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					JSONUtil.put(
						"message",
						_language.get(
							themeDisplay.getLocale(),
							"authentication-is-needed-to-use-this-feature.-" +
								"contact-your-administrator-to-add-an-api-" +
									"key-in-instance-or-site-settings")
					).put(
						"retry", false
					)));

			return;
		}

		String content = ParamUtil.getString(resourceRequest, "content");

		if (Validator.isNull(content)) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					JSONUtil.put(
						"message",
						_language.format(
							themeDisplay.getLocale(), "the-x-is-required",
							"content")
					).put(
						"retry", false
					)));

			return;
		}

		try {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"completion",
					JSONUtil.put(
						"content",
						aiCreatorOpenAIClient.getCompletion(
							apiKey, content,
							LocaleUtil.fromLanguageId(
								ParamUtil.getString(
									resourceRequest, "languageId",
									themeDisplay.getLanguageId())),
							ParamUtil.getString(
								resourceRequest, "tone", "formal"),
							ParamUtil.getInteger(resourceRequest, "words")))));
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					JSONUtil.put(
						"message",
						aiCreatorOpenAIClientException.
							getCompletionLocalizedMessage(
								themeDisplay.getLocale()))));
		}
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile AICreatorOpenAIClient aiCreatorOpenAIClient;

	@Reference
	private AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;

	@Reference
	private Language _language;

}