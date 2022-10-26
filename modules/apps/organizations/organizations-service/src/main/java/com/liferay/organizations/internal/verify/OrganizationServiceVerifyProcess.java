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

package com.liferay.organizations.internal.verify;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PropsUtil;
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
	property = "verify.process.name=com.liferay.organizations.service",
	service = VerifyProcess.class
)
public class OrganizationServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-157670"))) {
			return;
		}

		ExecutorService executorService = Executors.newFixedThreadPool(3);

		List<Future<Void>> futures = executorService.invokeAll(
			Arrays.asList(
				this::updateOrganizationAssets,
				this::updateOrganizationAssetEntries));

		executorService.shutdown();

		UnsafeConsumer.accept(futures, Future::get, Exception.class);
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

			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(sb.toString());
				ResultSet resultSet = preparedStatement1.executeQuery()) {

				try (PreparedStatement preparedStatement2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection,
							"update AssetEntry set classUuid = ? where " +
								"classPK = ? and classNameId = ?")) {

					while (resultSet.next()) {
						long classPK = resultSet.getLong("classPK");

						String uuid = resultSet.getString("uuid");

						preparedStatement2.setString(1, uuid);

						preparedStatement2.setLong(2, classPK);
						preparedStatement2.setLong(3, classNameId);

						preparedStatement2.addBatch();
					}

					preparedStatement2.executeBatch();
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
						"Processing ", organizations.size(),
						" organizations with no asset"));
			}

			for (Organization organization : organizations) {
				try {
					_organizationLocalService.updateAsset(
						organization.getUserId(), organization, null, null);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for organization ",
								organization.getOrganizationId(), ": ",
								exception.getMessage()));
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

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.organizations.service)(release.schema.version>=1.0.1))"
	)
	private Release _release;

}