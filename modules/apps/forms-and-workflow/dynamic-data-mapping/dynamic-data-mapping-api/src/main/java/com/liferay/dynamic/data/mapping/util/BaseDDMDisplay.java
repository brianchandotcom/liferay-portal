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
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Eduardo Garcia
 */
@ProviderType
public abstract class BaseDDMDisplay implements DDMDisplay {

	@Override
	public String getAvailableFields() {
		return "Liferay.FormBuilder.AVAILABLE_FIELDS.DDM_STRUCTURE";
	}

	public String getConfirmSelectStructureMessage(Locale locale) {
		return StringPool.BLANK;
	}

	public String getConfirmSelectTemplateMessage(Locale locale) {
		return StringPool.BLANK;
	}

	public DDMNavigationHelper getDDMNavigationHelper() {
		return new DDMNavigationHelperImpl();
	}

	@Override
	public String getDefaultTemplateLanguage() {
		return TemplateConstants.LANG_TYPE_FTL;
	}

	@Override
	public String getEditStructureDefaultValuesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DDMStructure structure, String redirectURL)
		throws Exception {

		return null;
	}

	@Override
	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId, String portletResource)
		throws Exception {

		return getViewTemplatesURL(
			liferayPortletRequest, liferayPortletResponse, classNameId, classPK,
			resourceClassNameId);
	}

	@Override
	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale) {

		if ((structure != null) && (template != null)) {
			return StringUtil.appendParentheticalSuffix(
				template.getName(locale), structure.getName(locale));
		}
		else if (structure != null) {
			ResourceBundle resourceBundle = getResourceBundle(locale);

			return LanguageUtil.format(
				resourceBundle, "new-template-for-structure-x",
				structure.getName(locale), false);
		}
		else if (template != null) {
			return template.getName(locale);
		}

		return getDefaultEditTemplateTitle(locale);
	}

	@Override
	public String getEditTemplateTitle(long classNameId, Locale locale) {
		if (classNameId > 0) {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

			if (templateHandler != null) {
				return LanguageUtil.get(locale, "new") + StringPool.SPACE +
					templateHandler.getName(locale);
			}
		}

		return getDefaultEditTemplateTitle(locale);
	}

	@Override
	public String getStorageType() {
		return StringPool.BLANK;
	}

	@Override
	public String getStructureName(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "structure");
	}

	@Override
	public String getStructureType() {
		return StringPool.BLANK;
	}

	@Override
	public long[] getTemplateClassNameIds(long classNameId) {
		if (classNameId > 0) {
			return new long[] {classNameId};
		}

		return TemplateHandlerRegistryUtil.getClassNameIds();
	}

	@Override
	public long[] getTemplateClassPKs(
			long companyId, long classNameId, long classPK)
		throws Exception {

		if (classPK > 0) {
			return new long[] {classPK};
		}

		List<Long> classPKs = new ArrayList<>();

		classPKs.add(0L);

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getClassStructures(
				companyId, PortalUtil.getClassNameId(getStructureType()));

		for (DDMStructure structure : structures) {
			classPKs.add(structure.getPrimaryKey());
		}

		return ArrayUtil.toLongArray(classPKs);
	}

	@Override
	public long[] getTemplateGroupIds(
			ThemeDisplay themeDisplay, boolean includeAncestorTemplates)
		throws Exception {

		if (includeAncestorTemplates) {
			return PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId());
		}

		return new long[] {themeDisplay.getScopeGroupId()};
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		if (template != null) {
			return template.getClassNameId();
		}

		return classNameId;
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return _templateLanguageTypes;
	}

	@Override
	public String getTemplateMode() {
		return StringPool.BLANK;
	}

	@Override
	public String getTemplateType() {
		return StringPool.BLANK;
	}

	@Override
	public String getTemplateType(DDMTemplate template, Locale locale) {
		return LanguageUtil.get(locale, template.getType());
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "structures");
	}

	@Override
	public String getViewStructuresBackURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) throws Exception {

		return ParamUtil.getString(liferayPortletRequest, "backURL");
	}

	@Override
	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnSelectStructure(
				liferayPortletRequest)) {

			return ParamUtil.getString(liferayPortletRequest, "redirect");
		}
		else {
			String portletId = PortletProviderUtil.getPortletId(
				DDMStructure.class.getName(), PortletProvider.Action.VIEW);

			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				liferayPortletRequest, portletId, PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/view.jsp");

			return portletURL.toString();
		}
	}

	@Override
	public Set<String> getViewTemplatesExcludedColumnNames() {
		return _viewTemplateExcludedColumnNames;
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, boolean search,
		Locale locale) {

		if (structure != null) {
			ResourceBundle resourceBundle = getResourceBundle(locale);

			return LanguageUtil.format(
				resourceBundle, "templates-for-structure-x",
				structure.getName(locale), false);
		}

		return getDefaultViewTemplateTitle(locale);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, Locale locale) {

		return getViewTemplatesTitle(structure, controlPanel, false, locale);
	}

	@Override
	public String getViewTemplatesTitle(DDMStructure structure, Locale locale) {
		return getViewTemplatesTitle(structure, false, false, locale);
	}

	@Override
	public boolean isShowAddStructureButton() {
		String portletId = getPortletId();

		String ddmStructurePortletId = PortletProviderUtil.getPortletId(
			DDMStructure.class.getName(), PortletProvider.Action.VIEW);

		if (portletId.equals(ddmStructurePortletId)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return false;
	}

	@Override
	public boolean isShowConfirmSelectStructure() {
		return false;
	}

	@Override
	public boolean isShowConfirmSelectTemplate() {
		return false;
	}

	@Override
	public boolean isShowStructureSelector() {
		return false;
	}

	@Override
	public boolean isVersioningEnabled() {
		return false;
	}

	protected ResourceBundle getBaseDDMDisplayResourceBundle(
		String languageId) {

		Class<?> baseDDMDisplayClazz = BaseDDMDisplay.class;

		return ResourceBundleUtil.getBundle(
			"content.Language", LocaleUtil.fromLanguageId(languageId),
			baseDDMDisplayClazz.getClassLoader());
	}

	protected ResourceBundle getDDMDisplayResourceBundle(String languageId) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		if (resourceBundleLoader == null) {
			return null;
		}

		return resourceBundleLoader.loadResourceBundle(languageId);
	}

	protected String getDefaultEditTemplateTitle(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "new-template");
	}

	protected String getDefaultViewTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "templates");
	}

	protected ResourceBundle getPortalResourceBundle(String languageId) {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		return portalResourceBundleLoader.loadResourceBundle(languageId);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		ResourceBundle ddmDisplayResourceBundle = getDDMDisplayResourceBundle(
			languageId);

		if (ddmDisplayResourceBundle == null) {
			return new AggregateResourceBundle(
				getBaseDDMDisplayResourceBundle(languageId),
				getPortalResourceBundle(languageId));
		}

		return new AggregateResourceBundle(
			ddmDisplayResourceBundle,
			getBaseDDMDisplayResourceBundle(languageId),
			getPortalResourceBundle(languageId));
	}

	protected String getViewTemplatesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId)
		throws Exception {

		String portletId = PortletProviderUtil.getPortletId(
			DDMStructure.class.getName(), PortletProvider.Action.VIEW);

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, portletId, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view_template.jsp");
		portletURL.setParameter("classNameId", String.valueOf(classNameId));
		portletURL.setParameter("classPK", String.valueOf(classPK));
		portletURL.setParameter(
			"resourceClassNameId", String.valueOf(resourceClassNameId));

		return portletURL.toString();
	}

	private static final Set<String> _templateLanguageTypes = SetUtil.fromArray(
		new String[] {
			TemplateConstants.LANG_TYPE_FTL, TemplateConstants.LANG_TYPE_VM
		});
	private static final Set<String> _viewTemplateExcludedColumnNames =
		SetUtil.fromArray(new String[] {"structure"});

}