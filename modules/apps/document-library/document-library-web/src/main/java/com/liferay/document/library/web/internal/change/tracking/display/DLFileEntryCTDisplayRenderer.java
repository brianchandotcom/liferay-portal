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

package com.liferay.document.library.web.internal.change.tracking.display;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.InputStream;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DLFileEntryCTDisplayRenderer
	implements CTDisplayRenderer<DLFileEntry> {

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

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0,
			0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/document_library/edit_file_entry");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"fileEntryId", String.valueOf(dlFileEntry.getFileEntryId()));

		return portletURL.toString();
	}

	@Override
	public Class<DLFileEntry> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileEntry dlFileEntry) {
		return dlFileEntry.getTitle();
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, DLFileEntryCTDisplayRenderer.class);

		return _language.get(
			resourceBundle,
			"model.resource.com.liferay.document.library.kernel.model." +
				"DLFileEntry");
	}

	@Override
	public void render(DisplayContext<DLFileEntry> displayContext)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/document_library/ct_display/render_file_version.jsp");

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		httpServletRequest.setAttribute(
			DLWebKeys.CHANGE_TRACKING_DISPLAY_CONTEXT, displayContext);

		DLFileEntry dlFileEntry = displayContext.getModel();

		httpServletRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY, dlFileEntry);
		httpServletRequest.setAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_VERSION,
			dlFileEntry.getFileVersion());

		requestDispatcher.include(
			httpServletRequest, displayContext.getHttpServletResponse());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.web)"
	)
	private ServletContext _servletContext;

}