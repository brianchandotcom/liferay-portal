/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.channel;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alec Sloan
 */
@ProviderType
public interface CommerceChannelType {

	public String getKey();

	public String getLabel(Locale locale);

	public UnicodeProperties getTypeSettingsUnicodeProperties(
		Map<String, String[]> parameterMap);

}