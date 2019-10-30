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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KBArticleServiceUtil} to access the kb article remote service. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.knowledge.base.model.KBArticle addKBArticle(
			String portletId, long parentResourceClassNameId,
			long parentResourcePrimKey, String title, String urlTitle,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKBArticle(
			portletId, parentResourceClassNameId, parentResourcePrimKey, title,
			urlTitle, content, description, sourceURL, sections,
			selectedFileNames, serviceContext);
	}

	public static int addKBArticlesMarkdown(
			long groupId, long parentKBFolderId, String fileName,
			boolean prioritizeByNumericalPrefix,
			java.io.InputStream inputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKBArticlesMarkdown(
			groupId, parentKBFolderId, fileName, prioritizeByNumericalPrefix,
			inputStream, serviceContext);
	}

	public static void addTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName, java.io.InputStream inputStream,
			String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addTempAttachment(
			groupId, resourcePrimKey, fileName, tempFolderName, inputStream,
			mimeType);
	}

	public static com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKBArticle(resourcePrimKey);
	}

	public static void deleteKBArticles(long groupId, long[] resourcePrimKeys)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKBArticles(groupId, resourcePrimKeys);
	}

	public static void deleteTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTempAttachment(
			groupId, resourcePrimKey, fileName, tempFolderName);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchFirstChildKBArticle(long groupId, long parentResourcePrimKey) {

		return getService().fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchFirstChildKBArticle(
			long groupId, long parentResourcePrimKey, int status) {

		return getService().fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey, status);
	}

	public static com.liferay.knowledge.base.model.KBArticle
			fetchKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	public static com.liferay.knowledge.base.model.KBArticle
			fetchLatestKBArticle(long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchLatestKBArticle(resourcePrimKey, status);
	}

	public static com.liferay.knowledge.base.model.KBArticle
			fetchLatestKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
			getAllDescendantKBArticles(
				long groupId, long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAllDescendantKBArticles(
			groupId, resourcePrimKey, status, orderByComparator);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getGroupKBArticles(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getGroupKBArticles(
			groupId, status, start, end, orderByComparator);
	}

	public static int getGroupKBArticlesCount(long groupId, int status) {
		return getService().getGroupKBArticlesCount(groupId, status);
	}

	public static String getGroupKBArticlesRSS(
			int status, int rssDelta, String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getGroupKBArticlesRSS(
			status, rssDelta, rssDisplayStyle, rssFormat, themeDisplay);
	}

	public static com.liferay.knowledge.base.model.KBArticle getKBArticle(
			long resourcePrimKey, int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticle(resourcePrimKey, version);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
			getKBArticleAndAllDescendantKBArticles(
				long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticleAndAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	public static String getKBArticleRSS(
			long resourcePrimKey, int status, int rssDelta,
			String rssDisplayStyle, String rssFormat,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticleRSS(
			resourcePrimKey, status, rssDelta, rssDisplayStyle, rssFormat,
			themeDisplay);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long parentResourcePrimKey, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long[] resourcePrimKeys, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticles(
			groupId, resourcePrimKeys, status, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long[] resourcePrimKeys, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

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
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticleSearchDisplay(
			groupId, title, content, status, startDate, endDate, andOperator,
			curStartValues, cur, delta, orderByComparator);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleVersions(
			long groupId, long resourcePrimKey, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticleVersions(
			groupId, resourcePrimKey, status, start, end, orderByComparator);
	}

	public static int getKBArticleVersionsCount(
		long groupId, long resourcePrimKey, int status) {

		return getService().getKBArticleVersionsCount(
			groupId, resourcePrimKey, status);
	}

	public static com.liferay.knowledge.base.model.KBArticle getLatestKBArticle(
			long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLatestKBArticle(resourcePrimKey, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.knowledge.base.model.KBArticle[]
			getPreviousAndNextKBArticles(long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPreviousAndNextKBArticles(kbArticleId);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getSectionsKBArticles(
			long groupId, String[] sections, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

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
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempAttachmentNames(groupId, tempFolderName);
	}

	public static void moveKBArticle(
			long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveKBArticle(
			resourcePrimKey, parentResourceClassNameId, parentResourcePrimKey,
			priority);
	}

	public static com.liferay.knowledge.base.model.KBArticle revertKBArticle(
			long resourcePrimKey, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().revertKBArticle(
			resourcePrimKey, version, serviceContext);
	}

	public static void subscribeGroupKBArticles(long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribeGroupKBArticles(groupId, portletId);
	}

	public static void subscribeKBArticle(long groupId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribeKBArticle(groupId, resourcePrimKey);
	}

	public static void unsubscribeGroupKBArticles(
			long groupId, String portletId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribeGroupKBArticles(groupId, portletId);
	}

	public static void unsubscribeKBArticle(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribeKBArticle(resourcePrimKey);
	}

	public static com.liferay.knowledge.base.model.KBArticle updateKBArticle(
			long resourcePrimKey, String title, String content,
			String description, String sourceURL, String[] sections,
			String[] selectedFileNames, long[] removeFileEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateKBArticle(
			resourcePrimKey, title, content, description, sourceURL, sections,
			selectedFileNames, removeFileEntryIds, serviceContext);
	}

	public static void updateKBArticlesPriorities(
			long groupId,
			java.util.Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateKBArticlesPriorities(
			groupId, resourcePrimKeyToPriorityMap);
	}

	public static KBArticleService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KBArticleService, KBArticleService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KBArticleService.class);

		ServiceTracker<KBArticleService, KBArticleService> serviceTracker =
			new ServiceTracker<KBArticleService, KBArticleService>(
				bundle.getBundleContext(), KBArticleService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}