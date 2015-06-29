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

package com.liferay.dynamic.data.lists.web.ddm;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.web.configuration.DDLWebConfigurationValues;
import com.liferay.dynamic.data.lists.web.constants.DDLPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.BaseDDMDisplay;
import com.liferay.portlet.dynamicdatamapping.util.DDMDisplay;
import com.liferay.portlet.dynamicdatamapping.util.DDMPermissionHandler;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	property = {"javax.portlet.name=" + DDLPortletKeys.DYNAMIC_DATA_LISTS},
	service = DDMDisplay.class
)
public class DDLDDMDisplay extends BaseDDMDisplay {

	@Override
	public DDMPermissionHandler getDDMPermissionHandler() {
		return _ddmPermissionHandler;
	}

	@Override
	public String getPortletId() {
		return DDLPortletKeys.DYNAMIC_DATA_LISTS;
	}

	@Override
	public String getStorageType() {
		return DDLWebConfigurationValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE;
	}

	@Override
	public String getStructureName(Locale locale) {
		return LanguageUtil.get(locale, "data-definition");
	}

	@Override
	public String getStructureType() {
		return DDLRecordSet.class.getName();
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		return PortalUtil.getClassNameId(DDLRecordSet.class);
	}

	private final DDMPermissionHandler _ddmPermissionHandler =
		new DDLDDMPermissionHandler();

}