/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.info.item.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "item.class.name=com.liferay.depot.model.DepotEntry",
	service = InfoItemDetailsProvider.class
)
public class DepotEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<DepotEntry> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(DepotEntry.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(DepotEntry depotEntry) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				DepotEntry.class.getName(), depotEntry.getDepotEntryId()));
	}

}