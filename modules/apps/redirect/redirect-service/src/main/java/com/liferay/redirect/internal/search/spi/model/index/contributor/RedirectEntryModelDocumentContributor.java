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

package com.liferay.redirect.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.redirect.internal.util.URLUtil;
import com.liferay.redirect.model.RedirectEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.redirect.model.RedirectEntry",
	service = ModelDocumentContributor.class
)
public class RedirectEntryModelDocumentContributor
	implements ModelDocumentContributor<RedirectEntry> {

	@Override
	public void contribute(Document document, RedirectEntry redirectEntry) {
		document.setSortableTextFields(
			new String[] {"destinationURL", "sourceURL"});

		document.addText("destinationURL", redirectEntry.getDestinationURL());
		document.addText(
			"destinationURLParts",
			URLUtil.splitURL(redirectEntry.getDestinationURL()));
		document.addDateSortable(
			"lastOccurrenceDate", redirectEntry.getLastOccurrenceDate());
		document.addText("sourceURL", redirectEntry.getSourceURL());
		document.addText(
			"sourceURLParts", URLUtil.splitURL(redirectEntry.getSourceURL()));
	}

}