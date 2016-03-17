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

package com.liferay.dynamic.data.mapping.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Set;

/**
 * @author Eduardo Garcia
 */
@ProviderType
public interface DDMDisplay {

	public String getAvailableFields();

	public String getConfirmSelectStructureMessage(Locale locale);

	public String getConfirmSelectTemplateMessage(Locale locale);

	public DDMNavigationHelper getDDMNavigationHelper();

	public String getDefaultTemplateLanguage();

	public String getEditStructureDefaultValuesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DDMStructure structure, String redirectURL)
		throws Exception;

	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId, String portletResource)
		throws Exception;

	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale);

	public String getEditTemplateTitle(long classNameId, Locale locale);

	public String getPortletId();

	public String getStorageType();

	public String getStructureName(Locale locale);

	public String getStructureType();

	public long[] getTemplateClassNameIds(long classNameId);

	public long[] getTemplateClassPKs(
			long companyId, long classNameId, long classPK)
		throws Exception;

	public long[] getTemplateGroupIds(
			ThemeDisplay themeDisplay, boolean includeAncestorTemplates)
		throws Exception;

	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId);

	public Set<String> getTemplateLanguageTypes();

	public String getTemplateMode();

	public String getTemplateType();

	public String getTemplateType(DDMTemplate template, Locale locale);

	public String getTitle(Locale locale);

	public String getViewStructuresBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception;

	public Set<String> getViewTemplatesExcludedColumnNames();

	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, boolean search,
		Locale locale);

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, Locale locale);

	public String getViewTemplatesTitle(DDMStructure structure, Locale locale);

	public boolean isShowAddStructureButton();

	public boolean isShowBackURLInTitleBar();

	public boolean isShowConfirmSelectStructure();

	public boolean isShowConfirmSelectTemplate();

	public boolean isShowStructureSelector();

	public boolean isVersioningEnabled();

}