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

package com.liferay.knowledge.base.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KBArticleService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleService
 * @generated
 */
public class KBArticleServiceWrapper
	implements KBArticleService, ServiceWrapper<KBArticleService> {

	public KBArticleServiceWrapper(KBArticleService kbArticleService) {
		_kbArticleService = kbArticleService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KBArticleServiceUtil} to access the kb article remote service. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle addKBArticle(
			String portletId, long parentResourceClassNameId,
			long parentResourcePrimKey, String title, String urlTitle,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.addKBArticle(
			portletId, parentResourceClassNameId, parentResourcePrimKey, title,
			urlTitle, content, description, sourceURL, sections,
			selectedFileNames, serviceContext);
	}

	@Override
	public int addKBArticlesMarkdown(
			long groupId, long parentKBFolderId, String fileName,
			boolean prioritizeByNumericalPrefix,
			java.io.InputStream inputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.addKBArticlesMarkdown(
			groupId, parentKBFolderId, fileName, prioritizeByNumericalPrefix,
			inputStream, serviceContext);
	}

	@Override
	public void addTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName, java.io.InputStream inputStream,
			String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.addTempAttachment(
			groupId, resourcePrimKey, fileName, tempFolderName, inputStream,
			mimeType);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.deleteKBArticle(resourcePrimKey);
	}

	@Override
	public void deleteKBArticles(long groupId, long[] resourcePrimKeys)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.deleteKBArticles(groupId, resourcePrimKeys);
	}

	@Override
	public void deleteTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.deleteTempAttachment(
			groupId, resourcePrimKey, fileName, tempFolderName);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchFirstChildKBArticle(
		long groupId, long parentResourcePrimKey) {

		return _kbArticleService.fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchFirstChildKBArticle(
		long groupId, long parentResourcePrimKey, int status) {

		return _kbArticleService.fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.fetchKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchLatestKBArticle(
			long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.fetchLatestKBArticle(resourcePrimKey, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle
			fetchLatestKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.fetchLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
			getAllDescendantKBArticles(
				long groupId, long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getAllDescendantKBArticles(
			groupId, resourcePrimKey, status, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getGroupKBArticles(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleService.getGroupKBArticles(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getGroupKBArticlesCount(long groupId, int status) {
		return _kbArticleService.getGroupKBArticlesCount(groupId, status);
	}

	@Override
	public String getGroupKBArticlesRSS(
			int status, int rssDelta, String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getGroupKBArticlesRSS(
			status, rssDelta, rssDisplayStyle, rssFormat, themeDisplay);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle getKBArticle(
			long resourcePrimKey, int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getKBArticle(resourcePrimKey, version);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
			getKBArticleAndAllDescendantKBArticles(
				long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getKBArticleAndAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	@Override
	public String getKBArticleRSS(
			long resourcePrimKey, int status, int rssDelta,
			String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getKBArticleRSS(
			resourcePrimKey, status, rssDelta, rssDisplayStyle, rssFormat,
			themeDisplay);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long parentResourcePrimKey, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleService.getKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long[] resourcePrimKeys, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleService.getKBArticles(
			groupId, resourcePrimKeys, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long[] resourcePrimKeys, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleService.getKBArticles(
			groupId, resourcePrimKeys, status, orderByComparator);
	}

	@Override
	public int getKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return _kbArticleService.getKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	@Override
	public int getKBArticlesCount(
		long groupId, long[] resourcePrimKeys, int status) {

		return _kbArticleService.getKBArticlesCount(
			groupId, resourcePrimKeys, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticleSearchDisplay
			getKBArticleSearchDisplay(
				long groupId, String title, String content, int status,
				java.util.Date startDate, java.util.Date endDate,
				boolean andOperator, int[] curStartValues, int cur, int delta,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getKBArticleSearchDisplay(
			groupId, title, content, status, startDate, endDate, andOperator,
			curStartValues, cur, delta, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleVersions(
			long groupId, long resourcePrimKey, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleService.getKBArticleVersions(
			groupId, resourcePrimKey, status, start, end, orderByComparator);
	}

	@Override
	public int getKBArticleVersionsCount(
		long groupId, long resourcePrimKey, int status) {

		return _kbArticleService.getKBArticleVersionsCount(
			groupId, resourcePrimKey, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle getLatestKBArticle(
			long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getLatestKBArticle(resourcePrimKey, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kbArticleService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle[]
			getPreviousAndNextKBArticles(long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getPreviousAndNextKBArticles(kbArticleId);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getSectionsKBArticles(
			long groupId, String[] sections, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleService.getSectionsKBArticles(
			groupId, sections, status, start, end, orderByComparator);
	}

	@Override
	public int getSectionsKBArticlesCount(
		long groupId, String[] sections, int status) {

		return _kbArticleService.getSectionsKBArticlesCount(
			groupId, sections, status);
	}

	@Override
	public String[] getTempAttachmentNames(long groupId, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.getTempAttachmentNames(
			groupId, tempFolderName);
	}

	@Override
	public void moveKBArticle(
			long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.moveKBArticle(
			resourcePrimKey, parentResourceClassNameId, parentResourcePrimKey,
			priority);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle revertKBArticle(
			long resourcePrimKey, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.revertKBArticle(
			resourcePrimKey, version, serviceContext);
	}

	@Override
	public void subscribeGroupKBArticles(long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.subscribeGroupKBArticles(groupId, portletId);
	}

	@Override
	public void subscribeKBArticle(long groupId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.subscribeKBArticle(groupId, resourcePrimKey);
	}

	@Override
	public void unsubscribeGroupKBArticles(long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.unsubscribeGroupKBArticles(groupId, portletId);
	}

	@Override
	public void unsubscribeKBArticle(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.unsubscribeKBArticle(resourcePrimKey);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle updateKBArticle(
			long resourcePrimKey, String title, String content,
			String description, String sourceURL, String[] sections,
			String[] selectedFileNames, long[] removeFileEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleService.updateKBArticle(
			resourcePrimKey, title, content, description, sourceURL, sections,
			selectedFileNames, removeFileEntryIds, serviceContext);
	}

	@Override
	public void updateKBArticlesPriorities(
			long groupId,
			java.util.Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleService.updateKBArticlesPriorities(
			groupId, resourcePrimKeyToPriorityMap);
	}

	@Override
	public KBArticleService getWrappedService() {
		return _kbArticleService;
	}

	@Override
	public void setWrappedService(KBArticleService kbArticleService) {
		_kbArticleService = kbArticleService;
	}

	private KBArticleService _kbArticleService;

}