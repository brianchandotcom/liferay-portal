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

package com.liferay.lcs.license.messaging;

import com.liferay.lcs.rest.LCSSubscriptionEntry;
import com.liferay.lcs.rest.LCSSubscriptionEntryService;
import com.liferay.lcs.rest.client.LCSSubscriptionEntry;
import com.liferay.lcs.rest.client.LCSSubscriptionEntryClient;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.license.messaging.LicenseManagerMessageType;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Igor Beslic
 */
public class LicenseManagerValidateLCSMessageListener
	extends LicenseManagerBaseMessageListener {

	public LicenseManagerValidateLCSMessageListener() {
		setAllowedLicenseManagerMessageType(
			LicenseManagerMessageType.VALIDATE_LCS);
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSSubscriptionEntryService(
		LCSSubscriptionEntryClient lcsSubscriptionEntryClient) {

		_lcsSubscriptionEntryClient = lcsSubscriptionEntryClient;
	}

	@Override
	protected Message createResponseMessage(JSONObject jsonObject) {
		LicenseManagerMessageType licenseManagerMessageType =
			LicenseManagerMessageType.LCS_AVAILABLE;

		if (!LCSUtil.isLCSClusterNodeRegistered()) {
			return licenseManagerMessageType.createMessage(
				LCSPortletState.NOT_REGISTERED);
		}

		LCSSubscriptionEntry lcsSubscriptionEntry =
			_lcsSubscriptionEntryClient.fetchLCSSubscriptionEntry(
				_keyGenerator.getKey());

		if (lcsSubscriptionEntry == null) {
			return licenseManagerMessageType.createMessage(
				LCSPortletState.NO_SUBSCRIPTION);
		}

		return licenseManagerMessageType.createMessage(LCSPortletState.GOOD);
	}

	private KeyGenerator _keyGenerator;
	private LCSSubscriptionEntryClient _lcsSubscriptionEntryClient;

}