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