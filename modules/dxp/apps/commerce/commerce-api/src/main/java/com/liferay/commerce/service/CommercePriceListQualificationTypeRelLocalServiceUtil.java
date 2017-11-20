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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePriceListQualificationTypeRel. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelLocalService
 * @see com.liferay.commerce.service.base.CommercePriceListQualificationTypeRelLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce price list qualification type rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was added
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		com.liferay.commerce.model.CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return getService()
				   .addCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRel);
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePriceListQualificationTypeRel(commercePriceListId,
			commercePriceListQualificationType, order, serviceContext);
	}

	/**
	* Creates a new commerce price list qualification type rel with the primary key. Does not add the commerce price list qualification type rel to the database.
	*
	* @param commercePriceListQualificationTypeRelId the primary key for the new commerce price list qualification type rel
	* @return the new commerce price list qualification type rel
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel createCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId) {
		return getService()
				   .createCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	/**
	* Deletes the commerce price list qualification type rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was removed
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel deleteCommercePriceListQualificationTypeRel(
		com.liferay.commerce.model.CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return getService()
				   .deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRel);
	}

	/**
	* Deletes the commerce price list qualification type rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was removed
	* @throws PortalException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel deleteCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	public static void deleteCommercePriceListQualificationTypeRels(
		long commercePriceListId) {
		getService()
			.deleteCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId) {
		return getService()
				   .fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		return getService()
				   .fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationType,
			commercePriceListId);
	}

	/**
	* Returns the commerce price list qualification type rel matching the UUID and group.
	*
	* @param uuid the commerce price list qualification type rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommercePriceListQualificationTypeRelByUuidAndGroupId(uuid,
			groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list qualification type rel with the primary key.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel
	* @throws PortalException if a commerce price list qualification type rel with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel getCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	/**
	* Returns the commerce price list qualification type rel matching the UUID and group.
	*
	* @param uuid the commerce price list qualification type rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list qualification type rel
	* @throws PortalException if a matching commerce price list qualification type rel could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel getCommercePriceListQualificationTypeRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommercePriceListQualificationTypeRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of commerce price list qualification type rels
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		int start, int end) {
		return getService().getCommercePriceListQualificationTypeRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId) {
		return getService()
				   .getCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		return getService()
				   .getCommercePriceListQualificationTypeRels(commercePriceListId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce price list qualification type rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list qualification type rels
	* @param companyId the primary key of the company
	* @return the matching commerce price list qualification type rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price list qualification type rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list qualification type rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price list qualification type rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		return getService()
				   .getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price list qualification type rels.
	*
	* @return the number of commerce price list qualification type rels
	*/
	public static int getCommercePriceListQualificationTypeRelsCount() {
		return getService().getCommercePriceListQualificationTypeRelsCount();
	}

	public static int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) {
		return getService()
				   .getCommercePriceListQualificationTypeRelsCount(commercePriceListId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce price list qualification type rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was updated
	*/
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		com.liferay.commerce.model.CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return getService()
				   .updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRel);
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId,
			order, serviceContext);
	}

	public static CommercePriceListQualificationTypeRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListQualificationTypeRelLocalService, CommercePriceListQualificationTypeRelLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommercePriceListQualificationTypeRelLocalService.class);
}