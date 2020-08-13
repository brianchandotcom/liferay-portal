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

package com.liferay.knowledge.base.service.http;

import com.liferay.knowledge.base.service.KBArticleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>KBArticleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.knowledge.base.model.KBArticleSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.knowledge.base.model.KBArticle</code>, that is translated to a
 * <code>com.liferay.knowledge.base.model.KBArticleSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class KBArticleServiceSoap {

	public static com.liferay.knowledge.base.model.KBArticleSoap addKBArticle(
			String portletId, long parentResourceClassNameId,
			long parentResourcePrimKey, String title, String urlTitle,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.addKBArticle(
					portletId, parentResourceClassNameId, parentResourcePrimKey,
					title, urlTitle, content, description, sourceURL, sections,
					selectedFileNames, serviceContext);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			deleteKBArticle(long resourcePrimKey)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.deleteKBArticle(resourcePrimKey);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteKBArticles(long groupId, long[] resourcePrimKeys)
		throws RemoteException {

		try {
			KBArticleServiceUtil.deleteKBArticles(groupId, resourcePrimKeys);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteTempAttachment(
			long groupId, long resourcePrimKey, String fileName,
			String tempFolderName)
		throws RemoteException {

		try {
			KBArticleServiceUtil.deleteTempAttachment(
				groupId, resourcePrimKey, fileName, tempFolderName);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			fetchFirstChildKBArticle(long groupId, long parentResourcePrimKey)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.fetchFirstChildKBArticle(
					groupId, parentResourcePrimKey);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			fetchFirstChildKBArticle(
				long groupId, long parentResourcePrimKey, int status)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.fetchFirstChildKBArticle(
					groupId, parentResourcePrimKey, status);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			fetchKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.fetchKBArticleByUrlTitle(
					groupId, kbFolderId, urlTitle);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			fetchLatestKBArticle(long resourcePrimKey, int status)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.fetchLatestKBArticle(
					resourcePrimKey, status);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			fetchLatestKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle, int status)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.fetchLatestKBArticleByUrlTitle(
					groupId, kbFolderId, urlTitle, status);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getAllDescendantKBArticles(
				long groupId, long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getAllDescendantKBArticles(
					groupId, resourcePrimKey, status, orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getGroupKBArticles(
				long groupId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getGroupKBArticles(
					groupId, status, start, end, orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupKBArticlesCount(long groupId, int status)
		throws RemoteException {

		try {
			int returnValue = KBArticleServiceUtil.getGroupKBArticlesCount(
				groupId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap getKBArticle(
			long resourcePrimKey, int version)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.getKBArticle(resourcePrimKey, version);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getKBArticleAndAllDescendantKBArticles(
				long resourcePrimKey, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue =
					KBArticleServiceUtil.getKBArticleAndAllDescendantKBArticles(
						resourcePrimKey, status, orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getKBArticles(
				long groupId, long parentResourcePrimKey, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getKBArticles(
					groupId, parentResourcePrimKey, status, start, end,
					orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getKBArticles(
				long groupId, long[] resourcePrimKeys, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getKBArticles(
					groupId, resourcePrimKeys, status, start, end,
					orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getKBArticles(
				long groupId, long[] resourcePrimKeys, int status,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getKBArticles(
					groupId, resourcePrimKeys, status, orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getKBArticlesCount(
			long groupId, long parentResourcePrimKey, int status)
		throws RemoteException {

		try {
			int returnValue = KBArticleServiceUtil.getKBArticlesCount(
				groupId, parentResourcePrimKey, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getKBArticlesCount(
			long groupId, long[] resourcePrimKeys, int status)
		throws RemoteException {

		try {
			int returnValue = KBArticleServiceUtil.getKBArticlesCount(
				groupId, resourcePrimKeys, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSearchDisplay
			getKBArticleSearchDisplay(
				long groupId, String title, String content, int status,
				java.util.Date startDate, java.util.Date endDate,
				boolean andOperator, int[] curStartValues, int cur, int delta,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticleSearchDisplay
				returnValue = KBArticleServiceUtil.getKBArticleSearchDisplay(
					groupId, title, content, status, startDate, endDate,
					andOperator, curStartValues, cur, delta, orderByComparator);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getKBArticleVersions(
				long groupId, long resourcePrimKey, int status, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getKBArticleVersions(
					groupId, resourcePrimKey, status, start, end,
					orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getKBArticleVersionsCount(
			long groupId, long resourcePrimKey, int status)
		throws RemoteException {

		try {
			int returnValue = KBArticleServiceUtil.getKBArticleVersionsCount(
				groupId, resourcePrimKey, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			getLatestKBArticle(long resourcePrimKey, int status)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.getLatestKBArticle(
					resourcePrimKey, status);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getPreviousAndNextKBArticles(long kbArticleId)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle[] returnValue =
				KBArticleServiceUtil.getPreviousAndNextKBArticles(kbArticleId);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap[]
			getSectionsKBArticles(
				long groupId, String[] sections, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.knowledge.base.model.KBArticle>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.knowledge.base.model.KBArticle>
				returnValue = KBArticleServiceUtil.getSectionsKBArticles(
					groupId, sections, status, start, end, orderByComparator);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSectionsKBArticlesCount(
			long groupId, String[] sections, int status)
		throws RemoteException {

		try {
			int returnValue = KBArticleServiceUtil.getSectionsKBArticlesCount(
				groupId, sections, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String[] getTempAttachmentNames(
			long groupId, String tempFolderName)
		throws RemoteException {

		try {
			String[] returnValue = KBArticleServiceUtil.getTempAttachmentNames(
				groupId, tempFolderName);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void moveKBArticle(
			long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws RemoteException {

		try {
			KBArticleServiceUtil.moveKBArticle(
				resourcePrimKey, parentResourceClassNameId,
				parentResourcePrimKey, priority);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			revertKBArticle(
				long resourcePrimKey, int version,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.revertKBArticle(
					resourcePrimKey, version, serviceContext);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void subscribeGroupKBArticles(long groupId, String portletId)
		throws RemoteException {

		try {
			KBArticleServiceUtil.subscribeGroupKBArticles(groupId, portletId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void subscribeKBArticle(long groupId, long resourcePrimKey)
		throws RemoteException {

		try {
			KBArticleServiceUtil.subscribeKBArticle(groupId, resourcePrimKey);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unsubscribeGroupKBArticles(
			long groupId, String portletId)
		throws RemoteException {

		try {
			KBArticleServiceUtil.unsubscribeGroupKBArticles(groupId, portletId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unsubscribeKBArticle(long resourcePrimKey)
		throws RemoteException {

		try {
			KBArticleServiceUtil.unsubscribeKBArticle(resourcePrimKey);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.knowledge.base.model.KBArticleSoap
			updateKBArticle(
				long resourcePrimKey, String title, String content,
				String description, String sourceURL, String[] sections,
				String[] selectedFileNames, long[] removeFileEntryIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.knowledge.base.model.KBArticle returnValue =
				KBArticleServiceUtil.updateKBArticle(
					resourcePrimKey, title, content, description, sourceURL,
					sections, selectedFileNames, removeFileEntryIds,
					serviceContext);

			return com.liferay.knowledge.base.model.KBArticleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(KBArticleServiceSoap.class);

}