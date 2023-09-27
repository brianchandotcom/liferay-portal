/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;

/**
 * @author Mika Koivisto
 */
public class CryptoStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) {
		try {
			Mac.getInstance("HmacSHA1");
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			_log.error(
				"Unable to get Mac instance for algorithm HmacSHA1",
				noSuchAlgorithmException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CryptoStartupAction.class);

}