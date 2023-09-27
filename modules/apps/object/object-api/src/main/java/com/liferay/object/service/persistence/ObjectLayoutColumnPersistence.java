/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectLayoutColumnException;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object layout column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutColumnUtil
 * @generated
 */
@ProviderType
public interface ObjectLayoutColumnPersistence
	extends BasePersistence<ObjectLayoutColumn> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectLayoutColumnUtil} to access the object layout column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object layout columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid(String uuid);

	/**
	 * Returns a range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn[] findByUuid_PrevAndNext(
			long objectLayoutColumnId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Removes all the object layout columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object layout columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout columns
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn[] findByUuid_C_PrevAndNext(
			long objectLayoutColumnId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Removes all the object layout columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout columns
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object layout columns where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId);

	/**
	 * Returns a range of all the object layout columns where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout columns where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout columns where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectFieldId(
		long objectFieldId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByObjectFieldId_First(
			long objectFieldId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the first object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByObjectFieldId_First(
		long objectFieldId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the last object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByObjectFieldId_Last(
			long objectFieldId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the last object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByObjectFieldId_Last(
		long objectFieldId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn[] findByObjectFieldId_PrevAndNext(
			long objectLayoutColumnId, long objectFieldId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Removes all the object layout columns where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	public void removeByObjectFieldId(long objectFieldId);

	/**
	 * Returns the number of object layout columns where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object layout columns
	 */
	public int countByObjectFieldId(long objectFieldId);

	/**
	 * Returns all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @return the matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId);

	/**
	 * Returns a range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end);

	/**
	 * Returns an ordered range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByObjectLayoutRowId_First(
			long objectLayoutRowId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the first object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByObjectLayoutRowId_First(
		long objectLayoutRowId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the last object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn findByObjectLayoutRowId_Last(
			long objectLayoutRowId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the last object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	public ObjectLayoutColumn fetchByObjectLayoutRowId_Last(
		long objectLayoutRowId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn[] findByObjectLayoutRowId_PrevAndNext(
			long objectLayoutColumnId, long objectLayoutRowId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
				orderByComparator)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Removes all the object layout columns where objectLayoutRowId = &#63; from the database.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 */
	public void removeByObjectLayoutRowId(long objectLayoutRowId);

	/**
	 * Returns the number of object layout columns where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @return the number of matching object layout columns
	 */
	public int countByObjectLayoutRowId(long objectLayoutRowId);

	/**
	 * Caches the object layout column in the entity cache if it is enabled.
	 *
	 * @param objectLayoutColumn the object layout column
	 */
	public void cacheResult(ObjectLayoutColumn objectLayoutColumn);

	/**
	 * Caches the object layout columns in the entity cache if it is enabled.
	 *
	 * @param objectLayoutColumns the object layout columns
	 */
	public void cacheResult(
		java.util.List<ObjectLayoutColumn> objectLayoutColumns);

	/**
	 * Creates a new object layout column with the primary key. Does not add the object layout column to the database.
	 *
	 * @param objectLayoutColumnId the primary key for the new object layout column
	 * @return the new object layout column
	 */
	public ObjectLayoutColumn create(long objectLayoutColumnId);

	/**
	 * Removes the object layout column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column that was removed
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn remove(long objectLayoutColumnId)
		throws NoSuchObjectLayoutColumnException;

	public ObjectLayoutColumn updateImpl(ObjectLayoutColumn objectLayoutColumn);

	/**
	 * Returns the object layout column with the primary key or throws a <code>NoSuchObjectLayoutColumnException</code> if it could not be found.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn findByPrimaryKey(long objectLayoutColumnId)
		throws NoSuchObjectLayoutColumnException;

	/**
	 * Returns the object layout column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column, or <code>null</code> if a object layout column with the primary key could not be found
	 */
	public ObjectLayoutColumn fetchByPrimaryKey(long objectLayoutColumnId);

	/**
	 * Returns all the object layout columns.
	 *
	 * @return the object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findAll();

	/**
	 * Returns a range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout columns
	 */
	public java.util.List<ObjectLayoutColumn> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectLayoutColumn>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object layout columns from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object layout columns.
	 *
	 * @return the number of object layout columns
	 */
	public int countAll();

}