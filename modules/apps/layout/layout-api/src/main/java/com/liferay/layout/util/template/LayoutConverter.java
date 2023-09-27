/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.template;

import com.liferay.portal.kernel.model.Layout;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public interface LayoutConverter {

	public LayoutConversionResult convert(Layout layout, Locale locale);

	public default boolean isConvertible(Layout layout) {
		if (layout.isTypeContent()) {
			return false;
		}

		return true;
	}

}