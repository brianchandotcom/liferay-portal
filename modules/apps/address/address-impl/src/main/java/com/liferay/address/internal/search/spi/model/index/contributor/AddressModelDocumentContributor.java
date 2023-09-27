/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Address",
	service = ModelDocumentContributor.class
)
public class AddressModelDocumentContributor
	implements ModelDocumentContributor<Address> {

	@Override
	public void contribute(Document document, Address address) {
		document.addText(Field.NAME, address.getName());
		document.addText("city", address.getCity());
		document.addText("countryName", _getCountryName(address));
		document.addText("description", address.getDescription());
		document.addKeyword("listTypeId", address.getListTypeId());
		document.addText("regionName", _getRegionName(address));
		document.addText("street1", address.getStreet1());
		document.addText("zip", address.getZip());
	}

	private String _getCountryName(Address address) {
		Country country = address.getCountry();

		return country.getName();
	}

	private String _getRegionName(Address address) {
		Region region = address.getRegion();

		if (region != null) {
			return region.getName();
		}

		return null;
	}

}