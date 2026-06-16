/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.search.spi.model.index.contributor;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "indexer.class.name=com.liferay.fragment.model.FragmentCollection",
	service = ModelDocumentContributor.class
)
public class FragmentCollectionModelDocumentContributor
	implements ModelDocumentContributor<FragmentCollection> {

	@Override
	public void contribute(
		Document document, FragmentCollection fragmentCollection) {

		document.addText(
			Field.DESCRIPTION, fragmentCollection.getDescription());
		document.addText(Field.NAME, fragmentCollection.getName());
		document.addKeyword("marketplace", fragmentCollection.isMarketplace());
	}

}