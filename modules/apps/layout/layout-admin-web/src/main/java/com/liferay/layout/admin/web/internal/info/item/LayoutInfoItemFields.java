/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author Adolfo Pérez
 */
public class LayoutInfoItemFields {

	public static final InfoField<TextInfoFieldType> nameInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			Layout.class.getSimpleName()
		).name(
			"name"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				InfoItemFieldValuesProvider.class, "name")
		).localizable(
			true
		).build();

}