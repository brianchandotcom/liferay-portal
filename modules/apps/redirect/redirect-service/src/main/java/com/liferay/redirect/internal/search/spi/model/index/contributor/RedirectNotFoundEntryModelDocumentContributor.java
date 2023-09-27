/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.redirect.internal.util.URLUtil;
import com.liferay.redirect.model.RedirectNotFoundEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "indexer.class.name=com.liferay.redirect.model.RedirectNotFoundEntry",
	service = ModelDocumentContributor.class
)
public class RedirectNotFoundEntryModelDocumentContributor
	implements ModelDocumentContributor<RedirectNotFoundEntry> {

	@Override
	public void contribute(
		Document document, RedirectNotFoundEntry redirectNotFoundEntry) {

		document.addText(Field.URL, redirectNotFoundEntry.getUrl());
		document.addKeyword("ignored", redirectNotFoundEntry.isIgnored());
		document.addNumber(
			"requestCount", redirectNotFoundEntry.getRequestCount());
		document.addText(
			"urlParts", URLUtil.splitURL(redirectNotFoundEntry.getUrl()));
	}

}