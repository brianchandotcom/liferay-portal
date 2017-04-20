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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceProductOption. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductOptionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceProductOptionLocalService
 * @see com.liferay.commerce.product.service.base.CommerceProductOptionLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductOptionLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductOptionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductOptionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce product option to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOption the commerce product option
	* @return the commerce product option that was added
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption addCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption) {
		return getService().addCommerceProductOption(commerceProductOption);
	}

	public static com.liferay.commerce.product.model.CommerceProductOption addCommerceProductOption(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductOption(nameMap, descriptionMap,
			ddmFormFieldTypeName, serviceContext);
	}

	/**
	* Creates a new commerce product option with the primary key. Does not add the commerce product option to the database.
	*
	* @param commerceProductOptionId the primary key for the new commerce product option
	* @return the new commerce product option
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption createCommerceProductOption(
		long commerceProductOptionId) {
		return getService().createCommerceProductOption(commerceProductOptionId);
	}

	/**
	* Deletes the commerce product option from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOption the commerce product option
	* @return the commerce product option that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption deleteCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceProductOption(commerceProductOption);
	}

	/**
	* Deletes the commerce product option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option that was removed
	* @throws PortalException if a commerce product option with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption deleteCommerceProductOption(
		long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceProductOption(commerceProductOptionId);
	}

	public static com.liferay.commerce.product.model.CommerceProductOption fetchCommerceProductOption(
		long commerceProductOptionId) {
		return getService().fetchCommerceProductOption(commerceProductOptionId);
	}

	/**
	* Returns the commerce product option matching the UUID and group.
	*
	* @param uuid the commerce product option's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption fetchCommerceProductOptionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommerceProductOptionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the commerce product option with the primary key.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option
	* @throws PortalException if a commerce product option with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption getCommerceProductOption(
		long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceProductOption(commerceProductOptionId);
	}

	/**
	* Returns the commerce product option matching the UUID and group.
	*
	* @param uuid the commerce product option's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product option
	* @throws PortalException if a matching commerce product option could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption getCommerceProductOptionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductOptionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the commerce product option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOption the commerce product option
	* @return the commerce product option that was updated
	*/
	public static com.liferay.commerce.product.model.CommerceProductOption updateCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption) {
		return getService().updateCommerceProductOption(commerceProductOption);
	}

	public static com.liferay.commerce.product.model.CommerceProductOption updateCommerceProductOption(
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductOption(commerceProductOptionId,
			nameMap, descriptionMap, ddmFormFieldTypeName, serviceContext);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce product options.
	*
	* @return the number of commerce product options
	*/
	public static int getCommerceProductOptionsCount() {
		return getService().getCommerceProductOptionsCount();
	}

	public static int getCommerceProductOptionsCount(long groupId) {
		return getService().getCommerceProductOptionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of commerce product options
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		int start, int end) {
		return getService().getCommerceProductOptions(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end) {
		return getService().getCommerceProductOptions(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOption> orderByComparator) {
		return getService()
				   .getCommerceProductOptions(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the commerce product options matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product options
	* @param companyId the primary key of the company
	* @return the matching commerce product options, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceProductOptionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of commerce product options matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product options
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce product options, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOption> orderByComparator) {
		return getService()
				   .getCommerceProductOptionsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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

	public static CommerceProductOptionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductOptionLocalService, CommerceProductOptionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductOptionLocalService.class);
}