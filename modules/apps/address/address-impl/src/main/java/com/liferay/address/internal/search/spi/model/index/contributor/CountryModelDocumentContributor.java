/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Balazs Breier
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Country",
	service = ModelDocumentContributor.class
)
public class CountryModelDocumentContributor
	implements ModelDocumentContributor<Country> {

	@Override
	public void contribute(Document document, Country country) {
		document.addText(Field.NAME, country.getName());
		document.addKeyword("a2", country.getA2());
		document.addKeyword("a3", country.getA3());
		document.addKeyword("active", country.isActive());
		document.addKeyword(
			"externalReferenceCode", country.getExternalReferenceCode());
		document.addKeyword("number", country.getNumber());
		document.addNumber("position", country.getPosition());
	}

}