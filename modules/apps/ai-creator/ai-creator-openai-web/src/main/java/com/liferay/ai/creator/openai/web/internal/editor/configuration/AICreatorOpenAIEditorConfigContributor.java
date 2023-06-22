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

package com.liferay.ai.creator.openai.web.internal.editor.configuration;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.ai.creator.openai.web.internal.constants.AICreatorOpenAIPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletMode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"editor.config.key=rich_text",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL
	},
	service = EditorConfigContributor.class
)
public class AICreatorOpenAIEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-179483") ||
			!_isAICreatorOpenAIGroupEnabled(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			return;
		}

		jsonObject.put(
			"aiCreatorOpenAIURL",
			() -> PortletURLBuilder.create(
				requestBackedPortletURLFactory.createControlPanelRenderURL(
					AICreatorOpenAIPortletKeys.AI_CREATOR_OPENAI,
					themeDisplay.getScopeGroup(),
					themeDisplay.getRefererGroupId(), 0)
			).setMVCPath(
				"/view.jsp"
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"aiCreatorPortletNamespace",
			() -> _portal.getPortletNamespace(
				AICreatorOpenAIPortletKeys.AI_CREATOR_OPENAI)
		).put(
			"isAICreatorOpenAIAPIKey",
			() -> {
				try {
					if (Validator.isNotNull(
							_aiCreatorOpenAIConfigurationManager.
								getAICreatorOpenAIGroupAPIKey(
									themeDisplay.getCompanyId(),
									themeDisplay.getScopeGroupId()))) {

						return true;
					}
				}
				catch (ConfigurationException configurationException) {
					if (_log.isDebugEnabled()) {
						_log.debug(configurationException);
					}
				}

				return false;
			}
		);
	}

	private boolean _isAICreatorOpenAIGroupEnabled(
		long companyId, long groupId) {

		try {
			if (_aiCreatorOpenAIConfigurationManager.
					isAICreatorOpenAIGroupEnabled(companyId, groupId)) {

				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AICreatorOpenAIEditorConfigContributor.class);

	@Reference
	private AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;

	@Reference
	private Portal _portal;

}