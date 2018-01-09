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

package com.liferay.organizations.service.internal.verify;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.verify.VerifyProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.organizations.service"},
	service = {VerifyProcess.class}
)
public class OrganizationServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		List<Future<Void>> futures = executorService.invokeAll(
			Arrays.asList(
				this::rebuildTree, this::updateOrganizationAssets,
				this::updateOrganizationAssetEntries));

		executorService.shutdown();

		UnsafeConsumer.accept(futures, Future::get, Exception.class);
	}

	protected Void rebuildTree() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				_organizationLocalService.rebuildTree(companyId);
			}
		}

		return null;
	}

	protected Void updateOrganizationAssetEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(7);

			sb.append("select distinct AssetEntry.classPK as classPK, ");
			sb.append("Organization_.uuid_ as uuid from AssetEntry, ");
			sb.append("Organization_ where AssetEntry.classNameId = ");

			long classNameId = _classNameLocalService.getClassNameId(
				Organization.class.getName());

			sb.append(classNameId);

			sb.append(" and AssetEntry.classPK = ");
			sb.append("Organization_.organizationId and AssetEntry.classUuid ");
			sb.append("is null");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps1.executeQuery()) {

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection.prepareStatement(
								"update AssetEntry set classUuid = ? where " +
									"classPK = ? and classNameId = ?"))) {

					while (rs.next()) {
						long classPK = rs.getLong("classPK");
						String uuid = rs.getString("uuid");

						ps2.setString(1, uuid);

						ps2.setLong(2, classPK);
						ps2.setLong(3, classNameId);

						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}

		return null;
	}

	protected Void updateOrganizationAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Organization> organizations =
				_organizationLocalService.getNoAssetOrganizations();

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Processing ", String.valueOf(organizations.size()),
						" organizations with no asset"));
			}

			for (Organization organization : organizations) {
				try {
					_organizationLocalService.updateAsset(
						organization.getUserId(), organization, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for organization ",
								String.valueOf(
									organization.getOrganizationId()),
								": ", e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for organizations");
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationServiceVerifyProcess.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}