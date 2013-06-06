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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetTagStatsLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStatsLocalService
 * @generated
 */
public class AssetTagStatsLocalServiceWrapper
	implements AssetTagStatsLocalService,
		ServiceWrapper<AssetTagStatsLocalService> {
	public AssetTagStatsLocalServiceWrapper(
		AssetTagStatsLocalService assetTagStatsLocalService) {
		_assetTagStatsLocalService = assetTagStatsLocalService;
	}

	/**
	* Adds the asset tag stats to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagStats the asset tag stats
	* @return the asset tag stats that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats addAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.addAssetTagStats(assetTagStats);
	}

	/**
	* Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	*
	* @param tagStatsId the primary key for the new asset tag stats
	* @return the new asset tag stats
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId) {
		return _assetTagStatsLocalService.createAssetTagStats(tagStatsId);
	}

	/**
	* Deletes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats that was removed
	* @throws PortalException if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats deleteAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.deleteAssetTagStats(tagStatsId);
	}

	/**
	* Deletes the asset tag stats from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagStats the asset tag stats
	* @return the asset tag stats that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.deleteAssetTagStats(assetTagStats);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetTagStatsLocalService.dynamicQuery();
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
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetTagStatsLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _assetTagStatsLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTagStats fetchAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.fetchAssetTagStats(tagStatsId);
	}

	/**
	* Returns the asset tag stats with the primary key.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats
	* @throws PortalException if a asset tag stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getAssetTagStats(tagStatsId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getAssetTagStatses(start, end);
	}

	/**
	* Returns the number of asset tag statses.
	*
	* @return the number of asset tag statses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetTagStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getAssetTagStatsesCount();
	}

	/**
	* Updates the asset tag stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTagStats the asset tag stats
	* @return the asset tag stats that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.updateAssetTagStats(assetTagStats);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _assetTagStatsLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetTagStatsLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds an asset tag statistics instance.
	*
	* @param tagId the primary key of the tag
	* @param classNameId the asset entry's class name ID
	* @return the asset tag statistics instance
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.addTagStats(tagId, classNameId);
	}

	/**
	* Deletes the asset tag statistics instance.
	*
	* @param tagStats the asset tag statistics instance
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTagStats(
		com.liferay.portlet.asset.model.AssetTagStats tagStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteTagStats(tagStats);
	}

	/**
	* Deletes the asset tag statistics instance matching the tag statistics ID.
	*
	* @param tagStatsId the primary key of the asset tag statistics instance
	* @throws PortalException if the assetTagStats with the primary key could
	not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTagStats(long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteTagStats(tagStatsId);
	}

	/**
	* Deletes all asset tag statistics instances associated with the asset
	* entry matching the class name ID.
	*
	* @param classNameId the asset entry's class name ID
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTagStatsByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteTagStatsByClassNameId(classNameId);
	}

	/**
	* Deletes all asset tag statistics instances associated with the tag.
	*
	* @param tagId the primary key of the tag
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteTagStatsByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagStatsLocalService.deleteTagStatsByTagId(tagId);
	}

	/**
	* Returns a range of all the asset tag statistics instances associated with
	* the asset entry matching the class name ID.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param classNameId the asset entry's class name ID
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of asset tag statistics associated with the asset entry
	matching the class name ID
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getTagStats(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getTagStats(classNameId, start, end);
	}

	/**
	* Returns the asset tag statistics instance with the tag and asset entry
	* matching the class name ID
	*
	* @param tagId the primary key of the tag
	* @param classNameId the asset entry's class name ID
	* @return Returns the asset tag statistics instance with the tag and asset
	entry  matching the class name ID
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.getTagStats(tagId, classNameId);
	}

	/**
	* Updates the asset tag statistics instance.
	*
	* @param tagId the primary key of the tag
	* @param classNameId the asset entry's class name ID
	* @return the updated asset tag statistics instance
	* @throws PortalException if an asset tag with the tag ID could not be
	found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagStatsLocalService.updateTagStats(tagId, classNameId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetTagStatsLocalService getWrappedAssetTagStatsLocalService() {
		return _assetTagStatsLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetTagStatsLocalService(
		AssetTagStatsLocalService assetTagStatsLocalService) {
		_assetTagStatsLocalService = assetTagStatsLocalService;
	}

	@Override
	public AssetTagStatsLocalService getWrappedService() {
		return _assetTagStatsLocalService;
	}

	@Override
	public void setWrappedService(
		AssetTagStatsLocalService assetTagStatsLocalService) {
		_assetTagStatsLocalService = assetTagStatsLocalService;
	}

	private AssetTagStatsLocalService _assetTagStatsLocalService;
}