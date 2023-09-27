/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field;

import com.liferay.info.localized.InfoLocalizedValue;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public interface InfoFieldSetEntry {

	public String getLabel(Locale locale);

	public InfoLocalizedValue<String> getLabelInfoLocalizedValue();

	public String getName();

	public default String getUniqueId() {
		return getName();
	}

}