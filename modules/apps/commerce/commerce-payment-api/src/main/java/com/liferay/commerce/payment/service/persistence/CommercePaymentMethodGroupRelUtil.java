/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.persistence;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce payment method group rel service. This utility wraps <code>com.liferay.commerce.payment.service.persistence.impl.CommercePaymentMethodGroupRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelPersistence
 * @generated
 */
public class CommercePaymentMethodGroupRelUtil {

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
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		getPersistence().clearCache(commercePaymentMethodGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CommercePaymentMethodGroupRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommercePaymentMethodGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommercePaymentMethodGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommercePaymentMethodGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommercePaymentMethodGroupRel update(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		return getPersistence().update(commercePaymentMethodGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommercePaymentMethodGroupRel update(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commercePaymentMethodGroupRel, serviceContext);
	}

	/**
	 * Returns all the commerce payment method group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByGroupId(
		long groupId) {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the commerce payment method group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @return the range of matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce payment method group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel findByGroupId_First(
			long groupId,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first commerce payment method group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel, or <code>null</code> if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel findByGroupId_Last(
			long groupId,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel, or <code>null</code> if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the commerce payment method group rels before and after the current commerce payment method group rel in the ordered set where groupId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the current commerce payment method group rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel[] findByGroupId_PrevAndNext(
			long commercePaymentMethodGroupRelId, long groupId,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByGroupId_PrevAndNext(
			commercePaymentMethodGroupRelId, groupId, orderByComparator);
	}

	/**
	 * Returns all the commerce payment method group rels that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce payment method group rels that the user has permission to view
	 */
	public static List<CommercePaymentMethodGroupRel> filterFindByGroupId(
		long groupId) {

		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the commerce payment method group rels that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @return the range of matching commerce payment method group rels that the user has permission to view
	 */
	public static List<CommercePaymentMethodGroupRel> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rels that the user has permission to view
	 */
	public static List<CommercePaymentMethodGroupRel> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce payment method group rels before and after the current commerce payment method group rel in the ordered set of commerce payment method group rels that the user has permission to view where groupId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the current commerce payment method group rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel[]
			filterFindByGroupId_PrevAndNext(
				long commercePaymentMethodGroupRelId, long groupId,
				OrderByComparator<CommercePaymentMethodGroupRel>
					orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			commercePaymentMethodGroupRelId, groupId, orderByComparator);
	}

	/**
	 * Removes all the commerce payment method group rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of commerce payment method group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce payment method group rels
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of commerce payment method group rels that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce payment method group rels that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns all the commerce payment method group rels where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByG_A(
		long groupId, boolean active) {

		return getPersistence().findByG_A(groupId, active);
	}

	/**
	 * Returns a range of all the commerce payment method group rels where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @return the range of matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByG_A(
		long groupId, boolean active, int start, int end) {

		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().findByG_A(
			groupId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_A(
			groupId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce payment method group rel in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel findByG_A_First(
			long groupId, boolean active,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByG_A_First(
			groupId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce payment method group rel in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel, or <code>null</code> if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByG_A_First(
		long groupId, boolean active,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().fetchByG_A_First(
			groupId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel findByG_A_Last(
			long groupId, boolean active,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByG_A_Last(
			groupId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce payment method group rel in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel, or <code>null</code> if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByG_A_Last(
		long groupId, boolean active,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().fetchByG_A_Last(
			groupId, active, orderByComparator);
	}

	/**
	 * Returns the commerce payment method group rels before and after the current commerce payment method group rel in the ordered set where groupId = &#63; and active = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the current commerce payment method group rel
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel[] findByG_A_PrevAndNext(
			long commercePaymentMethodGroupRelId, long groupId, boolean active,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByG_A_PrevAndNext(
			commercePaymentMethodGroupRelId, groupId, active,
			orderByComparator);
	}

	/**
	 * Returns all the commerce payment method group rels that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the matching commerce payment method group rels that the user has permission to view
	 */
	public static List<CommercePaymentMethodGroupRel> filterFindByG_A(
		long groupId, boolean active) {

		return getPersistence().filterFindByG_A(groupId, active);
	}

	/**
	 * Returns a range of all the commerce payment method group rels that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @return the range of matching commerce payment method group rels that the user has permission to view
	 */
	public static List<CommercePaymentMethodGroupRel> filterFindByG_A(
		long groupId, boolean active, int start, int end) {

		return getPersistence().filterFindByG_A(groupId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels that the user has permissions to view where groupId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rels that the user has permission to view
	 */
	public static List<CommercePaymentMethodGroupRel> filterFindByG_A(
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().filterFindByG_A(
			groupId, active, start, end, orderByComparator);
	}

	/**
	 * Returns the commerce payment method group rels before and after the current commerce payment method group rel in the ordered set of commerce payment method group rels that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the current commerce payment method group rel
	 * @param groupId the group ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel[] filterFindByG_A_PrevAndNext(
			long commercePaymentMethodGroupRelId, long groupId, boolean active,
			OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().filterFindByG_A_PrevAndNext(
			commercePaymentMethodGroupRelId, groupId, active,
			orderByComparator);
	}

	/**
	 * Removes all the commerce payment method group rels where groupId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 */
	public static void removeByG_A(long groupId, boolean active) {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	 * Returns the number of commerce payment method group rels where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce payment method group rels
	 */
	public static int countByG_A(long groupId, boolean active) {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	 * Returns the number of commerce payment method group rels that the user has permission to view where groupId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param active the active
	 * @return the number of matching commerce payment method group rels that the user has permission to view
	 */
	public static int filterCountByG_A(long groupId, boolean active) {
		return getPersistence().filterCountByG_A(groupId, active);
	}

	/**
	 * Returns the commerce payment method group rel where groupId = &#63; and paymentIntegrationKey = &#63; or throws a <code>NoSuchPaymentMethodGroupRelException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param paymentIntegrationKey the payment integration key
	 * @return the matching commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel findByG_P(
			long groupId, String paymentIntegrationKey)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByG_P(groupId, paymentIntegrationKey);
	}

	/**
	 * Returns the commerce payment method group rel where groupId = &#63; and paymentIntegrationKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param paymentIntegrationKey the payment integration key
	 * @return the matching commerce payment method group rel, or <code>null</code> if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByG_P(
		long groupId, String paymentIntegrationKey) {

		return getPersistence().fetchByG_P(groupId, paymentIntegrationKey);
	}

	/**
	 * Returns the commerce payment method group rel where groupId = &#63; and paymentIntegrationKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param paymentIntegrationKey the payment integration key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce payment method group rel, or <code>null</code> if a matching commerce payment method group rel could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByG_P(
		long groupId, String paymentIntegrationKey, boolean useFinderCache) {

		return getPersistence().fetchByG_P(
			groupId, paymentIntegrationKey, useFinderCache);
	}

	/**
	 * Removes the commerce payment method group rel where groupId = &#63; and paymentIntegrationKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param paymentIntegrationKey the payment integration key
	 * @return the commerce payment method group rel that was removed
	 */
	public static CommercePaymentMethodGroupRel removeByG_P(
			long groupId, String paymentIntegrationKey)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().removeByG_P(groupId, paymentIntegrationKey);
	}

	/**
	 * Returns the number of commerce payment method group rels where groupId = &#63; and paymentIntegrationKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param paymentIntegrationKey the payment integration key
	 * @return the number of matching commerce payment method group rels
	 */
	public static int countByG_P(long groupId, String paymentIntegrationKey) {
		return getPersistence().countByG_P(groupId, paymentIntegrationKey);
	}

	/**
	 * Caches the commerce payment method group rel in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRel the commerce payment method group rel
	 */
	public static void cacheResult(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		getPersistence().cacheResult(commercePaymentMethodGroupRel);
	}

	/**
	 * Caches the commerce payment method group rels in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRels the commerce payment method group rels
	 */
	public static void cacheResult(
		List<CommercePaymentMethodGroupRel> commercePaymentMethodGroupRels) {

		getPersistence().cacheResult(commercePaymentMethodGroupRels);
	}

	/**
	 * Creates a new commerce payment method group rel with the primary key. Does not add the commerce payment method group rel to the database.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key for the new commerce payment method group rel
	 * @return the new commerce payment method group rel
	 */
	public static CommercePaymentMethodGroupRel create(
		long commercePaymentMethodGroupRelId) {

		return getPersistence().create(commercePaymentMethodGroupRelId);
	}

	/**
	 * Removes the commerce payment method group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the commerce payment method group rel
	 * @return the commerce payment method group rel that was removed
	 * @throws NoSuchPaymentMethodGroupRelException if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel remove(
			long commercePaymentMethodGroupRelId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().remove(commercePaymentMethodGroupRelId);
	}

	public static CommercePaymentMethodGroupRel updateImpl(
		CommercePaymentMethodGroupRel commercePaymentMethodGroupRel) {

		return getPersistence().updateImpl(commercePaymentMethodGroupRel);
	}

	/**
	 * Returns the commerce payment method group rel with the primary key or throws a <code>NoSuchPaymentMethodGroupRelException</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the commerce payment method group rel
	 * @return the commerce payment method group rel
	 * @throws NoSuchPaymentMethodGroupRelException if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel findByPrimaryKey(
			long commercePaymentMethodGroupRelId)
		throws com.liferay.commerce.payment.exception.
			NoSuchPaymentMethodGroupRelException {

		return getPersistence().findByPrimaryKey(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns the commerce payment method group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelId the primary key of the commerce payment method group rel
	 * @return the commerce payment method group rel, or <code>null</code> if a commerce payment method group rel with the primary key could not be found
	 */
	public static CommercePaymentMethodGroupRel fetchByPrimaryKey(
		long commercePaymentMethodGroupRelId) {

		return getPersistence().fetchByPrimaryKey(
			commercePaymentMethodGroupRelId);
	}

	/**
	 * Returns all the commerce payment method group rels.
	 *
	 * @return the commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce payment method group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @return the range of commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rels
	 * @param end the upper bound of the range of commerce payment method group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment method group rels
	 */
	public static List<CommercePaymentMethodGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce payment method group rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce payment method group rels.
	 *
	 * @return the number of commerce payment method group rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommercePaymentMethodGroupRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommercePaymentMethodGroupRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommercePaymentMethodGroupRelPersistence
		_persistence;

}