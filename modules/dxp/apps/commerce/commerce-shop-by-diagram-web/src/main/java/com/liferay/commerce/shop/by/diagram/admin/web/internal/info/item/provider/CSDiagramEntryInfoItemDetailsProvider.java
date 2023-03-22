/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.admin.web.internal.info.item.provider;

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