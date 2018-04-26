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
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
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
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 * @author Shinn Lok
 */
public class UpgradeJournalServiceVerify extends UpgradeProcess {

	public UpgradeJournalServiceVerify(
		AssetEntryLocalService assetEntryLocalService,
		JournalArticleLocalService journalArticleLocalService,
		Portal portal, ResourceLocalService resourceLocalService,
		SystemEventLocalService systemEventLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_journalArticleLocalService = journalArticleLocalService;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
		_systemEventLocalService = systemEventLocalService;
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

	protected void updateResourcePrimKey() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_journalArticleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property resourcePrimKey = PropertyFactoryUtil.forName(
						"resourcePrimKey");

					dynamicQuery.add(resourcePrimKey.le(0L));
				}

			});

		if (_log.isDebugEnabled()) {
			long count = actionableDynamicQuery.performCount();

			_log.debug(
				"Processing " + count +
					" default article versions in draft mode");
		}

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<JournalArticle>() {

				@Override
				public void performAction(JournalArticle article)
					throws PortalException {

					long groupId = article.getGroupId();
					String articleId = article.getArticleId();
					double version = article.getVersion();

					_journalArticleLocalService.checkArticleResourcePrimKey(
						groupId, articleId, version);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void verifyArticleAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<JournalArticle> journalArticles =
				_journalArticleLocalService.getNoAssetArticles();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + journalArticles.size() +
						" articles with no asset");
			}

			for (JournalArticle journalArticle : journalArticles) {
				try {
					_journalArticleLocalService.updateAsset(
						journalArticle.getUserId(), journalArticle, null, null,
						null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for article ",
								String.valueOf(journalArticle.getId()), ": ",
								e.getMessage()));
					}
				}
			}

			ActionableDynamicQuery actionableDynamicQuery =
				_journalArticleLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				new ActionableDynamicQuery.AddCriteriaMethod() {

					@Override
					public void addCriteria(DynamicQuery dynamicQuery) {
						Property versionProperty = PropertyFactoryUtil.forName(
							"version");

						dynamicQuery.add(
							versionProperty.eq(
								JournalArticleConstants.VERSION_DEFAULT));

						Property statusProperty = PropertyFactoryUtil.forName(
							"status");

						dynamicQuery.add(
							statusProperty.eq(WorkflowConstants.STATUS_DRAFT));
					}

				});

			if (_log.isDebugEnabled()) {
				long count = actionableDynamicQuery.performCount();

				_log.debug(
					"Processing " + count +
						" default article versions in draft mode");
			}

			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.
					PerformActionMethod<JournalArticle>() {

					@Override
					public void performAction(JournalArticle article)
						throws PortalException {

						AssetEntry assetEntry =
							_assetEntryLocalService.fetchEntry(
								JournalArticle.class.getName(),
								article.getResourcePrimKey());

						boolean listable =
							_journalArticleLocalService.isListable(article);

						_assetEntryLocalService.updateEntry(
							assetEntry.getClassName(), assetEntry.getClassPK(),
							null, null, listable, assetEntry.isVisible());
					}

				});

			actionableDynamicQuery.performActions();

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for articles");
			}

			updateResourcePrimKey();
		}
	}

	protected void verifyArticleContents() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select id_ from JournalArticle where (content like " +
					"'%link_to_layout%') and DDMStructureKey != ''");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long id = rs.getLong("id_");

				JournalArticle article = _journalArticleLocalService.getArticle(
					id);

				try {
					Document document = SAXReaderUtil.read(
						article.getContent());

					Element rootElement = document.getRootElement();

					for (Element element : rootElement.elements()) {
						updateElement(article.getGroupId(), element);
					}

					article.setContent(document.asXML());

					_journalArticleLocalService.updateJournalArticle(article);
				}
				catch (Exception e) {
					_log.error(
						"Unable to update content for article " +
							article.getId(),
						e);
				}
			}
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

	protected void verifyArticleStructures() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_journalArticleLocalService.getActionableDynamicQuery();

			if (_log.isDebugEnabled()) {
				long count = actionableDynamicQuery.performCount();

				_log.debug(
					StringBundler.concat(
						"Processing ", String.valueOf(count),
						" articles for invalid structures and dynamic ",
						"elements"));
			}

			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.
					PerformActionMethod<JournalArticle>() {

					@Override
					public void performAction(JournalArticle article) {
						try {
							_journalArticleLocalService.checkStructure(
								article.getGroupId(), article.getArticleId(),
								article.getVersion());
						}
						catch (NoSuchStructureException nsse) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Removing reference to missing structure " +
										"for article " + article.getId());
							}

							article.setDDMStructureKey(StringPool.BLANK);
							article.setDDMTemplateKey(StringPool.BLANK);

							_journalArticleLocalService.updateJournalArticle(
								article);
						}
						catch (Exception e) {
							_log.error(
								"Unable to check the structure for article " +
									article.getId(),
								e);
						}
					}

				});

			actionableDynamicQuery.performActions();
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
				classNameIdProperty.eq(
					_portal.getClassNameId(JournalArticle.class)));

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

				JournalArticle journalArticle =
					_journalArticleLocalService.fetchArticle(
						systemEvent.getGroupId(), articleId,
						extraDataJSONObject.getDouble("version"));

				if ((journalArticle == null) || journalArticle.isInTrash()) {
					continue;
				}

				_systemEventLocalService.deleteSystemEvent(systemEvent);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Delete system events verified for journal articles");
			}
		}
	}

	protected void verifyPermissions() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<JournalArticle> articles =
				_journalArticleLocalService.getNoPermissionArticles();

			for (JournalArticle article : articles) {
				_resourceLocalService.addResources(
					article.getCompanyId(), 0, 0,
					JournalArticle.class.getName(),
					article.getResourcePrimKey(), false, false, false);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalServiceVerify.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;
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