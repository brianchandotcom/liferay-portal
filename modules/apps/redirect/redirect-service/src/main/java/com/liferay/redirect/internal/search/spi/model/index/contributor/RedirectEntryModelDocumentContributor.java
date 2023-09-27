/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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