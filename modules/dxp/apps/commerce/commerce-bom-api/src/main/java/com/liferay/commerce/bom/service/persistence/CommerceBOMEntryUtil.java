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

package com.liferay.commerce.bom.service.persistence;

import com.liferay.commerce.bom.model.CommerceBOMEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the commerce bom entry service. This utility wraps <code>com.liferay.commerce.bom.service.persistence.impl.CommerceBOMEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMEntryPersistence
 * @generated
 */
public class CommerceBOMEntryUtil {

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
	public static void clearCache(CommerceBOMEntry commerceBOMEntry) {
		getPersistence().clearCache(commerceBOMEntry);
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
	public static Map<Serializable, CommerceBOMEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceBOMEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceBOMEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceBOMEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceBOMEntry update(CommerceBOMEntry commerceBOMEntry) {
		return getPersistence().update(commerceBOMEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceBOMEntry update(
		CommerceBOMEntry commerceBOMEntry, ServiceContext serviceContext) {

		return getPersistence().update(commerceBOMEntry, serviceContext);
	}

	/**
	 * Returns all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @return the matching commerce bom entries
	 */
	public static List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId) {

		return getPersistence().findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId);
	}

	/**
	 * Returns a range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @return the range of matching commerce bom entries
	 */
	public static List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end) {

		return getPersistence().findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce bom entries
	 */
	public static List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return getPersistence().findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce bom entries
	 */
	public static List<CommerceBOMEntry> findByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId, int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceBOMDefinitionId(
			commerceBOMDefinitionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom entry
	 * @throws NoSuchBOMEntryException if a matching commerce bom entry could not be found
	 */
	public static CommerceBOMEntry findByCommerceBOMDefinitionId_First(
			long commerceBOMDefinitionId,
			OrderByComparator<CommerceBOMEntry> orderByComparator)
		throws com.liferay.commerce.bom.exception.NoSuchBOMEntryException {

		return getPersistence().findByCommerceBOMDefinitionId_First(
			commerceBOMDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce bom entry, or <code>null</code> if a matching commerce bom entry could not be found
	 */
	public static CommerceBOMEntry fetchByCommerceBOMDefinitionId_First(
		long commerceBOMDefinitionId,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return getPersistence().fetchByCommerceBOMDefinitionId_First(
			commerceBOMDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom entry
	 * @throws NoSuchBOMEntryException if a matching commerce bom entry could not be found
	 */
	public static CommerceBOMEntry findByCommerceBOMDefinitionId_Last(
			long commerceBOMDefinitionId,
			OrderByComparator<CommerceBOMEntry> orderByComparator)
		throws com.liferay.commerce.bom.exception.NoSuchBOMEntryException {

		return getPersistence().findByCommerceBOMDefinitionId_Last(
			commerceBOMDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce bom entry, or <code>null</code> if a matching commerce bom entry could not be found
	 */
	public static CommerceBOMEntry fetchByCommerceBOMDefinitionId_Last(
		long commerceBOMDefinitionId,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return getPersistence().fetchByCommerceBOMDefinitionId_Last(
			commerceBOMDefinitionId, orderByComparator);
	}

	/**
	 * Returns the commerce bom entries before and after the current commerce bom entry in the ordered set where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMEntryId the primary key of the current commerce bom entry
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	public static CommerceBOMEntry[] findByCommerceBOMDefinitionId_PrevAndNext(
			long commerceBOMEntryId, long commerceBOMDefinitionId,
			OrderByComparator<CommerceBOMEntry> orderByComparator)
		throws com.liferay.commerce.bom.exception.NoSuchBOMEntryException {

		return getPersistence().findByCommerceBOMDefinitionId_PrevAndNext(
			commerceBOMEntryId, commerceBOMDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the commerce bom entries where commerceBOMDefinitionId = &#63; from the database.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 */
	public static void removeByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId) {

		getPersistence().removeByCommerceBOMDefinitionId(
			commerceBOMDefinitionId);
	}

	/**
	 * Returns the number of commerce bom entries where commerceBOMDefinitionId = &#63;.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID
	 * @return the number of matching commerce bom entries
	 */
	public static int countByCommerceBOMDefinitionId(
		long commerceBOMDefinitionId) {

		return getPersistence().countByCommerceBOMDefinitionId(
			commerceBOMDefinitionId);
	}

	/**
	 * Caches the commerce bom entry in the entity cache if it is enabled.
	 *
	 * @param commerceBOMEntry the commerce bom entry
	 */
	public static void cacheResult(CommerceBOMEntry commerceBOMEntry) {
		getPersistence().cacheResult(commerceBOMEntry);
	}

	/**
	 * Caches the commerce bom entries in the entity cache if it is enabled.
	 *
	 * @param commerceBOMEntries the commerce bom entries
	 */
	public static void cacheResult(List<CommerceBOMEntry> commerceBOMEntries) {
		getPersistence().cacheResult(commerceBOMEntries);
	}

	/**
	 * Creates a new commerce bom entry with the primary key. Does not add the commerce bom entry to the database.
	 *
	 * @param commerceBOMEntryId the primary key for the new commerce bom entry
	 * @return the new commerce bom entry
	 */
	public static CommerceBOMEntry create(long commerceBOMEntryId) {
		return getPersistence().create(commerceBOMEntryId);
	}

	/**
	 * Removes the commerce bom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry that was removed
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	public static CommerceBOMEntry remove(long commerceBOMEntryId)
		throws com.liferay.commerce.bom.exception.NoSuchBOMEntryException {

		return getPersistence().remove(commerceBOMEntryId);
	}

	public static CommerceBOMEntry updateImpl(
		CommerceBOMEntry commerceBOMEntry) {

		return getPersistence().updateImpl(commerceBOMEntry);
	}

	/**
	 * Returns the commerce bom entry with the primary key or throws a <code>NoSuchBOMEntryException</code> if it could not be found.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry
	 * @throws NoSuchBOMEntryException if a commerce bom entry with the primary key could not be found
	 */
	public static CommerceBOMEntry findByPrimaryKey(long commerceBOMEntryId)
		throws com.liferay.commerce.bom.exception.NoSuchBOMEntryException {

		return getPersistence().findByPrimaryKey(commerceBOMEntryId);
	}

	/**
	 * Returns the commerce bom entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceBOMEntryId the primary key of the commerce bom entry
	 * @return the commerce bom entry, or <code>null</code> if a commerce bom entry with the primary key could not be found
	 */
	public static CommerceBOMEntry fetchByPrimaryKey(long commerceBOMEntryId) {
		return getPersistence().fetchByPrimaryKey(commerceBOMEntryId);
	}

	/**
	 * Returns all the commerce bom entries.
	 *
	 * @return the commerce bom entries
	 */
	public static List<CommerceBOMEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @return the range of commerce bom entries
	 */
	public static List<CommerceBOMEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce bom entries
	 */
	public static List<CommerceBOMEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce bom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceBOMEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce bom entries
	 * @param end the upper bound of the range of commerce bom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce bom entries
	 */
	public static List<CommerceBOMEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceBOMEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce bom entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce bom entries.
	 *
	 * @return the number of commerce bom entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceBOMEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceBOMEntryPersistence, CommerceBOMEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceBOMEntryPersistence.class);

		ServiceTracker<CommerceBOMEntryPersistence, CommerceBOMEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<CommerceBOMEntryPersistence, CommerceBOMEntryPersistence>(
						bundle.getBundleContext(),
						CommerceBOMEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}