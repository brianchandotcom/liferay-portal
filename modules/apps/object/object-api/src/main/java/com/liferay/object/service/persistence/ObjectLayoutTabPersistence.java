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

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectLayoutTabException;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout tab service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutTabUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutTabPersistence
	extends BasePersistence<ObjectLayoutTab> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutTabUtil} to access the object layout tab persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object layout tabs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid(String uuid);

	/**
	 * Returns a range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public ObjectLayoutTab[] findByUuid_PrevAndNext(
			long objectLayoutTabId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Removes all the object layout tabs where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout tabs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout tabs
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public ObjectLayoutTab[] findByUuid_C_PrevAndNext(
			long objectLayoutTabId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Removes all the object layout tabs where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout tabs
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @return the matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId);

	/**
	 * Returns a range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab findByObjectLayoutId_First(
			long objectLayoutId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the first object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab fetchByObjectLayoutId_First(
		long objectLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns the last object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab findByObjectLayoutId_Last(
			long objectLayoutId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the last object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	public ObjectLayoutTab fetchByObjectLayoutId_Last(
		long objectLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public ObjectLayoutTab[] findByObjectLayoutId_PrevAndNext(
			long objectLayoutTabId, long objectLayoutId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
				orderByComparator)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Removes all the object layout tabs where objectLayoutId = &#63; from the database.
	 *
	 * @param objectLayoutId the object layout ID
	 */
	public void removeByObjectLayoutId(long objectLayoutId);

	/**
	 * Returns the number of object layout tabs where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @return the number of matching object layout tabs
	 */
	public int countByObjectLayoutId(long objectLayoutId);

	/**
	 * Caches the object layout tab in the entity cache if it is enabled.
	 *
	 * @param objectLayoutTab the object layout tab
	 */
	public void cacheResult(ObjectLayoutTab objectLayoutTab);

	/**
	 * Caches the object layout tabs in the entity cache if it is enabled.
	 *
	 * @param objectLayoutTabs the object layout tabs
	 */
	public void cacheResult(java.util.List<ObjectLayoutTab> objectLayoutTabs);

	/**
	 * Creates a new object layout tab with the primary key. Does not add the object layout tab to the database.
	 *
	 * @param objectLayoutTabId the primary key for the new object layout tab
	 * @return the new object layout tab
	 */
	public ObjectLayoutTab create(long objectLayoutTabId);

	/**
	 * Removes the object layout tab with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab that was removed
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public ObjectLayoutTab remove(long objectLayoutTabId)
		throws NoSuchObjectLayoutTabException;

	public ObjectLayoutTab updateImpl(ObjectLayoutTab objectLayoutTab);

	/**
	 * Returns the object layout tab with the primary key or throws a <code>NoSuchObjectLayoutTabException</code> if it could not be found.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	public ObjectLayoutTab findByPrimaryKey(long objectLayoutTabId)
		throws NoSuchObjectLayoutTabException;

	/**
	 * Returns the object layout tab with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab, or <code>null</code> if a object layout tab with the primary key could not be found
	 */
	public ObjectLayoutTab fetchByPrimaryKey(long objectLayoutTabId);

	/**
	 * Returns all the object layout tabs.
	 *
	 * @return the object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findAll();

	/**
	 * Returns a range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout tabs
	 */
	public java.util.List<ObjectLayoutTab> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutTab>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object layout tabs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object layout tabs.
	 *
	 * @return the number of object layout tabs
	 */
	public int countAll();

}