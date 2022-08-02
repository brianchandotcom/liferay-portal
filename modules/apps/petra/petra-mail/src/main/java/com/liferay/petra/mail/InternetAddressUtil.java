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

package com.liferay.petra.mail;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

import javax.mail.Address;
import javax.mail.internet.AddressException;

/**
 * @author Alexander Chow
 * @see    com.liferay.util.mail.InternetAddressUtil
 */
public class InternetAddressUtil {

	public static void validateAddress(Address address)
		throws AddressException {

		if (address == null) {
			throw new AddressException("Email address is null");
		}

		String addressString = address.toString();

		for (char c : addressString.toCharArray()) {
			if ((c == CharPool.NEW_LINE) || (c == CharPool.RETURN)) {
				throw new AddressException(
					StringBundler.concat(
						"Email address ", addressString,
						" is invalid because it contains line breaks"));
			}
		}
	}

	public static void validateAddresses(Address[] addresses)
		throws AddressException {

		if (addresses == null) {
			throw new AddressException();
		}

		for (Address internetAddress : addresses) {
			validateAddress(internetAddress);
		}
	}

}