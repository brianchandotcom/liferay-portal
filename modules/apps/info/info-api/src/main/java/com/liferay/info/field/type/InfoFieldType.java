/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface InfoFieldType {

	public default String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getName());
	}

	public String getName();

	public final class Attribute<T extends InfoFieldType, V> {
	}

}