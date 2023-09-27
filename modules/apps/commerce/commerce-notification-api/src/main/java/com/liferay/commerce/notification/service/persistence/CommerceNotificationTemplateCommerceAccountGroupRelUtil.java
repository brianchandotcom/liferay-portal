/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence;

import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce notification template commerce account group rel service. This utility wraps <code>com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationTemplateCommerceAccountGroupRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRelPersistence
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelUtil {

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
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		getPersistence().clearCache(
			commerceNotificationTemplateCommerceAccountGroupRel);
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
	public static Map
		<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel update(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		return getPersistence().update(
			commerceNotificationTemplateCommerceAccountGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel update(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceNotificationTemplateCommerceAccountGroupRel,
			serviceContext);
	}

	/**
	 * Returns all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns a range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceNotificationTemplateId_First(
				long commerceNotificationTemplateId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByCommerceNotificationTemplateId_First(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceNotificationTemplateId_First(
			long commerceNotificationTemplateId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().fetchByCommerceNotificationTemplateId_First(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceNotificationTemplateId_Last(
				long commerceNotificationTemplateId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByCommerceNotificationTemplateId_Last(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceNotificationTemplateId_Last(
			long commerceNotificationTemplateId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().fetchByCommerceNotificationTemplateId_Last(
			commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Returns the commerce notification template commerce account group rels before and after the current commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the current commerce notification template commerce account group rel
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel[]
			findByCommerceNotificationTemplateId_PrevAndNext(
				long commerceNotificationTemplateCommerceAccountGroupRelId,
				long commerceNotificationTemplateId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().
			findByCommerceNotificationTemplateId_PrevAndNext(
				commerceNotificationTemplateCommerceAccountGroupRelId,
				commerceNotificationTemplateId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	public static void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		getPersistence().removeByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	public static int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		return getPersistence().countByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
	}

	/**
	 * Returns all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Returns a range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByCommerceAccountGroupId_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupId_First(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceAccountGroupId_Last(
				long commerceAccountGroupId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByCommerceAccountGroupId_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().fetchByCommerceAccountGroupId_Last(
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Returns the commerce notification template commerce account group rels before and after the current commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the current commerce notification template commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceNotificationTemplateCommerceAccountGroupRelId,
				long commerceAccountGroupId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByCommerceAccountGroupId_PrevAndNext(
			commerceNotificationTemplateCommerceAccountGroupRelId,
			commerceAccountGroupId, orderByComparator);
	}

	/**
	 * Removes all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public static void removeByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		getPersistence().removeByCommerceAccountGroupId(commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	public static int countByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		return getPersistence().countByCommerceAccountGroupId(
			commerceAccountGroupId);
	}

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchNotificationTemplateCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel findByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByC_C(
			commerceNotificationTemplateId, commerceAccountGroupId);
	}

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId) {

		return getPersistence().fetchByC_C(
			commerceNotificationTemplateId, commerceAccountGroupId);
	}

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId,
			boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			commerceNotificationTemplateId, commerceAccountGroupId,
			useFinderCache);
	}

	/**
	 * Removes the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce notification template commerce account group rel that was removed
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			removeByC_C(
				long commerceNotificationTemplateId,
				long commerceAccountGroupId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().removeByC_C(
			commerceNotificationTemplateId, commerceAccountGroupId);
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	public static int countByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId) {

		return getPersistence().countByC_C(
			commerceNotificationTemplateId, commerceAccountGroupId);
	}

	/**
	 * Caches the commerce notification template commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRel the commerce notification template commerce account group rel
	 */
	public static void cacheResult(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		getPersistence().cacheResult(
			commerceNotificationTemplateCommerceAccountGroupRel);
	}

	/**
	 * Caches the commerce notification template commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRels the commerce notification template commerce account group rels
	 */
	public static void cacheResult(
		List<CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels) {

		getPersistence().cacheResult(
			commerceNotificationTemplateCommerceAccountGroupRels);
	}

	/**
	 * Creates a new commerce notification template commerce account group rel with the primary key. Does not add the commerce notification template commerce account group rel to the database.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key for the new commerce notification template commerce account group rel
	 * @return the new commerce notification template commerce account group rel
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel create(
		long commerceNotificationTemplateCommerceAccountGroupRelId) {

		return getPersistence().create(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Removes the commerce notification template commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel that was removed
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel remove(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().remove(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	public static CommerceNotificationTemplateCommerceAccountGroupRel
		updateImpl(
			CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel) {

		return getPersistence().updateImpl(
			commerceNotificationTemplateCommerceAccountGroupRel);
	}

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or throws a <code>NoSuchNotificationTemplateCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
			findByPrimaryKey(
				long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws com.liferay.commerce.notification.exception.
			NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return getPersistence().findByPrimaryKey(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel, or <code>null</code> if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	public static CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByPrimaryKey(
			long commerceNotificationTemplateCommerceAccountGroupRelId) {

		return getPersistence().fetchByPrimaryKey(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Returns all the commerce notification template commerce account group rels.
	 *
	 * @return the commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll() {

		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll(int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll(
			int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification template commerce account group rels
	 */
	public static List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findAll(
			int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce notification template commerce account group rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels.
	 *
	 * @return the number of commerce notification template commerce account group rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceNotificationTemplateCommerceAccountGroupRelPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CommerceNotificationTemplateCommerceAccountGroupRelPersistence
			persistence) {

		_persistence = persistence;
	}

	private static volatile
		CommerceNotificationTemplateCommerceAccountGroupRelPersistence
			_persistence;

}