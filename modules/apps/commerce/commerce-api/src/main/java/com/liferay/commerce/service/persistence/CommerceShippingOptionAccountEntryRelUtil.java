/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce shipping option account entry rel service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceShippingOptionAccountEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRelPersistence
 * @generated
 */
public class CommerceShippingOptionAccountEntryRelUtil {

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
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		getPersistence().clearCache(commerceShippingOptionAccountEntryRel);
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
	public static Map<Serializable, CommerceShippingOptionAccountEntryRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceShippingOptionAccountEntryRel update(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		return getPersistence().update(commerceShippingOptionAccountEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceShippingOptionAccountEntryRel update(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceShippingOptionAccountEntryRel, serviceContext);
	}

	/**
	 * Returns all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(long accountEntryId) {

		return getPersistence().findByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(long accountEntryId, int start, int end) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(
			long accountEntryId, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByAccountEntryId(
			long accountEntryId, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
			findByAccountEntryId_First(
				long accountEntryId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
		fetchByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().fetchByAccountEntryId_First(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
			findByAccountEntryId_Last(
				long accountEntryId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
		fetchByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().fetchByAccountEntryId_Last(
			accountEntryId, orderByComparator);
	}

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel[]
			findByAccountEntryId_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				long accountEntryId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByAccountEntryId_PrevAndNext(
			CommerceShippingOptionAccountEntryRelId, accountEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce shipping option account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public static void removeByAccountEntryId(long accountEntryId) {
		getPersistence().removeByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public static int countByAccountEntryId(long accountEntryId) {
		return getPersistence().countByAccountEntryId(accountEntryId);
	}

	/**
	 * Returns all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(long commerceChannelId) {

		return getPersistence().findByCommerceChannelId(commerceChannelId);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(long commerceChannelId, int start, int end) {

		return getPersistence().findByCommerceChannelId(
			commerceChannelId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(
			long commerceChannelId, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().findByCommerceChannelId(
			commerceChannelId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceChannelId(
			long commerceChannelId, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceChannelId(
			commerceChannelId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
			findByCommerceChannelId_First(
				long commerceChannelId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByCommerceChannelId_First(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
		fetchByCommerceChannelId_First(
			long commerceChannelId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceChannelId_First(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
			findByCommerceChannelId_Last(
				long commerceChannelId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByCommerceChannelId_Last(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
		fetchByCommerceChannelId_Last(
			long commerceChannelId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceChannelId_Last(
			commerceChannelId, orderByComparator);
	}

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel[]
			findByCommerceChannelId_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				long commerceChannelId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByCommerceChannelId_PrevAndNext(
			CommerceShippingOptionAccountEntryRelId, commerceChannelId,
			orderByComparator);
	}

	/**
	 * Removes all the commerce shipping option account entry rels where commerceChannelId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 */
	public static void removeByCommerceChannelId(long commerceChannelId) {
		getPersistence().removeByCommerceChannelId(commerceChannelId);
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public static int countByCommerceChannelId(long commerceChannelId) {
		return getPersistence().countByCommerceChannelId(commerceChannelId);
	}

	/**
	 * Returns all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @return the matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(String commerceShippingOptionKey) {

		return getPersistence().findByCommerceShippingOptionKey(
			commerceShippingOptionKey);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end) {

		return getPersistence().findByCommerceShippingOptionKey(
			commerceShippingOptionKey, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().findByCommerceShippingOptionKey(
			commerceShippingOptionKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByCommerceShippingOptionKey(
			commerceShippingOptionKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
			findByCommerceShippingOptionKey_First(
				String commerceShippingOptionKey,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByCommerceShippingOptionKey_First(
			commerceShippingOptionKey, orderByComparator);
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
		fetchByCommerceShippingOptionKey_First(
			String commerceShippingOptionKey,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceShippingOptionKey_First(
			commerceShippingOptionKey, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
			findByCommerceShippingOptionKey_Last(
				String commerceShippingOptionKey,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByCommerceShippingOptionKey_Last(
			commerceShippingOptionKey, orderByComparator);
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel
		fetchByCommerceShippingOptionKey_Last(
			String commerceShippingOptionKey,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return getPersistence().fetchByCommerceShippingOptionKey_Last(
			commerceShippingOptionKey, orderByComparator);
	}

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel[]
			findByCommerceShippingOptionKey_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				String commerceShippingOptionKey,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByCommerceShippingOptionKey_PrevAndNext(
			CommerceShippingOptionAccountEntryRelId, commerceShippingOptionKey,
			orderByComparator);
	}

	/**
	 * Removes all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63; from the database.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 */
	public static void removeByCommerceShippingOptionKey(
		String commerceShippingOptionKey) {

		getPersistence().removeByCommerceShippingOptionKey(
			commerceShippingOptionKey);
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public static int countByCommerceShippingOptionKey(
		String commerceShippingOptionKey) {

		return getPersistence().countByCommerceShippingOptionKey(
			commerceShippingOptionKey);
	}

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or throws a <code>NoSuchShippingOptionAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel findByA_C(
			long accountEntryId, long commerceChannelId)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByA_C(accountEntryId, commerceChannelId);
	}

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel fetchByA_C(
		long accountEntryId, long commerceChannelId) {

		return getPersistence().fetchByA_C(accountEntryId, commerceChannelId);
	}

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel fetchByA_C(
		long accountEntryId, long commerceChannelId, boolean useFinderCache) {

		return getPersistence().fetchByA_C(
			accountEntryId, commerceChannelId, useFinderCache);
	}

	/**
	 * Removes the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the commerce shipping option account entry rel that was removed
	 */
	public static CommerceShippingOptionAccountEntryRel removeByA_C(
			long accountEntryId, long commerceChannelId)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().removeByA_C(accountEntryId, commerceChannelId);
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where accountEntryId = &#63; and commerceChannelId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	public static int countByA_C(long accountEntryId, long commerceChannelId) {
		return getPersistence().countByA_C(accountEntryId, commerceChannelId);
	}

	/**
	 * Caches the commerce shipping option account entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceShippingOptionAccountEntryRel the commerce shipping option account entry rel
	 */
	public static void cacheResult(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		getPersistence().cacheResult(commerceShippingOptionAccountEntryRel);
	}

	/**
	 * Caches the commerce shipping option account entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceShippingOptionAccountEntryRels the commerce shipping option account entry rels
	 */
	public static void cacheResult(
		List<CommerceShippingOptionAccountEntryRel>
			commerceShippingOptionAccountEntryRels) {

		getPersistence().cacheResult(commerceShippingOptionAccountEntryRels);
	}

	/**
	 * Creates a new commerce shipping option account entry rel with the primary key. Does not add the commerce shipping option account entry rel to the database.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key for the new commerce shipping option account entry rel
	 * @return the new commerce shipping option account entry rel
	 */
	public static CommerceShippingOptionAccountEntryRel create(
		long CommerceShippingOptionAccountEntryRelId) {

		return getPersistence().create(CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Removes the commerce shipping option account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel that was removed
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel remove(
			long CommerceShippingOptionAccountEntryRelId)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().remove(CommerceShippingOptionAccountEntryRelId);
	}

	public static CommerceShippingOptionAccountEntryRel updateImpl(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		return getPersistence().updateImpl(
			commerceShippingOptionAccountEntryRel);
	}

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or throws a <code>NoSuchShippingOptionAccountEntryRelException</code> if it could not be found.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel findByPrimaryKey(
			long CommerceShippingOptionAccountEntryRelId)
		throws com.liferay.commerce.exception.
			NoSuchShippingOptionAccountEntryRelException {

		return getPersistence().findByPrimaryKey(
			CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel, or <code>null</code> if a commerce shipping option account entry rel with the primary key could not be found
	 */
	public static CommerceShippingOptionAccountEntryRel fetchByPrimaryKey(
		long CommerceShippingOptionAccountEntryRelId) {

		return getPersistence().fetchByPrimaryKey(
			CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Returns all the commerce shipping option account entry rels.
	 *
	 * @return the commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping option account entry rels
	 */
	public static List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce shipping option account entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce shipping option account entry rels.
	 *
	 * @return the number of commerce shipping option account entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceShippingOptionAccountEntryRelPersistence
		getPersistence() {

		return _persistence;
	}

	public static void setPersistence(
		CommerceShippingOptionAccountEntryRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceShippingOptionAccountEntryRelPersistence
		_persistence;

}