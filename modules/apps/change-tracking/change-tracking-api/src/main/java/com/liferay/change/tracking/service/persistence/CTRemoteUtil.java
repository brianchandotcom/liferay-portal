/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTRemote;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ct remote service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTRemotePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTRemotePersistence
 * @generated
 */
public class CTRemoteUtil {

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
	public static void clearCache(CTRemote ctRemote) {
		getPersistence().clearCache(ctRemote);
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
	public static Map<Serializable, CTRemote> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTRemote> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTRemote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTRemote> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTRemote update(CTRemote ctRemote) {
		return getPersistence().update(ctRemote);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTRemote update(
		CTRemote ctRemote, ServiceContext serviceContext) {

		return getPersistence().update(ctRemote, serviceContext);
	}

	/**
	 * Returns all the ct remotes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct remotes
	 */
	public static List<CTRemote> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of matching ct remotes
	 */
	public static List<CTRemote> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct remotes
	 */
	public static List<CTRemote> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct remotes
	 */
	public static List<CTRemote> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTRemote> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct remote
	 * @throws NoSuchRemoteException if a matching ct remote could not be found
	 */
	public static CTRemote findByCompanyId_First(
			long companyId, OrderByComparator<CTRemote> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchRemoteException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct remote, or <code>null</code> if a matching ct remote could not be found
	 */
	public static CTRemote fetchByCompanyId_First(
		long companyId, OrderByComparator<CTRemote> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct remote
	 * @throws NoSuchRemoteException if a matching ct remote could not be found
	 */
	public static CTRemote findByCompanyId_Last(
			long companyId, OrderByComparator<CTRemote> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchRemoteException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct remote, or <code>null</code> if a matching ct remote could not be found
	 */
	public static CTRemote fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTRemote> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ct remotes before and after the current ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param ctRemoteId the primary key of the current ct remote
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public static CTRemote[] findByCompanyId_PrevAndNext(
			long ctRemoteId, long companyId,
			OrderByComparator<CTRemote> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchRemoteException {

		return getPersistence().findByCompanyId_PrevAndNext(
			ctRemoteId, companyId, orderByComparator);
	}

	/**
	 * Returns all the ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct remotes that the user has permission to view
	 */
	public static List<CTRemote> filterFindByCompanyId(long companyId) {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of matching ct remotes that the user has permission to view
	 */
	public static List<CTRemote> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct remotes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct remotes that the user has permission to view
	 */
	public static List<CTRemote> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTRemote> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the ct remotes before and after the current ct remote in the ordered set of ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param ctRemoteId the primary key of the current ct remote
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public static CTRemote[] filterFindByCompanyId_PrevAndNext(
			long ctRemoteId, long companyId,
			OrderByComparator<CTRemote> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchRemoteException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			ctRemoteId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ct remotes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct remotes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct remotes
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct remotes that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Caches the ct remote in the entity cache if it is enabled.
	 *
	 * @param ctRemote the ct remote
	 */
	public static void cacheResult(CTRemote ctRemote) {
		getPersistence().cacheResult(ctRemote);
	}

	/**
	 * Caches the ct remotes in the entity cache if it is enabled.
	 *
	 * @param ctRemotes the ct remotes
	 */
	public static void cacheResult(List<CTRemote> ctRemotes) {
		getPersistence().cacheResult(ctRemotes);
	}

	/**
	 * Creates a new ct remote with the primary key. Does not add the ct remote to the database.
	 *
	 * @param ctRemoteId the primary key for the new ct remote
	 * @return the new ct remote
	 */
	public static CTRemote create(long ctRemoteId) {
		return getPersistence().create(ctRemoteId);
	}

	/**
	 * Removes the ct remote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote that was removed
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public static CTRemote remove(long ctRemoteId)
		throws com.liferay.change.tracking.exception.NoSuchRemoteException {

		return getPersistence().remove(ctRemoteId);
	}

	public static CTRemote updateImpl(CTRemote ctRemote) {
		return getPersistence().updateImpl(ctRemote);
	}

	/**
	 * Returns the ct remote with the primary key or throws a <code>NoSuchRemoteException</code> if it could not be found.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public static CTRemote findByPrimaryKey(long ctRemoteId)
		throws com.liferay.change.tracking.exception.NoSuchRemoteException {

		return getPersistence().findByPrimaryKey(ctRemoteId);
	}

	/**
	 * Returns the ct remote with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote, or <code>null</code> if a ct remote with the primary key could not be found
	 */
	public static CTRemote fetchByPrimaryKey(long ctRemoteId) {
		return getPersistence().fetchByPrimaryKey(ctRemoteId);
	}

	/**
	 * Returns all the ct remotes.
	 *
	 * @return the ct remotes
	 */
	public static List<CTRemote> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct remotes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of ct remotes
	 */
	public static List<CTRemote> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct remotes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct remotes
	 */
	public static List<CTRemote> findAll(
		int start, int end, OrderByComparator<CTRemote> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct remotes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct remotes
	 */
	public static List<CTRemote> findAll(
		int start, int end, OrderByComparator<CTRemote> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct remotes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct remotes.
	 *
	 * @return the number of ct remotes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTRemotePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CTRemotePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CTRemotePersistence _persistence;

}