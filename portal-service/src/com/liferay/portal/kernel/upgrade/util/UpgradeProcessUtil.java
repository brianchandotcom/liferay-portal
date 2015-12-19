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

package com.liferay.portal.kernel.upgrade.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
@ProviderType
public class UpgradeProcessUtil {

	public static String getDefaultLanguageId(long companyId)
		throws SQLException {

		String languageId = _languageIds.get(companyId);

		if (languageId != null) {
			return languageId;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select languageId from User_ where companyId = ? and " +
					"defaultUser = ?");

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			if (rs.next()) {
				languageId = rs.getString("languageId");

				_languageIds.put(companyId, languageId);

				return languageId;
			}
			else {
				return LocaleUtil.toLanguageId(LocaleUtil.US);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static List<UpgradeProcess> initUpgradeProcesses(
		ClassLoader classLoader, String[] upgradeProcessClassNames) {

		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		for (String upgradeProcessClassName : upgradeProcessClassNames) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing upgrade " + upgradeProcessClassName);
			}

			UpgradeProcess upgradeProcess = null;

			try {
				Class<?> clazz = classLoader.loadClass(upgradeProcessClassName);

				upgradeProcess = (UpgradeProcess)clazz.newInstance();
			}
			catch (Exception e) {
				_log.error(
					"Unable to initialize upgrade " + upgradeProcessClassName);

				continue;
			}

			upgradeProcesses.add(upgradeProcess);
		}

		return upgradeProcesses;
	}

	public static boolean isCreateIGImageDocumentType() {
		return _createIGImageDocumentType;
	}

	public static void setCreateIGImageDocumentType(
		boolean createIGImageDocumentType) {

		_createIGImageDocumentType = createIGImageDocumentType;
	}

	public static boolean upgradeProcess(
			int buildNumber, List<UpgradeProcess> upgradeProcesses)
		throws UpgradeException {

		return upgradeProcess(buildNumber, upgradeProcesses, _INDEX_ON_UPGRADE);
	}

	public static boolean upgradeProcess(
			int buildNumber, List<UpgradeProcess> upgradeProcesses,
			boolean indexOnUpgrade)
		throws UpgradeException {

		boolean ranUpgradeProcess = false;

		boolean tempIndexReadOnly = IndexWriterHelperUtil.isIndexReadOnly();

		if (indexOnUpgrade) {
			IndexWriterHelperUtil.setIndexReadOnly(true);
		}

		try {
			for (UpgradeProcess upgradeProcess : upgradeProcesses) {
				boolean tempRanUpgradeProcess = _upgradeProcess(
					buildNumber, upgradeProcess);

				if (tempRanUpgradeProcess) {
					ranUpgradeProcess = true;
				}
			}
		}
		finally {
			IndexWriterHelperUtil.setIndexReadOnly(tempIndexReadOnly);
		}

		return ranUpgradeProcess;
	}

	private static boolean _upgradeProcess(
			int buildNumber, UpgradeProcess upgradeProcess)
		throws UpgradeException {

		Class<?> clazz = upgradeProcess.getClass();

		if ((upgradeProcess.getThreshold() == 0) ||
			(upgradeProcess.getThreshold() > buildNumber)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Running upgrade " + clazz.getName());
			}

			upgradeProcess.upgrade();

			if (_log.isDebugEnabled()) {
				_log.debug("Finished upgrade " + clazz.getName());
			}

			return true;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Upgrade threshold " + upgradeProcess.getThreshold() +
					" will not trigger upgrade");

			_log.debug("Skipping upgrade " + clazz.getName());
		}

		return false;
	}

	private static final boolean _INDEX_ON_UPGRADE = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_ON_UPGRADE));

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeProcessUtil.class);

	private static boolean _createIGImageDocumentType = false;
	private static final Map<Long, String> _languageIds = new HashMap<>();

}