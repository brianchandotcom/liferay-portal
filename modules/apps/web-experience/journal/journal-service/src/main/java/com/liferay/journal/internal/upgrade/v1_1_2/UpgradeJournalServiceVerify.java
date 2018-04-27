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

package com.liferay.journal.internal.upgrade.v1_1_2;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 * @author Shinn Lok
 */
public class UpgradeJournalServiceVerify extends UpgradeProcess {

	public UpgradeJournalServiceVerify(
		AssetEntryLocalService assetEntryLocalService,
		DDMStructureLocalService ddmStructureLocalService, Portal portal,
		ResourceLocalService resourceLocalService,
		SystemEventLocalService systemEventLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
		_systemEventLocalService = systemEventLocalService;

		_journalArticleClassNameId = _portal.getClassNameId(
			JournalArticle.class);
	}

	@Override
	protected void doUpgrade() throws Exception {
		verifyArticleAssets();
		verifyArticleContents();
		verifyArticleExpirationDate();
		verifyArticleStructures();
		verifyContentSearch();
		verifyFolderAssets();
		verifyPermissions();

		verifyJournalArticleDeleteSystemEvents();
	}

	protected void updateContentSearch(long groupId, String portletId)
		throws Exception {

		try (PreparedStatement selectPreferencesPS =
				connection.prepareStatement(
					"select preferences from PortletPreferences inner join " +
						"Layout on PortletPreferences.plid = Layout.plid " +
							"where groupId = ? and portletId = ?");
			PreparedStatement selectSearchPS = connection.prepareStatement(
				"select companyId, privateLayout, layoutId, portletId from " +
					"JournalContentSearch where JournalContentSearch.groupId " +
						"= ? AND JournalContentSearch.articleId = ?");
			PreparedStatement deleteSearchPS = connection.prepareStatement(
				"DELETE FROM JournalContentSearch WHERE" +
					"JournalContentSearch.groupId = ? AND" +
						"JournalContentSearch.articleId = ?");
			PreparedStatement insertSearchPS = connection.prepareStatement(
				"INSERT INTO JournalContentSearch(contentSearchId, " +
					"companyId, groupId, privateLayout, layoutId, portletId, " +
						"articleId) values (?, ?, ?, ?, ?, ?, ?)")) {

			selectPreferencesPS.setLong(1, groupId);
			selectPreferencesPS.setString(2, portletId);

			try (ResultSet preferencesRS = selectPreferencesPS.executeQuery()) {
				while (preferencesRS.next()) {
					String xml = preferencesRS.getString("preferences");

					PortletPreferences portletPreferences =
						PortletPreferencesFactoryUtil.fromDefaultXML(xml);

					String articleId = portletPreferences.getValue(
						"articleId", null);

					selectSearchPS.setLong(1, groupId);
					selectSearchPS.setString(2, articleId);

					try (ResultSet searchRS = selectSearchPS.executeQuery()) {
						if (searchRS.next()) {
							long companyId = searchRS.getLong("companyId");
							boolean privateLayout = searchRS.getBoolean(
								"privateLayout");
							long layoutId = searchRS.getLong("layoutId");
							String journalContentSearchPortletId =
								searchRS.getString("portletId");

							deleteSearchPS.setLong(1, groupId);
							deleteSearchPS.setString(2, articleId);

							deleteSearchPS.executeUpdate();

							insertSearchPS.setLong(1, increment());
							insertSearchPS.setLong(2, companyId);
							insertSearchPS.setBoolean(3, privateLayout);
							insertSearchPS.setLong(4, layoutId);
							insertSearchPS.setString(
								5, journalContentSearchPortletId);
							insertSearchPS.setString(6, articleId);

							insertSearchPS.executeUpdate();
						}
					}
				}
			}
		}
	}

	protected void updateElement(long groupId, Element element) {
		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateElement(groupId, dynamicElementElement);
		}

		String type = element.attributeValue("type");

