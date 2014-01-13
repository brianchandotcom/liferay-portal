/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

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

	/**
	* Adds the asset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetEntry addAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.addAssetEntry(assetEntry);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetEntry createAssetEntry(
		long entryId) {
		return _assetEntryLocalService.createAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws PortalException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetEntry deleteAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.deleteAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetEntry deleteAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.deleteAssetEntry(assetEntry);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry fetchAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.fetchAssetEntry(entryId);
	}

	/**
	* Returns the asset entry with the primary key.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws PortalException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetEntry getAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetEntry(entryId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetEntries(start, end);
	}

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetEntriesCount();
	}

	/**
	* Updates the asset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry
	* @return the asset entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetCategoryAssetEntry(long categoryId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetCategoryAssetEntry(categoryId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetCategoryAssetEntry(long categoryId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetCategoryAssetEntry(categoryId,
			assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetCategoryAssetEntries(long categoryId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetCategoryAssetEntries(categoryId,
			entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetCategoryAssetEntries(long categoryId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetCategoryAssetEntries(categoryId,
			AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearAssetCategoryAssetEntries(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.clearAssetCategoryAssetEntries(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetCategoryAssetEntry(categoryId,
			entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetCategoryAssetEntry(long categoryId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetCategoryAssetEntry(categoryId,
			assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetCategoryAssetEntries(long categoryId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetCategoryAssetEntries(categoryId,
			entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetCategoryAssetEntries(long categoryId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetCategoryAssetEntries(categoryId,
			AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetCategoryAssetEntries(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetCategoryAssetEntries(categoryId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetCategoryAssetEntriesCount(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetCategoryAssetEntriesCount(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasAssetCategoryAssetEntry(long categoryId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.hasAssetCategoryAssetEntry(categoryId,
			entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasAssetCategoryAssetEntries(long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.hasAssetCategoryAssetEntries(categoryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setAssetCategoryAssetEntries(long categoryId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.setAssetCategoryAssetEntries(categoryId,
			entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetTagAssetEntry(long tagId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetTagAssetEntry(long tagId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetTagAssetEntry(tagId, assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetTagAssetEntries(long tagId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addAssetTagAssetEntries(long tagId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.addAssetTagAssetEntries(tagId, AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearAssetTagAssetEntries(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.clearAssetTagAssetEntries(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetTagAssetEntry(long tagId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetTagAssetEntry(long tagId,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetTagAssetEntry(tagId, assetEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetTagAssetEntries(long tagId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteAssetTagAssetEntries(long tagId,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> AssetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetTagAssetEntries(tagId, AssetEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetTagAssetEntries(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetTagAssetEntries(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetTagAssetEntries(tagId, start,
			end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetTagAssetEntriesCount(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetTagAssetEntriesCount(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasAssetTagAssetEntry(long tagId, long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.hasAssetTagAssetEntry(tagId, entryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasAssetTagAssetEntries(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.hasAssetTagAssetEntries(tagId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setAssetTagAssetEntries(long tagId, long[] entryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.setAssetTagAssetEntries(tagId, entryIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _assetEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public void deleteEntry(com.liferay.portlet.asset.model.AssetEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteEntry(entry);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteEntry(className, classPK);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry fetchEntry(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.fetchEntry(entryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry fetchEntry(long groupId,
		java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.fetchEntry(groupId, classUuid);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry fetchEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.fetchEntry(className, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAncestorEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAncestorEntries(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getChildEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getChildEntries(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	@Override
	public int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntries(entryQuery);
	}

	@Override
	public int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntriesCount(entryQuery);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntry(entryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry getEntry(long groupId,
		java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntry(groupId, classUuid);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntry(className, classPK);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry getNextEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getNextEntry(entryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry getParentEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getParentEntry(entryId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry getPreviousEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getPreviousEntry(entryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry incrementViewCounter(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.incrementViewCounter(userId, className,
			classPK);
	}

	@Override
	public void incrementViewCounter(long userId, java.lang.String className,
		long classPK, int increment)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.incrementViewCounter(userId, className,
			classPK, increment);
	}

	@Override
	public void reindex(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> entries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.reindex(entries);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	String, String, int, int, int)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String keywords, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, keywords, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String keywords, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, keywords, status, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	String, String, String, String, String, String, int, boolean,
	int, int)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, boolean andSearch, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, userName, title, description, assetCategoryIds,
			assetTagNames, andSearch, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, long userId, java.lang.String className,
		java.lang.String userName, java.lang.String title,
		java.lang.String description, java.lang.String assetCategoryIds,
		java.lang.String assetTagNames, int status, boolean andSearch,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, userId,
			className, userName, title, description, assetCategoryIds,
			assetTagNames, status, andSearch, start, end);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	String, String, int, int, int)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, java.lang.String className, java.lang.String keywords,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, className,
			keywords, start, end);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.util.Date createDate, java.util.Date modifiedDate,
		java.lang.String className, long classPK, java.lang.String classUuid,
		long classTypeId, long[] categoryIds, java.lang.String[] tagNames,
		boolean visible, java.util.Date startDate, java.util.Date endDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, createDate,
			modifiedDate, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, visible, startDate, endDate, expirationDate,
			mimeType, title, description, summary, url, layoutUuid, height,
			width, priority, sync);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, categoryIds, tagNames);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	String, long, String, long, long[], String[], boolean, Date,
	Date, Date, String, String, String, String, String, String,
	int, int, Integer, boolean)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url,
		java.lang.String layoutUuid, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, classUuid, classTypeId, categoryIds, tagNames, visible,
			startDate, endDate, publishDate, expirationDate, mimeType, title,
			description, summary, url, layoutUuid, height, width, priority, sync);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	Date, Date, String, long, String, long, long[], String[],
	boolean, Date, Date, Date, String, String, String, String,
	String, String, int, int, Integer, boolean)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long classTypeId, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, java.lang.String layoutUuid, int height,
		int width, java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, classUuid, classTypeId, categoryIds, tagNames, visible,
			startDate, endDate, expirationDate, mimeType, title, description,
			summary, url, layoutUuid, height, width, priority, sync);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(className, classPK,
			publishDate, visible);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateEntry(
		java.lang.String className, long classPK, java.util.Date publishDate,
		java.util.Date expirationDate, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(className, classPK,
			publishDate, expirationDate, visible);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetEntry updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateVisible(className, classPK, visible);
	}

	@Override
	public void validate(long groupId, java.lang.String className,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.validate(groupId, className, categoryIds,
			tagNames);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public AssetEntryLocalService getWrappedAssetEntryLocalService() {
		return _assetEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
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