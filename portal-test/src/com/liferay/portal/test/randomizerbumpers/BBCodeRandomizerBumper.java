/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.randomizerbumpers;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;

/**
 * @author Shuyang Zhou
 */
public class BBCodeRandomizerBumper implements RandomizerBumper<String> {

	public static final BBCodeRandomizerBumper INSTANCE =
		new BBCodeRandomizerBumper();

	@Override
	public boolean accept(String randomValue) {
		if (randomValue.equals(BBCodeTranslatorUtil.getHTML(randomValue))) {
			return true;
		}

		return false;
	}

}