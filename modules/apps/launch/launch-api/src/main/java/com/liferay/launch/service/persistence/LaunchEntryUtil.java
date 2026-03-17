/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.persistence;

import com.liferay.launch.model.LaunchEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the launch entry service. This utility wraps <code>com.liferay.launch.service.persistence.impl.LaunchEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntryPersistence
 * @generated
 */
public class LaunchEntryUtil {

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
	public static void clearCache(LaunchEntry launchEntry) {
		getPersistence().clearCache(launchEntry);
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
	public static Map<Serializable, LaunchEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LaunchEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LaunchEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LaunchEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LaunchEntry update(LaunchEntry launchEntry) {
		return getPersistence().update(launchEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LaunchEntry update(
		LaunchEntry launchEntry, ServiceContext serviceContext) {

		return getPersistence().update(launchEntry, serviceContext);
	}

	/**
	 * Returns all the launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching launch entries
	 */
	public static List<LaunchEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<LaunchEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<LaunchEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByUuid_First(
			String uuid, OrderByComparator<LaunchEntry> orderByComparator)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByUuid_First(
		String uuid, OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByUuid_Last(
			String uuid, OrderByComparator<LaunchEntry> orderByComparator)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByUuid_Last(
		String uuid, OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Removes all the launch entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of launch entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching launch entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching launch entries
	 */
	public static List<LaunchEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<LaunchEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LaunchEntry> orderByComparator)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LaunchEntry> orderByComparator)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last launch entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the launch entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of launch entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the matching launch entries
	 */
	public static List<LaunchEntry> findByLaunchSetId(long launchSetId) {
		return getPersistence().findByLaunchSetId(launchSetId);
	}

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
	public static List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end) {

		return getPersistence().findByLaunchSetId(launchSetId, start, end);
	}

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
	public static List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().findByLaunchSetId(
			launchSetId, start, end, orderByComparator);
	}

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
	public static List<LaunchEntry> findByLaunchSetId(
		long launchSetId, int start, int end,
		OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLaunchSetId(
			launchSetId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByLaunchSetId_First(
			long launchSetId, OrderByComparator<LaunchEntry> orderByComparator)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByLaunchSetId_First(
			launchSetId, orderByComparator);
	}

	/**
	 * Returns the first launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByLaunchSetId_First(
		long launchSetId, OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().fetchByLaunchSetId_First(
			launchSetId, orderByComparator);
	}

	/**
	 * Returns the last launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByLaunchSetId_Last(
			long launchSetId, OrderByComparator<LaunchEntry> orderByComparator)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByLaunchSetId_Last(
			launchSetId, orderByComparator);
	}

	/**
	 * Returns the last launch entry in the ordered set where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByLaunchSetId_Last(
		long launchSetId, OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().fetchByLaunchSetId_Last(
			launchSetId, orderByComparator);
	}

	/**
	 * Removes all the launch entries where launchSetId = &#63; from the database.
	 *
	 * @param launchSetId the launch set ID
	 */
	public static void removeByLaunchSetId(long launchSetId) {
		getPersistence().removeByLaunchSetId(launchSetId);
	}

	/**
	 * Returns the number of launch entries where launchSetId = &#63;.
	 *
	 * @param launchSetId the launch set ID
	 * @return the number of matching launch entries
	 */
	public static int countByLaunchSetId(long launchSetId) {
		return getPersistence().countByLaunchSetId(launchSetId);
	}

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByC_C_C(classNameId, classPK, classVersion);
	}

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, classVersion);
	}

	/**
	 * Returns the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByC_C_C(
		long classNameId, long classPK, String classVersion,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C_C(
			classNameId, classPK, classVersion, useFinderCache);
	}

	/**
	 * Removes the launch entry where classNameId = &#63; and classPK = &#63; and classVersion = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the launch entry that was removed
	 */
	public static LaunchEntry removeByC_C_C(
			long classNameId, long classPK, String classVersion)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().removeByC_C_C(
			classNameId, classPK, classVersion);
	}

	/**
	 * Returns the number of launch entries where classNameId = &#63; and classPK = &#63; and classVersion = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param classVersion the class version
	 * @return the number of matching launch entries
	 */
	public static int countByC_C_C(
		long classNameId, long classPK, String classVersion) {

		return getPersistence().countByC_C_C(
			classNameId, classPK, classVersion);
	}

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry
	 * @throws NoSuchLaunchEntryException if a matching launch entry could not be found
	 */
	public static LaunchEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the launch entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching launch entry, or <code>null</code> if a matching launch entry could not be found
	 */
	public static LaunchEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the launch entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the launch entry that was removed
	 */
	public static LaunchEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of launch entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching launch entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the launch entry in the entity cache if it is enabled.
	 *
	 * @param launchEntry the launch entry
	 */
	public static void cacheResult(LaunchEntry launchEntry) {
		getPersistence().cacheResult(launchEntry);
	}

	/**
	 * Caches the launch entries in the entity cache if it is enabled.
	 *
	 * @param launchEntries the launch entries
	 */
	public static void cacheResult(List<LaunchEntry> launchEntries) {
		getPersistence().cacheResult(launchEntries);
	}

	/**
	 * Creates a new launch entry with the primary key. Does not add the launch entry to the database.
	 *
	 * @param launchEntryId the primary key for the new launch entry
	 * @return the new launch entry
	 */
	public static LaunchEntry create(long launchEntryId) {
		return getPersistence().create(launchEntryId);
	}

	/**
	 * Removes the launch entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry that was removed
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public static LaunchEntry remove(long launchEntryId)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().remove(launchEntryId);
	}

	public static LaunchEntry updateImpl(LaunchEntry launchEntry) {
		return getPersistence().updateImpl(launchEntry);
	}

	/**
	 * Returns the launch entry with the primary key or throws a <code>NoSuchLaunchEntryException</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry
	 * @throws NoSuchLaunchEntryException if a launch entry with the primary key could not be found
	 */
	public static LaunchEntry findByPrimaryKey(long launchEntryId)
		throws com.liferay.launch.exception.NoSuchLaunchEntryException {

		return getPersistence().findByPrimaryKey(launchEntryId);
	}

	/**
	 * Returns the launch entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param launchEntryId the primary key of the launch entry
	 * @return the launch entry, or <code>null</code> if a launch entry with the primary key could not be found
	 */
	public static LaunchEntry fetchByPrimaryKey(long launchEntryId) {
		return getPersistence().fetchByPrimaryKey(launchEntryId);
	}

	/**
	 * Returns all the launch entries.
	 *
	 * @return the launch entries
	 */
	public static List<LaunchEntry> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<LaunchEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<LaunchEntry> findAll(
		int start, int end, OrderByComparator<LaunchEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<LaunchEntry> findAll(
		int start, int end, OrderByComparator<LaunchEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the launch entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of launch entries.
	 *
	 * @return the number of launch entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LaunchEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(LaunchEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile LaunchEntryPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:576027500