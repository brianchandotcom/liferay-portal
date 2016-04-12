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

package com.liferay.document.library.web.dynamic.data.mapping.util;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 * @author Roberto Díaz
 */
@Component(
	property = {"javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY},
	service = DDMDisplay.class
)
public class DLDDMDisplay extends BaseDDMDisplay {

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
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "metadata-set");
	}

	@Override
	public String getStructureType() {
		return DLFileEntryMetadata.class.getName();
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "metadata-sets");
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

}