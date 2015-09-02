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

package com.liferay.document.library.ddm;

import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMPermissionHandler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	property = {"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY},
	service = DDMDisplay.class
)
public class DLDDMDisplay extends BaseDDMDisplay {

	@Override
	public DDMPermissionHandler getDDMPermissionHandler() {
		return _ddmPermissionHandler;
	}

	@Override
	public String getPortletId() {
		return PortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.toString();
	}

	@Override
	public String getStructureName(Locale locale) {
		return LanguageUtil.get(locale, "metadata-set");
	}

	@Override
	public String getStructureType() {
		return DLFileEntryMetadata.class.getName();
	}

	@Override
	public boolean isVersioningEnabled() {
		return false;
	}

	private final DDMPermissionHandler _ddmPermissionHandler =
		new DLDDMPermissionHandler();

}