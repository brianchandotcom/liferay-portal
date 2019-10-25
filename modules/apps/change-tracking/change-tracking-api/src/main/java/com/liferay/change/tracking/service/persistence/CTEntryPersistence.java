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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryUtil
 * @generated
 */
@ProviderType
public interface CTEntryPersistence extends BasePersistence<CTEntry> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEntryUtil} to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct entries
	 */
	public java.util.List<CTEntry> findByCTCollectionId(long ctCollectionId);

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public java.util.List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByCTCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByCTCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByCTCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByCTCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry[] findByCTCollectionId_PrevAndNext(
			long ctEntryId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCTCollectionId(long ctCollectionId);

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct entries
	 */
	public int countByCTCollectionId(long ctCollectionId);

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	public java.util.List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId);

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public java.util.List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByC_MCNI_First(
			long ctCollectionId, long modelClassNameId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_MCNI_First(
		long ctCollectionId, long modelClassNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByC_MCNI_Last(
			long ctCollectionId, long modelClassNameId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_MCNI_Last(
		long ctCollectionId, long modelClassNameId,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry[] findByC_MCNI_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 */
	public void removeByC_MCNI(long ctCollectionId, long modelClassNameId);

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	public int countByC_MCNI(long ctCollectionId, long modelClassNameId);

	/**
	 * Returns all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK);

	/**
	 * Returns a range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByNotC_MCPK_First(
			long ctCollectionId, long modelClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByNotC_MCPK_First(
		long ctCollectionId, long modelClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByNotC_MCPK_Last(
			long ctCollectionId, long modelClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByNotC_MCPK_Last(
		long ctCollectionId, long modelClassPK,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry[] findByNotC_MCPK_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassPK,
			com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @return the matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs);

	/**
	 * Returns a range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs, int start, int end);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public java.util.List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 */
	public void removeByNotC_MCPK(long ctCollectionId, long modelClassPK);

	/**
	 * Returns the number of ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	public int countByNotC_MCPK(long ctCollectionId, long modelClassPK);

	/**
	 * Returns the number of ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @return the number of matching ct entries
	 */
	public int countByNotC_MCPK(long ctCollectionId, long[] modelClassPKs);

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public CTEntry findByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException;

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK);

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		boolean useFinderCache);

	/**
	 * Removes the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	public CTEntry removeByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException;

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	public int countByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK);

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	public void cacheResult(CTEntry ctEntry);

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	public void cacheResult(java.util.List<CTEntry> ctEntries);

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	public CTEntry create(long ctEntryId);

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry remove(long ctEntryId) throws NoSuchEntryException;

	public CTEntry updateImpl(CTEntry ctEntry);

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public CTEntry findByPrimaryKey(long ctEntryId) throws NoSuchEntryException;

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	public CTEntry fetchByPrimaryKey(long ctEntryId);

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	public java.util.List<CTEntry> findAll();

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	public java.util.List<CTEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	public java.util.List<CTEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct entries
	 */
	public java.util.List<CTEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	public int countAll();

}