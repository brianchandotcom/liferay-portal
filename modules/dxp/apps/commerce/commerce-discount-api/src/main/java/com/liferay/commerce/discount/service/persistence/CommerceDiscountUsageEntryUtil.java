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

package com.liferay.commerce.discount.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce discount usage entry service. This utility wraps {@link com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUsageEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryPersistence
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUsageEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountUsageEntryUtil {
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
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		getPersistence().clearCache(commerceDiscountUsageEntry);
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
	public static List<CommerceDiscountUsageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceDiscountUsageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceDiscountUsageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceDiscountUsageEntry update(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		return getPersistence().update(commerceDiscountUsageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceDiscountUsageEntry update(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceDiscountUsageEntry, serviceContext);
	}

	/**
	* Returns all the commerce discount usage entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the commerce discount usage entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @return the range of matching commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce discount usage entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discount usage entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	*/
	public static CommerceDiscountUsageEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	*/
	public static CommerceDiscountUsageEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	*/
	public static CommerceDiscountUsageEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	*/
	public static CommerceDiscountUsageEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	*/
	public static CommerceDiscountUsageEntry[] findByGroupId_PrevAndNext(
		long commerceDiscountUsageEntryId, long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(commerceDiscountUsageEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the commerce discount usage entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of commerce discount usage entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce discount usage entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the commerce discount usage entry in the entity cache if it is enabled.
	*
	* @param commerceDiscountUsageEntry the commerce discount usage entry
	*/
	public static void cacheResult(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		getPersistence().cacheResult(commerceDiscountUsageEntry);
	}

	/**
	* Caches the commerce discount usage entries in the entity cache if it is enabled.
	*
	* @param commerceDiscountUsageEntries the commerce discount usage entries
	*/
	public static void cacheResult(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {
		getPersistence().cacheResult(commerceDiscountUsageEntries);
	}

	/**
	* Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	*
	* @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	* @return the new commerce discount usage entry
	*/
	public static CommerceDiscountUsageEntry create(
		long commerceDiscountUsageEntryId) {
		return getPersistence().create(commerceDiscountUsageEntryId);
	}

	/**
	* Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry that was removed
	* @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	*/
	public static CommerceDiscountUsageEntry remove(
		long commerceDiscountUsageEntryId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException {
		return getPersistence().remove(commerceDiscountUsageEntryId);
	}

	public static CommerceDiscountUsageEntry updateImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		return getPersistence().updateImpl(commerceDiscountUsageEntry);
	}

	/**
	* Returns the commerce discount usage entry with the primary key or throws a {@link NoSuchDiscountUsageEntryException} if it could not be found.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	*/
	public static CommerceDiscountUsageEntry findByPrimaryKey(
		long commerceDiscountUsageEntryId)
		throws com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException {
		return getPersistence().findByPrimaryKey(commerceDiscountUsageEntryId);
	}

	/**
	* Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	*/
	public static CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId) {
		return getPersistence().fetchByPrimaryKey(commerceDiscountUsageEntryId);
	}

	public static java.util.Map<java.io.Serializable, CommerceDiscountUsageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce discount usage entries.
	*
	* @return the commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce discount usage entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @return the range of commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce discount usage entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findAll(int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce discount usage entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discount usage entries
	* @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce discount usage entries
	*/
	public static List<CommerceDiscountUsageEntry> findAll(int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce discount usage entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce discount usage entries.
	*
	* @return the number of commerce discount usage entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceDiscountUsageEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountUsageEntryPersistence, CommerceDiscountUsageEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountUsageEntryPersistence.class);

		ServiceTracker<CommerceDiscountUsageEntryPersistence, CommerceDiscountUsageEntryPersistence> serviceTracker =
			new ServiceTracker<CommerceDiscountUsageEntryPersistence, CommerceDiscountUsageEntryPersistence>(bundle.getBundleContext(),
				CommerceDiscountUsageEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}