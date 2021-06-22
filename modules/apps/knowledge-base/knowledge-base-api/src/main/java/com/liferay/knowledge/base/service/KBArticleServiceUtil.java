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

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for KBArticle. This utility wraps
 * <code>com.liferay.knowledge.base.service.impl.KBArticleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleService
 * @generated
 */
public class KBArticleServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static KBArticle addKBArticle(
			String externalReferenceCode, String portletId,
			long parentResourceClassNameId, long parentResourcePrimKey,
			String title, String urlTitle, String content, String description,
			String sourceURL, String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKBArticle(
			externalReferenceCode, portletId, parentResourceClassNameId,
			parentResourcePrimKey, title, urlTitle, content, description,
			sourceURL, sections, selectedFileNames, serviceContext);
	}

	public static int addKBArticlesMarkdown(
			long groupId, long parentKBFolderId, String fileName,
			boolean prioritizeByNumericalPrefix, InputStream inputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKBArticlesMarkdown(
			groupId, parentKBFolderId, fileName, prioritizeByNumericalPrefix,
			inputStream, serviceContext);
	}

	public static void addTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName, InputStream inputStream, String mimeType)
		throws PortalException {

		getService().addTempAttachment(
			groupId, resourcePrimKey, fileName, tempFolderName, inputStream,
			mimeType);
	}

	public static KBArticle deleteKBArticle(long resourcePrimKey)
		throws PortalException {

		return getService().deleteKBArticle(resourcePrimKey);
	}

	public static void deleteKBArticles(long groupId, long[] resourcePrimKeys)
		throws PortalException {

		getService().deleteKBArticles(groupId, resourcePrimKeys);
	}

	public static void deleteTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName)
		throws PortalException {

		getService().deleteTempAttachment(
			groupId, resourcePrimKey, fileName, tempFolderName);
	}

	public static KBArticle fetchFirstChildKBArticle(
		long groupId, long parentResourcePrimKey) {

		return getService().fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey);
	}

	public static KBArticle fetchFirstChildKBArticle(
		long groupId, long parentResourcePrimKey, int status) {

		return getService().fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey, status);
	}

	public static KBArticle fetchKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle)
		throws PortalException {

		return getService().fetchKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	public static KBArticle fetchLatestKBArticle(
			long resourcePrimKey, int status)
		throws PortalException {

		return getService().fetchLatestKBArticle(resourcePrimKey, status);
	}

	public static KBArticle fetchLatestKBArticleByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchLatestKBArticleByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	public static KBArticle fetchLatestKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle, int status)
		throws PortalException {

		return getService().fetchLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	public static List<KBArticle> getAllDescendantKBArticles(
			long groupId, long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		return getService().getAllDescendantKBArticles(
			groupId, resourcePrimKey, status, orderByComparator);
	}

	public static List<KBArticle> getGroupKBArticles(
		long groupId, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		return getService().getGroupKBArticles(
			groupId, status, start, end, orderByComparator);
	}

	public static int getGroupKBArticlesCount(long groupId, int status) {
		return getService().getGroupKBArticlesCount(groupId, status);
	}

	public static String getGroupKBArticlesRSS(
			int status, int rssDelta, String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws PortalException {

		return getService().getGroupKBArticlesRSS(
			status, rssDelta, rssDisplayStyle, rssFormat, themeDisplay);
	}

	public static KBArticle getKBArticle(long resourcePrimKey, int version)
		throws PortalException {

		return getService().getKBArticle(resourcePrimKey, version);
	}

	public static List<KBArticle> getKBArticleAndAllDescendantKBArticles(
			long resourcePrimKey, int status,
			OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		return getService().getKBArticleAndAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	public static String getKBArticleRSS(
			long resourcePrimKey, int status, int rssDelta,
			String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws PortalException {

		return getService().getKBArticleRSS(
			resourcePrimKey, status, rssDelta, rssDisplayStyle, rssFormat,
			themeDisplay);
	}

	public static List<KBArticle> getKBArticles(
		long groupId, long parentResourcePrimKey, int status, int start,
		int end, OrderByComparator<KBArticle> orderByComparator) {

		return getService().getKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	public static List<KBArticle> getKBArticles(
		long groupId, long[] resourcePrimKeys, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		return getService().getKBArticles(
			groupId, resourcePrimKeys, status, start, end, orderByComparator);
	}

	public static List<KBArticle> getKBArticles(
		long groupId, long[] resourcePrimKeys, int status,
		OrderByComparator<KBArticle> orderByComparator) {

		return getService().getKBArticles(
			groupId, resourcePrimKeys, status, orderByComparator);
	}

	public static int getKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return getService().getKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	public static int getKBArticlesCount(
		long groupId, long[] resourcePrimKeys, int status) {

		return getService().getKBArticlesCount(
			groupId, resourcePrimKeys, status);
	}

	public static com.liferay.knowledge.base.model.KBArticleSearchDisplay
			getKBArticleSearchDisplay(
				long groupId, String title, String content, int status,
				java.util.Date startDate, java.util.Date endDate,
				boolean andOperator, int[] curStartValues, int cur, int delta,
				OrderByComparator<KBArticle> orderByComparator)
		throws PortalException {

		return getService().getKBArticleSearchDisplay(
			groupId, title, content, status, startDate, endDate, andOperator,
			curStartValues, cur, delta, orderByComparator);
	}

	public static List<KBArticle> getKBArticleVersions(
		long groupId, long resourcePrimKey, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		return getService().getKBArticleVersions(
			groupId, resourcePrimKey, status, start, end, orderByComparator);
	}

	public static int getKBArticleVersionsCount(
		long groupId, long resourcePrimKey, int status) {

		return getService().getKBArticleVersionsCount(
			groupId, resourcePrimKey, status);
	}

	public static KBArticle getLatestKBArticle(long resourcePrimKey, int status)
		throws PortalException {

		return getService().getLatestKBArticle(resourcePrimKey, status);
	}

	public static KBArticle getLatestKBArticleByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		return getService().getLatestKBArticleByExternalReferenceCode(
			groupId, externalReferenceCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static KBArticle[] getPreviousAndNextKBArticles(long kbArticleId)
		throws PortalException {

		return getService().getPreviousAndNextKBArticles(kbArticleId);
	}

	public static List<KBArticle> getSectionsKBArticles(
		long groupId, String[] sections, int status, int start, int end,
		OrderByComparator<KBArticle> orderByComparator) {

		return getService().getSectionsKBArticles(
			groupId, sections, status, start, end, orderByComparator);
	}

	public static int getSectionsKBArticlesCount(
		long groupId, String[] sections, int status) {

		return getService().getSectionsKBArticlesCount(
			groupId, sections, status);
	}

	public static String[] getTempAttachmentNames(
			long groupId, String tempFolderName)
		throws PortalException {

		return getService().getTempAttachmentNames(groupId, tempFolderName);
	}

	public static void moveKBArticle(
			long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws PortalException {

		getService().moveKBArticle(
			resourcePrimKey, parentResourceClassNameId, parentResourcePrimKey,
			priority);
	}

	public static KBArticle revertKBArticle(
			long resourcePrimKey, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().revertKBArticle(
			resourcePrimKey, version, serviceContext);
	}

	public static void subscribeGroupKBArticles(long groupId, String portletId)
		throws PortalException {

		getService().subscribeGroupKBArticles(groupId, portletId);
	}

	public static void subscribeKBArticle(long groupId, long resourcePrimKey)
		throws PortalException {

		getService().subscribeKBArticle(groupId, resourcePrimKey);
	}

	public static void unsubscribeGroupKBArticles(
			long groupId, String portletId)
		throws PortalException {

		getService().unsubscribeGroupKBArticles(groupId, portletId);
	}

	public static void unsubscribeKBArticle(long resourcePrimKey)
		throws PortalException {

		getService().unsubscribeKBArticle(resourcePrimKey);
	}

	public static KBArticle updateKBArticle(
			long resourcePrimKey, String title, String content,
			String description, String sourceURL, String[] sections,
			String[] selectedFileNames, long[] removeFileEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateKBArticle(
			resourcePrimKey, title, content, description, sourceURL, sections,
			selectedFileNames, removeFileEntryIds, serviceContext);
	}

	public static void updateKBArticlesPriorities(
			long groupId, Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws PortalException {

		getService().updateKBArticlesPriorities(
			groupId, resourcePrimKeyToPriorityMap);
	}

	public static KBArticleService getService() {
		return _service;
	}

	private static volatile KBArticleService _service;

}