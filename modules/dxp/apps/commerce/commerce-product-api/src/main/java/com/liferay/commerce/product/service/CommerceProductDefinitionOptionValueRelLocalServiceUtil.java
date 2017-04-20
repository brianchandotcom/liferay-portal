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
 * Provides the local service utility for CommerceProductDefinitionOptionValueRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelLocalService
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionValueRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce product definition option value rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was added
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return getService()
				   .addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionRelId,
			titleMap, priority, serviceContext);
	}

	/**
	* Creates a new commerce product definition option value rel with the primary key. Does not add the commerce product definition option value rel to the database.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key for the new commerce product definition option value rel
	* @return the new commerce product definition option value rel
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel createCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId) {
		return getService()
				   .createCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Deletes the commerce product definition option value rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	/**
	* Deletes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws PortalException if a commerce product definition option value rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel fetchCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId) {
		return getService()
				   .fetchCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Returns the commerce product definition option value rel matching the UUID and group.
	*
	* @param uuid the commerce product definition option value rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition option value rel, or <code>null</code> if a matching commerce product definition option value rel could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel fetchCommerceProductDefinitionOptionValueRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommerceProductDefinitionOptionValueRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the commerce product definition option value rel with the primary key.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel
	* @throws PortalException if a commerce product definition option value rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	/**
	* Returns the commerce product definition option value rel matching the UUID and group.
	*
	* @param uuid the commerce product definition option value rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition option value rel
	* @throws PortalException if a matching commerce product definition option value rel could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionValueRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the commerce product definition option value rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was updated
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
		return getService()
				   .updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId,
			titleMap, priority, serviceContext);
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
	* Returns the number of commerce product definition option value rels.
	*
	* @return the number of commerce product definition option value rels
	*/
	public static int getCommerceProductDefinitionOptionValueRelsCount() {
		return getService().getCommerceProductDefinitionOptionValueRelsCount();
	}

	public static int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId) {
		return getService()
				   .getCommerceProductDefinitionOptionValueRelsCount(commerceProductDefinitionOptionRelId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of commerce product definition option value rels
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		int start, int end) {
		return getService()
				   .getCommerceProductDefinitionOptionValueRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end) {
		return getService()
				   .getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getService()
				   .getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce product definition option value rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definition option value rels
	* @param companyId the primary key of the company
	* @return the matching commerce product definition option value rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceProductDefinitionOptionValueRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce product definition option value rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definition option value rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce product definition option value rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator) {
		return getService()
				   .getCommerceProductDefinitionOptionValueRelsByUuidAndCompanyId(uuid,
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

	public static CommerceProductDefinitionOptionValueRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionOptionValueRelLocalService, CommerceProductDefinitionOptionValueRelLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionOptionValueRelLocalService.class);
}