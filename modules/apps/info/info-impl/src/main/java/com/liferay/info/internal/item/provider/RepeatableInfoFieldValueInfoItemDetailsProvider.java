/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.item.provider;

import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemDetailsProvider;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemDetailsProvider.class
)
public class RepeatableInfoFieldValueInfoItemDetailsProvider
	implements InfoItemDetailsProvider<RepeatableInfoFieldValue> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(
			RepeatableInfoFieldValue.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(
		RepeatableInfoFieldValue repeatableInfoFieldValue) {

		InfoItemFieldValues infoItemFieldValues =
			repeatableInfoFieldValue.getInfoItemFieldValues();

		return new InfoItemDetails(
			getInfoItemClassDetails(),
			infoItemFieldValues.getInfoItemReference());
	}

}