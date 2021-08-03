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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.template.web.internal.util.TemplateUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditDDMTemplateDisplayContext {

	public EditDDMTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_ddmGroupServiceConfiguration =
			(DDMGroupServiceConfiguration)liferayPortletRequest.getAttribute(
				DDMGroupServiceConfiguration.class.getName());
		_ddmTemplateHelper =
			(DDMTemplateHelper)liferayPortletRequest.getAttribute(
				DDMTemplateHelper.class.getName());
		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public boolean autogenerateTemplateKey() {
		return false;
	}

	public long getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		_classNameId = BeanParamUtil.getLong(
			getDDMTemplate(), _httpServletRequest, "classNameId");

		return _classNameId;
	}

	public DDMTemplate getDDMTemplate() {
		if ((_ddmTemplate != null) || (getDDMTemplateId() <= 0)) {
			return _ddmTemplate;
		}

		_ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			getDDMTemplateId());

		return _ddmTemplate;
	}

	public HashMap<String, Object> getDDMTemplateEditorContext()
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"editorAutocompleteData", _getAutocompleteJSONObject()
		).put(
			"editorMode", _getEditorMode()
		).put(
			"propertiesViewURL",
			() -> PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/ddm_template_properties.jsp"
			).setTabs1(
				_getTabs1()
			).setParameter(
				"classNameId", getClassNameId()
			).setParameter(
				"classPK", getClassPK()
			).setParameter(
				"ddmTemplateId", getDDMTemplateId()
			).setWindowState(
				LiferayWindowState.EXCLUSIVE
			).buildString()
		).put(
			"script", _getScript()
		).put(
			"showLanguageChangeWarning", _getShowLanguageChangeWarning()
		).put(
			"templateVariableGroups", getTemplateVariableGroupJSONArray()
		).build();
	}

	public String getLanguage() {
		if (_language != null) {
			return _language;
		}

		String language = TemplateConstants.LANG_TYPE_FTL;

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate != null) {
			language = ddmTemplate.getLanguage();
		}

		_language = language;

		return _language;
	}

	public String getSmallImageSource() {
		if (Validator.isNotNull(_smallImageSource)) {
			return _smallImageSource;
		}

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate == null) {
			_smallImageSource = "none";

			return _smallImageSource;
		}

		_smallImageSource = ParamUtil.getString(
			_httpServletRequest, "smallImageSource");

		if (Validator.isNotNull(_smallImageSource)) {
			return _smallImageSource;
		}

		if (!ddmTemplate.isSmallImage()) {
			_smallImageSource = "none";
		}
		else if (Validator.isNotNull(ddmTemplate.getSmallImageURL())) {
			_smallImageSource = "url";
		}
		else if ((ddmTemplate.getSmallImageId() > 0) &&
				 Validator.isNull(ddmTemplate.getSmallImageURL())) {

			_smallImageSource = "file";
		}

		return _smallImageSource;
	}

	public String getTemplateSubtypeLocalizedLabel() {
		return StringPool.BLANK;
	}

	public String getTemplateTypeLabel(long classNameId) {
		return StringPool.BLANK;
	}

	public JSONArray getTemplateVariableGroupJSONArray() throws Exception {
		JSONArray templateVariableGroupJSONArray =
			JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle = _getResourceBundle();

		for (TemplateVariableGroup templateVariableGroup :
				getTemplateVariableGroups()) {

			if (templateVariableGroup.isEmpty()) {
				continue;
			}

			JSONArray templateVariableDefinitionJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (TemplateVariableDefinition templateVariableDefinition :
					templateVariableGroup.getTemplateVariableDefinitions()) {

				templateVariableDefinitionJSONArray.put(
					JSONUtil.put(
						"content",
						TemplateUtil.getDataContent(
							templateVariableDefinition, getLanguage())
					).put(
						"label",
						LanguageUtil.get(
							_httpServletRequest, resourceBundle,
							templateVariableDefinition.getLabel())
					).put(
						"repeatable",
						templateVariableDefinition.isCollection() ||
						templateVariableDefinition.isRepeatable()
					).put(
						"tooltip",
						TemplateUtil.getPaletteItemTitle(
							_httpServletRequest, resourceBundle,
							templateVariableDefinition)
					));
			}

			templateVariableGroupJSONArray.put(
				JSONUtil.put(
					"items", templateVariableDefinitionJSONArray
				).put(
					"label",
					LanguageUtil.get(
						_httpServletRequest, resourceBundle,
						templateVariableGroup.getLabel())
				));
		}

		return templateVariableGroupJSONArray;
	}

	public Collection<TemplateVariableGroup> getTemplateVariableGroups()
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			TemplateContextHelper.getTemplateVariableGroups(
				getTemplateHandlerClassNameId(), getClassPK(), getLanguage(),
				_themeDisplay.getLocale());

		templateVariableGroups.putAll(getAdditionalTemplateVariableGroups());

		return templateVariableGroups.values();
	}

	public String[] imageExtensions() {
		return _ddmGroupServiceConfiguration.smallImageExtensions();
	}

	public boolean isSmallImage() {
		if (_smallImage != null) {
			return _smallImage;
		}

		_smallImage = BeanParamUtil.getBoolean(
			getDDMTemplate(), _httpServletRequest, "smallImage");

		return _smallImage;
	}

	public long smallImageMaxSize() {
		return _ddmGroupServiceConfiguration.smallImageMaxSize();
	}

	protected Map<String, TemplateVariableGroup>
		getAdditionalTemplateVariableGroups() {

		return Collections.emptyMap();
	}

	protected long getClassPK() {
		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate != null) {
			return ddmTemplate.getClassPK();
		}

		return 0;
	}

	protected long getDDMTemplateId() {
		if (_ddmTemplateId != null) {
			return _ddmTemplateId;
		}

		_ddmTemplateId = ParamUtil.getLong(
			_httpServletRequest, "ddmTemplateId");

		return _ddmTemplateId;
	}

	protected String getDefaultScript(long classNameId) {
		return StringPool.BLANK;
	}

	protected long getTemplateHandlerClassNameId() {
		return getClassNameId();
	}

	protected String[] getTemplateLanguageTypes() {
		return new String[] {TemplateConstants.LANG_TYPE_FTL};
	}

	private JSONObject _getAutocompleteJSONObject() throws Exception {
		return JSONFactoryUtil.createJSONObject(
			_ddmTemplateHelper.getAutocompleteJSON(
				_httpServletRequest, getLanguage()));
	}

	private String _getEditorMode() {
		if (Objects.equals(getLanguage(), "ftl")) {
			return "ftl";
		}

		return "velocity";
	}

	private ResourceBundle _getResourceBundle() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(getClassNameId());

		Class<?> clazz = getClass();

		if (templateHandler != null) {
			clazz = templateHandler.getClass();
		}

		return ResourceBundleUtil.getBundle(_themeDisplay.getLocale(), clazz);
	}

	private String _getScript() {
		if (_script != null) {
			return _script;
		}

		_language = BeanParamUtil.getString(
			getDDMTemplate(), _httpServletRequest, "language",
			TemplateConstants.LANG_TYPE_FTL);

		String script = BeanParamUtil.getString(
			getDDMTemplate(), _httpServletRequest, "script");

		if (Validator.isNull(script)) {
			script = getDefaultScript(getClassNameId());
		}

		String scriptContent = ParamUtil.getString(
			_httpServletRequest, "scriptContent");

		if (Validator.isNotNull(scriptContent)) {
			script = scriptContent;
		}

		_script = script;

		return _script;
	}

	private boolean _getShowLanguageChangeWarning() {
		DDMTemplate ddmTemplate = getDDMTemplate();

		if ((ddmTemplate != null) && (getTemplateLanguageTypes().length > 1) &&
			!Objects.equals(ddmTemplate.getLanguage(), getLanguage())) {

			return true;
		}

		return false;
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "information-templates");

		return _tabs1;
	}

	private Long _classNameId;
	private final DDMGroupServiceConfiguration _ddmGroupServiceConfiguration;
	private DDMTemplate _ddmTemplate;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private Long _ddmTemplateId;
	private final HttpServletRequest _httpServletRequest;
	private String _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _script;
	private Boolean _smallImage;
	private String _smallImageSource;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}