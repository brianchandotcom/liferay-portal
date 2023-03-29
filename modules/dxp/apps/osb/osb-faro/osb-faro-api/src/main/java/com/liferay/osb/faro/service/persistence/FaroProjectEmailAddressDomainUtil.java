/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence;

import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the faro project email address domain service. This utility wraps <code>com.liferay.osb.faro.service.persistence.impl.FaroProjectEmailAddressDomainPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @see FaroProjectEmailAddressDomainPersistence
 * @generated
 */
public class FaroProjectEmailAddressDomainUtil {

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
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		getPersistence().clearCache(faroProjectEmailAddressDomain);
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
	public static Map<Serializable, FaroProjectEmailAddressDomain>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FaroProjectEmailAddressDomain> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FaroProjectEmailAddressDomain> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FaroProjectEmailAddressDomain> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FaroProjectEmailAddressDomain update(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		return getPersistence().update(faroProjectEmailAddressDomain);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FaroProjectEmailAddressDomain update(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain,
		ServiceContext serviceContext) {

		return getPersistence().update(
			faroProjectEmailAddressDomain, serviceContext);
	}

	/**
	 * Returns all the faro project email address domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId) {

		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain findByGroupId_First(
			long groupId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain fetchByGroupId_First(
		long groupId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain findByGroupId_Last(
			long groupId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain fetchByGroupId_Last(
		long groupId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the faro project email address domains before and after the current faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the current faro project email address domain
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public static FaroProjectEmailAddressDomain[] findByGroupId_PrevAndNext(
			long faroProjectEmailAddressDomainId, long groupId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByGroupId_PrevAndNext(
			faroProjectEmailAddressDomainId, groupId, orderByComparator);
	}

	/**
	 * Removes all the faro project email address domains where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of faro project email address domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro project email address domains
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId) {

		return getPersistence().findByFaroProjectId(faroProjectId);
	}

	/**
	 * Returns a range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end) {

		return getPersistence().findByFaroProjectId(faroProjectId, start, end);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().findByFaroProjectId(
			faroProjectId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByFaroProjectId(
			faroProjectId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain findByFaroProjectId_First(
			long faroProjectId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByFaroProjectId_First(
			faroProjectId, orderByComparator);
	}

	/**
	 * Returns the first faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain fetchByFaroProjectId_First(
		long faroProjectId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().fetchByFaroProjectId_First(
			faroProjectId, orderByComparator);
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain findByFaroProjectId_Last(
			long faroProjectId,
			OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByFaroProjectId_Last(
			faroProjectId, orderByComparator);
	}

	/**
	 * Returns the last faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public static FaroProjectEmailAddressDomain fetchByFaroProjectId_Last(
		long faroProjectId,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().fetchByFaroProjectId_Last(
			faroProjectId, orderByComparator);
	}

	/**
	 * Returns the faro project email address domains before and after the current faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the current faro project email address domain
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public static FaroProjectEmailAddressDomain[]
			findByFaroProjectId_PrevAndNext(
				long faroProjectEmailAddressDomainId, long faroProjectId,
				OrderByComparator<FaroProjectEmailAddressDomain>
					orderByComparator)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByFaroProjectId_PrevAndNext(
			faroProjectEmailAddressDomainId, faroProjectId, orderByComparator);
	}

	/**
	 * Removes all the faro project email address domains where faroProjectId = &#63; from the database.
	 *
	 * @param faroProjectId the faro project ID
	 */
	public static void removeByFaroProjectId(long faroProjectId) {
		getPersistence().removeByFaroProjectId(faroProjectId);
	}

	/**
	 * Returns the number of faro project email address domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the number of matching faro project email address domains
	 */
	public static int countByFaroProjectId(long faroProjectId) {
		return getPersistence().countByFaroProjectId(faroProjectId);
	}

	/**
	 * Caches the faro project email address domain in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 */
	public static void cacheResult(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		getPersistence().cacheResult(faroProjectEmailAddressDomain);
	}

	/**
	 * Caches the faro project email address domains in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailAddressDomains the faro project email address domains
	 */
	public static void cacheResult(
		List<FaroProjectEmailAddressDomain> faroProjectEmailAddressDomains) {

		getPersistence().cacheResult(faroProjectEmailAddressDomains);
	}

	/**
	 * Creates a new faro project email address domain with the primary key. Does not add the faro project email address domain to the database.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key for the new faro project email address domain
	 * @return the new faro project email address domain
	 */
	public static FaroProjectEmailAddressDomain create(
		long faroProjectEmailAddressDomainId) {

		return getPersistence().create(faroProjectEmailAddressDomainId);
	}

	/**
	 * Removes the faro project email address domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain that was removed
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public static FaroProjectEmailAddressDomain remove(
			long faroProjectEmailAddressDomainId)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().remove(faroProjectEmailAddressDomainId);
	}

	public static FaroProjectEmailAddressDomain updateImpl(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		return getPersistence().updateImpl(faroProjectEmailAddressDomain);
	}

	/**
	 * Returns the faro project email address domain with the primary key or throws a <code>NoSuchFaroProjectEmailAddressDomainException</code> if it could not be found.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public static FaroProjectEmailAddressDomain findByPrimaryKey(
			long faroProjectEmailAddressDomainId)
		throws com.liferay.osb.faro.exception.
			NoSuchFaroProjectEmailAddressDomainException {

		return getPersistence().findByPrimaryKey(
			faroProjectEmailAddressDomainId);
	}

	/**
	 * Returns the faro project email address domain with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain, or <code>null</code> if a faro project email address domain with the primary key could not be found
	 */
	public static FaroProjectEmailAddressDomain fetchByPrimaryKey(
		long faroProjectEmailAddressDomainId) {

		return getPersistence().fetchByPrimaryKey(
			faroProjectEmailAddressDomainId);
	}

	/**
	 * Returns all the faro project email address domains.
	 *
	 * @return the faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findAll(
		int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro project email address domains
	 */
	public static List<FaroProjectEmailAddressDomain> findAll(
		int start, int end,
		OrderByComparator<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the faro project email address domains from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of faro project email address domains.
	 *
	 * @return the number of faro project email address domains
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FaroProjectEmailAddressDomainPersistence getPersistence() {
		return _persistence;
	}

	private static volatile FaroProjectEmailAddressDomainPersistence
		_persistence;

}