/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the spring entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.spring.sample.service.persistence.impl.SpringEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SpringEntryPersistence
 * @generated
 */
public class SpringEntryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(SpringEntry springEntry) {
		getPersistence().clearCache(springEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, SpringEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SpringEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SpringEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SpringEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SpringEntry update(SpringEntry springEntry) {
		return getPersistence().update(springEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SpringEntry update(
		SpringEntry springEntry, ServiceContext serviceContext) {

		return getPersistence().update(springEntry, serviceContext);
	}

	/**
	 * Returns all the spring entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching spring entries
	 */
	public static List<SpringEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<SpringEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<SpringEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<SpringEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public static SpringEntry findByUuid_First(
			String uuid, OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public static SpringEntry fetchByUuid_First(
		String uuid, OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public static SpringEntry findByUuid_Last(
			String uuid, OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public static SpringEntry fetchByUuid_Last(
		String uuid, OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the spring entries before and after the current spring entry in the ordered set where uuid = &#63;.
	 *
	 * @param springEntryId the primary key of the current spring entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public static SpringEntry[] findByUuid_PrevAndNext(
			long springEntryId, String uuid,
			OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			springEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the spring entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of spring entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching spring entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching spring entries
	 */
	public static List<SpringEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<SpringEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<SpringEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<SpringEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public static SpringEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public static SpringEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public static SpringEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last spring entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public static SpringEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

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
	public static SpringEntry[] findByUuid_C_PrevAndNext(
			long springEntryId, String uuid, long companyId,
			OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			springEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the spring entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of spring entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching spring entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the spring entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spring entries
	 */
	public static List<SpringEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

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
	public static List<SpringEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

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
	public static List<SpringEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

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
	public static List<SpringEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SpringEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public static SpringEntry findByCompanyId_First(
			long companyId, OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public static SpringEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry
	 * @throws NoSuchSpringEntryException if a matching spring entry could not be found
	 */
	public static SpringEntry findByCompanyId_Last(
			long companyId, OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	public static SpringEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the spring entries before and after the current spring entry in the ordered set where companyId = &#63;.
	 *
	 * @param springEntryId the primary key of the current spring entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public static SpringEntry[] findByCompanyId_PrevAndNext(
			long springEntryId, long companyId,
			OrderByComparator<SpringEntry> orderByComparator)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			springEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the spring entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of spring entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spring entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the spring entry in the entity cache if it is enabled.
	 *
	 * @param springEntry the spring entry
	 */
	public static void cacheResult(SpringEntry springEntry) {
		getPersistence().cacheResult(springEntry);
	}

	/**
	 * Caches the spring entries in the entity cache if it is enabled.
	 *
	 * @param springEntries the spring entries
	 */
	public static void cacheResult(List<SpringEntry> springEntries) {
		getPersistence().cacheResult(springEntries);
	}

	/**
	 * Creates a new spring entry with the primary key. Does not add the spring entry to the database.
	 *
	 * @param springEntryId the primary key for the new spring entry
	 * @return the new spring entry
	 */
	public static SpringEntry create(long springEntryId) {
		return getPersistence().create(springEntryId);
	}

	/**
	 * Removes the spring entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry that was removed
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public static SpringEntry remove(long springEntryId)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().remove(springEntryId);
	}

	public static SpringEntry updateImpl(SpringEntry springEntry) {
		return getPersistence().updateImpl(springEntry);
	}

	/**
	 * Returns the spring entry with the primary key or throws a <code>NoSuchSpringEntryException</code> if it could not be found.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry
	 * @throws NoSuchSpringEntryException if a spring entry with the primary key could not be found
	 */
	public static SpringEntry findByPrimaryKey(long springEntryId)
		throws com.liferay.portal.tools.service.builder.spring.sample.exception.
			NoSuchSpringEntryException {

		return getPersistence().findByPrimaryKey(springEntryId);
	}

	/**
	 * Returns the spring entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry, or <code>null</code> if a spring entry with the primary key could not be found
	 */
	public static SpringEntry fetchByPrimaryKey(long springEntryId) {
		return getPersistence().fetchByPrimaryKey(springEntryId);
	}

	/**
	 * Returns all the spring entries.
	 *
	 * @return the spring entries
	 */
	public static List<SpringEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<SpringEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<SpringEntry> findAll(
		int start, int end, OrderByComparator<SpringEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<SpringEntry> findAll(
		int start, int end, OrderByComparator<SpringEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the spring entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of spring entries.
	 *
	 * @return the number of spring entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SpringEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(SpringEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile SpringEntryPersistence _persistence;

}