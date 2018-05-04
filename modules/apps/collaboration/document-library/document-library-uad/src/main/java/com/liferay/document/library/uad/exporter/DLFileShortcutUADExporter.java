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

package com.liferay.document.library.uad.exporter;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.uad.constants.DLUADConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.user.associated.data.exporter.DynamicQueryUADExporter;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = "model.class.name=" + DLUADConstants.CLASS_NAME_DL_FILE_SHORTCUT,
	service = UADExporter.class
)
public class DLFileShortcutUADExporter
	extends DynamicQueryUADExporter<DLFileShortcut> {

	public String getApplicationName() {
		return DLUADConstants.APPLICATION_NAME;
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _dlFileShortcutLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return DLUADConstants.USER_ID_FIELD_NAMES_DL_FILE_SHORTCUT;
	}

	@Reference
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

}