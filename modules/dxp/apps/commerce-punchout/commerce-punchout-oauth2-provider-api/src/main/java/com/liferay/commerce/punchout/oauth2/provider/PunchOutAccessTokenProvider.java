/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.oauth2.provider;

import com.liferay.commerce.punchout.oauth2.provider.model.PunchOutAccessToken;

import java.util.HashMap;

/**
 * @author Jaclyn Ong
 */
public interface PunchOutAccessTokenProvider {

	public PunchOutAccessToken generatePunchOutAccessToken(
		long groupId, long commerceAccountId, String currencyCode,
		String userEmailAddress, String commerceOrderUuid,
		HashMap<String, Object> punchOutSessionAttributes);

	public PunchOutAccessToken getPunchOutAccessToken(String token);

	public PunchOutAccessToken removePunchOutAccessToken(String token);

}