/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;

import java.util.List;

/**
 * @author Víctor Galán
 */
public class RepeatableInfoFieldValue {

	public RepeatableInfoFieldValue(
		InfoItemReference infoItemReference,
		List<InfoFieldValue<Object>> infoFieldValues) {

		_infoItemFieldValues = InfoItemFieldValues.builder(
		).infoFieldValue(
			infoFieldValueUnsafeConsumer -> {
				for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
					infoFieldValueUnsafeConsumer.accept(infoFieldValue);
				}
			}
		).infoItemReference(
			infoItemReference
		).build();
	}

	public InfoItemFieldValues getInfoItemFieldValues() {
		return _infoItemFieldValues;
	}

	public InfoItemReference getInfoItemReference() {
		return _infoItemFieldValues.getInfoItemReference();
	}

	private final InfoItemFieldValues _infoItemFieldValues;

}