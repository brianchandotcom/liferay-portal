/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ip.geocoder;

import com.liferay.petra.string.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class IPInfo {

	public IPInfo(String countryCode, String ipAddress) {
		_countryCode = countryCode;
		_ipAddress = ipAddress;
	}

	public String getCountryCode() {
		return _countryCode;
	}

	public String getIPAddress() {
		return _ipAddress;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{countryCode=", _countryCode, ", ipAddress=", _ipAddress, "}");
	}

	private final String _countryCode;
	private final String _ipAddress;

}