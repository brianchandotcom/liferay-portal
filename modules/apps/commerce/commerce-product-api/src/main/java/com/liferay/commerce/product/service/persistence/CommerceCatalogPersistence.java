/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCatalogException;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce catalog service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCatalogUtil
 * @generated
 */
@ProviderType
public interface CommerceCatalogPersistence
	extends BasePersistence<CommerceCatalog>, CTPersistence<CommerceCatalog> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCatalogUtil} to access the commerce catalog persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce catalogs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce catalogs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce catalogs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce catalog in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the first commerce catalog in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the last commerce catalog in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the last commerce catalog in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where uuid = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] findByUuid_PrevAndNext(
			long commerceCatalogId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns all the commerce catalogs that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByUuid(String uuid);

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where uuid = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] filterFindByUuid_PrevAndNext(
			long commerceCatalogId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Removes all the commerce catalogs where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce catalogs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce catalogs
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public int filterCountByUuid(String uuid);

	/**
	 * Returns all the commerce catalogs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce catalogs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce catalogs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce catalog in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the first commerce catalog in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the last commerce catalog in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the last commerce catalog in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] findByUuid_C_PrevAndNext(
			long commerceCatalogId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns all the commerce catalogs that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] filterFindByUuid_C_PrevAndNext(
			long commerceCatalogId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Removes all the commerce catalogs where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce catalogs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public int filterCountByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce catalogs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce catalogs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] findByCompanyId_PrevAndNext(
			long commerceCatalogId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns all the commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] filterFindByCompanyId_PrevAndNext(
			long commerceCatalogId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Removes all the commerce catalogs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce catalogs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce catalogs where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByAccountEntryId(
		long accountEntryId);

	/**
	 * Returns a range of all the commerce catalogs where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByAccountEntryId(
		long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce catalogs where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce catalog in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByAccountEntryId_First(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the first commerce catalog in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByAccountEntryId_First(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the last commerce catalog in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByAccountEntryId_Last(
			long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the last commerce catalog in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByAccountEntryId_Last(
		long accountEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where accountEntryId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] findByAccountEntryId_PrevAndNext(
			long commerceCatalogId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns all the commerce catalogs that the user has permission to view where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByAccountEntryId(
		long accountEntryId);

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByAccountEntryId(
		long accountEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByAccountEntryId(
		long accountEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where accountEntryId = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] filterFindByAccountEntryId_PrevAndNext(
			long commerceCatalogId, long accountEntryId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Removes all the commerce catalogs where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	public void removeByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of commerce catalogs where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching commerce catalogs
	 */
	public int countByAccountEntryId(long accountEntryId);

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public int filterCountByAccountEntryId(long accountEntryId);

	/**
	 * Returns all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByC_S(
		long companyId, boolean system);

	/**
	 * Returns a range of all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByC_S(
		long companyId, boolean system, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByC_S(
		long companyId, boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findByC_S(
		long companyId, boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByC_S_First(
			long companyId, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the first commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByC_S_First(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByC_S_Last(
			long companyId, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns the last commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByC_S_Last(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set where companyId = &#63; and system = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] findByC_S_PrevAndNext(
			long commerceCatalogId, long companyId, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Returns all the commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByC_S(
		long companyId, boolean system);

	/**
	 * Returns a range of all the commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByC_S(
		long companyId, boolean system, int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs that the user has permissions to view where companyId = &#63; and system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce catalogs that the user has permission to view
	 */
	public java.util.List<CommerceCatalog> filterFindByC_S(
		long companyId, boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns the commerce catalogs before and after the current commerce catalog in the ordered set of commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * @param commerceCatalogId the primary key of the current commerce catalog
	 * @param companyId the company ID
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog[] filterFindByC_S_PrevAndNext(
			long commerceCatalogId, long companyId, boolean system,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
				orderByComparator)
		throws NoSuchCatalogException;

	/**
	 * Removes all the commerce catalogs where companyId = &#63; and system = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 */
	public void removeByC_S(long companyId, boolean system);

	/**
	 * Returns the number of commerce catalogs where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the number of matching commerce catalogs
	 */
	public int countByC_S(long companyId, boolean system);

	/**
	 * Returns the number of commerce catalogs that the user has permission to view where companyId = &#63; and system = &#63;.
	 *
	 * @param companyId the company ID
	 * @param system the system
	 * @return the number of matching commerce catalogs that the user has permission to view
	 */
	public int filterCountByC_S(long companyId, boolean system);

	/**
	 * Returns the commerce catalog where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCatalogException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce catalog
	 * @throws NoSuchCatalogException if a matching commerce catalog could not be found
	 */
	public CommerceCatalog findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCatalogException;

	/**
	 * Returns the commerce catalog where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByERC_C(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the commerce catalog where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce catalog, or <code>null</code> if a matching commerce catalog could not be found
	 */
	public CommerceCatalog fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the commerce catalog where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce catalog that was removed
	 */
	public CommerceCatalog removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCatalogException;

	/**
	 * Returns the number of commerce catalogs where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce catalogs
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Caches the commerce catalog in the entity cache if it is enabled.
	 *
	 * @param commerceCatalog the commerce catalog
	 */
	public void cacheResult(CommerceCatalog commerceCatalog);

	/**
	 * Caches the commerce catalogs in the entity cache if it is enabled.
	 *
	 * @param commerceCatalogs the commerce catalogs
	 */
	public void cacheResult(java.util.List<CommerceCatalog> commerceCatalogs);

	/**
	 * Creates a new commerce catalog with the primary key. Does not add the commerce catalog to the database.
	 *
	 * @param commerceCatalogId the primary key for the new commerce catalog
	 * @return the new commerce catalog
	 */
	public CommerceCatalog create(long commerceCatalogId);

	/**
	 * Removes the commerce catalog with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCatalogId the primary key of the commerce catalog
	 * @return the commerce catalog that was removed
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog remove(long commerceCatalogId)
		throws NoSuchCatalogException;

	public CommerceCatalog updateImpl(CommerceCatalog commerceCatalog);

	/**
	 * Returns the commerce catalog with the primary key or throws a <code>NoSuchCatalogException</code> if it could not be found.
	 *
	 * @param commerceCatalogId the primary key of the commerce catalog
	 * @return the commerce catalog
	 * @throws NoSuchCatalogException if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog findByPrimaryKey(long commerceCatalogId)
		throws NoSuchCatalogException;

	/**
	 * Returns the commerce catalog with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCatalogId the primary key of the commerce catalog
	 * @return the commerce catalog, or <code>null</code> if a commerce catalog with the primary key could not be found
	 */
	public CommerceCatalog fetchByPrimaryKey(long commerceCatalogId);

	/**
	 * Returns all the commerce catalogs.
	 *
	 * @return the commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findAll();

	/**
	 * Returns a range of all the commerce catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @return the range of commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce catalogs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCatalogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce catalogs
	 * @param end the upper bound of the range of commerce catalogs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce catalogs
	 */
	public java.util.List<CommerceCatalog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCatalog>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce catalogs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce catalogs.
	 *
	 * @return the number of commerce catalogs
	 */
	public int countAll();

}