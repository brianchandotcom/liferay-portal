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
		catch (NoSuchAlgorithmException nsae) {
			_log.error(
				"Unable to get Mac instance for algorithm HmacSHA1", nsae);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CryptoStartupAction.class);

}