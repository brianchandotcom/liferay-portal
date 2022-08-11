/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mail.reader.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

/**
 * @author Alexander Chow
 * @see    com.liferay.util.mail.InternetAddressUtil
 */
public class InternetAddressUtil {

	public static String toString(Address[] addresses) {
		if (ArrayUtil.isEmpty(addresses)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((addresses.length * 2) - 1);

		for (int i = 0; i < (addresses.length - 1); i++) {
			sb.append(_toString(addresses[i]));
			sb.append(StringPool.COMMA);
		}

		sb.append(_toString(addresses[addresses.length - 1]));

		return sb.toString();
	}

	private static String _toString(Address address) {
		InternetAddress internetAddress = (InternetAddress)address;

		if (internetAddress != null) {
			StringBundler sb = new StringBundler(5);

			String personal = internetAddress.getPersonal();
			String emailAddress = internetAddress.getAddress();

			if (Validator.isNotNull(personal)) {
				sb.append(personal);
				sb.append(StringPool.SPACE);
				sb.append(StringPool.LESS_THAN);
				sb.append(emailAddress);
				sb.append(StringPool.GREATER_THAN);
			}
			else {
				sb.append(emailAddress);
			}

			return sb.toString();
		}

		return StringPool.BLANK;
	}

}