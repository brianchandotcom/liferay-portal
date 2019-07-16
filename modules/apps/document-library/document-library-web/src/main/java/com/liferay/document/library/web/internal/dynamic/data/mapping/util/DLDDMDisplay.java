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

package com.liferay.document.library.web.internal.dynamic.data.mapping.util;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 * @author Roberto Díaz
 */
@Component(
	property = "javax.portlet.name=" + PortletKeys.DOCUMENT_LIBRARY,
	service = DDMDisplay.class
)
public class DLDDMDisplay extends BaseDDMDisplay {

	@Override
	public DDMDisplayTabItem getDefaultTabItem() {
		return _defaultTabItem;
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale),
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD
			).concat(
				PortletKeys.DOCUMENT_LIBRARY_ADMIN
			));
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
		return LanguageUtil.get(getResourceBundle(locale), "metadata-set");
	}

	@Override
	public String getStructureType() {
		return DLFileEntryMetadata.class.getName();
	}

	@Override
	public List<DDMDisplayTabItem> getTabItems() {
		return Arrays.asList(
			_documentsAndMediaDDMDisplayTabItem,
			_documentTypesDDMDisplayTabItem, getDefaultTabItem());
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale), "documents-and-media");
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	private final DDMDisplayTabItem _defaultTabItem =
		(liferayPortletRequest, liferayPortletResponse) -> {
			ResourceBundle resourceBundle = getResourceBundle(
				liferayPortletRequest.getLocale());

			return LanguageUtil.get(resourceBundle, "metadata-sets");
		};

	@Reference
	private DocumentsAndMediaDDMDisplayTabItem
		_documentsAndMediaDDMDisplayTabItem;

	@Reference
	private DocumentTypesDDMDisplayTabItem _documentTypesDDMDisplayTabItem;

}