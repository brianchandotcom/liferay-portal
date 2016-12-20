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

package com.liferay.asset.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryLocalService
 * @generated
 */
@ProviderType
public class AssetEntryLocalServiceWrapper implements AssetEntryLocalService,
	ServiceWrapper<AssetEntryLocalService> {
	public AssetEntryLocalServiceWrapper(
		AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public boolean hasAssetCategoryAssetEntries(long categoryId) {
		return _assetEntryLocalService.hasAssetCategoryAssetEntries(categoryId);
	}

	@Override
	public boolean hasAssetCategoryAssetEntry(long categoryId, long entryId) {
		return _assetEntryLocalService.hasAssetCategoryAssetEntry(categoryId,
			entryId);
	}

	@Override
	public boolean hasAssetTagAssetEntries(long tagId) {
		return _assetEntryLocalService.hasAssetTagAssetEntries(tagId);
	}

	@Override
	public boolean hasAssetTagAssetEntry(long tagId, long entryId) {
		return _assetEntryLocalService.hasAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* Adds the asset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was added
	*/
	@Override
	public com.liferay.asset.kernel.model.AssetEntry addAssetEntry(
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		return _assetEntryLocalService.addAssetEntry(assetEntry);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	@Override
	public com.liferay.asset.kernel.model.AssetEntry createAssetEntry(
		long entryId) {
		return _assetEntryLocalService.createAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was removed
	*/
	@Override
	public com.liferay.asset.kernel.model.AssetEntry deleteAssetEntry(
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		return _assetEntryLocalService.deleteAssetEntry(assetEntry);
	}

	/**
	* Deletes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws PortalException if a asset entry with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.kernel.model.AssetEntry deleteAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.deleteAssetEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry fetchAssetEntry(
		long entryId) {
		return _assetEntryLocalService.fetchAssetEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry fetchEntry(
		java.lang.String className, long classPK) {
		return _assetEntryLocalService.fetchEntry(className, classPK);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry fetchEntry(long entryId) {
		return _assetEntryLocalService.fetchEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry fetchEntry(long groupId,
		java.lang.String classUuid) {
		return _assetEntryLocalService.fetchEntry(groupId, classUuid);
	}

	/**
	* Returns the asset entry with the primary key.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws PortalException if a asset entry with the primary key could not be found
	*/
	@Override
	public com.liferay.asset.kernel.model.AssetEntry getAssetEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getAssetEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getEntry(className, classPK);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getEntry(long groupId,
		java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getEntry(groupId, classUuid);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getNextEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getNextEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getParentEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getParentEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getPreviousEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getPreviousEntry(entryId);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry incrementViewCounter(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.incrementViewCounter(userId, className,
			classPK);
	}

	/**
	* Updates the asset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was updated
	*/
	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateAssetEntry(
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		return _assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(String, long,
	Date, Date, boolean, boolean)}
	*/
	@Deprecated
	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(className, classPK,
			publishDate, visible);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		java.util.Date expirationDate, boolean listable, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(className, classPK,
			publishDate, expirationDate, listable, visible);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(String, long,
	Date, Date, boolean, boolean)}
	*/
	@Deprecated
	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		java.util.Date expirationDate, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(className, classPK,
			publishDate, expirationDate, visible);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, categoryIds, tagNames);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	Date, Date, String, long, String, long, long[], String[],
	boolean, boolean, Date, Date, Date, Date, String, String,
	String, String, String, String, int, int, Double)}
	*/
	@Deprecated
	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(long userId,
		long groupId, java.util.Date createDate, java.util.Date modifiedDate,
		java.lang.String className, long classPK, java.lang.String classUuid,
		long classTypeId, long[] categoryIds, java.lang.String[] tagNames,
		boolean listable, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(userId, groupId, createDate,
			modifiedDate, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, listable, visible, startDate, endDate,
			expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(long userId,
		long groupId, java.util.Date createDate, java.util.Date modifiedDate,
		java.lang.String className, long classPK, java.lang.String classUuid,
		long classTypeId, long[] categoryIds, java.lang.String[] tagNames,
		boolean listable, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(userId, groupId, createDate,
			modifiedDate, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, listable, visible, startDate, endDate,
			publishDate, expirationDate, mimeType, title, description, summary,
			url, layoutUuid, height, width, priority);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	Date, Date, String, long, String, long, long[], String[],
	boolean, boolean, Date, Date, Date, Date, String, String,
	String, String, String, String, int, int, Double)}
	*/
	@Deprecated
	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateEntry(long userId,
		long groupId, java.util.Date createDate, java.util.Date modifiedDate,
		java.lang.String className, long classPK, java.lang.String classUuid,
		long classTypeId, long[] categoryIds, java.lang.String[] tagNames,
		boolean visible, java.util.Date startDate, java.util.Date endDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateEntry(userId, groupId, createDate,
			modifiedDate, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, visible, startDate, endDate, expirationDate,
			mimeType, title, description, summary, url, layoutUuid, height,
			width, priority, sync);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateVisible(
		com.liferay.asset.kernel.model.AssetEntry entry, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateVisible(entry, visible);
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.updateVisible(className, classPK, visible);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _assetEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetEntryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _assetEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String keywords, int status, int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, keywords, status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, int status, boolean andSearch,
		int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, userName, title, description, assetCategoryIds,
			assetTagNames, status, andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String keywords, boolean showNonindexable,
		int status, int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, keywords, showNonindexable, status, start,
			end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String keywords, boolean showNonindexable,
		int[] statuses, int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, keywords, showNonindexable, statuses,
			start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String keywords, boolean showNonindexable,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.search.Sort sort) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, keywords, showNonindexable, statuses,
			start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String keywords, int status, int start,
		int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, keywords, status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, boolean showNonindexable, int status,
		boolean andSearch, int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, userName, title, description,
			assetCategoryIds, assetTagNames, showNonindexable, status,
			andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch, int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, userName, title, description,
			assetCategoryIds, assetTagNames, showNonindexable, statuses,
			andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		long classTypeId, java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, int status, boolean andSearch,
		int start, int end) {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, classTypeId, userName, title, description,
			assetCategoryIds, assetTagNames, status, andSearch, start, end);
	}

	@Override
	public int getAssetCategoryAssetEntriesCount(long categoryId) {
		return _assetEntryLocalService.getAssetCategoryAssetEntriesCount(categoryId);
	}

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	*/
	@Override
	public int getAssetEntriesCount() {
		return _assetEntryLocalService.getAssetEntriesCount();
	}

	@Override
	public int getAssetTagAssetEntriesCount(long tagId) {
		return _assetEntryLocalService.getAssetTagAssetEntriesCount(tagId);
	}

	@Override
	public int getCompanyEntriesCount(long companyId) {
		return _assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	@Override
	public int getEntriesCount(
		com.liferay.asset.kernel.service.persistence.AssetEntryQuery entryQuery) {
		return _assetEntryLocalService.getEntriesCount(entryQuery);
	}

	@Override
	public int getEntriesCount(long[] groupIds, long[] classNameIds,
		java.lang.String keywords, java.lang.String userName,
		java.lang.String title, java.lang.String description,
		java.lang.Boolean listable, boolean advancedSearch, boolean andOperator) {
		return _assetEntryLocalService.getEntriesCount(groupIds, classNameIds,
			keywords, userName, title, description, listable, advancedSearch,
			andOperator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _assetEntryLocalService.getOSGiServiceIdentifier();
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
		return _assetEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAncestorEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getAncestorEntries(entryId);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId) {
		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end) {
		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.kernel.model.AssetEntry> orderByComparator) {
		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset entries
	*/
	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetEntries(
		int start, int end) {
		return _assetEntryLocalService.getAssetEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetTagAssetEntries(
		long tagId) {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end) {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId, start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.kernel.model.AssetEntry> orderByComparator) {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getChildEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetEntryLocalService.getChildEntries(entryId);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end) {
		return _assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getEntries(
		com.liferay.asset.kernel.service.persistence.AssetEntryQuery entryQuery) {
		return _assetEntryLocalService.getEntries(entryQuery);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getEntries(
		long[] groupIds, long[] classNameIds, java.lang.String keywords,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.Boolean listable,
		boolean advancedSearch, boolean andOperator, int start, int end,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2) {
		return _assetEntryLocalService.getEntries(groupIds, classNameIds,
			keywords, userName, title, description, listable, advancedSearch,
			andOperator, start, end, orderByCol1, orderByCol2, orderByType1,
			orderByType2);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getGroupEntries(
		long groupId) {
		return _assetEntryLocalService.getGroupEntries(groupId);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getTopViewedEntries(
		java.lang.String className, boolean asc, int start, int end) {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.asset.kernel.model.AssetEntry> getTopViewedEntries(
		java.lang.String[] className, boolean asc, int start, int end) {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
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
		return _assetEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _assetEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public long searchCount(long companyId, long[] groupIds, long userId,
		java.lang.String className, long classTypeId,
		java.lang.String keywords, boolean showNonindexable, int[] statuses) {
		return _assetEntryLocalService.searchCount(companyId, groupIds, userId,
			className, classTypeId, keywords, showNonindexable, statuses);
	}

	@Override
	public long searchCount(long companyId, long[] groupIds, long userId,
		java.lang.String className, long classTypeId,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, boolean showInvisible,
		boolean showNonindexable, int[] statuses, boolean andSearch) {
		return _assetEntryLocalService.searchCount(companyId, groupIds, userId,
			className, classTypeId, userName, title, description,
			assetCategoryIds, assetTagNames, showInvisible, showNonindexable,
			statuses, andSearch);
	}

	@Override
	public long searchCount(long companyId, long[] groupIds, long userId,
		java.lang.String className, long classTypeId,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch) {
		return _assetEntryLocalService.searchCount(companyId, groupIds, userId,
			className, classTypeId, userName, title, description,
			assetCategoryIds, assetTagNames, showNonindexable, statuses,
			andSearch);
	}

	/**
	* Returns the categoryIds of the asset categories associated with the asset entry.
	*
	* @param entryId the entryId of the asset entry
	* @return long[] the categoryIds of asset categories associated with the asset entry
	*/
	@Override
	public long[] getAssetCategoryPrimaryKeys(long entryId) {
		return _assetEntryLocalService.getAssetCategoryPrimaryKeys(entryId);
	}

	/**
	* Returns the tagIds of the asset tags associated with the asset entry.
	*
	* @param entryId the entryId of the asset entry
	* @return long[] the tagIds of asset tags associated with the asset entry
	*/
	@Override
	public long[] getAssetTagPrimaryKeys(long entryId) {
		return _assetEntryLocalService.getAssetTagPrimaryKeys(entryId);
	}

	@Override
	public void addAssetCategoryAssetEntries(long categoryId,
		java.util.List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {
		_assetEntryLocalService.addAssetCategoryAssetEntries(categoryId,
			assetEntries);
	}

	@Override
	public void addAssetCategoryAssetEntries(long categoryId, long[] entryIds) {
		_assetEntryLocalService.addAssetCategoryAssetEntries(categoryId,
			entryIds);
	}

	@Override
	public void addAssetCategoryAssetEntry(long categoryId,
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		_assetEntryLocalService.addAssetCategoryAssetEntry(categoryId,
			assetEntry);
	}

	@Override
	public void addAssetCategoryAssetEntry(long categoryId, long entryId) {
		_assetEntryLocalService.addAssetCategoryAssetEntry(categoryId, entryId);
	}

	@Override
	public void addAssetTagAssetEntries(long tagId,
		java.util.List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {
		_assetEntryLocalService.addAssetTagAssetEntries(tagId, assetEntries);
	}

	@Override
	public void addAssetTagAssetEntries(long tagId, long[] entryIds) {
		_assetEntryLocalService.addAssetTagAssetEntries(tagId, entryIds);
	}

	@Override
	public void addAssetTagAssetEntry(long tagId,
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		_assetEntryLocalService.addAssetTagAssetEntry(tagId, assetEntry);
	}

	@Override
	public void addAssetTagAssetEntry(long tagId, long entryId) {
		_assetEntryLocalService.addAssetTagAssetEntry(tagId, entryId);
	}

	@Override
	public void clearAssetCategoryAssetEntries(long categoryId) {
		_assetEntryLocalService.clearAssetCategoryAssetEntries(categoryId);
	}

	@Override
	public void clearAssetTagAssetEntries(long tagId) {
		_assetEntryLocalService.clearAssetTagAssetEntries(tagId);
	}

	@Override
	public void deleteAssetCategoryAssetEntries(long categoryId,
		java.util.List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {
		_assetEntryLocalService.deleteAssetCategoryAssetEntries(categoryId,
			assetEntries);
	}

	@Override
	public void deleteAssetCategoryAssetEntries(long categoryId, long[] entryIds) {
		_assetEntryLocalService.deleteAssetCategoryAssetEntries(categoryId,
			entryIds);
	}

	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId,
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		_assetEntryLocalService.deleteAssetCategoryAssetEntry(categoryId,
			assetEntry);
	}

	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId, long entryId) {
		_assetEntryLocalService.deleteAssetCategoryAssetEntry(categoryId,
			entryId);
	}

	@Override
	public void deleteAssetTagAssetEntries(long tagId,
		java.util.List<com.liferay.asset.kernel.model.AssetEntry> assetEntries) {
		_assetEntryLocalService.deleteAssetTagAssetEntries(tagId, assetEntries);
	}

	@Override
	public void deleteAssetTagAssetEntries(long tagId, long[] entryIds) {
		_assetEntryLocalService.deleteAssetTagAssetEntries(tagId, entryIds);
	}

	@Override
	public void deleteAssetTagAssetEntry(long tagId,
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {
		_assetEntryLocalService.deleteAssetTagAssetEntry(tagId, assetEntry);
	}

	@Override
	public void deleteAssetTagAssetEntry(long tagId, long entryId) {
		_assetEntryLocalService.deleteAssetTagAssetEntry(tagId, entryId);
	}

	@Override
	public void deleteEntry(com.liferay.asset.kernel.model.AssetEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.deleteEntry(className, classPK);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public void deleteGroupEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.deleteGroupEntries(groupId);
	}

	@Override
	public void incrementViewCounter(long userId, java.lang.String className,
		long classPK, int increment) {
		_assetEntryLocalService.incrementViewCounter(userId, className,
			classPK, increment);
	}

	@Override
	public void reindex(
		java.util.List<com.liferay.asset.kernel.model.AssetEntry> entries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.reindex(entries);
	}

	@Override
	public void setAssetCategoryAssetEntries(long categoryId, long[] entryIds) {
		_assetEntryLocalService.setAssetCategoryAssetEntries(categoryId,
			entryIds);
	}

	@Override
	public void setAssetTagAssetEntries(long tagId, long[] entryIds) {
		_assetEntryLocalService.setAssetTagAssetEntries(tagId, entryIds);
	}

	@Override
	public void validate(long groupId, java.lang.String className,
		long classPK, long classTypePK, long[] categoryIds,
		java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.validate(groupId, className, classPK,
			classTypePK, categoryIds, tagNames);
	}

	@Override
	public void validate(long groupId, java.lang.String className,
		long classTypePK, long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.validate(groupId, className, classTypePK,
			categoryIds, tagNames);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #validate(long, String, long,
	long[], String[])}
	*/
	@Deprecated
	@Override
	public void validate(long groupId, java.lang.String className,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.validate(groupId, className, categoryIds,
			tagNames);
	}

	@Override
	public AssetEntryLocalService getWrappedService() {
		return _assetEntryLocalService;
	}

	@Override
	public void setWrappedService(AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
}