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

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/export_style_book_entries"
	},
	service = MVCResourceCommand.class
)
public class ExportStyleBookEntriesMVCResourceCommand
	implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		long[] exportStyleBookEntryIds = null;

		long styleBookEntryId = ParamUtil.getLong(
			resourceRequest, "styleBookEntryId");

		if (styleBookEntryId > 0) {
			exportStyleBookEntryIds = new long[] {styleBookEntryId};
		}
		else {
			exportStyleBookEntryIds = ParamUtil.getLongValues(
				resourceRequest, "rowIds");
		}

		try {
			File file = _exportStyleBookEntries(exportStyleBookEntryIds);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				"style-book-entries-" + Time.getTimestamp() + ".zip",
				new FileInputStream(file), ContentTypes.APPLICATION_ZIP);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	private File _exportStyleBookEntries(long[] exportStyleBookEntryIds)
		throws Exception {

		List<StyleBookEntry> styleBookEntries = new ArrayList<>();

		if (ArrayUtil.isNotEmpty(exportStyleBookEntryIds)) {
			for (long exportStyleBookEntryId : exportStyleBookEntryIds) {
				StyleBookEntry styleBookEntry =
					_styleBookEntryLocalService.fetchStyleBookEntry(
						exportStyleBookEntryId);

				styleBookEntries.add(styleBookEntry);
			}
		}

		return _styleBookEntryZipProcessor.exportStyleBookEntries(
			styleBookEntries);
	}

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private StyleBookEntryZipProcessor _styleBookEntryZipProcessor;

}