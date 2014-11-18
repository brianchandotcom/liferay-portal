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
 * Provides the local service utility for AssetTagProperty. This utility wraps
 * {@link com.liferay.portlet.asset.service.impl.AssetTagPropertyLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPropertyLocalService
 * @see com.liferay.portlet.asset.service.base.AssetTagPropertyLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetTagPropertyLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetTagPropertyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetTagPropertyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset tag property to the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagProperty the asset tag property
	* @return the asset tag property that was added
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty addAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		return getService().addAssetTagProperty(assetTagProperty);
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
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty addTagProperty(
		long userId, long tagId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addTagProperty(userId, tagId, key, value);
	}

	/**
	* Creates a new asset tag property with the primary key. Does not add the asset tag property to the database.
	*
	* @param tagPropertyId the primary key for the new asset tag property
	* @return the new asset tag property
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty createAssetTagProperty(
		long tagPropertyId) {
		return getService().createAssetTagProperty(tagPropertyId);
	}

	/**
	* Deletes the asset tag property from the database. Also notifies the appropriate model listeners.
	*
	* @param assetTagProperty the asset tag property
	* @return the asset tag property that was removed
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty deleteAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		return getService().deleteAssetTagProperty(assetTagProperty);
	}

	/**
	* Deletes the asset tag property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property that was removed
	* @throws PortalException if a asset tag property with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty deleteAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetTagProperty(tagPropertyId);
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
	* Deletes the asset tag property with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	*/
	public static void deleteTagProperties(long tagId) {
		getService().deleteTagProperties(tagId);
	}

	/**
	* Deletes the asset tag property instance.
	*
	* @param tagProperty the asset tag property instance
	*/
	public static void deleteTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty tagProperty) {
		getService().deleteTagProperty(tagProperty);
	}

	/**
	* Deletes the asset tag property with the specified ID.
	*
	* @param tagPropertyId the primary key of the asset tag property instance
	* @throws PortalException if an asset tag property with the primary key
	could not be found
	*/
	public static void deleteTagProperty(long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteTagProperty(tagPropertyId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.asset.model.AssetTagProperty fetchAssetTagProperty(
		long tagPropertyId) {
		return getService().fetchAssetTagProperty(tagPropertyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getAssetTagProperties(
		int start, int end) {
		return getService().getAssetTagProperties(start, end);
	}

	/**
	* Returns the number of asset tag properties.
	*
	* @return the number of asset tag properties
	*/
	public static int getAssetTagPropertiesCount() {
		return getService().getAssetTagPropertiesCount();
	}

	/**
	* Returns the asset tag property with the primary key.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property
	* @throws PortalException if a asset tag property with the primary key could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty getAssetTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetTagProperty(tagPropertyId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns all the asset tag property instances.
	*
	* @return the asset tag property instances
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties() {
		return getService().getTagProperties();
	}

	/**
	* Returns all the asset tag property instances with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	* @return the matching asset tag properties
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagProperties(
		long tagId) {
		return getService().getTagProperties(tagId);
	}

	/**
	* Returns the asset tag property with the specified tag ID and key.
	*
	* @param tagId the primary key of the tag
	* @param key the key that refers to some value
	* @return the matching asset tag property
	* @throws PortalException if an asset tag property with the tag ID and key
	could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTagProperty(tagId, key);
	}

	/**
	* Returns the asset tag property with the specified ID.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the matching asset tag property
	* @throws PortalException if an asset tag property with the primary key
	could not be found
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty getTagProperty(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTagProperty(tagPropertyId);
	}

	/**
	* Returns asset tag property keys with the specified group
	*
	* @param groupId the primary key of the group
	* @return the matching asset tag property keys
	*/
	public static java.lang.String[] getTagPropertyKeys(long groupId) {
		return getService().getTagPropertyKeys(groupId);
	}

	/**
	* Returns asset tag properties with the specified group and key.
	*
	* @param groupId the primary key of the group
	* @param key the key that refers to some value
	* @return the matching asset tag properties
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> getTagPropertyValues(
		long groupId, java.lang.String key) {
		return getService().getTagPropertyValues(groupId, key);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the asset tag property in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetTagProperty the asset tag property
	* @return the asset tag property that was updated
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty updateAssetTagProperty(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		return getService().updateAssetTagProperty(assetTagProperty);
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
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateTagProperty(tagPropertyId, key, value);
	}

	public static AssetTagPropertyLocalService getService() {
		if (_service == null) {
			_service = (AssetTagPropertyLocalService)PortalBeanLocatorUtil.locate(AssetTagPropertyLocalService.class.getName());

			ReferenceRegistry.registerReference(AssetTagPropertyLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(AssetTagPropertyLocalService service) {
	}

	private static AssetTagPropertyLocalService _service;
}