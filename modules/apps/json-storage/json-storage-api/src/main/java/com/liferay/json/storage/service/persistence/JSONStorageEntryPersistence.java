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

package com.liferay.json.storage.service.persistence;

import com.liferay.json.storage.exception.NoSuchJSONStorageEntryException;
import com.liferay.json.storage.model.JSONStorageEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the json storage entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @see JSONStorageEntryUtil
 * @generated
 */
@ProviderType
public interface JSONStorageEntryPersistence
	extends BasePersistence<JSONStorageEntry>, CTPersistence<JSONStorageEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JSONStorageEntryUtil} to access the json storage entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK);

	/**
	 * Returns a range of all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByCN_CPK_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the first json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByCN_CPK_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns the last json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByCN_CPK_Last(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the last json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByCN_CPK_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns the json storage entries before and after the current json storage entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param jsonStorageEntryId the primary key of the current json storage entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	public JSONStorageEntry[] findByCN_CPK_PrevAndNext(
			long jsonStorageEntryId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Removes all the json storage entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByCN_CPK(long classNameId, long classPK);

	/**
	 * Returns the number of json storage entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching json storage entries
	 */
	public int countByCN_CPK(long classNameId, long classPK);

	/**
	 * Returns all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong);

	/**
	 * Returns a range of all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end);

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByC_CN_I_T_VL_First(
			long companyId, long classNameId, int index, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByC_CN_I_T_VL_First(
		long companyId, long classNameId, int index, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByC_CN_I_T_VL_Last(
			long companyId, long classNameId, int index, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByC_CN_I_T_VL_Last(
		long companyId, long classNameId, int index, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns the json storage entries before and after the current json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStorageEntryId the primary key of the current json storage entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	public JSONStorageEntry[] findByC_CN_I_T_VL_PrevAndNext(
			long jsonStorageEntryId, long companyId, long classNameId,
			int index, int type, long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Removes all the json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63; from the database.
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
	 * Returns the number of json storage entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json storage entries
	 */
	public int countByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong);

	/**
	 * Returns all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong);

	/**
	 * Returns a range of all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end);

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json storage entries
	 */
	public java.util.List<JSONStorageEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByC_CN_K_T_VL_First(
			long companyId, long classNameId, String key, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the first json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByC_CN_K_T_VL_First(
		long companyId, long classNameId, String key, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByC_CN_K_T_VL_Last(
			long companyId, long classNameId, String key, int type,
			long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the last json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByC_CN_K_T_VL_Last(
		long companyId, long classNameId, String key, int type, long valueLong,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns the json storage entries before and after the current json storage entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStorageEntryId the primary key of the current json storage entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	public JSONStorageEntry[] findByC_CN_K_T_VL_PrevAndNext(
			long jsonStorageEntryId, long companyId, long classNameId,
			String key, int type, long valueLong,
			com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
				orderByComparator)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Removes all the json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63; from the database.
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
	 * Returns the number of json storage entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json storage entries
	 */
	public int countByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong);

	/**
	 * Returns the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; or throws a <code>NoSuchJSONStorageEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json storage entry
	 * @throws NoSuchJSONStorageEntryException if a matching json storage entry could not be found
	 */
	public JSONStorageEntry findByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStorageEntryId,
			int index, String key)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStorageEntryId,
		int index, String key);

	/**
	 * Returns the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching json storage entry, or <code>null</code> if a matching json storage entry could not be found
	 */
	public JSONStorageEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStorageEntryId,
		int index, String key, boolean useFinderCache);

	/**
	 * Removes the json storage entry where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the json storage entry that was removed
	 */
	public JSONStorageEntry removeByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStorageEntryId,
			int index, String key)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the number of json storage entries where classNameId = &#63; and classPK = &#63; and parentJSONStorageEntryId = &#63; and index = &#63; and key = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStorageEntryId the parent json storage entry ID
	 * @param index the index
	 * @param key the key
	 * @return the number of matching json storage entries
	 */
	public int countByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStorageEntryId,
		int index, String key);

	/**
	 * Caches the json storage entry in the entity cache if it is enabled.
	 *
	 * @param jsonStorageEntry the json storage entry
	 */
	public void cacheResult(JSONStorageEntry jsonStorageEntry);

	/**
	 * Caches the json storage entries in the entity cache if it is enabled.
	 *
	 * @param jsonStorageEntries the json storage entries
	 */
	public void cacheResult(
		java.util.List<JSONStorageEntry> jsonStorageEntries);

	/**
	 * Creates a new json storage entry with the primary key. Does not add the json storage entry to the database.
	 *
	 * @param jsonStorageEntryId the primary key for the new json storage entry
	 * @return the new json storage entry
	 */
	public JSONStorageEntry create(long jsonStorageEntryId);

	/**
	 * Removes the json storage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param jsonStorageEntryId the primary key of the json storage entry
	 * @return the json storage entry that was removed
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	public JSONStorageEntry remove(long jsonStorageEntryId)
		throws NoSuchJSONStorageEntryException;

	public JSONStorageEntry updateImpl(JSONStorageEntry jsonStorageEntry);

	/**
	 * Returns the json storage entry with the primary key or throws a <code>NoSuchJSONStorageEntryException</code> if it could not be found.
	 *
	 * @param jsonStorageEntryId the primary key of the json storage entry
	 * @return the json storage entry
	 * @throws NoSuchJSONStorageEntryException if a json storage entry with the primary key could not be found
	 */
	public JSONStorageEntry findByPrimaryKey(long jsonStorageEntryId)
		throws NoSuchJSONStorageEntryException;

	/**
	 * Returns the json storage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param jsonStorageEntryId the primary key of the json storage entry
	 * @return the json storage entry, or <code>null</code> if a json storage entry with the primary key could not be found
	 */
	public JSONStorageEntry fetchByPrimaryKey(long jsonStorageEntryId);

	/**
	 * Returns all the json storage entries.
	 *
	 * @return the json storage entries
	 */
	public java.util.List<JSONStorageEntry> findAll();

	/**
	 * Returns a range of all the json storage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @return the range of json storage entries
	 */
	public java.util.List<JSONStorageEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the json storage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of json storage entries
	 */
	public java.util.List<JSONStorageEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the json storage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStorageEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json storage entries
	 * @param end the upper bound of the range of json storage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of json storage entries
	 */
	public java.util.List<JSONStorageEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JSONStorageEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the json storage entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of json storage entries.
	 *
	 * @return the number of json storage entries
	 */
	public int countAll();

}