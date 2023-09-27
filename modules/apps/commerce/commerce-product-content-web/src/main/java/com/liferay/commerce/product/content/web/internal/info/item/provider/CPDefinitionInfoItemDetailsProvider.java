/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 * @author Alec Sloan
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemDetailsProvider.class
)
public class CPDefinitionInfoItemDetailsProvider
	implements InfoItemDetailsProvider<CPDefinition> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(CPDefinition.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(CPDefinition cpDefinition) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				CPDefinition.class.getName(),
				cpDefinition.getCPDefinitionId()));
	}

}