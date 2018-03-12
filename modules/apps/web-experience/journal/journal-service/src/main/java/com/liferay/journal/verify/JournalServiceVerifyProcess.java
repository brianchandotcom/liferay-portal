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

package com.liferay.journal.verify;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.internal.verify.model.JournalArticleResourceVerifiableModel;
import com.liferay.journal.internal.verify.model.JournalArticleVerifiableModel;
import com.liferay.journal.internal.verify.model.JournalFeedVerifiableModel;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.verify.VerifyLayout;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyResourcePermissions;
import com.liferay.portal.verify.VerifyUUID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.journal.service"},
	service = {JournalServiceVerifyProcess.class, VerifyProcess.class}
)
public class JournalServiceVerifyProcess extends VerifyLayout {

	@Override
	protected void doVerify() throws Exception {
		verifyArticleAssets();
		verifyArticleContents();
		verifyArticleExpirationDate();
		verifyArticleLayouts();
		verifyArticleStructures();
		verifyContentSearch();
		verifyFolderAssets();
		verifyPermissions();
		verifyResourcedModels();
		verifyUUIDModels();

		VerifyProcess verifyProcess =
			new JournalServiceSystemEventVerifyProcess(
				_journalArticleLocalService,
				_journalArticleResourceLocalService, _systemEventLocalService);

		verifyProcess.verify();
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {

		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceLocalService(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setSystemEventLocalService(
		SystemEventLocalService systemEventLocalService) {

		_systemEventLocalService = systemEventLocalService;
	}

	protected void updateContentSearch(long groupId, String portletId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select preferences from PortletPreferences inner join " +
					"Layout on PortletPreferences.plid = Layout.plid where " +
						"groupId = ? and portletId = ?")) {

			ps.setLong(1, groupId);
			ps.setString(2, portletId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String xml = rs.getString("preferences");

					PortletPreferences portletPreferences =
						PortletPreferencesFactoryUtil.fromDefaultXML(xml);

					String articleId = portletPreferences.getValue(
						"articleId", null);

					List<JournalContentSearch> contentSearches =
						_journalContentSearchLocalService.
							getArticleContentSearches(groupId, articleId);

					if (contentSearches.isEmpty()) {
						continue;
					}

					JournalContentSearch contentSearch = contentSearches.get(0);

					_journalContentSearchLocalService.updateContentSearch(
						contentSearch.getGroupId(),
						contentSearch.isPrivateLayout(),
						contentSearch.getLayoutId(),
						contentSearch.getPortletId(), articleId, true);
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

	protected void verifyArticleLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			verifyUuid("JournalArticle");
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
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<JournalFolder> folders =
				_journalFolderLocalService.getNoAssetFolders();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + folders.size() + " folders with no asset");
			}

			for (JournalFolder folder : folders) {
				try {
					_journalFolderLocalService.updateAsset(
						folder.getUserId(), folder, null, null, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for folder ",
								String.valueOf(folder.getFolderId()), ": ",
								e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for folders");
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

	protected void verifyResourcedModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_verifyResourcePermissions.verify(
				new JournalArticleVerifiableModel());
			_verifyResourcePermissions.verify(new JournalFeedVerifiableModel());
		}
	}

	protected void verifyUUIDModels() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			VerifyUUID.verify(new JournalArticleResourceVerifiableModel());
			VerifyUUID.verify(new JournalFeedVerifiableModel());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalServiceVerifyProcess.class);

	private AssetEntryLocalService _assetEntryLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;
	private JournalContentSearchLocalService _journalContentSearchLocalService;
	private JournalFolderLocalService _journalFolderLocalService;

	@Reference
	private Portal _portal;

	private ResourceLocalService _resourceLocalService;
	private SystemEventLocalService _systemEventLocalService;
	private final VerifyResourcePermissions _verifyResourcePermissions =
		new VerifyResourcePermissions();

}