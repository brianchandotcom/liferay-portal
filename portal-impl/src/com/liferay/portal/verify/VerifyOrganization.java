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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.concurrent.ThrowableAwareRunnable;
import com.liferay.portal.kernel.concurrent.ThrowableAwareRunnablesExecutorUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class VerifyOrganization extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<ThrowableAwareRunnable> throwableAwareRunnables =
			new ArrayList<>();

		throwableAwareRunnables.add(
			new ThrowableAwareRunnable() {

				@Override
				protected void doRun() throws Exception {
					rebuildTree();
				}

			});

		throwableAwareRunnables.add(
			new ThrowableAwareRunnable() {

				@Override
				protected void doRun() throws Exception {
					updateOrganizationAssets();
				}

			});

		throwableAwareRunnables.add(
			new ThrowableAwareRunnable() {

				@Override
				protected void doRun() throws Exception {
					updateOrganizationAssetEntries();
				}

			});

		ThrowableAwareRunnablesExecutorUtil.execute(throwableAwareRunnables);
	}

	protected void rebuildTree() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				OrganizationLocalServiceUtil.rebuildTree(companyId);
			}
		}
	}

	protected void updateOrganizationAssetEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler();

			sb.append("select distinct AssetEntry.classPK, ");
			sb.append("Organization_.uuid_ from ");
			sb.append(
				"AssetEntry, Organization_ where AssetEntry.classNameId = ");

			long classNameId = ClassNameLocalServiceUtil.getClassNameId(
				Organization.class.getName());

			sb.append(classNameId);

			sb.append(
				" and AssetEntry.classPK = Organization_.organizationId ");
			sb.append("and AssetEntry.classUuid is null");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps1.executeQuery()) {

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection.prepareStatement(
								"update AssetEntry set classUuid = ? where " +
									"classPK = ? and classNameId = ?"))) {

					while (rs.next()) {
						long classPK = rs.getLong("AssetEntry.classPK");
						String uuid = rs.getString("Organization_.uuid_");

						ps2.setString(1, uuid);
						ps2.setLong(2, classPK);
						ps2.setLong(3, classNameId);

						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}
	}

	protected void updateOrganizationAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Organization> organizations =
				OrganizationLocalServiceUtil.getNoAssetOrganizations();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + organizations.size() + " organizations " +
						"with no asset");
			}

			for (Organization organization : organizations) {
				try {
					OrganizationLocalServiceUtil.updateAsset(
						organization.getUserId(), organization, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update asset for organization " +
								organization.getOrganizationId() + ": " +
									e.getMessage());
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for organizations");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyOrganization.class);

}