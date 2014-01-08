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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for AssetCategoryProperty. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyLocalService
 * @see com.liferay.portlet.asset.service.base.AssetCategoryPropertyLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetCategoryPropertyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetCategoryPropertyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset category property to the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property
	* @return the asset category property that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty addAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetCategoryProperty(assetCategoryProperty);
	}

	/**
	* Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	*
	* @param categoryPropertyId the primary key for the new asset category property
	* @return the new asset category property
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty createAssetCategoryProperty(
		long categoryPropertyId) {
		return getService().createAssetCategoryProperty(categoryPropertyId);
	}

	/**
	* Deletes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property that was removed
	* @throws PortalException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty deleteAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetCategoryProperty(categoryPropertyId);
	}

	/**
	* Deletes the asset category property from the database. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property
	* @return the asset category property that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty deleteAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteAssetCategoryProperty(assetCategoryProperty);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty fetchAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchAssetCategoryProperty(categoryPropertyId);
	}

	/**
	* Returns the asset category property with the primary key.
	*
	* @param categoryPropertyId the primary key of the asset category property
	* @return the asset category property
	* @throws PortalException if a asset category property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty getAssetCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryProperty(categoryPropertyId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset category properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset category properties
	* @param end the upper bound of the range of asset category properties (not inclusive)
	* @return the range of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getAssetCategoryProperties(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryProperties(start, end);
	}

	/**
	* Returns the number of asset category properties.
	*
	* @return the number of asset category properties
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetCategoryPropertiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetCategoryPropertiesCount();
	}

	/**
	* Updates the asset category property in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetCategoryProperty the asset category property
	* @return the asset category property that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateAssetCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetCategoryProperty(assetCategoryProperty);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty addCategoryProperty(
		long userId, long categoryId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addCategoryProperty(userId, categoryId, key, value);
	}

	public static void deleteCategoryProperties(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategoryProperties(entryId);
	}

	public static void deleteCategoryProperty(
		com.liferay.portlet.asset.model.AssetCategoryProperty categoryProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategoryProperty(categoryProperty);
	}

	public static void deleteCategoryProperty(long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategoryProperty(categoryPropertyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperties();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryProperties(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperties(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperty(categoryPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty getCategoryProperty(
		long categoryId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryProperty(categoryId, key);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoryPropertyValues(groupId, key);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long categoryPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCategoryProperty(categoryPropertyId, key, value);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryProperty updateCategoryProperty(
		long userId, long categoryPropertyId, java.lang.String key,
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCategoryProperty(userId, categoryPropertyId, key,
			value);
	}

	public static AssetCategoryPropertyLocalService getService() {
		if (_service == null) {
			_service = (AssetCategoryPropertyLocalService)PortalBeanLocatorUtil.locate(AssetCategoryPropertyLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryPropertyLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(AssetCategoryPropertyLocalService service) {
	}

	private static AssetCategoryPropertyLocalService _service;
}