/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.frontend.util;

import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelClayTableUtil {

	public static LabelField getLabelField(boolean success, Locale locale) {
		if (success) {
			return new LabelField(
				"success", LanguageUtil.get(locale, "active"));
		}

		return new LabelField("danger", LanguageUtil.get(locale, "inactive"));
	}

}