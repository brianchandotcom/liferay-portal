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
 * Provides a wrapper for {@link KBArticleLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleLocalService
 * @generated
 */
public class KBArticleLocalServiceWrapper
	implements KBArticleLocalService, ServiceWrapper<KBArticleLocalService> {

	public KBArticleLocalServiceWrapper(
		KBArticleLocalService kbArticleLocalService) {

		_kbArticleLocalService = kbArticleLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KBArticleLocalServiceUtil} to access the kb article local service. Add custom service methods to <code>com.liferay.knowledge.base.service.impl.KBArticleLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry addAttachment(
			long userId, long resourcePrimKey, String fileName,
			java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.addAttachment(
			userId, resourcePrimKey, fileName, inputStream, mimeType);
	}

	/**
	 * Adds the kb article to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticle the kb article
	 * @return the kb article that was added
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle addKBArticle(
		com.liferay.knowledge.base.model.KBArticle kbArticle) {

		return _kbArticleLocalService.addKBArticle(kbArticle);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle addKBArticle(
			long userId, long parentResourceClassNameId,
			long parentResourcePrimKey, String title, String urlTitle,
			String content, String description, String sourceURL,
			String[] sections, String[] selectedFileNames,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.addKBArticle(
			userId, parentResourceClassNameId, parentResourcePrimKey, title,
			urlTitle, content, description, sourceURL, sections,
			selectedFileNames, serviceContext);
	}

	@Override
	public void addKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.addKBArticleResources(
			kbArticle, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.addKBArticleResources(
			kbArticle, modelPermissions);
	}

	@Override
	public void addKBArticleResources(
			long kbArticleId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.addKBArticleResources(
			kbArticleId, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public int addKBArticlesMarkdown(
			long userId, long groupId, long parentKbFolderId, String fileName,
			boolean prioritizeByNumericalPrefix,
			java.io.InputStream inputStream,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.addKBArticlesMarkdown(
			userId, groupId, parentKbFolderId, fileName,
			prioritizeByNumericalPrefix, inputStream, serviceContext);
	}

	@Override
	public void addTempAttachment(
			long groupId, long userId, String fileName, String tempFolderName,
			java.io.InputStream inputStream, String mimeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.addTempAttachment(
			groupId, userId, fileName, tempFolderName, inputStream, mimeType);
	}

	/**
	 * Creates a new kb article with the primary key. Does not add the kb article to the database.
	 *
	 * @param kbArticleId the primary key for the new kb article
	 * @return the new kb article
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle createKBArticle(
		long kbArticleId) {

		return _kbArticleLocalService.createKBArticle(kbArticleId);
	}

	@Override
	public void deleteGroupKBArticles(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.deleteGroupKBArticles(groupId);
	}

	/**
	 * Deletes the kb article from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticle the kb article
	 * @return the kb article that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			com.liferay.knowledge.base.model.KBArticle kbArticle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.deleteKBArticle(kbArticle);
	}

	/**
	 * Deletes the kb article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticleId the primary key of the kb article
	 * @return the kb article that was removed
	 * @throws PortalException if a kb article with the primary key could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle deleteKBArticle(
			long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.deleteKBArticle(kbArticleId);
	}

	@Override
	public void deleteKBArticles(long groupId, long parentResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.deleteKBArticles(groupId, parentResourcePrimKey);
	}

	@Override
	public void deleteKBArticles(long[] resourcePrimKeys)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.deleteKBArticles(resourcePrimKeys);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteTempAttachment(
			long groupId, long userId, String fileName, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.deleteTempAttachment(
			groupId, userId, fileName, tempFolderName);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kbArticleLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _kbArticleLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _kbArticleLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _kbArticleLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _kbArticleLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _kbArticleLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchFirstChildKBArticle(
		long groupId, long parentResourcePrimKey) {

		return _kbArticleLocalService.fetchFirstChildKBArticle(
			groupId, parentResourcePrimKey);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchKBArticle(
		long kbArticleId) {

		return _kbArticleLocalService.fetchKBArticle(kbArticleId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchKBArticle(
		long resourcePrimKey, long groupId, int version) {

		return _kbArticleLocalService.fetchKBArticle(
			resourcePrimKey, groupId, version);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchKBArticleByUrlTitle(
		long groupId, long kbFolderId, String urlTitle) {

		return _kbArticleLocalService.fetchKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchKBArticleByUrlTitle(
		long groupId, String kbFolderUrlTitle, String urlTitle) {

		return _kbArticleLocalService.fetchKBArticleByUrlTitle(
			groupId, kbFolderUrlTitle, urlTitle);
	}

	/**
	 * Returns the kb article matching the UUID and group.
	 *
	 * @param uuid the kb article's UUID
	 * @param groupId the primary key of the group
	 * @return the matching kb article, or <code>null</code> if a matching kb article could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle
		fetchKBArticleByUuidAndGroupId(String uuid, long groupId) {

		return _kbArticleLocalService.fetchKBArticleByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchLatestKBArticle(
		long resourcePrimKey, int status) {

		return _kbArticleLocalService.fetchLatestKBArticle(
			resourcePrimKey, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle fetchLatestKBArticle(
		long resourcePrimKey, long groupId) {

		return _kbArticleLocalService.fetchLatestKBArticle(
			resourcePrimKey, groupId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle
		fetchLatestKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle, int status) {

		return _kbArticleLocalService.fetchLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _kbArticleLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getAllDescendantKBArticles(
			long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getCompanyKBArticles(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getCompanyKBArticles(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public int getCompanyKBArticlesCount(long companyId, int status) {
		return _kbArticleLocalService.getCompanyKBArticlesCount(
			companyId, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _kbArticleLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getGroupKBArticles(
			long groupId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getGroupKBArticles(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public int getGroupKBArticlesCount(long groupId, int status) {
		return _kbArticleLocalService.getGroupKBArticlesCount(groupId, status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _kbArticleLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kb article with the primary key.
	 *
	 * @param kbArticleId the primary key of the kb article
	 * @return the kb article
	 * @throws PortalException if a kb article with the primary key could not be found
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle getKBArticle(
			long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getKBArticle(kbArticleId);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle getKBArticle(
			long resourcePrimKey, int version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getKBArticle(resourcePrimKey, version);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleAndAllDescendantKBArticles(
			long resourcePrimKey, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getKBArticleAndAllDescendantKBArticles(
			resourcePrimKey, status, orderByComparator);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle getKBArticleByUrlTitle(
			long groupId, long kbFolderId, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle getKBArticleByUrlTitle(
			long groupId, String kbFolderUrlTitle, String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getKBArticleByUrlTitle(
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
	@Override
	public com.liferay.knowledge.base.model.KBArticle
			getKBArticleByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getKBArticleByUuidAndGroupId(
			uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(int start, int end) {

		return _kbArticleLocalService.getKBArticles(start, end);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long groupId, long parentResourcePrimKey, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getKBArticles(
			groupId, parentResourcePrimKey, status, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticles(
			long[] resourcePrimKeys, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getKBArticles(
			resourcePrimKeys, status, orderByComparator);
	}

	/**
	 * Returns all the kb articles matching the UUID and company.
	 *
	 * @param uuid the UUID of the kb articles
	 * @param companyId the primary key of the company
	 * @return the matching kb articles, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticlesByUuidAndCompanyId(String uuid, long companyId) {

		return _kbArticleLocalService.getKBArticlesByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticlesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getKBArticlesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of kb articles.
	 *
	 * @return the number of kb articles
	 */
	@Override
	public int getKBArticlesCount() {
		return _kbArticleLocalService.getKBArticlesCount();
	}

	@Override
	public int getKBArticlesCount(
		long groupId, long parentResourcePrimKey, int status) {

		return _kbArticleLocalService.getKBArticlesCount(
			groupId, parentResourcePrimKey, status);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBArticleVersions(
			long resourcePrimKey, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getKBArticleVersions(
			resourcePrimKey, status, start, end, orderByComparator);
	}

	@Override
	public int getKBArticleVersionsCount(long resourcePrimKey, int status) {
		return _kbArticleLocalService.getKBArticleVersionsCount(
			resourcePrimKey, status);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getKBFolderKBArticles(long groupId, long kbFolderId) {

		return _kbArticleLocalService.getKBFolderKBArticles(
			groupId, kbFolderId);
	}

	@Override
	public int getKBFolderKBArticlesCount(
		long groupId, long kbFolderId, int status) {

		return _kbArticleLocalService.getKBFolderKBArticlesCount(
			groupId, kbFolderId, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle getLatestKBArticle(
			long resourcePrimKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getLatestKBArticle(
			resourcePrimKey, status);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle
			getLatestKBArticleByUrlTitle(
				long groupId, long kbFolderId, String urlTitle, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getLatestKBArticleByUrlTitle(
			groupId, kbFolderId, urlTitle, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _kbArticleLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<? extends com.liferay.portal.kernel.model.PersistedModel>
				getPersistedModel(long resourcePrimKey)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getPersistedModel(resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle[]
			getPreviousAndNextKBArticles(long kbArticleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getPreviousAndNextKBArticles(kbArticleId);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle>
		getSectionsKBArticles(
			long groupId, String[] sections, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.knowledge.base.model.KBArticle>
					orderByComparator) {

		return _kbArticleLocalService.getSectionsKBArticles(
			groupId, sections, status, start, end, orderByComparator);
	}

	@Override
	public int getSectionsKBArticlesCount(
		long groupId, String[] sections, int status) {

		return _kbArticleLocalService.getSectionsKBArticlesCount(
			groupId, sections, status);
	}

	@Override
	public String[] getTempAttachmentNames(
			long groupId, long userId, String tempFolderName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.getTempAttachmentNames(
			groupId, userId, tempFolderName);
	}

	@Override
	public void moveKBArticle(
			long userId, long resourcePrimKey, long parentResourceClassNameId,
			long parentResourcePrimKey, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.moveKBArticle(
			userId, resourcePrimKey, parentResourceClassNameId,
			parentResourcePrimKey, priority);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle revertKBArticle(
			long userId, long resourcePrimKey, int version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.revertKBArticle(
			userId, resourcePrimKey, version, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.knowledge.base.model.KBArticle> search(
		long groupId, String title, String content, int status,
		java.util.Date startDate, java.util.Date endDate, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.knowledge.base.model.KBArticle> orderByComparator) {

		return _kbArticleLocalService.search(
			groupId, title, content, status, startDate, endDate, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public void subscribeGroupKBArticles(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.subscribeGroupKBArticles(userId, groupId);
	}

	@Override
	public void subscribeKBArticle(
			long userId, long groupId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.subscribeKBArticle(
			userId, groupId, resourcePrimKey);
	}

	@Override
	public void unsubscribeGroupKBArticles(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.unsubscribeGroupKBArticles(userId, groupId);
	}

	@Override
	public void unsubscribeKBArticle(long userId, long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.unsubscribeKBArticle(userId, resourcePrimKey);
	}

	/**
	 * Updates the kb article in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kbArticle the kb article
	 * @return the kb article that was updated
	 */
	@Override
	public com.liferay.knowledge.base.model.KBArticle updateKBArticle(
		com.liferay.knowledge.base.model.KBArticle kbArticle) {

		return _kbArticleLocalService.updateKBArticle(kbArticle);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle updateKBArticle(
			long userId, long resourcePrimKey, String title, String content,
			String description, String sourceURL, String[] sections,
			String[] selectedFileNames, long[] removeFileEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.updateKBArticle(
			userId, resourcePrimKey, title, content, description, sourceURL,
			sections, selectedFileNames, removeFileEntryIds, serviceContext);
	}

	@Override
	public void updateKBArticleAsset(
			long userId, com.liferay.knowledge.base.model.KBArticle kbArticle,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.updateKBArticleAsset(
			userId, kbArticle, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	@Override
	public void updateKBArticleResources(
			com.liferay.knowledge.base.model.KBArticle kbArticle,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.updateKBArticleResources(
			kbArticle, groupPermissions, guestPermissions);
	}

	@Override
	public void updateKBArticlesPriorities(
			java.util.Map<Long, Double> resourcePrimKeyToPriorityMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.updateKBArticlesPriorities(
			resourcePrimKeyToPriorityMap);
	}

	@Override
	public void updatePriority(long resourcePrimKey, double priority) {
		_kbArticleLocalService.updatePriority(resourcePrimKey, priority);
	}

	@Override
	public com.liferay.knowledge.base.model.KBArticle updateStatus(
			long userId, long resourcePrimKey, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _kbArticleLocalService.updateStatus(
			userId, resourcePrimKey, status, serviceContext);
	}

	@Override
	public void updateViewCount(
			long userId, long resourcePrimKey, int viewCount)
		throws com.liferay.portal.kernel.exception.PortalException {

		_kbArticleLocalService.updateViewCount(
			userId, resourcePrimKey, viewCount);
	}

	@Override
	public KBArticleLocalService getWrappedService() {
		return _kbArticleLocalService;
	}

	@Override
	public void setWrappedService(KBArticleLocalService kbArticleLocalService) {
		_kbArticleLocalService = kbArticleLocalService;
	}

	private KBArticleLocalService _kbArticleLocalService;

}