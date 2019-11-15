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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link AssetEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryLocalService
 * @generated
 */
public class AssetEntryLocalServiceWrapper
	implements AssetEntryLocalService, ServiceWrapper<AssetEntryLocalService> {

	public AssetEntryLocalServiceWrapper(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryLocalServiceUtil} to access the asset entry local service. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public void addAssetCategoryAssetEntries(
		long categoryId, java.util.List<AssetEntry> assetEntries) {

		_assetEntryLocalService.addAssetCategoryAssetEntries(
			categoryId, assetEntries);
	}

	@Override
	public void addAssetCategoryAssetEntries(long categoryId, long[] entryIds) {
		_assetEntryLocalService.addAssetCategoryAssetEntries(
			categoryId, entryIds);
	}

	@Override
	public void addAssetCategoryAssetEntry(
		long categoryId, AssetEntry assetEntry) {

		_assetEntryLocalService.addAssetCategoryAssetEntry(
			categoryId, assetEntry);
	}

	@Override
	public void addAssetCategoryAssetEntry(long categoryId, long entryId) {
		_assetEntryLocalService.addAssetCategoryAssetEntry(categoryId, entryId);
	}

	/**
	 * Adds the asset entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntry the asset entry
	 * @return the asset entry that was added
	 */
	@Override
	public AssetEntry addAssetEntry(AssetEntry assetEntry) {
		return _assetEntryLocalService.addAssetEntry(assetEntry);
	}

	@Override
	public void addAssetTagAssetEntries(
		long tagId, java.util.List<AssetEntry> assetEntries) {

		_assetEntryLocalService.addAssetTagAssetEntries(tagId, assetEntries);
	}

	@Override
	public void addAssetTagAssetEntries(long tagId, long[] entryIds) {
		_assetEntryLocalService.addAssetTagAssetEntries(tagId, entryIds);
	}

	@Override
	public void addAssetTagAssetEntry(long tagId, AssetEntry assetEntry) {
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

	/**
	 * Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	 *
	 * @param entryId the primary key for the new asset entry
	 * @return the new asset entry
	 */
	@Override
	public AssetEntry createAssetEntry(long entryId) {
		return _assetEntryLocalService.createAssetEntry(entryId);
	}

	@Override
	public void deleteAssetCategoryAssetEntries(
		long categoryId, java.util.List<AssetEntry> assetEntries) {

		_assetEntryLocalService.deleteAssetCategoryAssetEntries(
			categoryId, assetEntries);
	}

	@Override
	public void deleteAssetCategoryAssetEntries(
		long categoryId, long[] entryIds) {

		_assetEntryLocalService.deleteAssetCategoryAssetEntries(
			categoryId, entryIds);
	}

	@Override
	public void deleteAssetCategoryAssetEntry(
		long categoryId, AssetEntry assetEntry) {

		_assetEntryLocalService.deleteAssetCategoryAssetEntry(
			categoryId, assetEntry);
	}

	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId, long entryId) {
		_assetEntryLocalService.deleteAssetCategoryAssetEntry(
			categoryId, entryId);
	}

	/**
	 * Deletes the asset entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntry the asset entry
	 * @return the asset entry that was removed
	 */
	@Override
	public AssetEntry deleteAssetEntry(AssetEntry assetEntry) {
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
	public AssetEntry deleteAssetEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.deleteAssetEntry(entryId);
	}

	@Override
	public void deleteAssetTagAssetEntries(
		long tagId, java.util.List<AssetEntry> assetEntries) {

		_assetEntryLocalService.deleteAssetTagAssetEntries(tagId, assetEntries);
	}

	@Override
	public void deleteAssetTagAssetEntries(long tagId, long[] entryIds) {
		_assetEntryLocalService.deleteAssetTagAssetEntries(tagId, entryIds);
	}

	@Override
	public void deleteAssetTagAssetEntry(long tagId, AssetEntry assetEntry) {
		_assetEntryLocalService.deleteAssetTagAssetEntry(tagId, assetEntry);
	}

	@Override
	public void deleteAssetTagAssetEntry(long tagId, long entryId) {
		_assetEntryLocalService.deleteAssetTagAssetEntry(tagId, entryId);
	}

	@Override
	public void deleteEntry(AssetEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public void deleteEntry(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.deleteEntry(className, classPK);
	}

	@Override
	public void deleteGroupEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.deleteGroupEntries(groupId);
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
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetEntryLocalService.dynamicQuery();
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetEntryModelImpl</code>.
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

		return _assetEntryLocalService.dynamicQuery(
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

		return _assetEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public AssetEntry fetchAssetEntry(long entryId) {
		return _assetEntryLocalService.fetchAssetEntry(entryId);
	}

	@Override
	public AssetEntry fetchEntry(long entryId) {
		return _assetEntryLocalService.fetchEntry(entryId);
	}

	@Override
	public AssetEntry fetchEntry(long classNameId, long classPK) {
		return _assetEntryLocalService.fetchEntry(classNameId, classPK);
	}

	@Override
	public AssetEntry fetchEntry(long groupId, String classUuid) {
		return _assetEntryLocalService.fetchEntry(groupId, classUuid);
	}

	@Override
	public AssetEntry fetchEntry(String className, long classPK) {
		return _assetEntryLocalService.fetchEntry(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _assetEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<AssetEntry> getAncestorEntries(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getAncestorEntries(entryId);
	}

	@Override
	public java.util.List<AssetEntry> getAssetCategoryAssetEntries(
		long categoryId) {

		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId);
	}

	@Override
	public java.util.List<AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end) {

		return _assetEntryLocalService.getAssetCategoryAssetEntries(
			categoryId, start, end);
	}

	@Override
	public java.util.List<AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry>
			orderByComparator) {

		return _assetEntryLocalService.getAssetCategoryAssetEntries(
			categoryId, start, end, orderByComparator);
	}

	@Override
	public int getAssetCategoryAssetEntriesCount(long categoryId) {
		return _assetEntryLocalService.getAssetCategoryAssetEntriesCount(
			categoryId);
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
	 * Returns a range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.asset.model.impl.AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset entries
	 */
	@Override
	public java.util.List<AssetEntry> getAssetEntries(int start, int end) {
		return _assetEntryLocalService.getAssetEntries(start, end);
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

	/**
	 * Returns the asset entry with the primary key.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry
	 * @throws PortalException if a asset entry with the primary key could not be found
	 */
	@Override
	public AssetEntry getAssetEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getAssetEntry(entryId);
	}

	@Override
	public java.util.List<AssetEntry> getAssetTagAssetEntries(long tagId) {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId);
	}

	@Override
	public java.util.List<AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end) {

		return _assetEntryLocalService.getAssetTagAssetEntries(
			tagId, start, end);
	}

	@Override
	public java.util.List<AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntry>
			orderByComparator) {

		return _assetEntryLocalService.getAssetTagAssetEntries(
			tagId, start, end, orderByComparator);
	}

	@Override
	public int getAssetTagAssetEntriesCount(long tagId) {
		return _assetEntryLocalService.getAssetTagAssetEntriesCount(tagId);
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
	public java.util.List<AssetEntry> getChildEntries(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getChildEntries(entryId);
	}

	@Override
	public java.util.List<AssetEntry> getCompanyEntries(
		long companyId, int start, int end) {

		return _assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	@Override
	public int getCompanyEntriesCount(long companyId) {
		return _assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	@Override
	public java.util.List<AssetEntry> getEntries(
		com.liferay.asset.kernel.service.persistence.AssetEntryQuery
			entryQuery) {

		return _assetEntryLocalService.getEntries(entryQuery);
	}

	@Override
	public java.util.List<AssetEntry> getEntries(
		long[] groupIds, long[] classNameIds, long[] classTypeIds,
		String keywords, String userName, String title, String description,
		Boolean listable, boolean advancedSearch, boolean andOperator,
		int start, int end, String orderByCol1, String orderByCol2,
		String orderByType1, String orderByType2) {

		return _assetEntryLocalService.getEntries(
			groupIds, classNameIds, classTypeIds, keywords, userName, title,
			description, listable, advancedSearch, andOperator, start, end,
			orderByCol1, orderByCol2, orderByType1, orderByType2);
	}

	@Override
	public java.util.List<AssetEntry> getEntries(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, Boolean listable,
		boolean advancedSearch, boolean andOperator, int start, int end,
		String orderByCol1, String orderByCol2, String orderByType1,
		String orderByType2) {

		return _assetEntryLocalService.getEntries(
			groupIds, classNameIds, keywords, userName, title, description,
			listable, advancedSearch, andOperator, start, end, orderByCol1,
			orderByCol2, orderByType1, orderByType2);
	}

	@Override
	public int getEntriesCount(
		com.liferay.asset.kernel.service.persistence.AssetEntryQuery
			entryQuery) {

		return _assetEntryLocalService.getEntriesCount(entryQuery);
	}

	@Override
	public int getEntriesCount(
		long[] groupIds, long[] classNameIds, long[] classTypeIds,
		String keywords, String userName, String title, String description,
		Boolean listable, boolean advancedSearch, boolean andOperator) {

		return _assetEntryLocalService.getEntriesCount(
			groupIds, classNameIds, classTypeIds, keywords, userName, title,
			description, listable, advancedSearch, andOperator);
	}

	@Override
	public int getEntriesCount(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, Boolean listable,
		boolean advancedSearch, boolean andOperator) {

		return _assetEntryLocalService.getEntriesCount(
			groupIds, classNameIds, keywords, userName, title, description,
			listable, advancedSearch, andOperator);
	}

	@Override
	public AssetEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getEntry(entryId);
	}

	@Override
	public AssetEntry getEntry(long groupId, String classUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getEntry(groupId, classUuid);
	}

	@Override
	public AssetEntry getEntry(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getEntry(className, classPK);
	}

	@Override
	public double getEntryPriority(long classNameId, long classPK) {
		return _assetEntryLocalService.getEntryPriority(classNameId, classPK);
	}

	@Override
	public double getEntryPriority(String className, long classPK) {
		return _assetEntryLocalService.getEntryPriority(className, classPK);
	}

	@Override
	public java.util.List<AssetEntry> getGroupEntries(long groupId) {
		return _assetEntryLocalService.getGroupEntries(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _assetEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public AssetEntry getNextEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getNextEntry(entryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetEntry getParentEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getParentEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public AssetEntry getPreviousEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.getPreviousEntry(entryId);
	}

	@Override
	public java.util.List<AssetEntry> getTopViewedEntries(
		String className, boolean asc, int start, int end) {

		return _assetEntryLocalService.getTopViewedEntries(
			className, asc, start, end);
	}

	@Override
	public java.util.List<AssetEntry> getTopViewedEntries(
		String[] className, boolean asc, int start, int end) {

		return _assetEntryLocalService.getTopViewedEntries(
			className, asc, start, end);
	}

	@Override
	public boolean hasAssetCategoryAssetEntries(long categoryId) {
		return _assetEntryLocalService.hasAssetCategoryAssetEntries(categoryId);
	}

	@Override
	public boolean hasAssetCategoryAssetEntry(long categoryId, long entryId) {
		return _assetEntryLocalService.hasAssetCategoryAssetEntry(
			categoryId, entryId);
	}

	@Override
	public boolean hasAssetTagAssetEntries(long tagId) {
		return _assetEntryLocalService.hasAssetTagAssetEntries(tagId);
	}

	@Override
	public boolean hasAssetTagAssetEntry(long tagId, long entryId) {
		return _assetEntryLocalService.hasAssetTagAssetEntry(tagId, entryId);
	}

	@Override
	public void incrementViewCounter(long userId, AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.incrementViewCounter(userId, assetEntry);
	}

	@Override
	public AssetEntry incrementViewCounter(
			long companyId, long userId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.incrementViewCounter(
			companyId, userId, className, classPK);
	}

	@Override
	public void incrementViewCounter(
		long companyId, long userId, String className, long classPK,
		int increment) {

		_assetEntryLocalService.incrementViewCounter(
			companyId, userId, className, classPK, increment);
	}

	@Override
	public void reindex(java.util.List<AssetEntry> entries)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.reindex(entries);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, long[] classNameIds,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.search.Sort sort) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, classNameIds, classTypeId, keywords,
			showNonindexable, statuses, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable, int status,
		int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			showNonindexable, status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			showNonindexable, statuses, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses, int start, int end,
		com.liferay.portal.kernel.search.Sort sort) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			showNonindexable, statuses, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, int status, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int status, boolean andSearch, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames,
			showNonindexable, status, andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames,
			showNonindexable, statuses, andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, int status,
		boolean andSearch, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames, status,
			andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String keywords, int status, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, keywords, status, start,
			end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, int status,
		boolean andSearch, int start, int end) {

		return _assetEntryLocalService.search(
			companyId, groupIds, userId, className, userName, title,
			description, assetCategoryIds, assetTagNames, status, andSearch,
			start, end);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, long[] classNameIds,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses) {

		return _assetEntryLocalService.searchCount(
			companyId, groupIds, userId, classNameIds, classTypeId, keywords,
			showNonindexable, statuses);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses) {

		return _assetEntryLocalService.searchCount(
			companyId, groupIds, userId, className, classTypeId, keywords,
			showNonindexable, statuses);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, String assetCategoryIds,
		String assetTagNames, boolean showInvisible, boolean showNonindexable,
		int[] statuses, boolean andSearch) {

		return _assetEntryLocalService.searchCount(
			companyId, groupIds, userId, className, classTypeId, keywords,
			assetCategoryIds, assetTagNames, showInvisible, showNonindexable,
			statuses, andSearch);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showInvisible,
		boolean showNonindexable, int[] statuses, boolean andSearch) {

		return _assetEntryLocalService.searchCount(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames, showInvisible,
			showNonindexable, statuses, andSearch);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch) {

		return _assetEntryLocalService.searchCount(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames,
			showNonindexable, statuses, andSearch);
	}

	@Override
	public void setAssetCategoryAssetEntries(long categoryId, long[] entryIds) {
		_assetEntryLocalService.setAssetCategoryAssetEntries(
			categoryId, entryIds);
	}

	@Override
	public void setAssetTagAssetEntries(long tagId, long[] entryIds) {
		_assetEntryLocalService.setAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	 * Updates the asset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntry the asset entry
	 * @return the asset entry that was updated
	 */
	@Override
	public AssetEntry updateAssetEntry(AssetEntry assetEntry) {
		return _assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, java.util.Date createDate,
			java.util.Date modifiedDate, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean listable, boolean visible,
			java.util.Date startDate, java.util.Date endDate,
			java.util.Date publishDate, java.util.Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, String layoutUuid, int height, int width,
			Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.updateEntry(
			userId, groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, listable, visible,
			startDate, endDate, publishDate, expirationDate, mimeType, title,
			description, summary, url, layoutUuid, height, width, priority);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #updateEntry(long,
	 long, Date, Date, String, long, String, long, long[],
	 String[], boolean, boolean, Date, Date, Date, Date, String,
	 String, String, String, String, String, int, int, Double)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, java.util.Date createDate,
			java.util.Date modifiedDate, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean listable, boolean visible,
			java.util.Date startDate, java.util.Date endDate,
			java.util.Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.updateEntry(
			userId, groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, listable, visible,
			startDate, endDate, expirationDate, mimeType, title, description,
			summary, url, layoutUuid, height, width, priority);
	}

	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.updateEntry(
			userId, groupId, className, classPK, categoryIds, tagNames);
	}

	@Override
	public AssetEntry updateEntry(
			String className, long classPK, java.util.Date publishDate,
			java.util.Date expirationDate, boolean listable, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.updateEntry(
			className, classPK, publishDate, expirationDate, listable, visible);
	}

	@Override
	public AssetEntry updateVisible(AssetEntry entry, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.updateVisible(entry, visible);
	}

	@Override
	public AssetEntry updateVisible(
			String className, long classPK, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryLocalService.updateVisible(
			className, classPK, visible);
	}

	@Override
	public void validate(
			long groupId, String className, long classPK, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.validate(
			groupId, className, classPK, classTypePK, categoryIds, tagNames);
	}

	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryLocalService.validate(
			groupId, className, classTypePK, categoryIds, tagNames);
	}

	@Override
	public CTPersistence<AssetEntry> getCTPersistence() {
		return _assetEntryLocalService.getCTPersistence();
	}

	@Override
	public Class<AssetEntry> getModelClass() {
		return _assetEntryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<AssetEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return _assetEntryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public AssetEntryLocalService getWrappedService() {
		return _assetEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;

}