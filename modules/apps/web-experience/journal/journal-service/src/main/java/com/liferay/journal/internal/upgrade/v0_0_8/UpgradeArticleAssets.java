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

package com.liferay.journal.internal.upgrade.v0_0_8;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 * @author Alberto Chaparro
 */
public class UpgradeArticleAssets extends UpgradeProcess {

	public UpgradeArticleAssets(AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateArticleAssets();
	}

	protected void updateArticleAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps = connection.prepareStatement(
					StringBundler.concat(
						"select resourcePrimKey, indexable from ",
						"JournalArticle where version = ",
						String.valueOf(JournalArticleConstants.VERSION_DEFAULT),
						" and status = ",
						String.valueOf(WorkflowConstants.STATUS_DRAFT)));
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long resourcePrimKey = rs.getLong("resourcePrimKey");
					boolean indexable = rs.getBoolean("indexable");

					AssetEntry assetEntry = _assetEntryLocalService.getEntry(
						JournalArticle.class.getName(), resourcePrimKey);

					_assetEntryLocalService.updateEntry(
						assetEntry.getClassName(), assetEntry.getClassPK(),
						null, null, indexable, assetEntry.isVisible());
				}
			}

			updateResourcePrimKey();
		}
	}

	protected void updateResourcePrimKey() throws Exception {
		try (PreparedStatement selectArticlePS = connection.prepareStatement(
				"select distinct companyId, groupId, articleId from " +
					"JournalArticle where resourcePrimKey <= 0");
			PreparedStatement selectResourcePS = connection.prepareStatement(
				"select resourcePrimKey from JournalArticleResource where " +
					"groupId = ? and articleId = ?");
			PreparedStatement insertResourcePS = connection.prepareStatement(
				"insert into JournalArticleResource (resourcePrimKey, " +
					"companyId, groupId, articleId) values (?, ?, ?, ?)");
			PreparedStatement updateArticlePS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update JournalArticle set resourcePrimKey = ? where " +
							"groupId = ? and articleId = ?"));
			ResultSet articleRS = selectArticlePS.executeQuery()) {

			while (articleRS.next()) {
				long companyId = articleRS.getLong("companyId");
				long groupId = articleRS.getLong("groupId");
				String articleId = articleRS.getString("articleId");

				selectResourcePS.setLong(1, groupId);
				selectResourcePS.setString(2, articleId);

				try (ResultSet resourceRS = selectResourcePS.executeQuery()) {
					long resourcePrimKey = 0;

					if (resourceRS.next()) {
						resourcePrimKey = resourceRS.getLong("resourcePrimKey");
					}
					else {
						resourcePrimKey = increment();

						insertResourcePS.setLong(1, resourcePrimKey);

						insertResourcePS.setLong(2, companyId);
						insertResourcePS.setLong(3, groupId);
						insertResourcePS.setString(4, articleId);

						insertResourcePS.executeUpdate();
					}

					updateArticlePS.setLong(1, resourcePrimKey);
					updateArticlePS.setLong(2, groupId);
					updateArticlePS.setString(3, articleId);

					updateArticlePS.addBatch();
				}
			}

			updateArticlePS.executeBatch();
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;

}