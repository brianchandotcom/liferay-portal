/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

		return result >>> (bytes * 8 - bits);
	}

}