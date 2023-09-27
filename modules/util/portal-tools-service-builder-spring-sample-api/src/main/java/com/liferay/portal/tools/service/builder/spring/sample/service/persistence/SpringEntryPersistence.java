/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.spring.sample.exception.NoSuchSpringEntryException;
import com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the spring entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SpringEntryUtil
 * @generated
 */
@ProviderType
public interface SpringEntryPersistence extends BasePersistence<SpringEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SpringEntryUtil} to access the spring entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the spring entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the spring entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @return the range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the spring entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spring entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public SpringEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public SpringEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public SpringEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public SpringEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns the spring entries before and after the current spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param springEntryId the primary key of the current spring entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public SpringEntry[] findByUuid_PrevAndNext(
			long springEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Removes all the spring entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of spring entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching spring entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @return the range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public SpringEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public SpringEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public SpringEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public SpringEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns the spring entries before and after the current spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param springEntryId the primary key of the current spring entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public SpringEntry[] findByUuid_C_PrevAndNext(
			long springEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Removes all the spring entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching spring entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the spring entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spring entries
	 */
	public java.util.List<SpringEntry> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the spring entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @return the range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the spring entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spring entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spring entries
	 */
	public java.util.List<SpringEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public SpringEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the first spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public SpringEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns the last spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public SpringEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the last spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public SpringEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns the spring entries before and after the current spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param springEntryId the primary key of the current spring entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public SpringEntry[] findByCompanyId_PrevAndNext(
			long springEntryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
				orderByComparator)
		throws NoSuchSpringEntryException;

	/**
	 * Removes all the spring entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of spring entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spring entries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Caches the spring entry in the entity cache if it is enabled.
	 *
	 * @param springEntry the spring entry
	 */
	public void cacheResult(SpringEntry springEntry);

	/**
	 * Caches the spring entries in the entity cache if it is enabled.
	 *
	 * @param springEntries the spring entries
	 */
	public void cacheResult(java.util.List<SpringEntry> springEntries);

	/**
	 * Creates a new spring entry with the primary key. Does not add the spring entry to the database.
	 *
	 * @param springEntryId the primary key for the new spring entry
	 * @return the new spring entry
	 */
	public SpringEntry create(long springEntryId);

	/**
	 * Removes the spring entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry that was removed
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public SpringEntry remove(long springEntryId)
		throws NoSuchSpringEntryException;

	public SpringEntry updateImpl(SpringEntry springEntry);

	/**
	 * Returns the spring entry with the primary key or throws a <code>NoSuchSpringEntryException</code> if it could not be found.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public SpringEntry findByPrimaryKey(long springEntryId)
		throws NoSuchSpringEntryException;

	/**
	 * Returns the spring entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry, or <code>null</code> if a spring entry with the primary key could not be found
	 */
	public SpringEntry fetchByPrimaryKey(long springEntryId);

	/**
	 * Returns all the spring entries.
	 *
	 * @return the spring entries
	 */
	public java.util.List<SpringEntry> findAll();

	/**
	 * Returns a range of all the spring entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @return the range of spring entries
	 */
	public java.util.List<SpringEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the spring entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of spring entries
	 */
	public java.util.List<SpringEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the spring entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of spring entries
	 */
	public java.util.List<SpringEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SpringEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the spring entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of spring entries.
	 *
	 * @return the number of spring entries
	 */
	public int countAll();

}