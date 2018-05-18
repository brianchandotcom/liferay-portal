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

package com.liferay.commerce.notification.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce notification template user segment rel service. This utility wraps {@link com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationTemplateUserSegmentRelPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelPersistence
 * @see com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationTemplateUserSegmentRelPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		getPersistence().clearCache(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceNotificationTemplateUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceNotificationTemplateUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceNotificationTemplateUserSegmentRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceNotificationTemplateUserSegmentRel update(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		return getPersistence()
				   .update(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceNotificationTemplateUserSegmentRel update(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceNotificationTemplateUserSegmentRel,
			serviceContext);
	}

	/**
	* Returns all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns a range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the commerce notification template user segment rels before and after the current commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the current commerce notification template user segment rel
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceNotificationTemplateUserSegmentRelId,
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_PrevAndNext(commerceNotificationTemplateUserSegmentRelId,
			commerceUserSegmentEntryId, orderByComparator);
	}

	/**
	* Removes all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public static void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		getPersistence()
			.removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns the number of commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce notification template user segment rels
	*/
	public static int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @return the matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Returns a range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel findByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByCommerceNotificationTemplateId_First(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceNotificationTemplateId_First(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel findByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByCommerceNotificationTemplateId_Last(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceNotificationTemplateId_Last(commerceNotificationTemplateId,
			orderByComparator);
	}

	/**
	* Returns the commerce notification template user segment rels before and after the current commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the current commerce notification template user segment rel
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel[] findByCommerceNotificationTemplateId_PrevAndNext(
		long commerceNotificationTemplateUserSegmentRelId,
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByCommerceNotificationTemplateId_PrevAndNext(commerceNotificationTemplateUserSegmentRelId,
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	* Removes all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63; from the database.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	*/
	public static void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		getPersistence()
			.removeByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Returns the number of commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @return the number of matching commerce notification template user segment rels
	*/
	public static int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		return getPersistence()
				   .countByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	/**
	* Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or throws a {@link NoSuchNotificationTemplateUserSegmentRelException} if it could not be found.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel findByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByC_C(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId) {
		return getPersistence()
				   .fetchByC_C(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(commerceNotificationTemplateId,
			commerceUserSegmentEntryId, retrieveFromCache);
	}

	/**
	* Removes the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the commerce notification template user segment rel that was removed
	*/
	public static CommerceNotificationTemplateUserSegmentRel removeByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .removeByC_C(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	/**
	* Returns the number of commerce notification template user segment rels where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce notification template user segment rels
	*/
	public static int countByC_C(long commerceNotificationTemplateId,
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .countByC_C(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	/**
	* Caches the commerce notification template user segment rel in the entity cache if it is enabled.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	*/
	public static void cacheResult(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		getPersistence().cacheResult(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	* Caches the commerce notification template user segment rels in the entity cache if it is enabled.
	*
	* @param commerceNotificationTemplateUserSegmentRels the commerce notification template user segment rels
	*/
	public static void cacheResult(
		List<CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels) {
		getPersistence().cacheResult(commerceNotificationTemplateUserSegmentRels);
	}

	/**
	* Creates a new commerce notification template user segment rel with the primary key. Does not add the commerce notification template user segment rel to the database.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key for the new commerce notification template user segment rel
	* @return the new commerce notification template user segment rel
	*/
	public static CommerceNotificationTemplateUserSegmentRel create(
		long commerceNotificationTemplateUserSegmentRelId) {
		return getPersistence()
				   .create(commerceNotificationTemplateUserSegmentRelId);
	}

	/**
	* Removes the commerce notification template user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was removed
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel remove(
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .remove(commerceNotificationTemplateUserSegmentRelId);
	}

	public static CommerceNotificationTemplateUserSegmentRel updateImpl(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		return getPersistence()
				   .updateImpl(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	* Returns the commerce notification template user segment rel with the primary key or throws a {@link NoSuchNotificationTemplateUserSegmentRelException} if it could not be found.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel findByPrimaryKey(
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException {
		return getPersistence()
				   .findByPrimaryKey(commerceNotificationTemplateUserSegmentRelId);
	}

	/**
	* Returns the commerce notification template user segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel, or <code>null</code> if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static CommerceNotificationTemplateUserSegmentRel fetchByPrimaryKey(
		long commerceNotificationTemplateUserSegmentRelId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceNotificationTemplateUserSegmentRelId);
	}

	public static java.util.Map<java.io.Serializable, CommerceNotificationTemplateUserSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce notification template user segment rels.
	*
	* @return the commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce notification template user segment rels
	*/
	public static List<CommerceNotificationTemplateUserSegmentRel> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce notification template user segment rels from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce notification template user segment rels.
	*
	* @return the number of commerce notification template user segment rels
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceNotificationTemplateUserSegmentRelPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationTemplateUserSegmentRelPersistence, CommerceNotificationTemplateUserSegmentRelPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationTemplateUserSegmentRelPersistence.class);

		ServiceTracker<CommerceNotificationTemplateUserSegmentRelPersistence, CommerceNotificationTemplateUserSegmentRelPersistence> serviceTracker =
			new ServiceTracker<CommerceNotificationTemplateUserSegmentRelPersistence, CommerceNotificationTemplateUserSegmentRelPersistence>(bundle.getBundleContext(),
				CommerceNotificationTemplateUserSegmentRelPersistence.class,
				null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}