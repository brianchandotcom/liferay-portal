/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eduardo García
 */
@ProviderType
public interface DDMDisplay {

	public String getAvailableFields();

	public String getConfirmSelectStructureMessage(Locale locale);

	public String getConfirmSelectTemplateMessage(Locale locale);

	public DDMNavigationHelper getDDMNavigationHelper();

	public DDMDisplayTabItem getDefaultTabItem();

	public String getDefaultTemplateLanguage();

	public String getDescription(Locale locale);

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

	public List<DDMDisplayTabItem> getTabItems();

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

	public String getViewTemplatesTitle(DDMStructure structure, Locale locale);

	public boolean isEnableSelectStructureLink(
		DDMStructure structure, long classPK);

	public boolean isShowAddButton(Group scopeGroup);

	public boolean isShowBackURLInTitleBar();

	public boolean isShowConfirmSelectStructure();

	public boolean isShowConfirmSelectTemplate();

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public boolean isShowStructureSelector();

	public boolean isVersioningEnabled();

}