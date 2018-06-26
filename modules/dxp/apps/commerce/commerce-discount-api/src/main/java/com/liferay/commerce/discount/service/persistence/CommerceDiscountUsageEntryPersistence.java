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

import com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException;
import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce discount usage entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountUsageEntryPersistenceImpl
 * @see CommerceDiscountUsageEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountUsageEntryPersistence extends BasePersistence<CommerceDiscountUsageEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountUsageEntryUtil} to access the commerce discount usage entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce discount usage entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce discount usage entries
	*/
	public java.util.List<CommerceDiscountUsageEntry> findByGroupId(
		long groupId);

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
	public java.util.List<CommerceDiscountUsageEntry> findByGroupId(
		long groupId, int start, int end);

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
	public java.util.List<CommerceDiscountUsageEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator);

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
	public java.util.List<CommerceDiscountUsageEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	*/
	public CommerceDiscountUsageEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException;

	/**
	* Returns the first commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	*/
	public CommerceDiscountUsageEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator);

	/**
	* Returns the last commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	*/
	public CommerceDiscountUsageEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException;

	/**
	* Returns the last commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	*/
	public CommerceDiscountUsageEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator);

	/**
	* Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where groupId = &#63;.
	*
	* @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	*/
	public CommerceDiscountUsageEntry[] findByGroupId_PrevAndNext(
		long commerceDiscountUsageEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException;

	/**
	* Removes all the commerce discount usage entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce discount usage entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce discount usage entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the commerce discount usage entry in the entity cache if it is enabled.
	*
	* @param commerceDiscountUsageEntry the commerce discount usage entry
	*/
	public void cacheResult(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry);

	/**
	* Caches the commerce discount usage entries in the entity cache if it is enabled.
	*
	* @param commerceDiscountUsageEntries the commerce discount usage entries
	*/
	public void cacheResult(
		java.util.List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries);

	/**
	* Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	*
	* @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	* @return the new commerce discount usage entry
	*/
	public CommerceDiscountUsageEntry create(long commerceDiscountUsageEntryId);

	/**
	* Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry that was removed
	* @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	*/
	public CommerceDiscountUsageEntry remove(long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException;

	public CommerceDiscountUsageEntry updateImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry);

	/**
	* Returns the commerce discount usage entry with the primary key or throws a {@link NoSuchDiscountUsageEntryException} if it could not be found.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry
	* @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	*/
	public CommerceDiscountUsageEntry findByPrimaryKey(
		long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException;

	/**
	* Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	* @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	*/
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceDiscountUsageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce discount usage entries.
	*
	* @return the commerce discount usage entries
	*/
	public java.util.List<CommerceDiscountUsageEntry> findAll();

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
	public java.util.List<CommerceDiscountUsageEntry> findAll(int start, int end);

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
	public java.util.List<CommerceDiscountUsageEntry> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator);

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
	public java.util.List<CommerceDiscountUsageEntry> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce discount usage entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce discount usage entries.
	*
	* @return the number of commerce discount usage entries
	*/
	public int countAll();
}