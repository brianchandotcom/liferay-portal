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

package com.liferay.data.engine.taglib.servlet.taglib;

import com.liferay.data.engine.taglib.internal.servlet.taglib.util.DataLayoutTaglibUtil;
import com.liferay.data.engine.taglib.servlet.taglib.base.BaseDataLayoutBuilderTag;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class DataLayoutBuilderTag extends BaseDataLayoutBuilderTag {

	@Override
	public int doStartTag() throws JspException {
		int result = super.doStartTag();

		try {
			HttpServletRequest httpServletRequest = getRequest();

			setNamespacedAttribute(
				httpServletRequest, "dataLayoutBuilderModule",
				DataLayoutTaglibUtil.resolveModule(
					"data-engine-taglib/data_layout_builder/js" +
						"/DataLayoutBuilder.es"));

			if (Validator.isNotNull(getDataDefinitionId()) &&
				Validator.isNull(getDataLayoutId())) {

				setDataLayoutId(
					DataLayoutTaglibUtil.getDefaultDataLayoutId(
						getDataDefinitionId(), httpServletRequest));
			}

			setNamespacedAttribute(
				httpServletRequest, "fieldTypes",
				DataLayoutTaglibUtil.getFieldTypesJSONArray(
					httpServletRequest, getScopes()));
			setNamespacedAttribute(
				httpServletRequest, "fieldTypesModules",
				DataLayoutTaglibUtil.resolveFieldTypesModules());
			setNamespacedAttribute(
				httpServletRequest, "sidebarPanels", _getSidebarPanels());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return result;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		Set<Locale> availableLocales = DataLayoutTaglibUtil.getAvailableLocales(
			getDataDefinitionId(), getDataLayoutId(), httpServletRequest);

		setNamespacedAttribute(
			httpServletRequest, "availableLanguageIds",
			_getLanguageIds(availableLocales));
		setNamespacedAttribute(
			httpServletRequest, "availableLocales",
			availableLocales.toArray(new Locale[0]));

		HttpServletRequest tagHttpServletRequest = getRequest();

		setNamespacedAttribute(
			httpServletRequest, "config",
			DataLayoutTaglibUtil.getDataLayoutConfigJSONObject(
				getContentType(), tagHttpServletRequest.getLocale()));

		setNamespacedAttribute(
			httpServletRequest, "contentTypeConfig",
			DataLayoutTaglibUtil.getContentTypeConfigJSONObject(
				getContentType()));
		setNamespacedAttribute(
			httpServletRequest, "dataLayout",
			DataLayoutTaglibUtil.getDataLayoutJSONObject(
				availableLocales, getDataDefinitionId(), getDataLayoutId(),
				httpServletRequest,
				(HttpServletResponse)pageContext.getResponse()));
		setNamespacedAttribute(
			httpServletRequest, "defaultLanguageId", _getDefaultLanguageId());
		setNamespacedAttribute(httpServletRequest, "module", _getModule());
		setNamespacedAttribute(
			httpServletRequest, "moduleServletContext",
			_getModuleServletContext());
	}

	private String _getDefaultLanguageId() {
		Long dataDefinitionId = getDataDefinitionId();

		String languageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		if ((dataDefinitionId == null) || (dataDefinitionId <= 0)) {
			return languageId;
		}

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructure(dataDefinitionId);

		if (ddmStructure == null) {
			return languageId;
		}

		return ddmStructure.getDefaultLanguageId();
	}

	private String[] _getLanguageIds(Set<Locale> locales) {
		Stream<Locale> stream = locales.stream();

		return stream.map(
			LanguageUtil::getLanguageId
		).toArray(
			String[]::new
		);
	}

	private String _getModule() {
		if (Validator.isBlank(getModule())) {
			return "data_layout_builder/new-js/App";
		}

		return getModule();
	}

	private ServletContext _getModuleServletContext() {
		if (getModuleServletContext() == null) {
			return getServletContext();
		}

		return getModuleServletContext();
	}

	private String _getPluginEntryPoint(String value) {
		return DataLayoutTaglibUtil.resolveModule(
			"data-engine-taglib/data_layout_builder/new-js/plugins/" + value +
				"/index");
	}

	private Map<String, Object> _getSidebarPanels() {
		HttpServletRequest httpServletRequest = getRequest();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", httpServletRequest.getLocale(), getClass());

		Map<String, Object> sidebarPanels =
			LinkedHashMapBuilder.<String, Object>put(
				"fields",
				HashMapBuilder.<String, Object>put(
					"icon", "forms"
				).put(
					"isLink", false
				).put(
					"label", LanguageUtil.get(resourceBundle, "builder")
				).put(
					"pluginEntryPoint", _getPluginEntryPoint("fields-sidebar")
				).put(
					"sidebarPanelId", "fields"
				).build()
			).build();

		JSONObject dataLayoutConfigJSONObject =
			DataLayoutTaglibUtil.getDataLayoutConfigJSONObject(
				getContentType(), httpServletRequest.getLocale());

		if (dataLayoutConfigJSONObject.getBoolean("allowRules")) {
			sidebarPanels.put(
				"rules",
				HashMapBuilder.<String, Object>put(
					"icon", "rules"
				).put(
					"isLink", false
				).put(
					"label", LanguageUtil.get(resourceBundle, "rules")
				).put(
					"pluginEntryPoint", _getPluginEntryPoint("rules-sidebar")
				).put(
					"sidebarPanelId", "rules"
				).build());
		}

		List<Map<String, Object>> additionalPanels = getAdditionalPanels();

		if (ListUtil.isEmpty(additionalPanels)) {
			return sidebarPanels;
		}

		for (Map<String, Object> additionalPanel : additionalPanels) {
			sidebarPanels.put(
				(String)additionalPanel.get("sidebarPanelId"), additionalPanel);
		}

		return sidebarPanels;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataLayoutBuilderTag.class);

}