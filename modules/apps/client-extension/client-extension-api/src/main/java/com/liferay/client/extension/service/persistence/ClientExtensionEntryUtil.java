/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.persistence;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the client extension entry service. This utility wraps <code>com.liferay.client.extension.service.persistence.impl.ClientExtensionEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryPersistence
 * @generated
 */
public class ClientExtensionEntryUtil {

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
	public static void clearCache(ClientExtensionEntry clientExtensionEntry) {
		getPersistence().clearCache(clientExtensionEntry);
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
	public static Map<Serializable, ClientExtensionEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ClientExtensionEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ClientExtensionEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ClientExtensionEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ClientExtensionEntry update(
		ClientExtensionEntry clientExtensionEntry) {

		return getPersistence().update(clientExtensionEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ClientExtensionEntry update(
		ClientExtensionEntry clientExtensionEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(clientExtensionEntry, serviceContext);
	}

	/**
	 * Returns all the client extension entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the client extension entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByUuid_First(
			String uuid,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByUuid_Last(
			String uuid,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where uuid = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] findByUuid_PrevAndNext(
			long clientExtensionEntryId, String uuid,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			clientExtensionEntryId, uuid, orderByComparator);
	}

	/**
	 * Returns all the client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByUuid(String uuid) {
		return getPersistence().filterFindByUuid(uuid);
	}

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByUuid(
		String uuid, int start, int end) {

		return getPersistence().filterFindByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().filterFindByUuid(
			uuid, start, end, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] filterFindByUuid_PrevAndNext(
			long clientExtensionEntryId, String uuid,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().filterFindByUuid_PrevAndNext(
			clientExtensionEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the client extension entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of client extension entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the number of client extension entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public static int filterCountByUuid(String uuid) {
		return getPersistence().filterCountByUuid(uuid);
	}

	/**
	 * Returns all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] findByUuid_C_PrevAndNext(
			long clientExtensionEntryId, String uuid, long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			clientExtensionEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Returns all the client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByUuid_C(
		String uuid, long companyId) {

		return getPersistence().filterFindByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().filterFindByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().filterFindByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] filterFindByUuid_C_PrevAndNext(
			long clientExtensionEntryId, String uuid, long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().filterFindByUuid_C_PrevAndNext(
			clientExtensionEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the client extension entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of client extension entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of client extension entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public static int filterCountByUuid_C(String uuid, long companyId) {
		return getPersistence().filterCountByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the client extension entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the client extension entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] findByCompanyId_PrevAndNext(
			long clientExtensionEntryId, long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			clientExtensionEntryId, companyId, orderByComparator);
	}

	/**
	 * Returns all the client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByCompanyId(
		long companyId) {

		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] filterFindByCompanyId_PrevAndNext(
			long clientExtensionEntryId, long companyId,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().filterFindByCompanyId_PrevAndNext(
			clientExtensionEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the client extension entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of client extension entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching client extension entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the number of client extension entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public static int filterCountByCompanyId(long companyId) {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	 * Returns all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByC_T(
		long companyId, String type) {

		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entries
	 */
	public static List<ClientExtensionEntry> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByC_T_First(
			long companyId, String type,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByC_T_Last(
			long companyId, String type,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] findByC_T_PrevAndNext(
			long clientExtensionEntryId, long companyId, String type,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByC_T_PrevAndNext(
			clientExtensionEntryId, companyId, type, orderByComparator);
	}

	/**
	 * Returns all the client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByC_T(
		long companyId, String type) {

		return getPersistence().filterFindByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByC_T(
		long companyId, String type, int start, int end) {

		return getPersistence().filterFindByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entries that the user has permission to view
	 */
	public static List<ClientExtensionEntry> filterFindByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().filterFindByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the client extension entries before and after the current client extension entry in the ordered set of client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryId the primary key of the current client extension entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry[] filterFindByC_T_PrevAndNext(
			long clientExtensionEntryId, long companyId, String type,
			OrderByComparator<ClientExtensionEntry> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().filterFindByC_T_PrevAndNext(
			clientExtensionEntryId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the client extension entries where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, String type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of client extension entries where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching client extension entries
	 */
	public static int countByC_T(long companyId, String type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Returns the number of client extension entries that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching client extension entries that the user has permission to view
	 */
	public static int filterCountByC_T(long companyId, String type) {
		return getPersistence().filterCountByC_T(companyId, type);
	}

	/**
	 * Returns the client extension entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchClientExtensionEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching client extension entry
	 * @throws NoSuchClientExtensionEntryException if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the client extension entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the client extension entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry, or <code>null</code> if a matching client extension entry could not be found
	 */
	public static ClientExtensionEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the client extension entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the client extension entry that was removed
	 */
	public static ClientExtensionEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of client extension entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching client extension entries
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the client extension entry in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntry the client extension entry
	 */
	public static void cacheResult(ClientExtensionEntry clientExtensionEntry) {
		getPersistence().cacheResult(clientExtensionEntry);
	}

	/**
	 * Caches the client extension entries in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntries the client extension entries
	 */
	public static void cacheResult(
		List<ClientExtensionEntry> clientExtensionEntries) {

		getPersistence().cacheResult(clientExtensionEntries);
	}

	/**
	 * Creates a new client extension entry with the primary key. Does not add the client extension entry to the database.
	 *
	 * @param clientExtensionEntryId the primary key for the new client extension entry
	 * @return the new client extension entry
	 */
	public static ClientExtensionEntry create(long clientExtensionEntryId) {
		return getPersistence().create(clientExtensionEntryId);
	}

	/**
	 * Removes the client extension entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry that was removed
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry remove(long clientExtensionEntryId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().remove(clientExtensionEntryId);
	}

	public static ClientExtensionEntry updateImpl(
		ClientExtensionEntry clientExtensionEntry) {

		return getPersistence().updateImpl(clientExtensionEntry);
	}

	/**
	 * Returns the client extension entry with the primary key or throws a <code>NoSuchClientExtensionEntryException</code> if it could not be found.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry
	 * @throws NoSuchClientExtensionEntryException if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry findByPrimaryKey(
			long clientExtensionEntryId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryException {

		return getPersistence().findByPrimaryKey(clientExtensionEntryId);
	}

	/**
	 * Returns the client extension entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param clientExtensionEntryId the primary key of the client extension entry
	 * @return the client extension entry, or <code>null</code> if a client extension entry with the primary key could not be found
	 */
	public static ClientExtensionEntry fetchByPrimaryKey(
		long clientExtensionEntryId) {

		return getPersistence().fetchByPrimaryKey(clientExtensionEntryId);
	}

	/**
	 * Returns all the client extension entries.
	 *
	 * @return the client extension entries
	 */
	public static List<ClientExtensionEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @return the range of client extension entries
	 */
	public static List<ClientExtensionEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of client extension entries
	 */
	public static List<ClientExtensionEntry> findAll(
		int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entries
	 * @param end the upper bound of the range of client extension entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of client extension entries
	 */
	public static List<ClientExtensionEntry> findAll(
		int start, int end,
		OrderByComparator<ClientExtensionEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the client extension entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of client extension entries.
	 *
	 * @return the number of client extension entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ClientExtensionEntryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ClientExtensionEntryPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ClientExtensionEntryPersistence _persistence;

}