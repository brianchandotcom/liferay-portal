/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public interface Value extends Serializable {

	public void addString(Locale locale, String value);

	public Set<Locale> getAvailableLocales();

	public Locale getDefaultLocale();

	public String getString(Locale locale);

	public Map<Locale, String> getValues();

	public boolean isLocalized();

	public void removeLocale(Locale locale);

	public void setDefaultLocale(Locale defaultLocale);

}