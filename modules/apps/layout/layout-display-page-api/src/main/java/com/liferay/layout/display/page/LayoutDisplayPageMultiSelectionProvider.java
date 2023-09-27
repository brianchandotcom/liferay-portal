/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import com.liferay.info.item.InfoItemReference;

import java.util.List;
import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public interface LayoutDisplayPageMultiSelectionProvider<T> {

	public String getClassName();

	public String getPluralLabel(Locale locale);

	public default List<InfoItemReference> process(
		List<InfoItemReference> infoItemReferences) {

		return infoItemReferences;
	}

}