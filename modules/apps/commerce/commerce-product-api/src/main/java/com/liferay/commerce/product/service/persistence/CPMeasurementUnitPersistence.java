/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp measurement unit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPMeasurementUnitUtil
 * @generated
 */
@ProviderType
public interface CPMeasurementUnitPersistence
	extends BasePersistence<CPMeasurementUnit>,
			CTPersistence<CPMeasurementUnit> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPMeasurementUnitUtil} to access the cp measurement unit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	public java.util.List<CPMeasurementUnit> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator);

	/**
	 * Removes all the cp measurement units where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp measurement units where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp measurement units
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByUUID_G(String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the cp measurement unit where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp measurement unit that was removed
	 */
	public CPMeasurementUnit removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the number of cp measurement units where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp measurement units
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	public java.util.List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the first cp measurement unit in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator);

	/**
	 * Removes all the cp measurement units where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	public java.util.List<CPMeasurementUnit> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator);

	/**
	 * Removes all the cp measurement units where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of cp measurement units where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the cp measurement unit where companyId = &#63; and key = &#63; or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByC_K(long companyId, String key)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the cp measurement unit where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByC_K(
		long companyId, String key, boolean useFinderCache);

	/**
	 * Removes the cp measurement unit where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the cp measurement unit that was removed
	 */
	public CPMeasurementUnit removeByC_K(long companyId, String key)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the number of cp measurement units where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching cp measurement units
	 */
	public int countByC_K(long companyId, String key);

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	public java.util.List<CPMeasurementUnit> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByC_T_First(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator);

	/**
	 * Removes all the cp measurement units where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, int type);

	/**
	 * Returns the number of cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	public int countByC_T(long companyId, int type);

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp measurement units
	 */
	public java.util.List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByC_P_T_First(
			long companyId, boolean primary, int type,
			com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
				orderByComparator)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the first cp measurement unit in the ordered set where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByC_P_T_First(
		long companyId, boolean primary, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator);

	/**
	 * Removes all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 */
	public void removeByC_P_T(long companyId, boolean primary, int type);

	/**
	 * Returns the number of cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @return the number of matching cp measurement units
	 */
	public int countByC_P_T(long companyId, boolean primary, int type);

	/**
	 * Returns the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public CPMeasurementUnit fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache);

	/**
	 * Removes the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the cp measurement unit that was removed
	 */
	public CPMeasurementUnit removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the number of cp measurement units where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching cp measurement units
	 */
	public int countByERC_C(String externalReferenceCode, long companyId);

	/**
	 * Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	 *
	 * @param CPMeasurementUnitId the primary key for the new cp measurement unit
	 * @return the new cp measurement unit
	 */
	public CPMeasurementUnit create(long CPMeasurementUnitId);

	/**
	 * Removes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit that was removed
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	public CPMeasurementUnit remove(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException;

	public CPMeasurementUnit updateImpl(CPMeasurementUnit cpMeasurementUnit);

	/**
	 * Returns the cp measurement unit with the primary key or throws a <code>NoSuchCPMeasurementUnitException</code> if it could not be found.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit
	 * @throws NoSuchCPMeasurementUnitException if a cp measurement unit with the primary key could not be found
	 */
	public CPMeasurementUnit findByPrimaryKey(long CPMeasurementUnitId)
		throws NoSuchCPMeasurementUnitException;

	/**
	 * Returns the cp measurement unit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPMeasurementUnitId the primary key of the cp measurement unit
	 * @return the cp measurement unit, or <code>null</code> if a cp measurement unit with the primary key could not be found
	 */
	public CPMeasurementUnit fetchByPrimaryKey(long CPMeasurementUnitId);

	/**
	 * Returns the cp measurement unit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public default CPMeasurementUnit fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp measurement unit where companyId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public default CPMeasurementUnit fetchByC_K(long companyId, String key) {
		return fetchByC_K(companyId, key, true);
	}

	/**
	 * Returns the cp measurement unit where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	 */
	public default CPMeasurementUnit fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns all the cp measurement units where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByUuid(String uuid) {
		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp measurement units where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp measurement units where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByC_T(
		long companyId, int type) {

		return findByC_T(
			companyId, type,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByC_T(
		long companyId, int type, int start, int end) {

		return findByC_T(companyId, type, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @return the matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type) {

		return findByC_P_T(
			companyId, primary, type,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @return the range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type, int start, int end) {

		return findByC_P_T(companyId, primary, type, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the cp measurement units where companyId = &#63; and primary = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param primary the primary
	 * @param type the type
	 * @param start the lower bound of the range of cp measurement units
	 * @param end the upper bound of the range of cp measurement units (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp measurement units
	 */
	public default java.util.List<CPMeasurementUnit> findByC_P_T(
		long companyId, boolean primary, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPMeasurementUnit>
			orderByComparator) {

		return findByC_P_T(
			companyId, primary, type, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:25839433