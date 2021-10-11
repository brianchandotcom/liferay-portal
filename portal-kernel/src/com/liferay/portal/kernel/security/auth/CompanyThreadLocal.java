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

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class CompanyThreadLocal {

	public static Long getCompanyId() {
		Long companyId = _companyId.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getCompanyId " + companyId);
		}

		return companyId;
	}

	public static boolean isDeleteInProcess() {
		return _deleteInProcess.get();
	}

	public static boolean isInitializingPortalInstance() {
		return _initializingPortalInstance.get();
	}

	public static boolean isLocked() {
		return _locked.get();
	}

	public static SafeCloseable lock(long companyId) {
		SafeCloseable safeCloseable = setWithSafeCloseable(companyId);

		_locked.set(true);

		return () -> {
			_locked.set(false);

			safeCloseable.close();
		};
	}

	public static void setCompanyId(Long companyId) {
		if (_setCompanyId(companyId)) {
			CTCollectionThreadLocal.removeCTCollectionId();
		}
	}

	public static void setDeleteInProcess(boolean deleteInProcess) {
		_deleteInProcess.set(deleteInProcess);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setInitializingCompanyIdWithSafeCloseable(long)}
	 */
	@Deprecated
	public static SafeClosable setInitializingCompanyId(long companyId) {
		if (companyId > 0) {
			return _companyId.setWithSafeClosable(companyId);
		}

		return _companyId.setWithSafeClosable(CompanyConstants.SYSTEM);
	}

	public static SafeCloseable setInitializingCompanyIdWithSafeCloseable(
		long companyId) {

		if (companyId > 0) {
			return _companyId.setWithSafeCloseable(companyId);
		}

		return _companyId.setWithSafeCloseable(CompanyConstants.SYSTEM);
	}

	public static SafeCloseable setInitializingPortalInstance(
		boolean initializingPortalInstance) {

		return _initializingPortalInstance.setWithSafeCloseable(
			initializingPortalInstance);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setWithSafeCloseable(Long)}
	 */
	@Deprecated
	public static SafeClosable setWithSafeClosable(Long companyId) {
		long currentCompanyId = _companyId.get();
		Locale defaultLocale = LocaleThreadLocal.getDefaultLocale();
		TimeZone defaultTimeZone = TimeZoneThreadLocal.getDefaultTimeZone();

		_setCompanyId(companyId);

		SafeClosable ctCollectionSafeClosable =
			CTCollectionThreadLocal.setCTCollectionId(0);

		return () -> {
			_companyId.set(currentCompanyId);
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
			TimeZoneThreadLocal.setDefaultTimeZone(defaultTimeZone);

			ctCollectionSafeClosable.close();
		};
	}

	public static SafeCloseable setWithSafeCloseable(Long companyId) {
		long currentCompanyId = _companyId.get();
		Locale defaultLocale = LocaleThreadLocal.getDefaultLocale();
		TimeZone defaultTimeZone = TimeZoneThreadLocal.getDefaultTimeZone();

		_setCompanyId(companyId);

		SafeCloseable ctCollectionSafeCloseable =
			CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(0);

		return () -> {
			_companyId.set(currentCompanyId);
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
			TimeZoneThreadLocal.setDefaultTimeZone(defaultTimeZone);

			ctCollectionSafeCloseable.close();
		};
	}

	private static User _fetchDefaultUser(long companyId) throws Exception {
		User defaultUser = null;

		try {
			defaultUser = UserLocalServiceUtil.fetchDefaultUser(companyId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		if (defaultUser != null) {
			return defaultUser;
		}

		try (Connection connection = DataAccess.getConnection()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select userId, languageId, timeZoneId from User_ " +
							"where companyId = ? and defaultUser = ?")) {

				preparedStatement.setLong(1, companyId);
				preparedStatement.setBoolean(2, true);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (!resultSet.next()) {
						return null;
					}

					defaultUser = UserLocalServiceUtil.createUser(
						resultSet.getLong("userId"));

					defaultUser.setLanguageId(
						resultSet.getString("languageId"));
					defaultUser.setTimeZoneId(
						resultSet.getString("timeZoneId"));
				}
			}
		}

		return defaultUser;
	}

	private static boolean _setCompanyId(Long companyId) {
		if (companyId.equals(_companyId.get())) {
			return false;
		}

		if (isLocked()) {
			throw new UnsupportedOperationException(
				"CompanyThreadLocal modification is not allowed");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("setCompanyId " + companyId);
		}

		if (companyId > 0) {
			_companyId.set(companyId);

			try {
				User defaultUser = _fetchDefaultUser(companyId);

				if (defaultUser == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"No default user was found for company " +
								companyId);
					}
				}
				else {
					LocaleThreadLocal.setDefaultLocale(defaultUser.getLocale());
					TimeZoneThreadLocal.setDefaultTimeZone(
						defaultUser.getTimeZone());
				}
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
		else {
			_companyId.set(CompanyConstants.SYSTEM);

			LocaleThreadLocal.setDefaultLocale(null);
			TimeZoneThreadLocal.setDefaultTimeZone(null);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyThreadLocal.class);

	private static final CentralizedThreadLocal<Long> _companyId =
		new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._companyId",
			() -> CompanyConstants.SYSTEM);
	private static final ThreadLocal<Boolean> _deleteInProcess =
		new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._deleteInProcess",
			() -> Boolean.FALSE);
	private static final CentralizedThreadLocal<Boolean>
		_initializingPortalInstance = new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._initializingPortalInstance",
			() -> Boolean.FALSE);
	private static final ThreadLocal<Boolean> _locked =
		new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._locked", () -> Boolean.FALSE);

}