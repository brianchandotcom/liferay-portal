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
 * Provides the local service utility for KBArticle. This utility wraps
 * <code>com.liferay.knowledge.base.service.impl.KBArticleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleLocalService
 * @generated
 */
public class KBArticleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KBArticleLocalServiceUtil} to access the kb article local service. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.portal.kernel.repository.model.FileEntry
			addAttachment(
				long userId, long resourcePrimKey, String fileName,
				java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAttachment(
			userId, resourcePrimKey, fileName, inputStream, mimeType);
	}

	/**
	 * Adds the kb article to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticle the kb article
	 * @return the kb article that was added
	 */
	public static com.liferay.knowledge.base.model.KBArticle addKBArticle(
		com.liferay.knowledge.base.model.KBArticle kbArticle) {

		return getService().addKBArticle(kbArticle);
	}

	public static com.liferay.knowledge.base.model.KBArticle addKBArticle(
			long userId, long parentResourceClassNameId,
			long parentResourcePrimKey, String title, String urlTitle,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKBArticle(
			userId, parentResourceClassNameId, parentResourcePrimKey, title,
			urlTitle, content, description, sourceURL, sections,
			selectedFileNames, serviceContext);
	}

	public static void addKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addKBArticleResources(
			kbArticle, addGroupPermissions, addGuestPermissions);
	}

	public static void addKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addKBArticleResources(kbArticle, modelPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addKBArticleResources(KBArticle, ModelPermissions)}
	 */
	@Deprecated
	public static void addKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addKBArticleResources(
			kbArticle, groupPermissions, guestPermissions);
	}

	public static void addKBArticleResources(
			long kbArticleId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addKBArticleResources(
			kbArticleId, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addKBArticleResources(KBArticle, ModelPermissions)}
	 */
	@Deprecated
	public static void addKBArticleResources(
			long kbArticleId, String[] groupPermissions,
			String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addKBArticleResources(
			kbArticleId, groupPermissions, guestPermissions);
	}

	public static int addKBArticlesMarkdown(
			long userId, long groupId, long parentKbFolderId, String fileName,
			boolean prioritizeByNumericalPrefix,
			java.io.InputStream inputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKBArticlesMarkdown(
			userId, groupId, parentKbFolderId, fileName,
			prioritizeByNumericalPrefix, inputStream, serviceContext);
	}

	public static void addTempAttachment(
			long groupId, long userId, String fileName, String tempFolderName,
			java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().addTempAttachment(
			groupId, userId, fileName, tempFolderName, inputStream, mimeType);
	}

	/**
	 * Creates a new kb article with the primary key. Does not add the kb article to the database.
	 *
	 * @param kbArticleId the primary key for the new kb article
	 * @return the new kb article
	 */
	public static com.liferay.knowledge.base.model.KBArticle createKBArticle(
		long kbArticleId) {

		return getService().createKBArticle(kbArticleId);
	}

	public static void deleteGroupKBArticles(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteGroupKBArticles(groupId);
	}

	/**
	 * Deletes the kb article from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticle the kb article
	 * @return the kb article that was removed
	 * @throws PortalException
	 */
	public static com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			com.liferay.knowledge.base.model.KBArticle kbArticle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKBArticle(kbArticle);
	}

	/**
	 * Deletes the kb article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticleId the primary key of the kb article
	 * @return the kb article that was removed
	 * @throws PortalException if a kb article with the primary key could not be found
	 */
	public static com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKBArticle(kbArticleId);
	}

	public static void deleteKBArticles(
			long groupId, long parentResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKBArticles(groupId, parentResourcePrimKey);
	}

	public static void deleteKBArticles(long[] resourcePrimKeys)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKBArticles(resourcePrimKeys);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteTempAttachment(
			long groupId, long userId, String fileName, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTempAttachment(
			groupId, userId, fileName, tempFolderName);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBArticleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBArticleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchFirstChildKBArticle(long groupId, long parentResourcePrimKey) {

		return getService().fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey);
	}

	public static com.liferay.knowledge.base.model.KBArticle fetchKBArticle(
		long kbArticleId) {

		return getService().fetchKBArticle(kbArticleId);
	}

	public static com.liferay.knowledge.base.model.KBArticle fetchKBArticle(
		long resourcePrimKey, long groupId, int version) {

		return getService().fetchKBArticle(resourcePrimKey, groupId, version);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle) {

		return getService().fetchKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchKBArticleByUrlTitle(
			long groupId, String kbFolderUrlTitle, String urlTitle) {

		return getService().fetchKBArticleByUrlTitle(
			groupId, kbFolderUrlTitle, urlTitle);
	}

	/**
	 * Returns the kb article matching the UUID and group.
	 *
	 * @param uuid the kb article's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb article, or <code>null</code> if a matching kb article could not be found
	 */
	public static com.liferay.knowledge.base.model.KBArticle
		fetchKBArticleByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchKBArticleByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchLatestKBArticle(long resourcePrimKey, int status) {

		return getService().fetchLatestKBArticle(resourcePrimKey, status);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchLatestKBArticle(long resourcePrimKey, long groupId) {

		return getService().fetchLatestKBArticle(resourcePrimKey, groupId);
	}

	public static com.liferay.knowledge.base.model.KBArticle
		fetchLatestKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle, int status) {

		return getService().fetchLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getAllDescendantKBArticles(
			long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getCompanyKBArticles(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getCompanyKBArticles(
			companyId, status, start, end, orderByComparator);
	}

	public static int getCompanyKBArticlesCount(long companyId, int status) {
		return getService().getCompanyKBArticlesCount(companyId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kb article with the primary key.
	 *
	 * @param kbArticleId the primary key of the kb article
	 * @return the kb article
	 * @throws PortalException if a kb article with the primary key could not be found
	 */
	public static com.liferay.knowledge.base.model.KBArticle getKBArticle(
			long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticle(kbArticleId);
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
					orderByComparator) {

		return getService().getKBArticleAndAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getKBArticleAndAllDescendantKBArticles(long, int,
	 OrderByComparator)}
	 */
	@Deprecated
	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleAndAllDescendants(
			long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticleAndAllDescendants(
			resourcePrimKey, status, orderByComparator);
	}

	public static com.liferay.knowledge.base.model.KBArticle
			getKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	public static com.liferay.knowledge.base.model.KBArticle
			getKBArticleByUrlTitle(
				long groupId, String kbFolderUrlTitle, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticleByUrlTitle(
			groupId, kbFolderUrlTitle, urlTitle);
	}

	/**
	 * Returns the kb article matching the UUID and group.
	 *
	 * @param uuid the kb article's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb article
	 * @throws PortalException if a matching kb article could not be found
	 */
	public static com.liferay.knowledge.base.model.KBArticle
			getKBArticleByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKBArticleByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the kb articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.knowledge.base.model.impl.KBArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb articles
	 * @param end the upper bound of the range of kb articles (not inclusive)
	 * @return the range of kb articles
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(int start, int end) {

		return getService().getKBArticles(start, end);
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
			long[] resourcePrimKeys, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticles(
			resourcePrimKeys, status, orderByComparator);
	}

	/**
	 * Returns all the kb articles matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb articles
	 * @param companyId the primary key of the company
	 * @return the matching kb articles, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticlesByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getKBArticlesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of kb articles matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb articles
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of kb articles
	 * @param end the upper bound of the range of kb articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching kb articles, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticlesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticlesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of kb articles.
	 *
	 * @return the number of kb articles
	 */
	public static int getKBArticlesCount() {
		return getService().getKBArticlesCount();
	}

	public static int getKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return getService().getKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleVersions(
			long resourcePrimKey, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getKBArticleVersions(
			resourcePrimKey, status, start, end, orderByComparator);
	}

	public static int getKBArticleVersionsCount(
		long resourcePrimKey, int status) {

		return getService().getKBArticleVersionsCount(resourcePrimKey, status);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBFolderKBArticles(long groupId, long kbFolderId) {

		return getService().getKBFolderKBArticles(groupId, kbFolderId);
	}

	public static int getKBFolderKBArticlesCount(
		long groupId, long kbFolderId, int status) {

		return getService().getKBFolderKBArticlesCount(
			groupId, kbFolderId, status);
	}

	public static com.liferay.knowledge.base.model.KBArticle getLatestKBArticle(
			long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLatestKBArticle(resourcePrimKey, status);
	}

	public static com.liferay.knowledge.base.model.KBArticle
			getLatestKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<? extends com.liferay.portal.kernel.model.PersistedModel>
				getPersistedModel(long resourcePrimKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(resourcePrimKey);
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #getKBArticles(long,
	 long, int, int, int, OrderByComparator)}
	 */
	@Deprecated
	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getSiblingKBArticles(
			long groupId, long parentResourcePrimKey, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().getSiblingKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #getKBArticlesCount(long, long, int)}
	 */
	@Deprecated
	public static int getSiblingKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return getService().getSiblingKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	public static String[] getTempAttachmentNames(
			long groupId, long userId, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTempAttachmentNames(
			groupId, userId, tempFolderName);
	}

	public static void moveKBArticle(
			long userId, long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveKBArticle(
			userId, resourcePrimKey, parentResourceClassNameId,
			parentResourcePrimKey, priority);
	}

	public static com.liferay.knowledge.base.model.KBArticle revertKBArticle(
			long userId, long resourcePrimKey, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().revertKBArticle(
			userId, resourcePrimKey, version, serviceContext);
	}

	public static java.util.List<com.liferay.knowledge.base.model.KBArticle>
		search(
			long groupId, String title, String content, int status,
			java.util.Date startDate, java.util.Date endDate,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return getService().search(
			groupId, title, content, status, startDate, endDate, andOperator,
			start, end, orderByComparator);
	}

	public static void subscribeGroupKBArticles(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribeGroupKBArticles(userId, groupId);
	}

	public static void subscribeKBArticle(
			long userId, long groupId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().subscribeKBArticle(userId, groupId, resourcePrimKey);
	}

	public static void unsubscribeGroupKBArticles(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribeGroupKBArticles(userId, groupId);
	}

	public static void unsubscribeKBArticle(long userId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().unsubscribeKBArticle(userId, resourcePrimKey);
	}

	/**
	 * Updates the kb article in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticle the kb article
	 * @return the kb article that was updated
	 */
	public static com.liferay.knowledge.base.model.KBArticle updateKBArticle(
		com.liferay.knowledge.base.model.KBArticle kbArticle) {

		return getService().updateKBArticle(kbArticle);
	}

	public static com.liferay.knowledge.base.model.KBArticle updateKBArticle(
			long userId, long resourcePrimKey, String title, String content,
			String description, String sourceURL, String[] sections,
			String[] selectedFileNames, long[] removeFileEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateKBArticle(
			userId, resourcePrimKey, title, content, description, sourceURL,
			sections, selectedFileNames, removeFileEntryIds, serviceContext);
	}

	public static void updateKBArticleAsset(
			long userId, com.liferay.knowledge.base.model.KBArticle kbArticle,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateKBArticleAsset(
			userId, kbArticle, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	public static void updateKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateKBArticleResources(
			kbArticle, groupPermissions, guestPermissions);
	}

	public static void updateKBArticlesPriorities(
			java.util.Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateKBArticlesPriorities(resourcePrimKeyToPriorityMap);
	}

	public static void updatePriority(long resourcePrimKey, double priority) {
		getService().updatePriority(resourcePrimKey, priority);
	}

	public static com.liferay.knowledge.base.model.KBArticle updateStatus(
			long userId, long resourcePrimKey, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, resourcePrimKey, status, serviceContext);
	}

	public static void updateViewCount(
			long userId, long resourcePrimKey, int viewCount)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateViewCount(userId, resourcePrimKey, viewCount);
	}

	public static KBArticleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KBArticleLocalService, KBArticleLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KBArticleLocalService.class);

		ServiceTracker<KBArticleLocalService, KBArticleLocalService>
			serviceTracker =
				new ServiceTracker
					<KBArticleLocalService, KBArticleLocalService>(
						bundle.getBundleContext(), KBArticleLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}