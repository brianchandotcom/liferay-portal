/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.validation;

import java.util.Locale;

/**
 * @author Marcela Cunha
 */
public interface DDMValidation {

	public String getLabel(Locale locale);

	public String getName();

	public String getParameterMessage(Locale locale);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public default String getRegex() {
		return null;
	}

	public String getTemplate();

}