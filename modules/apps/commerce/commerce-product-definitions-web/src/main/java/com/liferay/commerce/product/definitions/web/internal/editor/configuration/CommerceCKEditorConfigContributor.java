/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.editor.configuration;

import com.liferay.ai.creator.openai.configuration.manager.AICreatorOpenAIConfigurationManager;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.document.library.kernel.processor.AudioProcessorUtil;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
 * @author Andrea Sbarra
 */
@Component(
	property = {
		"editor.name=ckeditor",
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS
	},
	service = EditorConfigContributor.class
)
public class CommerceCKEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		if (!_isAICreatorChatGPTGroupEnabled(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			return;
		}

		String extraPlugins = (String)jsonObject.get("extraPlugins");

		JSONArray toolbarSimpleJSONArray = _getToolbarSimpleJSONArray();

		jsonObject.put(
			"aiCreatorOpenAIURL",
			() -> PortletURLBuilder.create(
				requestBackedPortletURLFactory.createControlPanelRenderURL(
					"com_liferay_ai_creator_openai_web_internal_portlet_" +
						"AICreatorOpenAIPortlet",
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
				"com_liferay_ai_creator_openai_web_internal_portlet_" +
					"AICreatorOpenAIPortlet")
		).put(
			"extraPlugins", extraPlugins.concat(",aicreator")
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
		).put(
			"removePlugins", "elementspath"
		).put(
			"toolbar_editInPlace", toolbarSimpleJSONArray
		).put(
			"toolbar_email", toolbarSimpleJSONArray
		).put(
			"toolbar_liferay", toolbarSimpleJSONArray
		).put(
			"toolbar_liferayArticle", toolbarSimpleJSONArray
		).put(
			"toolbar_phone", toolbarSimpleJSONArray
		).put(
			"toolbar_simple", toolbarSimpleJSONArray
		).put(
			"toolbar_tablet", toolbarSimpleJSONArray
		).put(
			"toolbar_text_advanced", _getToolbarTextAdvancedJSONArray()
		).put(
			"toolbar_text_simple", _getToolbarTextSimpleJSONArray()
		);
	}

	private JSONArray _getToolbarSimpleJSONArray() {
		return JSONUtil.putAll(
			toJSONArray("['Undo', 'Redo']"),
			toJSONArray("['Styles', 'Bold', 'Italic', 'Underline']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', Unlink]"),
			toJSONArray("['Table', 'ImageSelector', 'VideoSelector']")
		).put(
			() -> {
				if (AudioProcessorUtil.isEnabled()) {
					return toJSONArray("['AudioSelector']");
				}

				return null;
			}
		).put(
			toJSONArray("['AICreator']")
		);
	}

	private JSONArray _getToolbarTextAdvancedJSONArray() {
		return JSONUtil.putAll(
			toJSONArray("['Undo', 'Redo']"), toJSONArray("['Styles']"),
			toJSONArray("['FontColor', 'BGColor']"),
			toJSONArray("['Bold', 'Italic', 'Underline', 'Strikethrough']"),
			toJSONArray("['RemoveFormat']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['IncreaseIndent', 'DecreaseIndent']"),
			toJSONArray("['IncreaseIndent', 'DecreaseIndent']"),
			toJSONArray("['Link', Unlink]")
		).put(
			toJSONArray("['AICreator']")
		);
	}

	private JSONArray _getToolbarTextSimpleJSONArray() {
		return JSONUtil.putAll(
			toJSONArray("['Undo', 'Redo']"),
			toJSONArray("['Styles', 'Bold', 'Italic', 'Underline']"),
			toJSONArray("['NumberedList', 'BulletedList']"),
			toJSONArray("['Link', Unlink]")
		).put(
			toJSONArray("['AICreator']")
		);
	}

	private boolean _isAICreatorChatGPTGroupEnabled(
		long companyId, long groupId) {

		try {
			if (_aiCreatorOpenAIConfigurationManager.
					isAICreatorChatGPTGroupEnabled(companyId, groupId) &&
				FeatureFlagManagerUtil.isEnabled("LPD-10862")) {

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
		CommerceCKEditorConfigContributor.class);

	@Reference
	private AICreatorOpenAIConfigurationManager
		_aiCreatorOpenAIConfigurationManager;

	@Reference
	private Portal _portal;

}