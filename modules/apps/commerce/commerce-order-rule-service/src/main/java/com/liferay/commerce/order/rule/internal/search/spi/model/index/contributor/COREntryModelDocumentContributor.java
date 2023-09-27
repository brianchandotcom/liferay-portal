/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.internal.search.spi.model.index.contributor;

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.order.rule.model.COREntry",
	service = ModelDocumentContributor.class
)
public class COREntryModelDocumentContributor
	implements ModelDocumentContributor<COREntry> {

	@Override
	public void contribute(Document document, COREntry corEntry) {
		document.addText(Field.NAME, corEntry.getName());
	}

}