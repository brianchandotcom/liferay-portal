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

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = CTDisplayRenderer.class)
public class AddressCTDisplayRenderer extends BaseCTDisplayRenderer<Address> {

	@Override
	public Class<Address> getModelClass() {
		return Address.class;
	}

	@Override
	public String getTitle(Locale locale, Address address)
		throws PortalException {

		return address.getName();
	}

	@Override
	public String getTypeName(Locale locale) {
		return _language.get(locale, "address");
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Address> displayBuilder)
		throws PortalException {

		Address address = displayBuilder.getModel();

		displayBuilder.display(
			"username", address.getUserName()
		).display(
			"userId", address.getUserId()
		).display(
			"create-date", address.getCreateDate()
		).display(
			"name", address.getName()
		).display(
			"description", address.getDescription()
		).display(
			"street1", address.getStreet1()
		).display(
			"street2", address.getStreet2()
		).display(
			"street3", address.getStreet3()
		).display(
			"city", address.getCity()
		).display(
			"zip", address.getZip()
		).display(
			"country",
			() -> {
				Country country = address.getCountry();

				return country.getName(displayBuilder.getLocale());
			}
		).display(
			"region",
			() -> {
				Region region = address.getRegion();

				return region.getName();
			}
		).display(
			"primary", address.isPrimary()
		).display(
			"mailing", address.isMailing()
		);
	}

	@Reference
	private Language _language;

}