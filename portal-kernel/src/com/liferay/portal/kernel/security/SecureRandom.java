/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security;

import java.util.Random;

/**
 * @author Shuyang Zhou
 */
public class SecureRandom extends Random {

	@Override
	protected int next(int bits) {
		int bytes = (bits + 7) / 8;

		int result = SecureRandomUtil.nextByte();

		for (int i = 1; i < bytes; i++) {
			result = (result << 8) + (SecureRandomUtil.nextByte() & 0xFF);
		}

		return result >>> ((bytes * 8) - bits);
	}

}