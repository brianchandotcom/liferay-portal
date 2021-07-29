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

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Objects;

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

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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
			"showCacheableWarning", false
		).put(
			"showLanguageChangeWarning", _getShowLanguageChangeWarning()
		).put(
			"showPropertiesPanel",
			() -> {
				DDMTemplate ddmTemplate = getDDMTemplate();

				if ((ddmTemplate == null) || (ddmTemplate.getClassPK() <= 0)) {
					return true;
				}

				return false;
			}
		).put(
			"templateVariableGroups", getTemplateVariableGroupJSONArray()
		).build();
	}

	public String getTemplateTypeLabel(long classNameId) {
		return StringPool.BLANK;
	}

	public JSONArray getTemplateVariableGroupJSONArray() throws Exception {
		return JSONFactoryUtil.createJSONArray();
	}

	protected long getClassPK() {
		return 0;
	}

	protected DDMTemplate getDDMTemplate() {
		if ((_ddmTemplate != null) || (getDDMTemplateId() <= 0)) {
			return _ddmTemplate;
		}

		_ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			getDDMTemplateId());

		return _ddmTemplate;
	}

	protected long getDDMTemplateId() {
		if (_ddmTemplateId != null) {
			return _ddmTemplateId;
		}

		_ddmTemplateId = ParamUtil.getLong(
			_httpServletRequest, "ddmTemplateId");

		return _ddmTemplateId;
	}

	protected String getDefaultScript() {
		return "<#-- Empty script-->";
	}

	protected String getLanguage() {
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

	protected String[] getTemplateLanguageTypes() {
		return new String[] {TemplateConstants.LANG_TYPE_FTL};
	}

	private JSONObject _getAutocompleteJSONObject() throws Exception {
		return JSONFactoryUtil.createJSONObject();
	}

	private String _getEditorMode() {
		if (Objects.equals(getLanguage(), "ftl")) {
			return "ftl";
		}

		return "velocity";
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
			script = getDefaultScript();
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

	private DDMTemplate _ddmTemplate;
	private Long _ddmTemplateId;
	private final HttpServletRequest _httpServletRequest;
	private String _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _script;
	private final ThemeDisplay _themeDisplay;

}