/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.provider;

import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class RepeatableInfoFieldValueInfoFieldValuesProvider
	implements InfoItemFieldValuesProvider<RepeatableInfoFieldValue> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		RepeatableInfoFieldValue repeatableInfoFieldValue) {

		return repeatableInfoFieldValue.getInfoItemFieldValues();
	}

}