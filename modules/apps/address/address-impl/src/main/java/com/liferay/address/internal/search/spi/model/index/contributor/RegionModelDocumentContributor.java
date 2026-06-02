/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Balazs Breier
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Region",
	service = ModelDocumentContributor.class
)
public class RegionModelDocumentContributor
	implements ModelDocumentContributor<Region> {

	@Override
	public void contribute(Document document, Region region) {
		document.addText(Field.NAME, region.getName());
		document.addKeyword("active", region.isActive());
		document.addKeyword("countryId", region.getCountryId());
		document.addKeyword(
			"externalReferenceCode", region.getExternalReferenceCode());
		document.addNumber("position", region.getPosition());
		document.addKeyword("regionCode", region.getRegionCode());
	}

}