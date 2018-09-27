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

package com.liferay.portal.transaction;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

/**
 * @author Miguel Pastor
 */
public class TransactionsUtil {

	public static void disableTransactions() {
		if (_log.isDebugEnabled()) {
			_log.debug("Disable transactions");
		}

		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = false;

		try {
			Field field = ServiceBeanAopProxy.class.getDeclaredField(
				"_enabled");

			field.setAccessible(true);

			field.set(null, false);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unexpected error disabling transactions", e);
		}
	}

	public static void enableTransactions() {
		if (_log.isDebugEnabled()) {
			_log.debug("Enable transactions");
		}

		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SPRING_HIBERNATE_SESSION_DELEGATED));

		try {
			Field field = ServiceBeanAopProxy.class.getDeclaredField(
				"_enabled");

			field.setAccessible(true);

			field.set(null, true);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unexpected error enabling transactions", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TransactionsUtil.class);

}