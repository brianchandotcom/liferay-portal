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

package com.liferay.document.library.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.trash.kernel.util.TrashUtil;

import java.io.InputStream;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DLFileEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<DLFileEntry> {

	@Override
	public InputStream getDownloadInputStream(
			DLFileEntry dlFileEntry, String version)
		throws PortalException {

		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore();

		return store.getFileAsStream(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), version);
	}

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, DLFileEntry dlFileEntry)
		throws Exception {

		Group group = _groupLocalService.getGroup(dlFileEntry.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
				0, 0, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/document_library/edit_file_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"fileEntryId", dlFileEntry.getFileEntryId()
		).buildString();
	}

	@Override
	public Class<DLFileEntry> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileEntry dlFileEntry) {
		if (dlFileEntry.isInTrash()) {
			return TrashUtil.getOriginalTitle(dlFileEntry.getTitle());
		}

		return dlFileEntry.getTitle();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DLFileEntry> displayBuilder)
		throws PortalException {

		DLFileEntry dlFileEntry = displayBuilder.getModel();

		DLFileVersionCTDisplayRenderer.buildDisplay(
			displayBuilder, dlFileEntry.getFileVersion());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}