/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectViewFilterColumnException;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object view filter column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewFilterColumnUtil
 * @generated
 */
@ProviderType
public interface ObjectViewFilterColumnPersistence
	extends BasePersistence<ObjectViewFilterColumn> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectViewFilterColumnUtil} to access the object view filter column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid(String uuid);

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn[] findByUuid_PrevAndNext(
			long objectViewFilterColumnId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Removes all the object view filter columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object view filter columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view filter columns
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the first object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the last object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn[] findByUuid_C_PrevAndNext(
			long objectViewFilterColumnId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Removes all the object view filter columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object view filter columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view filter columns
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId);

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end);

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByObjectViewId_First(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByObjectViewId_First(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByObjectViewId_Last(
			long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByObjectViewId_Last(
		long objectViewId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn[] findByObjectViewId_PrevAndNext(
			long objectViewFilterColumnId, long objectViewId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	public void removeByObjectViewId(long objectViewId);

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view filter columns
	 */
	public int countByObjectViewId(long objectViewId);

	/**
	 * Returns all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName);

	/**
	 * Returns a range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end);

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the first object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn findByOVI_OFN_Last(
			long objectViewId, String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the last object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view filter column, or <code>null</code> if a matching object view filter column could not be found
	 */
	public ObjectViewFilterColumn fetchByOVI_OFN_Last(
		long objectViewId, String objectFieldName,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns the object view filter columns before and after the current object view filter column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewFilterColumnId the primary key of the current object view filter column
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn[] findByOVI_OFN_PrevAndNext(
			long objectViewFilterColumnId, long objectViewId,
			String objectFieldName,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectViewFilterColumn> orderByComparator)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Removes all the object view filter columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	public void removeByOVI_OFN(long objectViewId, String objectFieldName);

	/**
	 * Returns the number of object view filter columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view filter columns
	 */
	public int countByOVI_OFN(long objectViewId, String objectFieldName);

	/**
	 * Caches the object view filter column in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumn the object view filter column
	 */
	public void cacheResult(ObjectViewFilterColumn objectViewFilterColumn);

	/**
	 * Caches the object view filter columns in the entity cache if it is enabled.
	 *
	 * @param objectViewFilterColumns the object view filter columns
	 */
	public void cacheResult(
		java.util.List<ObjectViewFilterColumn> objectViewFilterColumns);

	/**
	 * Creates a new object view filter column with the primary key. Does not add the object view filter column to the database.
	 *
	 * @param objectViewFilterColumnId the primary key for the new object view filter column
	 * @return the new object view filter column
	 */
	public ObjectViewFilterColumn create(long objectViewFilterColumnId);

	/**
	 * Removes the object view filter column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column that was removed
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn remove(long objectViewFilterColumnId)
		throws NoSuchObjectViewFilterColumnException;

	public ObjectViewFilterColumn updateImpl(
		ObjectViewFilterColumn objectViewFilterColumn);

	/**
	 * Returns the object view filter column with the primary key or throws a <code>NoSuchObjectViewFilterColumnException</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column
	 * @throws NoSuchObjectViewFilterColumnException if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn findByPrimaryKey(
			long objectViewFilterColumnId)
		throws NoSuchObjectViewFilterColumnException;

	/**
	 * Returns the object view filter column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewFilterColumnId the primary key of the object view filter column
	 * @return the object view filter column, or <code>null</code> if a object view filter column with the primary key could not be found
	 */
	public ObjectViewFilterColumn fetchByPrimaryKey(
		long objectViewFilterColumnId);

	/**
	 * Returns all the object view filter columns.
	 *
	 * @return the object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findAll();

	/**
	 * Returns a range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @return the range of object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object view filter columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewFilterColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view filter columns
	 * @param end the upper bound of the range of object view filter columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view filter columns
	 */
	public java.util.List<ObjectViewFilterColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectViewFilterColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object view filter columns from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object view filter columns.
	 *
	 * @return the number of object view filter columns
	 */
	public int countAll();

}