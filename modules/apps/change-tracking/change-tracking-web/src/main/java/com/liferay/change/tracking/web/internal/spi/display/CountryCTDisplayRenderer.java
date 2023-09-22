/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Country;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTDisplayRenderer.class)
public class CountryCTDisplayRenderer extends BaseCTDisplayRenderer<Country> {

	@Override
	public Class<Country> getModelClass() {
		return Country.class;
	}

	@Override
	public String getTitle(Locale locale, Country country)
		throws PortalException {

		return country.getTitle(locale);
	}

	@Override
	public String getTypeName(Locale locale) {
		return _language.get(locale, "country");
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Country> displayBuilder)
		throws PortalException {

		Country country = displayBuilder.getModel();

		displayBuilder.display(
			"name", country.getTitle()
		).display(
			"key", country.getName()
		).display(
			"a2", country.getA2()
		).display(
			"a3", country.getA3()
		).display(
			"country-id", country.getCountryId()
		).display(
			"number", country.getNumber()
		).display(
			"position", country.getPosition()
		).display(
			"idd", country.getIdd()
		).display(
			"shipping", country.isShippingAllowed()
		).display(
			"subject-to-vat", country.isSubjectToVAT()
		).display(
			"active", country.isActive()
		).display(
			"billing-allowed", country.isBillingAllowed()
		);
	}

	@Reference
	private Language _language;

}