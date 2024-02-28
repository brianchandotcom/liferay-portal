/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.change.tracking.spi.display;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTDisplayRenderer.class)
public class AnnouncementsDeliveryCTDisplayRenderer
	extends BaseCTDisplayRenderer<AnnouncementsDelivery> {

	@Override
	public Class<AnnouncementsDelivery> getModelClass() {
		return AnnouncementsDelivery.class;
	}

	@Override
	public String getTitle(Locale locale, AnnouncementsDelivery model)
		throws PortalException {

		return _language.get(locale, model.getType());
	}

	@Override
	public String getTypeName(Locale locale) {
		return _language.get(locale, "alerts-and-announcements-delivery");
	}

	@Override
	public boolean isHideable(AnnouncementsDelivery announcementsDelivery) {
		if (announcementsDelivery.isEmail()) {
			return false;
		}

		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<AnnouncementsDelivery> displayBuilder) {

		AnnouncementsDelivery announcementsDelivery = displayBuilder.getModel();

		displayBuilder.display(
			"delivery-id", announcementsDelivery.getDeliveryId()
		).display(
			"company-id", announcementsDelivery.getCompanyId()
		).display(
			"user-id", announcementsDelivery.getUserId()
		).display(
			"type", announcementsDelivery.getType()
		).display(
			"email", announcementsDelivery.isEmail()
		).display(
			"sms", announcementsDelivery.isSms()
		).display(
			"website", announcementsDelivery.isWebsite()
		);
	}

	@Reference
	private Language _language;

}