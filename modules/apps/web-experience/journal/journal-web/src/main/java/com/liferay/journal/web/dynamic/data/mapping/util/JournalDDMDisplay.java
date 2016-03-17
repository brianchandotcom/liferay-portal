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

package com.liferay.journal.web.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMNavigationHelper;
import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = DDMDisplay.class
)
public class JournalDDMDisplay extends BaseDDMDisplay {

	@Override
	public String getAvailableFields() {
		return "Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE";
	}

	public String getConfirmSelectStructureMessage(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		String key =
			"selecting-a-new-structure-changes-the-available-input-fields-" +
				"and-available-templates";

		return LanguageUtil.get(resourceBundle, key);
	}

	public String getConfirmSelectTemplateMessage(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(
			resourceBundle,
			"selecting-a-new-template-deletes-all-unsaved-content");
	}

	@Override
	public String getEditStructureDefaultValuesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DDMStructure structure, String redirectURL)
		throws Exception {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_article.jsp");
		portletURL.setParameter("redirect", redirectURL);
		portletURL.setParameter(
			"groupId", String.valueOf(structure.getGroupId()));
		portletURL.setParameter(
			"classNameId",
			String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
		portletURL.setParameter(
			"classPK", String.valueOf(structure.getStructureId()));
		portletURL.setParameter("ddmStructureKey", structure.getStructureKey());

		return portletURL.toString();
	}

	@Override
	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId, String portletResource)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnEditTemplate(
				liferayPortletRequest)) {

			return StringPool.BLANK;
		}

		if (ddmNavigationHelper.isNavigationStartsOnSelectTemplate(
				liferayPortletRequest)) {

			return ParamUtil.getString(liferayPortletRequest, "redirect");
		}

		if (ddmNavigationHelper.isNavigationStartsOnViewTemplates(
				liferayPortletRequest)) {

			return getViewTemplatesURL(
				liferayPortletRequest, liferayPortletResponse, classNameId, 0,
				resourceClassNameId);
		}

		return getViewTemplatesURL(
			liferayPortletRequest, liferayPortletResponse, classNameId, classPK,
			resourceClassNameId);
	}

	@Override
	public String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	@Override
	public String getStorageType() {
		return JournalServiceConfigurationValues.JOURNAL_ARTICLE_STORAGE_TYPE;
	}

	@Override
	public String getStructureType() {
		return JournalArticle.class.getName();
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		return PortalUtil.getClassNameId(JournalArticle.class);
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return _templateLanguageTypes;
	}

	@Override
	public String getTemplateType() {
		return DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY;
	}

	@Override
	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnEditStructure(
				liferayPortletRequest)) {

			return StringPool.BLANK;
		}

		if (ddmNavigationHelper.isNavigationStartsOnViewTemplates(
				liferayPortletRequest)) {

			return ParamUtil.getString(liferayPortletRequest, "backURL");
		}

		return super.getViewTemplatesBackURL(
			liferayPortletRequest, liferayPortletResponse, classPK);
	}

	@Override
	public Set<String> getViewTemplatesExcludedColumnNames() {
		return _viewTemplateExcludedColumnNames;
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	@Override
	public boolean isShowConfirmSelectStructure() {
		return true;
	}

	@Override
	public boolean isShowConfirmSelectTemplate() {
		return true;
	}

	@Override
	public boolean isShowStructureSelector() {
		return true;
	}

	private static final Set<String> _templateLanguageTypes = SetUtil.fromArray(
		new String[] {
			TemplateConstants.LANG_TYPE_FTL, TemplateConstants.LANG_TYPE_VM,
			TemplateConstants.LANG_TYPE_XSL
		});
	private static final Set<String> _viewTemplateExcludedColumnNames =
		SetUtil.fromArray(new String[] {"mode"});

}