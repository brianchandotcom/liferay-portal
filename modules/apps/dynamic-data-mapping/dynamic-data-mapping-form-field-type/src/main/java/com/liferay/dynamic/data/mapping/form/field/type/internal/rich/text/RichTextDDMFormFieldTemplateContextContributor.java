/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.ai.creator.openai.manager.AICreatorOpenAIManager;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Lancha
 * @author Marko Cikos
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.RICH_TEXT,
	service = DDMFormFieldTemplateContextContributor.class
)
public class RichTextDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMForm ddmForm = ddmFormField.getDDMForm();

		JSONObject localeJSONObject = _getLocaleJSONObject(
			ddmForm.getDefaultLocale());

		boolean localizedObjectField = GetterUtil.getBoolean(
			ddmFormField.getProperty("localizedObjectField"));

		return HashMapBuilder.<String, Object>put(
			"availableLocales",
			JSONUtil.toJSONArray(
				_language.getAvailableLocales(), this::_getLocaleJSONObject,
				_log)
		).put(
			"defaultLocale", localeJSONObject
		).put(
			"editingLocale", localeJSONObject
		).put(
			"localizedObjectField", localizedObjectField
		).put(
			"predefinedValue",
			() -> {
				if (localizedObjectField) {
					return _getPredefinedValue(
						ddmFormField, ddmFormFieldRenderingContext);
				}

				return DDMFormFieldTypeUtil.getPropertyValue(
					ddmFormField, ddmFormFieldRenderingContext.getLocale(),
					"predefinedValue");
			}
		).put(
			"value",
			() -> {
				if (localizedObjectField) {
					return _getValueJSONObject(ddmFormFieldRenderingContext);
				}

				return DDMFormFieldTypeUtil.getPropertyValue(
					ddmFormFieldRenderingContext, "value");
			}
		).putAll(
			_getData(ddmFormFieldRenderingContext, ddmFormField.getType())
		).build();
	}

	private Map<String, Object> _getData(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		String ddmFormFieldType) {

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				themeDisplay.getPpid(), ddmFormFieldType, "ckeditor_classic",
				HashMapBuilder.<String, Object>put(
					"liferay-ui:input-editor:allowBrowseDocuments", true
				).put(
					"liferay-ui:input-editor:name",
					ddmFormFieldRenderingContext.getName()
				).put(
					"liferay-ui:input-editor:showAICreator",
					_aiCreatorOpenAIManager.isAICreatorToolbarEnabled(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(),
						ddmFormFieldRenderingContext.getPortletNamespace())
				).build(),
				themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(httpServletRequest));

		return editorConfiguration.getData();
	}

	private JSONObject _getLocaleJSONObject(Locale locale) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		String languageId = LocaleUtil.toLanguageId(locale);

		jsonObject.put(
			"displayName", locale.getDisplayName(locale)
		).put(
			"icon",
			StringUtil.toLowerCase(StringUtil.replace(languageId, '_', "-"))
		).put(
			"localeId", languageId
		);

		return jsonObject;
	}

	private String _getPredefinedValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		LocalizedValue localizedValue = ddmFormField.getPredefinedValue();

		if (localizedValue == null) {
			return null;
		}

		return localizedValue.getString(
			ddmFormFieldRenderingContext.getLocale());
	}

	private JSONObject _getValueJSONObject(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		try {
			return _jsonFactory.createJSONObject(
				ddmFormFieldRenderingContext.getValue());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return _jsonFactory.createJSONObject();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RichTextDDMFormFieldTemplateContextContributor.class);

	@Reference
	private AICreatorOpenAIManager _aiCreatorOpenAIManager;

	@Reference
	private EditorConfigurationFactory _editorConfigurationFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}