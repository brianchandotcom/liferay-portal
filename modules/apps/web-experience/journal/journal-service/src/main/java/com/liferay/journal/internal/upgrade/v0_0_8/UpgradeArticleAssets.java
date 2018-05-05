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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Preston Crary
 * @author Alberto Chaparro
 */
public class UpgradeArticleAssets extends UpgradeProcess {

	public UpgradeArticleAssets(
		AssetEntryLocalService assetEntryLocalService,
		DDMStructureLocalService ddmStructureLocalService, Portal portal) {

		_assetEntryLocalService = assetEntryLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateArticleAssets();
	}

	protected void updateArticleAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps = connection.prepareStatement(
					StringBundler.concat(
						"select * from JournalArticle left join AssetEntry on ",
						"(AssetEntry.classNameId = ",
						String.valueOf(
							_portal.getClassNameId(
								_CLASS_NAME_JOURNAL_ARTICLE)),
						") AND (AssetEntry.classPK = ",
						"JournalArticle.resourcePrimKey) where ",
						"AssetEntry.classPK is null"));
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String uuid = rs.getString("uuid_");
					long id = rs.getLong("id_");
					long resourcePrimKey = rs.getLong("resourcePrimKey");
					long groupId = rs.getLong("groupId");
					long userId = rs.getLong("userId");
					Date createDate = rs.getTimestamp("createDate");
					Date modifiedDate = rs.getTimestamp("modifiedDate");
					long classNameId = rs.getLong("classNameId");
					String articleId = rs.getString("articleId");
					double version = rs.getDouble("version");
					String ddmStructureKey = rs.getString("DDMStructureKey");
					String defaultLanguageId = rs.getString(
						"defaultLanguageId");
					String layoutUuid = rs.getString("layoutUuid");
					Date displayDate = rs.getTimestamp("displayDate");
					Date expirationDate = rs.getTimestamp("expirationDate");
					boolean indexable = rs.getBoolean("indexable");
					int status = rs.getInt("status");

					try {
						_updateAsset(
							uuid, id, resourcePrimKey, groupId, userId,
							createDate, modifiedDate, classNameId, articleId,
							version, ddmStructureKey, defaultLanguageId,
							layoutUuid, displayDate, expirationDate, indexable,
							status);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Unable to update asset for article ",
									String.valueOf(id), ": ", e.getMessage()));
						}
					}
				}
			}

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

	private void _updateAsset(
			String uuid, long id, long resourcePrimKey, long groupId,
			long userId, Date createDate, Date modifiedDate, long classNameId,
			String articleId, double version, String ddmStructureKey,
			String defaultLanguageId, String layoutUuid, Date displayDate,
			Date expirationDate, boolean indexable, int status)
		throws Exception {

		boolean visible = false;

		if (status == WorkflowConstants.STATUS_APPROVED) {
			visible = true;
		}

		if (classNameId != JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
			visible = false;
		}

		boolean addDraftAssetEntry = false;

		if ((status != WorkflowConstants.STATUS_APPROVED) &&
			(version != JournalArticleConstants.VERSION_DEFAULT)) {

			try (PreparedStatement ps = connection.prepareStatement(
					"select 1 from JournalArticle where groupId = ? and " +
						"articleId = ? and status in (?, ?, ?)")) {

				ps.setLong(1, groupId);
				ps.setString(2, articleId);
				ps.setInt(3, WorkflowConstants.STATUS_APPROVED);
				ps.setInt(4, WorkflowConstants.STATUS_EXPIRED);
				ps.setInt(5, WorkflowConstants.STATUS_SCHEDULED);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						addDraftAssetEntry = true;
					}
				}
			}
		}

		Map<String, String> titleMap = new HashMap<>();
		Map<String, String> descriptionMap = new HashMap<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select languageId, title, description from " +
					"JournalArticleLocalization where articlePK = " + id);
			ResultSet rs = ps.executeQuery()) {

			String languageId = rs.getString("languageId");

			titleMap.put(languageId, rs.getString("title"));
			descriptionMap.put(languageId, rs.getString("description"));
		}

		String title = LocalizationUtil.getXml(
			titleMap, defaultLanguageId, "Title");
		String description = LocalizationUtil.getXml(
			descriptionMap, defaultLanguageId, "Description");

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			groupId, classNameId, ddmStructureKey, true);

		long classTypeId = ddmStructure.getStructureId();

		if (addDraftAssetEntry) {
			_assetEntryLocalService.updateEntry(
				userId, groupId, createDate, modifiedDate,
				JournalArticle.class.getName(), id, uuid, classTypeId, null,
				null, indexable, false, null, null, null, expirationDate,
				ContentTypes.TEXT_HTML, title, description, description, null,
				layoutUuid, 0, 0, null);
		}
		else {
			try (PreparedStatement ps = connection.prepareStatement(
					"select uuid_ from JournalArticleResource where " +
						"resourcePrimKey = " + resourcePrimKey);
				ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					uuid = rs.getString("uuid_");
				}

				Date publishDate = null;

				if (status == WorkflowConstants.STATUS_APPROVED) {
					publishDate = displayDate;
				}

				_assetEntryLocalService.updateEntry(
					userId, groupId, createDate, modifiedDate,
					JournalArticle.class.getName(), resourcePrimKey, uuid,
					classTypeId, null, null, indexable, visible, null, null,
					publishDate, expirationDate, ContentTypes.TEXT_HTML, title,
					description, description, null, layoutUuid, 0, 0, null);
			}
		}
	}

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeArticleAssets.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final Portal _portal;

}