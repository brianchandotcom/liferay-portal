/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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