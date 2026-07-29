/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.randomizerbumpers;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FriendlyURLKeywordsUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Mariano Álvaro Sáiz
 */
public class GroupFriendlyURLRandomizerBumper
	implements RandomizerBumper<String> {

	public static final GroupFriendlyURLRandomizerBumper INSTANCE =
		new GroupFriendlyURLRandomizerBumper();

	@Override
	public boolean accept(String randomValue) {
		if (Validator.isNull(randomValue)) {
			return false;
		}

		return !FriendlyURLKeywordsUtil.hasFriendlyURLKeyword(
			StringPool.SLASH +
				FriendlyURLNormalizerUtil.normalize(randomValue));
	}

}