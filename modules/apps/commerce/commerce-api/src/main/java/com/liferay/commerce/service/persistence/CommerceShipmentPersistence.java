/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce shipment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentUtil
 * @generated
 */
@ProviderType
public interface CommerceShipmentPersistence
	extends BasePersistence<CommerceShipment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShipmentUtil} to access the commerce shipment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce shipments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce shipments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce shipment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the first commerce shipment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the last commerce shipment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the last commerce shipment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where uuid = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment[] findByUuid_PrevAndNext(
			long commerceShipmentId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Removes all the commerce shipments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce shipments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce shipments
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce shipment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchShipmentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByUUID_G(String uuid, long groupId)
		throws NoSuchShipmentException;

	/**
	 * Returns the commerce shipment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the commerce shipment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce shipment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce shipment that was removed
	 */
	public CommerceShipment removeByUUID_G(String uuid, long groupId)
		throws NoSuchShipmentException;

	/**
	 * Returns the number of commerce shipments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce shipments
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce shipments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce shipments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce shipment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the first commerce shipment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the last commerce shipment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the last commerce shipment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment[] findByUuid_C_PrevAndNext(
			long commerceShipmentId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Removes all the commerce shipments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce shipments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce shipments
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce shipments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(long groupId);

	/**
	 * Returns a range of all the commerce shipments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment[] findByGroupId_PrevAndNext(
			long commerceShipmentId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns all the commerce shipments where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(long[] groupIds);

	/**
	 * Returns a range of all the commerce shipments where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(
		long[] groupIds, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByGroupId(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipments where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of commerce shipments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce shipments
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of commerce shipments where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching commerce shipments
	 */
	public int countByGroupId(long[] groupIds);

	/**
	 * Returns all the commerce shipments where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long groupId, long commerceAddressId);

	/**
	 * Returns a range of all the commerce shipments where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long groupId, long commerceAddressId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long groupId, long commerceAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long groupId, long commerceAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByG_C_First(
			long groupId, long commerceAddressId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByG_C_First(
		long groupId, long commerceAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByG_C_Last(
			long groupId, long commerceAddressId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByG_C_Last(
		long groupId, long commerceAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment[] findByG_C_PrevAndNext(
			long commerceShipmentId, long groupId, long commerceAddressId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns all the commerce shipments where groupId = any &#63; and commerceAddressId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param commerceAddressId the commerce address ID
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long[] groupIds, long commerceAddressId);

	/**
	 * Returns a range of all the commerce shipments where groupId = any &#63; and commerceAddressId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param commerceAddressId the commerce address ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long[] groupIds, long commerceAddressId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = any &#63; and commerceAddressId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param commerceAddressId the commerce address ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long[] groupIds, long commerceAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and commerceAddressId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param commerceAddressId the commerce address ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_C(
		long[] groupIds, long commerceAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipments where groupId = &#63; and commerceAddressId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 */
	public void removeByG_C(long groupId, long commerceAddressId);

	/**
	 * Returns the number of commerce shipments where groupId = &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceAddressId the commerce address ID
	 * @return the number of matching commerce shipments
	 */
	public int countByG_C(long groupId, long commerceAddressId);

	/**
	 * Returns the number of commerce shipments where groupId = any &#63; and commerceAddressId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param commerceAddressId the commerce address ID
	 * @return the number of matching commerce shipments
	 */
	public int countByG_C(long[] groupIds, long commerceAddressId);

	/**
	 * Returns all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(long groupId, int status);

	/**
	 * Returns a range of all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment[] findByG_S_PrevAndNext(
			long commerceShipmentId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
				orderByComparator)
		throws NoSuchShipmentException;

	/**
	 * Returns all the commerce shipments where groupId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param status the status
	 * @return the matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long[] groupIds, int status);

	/**
	 * Returns a range of all the commerce shipments where groupId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long[] groupIds, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long[] groupIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	public java.util.List<CommerceShipment> findByG_S(
		long[] groupIds, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipments where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching commerce shipments
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns the number of commerce shipments where groupId = any &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param status the status
	 * @return the number of matching commerce shipments
	 */
	public int countByG_S(long[] groupIds, int status);

	/**
	 * Returns the commerce shipment where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchShipmentException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	public CommerceShipment findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchShipmentException;

	/**
	 * Returns the commerce shipment where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce shipment where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public CommerceShipment fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce shipment where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce shipment that was removed
	 */
	public CommerceShipment removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchShipmentException;

	/**
	 * Returns the number of commerce shipments where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce shipments
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce shipment in the entity cache if it is enabled.
	 *
	 * @param commerceShipment the commerce shipment
	 */
	public void cacheResult(CommerceShipment commerceShipment);

	/**
	 * Caches the commerce shipments in the entity cache if it is enabled.
	 *
	 * @param commerceShipments the commerce shipments
	 */
	public void cacheResult(java.util.List<CommerceShipment> commerceShipments);

	/**
	 * Creates a new commerce shipment with the primary key. Does not add the commerce shipment to the database.
	 *
	 * @param commerceShipmentId the primary key for the new commerce shipment
	 * @return the new commerce shipment
	 */
	public CommerceShipment create(long commerceShipmentId);

	/**
	 * Removes the commerce shipment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment that was removed
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment remove(long commerceShipmentId)
		throws NoSuchShipmentException;

	public CommerceShipment updateImpl(CommerceShipment commerceShipment);

	/**
	 * Returns the commerce shipment with the primary key or throws a <code>NoSuchShipmentException</code> if it could not be found.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment findByPrimaryKey(long commerceShipmentId)
		throws NoSuchShipmentException;

	/**
	 * Returns the commerce shipment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment, or <code>null</code> if a commerce shipment with the primary key could not be found
	 */
	public CommerceShipment fetchByPrimaryKey(long commerceShipmentId);

	/**
	 * Returns all the commerce shipments.
	 *
	 * @return the commerce shipments
	 */
	public java.util.List<CommerceShipment> findAll();

	/**
	 * Returns a range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of commerce shipments
	 */
	public java.util.List<CommerceShipment> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipments
	 */
	public java.util.List<CommerceShipment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipments
	 */
	public java.util.List<CommerceShipment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceShipment>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce shipments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce shipments.
	 *
	 * @return the number of commerce shipments
	 */
	public int countAll();

}