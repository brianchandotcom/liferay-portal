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

package com.liferay.document.library.internal.exportimport.data.handler;

import com.liferay.document.library.exportimport.data.handler.DLPluggableContentDataHandler;
import com.liferay.document.library.kernel.util.DLProcessorRegistry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileEntry",
	service = DLPluggableContentDataHandler.class
)
public class DLProcessorPluggableContentDataHandler
	implements DLPluggableContentDataHandler<FileEntry> {

	@Override
	public void exportContent(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry)
		throws Exception {

		if (_isEnabled(portletDataContext)) {
			_dlProcessorRegistry.exportGeneratedFiles(
				portletDataContext, fileEntry, fileEntryElement);
		}
	}

	@Override
	public void importContent(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry, FileEntry importedFileEntry)
		throws Exception {

		if (_isEnabled(portletDataContext)) {
			_dlProcessorRegistry.importGeneratedFiles(
				portletDataContext, fileEntry, importedFileEntry,
				fileEntryElement);
		}
	}

	private boolean _isEnabled(PortletDataContext portletDataContext) {
		return portletDataContext.getBooleanParameter(
			_DL_PORTLET_DATA_HANDLER_NAMESPACE, "previews-and-thumbnails");
	}

	/**
	 * @see com.liferay.document.library.web.internal.lar.DLPortletDataHandler#NAMESPACE
	 */
	private static final String _DL_PORTLET_DATA_HANDLER_NAMESPACE =
		"document_library";

	@Reference
	private DLProcessorRegistry _dlProcessorRegistry;

}