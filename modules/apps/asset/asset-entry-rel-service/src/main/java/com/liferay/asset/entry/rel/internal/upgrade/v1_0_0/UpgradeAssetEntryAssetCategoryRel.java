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

package com.liferay.asset.entry.rel.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeAssetEntryAssetCategoryRel extends UpgradeProcess {

	protected void addAssetEntryAssetCategoryRels() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select entryId, categoryId from AssetEntries_AssetCategories");
			ResultSet rs = ps.executeQuery()) {

			List<InsertAssetEntryAssetCategoryRelCallable>
				insertAssetEntryAssetCategoryRelCallables = new ArrayList<>();

			while (rs.next()) {
				long assetEntryId = rs.getLong("entryId");
				long assetCategoryId = rs.getLong("categoryId");

				InsertAssetEntryAssetCategoryRelCallable
					insertAssetEntryAssetCategoryRelCallable =
						new InsertAssetEntryAssetCategoryRelCallable(
							assetEntryId, assetCategoryId);

				insertAssetEntryAssetCategoryRelCallables.add(
					insertAssetEntryAssetCategoryRelCallable);
			}

			ExecutorService executorService = Executors.newWorkStealingPool();

			List<Future<Boolean>> futures = executorService.invokeAll(
				insertAssetEntryAssetCategoryRelCallables);

			executorService.shutdown();

			for (Future<Boolean> future : futures) {
				boolean success = GetterUtil.get(future.get(), true);

				if (!success) {
					throw new UpgradeException(
						"Unable to add relationships between asset entries " +
							"and asset categories");
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		addAssetEntryAssetCategoryRels();
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeAssetEntryAssetCategoryRel.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeAssetEntryAssetCategoryRel.class);

	private class InsertAssetEntryAssetCategoryRelCallable
		implements Callable<Boolean> {

		public InsertAssetEntryAssetCategoryRelCallable(
			long assetEntryId, long assetCategoryId) {

			_assetEntryId = assetEntryId;
			_assetCategoryId = assetCategoryId;
		}

		@Override
		public Boolean call() throws Exception {
			try (Connection connection = DataAccess.getConnection()) {
				StringBundler sb = new StringBundler(9);

				sb.append("insert into AssetEntryAssetCategoryRel (");
				sb.append("assetEntryAssetCategoryRelId, assetEntryId, ");
				sb.append("assetCategoryId) values (");
				sb.append(increment());
				sb.append(", ");
				sb.append(_assetEntryId);
				sb.append(", ");
				sb.append(_assetCategoryId);
				sb.append(")");

				runSQL(connection, sb.toString());
			}
			catch (Exception exception) {
				_log.error(
					StringBundler.concat(
						"Unable to add relationship for asset entry ",
						_assetEntryId, " and asset category ",
						_assetCategoryId),
					exception);

				return false;
			}

			return true;
		}

		private final long _assetCategoryId;
		private final long _assetEntryId;

	}

}