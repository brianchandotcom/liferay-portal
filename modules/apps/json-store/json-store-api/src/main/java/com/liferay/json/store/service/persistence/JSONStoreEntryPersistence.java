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

package com.liferay.json.store.service.persistence;

import com.liferay.json.store.exception.NoSuchEntryException;
import com.liferay.json.store.model.JSONStoreEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the json store entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @see JSONStoreEntryUtil
 * @generated
 */
@ProviderType
public interface JSONStoreEntryPersistence
	extends BasePersistence<JSONStoreEntry>, CTPersistence<JSONStoreEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JSONStoreEntryUtil} to access the json store entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByCN_CPK_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByCN_CPK_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns the last json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByCN_CPK_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByCN_CPK_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public JSONStoreEntry[] findByCN_CPK_PrevAndNext(
			long jsonStoreEntryId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the json store entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByCN_CPK(long classNameId, long classPK);

	/**
	 * Returns the number of json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching json store entries
	 */
	public int countByCN_CPK(long classNameId, long classPK);

	/**
	 * Returns all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong);

	/**
	 * Returns a range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end);

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByC_CN_I_T_VL_First(
			long companyId, long classNameId, int index, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByC_CN_I_T_VL_First(
		long companyId, long classNameId, int index, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByC_CN_I_T_VL_Last(
			long companyId, long classNameId, int index, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByC_CN_I_T_VL_Last(
		long companyId, long classNameId, int index, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public JSONStoreEntry[] findByC_CN_I_T_VL_PrevAndNext(
			long jsonStoreEntryId, long companyId, long classNameId, int index,
			int type, long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 */
	public void removeByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong);

	/**
	 * Returns the number of json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json store entries
	 */
	public int countByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong);

	/**
	 * Returns all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong);

	/**
	 * Returns a range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end);

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	public java.util.List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByC_CN_K_T_VL_First(
			long companyId, long classNameId, String key, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByC_CN_K_T_VL_First(
		long companyId, long classNameId, String key, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByC_CN_K_T_VL_Last(
			long companyId, long classNameId, String key, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByC_CN_K_T_VL_Last(
		long companyId, long classNameId, String key, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public JSONStoreEntry[] findByC_CN_K_T_VL_PrevAndNext(
			long jsonStoreEntryId, long companyId, long classNameId, String key,
			int type, long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 */
	public void removeByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong);

	/**
	 * Returns the number of json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json store entries
	 */
	public int countByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong);

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public JSONStoreEntry findByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStoreEntryId,
			int index, String key)
		throws NoSuchEntryException;

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key);

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public JSONStoreEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key, boolean useFinderCache);

	/**
	 * Removes the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the json store entry that was removed
	 */
	public JSONStoreEntry removeByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStoreEntryId,
			int index, String key)
		throws NoSuchEntryException;

	/**
	 * Returns the number of json store entries where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the number of matching json store entries
	 */
	public int countByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key);

	/**
	 * Caches the json store entry in the entity cache if it is enabled.
	 *
	 * @param jsonStoreEntry the json store entry
	 */
	public void cacheResult(JSONStoreEntry jsonStoreEntry);

	/**
	 * Caches the json store entries in the entity cache if it is enabled.
	 *
	 * @param jsonStoreEntries the json store entries
	 */
	public void cacheResult(java.util.List<JSONStoreEntry> jsonStoreEntries);

	/**
	 * Creates a new json store entry with the primary key. Does not add the json store entry to the database.
	 *
	 * @param jsonStoreEntryId the primary key for the new json store entry
	 * @return the new json store entry
	 */
	public JSONStoreEntry create(long jsonStoreEntryId);

	/**
	 * Removes the json store entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry that was removed
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public JSONStoreEntry remove(long jsonStoreEntryId)
		throws NoSuchEntryException;

	public JSONStoreEntry updateImpl(JSONStoreEntry jsonStoreEntry);

	/**
	 * Returns the json store entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public JSONStoreEntry findByPrimaryKey(long jsonStoreEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the json store entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry, or <code>null</code> if a json store entry with the primary key could not be found
	 */
	public JSONStoreEntry fetchByPrimaryKey(long jsonStoreEntryId);

	/**
	 * Returns all the json store entries.
	 *
	 * @return the json store entries
	 */
	public java.util.List<JSONStoreEntry> findAll();

	/**
	 * Returns a range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of json store entries
	 */
	public java.util.List<JSONStoreEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of json store entries
	 */
	public java.util.List<JSONStoreEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of json store entries
	 */
	public java.util.List<JSONStoreEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStoreEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the json store entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of json store entries.
	 *
	 * @return the number of json store entries
	 */
	public int countAll();

}