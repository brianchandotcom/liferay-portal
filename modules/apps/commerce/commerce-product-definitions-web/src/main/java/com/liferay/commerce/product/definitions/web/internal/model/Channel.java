/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Channel {

	public Channel(long commerceChannelRelId, String name) {
		_commerceChannelRelId = commerceChannelRelId;
		_name = name;
	}

	public long getCommerceChannelRelId() {
		return _commerceChannelRelId;
	}

	public String getName() {
		return _name;
	}

	private final long _commerceChannelRelId;
	private final String _name;

}