		if (type.equals("link_to_layout")) {
			updateLinkToLayoutElements(groupId, element);
		}
	}

	protected void updateExpirationDate(
			long groupId, String articleId, Timestamp expirationDate,
			int status)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update JournalArticle set expirationDate = ? where groupId " +
					"= ? and articleId = ? and status = ?")) {

			ps.setTimestamp(1, expirationDate);
			ps.setLong(2, groupId);
			ps.setString(3, articleId);
			ps.setInt(4, status);

			ps.executeUpdate();
		}
	}

	protected void updateLinkToLayoutElements(long groupId, Element element) {
		Element dynamicContentElement = element.element("dynamic-content");

		Node node = dynamicContentElement.node(0);

		String text = node.getText();

		if (!text.isEmpty() && !text.endsWith(StringPool.AT + groupId)) {
			node.setText(
				dynamicContentElement.getStringValue() + StringPool.AT +
					groupId);
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

	protected void verifyArticleAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps = connection.prepareStatement(
					StringBundler.concat(
						"select * from JournalArticle left join AssetEntry on ",
						"(AssetEntry.classNameId = ",
						String.valueOf(_journalArticleClassNameId),
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

	protected void verifyArticleContents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement selectPS = connection.prepareStatement(
				"select id_, groupId, content from JournalArticle where " +
					"(content like '%link_to_layout%') and DDMStructureKey " +
						"!= ''");
			PreparedStatement updatePS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update JournalArticle set content = ? where id_ = ?"));
			ResultSet rs = selectPS.executeQuery()) {

			while (rs.next()) {
				long id = rs.getLong("id_");
				long groupId = rs.getLong("groupId");
				String content = rs.getString("content");

				try {
					Document document = SAXReaderUtil.read(content);

					Element rootElement = document.getRootElement();

					for (Element element : rootElement.elements()) {
						updateElement(groupId, element);
					}

					updatePS.setString(1, document.asXML());
					updatePS.setLong(2, id);

					updatePS.addBatch();
				}
				catch (Exception e) {
					_log.error("Unable to update content for article " + id, e);
				}
			}

			updatePS.executeBatch();
		}
	}

	protected void verifyArticleExpirationDate() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long companyId = CompanyThreadLocal.getCompanyId();

			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);

			if (!journalServiceConfiguration.
					expireAllArticleVersionsEnabled()) {

				return;
			}

			StringBundler sb = new StringBundler(15);

			sb.append("select JournalArticle.* from JournalArticle left join ");
			sb.append("JournalArticle tempJournalArticle on ");
			sb.append("(JournalArticle.groupId = tempJournalArticle.groupId) ");
			sb.append("and (JournalArticle.articleId = ");
			sb.append("tempJournalArticle.articleId) and ");
			sb.append("(JournalArticle.version < tempJournalArticle.version) ");
			sb.append("and (JournalArticle.status = ");
			sb.append("tempJournalArticle.status) where ");
			sb.append("(JournalArticle.classNameId = ");
			sb.append(JournalArticleConstants.CLASSNAME_ID_DEFAULT);
			sb.append(") and (tempJournalArticle.version is null) and ");
			sb.append("(JournalArticle.expirationDate is not null) and ");
			sb.append("(JournalArticle.status = ");
			sb.append(WorkflowConstants.STATUS_APPROVED);
			sb.append(")");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					String articleId = rs.getString("articleId");
					Timestamp expirationDate = rs.getTimestamp(
						"expirationDate");
					int status = rs.getInt("status");

					updateExpirationDate(
						groupId, articleId, expirationDate, status);
				}
			}
		}
	}

	protected void verifyArticleStructures() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement selectPS = connection.prepareStatement(
				"select id_, groupId, DDMStructureKey from JournalArticle " +
					"where DDMStructureKey != null and DDMStructureKey != ''");
			PreparedStatement updatePS =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update JournalArticle set DDMStructureKey = '', " +
							"DDMTemplateKey = '' where id_ = ?"));
			ResultSet rs = selectPS.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				String ddmStructureKey = rs.getString("DDMStructureKey");

				DDMStructure ddmStructure =
					_ddmStructureLocalService.fetchStructure(
						_portal.getSiteGroupId(groupId),
						_journalArticleClassNameId, ddmStructureKey, true);

				if (ddmStructure == null) {
					updatePS.setLong(1, rs.getLong("id_"));

					updatePS.addBatch();
				}
			}

			updatePS.executeBatch();
		}
	}

	protected void verifyContentSearch() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select groupId, portletId from JournalContentSearch group " +
					"by groupId, portletId having count(groupId) > 1 and " +
						"count(portletId) > 1");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				String portletId = rs.getString("portletId");

				updateContentSearch(groupId, portletId);
			}
		}
	}

	protected void verifyFolderAssets() throws Exception {
		long classNameId = _portal.getClassNameId(JournalFolder.class);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select JournalFolder.userId, JournalFolder.groupId, ",
					"JournalFolder.createDate, JournalFolder.modifiedDate, ",
					"JournalFolder.folderId, JournalFolder.uuid_, ",
					"JournalFolder.name, JournalFolder.description from ",
					"JournalFolder left join AssetEntry on ",
					"(AssetEntry.classNameId = ", String.valueOf(classNameId),
					") AND (AssetEntry.classPK = JournalFolder.folderId) ",
					"where AssetEntry.classPK IS NULL"));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long userId = rs.getLong("userId");
				long groupId = rs.getLong("groupId");
				Date createDate = rs.getTimestamp("createDate");
				Date modifiedDate = rs.getTimestamp("modifiedDate");
				long folderId = rs.getLong("folderId");
				String uuid = rs.getString("uuid_");
				String name = rs.getString("name");
				String description = rs.getString("description");

				try {
					_assetEntryLocalService.updateEntry(
						userId, groupId, createDate, modifiedDate,
						JournalFolder.class.getName(), folderId, uuid, 0, null,
						null, true, true, null, null, createDate, null,
						ContentTypes.TEXT_PLAIN, name, description, null, null,
						null, 0, 0, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for folder ",
								String.valueOf(folderId), ": ",
								e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for folders");
			}
		}
	}

	protected void verifyJournalArticleDeleteSystemEvents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			DynamicQuery dynamicQuery = _systemEventLocalService.dynamicQuery();

			Property classNameIdProperty = PropertyFactoryUtil.forName(
				"classNameId");

			dynamicQuery.add(
				classNameIdProperty.eq(_journalArticleClassNameId));

			Property typeProperty = PropertyFactoryUtil.forName("type");

			dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

			List<SystemEvent> systemEvents =
				_systemEventLocalService.dynamicQuery(dynamicQuery);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Processing ", String.valueOf(systemEvents.size()),
						" delete system events for journal articles"));
			}

			for (SystemEvent systemEvent : systemEvents) {
				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject(
						systemEvent.getExtraData());

				if (extraDataJSONObject.has("uuid") ||
					!extraDataJSONObject.has("version")) {

					continue;
				}

				String articleId = null;

				try (PreparedStatement ps = connection.prepareStatement(
						"select articleId from JournalArticleResource where " +
							"JournalArticleResource.uuid_ = ? AND " +
								"JournalArticleResource.groupId = ?")) {

					ps.setString(1, systemEvent.getClassUuid());
					ps.setLong(2, systemEvent.getGroupId());

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							articleId = rs.getString(1);
						}
					}
				}

				if (articleId == null) {
					continue;
				}

				try (PreparedStatement ps = connection.prepareStatement(
						"select 1 from JournalArticle where groupId = ? and " +
							"articleId = ? and version = ? and status = ?")) {

					ps.setLong(1, systemEvent.getGroupId());
					ps.setString(2, articleId);
					ps.setDouble(3, extraDataJSONObject.getDouble("version"));
					ps.setInt(4, WorkflowConstants.STATUS_IN_TRASH);

					try (ResultSet rs = ps.executeQuery()) {
						if (rs.next()) {
							_systemEventLocalService.deleteSystemEvent(
								systemEvent);
						}
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delete system events verified for journal articles");
			}
		}
	}

	protected void verifyPermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select JournalArticle.companyId, ",
					"JournalArticle.resourcePrimKey from JournalArticle left ",
					"join ResourcePermission on (ResourcePermission.companyId ",
					"= JournalArticle.companyId) and (ResourcePermission.name ",
					"= '", JournalArticle.class.getName(),
					"') and (ResourcePermission.primKeyId = ",
					"JournalArticle.resourcePrimKey) and ",
					"(ResourcePermission.scope = ",
					String.valueOf(ResourceConstants.SCOPE_INDIVIDUAL),
					") where ResourcePermission.primKey is null"));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long resourcePrimKey = rs.getLong("resourcePrimKey");

				_resourceLocalService.addResources(
					companyId, 0, 0, JournalArticle.class.getName(),
					resourcePrimKey, false, false, false);
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalServiceVerify.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final long _journalArticleClassNameId;
	private final Portal _portal;
	private final ResourceLocalService _resourceLocalService;
	private final SystemEventLocalService _systemEventLocalService;

	private static class JournalArticleResourceVerifiableModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "resourcePrimKey";
		}

		@Override
		public String getTableName() {
			return "JournalArticleResource";
		}

	}

	private static class JournalFeedVerifiableModel
		implements VerifiableUUIDModel {

		@Override
		public String getPrimaryKeyColumnName() {
			return "id_";
		}

		@Override
		public String getTableName() {
			return "JournalFeed";
		}

	}

}