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
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.template.web.internal.util.TemplateDDMTemplateUtil;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditDDMTemplateDisplayContext {

	public EditDDMTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		this.liferayPortletResponse = liferayPortletResponse;

		_ddmGroupServiceConfiguration =
			(DDMGroupServiceConfiguration)liferayPortletRequest.getAttribute(
				DDMGroupServiceConfiguration.class.getName());
		_ddmTemplateHelper =
			(DDMTemplateHelper)liferayPortletRequest.getAttribute(
				DDMTemplateHelper.class.getName());
		httpServletRequest = PortalUtil.getHttpServletRequest(
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
			getDDMTemplate(), _liferayPortletRequest, "classNameId");

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

	public Map<String, Object> getDDMTemplateEditorContext() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"editorAutocompleteData",
			JSONFactoryUtil.createJSONObject(
				_ddmTemplateHelper.getAutocompleteJSON(
					httpServletRequest, TemplateConstants.LANG_TYPE_FTL))
		).put(
			"propertiesViewURL",
			() -> PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/template/edit_ddm_template_properties"
			).setTabs1(
				getTabs1()
			).setParameter(
				"classNameId", getClassNameId()
			).setParameter(
				"classPK", getClassPK()
			).setParameter(
				"ddmTemplateId", getDDMTemplateId()
			).setParameter(
				"templateEntryId", getTemplateEntryId()
			).setWindowState(
				LiferayWindowState.EXCLUSIVE
			).buildString()
		).put(
			"script", _getScript()
		).put(
			"templateVariableGroups", _getTemplateVariableGroupJSONArray()
		).build();
	}

	public String getRefererWebDAVToken() {
		if (_refererWebDAVToken != null) {
			return _refererWebDAVToken;
		}

		PortletConfig portletConfig =
			(PortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		_refererWebDAVToken = ParamUtil.getString(
			httpServletRequest, "refererWebDAVToken",
			portletConfig.getInitParameter("refererWebDAVToken"));

		return _refererWebDAVToken;
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
			_liferayPortletRequest, "smallImageSource");

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

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(
			_liferayPortletRequest, "tabs1", "information-templates");

		return _tabs1;
	}

	public long getTemplateEntryId() {
		if (_templateEntryId != null) {
			return _templateEntryId;
		}

		_templateEntryId = ParamUtil.getLong(
			_liferayPortletRequest, "templateEntryId");

		return _templateEntryId;
	}

	public String getTemplateSubtypeLabel() {
		return StringPool.BLANK;
	}

	public String getTemplateTypeLabel() {
		return StringPool.BLANK;
	}

	public String getUpdateDDMTemplateURL() {
		return StringPool.BLANK;
	}

	public boolean isSmallImage() {
		if (_smallImage != null) {
			return _smallImage;
		}

		_smallImage = BeanParamUtil.getBoolean(
			getDDMTemplate(), _liferayPortletRequest, "smallImage");

		return _smallImage;
	}

	public String[] smallImageExtensions() {
		return _ddmGroupServiceConfiguration.smallImageExtensions();
	}

	public int smallImageMaxSize() {
		return _ddmGroupServiceConfiguration.smallImageMaxSize();
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
			_liferayPortletRequest, "ddmTemplateId");

		return _ddmTemplateId;
	}

	protected String getDefaultScript() {
		return "<#-- Empty script -->";
	}

	protected long getTemplateHandlerClassNameId() {
		return getClassNameId();
	}

	protected Collection<TemplateVariableGroup> getTemplateVariableGroups()
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			TemplateContextHelper.getTemplateVariableGroups(
				getTemplateHandlerClassNameId(), getClassPK(),
				TemplateConstants.LANG_TYPE_FTL, _themeDisplay.getLocale());

		return templateVariableGroups.values();
	}

	protected final HttpServletRequest httpServletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;

	private String _getScript() {
		if (_script != null) {
			return _script;
		}

		String script = BeanParamUtil.getString(
			getDDMTemplate(), httpServletRequest, "script");

		if (Validator.isNull(script)) {
			script = getDefaultScript();
		}

		String scriptContent = ParamUtil.getString(
			httpServletRequest, "scriptContent");

		if (Validator.isNotNull(scriptContent)) {
			script = scriptContent;
		}

		_script = script;

		return _script;
	}

	private ResourceBundle _getTemplateHandlerResourceBundle() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(getClassNameId());

		Class<?> clazz = getClass();

		if (templateHandler != null) {
			clazz = templateHandler.getClass();
		}

		return ResourceBundleUtil.getBundle(_themeDisplay.getLocale(), clazz);
	}

	private JSONArray _getTemplateVariableGroupJSONArray() throws Exception {
		JSONArray templateVariableGroupJSONArray =
			JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle = _getTemplateHandlerResourceBundle();

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
						TemplateDDMTemplateUtil.getDataContent(
							templateVariableDefinition)
					).put(
						"label",
						LanguageUtil.get(
							_themeDisplay.getRequest(), resourceBundle,
							templateVariableDefinition.getLabel())
					).put(
						"repeatable",
						templateVariableDefinition.isCollection() ||
						templateVariableDefinition.isRepeatable()
					).put(
						"tooltip",
						TemplateDDMTemplateUtil.getPaletteItemTitle(
							_themeDisplay.getRequest(), resourceBundle,
							templateVariableDefinition)
					));
			}

			templateVariableGroupJSONArray.put(
				JSONUtil.put(
					"items", templateVariableDefinitionJSONArray
				).put(
					"label",
					LanguageUtil.get(
						_themeDisplay.getRequest(),
						templateVariableGroup.getLabel())
				));
		}

		return templateVariableGroupJSONArray;
	}

	private Long _classNameId;
	private final DDMGroupServiceConfiguration _ddmGroupServiceConfiguration;
	private DDMTemplate _ddmTemplate;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private Long _ddmTemplateId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private String _refererWebDAVToken;
	private String _script;
	private Boolean _smallImage;
	private String _smallImageSource;
	private String _tabs1;
	private Long _templateEntryId;
	private final ThemeDisplay _themeDisplay;

}