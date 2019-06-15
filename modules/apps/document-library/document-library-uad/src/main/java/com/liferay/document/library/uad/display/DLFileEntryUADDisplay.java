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

package com.liferay.document.library.uad.display;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = {DLFileEntryUADDisplay.class, UADDisplay.class}
)
public class DLFileEntryUADDisplay extends BaseDLFileEntryUADDisplay {

	@Override
	public String[] getColumnFieldNames() {
		return new String[] {"fileName", "description"};
	}

	@Override
	public String getEditURL(
			DLFileEntry dlFileEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		if (dlFileEntry.isInTrash()) {
			return StringPool.BLANK;
		}

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			portal.getControlPanelPlid(liferayPortletRequest),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"redirect", portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter(
			"fileEntryId", String.valueOf(dlFileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	@Override
	public Map<String, Object> getFieldValues(
		DLFileEntry dlFileEntry, String[] fieldNames, Locale locale) {

		Map<String, Object> fieldValues = super.getFieldValues(
			dlFileEntry, fieldNames, locale);

		List<String> fieldNamesList = Arrays.asList(fieldNames);

		if (fieldNamesList.contains("type")) {
			DLFileEntryType dlFileEntryType =
				dlFileEntryTypeLocalService.fetchDLFileEntryType(
					dlFileEntry.getFileEntryTypeId());

			String typeName = "--";

			if (dlFileEntryType != null) {
				typeName = dlFileEntryType.getName(locale);
			}

			fieldValues.put("type", typeName);
		}

		return fieldValues;
	}

	@Override
	public String getName(DLFileEntry dlFileEntry, Locale locale) {
		return dlFileEntry.getFileName();
	}

	@Override
	public Class<?> getParentContainerClass() {
		return DLFolder.class;
	}

	@Override
	public Serializable getParentContainerId(DLFileEntry dlFileEntry) {
		return dlFileEntry.getFolderId();
	}

	@Override
	public boolean isUserOwned(DLFileEntry dlFileEntry, long userId) {
		if (dlFileEntry.getUserId() == userId) {
			return true;
		}

		return false;
	}

	@Reference
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;

	@Reference
	protected Portal portal;

}