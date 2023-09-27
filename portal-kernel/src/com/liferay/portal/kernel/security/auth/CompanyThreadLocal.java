/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.spring.orm.LastSessionRecorderHelperUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class CompanyThreadLocal {

	public static User fetchGuestUser() {
		Long companyId = _companyId.get();

		if (companyId == CompanyConstants.SYSTEM) {
			return null;
		}

		User guestUser = null;

		try {
			guestUser = UserLocalServiceUtil.fetchGuestUser(companyId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if (guestUser != null) {
			return guestUser;
		}

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select userId, languageId, timeZoneId from User_ where " +
					"companyId = ? and type_ = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setInt(2, UserConstants.TYPE_GUEST);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return null;
				}

				guestUser = UserLocalServiceUtil.createUser(
					resultSet.getLong("userId"));

				guestUser.setLanguageId(resultSet.getString("languageId"));
				guestUser.setTimeZoneId(resultSet.getString("timeZoneId"));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return guestUser;
	}

	public static Long getCompanyId() {
		Long companyId = _companyId.get();

		if (_log.isDebugEnabled()) {
			_log.debug("Get company ID " + companyId);
		}

		return companyId;
	}

	public static boolean isInitializingPortalInstance() {
		return _initializingPortalInstance.get();
	}

	public static boolean isLocked() {
		return _locked.get();
	}

	public static SafeCloseable lock(long companyId) {
		if (isLocked()) {
			Long currentCompanyId = _companyId.get();

			if (companyId == currentCompanyId.longValue()) {
				return () -> {
				};
			}

			throw new UnsupportedOperationException(
				StringBundler.concat(
					"Company ID ", companyId, " and company ID ",
					currentCompanyId.longValue(), " are different"));
		}

		_syncLastDBPartitionSessionState();

		SafeCloseable safeCloseable = _companyId.setWithSafeCloseable(
			companyId);

		_locked.set(true);

		return () -> {
			_locked.set(false);

			_syncLastDBPartitionSessionState();

			safeCloseable.close();
		};
	}

	public static void setCompanyId(Long companyId) {
		if (_setCompanyId(companyId)) {
			CTCollectionThreadLocal.removeCTCollectionId();
		}
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

	public static SafeCloseable setWithSafeCloseable(Long companyId) {
		return setWithSafeCloseable(
			companyId, CTCollectionThreadLocal.CT_COLLECTION_ID_PRODUCTION);
	}

	public static SafeCloseable setWithSafeCloseable(
		Long companyId, Long ctCollectionId) {

		long currentCompanyId = _companyId.get();

		boolean changed = _setCompanyId(companyId);

		SafeCloseable ctCollectionSafeCloseable =
			CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
				ctCollectionId);

		return () -> {
			if (changed) {
				_syncLastDBPartitionSessionState();
			}

			_companyId.set(currentCompanyId);

			_clearUserThreadLocals();

			ctCollectionSafeCloseable.close();
		};
	}

	private static void _clearUserThreadLocals() {
		LocaleThreadLocal.removeDefaultLocale();
		TimeZoneThreadLocal.removeDefaultTimeZone();
	}

	private static boolean _setCompanyId(Long companyId) {
		if (companyId.equals(_companyId.get())) {
			if (!isLocked()) {
				return false;
			}

			if ((LocaleThreadLocal.getDefaultLocale() == null) ||
				(TimeZoneThreadLocal.getDefaultTimeZone() == null)) {

				_clearUserThreadLocals();
			}

			return false;
		}

		if (isLocked()) {
			throw new UnsupportedOperationException(
				"CompanyThreadLocal modification is not allowed");
		}

		_syncLastDBPartitionSessionState();

		if (_log.isDebugEnabled()) {
			_log.debug("setCompanyId " + companyId);
		}

		if (companyId > 0) {
			_companyId.set(companyId);

			_clearUserThreadLocals();
		}
		else {
			_companyId.set(CompanyConstants.SYSTEM);

			_clearUserThreadLocals();
		}

		return true;
	}

	private static void _syncLastDBPartitionSessionState() {
		if (DBPartition.isPartitionEnabled()) {
			LastSessionRecorderHelperUtil.syncLastSessionState(false);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyThreadLocal.class);

	private static final CentralizedThreadLocal<Long> _companyId;
	private static final CentralizedThreadLocal<Boolean>
		_initializingPortalInstance = new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._initializingPortalInstance",
			() -> Boolean.FALSE);
	private static final ThreadLocal<Boolean> _locked =
		new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._locked", () -> Boolean.FALSE);

	static {
		_companyId = new CentralizedThreadLocal<>(
			CompanyThreadLocal.class + "._companyId",
			() -> CompanyConstants.SYSTEM);
	}

}