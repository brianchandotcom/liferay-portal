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
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.frontend.taglib.clay.servlet.taglib.LinkTag;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.InputStream;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileVersion",
	service = CTDisplayRenderer.class
)
public class DLFileVersionCTDisplayRenderer
	extends BaseCTDisplayRenderer<DLFileVersion> {

	public static void buildDisplay(
		DisplayBuilder<?> displayBuilder, DLFileVersion dlFileVersion) {

		displayBuilder.display(
			"title", dlFileVersion.getTitle()
		).display(
			"description", dlFileVersion.getDescription()
		).display(
			"file-name", dlFileVersion.getFileName()
		).display(
			"extension", dlFileVersion.getExtension()
		).display(
			"mime-type", dlFileVersion.getMimeType()
		).display(
			"version", dlFileVersion.getVersion()
		).display(
			"size", dlFileVersion.getSize()
		).display(
			"download", getDownloadLink(displayBuilder, dlFileVersion), false
		);
	}

	@Override
	public InputStream getDownloadInputStream(
			DLFileVersion dlFileVersion, String version)
		throws PortalException {

		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore();

		DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

		return store.getFileAsStream(
			dlFileVersion.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), dlFileVersion.getVersion());
	}

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest, DLFileVersion dlFileVersion) {

		return null;
	}

	@Override
	public Class<DLFileVersion> getModelClass() {
		return DLFileVersion.class;
	}

	@Override
	public String getTitle(Locale locale, DLFileVersion dlFileVersion) {
		return dlFileVersion.getTitle();
	}

	@Override
	public boolean isHideable(DLFileVersion dlFileVersion) {
		return true;
	}

	protected static String getDownloadLink(
		DisplayBuilder<?> displayBuilder, DLFileVersion dlFileVersion) {

		DisplayContext<?> displayContext = displayBuilder.getDisplayContext();

		LinkTag linkTag = new LinkTag();

		linkTag.setDisplayType("primary");
		linkTag.setHref(
			displayContext.getDownloadURL(
				dlFileVersion.getVersion(), dlFileVersion.getSize(),
				dlFileVersion.getFileName()));
		linkTag.setIcon("download");
		linkTag.setLabel("download");
		linkTag.setSmall(true);
		linkTag.setType("button");

		try {
			return linkTag.doTagAsString(
				displayContext.getHttpServletRequest(),
				displayContext.getHttpServletResponse());
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DLFileVersion> displayBuilder) {
		buildDisplay(displayBuilder, displayBuilder.getModel());
	}

}