/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.model;

/**
 * @author Stefano Motta
 */
public class ChannelCountry {

	public ChannelCountry(
		long channelId, long channelRelId, String countryA2, String countryA3,
		long countryId, String countryName) {

		_channelId = channelId;
		_channelRelId = channelRelId;
		_countryA2 = countryA2;
		_countryA3 = countryA3;
		_countryId = countryId;
		_countryName = countryName;
	}

	public long getChannelId() {
		return _channelId;
	}

	public long getChannelRelId() {
		return _channelRelId;
	}

	public String getCountryA2() {
		return _countryA2;
	}

	public String getCountryA3() {
		return _countryA3;
	}

	public long getCountryId() {
		return _countryId;
	}

	public String getCountryName() {
		return _countryName;
	}

	private final long _channelId;
	private final long _channelRelId;
	private final String _countryA2;
	private final String _countryA3;
	private final long _countryId;
	private final String _countryName;

}