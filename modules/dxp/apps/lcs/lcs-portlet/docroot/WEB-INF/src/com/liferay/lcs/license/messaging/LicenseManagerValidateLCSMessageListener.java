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

import com.liferay.lcs.advisor.LCSPortletStateAdvisor;
import com.liferay.portal.kernel.json.JSONObject;
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

	public void setLCSPortletStateAdvisor(
		LCSPortletStateAdvisor lcsPortletStateAdvisor) {

		_lcsPortletStateAdvisor = lcsPortletStateAdvisor;
	}

	@Override
	protected Message createResponseMessage(JSONObject jsonObject) {
		LicenseManagerMessageType licenseManagerMessageType =
			LicenseManagerMessageType.LCS_AVAILABLE;

		return licenseManagerMessageType.createMessage(
			_lcsPortletStateAdvisor.getLCSPortletState(true));
	}

	private LCSPortletStateAdvisor _lcsPortletStateAdvisor;

}