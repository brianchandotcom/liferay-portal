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
 * Provides the local service utility for CommercePriceListUserRel. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePriceListUserRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelLocalService
 * @see com.liferay.commerce.service.base.CommercePriceListUserRelLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceListUserRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceListUserRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce price list user rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	* @return the commerce price list user rel that was added
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel addCommercePriceListUserRel(
		com.liferay.commerce.model.CommercePriceListUserRel commercePriceListUserRel) {
		return getService().addCommercePriceListUserRel(commercePriceListUserRel);
	}

	/**
	* Creates a new commerce price list user rel with the primary key. Does not add the commerce price list user rel to the database.
	*
	* @param commercePriceListUserRelId the primary key for the new commerce price list user rel
	* @return the new commerce price list user rel
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel createCommercePriceListUserRel(
		long commercePriceListUserRelId) {
		return getService()
				   .createCommercePriceListUserRel(commercePriceListUserRelId);
	}

	/**
	* Deletes the commerce price list user rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	* @return the commerce price list user rel that was removed
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel deleteCommercePriceListUserRel(
		com.liferay.commerce.model.CommercePriceListUserRel commercePriceListUserRel) {
		return getService()
				   .deleteCommercePriceListUserRel(commercePriceListUserRel);
	}

	/**
	* Deletes the commerce price list user rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel that was removed
	* @throws PortalException if a commerce price list user rel with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel deleteCommercePriceListUserRel(
		long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommercePriceListUserRel(commercePriceListUserRelId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommercePriceListUserRel fetchCommercePriceListUserRel(
		long commercePriceListUserRelId) {
		return getService()
				   .fetchCommercePriceListUserRel(commercePriceListUserRelId);
	}

	/**
	* Returns the commerce price list user rel matching the UUID and group.
	*
	* @param uuid the commerce price list user rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel fetchCommercePriceListUserRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchCommercePriceListUserRelByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list user rel with the primary key.
	*
	* @param commercePriceListUserRelId the primary key of the commerce price list user rel
	* @return the commerce price list user rel
	* @throws PortalException if a commerce price list user rel with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel getCommercePriceListUserRel(
		long commercePriceListUserRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommercePriceListUserRel(commercePriceListUserRelId);
	}

	/**
	* Returns the commerce price list user rel matching the UUID and group.
	*
	* @param uuid the commerce price list user rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user rel
	* @throws PortalException if a matching commerce price list user rel could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel getCommercePriceListUserRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommercePriceListUserRelByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the commerce price list user rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @return the range of commerce price list user rels
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceListUserRel> getCommercePriceListUserRels(
		int start, int end) {
		return getService().getCommercePriceListUserRels(start, end);
	}

	/**
	* Returns all the commerce price list user rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user rels
	* @param companyId the primary key of the company
	* @return the matching commerce price list user rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceListUserRel> getCommercePriceListUserRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommercePriceListUserRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price list user rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price list user rels
	* @param end the upper bound of the range of commerce price list user rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price list user rels, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceListUserRel> getCommercePriceListUserRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListUserRel> orderByComparator) {
		return getService()
				   .getCommercePriceListUserRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price list user rels.
	*
	* @return the number of commerce price list user rels
	*/
	public static int getCommercePriceListUserRelsCount() {
		return getService().getCommercePriceListUserRelsCount();
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
	* Updates the commerce price list user rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserRel the commerce price list user rel
	* @return the commerce price list user rel that was updated
	*/
	public static com.liferay.commerce.model.CommercePriceListUserRel updateCommercePriceListUserRel(
		com.liferay.commerce.model.CommercePriceListUserRel commercePriceListUserRel) {
		return getService()
				   .updateCommercePriceListUserRel(commercePriceListUserRel);
	}

	public static CommercePriceListUserRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListUserRelLocalService, CommercePriceListUserRelLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommercePriceListUserRelLocalService.class);
}