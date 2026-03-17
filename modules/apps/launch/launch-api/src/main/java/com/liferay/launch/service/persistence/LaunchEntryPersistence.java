/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence;

import com.liferay.launch.exception.NoSuchLaunchEntryException;
import com.liferay.launch.model.LaunchEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the launch entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntryUtil
 * @generated
 */
@ProviderType
public interface LaunchEntryPersistence extends BasePersistence<LaunchEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LaunchEntryUtil} to access the launch entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns the launch entries before and after the current launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param launchEntryId the primary key of the current launch entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public LaunchEntry[] findByUuid_PrevAndNext(
			long launchEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Removes all the launch entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching launch entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns the launch entries before and after the current launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param launchEntryId the primary key of the current launch entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public LaunchEntry[] findByUuid_C_PrevAndNext(
			long launchEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Removes all the launch entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the matching launch entries
	 */
	public java.util.List<LaunchEntry> findByLaunchSetId(long launchSetId);

	/**
	 * Returns a range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end);

	/**
	 * Returns an ordered range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the launch entries where launchSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param launchSetId the launch set ID
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching launch entries
	 */
	public java.util.List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByLaunchSetId_First(
			long launchSetId,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByLaunchSetId_First(
		long launchSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns the last launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByLaunchSetId_Last(
			long launchSetId,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the last launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByLaunchSetId_Last(
		long launchSetId,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns the launch entries before and after the current launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchEntryId the primary key of the current launch entry
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public LaunchEntry[] findByLaunchSetId_PrevAndNext(
			long launchEntryId, long launchSetId,
			com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
				orderByComparator)
		throws NoSuchLaunchEntryException;

	/**
	 * Removes all the launch entries where launchSetId = &#63; from the database.
	 *
	 * @param launchSetId the launch set ID
	 */
	public void removeByLaunchSetId(long launchSetId);

	/**
	 * Returns the number of launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the number of matching launch entries
	 */
	public int countByLaunchSetId(long launchSetId);

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion);

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion,
		boolean useFinderCache);

	/**
	 * Removes the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the launch entry that was removed
	 */
	public LaunchEntry removeByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the number of launch entries where classNameId = &#63; and classPK = &#63; and classVersion = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the number of matching launch entries
	 */
	public int countByC_C_C(
		long classNameId, long classPK, String classVersion);

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public LaunchEntry findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public LaunchEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the launch entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the launch entry that was removed
	 */
	public LaunchEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the number of launch entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the launch entry in the entity cache if it is enabled.
	 *
	 * @param launchEntry the launch entry
	 */
	public void cacheResult(LaunchEntry launchEntry);

	/**
	 * Caches the launch entries in the entity cache if it is enabled.
	 *
	 * @param launchEntries the launch entries
	 */
	public void cacheResult(java.util.List<LaunchEntry> launchEntries);

	/**
	 * Creates a new launch entry with the primary key. Does not add the launch entry to the database.
	 *
	 * @param launchEntryId the primary key for the new launch entry
	 * @return the new launch entry
	 */
	public LaunchEntry create(long launchEntryId);

	/**
	 * Removes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public LaunchEntry remove(long launchEntryId)
		throws NoSuchLaunchEntryException;

	public LaunchEntry updateImpl(LaunchEntry launchEntry);

	/**
	 * Returns the launch entry with the primary key or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public LaunchEntry findByPrimaryKey(long launchEntryId)
		throws NoSuchLaunchEntryException;

	/**
	 * Returns the launch entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry, or <code>null</code> if a launch entry with the primary key could not be found
	 */
	public LaunchEntry fetchByPrimaryKey(long launchEntryId);

	/**
	 * Returns all the launch entries.
	 *
	 * @return the launch entries
	 */
	public java.util.List<LaunchEntry> findAll();

	/**
	 * Returns a range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @return the range of launch entries
	 */
	public java.util.List<LaunchEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of launch entries
	 */
	public java.util.List<LaunchEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the launch entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LaunchEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch entries
	 * @param end the upper bound of the range of launch entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of launch entries
	 */
	public java.util.List<LaunchEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LaunchEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the launch entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of launch entries.
	 *
	 * @return the number of launch entries
	 */
	public int countAll();

}
// SB-Hash:1056526949