/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.web.internal.info.item.provider;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mahmoud Azzam
 * @author Alessio Antonio Rendina
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemDetailsProvider.class
)
public class CSDiagramEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<CSDiagramEntry> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(CSDiagramEntry.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(CSDiagramEntry csDiagramEntry) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				CSDiagramEntry.class.getName(),
				csDiagramEntry.getCSDiagramEntryId()));
	}

}