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

package com.liferay.portlet.dynamicdatamapping.util.bundle.ddmdisplayregistryutil;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestDDMDisplay implements DDMDisplay {

	@Override
	public String getAvailableFields() {
		return null;
	}

	@Override
	public DDMPermissionHandler getDDMPermissionHandler() {
		return null;
	}

	@Override
	public String getEditStructureDefaultValuesURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, DDMStructure structure,
		String redirectURL, String backURL) {

		return null;
	}

	@Override
	public String getEditTemplateBackURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classNameId,
		long classPK, String portletResource) {

		return null;
	}

	@Override
	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale) {

		return null;
	}

	@Override
	public String getEditTemplateTitle(long classNameId, Locale locale) {
		return null;
	}

	@Override
	public String getPortletId() {
		return TestDDMDisplay.class.getName();
	}

	@Override
	public String getStorageType() {
		return null;
	}

	@Override
	public String getStructureName(Locale locale) {
		return null;
	}

	@Override
	public String getStructureType() {
		return null;
	}

	@Override
	public long[] getTemplateClassNameIds(long classNameId) {
		return null;
	}

	@Override
	public long[] getTemplateClassPKs(
		long companyId, long classNameId, long classPK) {

		return null;
	}

	@Override
	public long[] getTemplateGroupIds(
		ThemeDisplay themeDisplay, boolean includeAncestorTemplates) {

		return null;
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		return 0;
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return null;
	}

	@Override
	public String getTemplateMode() {
		return null;
	}

	@Override
	public String getTemplateType() {
		return null;
	}

	@Override
	public String getTemplateType(DDMTemplate template, Locale locale) {
		return null;
	}

	@Override
	public String getViewTemplatesBackURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classPK) {

		return null;
	}

	@Override
	public Set<String> getViewTemplatesExcludedColumnNames() {
		return null;
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, boolean search,
		Locale locale) {

		return null;
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, Locale locale) {

		return null;
	}

	@Override
	public String getViewTemplatesTitle(DDMStructure structure, Locale locale) {
		return null;
	}

	@Override
	public boolean isShowAddStructureButton() {
		return false;
	}

	@Override
	public boolean isShowStructureSelector() {
		return false;
	}

}