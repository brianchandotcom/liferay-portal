/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.test.util.search;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleSearchFixture {

	public JournalArticleSearchFixture(
		DDMStructureLocalService ddmStructureLocalService,
		JournalArticleLocalService journalArticleLocalService, Portal portal) {

		_ddmStructureLocalService = ddmStructureLocalService;
		_journalArticleLocalService = journalArticleLocalService;
		_portal = portal;
	}

	public JournalArticle addArticle(
		JournalArticleBlueprint journalArticleBlueprint) {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_portal.getSiteGroupId(journalArticleBlueprint.getGroupId()),
			_portal.getClassNameId(JournalArticle.class.getName()),
			"BASIC-WEB-CONTENT", true);

		return addArticle(
			journalArticleBlueprint, ddmStructure.getStructureId(),
			"BASIC-WEB-CONTENT");
	}

	public JournalArticle addArticle(
		JournalArticleBlueprint journalArticleBlueprint, long ddmStructureId,
		String ddmTemplateKey) {

		long userId = journalArticleBlueprint.getUserId();
		long groupId = journalArticleBlueprint.getGroupId();
		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		Map<Locale, String> titleMap = journalArticleBlueprint.getTitleMap();
		Map<Locale, String> descriptionMap =
			journalArticleBlueprint.getDescriptionMap();
		String contentString = journalArticleBlueprint.getContentString();

		ServiceContext serviceContext = getServiceContext(
			journalArticleBlueprint);

		serviceContext.setAssetCategoryIds(
			journalArticleBlueprint.getAssetCategoryIds());
		serviceContext.setExpandoBridgeAttributes(
			journalArticleBlueprint.getExpandoBridgeAttributes());

		if (journalArticleBlueprint.isWorkflowEnabled()) {
			serviceContext.setWorkflowAction(
				journalArticleBlueprint.getWorkflowAction());
		}

		JournalArticle journalArticle = addArticle(
			userId, groupId, folderId, titleMap, descriptionMap, contentString,
			ddmStructureId, ddmTemplateKey, serviceContext);

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	public List<JournalArticle> getJournalArticles() {
		return _journalArticles;
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public JournalArticle updateArticle(JournalArticle journalArticle)
		throws Exception {

		journalArticle = _journalArticleLocalService.updateArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), journalArticle.getTitleMap(),
			journalArticle.getDescriptionMap(), journalArticle.getContent(),
			journalArticle.getLayoutUuid(),
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	protected JournalArticle addArticle(
		long userId, long groupId, long folderId, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap, String contentString,
		long ddmStructureId, String ddmTemplateKey,
		ServiceContext serviceContext) {

		try {
			return _journalArticleLocalService.addArticle(
				null, userId, groupId, folderId, titleMap, descriptionMap,
				contentString, ddmStructureId, ddmTemplateKey, serviceContext);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected ServiceContext getServiceContext(
		JournalArticleBlueprint journalArticleBlueprint) {

		if (journalArticleBlueprint.getServiceContext() != null) {
			return journalArticleBlueprint.getServiceContext();
		}

		try {
			return ServiceContextTestUtil.getServiceContext(
				journalArticleBlueprint.getGroupId(),
				journalArticleBlueprint.getUserId());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	protected ServiceContext getServiceContext(long groupId, long userId) {
		try {
			return ServiceContextTestUtil.getServiceContext(groupId, userId);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final List<JournalArticle> _journalArticles = new ArrayList<>();
	private final Portal _portal;

}