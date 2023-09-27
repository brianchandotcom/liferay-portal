/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.web.internal.display.context;

import com.liferay.commerce.punchout.configuration.PunchOutConfiguration;

/**
 * @author Jaclyn Ong
 */
public class CommercePunchOutDisplayContext {

	public CommercePunchOutDisplayContext(
		long commerceChannelId, PunchOutConfiguration punchOutConfiguration) {

		_commerceChannelId = commerceChannelId;
		_punchOutConfiguration = punchOutConfiguration;
	}

	public boolean enabled() {
		return _punchOutConfiguration.enabled();
	}

	public long getCommerceChannelId() {
		return _commerceChannelId;
	}

	public String getPunchOutStartURL() {
		return _punchOutConfiguration.punchOutStartURL();
	}

	private final long _commerceChannelId;
	private final PunchOutConfiguration _punchOutConfiguration;

}