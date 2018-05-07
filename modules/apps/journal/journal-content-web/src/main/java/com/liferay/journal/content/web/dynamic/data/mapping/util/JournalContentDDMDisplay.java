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

package com.liferay.journal.content.web.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "javax.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT,
	service = DDMDisplay.class
)
public class JournalContentDDMDisplay extends BaseDDMDisplay {

	@Override
	public String getAvailableFields() {
		return _ddmDisplay.getAvailableFields();
	}

	@Override
	public String getConfirmSelectStructureMessage(Locale locale) {
		return _ddmDisplay.getConfirmSelectTemplateMessage(locale);
	}

	@Override
	public String getConfirmSelectTemplateMessage(Locale locale) {
		return _ddmDisplay.getConfirmSelectTemplateMessage(locale);
	}

	@Override
	public String getEditStructureDefaultValuesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DDMStructure structure, String redirectURL)
		throws Exception {

		return _ddmDisplay.getEditStructureDefaultValuesURL(
			liferayPortletRequest, liferayPortletResponse, structure,
			redirectURL);
	}

	@Override
	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId, String portletResource)
		throws Exception {

		return _ddmDisplay.getEditTemplateBackURL(
			liferayPortletRequest, liferayPortletResponse, classNameId, classPK,
			resourceClassNameId, portletResource);
	}

	@Override
	public String getPortletId() {
		return JournalContentPortletKeys.JOURNAL_CONTENT;
	}

	@Override
	public String getStorageType() {
		return _ddmDisplay.getStorageType();
	}

	@Override
	public String getStructureType() {
		return _ddmDisplay.getStructureType();
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		return _ddmDisplay.getTemplateHandlerClassNameId(template, classNameId);
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return _ddmDisplay.getTemplateLanguageTypes();
	}

	@Override
	public String getTemplateType() {
		return _ddmDisplay.getTemplateType();
	}

	@Override
	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception {

		return _ddmDisplay.getViewTemplatesBackURL(
			liferayPortletRequest, liferayPortletResponse, classPK);
	}

	@Override
	public Set<String> getViewTemplatesExcludedColumnNames() {
		return _ddmDisplay.getViewTemplatesExcludedColumnNames();
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return _ddmDisplay.isShowBackURLInTitleBar();
	}

	@Override
	public boolean isShowConfirmSelectStructure() {
		return _ddmDisplay.isShowConfirmSelectStructure();
	}

	@Override
	public boolean isShowConfirmSelectTemplate() {
		return false;
	}

	@Override
	public boolean isShowStructureSelector() {
		return _ddmDisplay.isShowStructureSelector();
	}

	@Reference(
		target = "(javax.portlet.name=" + JournalPortletKeys.JOURNAL + ")"
	)
	private DDMDisplay _ddmDisplay;

}