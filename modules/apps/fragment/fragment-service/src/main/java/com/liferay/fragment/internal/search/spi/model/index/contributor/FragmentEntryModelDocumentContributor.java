/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.search.spi.model.index.contributor;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "indexer.class.name=com.liferay.fragment.model.FragmentEntry",
	service = ModelDocumentContributor.class
)
public class FragmentEntryModelDocumentContributor
	implements ModelDocumentContributor<FragmentEntry> {

	@Override
	public void contribute(Document document, FragmentEntry fragmentEntry) {
		document.addText(Field.NAME, fragmentEntry.getName());
		document.addKeyword(
			"fragmentCollectionId", fragmentEntry.getFragmentCollectionId());
		document.addKeyword("head", fragmentEntry.isHead());
		document.addKeyword(
			"headListable",
			fragmentEntry.isHead() ||
			(fragmentEntry.getHeadId() == fragmentEntry.getFragmentEntryId()));
		document.addKeyword("marketplace", fragmentEntry.isMarketplace());
	}

}