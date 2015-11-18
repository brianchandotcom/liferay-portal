/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;

/**
 * @author Jorge Ferrer
 */
public class OpenIdUtil {

	public static ConsumerManager getConsumerManager() {
		_instance._initialize();

		return _instance._manager;
	}

	public static String getScreenName(String openId) {
		String result = normalize(openId);

		if (result.startsWith(Http.HTTP_WITH_SLASH)) {
			result = result.substring(Http.HTTP_WITH_SLASH.length());
		}

		if (result.startsWith(Http.HTTPS_WITH_SLASH)) {
			result = result.substring(Http.HTTPS_WITH_SLASH.length());
		}

		result = StringUtil.replace(
			result, new String[] {StringPool.SLASH, StringPool.UNDERLINE},
			new String[] {StringPool.PERIOD, StringPool.PERIOD});

		return result;
	}

	public static boolean isEnabled(long companyId) throws SystemException {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.OPEN_ID_AUTH_ENABLED,
			PropsValues.OPEN_ID_AUTH_ENABLED);
	}

	public static String normalize(String identity) {
		String result = identity;

		if (result.endsWith(StringPool.SLASH)) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	private void _initialize() {
		if (_manager != null) {
			return;
		}

		_manager = new ConsumerManager();

		_manager.setAssociations(new InMemoryConsumerAssociationStore());
		_manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
	}

	private static OpenIdUtil _instance = new OpenIdUtil();

	private ConsumerManager _manager;

}