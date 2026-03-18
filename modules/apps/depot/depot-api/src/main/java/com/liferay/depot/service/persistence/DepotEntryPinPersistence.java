/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.service.persistence;

import com.liferay.depot.exception.NoSuchEntryPinException;
import com.liferay.depot.model.DepotEntryPin;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the depot entry pin service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPinUtil
 * @generated
 */
@ProviderType
public interface DepotEntryPinPersistence
	extends BasePersistence<DepotEntryPin>, CTPersistence<DepotEntryPin> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DepotEntryPinUtil} to access the depot entry pin persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the depot entry pins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid(String uuid);

	/**
	 * Returns a range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the last depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the last depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the depot entry pins before and after the current depot entry pin in the ordered set where uuid = &#63;.
	 *
	 * @param depotEntryPinId the primary key of the current depot entry pin
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin[] findByUuid_PrevAndNext(
			long depotEntryPinId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Removes all the depot entry pins where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of depot entry pins where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching depot entry pins
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryPinException;

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the depot entry pin where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the depot entry pin where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the depot entry pin that was removed
	 */
	public DepotEntryPin removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryPinException;

	/**
	 * Returns the number of depot entry pins where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching depot entry pins
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the first depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the last depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the last depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the depot entry pins before and after the current depot entry pin in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param depotEntryPinId the primary key of the current depot entry pin
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin[] findByUuid_C_PrevAndNext(
			long depotEntryPinId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Removes all the depot entry pins where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of depot entry pins where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching depot entry pins
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the depot entry pins where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUserId(long userId);

	/**
	 * Returns a range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUserId(
		long userId, int start, int end);

	/**
	 * Returns an ordered range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry pins where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the first depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the last depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByUserId_Last(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the last depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the depot entry pins before and after the current depot entry pin in the ordered set where userId = &#63;.
	 *
	 * @param depotEntryPinId the primary key of the current depot entry pin
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin[] findByUserId_PrevAndNext(
			long depotEntryPinId, long userId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Removes all the depot entry pins where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of depot entry pins where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching depot entry pins
	 */
	public int countByUserId(long userId);

	/**
	 * Returns all the depot entry pins where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByDepotEntryId(long depotEntryId);

	/**
	 * Returns a range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry pins where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry pins
	 */
	public java.util.List<DepotEntryPin> findByDepotEntryId(
		long depotEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByDepotEntryId_First(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the first depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByDepotEntryId_First(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the last depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByDepotEntryId_Last(
			long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Returns the last depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByDepotEntryId_Last(
		long depotEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns the depot entry pins before and after the current depot entry pin in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryPinId the primary key of the current depot entry pin
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin[] findByDepotEntryId_PrevAndNext(
			long depotEntryPinId, long depotEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
				orderByComparator)
		throws NoSuchEntryPinException;

	/**
	 * Removes all the depot entry pins where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	public void removeByDepotEntryId(long depotEntryId);

	/**
	 * Returns the number of depot entry pins where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry pins
	 */
	public int countByDepotEntryId(long depotEntryId);

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pin
	 * @throws NoSuchEntryPinException if a matching depot entry pin could not be found
	 */
	public DepotEntryPin findByU_D(long userId, long depotEntryId)
		throws NoSuchEntryPinException;

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByU_D(long userId, long depotEntryId);

	/**
	 * Returns the depot entry pin where userId = &#63; and depotEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry pin, or <code>null</code> if a matching depot entry pin could not be found
	 */
	public DepotEntryPin fetchByU_D(
		long userId, long depotEntryId, boolean useFinderCache);

	/**
	 * Removes the depot entry pin where userId = &#63; and depotEntryId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the depot entry pin that was removed
	 */
	public DepotEntryPin removeByU_D(long userId, long depotEntryId)
		throws NoSuchEntryPinException;

	/**
	 * Returns the number of depot entry pins where userId = &#63; and depotEntryId = &#63;.
	 *
	 * @param userId the user ID
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry pins
	 */
	public int countByU_D(long userId, long depotEntryId);

	/**
	 * Caches the depot entry pin in the entity cache if it is enabled.
	 *
	 * @param depotEntryPin the depot entry pin
	 */
	public void cacheResult(DepotEntryPin depotEntryPin);

	/**
	 * Caches the depot entry pins in the entity cache if it is enabled.
	 *
	 * @param depotEntryPins the depot entry pins
	 */
	public void cacheResult(java.util.List<DepotEntryPin> depotEntryPins);

	/**
	 * Creates a new depot entry pin with the primary key. Does not add the depot entry pin to the database.
	 *
	 * @param depotEntryPinId the primary key for the new depot entry pin
	 * @return the new depot entry pin
	 */
	public DepotEntryPin create(long depotEntryPinId);

	/**
	 * Removes the depot entry pin with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin that was removed
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin remove(long depotEntryPinId)
		throws NoSuchEntryPinException;

	public DepotEntryPin updateImpl(DepotEntryPin depotEntryPin);

	/**
	 * Returns the depot entry pin with the primary key or throws a <code>NoSuchEntryPinException</code> if it could not be found.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin
	 * @throws NoSuchEntryPinException if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin findByPrimaryKey(long depotEntryPinId)
		throws NoSuchEntryPinException;

	/**
	 * Returns the depot entry pin with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotEntryPinId the primary key of the depot entry pin
	 * @return the depot entry pin, or <code>null</code> if a depot entry pin with the primary key could not be found
	 */
	public DepotEntryPin fetchByPrimaryKey(long depotEntryPinId);

	/**
	 * Returns all the depot entry pins.
	 *
	 * @return the depot entry pins
	 */
	public java.util.List<DepotEntryPin> findAll();

	/**
	 * Returns a range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @return the range of depot entry pins
	 */
	public java.util.List<DepotEntryPin> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot entry pins
	 */
	public java.util.List<DepotEntryPin> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator);

	/**
	 * Returns an ordered range of all the depot entry pins.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryPinModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry pins
	 * @param end the upper bound of the range of depot entry pins (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot entry pins
	 */
	public java.util.List<DepotEntryPin> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DepotEntryPin>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the depot entry pins from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of depot entry pins.
	 *
	 * @return the number of depot entry pins
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:1292394846