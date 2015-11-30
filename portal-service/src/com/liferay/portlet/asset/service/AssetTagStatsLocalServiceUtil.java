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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for AssetTagStats. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetTagStatsLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStatsLocalService
 * @see com.liferay.portlet.asset.service.base.AssetTagStatsLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagStatsLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetTagStatsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagStatsLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset tag stats to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagStats the asset tag stats
	* @return the asset tag stats that was added
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats addAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return getService().addAssetTagStats(assetTagStats);
	}

	/**
	* Adds an asset tag statistics instance.
	*
	* @param tagId the primary key of the tag
	* @param classNameId the asset entry's class name ID
	* @return the asset tag statistics instance
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats addTagStats(
		long tagId, long classNameId) {
		return getService().addTagStats(tagId, classNameId);
	}

	/**
	* Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	*
	* @param tagStatsId the primary key for the new asset tag stats
	* @return the new asset tag stats
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats createAssetTagStats(
		long tagStatsId) {
		return getService().createAssetTagStats(tagStatsId);
	}

	/**
	* Deletes the asset tag stats from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagStats the asset tag stats
	* @return the asset tag stats that was removed
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats deleteAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return getService().deleteAssetTagStats(assetTagStats);
	}

	/**
	* Deletes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats that was removed
	* @throws PortalException if a asset tag stats with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats deleteAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetTagStats(tagStatsId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the asset tag statistics instance.
	*
	* @param tagStats the asset tag statistics instance
	*/
	public static void deleteTagStats(
		com.liferay.portlet.asset.model.AssetTagStats tagStats) {
		getService().deleteTagStats(tagStats);
	}

	/**
	* Deletes the asset tag statistics instance matching the tag statistics ID.
	*
	* @param tagStatsId the primary key of the asset tag statistics instance
	*/
	public static void deleteTagStats(long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTagStats(tagStatsId);
	}

	/**
	* Deletes all asset tag statistics instances associated with the asset
	* entry matching the class name ID.
	*
	* @param classNameId the asset entry's class name ID
	*/
	public static void deleteTagStatsByClassNameId(long classNameId) {
		getService().deleteTagStatsByClassNameId(classNameId);
	}

	/**
	* Deletes all asset tag statistics instances associated with the tag.
	*
	* @param tagId the primary key of the tag
	*/
	public static void deleteTagStatsByTagId(long tagId) {
		getService().deleteTagStatsByTagId(tagId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.portlet.asset.model.AssetTagStats fetchAssetTagStats(
		long tagStatsId) {
		return getService().fetchAssetTagStats(tagStatsId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the asset tag stats with the primary key.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats
	* @throws PortalException if a asset tag stats with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats getAssetTagStats(
		long tagStatsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetTagStats(tagStatsId);
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
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getAssetTagStatses(
		int start, int end) {
		return getService().getAssetTagStatses(start, end);
	}

	/**
	* Returns the number of asset tag statses.
	*
	* @return the number of asset tag statses
	*/
	public static int getAssetTagStatsesCount() {
		return getService().getAssetTagStatsesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
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
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagStats> getTagStats(
		long classNameId, int start, int end) {
		return getService().getTagStats(classNameId, start, end);
	}

	/**
	* Returns the asset tag statistics instance with the tag and asset entry
	* matching the class name ID
	*
	* @param tagId the primary key of the tag
	* @param classNameId the asset entry's class name ID
	* @return Returns the asset tag statistics instance with the tag and asset
	entry  matching the class name ID
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats getTagStats(
		long tagId, long classNameId) {
		return getService().getTagStats(tagId, classNameId);
	}

	/**
	* Updates the asset tag stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTagStats the asset tag stats
	* @return the asset tag stats that was updated
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats updateAssetTagStats(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return getService().updateAssetTagStats(assetTagStats);
	}

	/**
	* Updates the asset tag statistics instance.
	*
	* @param tagId the primary key of the tag
	* @param classNameId the asset entry's class name ID
	* @return the updated asset tag statistics instance
	*/
	public static com.liferay.portlet.asset.model.AssetTagStats updateTagStats(
		long tagId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateTagStats(tagId, classNameId);
	}

	public static AssetTagStatsLocalService getService() {
		if (_service == null) {
			_service = (AssetTagStatsLocalService)PortalBeanLocatorUtil.locate(AssetTagStatsLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetTagStatsLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(AssetTagStatsLocalService service) {
	}

	private static AssetTagStatsLocalService _service;
}