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
 * Provides the local service utility for CommerceProductDefinitionOptionRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelLocalService
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce product definition option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	* @return the commerce product definition option rel that was added
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		return getService()
				   .addCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductDefinitionOptionRel(commerceProductDefinitionId,
			commerceProductOptionId, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductDefinitionOptionRel(commerceProductDefinitionId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, skuContributor,
			serviceContext);
	}

	/**
	* Creates a new commerce product definition option rel with the primary key. Does not add the commerce product definition option rel to the database.
	*
	* @param commerceProductDefinitionOptionRelId the primary key for the new commerce product definition option rel
	* @return the new commerce product definition option rel
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel createCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId) {
		return getService()
				   .createCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	/**
	* Deletes the commerce product definition option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	* @return the commerce product definition option rel that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	/**
	* Deletes the commerce product definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel that was removed
	* @throws PortalException if a commerce product definition option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel fetchCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId) {
		return getService()
				   .fetchCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the commerce product definition option rel matching the UUID and group.
	*
	* @param uuid the commerce product definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel fetchCommerceProductDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommerceProductDefinitionOptionRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the commerce product definition option rel with the primary key.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel
	* @throws PortalException if a commerce product definition option rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel getCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the commerce product definition option rel matching the UUID and group.
	*
	* @param uuid the commerce product definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition option rel
	* @throws PortalException if a matching commerce product definition option rel could not be found
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel getCommerceProductDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductDefinitionOptionRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the commerce product definition option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	* @return the commerce product definition option rel that was updated
	*/
	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		return getService()
				   .updateCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	public static com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId,
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, skuContributor,
			serviceContext);
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
	* Returns the number of commerce product definition option rels.
	*
	* @return the number of commerce product definition option rels
	*/
	public static int getCommerceProductDefinitionOptionRelsCount() {
		return getService().getCommerceProductDefinitionOptionRelsCount();
	}

	public static int getCommerceProductDefinitionOptionRelsCount(
		long commerceProductDefinitionId) {
		return getService()
				   .getCommerceProductDefinitionOptionRelsCount(commerceProductDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce product definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of commerce product definition option rels
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		int start, int end) {
		return getService().getCommerceProductDefinitionOptionRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end) {
		return getService()
				   .getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator) {
		return getService()
				   .getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce product definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definition option rels
	* @param companyId the primary key of the company
	* @return the matching commerce product definition option rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce product definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definition option rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce product definition option rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator) {
		return getService()
				   .getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(uuid,
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

	public static void deleteCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceProductDefinitionOptionRels(commerceProductDefinitionId);
	}

	public static CommerceProductDefinitionOptionRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionOptionRelLocalService, CommerceProductDefinitionOptionRelLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionOptionRelLocalService.class);
}