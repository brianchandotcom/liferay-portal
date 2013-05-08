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
 * Provides a wrapper for {@link AssetTagPropertyLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyLocalService
 * @generated
 */
public class AssetTagPropertyLocalServiceWrapper
	implements AssetTagPropertyLocalService,
		ServiceWrapper<AssetTagPropertyLocalService> {
	public AssetTagPropertyLocalServiceWrapper(
		AssetTagPropertyLocalService assetTagPropertyLocalService) {
		_assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	/**
	* Adds the asset tag property to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagProperty the asset tag property
	* @return the asset tag property that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty addAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.addAssetTagProperty(assetTagProperty);
	}

	/**
	* Creates a new asset tag property with the primary key. Does not add the asset tag property to the database.
	*
	* @param tagPropertyId the primary key for the new asset tag property
	* @return the new asset tag property
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty createAssetTagProperty(
		long tagPropertyId) {
		return _assetTagPropertyLocalService.createAssetTagProperty(tagPropertyId);
	}

	/**
	* Deletes the asset tag property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property that was removed
	* @throws PortalException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty deleteAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.deleteAssetTagProperty(tagPropertyId);
	}

	/**
	* Deletes the asset tag property from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagProperty the asset tag property
	* @return the asset tag property that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty deleteAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.deleteAssetTagProperty(assetTagProperty);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetTagPropertyLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.asset.model.AssetTagProperty fetchAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.fetchAssetTagProperty(tagPropertyId);
	}

	/**
	* Returns the asset tag property with the primary key.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property
	* @throws PortalException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty getAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getAssetTagProperty(tagPropertyId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset tag properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @return the range of asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getAssetTagProperties(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getAssetTagProperties(start, end);
	}

	/**
	* Returns the number of asset tag properties.
	*
	* @return the number of asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetTagPropertiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getAssetTagPropertiesCount();
	}

	/**
	* Updates the asset tag property in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTagProperty the asset tag property
	* @return the asset tag property that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty updateAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.updateAssetTagProperty(assetTagProperty);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _assetTagPropertyLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetTagPropertyLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds an asset tag property.
	*
	* @param userId the primary key of the user
	* @param tagId the primary key of the tag
	* @param key the key to be associated to the value
	* @param value the value to which the key will refer
	* @return the created asset tag property
	* @throws PortalException if a user with the primary key could not be
	found, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long userId, long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.addTagProperty(userId, tagId, key,
			value);
	}

	/**
	* Deletes the asset tag property with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTagProperties(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteTagProperties(tagId);
	}

	/**
	* Deletes the asset tag property instance.
	*
	* @param tagProperty the asset tag property instance
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty tagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteTagProperty(tagProperty);
	}

	/**
	* Deletes the asset tag property with the specified ID.
	*
	* @param tagPropertyId the primary key of the asset tag property instance
	* @throws PortalException if an asset tag property with the primary key
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetTagPropertyLocalService.deleteTagProperty(tagPropertyId);
	}

	/**
	* Returns all the asset tag property instances.
	*
	* @return the asset tag property instances
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperties();
	}

	/**
	* Returns all the asset tag property instances with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperties(tagId);
	}

	/**
	* Returns the asset tag property with the specified ID.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the matching asset tag property
	* @throws PortalException if an asset tag property with the primary key
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperty(tagPropertyId);
	}

	/**
	* Returns the asset tag property with the specified tag ID and key.
	*
	* @param tagId the primary key of the tag
	* @param key the key that refers to some value
	* @return the matching asset tag property
	* @throws PortalException if an asset tag property with the tag ID and key
	could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagProperty(tagId, key);
	}

	/**
	* Returns asset tag property keys with the specified group
	*
	* @param groupId the primary key of the group
	* @return the matching asset tag property keys
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String[] getTagPropertyKeys(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagPropertyKeys(groupId);
	}

	/**
	* Returns asset tag properties with the specified group and key.
	*
	* @param groupId the primary key of the group
	* @param key the key that refers to some value
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long groupId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.getTagPropertyValues(groupId, key);
	}

	/**
	* Updates the asset tag property.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @param key the new key to be associated to the value
	* @param value the new value to which the key will refer
	* @return the updated asset tag property
	* @throws PortalException if an asset tag property with the primary key
	could not be found, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetTagPropertyLocalService.updateTagProperty(tagPropertyId,
			key, value);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetTagPropertyLocalService getWrappedAssetTagPropertyLocalService() {
		return _assetTagPropertyLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetTagPropertyLocalService(
		AssetTagPropertyLocalService assetTagPropertyLocalService) {
		_assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	public AssetTagPropertyLocalService getWrappedService() {
		return _assetTagPropertyLocalService;
	}

	public void setWrappedService(
		AssetTagPropertyLocalService assetTagPropertyLocalService) {
		_assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	private AssetTagPropertyLocalService _assetTagPropertyLocalService;
}