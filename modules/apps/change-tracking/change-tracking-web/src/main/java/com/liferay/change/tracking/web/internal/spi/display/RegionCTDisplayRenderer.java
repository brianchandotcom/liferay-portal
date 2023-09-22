/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Region;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTDisplayRenderer.class)
public class RegionCTDisplayRenderer extends BaseCTDisplayRenderer<Region> {

	@Override
	public Class<Region> getModelClass() {
		return Region.class;
	}

	@Override
	public String getTitle(Locale locale, Region region)
		throws PortalException {

		return region.getTitle(String.valueOf(locale));
	}

	@Override
	public String getTypeName(Locale locale) {
		return _language.get(locale, "region");
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Region> displayBuilder)
		throws PortalException {

		Region region = displayBuilder.getModel();

		displayBuilder.display(
			"name", region.getName()
		).display(
			"region-id", region.getRegionId()
		).display(
			"country-id", region.getCountryId()
		).display(
			"position", region.getPosition()
		).display(
			"region-code", region.getRegionCode()
		).display(
			"active", region.isActive()
		);
	}

	@Reference
	private Language _language;

}