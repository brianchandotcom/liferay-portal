/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence;

import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce inventory warehouse service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseUtil
 * @generated
 */
@ProviderType
public interface CommerceInventoryWarehousePersistence
	extends BasePersistence<CommerceInventoryWarehouse> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceInventoryWarehouseUtil} to access the commerce inventory warehouse persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where uuid = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] findByUuid_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByUuid(
		String uuid);

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] filterFindByUuid_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Removes all the commerce inventory warehouses where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce inventory warehouses where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] findByUuid_C_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] filterFindByUuid_C_PrevAndNext(
			long commerceInventoryWarehouseId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Removes all the commerce inventory warehouses where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce inventory warehouses where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] findByCompanyId_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] filterFindByCompanyId_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] findByC_A_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] filterFindByC_A_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, boolean active);

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode);

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByC_C_First(
			long companyId, String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByC_C_First(
		long companyId, String countryTwoLettersISOCode,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByC_C_Last(
			long companyId, String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByC_C_Last(
		long companyId, String countryTwoLettersISOCode,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] findByC_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode);

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_C(
		long companyId, String countryTwoLettersISOCode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] filterFindByC_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId,
			String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 */
	public void removeByC_C(long companyId, String countryTwoLettersISOCode);

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByC_C(long companyId, String countryTwoLettersISOCode);

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public int filterCountByC_C(
		long companyId, String countryTwoLettersISOCode);

	/**
	 * Returns all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode);

	/**
	 * Returns a range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByC_A_C_First(
			long companyId, boolean active, String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the first commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByC_A_C_First(
		long companyId, boolean active, String countryTwoLettersISOCode,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByC_A_C_Last(
			long companyId, boolean active, String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the last commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByC_A_C_Last(
		long companyId, boolean active, String countryTwoLettersISOCode,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] findByC_A_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode);

	/**
	 * Returns a range of all the commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses that the user has permissions to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory warehouses that the user has permission to view
	 */
	public java.util.List<CommerceInventoryWarehouse> filterFindByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns the commerce inventory warehouses before and after the current commerce inventory warehouse in the ordered set of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the current commerce inventory warehouse
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse[] filterFindByC_A_C_PrevAndNext(
			long commerceInventoryWarehouseId, long companyId, boolean active,
			String countryTwoLettersISOCode,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceInventoryWarehouse> orderByComparator)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Removes all the commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 */
	public void removeByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode);

	/**
	 * Returns the number of commerce inventory warehouses where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode);

	/**
	 * Returns the number of commerce inventory warehouses that the user has permission to view where companyId = &#63; and active = &#63; and countryTwoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param countryTwoLettersISOCode the country two letters iso code
	 * @return the number of matching commerce inventory warehouses that the user has permission to view
	 */
	public int filterCountByC_A_C(
		long companyId, boolean active, String countryTwoLettersISOCode);

	/**
	 * Returns the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchInventoryWarehouseException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce inventory warehouse, or <code>null</code> if a matching commerce inventory warehouse could not be found
	 */
	public CommerceInventoryWarehouse fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce inventory warehouse where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce inventory warehouse that was removed
	 */
	public CommerceInventoryWarehouse removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the number of commerce inventory warehouses where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce inventory warehouses
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce inventory warehouse in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouse the commerce inventory warehouse
	 */
	public void cacheResult(
		CommerceInventoryWarehouse commerceInventoryWarehouse);

	/**
	 * Caches the commerce inventory warehouses in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryWarehouses the commerce inventory warehouses
	 */
	public void cacheResult(
		java.util.List<CommerceInventoryWarehouse> commerceInventoryWarehouses);

	/**
	 * Creates a new commerce inventory warehouse with the primary key. Does not add the commerce inventory warehouse to the database.
	 *
	 * @param commerceInventoryWarehouseId the primary key for the new commerce inventory warehouse
	 * @return the new commerce inventory warehouse
	 */
	public CommerceInventoryWarehouse create(long commerceInventoryWarehouseId);

	/**
	 * Removes the commerce inventory warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse that was removed
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse remove(long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseException;

	public CommerceInventoryWarehouse updateImpl(
		CommerceInventoryWarehouse commerceInventoryWarehouse);

	/**
	 * Returns the commerce inventory warehouse with the primary key or throws a <code>NoSuchInventoryWarehouseException</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse
	 * @throws NoSuchInventoryWarehouseException if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse findByPrimaryKey(
			long commerceInventoryWarehouseId)
		throws NoSuchInventoryWarehouseException;

	/**
	 * Returns the commerce inventory warehouse with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryWarehouseId the primary key of the commerce inventory warehouse
	 * @return the commerce inventory warehouse, or <code>null</code> if a commerce inventory warehouse with the primary key could not be found
	 */
	public CommerceInventoryWarehouse fetchByPrimaryKey(
		long commerceInventoryWarehouseId);

	/**
	 * Returns all the commerce inventory warehouses.
	 *
	 * @return the commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findAll();

	/**
	 * Returns a range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @return the range of commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce inventory warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryWarehouseModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouses
	 * @param end the upper bound of the range of commerce inventory warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory warehouses
	 */
	public java.util.List<CommerceInventoryWarehouse> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceInventoryWarehouse> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce inventory warehouses from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce inventory warehouses.
	 *
	 * @return the number of commerce inventory warehouses
	 */
	public int countAll();

}