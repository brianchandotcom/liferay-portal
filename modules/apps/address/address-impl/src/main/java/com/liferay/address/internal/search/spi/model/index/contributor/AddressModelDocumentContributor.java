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