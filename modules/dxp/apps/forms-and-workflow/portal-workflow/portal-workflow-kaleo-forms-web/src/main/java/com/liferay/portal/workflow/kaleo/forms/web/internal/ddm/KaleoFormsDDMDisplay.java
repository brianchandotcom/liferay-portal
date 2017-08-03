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

package com.liferay.portal.workflow.kaleo.forms.web.internal.ddm;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMNavigationHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {"javax.portlet.name=" + KaleoFormsPortletKeys.KALEO_FORMS_ADMIN},
	service = DDMDisplay.class
)
public class KaleoFormsDDMDisplay extends BaseDDMDisplay {

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

		return super.getEditTemplateBackURL(
			liferayPortletRequest, liferayPortletResponse, classNameId, classPK,
			resourceClassNameId, portletResource);
	}

	@Override
	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale) {

		ResourceBundle resourceBundle = getResourceBundle(locale);

		if ((structure != null) && (template == null)) {
			return LanguageUtil.format(
				resourceBundle, "new-form-for-field-set-x",
				structure.getName(locale), false);
		}

		return super.getEditTemplateTitle(structure, template, locale);
	}

	@Override
	public String getPortletId() {
		return KaleoFormsPortletKeys.KALEO_FORMS_ADMIN;
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.toString();
	}

	@Override
	public String getStructureName(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "field-set");
	}

	@Override
	public String getStructureType() {
		return KaleoProcess.class.getName();
	}

	@Override
	public String getTemplateType() {
		return DDMTemplateConstants.TEMPLATE_TYPE_FORM;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "field-sets");
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

		return super.getViewTemplatesBackURL(
			liferayPortletRequest, liferayPortletResponse, classPK);
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, boolean search,
		Locale locale) {

		if (structure != null) {
			ResourceBundle resourceBundle = getResourceBundle(locale);

			return LanguageUtil.format(
				resourceBundle, "forms-for-field-set-x",
				structure.getName(locale), false);
		}

		return getDefaultViewTemplateTitle(locale);
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	@Override
	protected String getDefaultViewTemplateTitle(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "forms");
	}

}