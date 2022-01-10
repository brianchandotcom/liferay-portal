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

package com.liferay.portal.language.override.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.language.override.exception.NoSuchPLOEntryException;
import com.liferay.portal.language.override.model.PLOEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the plo entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Drew Brokke
 * @see PLOEntryUtil
 * @generated
 */
@ProviderType
public interface PLOEntryPersistence extends BasePersistence<PLOEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PLOEntryUtil} to access the plo entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the plo entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching plo entries
	 */
	public java.util.List<PLOEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public PLOEntry[] findByCompanyId_PrevAndNext(
			long ploEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Removes all the plo entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of plo entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching plo entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_K(long companyId, String key);

	/**
	 * Returns a range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end);

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByC_K_First(
			long companyId, String key,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByC_K_First(
		long companyId, String key,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByC_K_Last(
			long companyId, String key,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByC_K_Last(
		long companyId, String key,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public PLOEntry[] findByC_K_PrevAndNext(
			long ploEntryId, long companyId, String key,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Removes all the plo entries where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 */
	public void removeByC_K(long companyId, String key);

	/**
	 * Returns the number of plo entries where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching plo entries
	 */
	public int countByC_K(long companyId, String key);

	/**
	 * Returns all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_L(
		long companyId, String languageId);

	/**
	 * Returns a range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end);

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	public java.util.List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByC_L_First(
			long companyId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByC_L_First(
		long companyId, String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByC_L_Last(
			long companyId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByC_L_Last(
		long companyId, String languageId,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public PLOEntry[] findByC_L_PrevAndNext(
			long ploEntryId, long companyId, String languageId,
			com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
				orderByComparator)
		throws NoSuchPLOEntryException;

	/**
	 * Removes all the plo entries where companyId = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 */
	public void removeByC_L(long companyId, String languageId);

	/**
	 * Returns the number of plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the number of matching plo entries
	 */
	public int countByC_L(long companyId, String languageId);

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or throws a <code>NoSuchPLOEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public PLOEntry findByC_K_L(long companyId, String key, String languageId)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByC_K_L(long companyId, String key, String languageId);

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public PLOEntry fetchByC_K_L(
		long companyId, String key, String languageId, boolean useFinderCache);

	/**
	 * Removes the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the plo entry that was removed
	 */
	public PLOEntry removeByC_K_L(long companyId, String key, String languageId)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the number of plo entries where companyId = &#63; and key = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the number of matching plo entries
	 */
	public int countByC_K_L(long companyId, String key, String languageId);

	/**
	 * Caches the plo entry in the entity cache if it is enabled.
	 *
	 * @param ploEntry the plo entry
	 */
	public void cacheResult(PLOEntry ploEntry);

	/**
	 * Caches the plo entries in the entity cache if it is enabled.
	 *
	 * @param ploEntries the plo entries
	 */
	public void cacheResult(java.util.List<PLOEntry> ploEntries);

	/**
	 * Creates a new plo entry with the primary key. Does not add the plo entry to the database.
	 *
	 * @param ploEntryId the primary key for the new plo entry
	 * @return the new plo entry
	 */
	public PLOEntry create(long ploEntryId);

	/**
	 * Removes the plo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry that was removed
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public PLOEntry remove(long ploEntryId) throws NoSuchPLOEntryException;

	public PLOEntry updateImpl(PLOEntry ploEntry);

	/**
	 * Returns the plo entry with the primary key or throws a <code>NoSuchPLOEntryException</code> if it could not be found.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public PLOEntry findByPrimaryKey(long ploEntryId)
		throws NoSuchPLOEntryException;

	/**
	 * Returns the plo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry, or <code>null</code> if a plo entry with the primary key could not be found
	 */
	public PLOEntry fetchByPrimaryKey(long ploEntryId);

	/**
	 * Returns all the plo entries.
	 *
	 * @return the plo entries
	 */
	public java.util.List<PLOEntry> findAll();

	/**
	 * Returns a range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of plo entries
	 */
	public java.util.List<PLOEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of plo entries
	 */
	public java.util.List<PLOEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of plo entries
	 */
	public java.util.List<PLOEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PLOEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the plo entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of plo entries.
	 *
	 * @return the number of plo entries
	 */
	public int countAll();

}