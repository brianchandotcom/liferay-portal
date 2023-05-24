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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditDDMStructuresDisplayContext {

	public JournalEditDDMStructuresDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_journalWebConfiguration =
			(JournalWebConfiguration)httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public List<Map<String, Object>> getAdditionalPanels(
		String npmResolvedPackageName) {

		return ListUtil.fromArray(
			HashMapBuilder.<String, Object>put(
				"icon", "cog"
			).put(
				"label", LanguageUtil.get(_httpServletRequest, "properties")
			).put(
				"pluginEntryPoint",
				npmResolvedPackageName + "/js/data_engine/panels/index.es"
			).put(
				"sidebarPanelId", "properties"
			).put(
				"url",
				() -> PortletURLBuilder.createRenderURL(
					_liferayPortletResponse
				).setMVCPath(
					"/data_engine/basic_info.jsp"
				).setParameter(
					"ddmStructureId", getDDMStructureId()
				).setWindowState(
					LiferayWindowState.EXCLUSIVE
				).buildString()
			).build());
	}

	public List<String> getAvailableLanguageIds() {
		if (_ddmStructure == null) {
			return StringUtil.asList(getDefaultLanguageId());
		}

		return Arrays.asList(_ddmStructure.getAvailableLanguageIds());
	}

	public Map<String, Object> getDataEngineLayoutBuilderHandlerContext() {
		return HashMapBuilder.<String, Object>put(
			"defaultLanguageId", getDefaultLanguageId()
		).build();
	}

	public DDMForm getDDMForm() {
		try {
			return DDMUtil.getDDMForm(getScript());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			getDDMStructureId());

		return _ddmStructure;
	}

	public long getDDMStructureId() {
		if (_ddmStructureId != null) {
			return _ddmStructureId;
		}

		_ddmStructureId = ParamUtil.getLong(
			_httpServletRequest, "ddmStructureId");

		return _ddmStructureId;
	}

	public String getDefaultLanguageId() {
		DDMForm ddmForm = getDDMForm();

		if ((ddmForm == null) || (ddmForm.getDefaultLocale() == null)) {
			return LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());
		}

		return LocaleUtil.toLanguageId(ddmForm.getDefaultLocale());
	}

	public Map<String, Object> getLocaleChangedHandlerContext() {
		return HashMapBuilder.<String, Object>put(
			"contentTitle", "name"
		).put(
			"defaultLanguageId", getDefaultLanguageId()
		).build();
	}

	public String getScript() throws PortalException {
		if (_script != null) {
			return _script;
		}

		_script = ParamUtil.getString(_httpServletRequest, "dataDefinition");

		if (Validator.isNotNull(_script)) {
			return _script;
		}

		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure != null) {
			DDMStructureVersion ddmStructureVersion =
				ddmStructure.getLatestStructureVersion();

			_script = ddmStructureVersion.getDefinition();
		}

		return _script;
	}

	public String getStorageType() {
		String storageType = StorageType.DEFAULT.getValue();

		try {
			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			storageType =
				journalServiceConfiguration.journalArticleStorageType();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return storageType;
	}

	public boolean isStructureFieldIndexableEnable() {
		return _journalWebConfiguration.structureFieldIndexableEnable();
	}

	public boolean isStructureKeyInputDisabled() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-184255")) {
			return _autogenerateDDMStructureKey();
		}

		if (!_autogenerateDDMStructureKey() && (getDDMStructure() == null)) {
			return false;
		}

		return true;
	}

	public boolean showStructureKeyInput() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-184255") ||
			!_autogenerateDDMStructureKey()) {

			return true;
		}

		return false;
	}

	private boolean _autogenerateDDMStructureKey() {
		return _journalWebConfiguration.autogenerateDDMStructureKey();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalEditDDMStructuresDisplayContext.class);

	private DDMStructure _ddmStructure;
	private Long _ddmStructureId;
	private final HttpServletRequest _httpServletRequest;
	private final JournalWebConfiguration _journalWebConfiguration;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _script;

}