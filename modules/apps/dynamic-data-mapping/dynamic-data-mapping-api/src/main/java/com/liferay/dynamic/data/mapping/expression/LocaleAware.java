/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 * @author Marcos Martins
 */
@Deprecated
@ProviderType
public interface LocaleAware {

	public void setLocale(Locale locale);

}