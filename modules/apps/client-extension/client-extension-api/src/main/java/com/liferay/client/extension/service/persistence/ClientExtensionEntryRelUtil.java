/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.persistence;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the client extension entry rel service. This utility wraps <code>com.liferay.client.extension.service.persistence.impl.ClientExtensionEntryRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryRelPersistence
 * @generated
 */
public class ClientExtensionEntryRelUtil {

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
	public static void clearCache(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		getPersistence().clearCache(clientExtensionEntryRel);
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
	public static Map<Serializable, ClientExtensionEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ClientExtensionEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ClientExtensionEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ClientExtensionEntryRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ClientExtensionEntryRel update(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		return getPersistence().update(clientExtensionEntryRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ClientExtensionEntryRel update(
		ClientExtensionEntryRel clientExtensionEntryRel,
		ServiceContext serviceContext) {

		return getPersistence().update(clientExtensionEntryRel, serviceContext);
	}

	/**
	 * Returns all the client extension entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByUuid_First(
			String uuid,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByUuid_First(
		String uuid,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByUuid_Last(
			String uuid,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel[] findByUuid_PrevAndNext(
			long clientExtensionEntryRelId, String uuid,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUuid_PrevAndNext(
			clientExtensionEntryRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the client extension entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of client extension entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching client extension entry rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the client extension entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the client extension entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the client extension entry rel that was removed
	 */
	public static ClientExtensionEntryRel removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of client extension entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching client extension entry rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel[] findByUuid_C_PrevAndNext(
			long clientExtensionEntryRelId, String uuid, long companyId,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			clientExtensionEntryRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the client extension entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of client extension entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching client extension entry rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @return the matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode) {

		return getPersistence().findByC_CETERC(
			companyId, cetExternalReferenceCode);
	}

	/**
	 * Returns a range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end) {

		return getPersistence().findByC_CETERC(
			companyId, cetExternalReferenceCode, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findByC_CETERC(
			companyId, cetExternalReferenceCode, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_CETERC(
		long companyId, String cetExternalReferenceCode, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_CETERC(
			companyId, cetExternalReferenceCode, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByC_CETERC_First(
			long companyId, String cetExternalReferenceCode,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_CETERC_First(
			companyId, cetExternalReferenceCode, orderByComparator);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByC_CETERC_First(
		long companyId, String cetExternalReferenceCode,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByC_CETERC_First(
			companyId, cetExternalReferenceCode, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByC_CETERC_Last(
			long companyId, String cetExternalReferenceCode,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_CETERC_Last(
			companyId, cetExternalReferenceCode, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByC_CETERC_Last(
		long companyId, String cetExternalReferenceCode,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByC_CETERC_Last(
			companyId, cetExternalReferenceCode, orderByComparator);
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel[] findByC_CETERC_PrevAndNext(
			long clientExtensionEntryRelId, long companyId,
			String cetExternalReferenceCode,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_CETERC_PrevAndNext(
			clientExtensionEntryRelId, companyId, cetExternalReferenceCode,
			orderByComparator);
	}

	/**
	 * Removes all the client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 */
	public static void removeByC_CETERC(
		long companyId, String cetExternalReferenceCode) {

		getPersistence().removeByC_CETERC(companyId, cetExternalReferenceCode);
	}

	/**
	 * Returns the number of client extension entry rels where companyId = &#63; and cetExternalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param cetExternalReferenceCode the cet external reference code
	 * @return the number of matching client extension entry rels
	 */
	public static int countByC_CETERC(
		long companyId, String cetExternalReferenceCode) {

		return getPersistence().countByC_CETERC(
			companyId, cetExternalReferenceCode);
	}

	/**
	 * Returns all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK) {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel[] findByC_C_PrevAndNext(
			long clientExtensionEntryRelId, long classNameId, long classPK,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_C_PrevAndNext(
			clientExtensionEntryRelId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the client extension entry rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of client extension entry rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching client extension entry rels
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type) {

		return getPersistence().findByC_C_T(classNameId, classPK, type);
	}

	/**
	 * Returns a range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end) {

		return getPersistence().findByC_C_T(
			classNameId, classPK, type, start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findByC_C_T(
		long classNameId, long classPK, String type, int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_C_T(
			classNameId, classPK, type, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByC_C_T_First(
			long classNameId, long classPK, String type,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_C_T_First(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the first client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByC_C_T_First(
		long classNameId, long classPK, String type,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_T_First(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByC_C_T_Last(
			long classNameId, long classPK, String type,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the last client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByC_C_T_Last(
		long classNameId, long classPK, String type,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().fetchByC_C_T_Last(
			classNameId, classPK, type, orderByComparator);
	}

	/**
	 * Returns the client extension entry rels before and after the current client extension entry rel in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param clientExtensionEntryRelId the primary key of the current client extension entry rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel[] findByC_C_T_PrevAndNext(
			long clientExtensionEntryRelId, long classNameId, long classPK,
			String type,
			OrderByComparator<ClientExtensionEntryRel> orderByComparator)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByC_C_T_PrevAndNext(
			clientExtensionEntryRelId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	 * Removes all the client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	public static void removeByC_C_T(
		long classNameId, long classPK, String type) {

		getPersistence().removeByC_C_T(classNameId, classPK, type);
	}

	/**
	 * Returns the number of client extension entry rels where classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching client extension entry rels
	 */
	public static int countByC_C_T(
		long classNameId, long classPK, String type) {

		return getPersistence().countByC_C_T(classNameId, classPK, type);
	}

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel findByERC_G(
			String externalReferenceCode, long groupId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return getPersistence().fetchByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Returns the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	public static ClientExtensionEntryRel fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByERC_G(
			externalReferenceCode, groupId, useFinderCache);
	}

	/**
	 * Removes the client extension entry rel where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the client extension entry rel that was removed
	 */
	public static ClientExtensionEntryRel removeByERC_G(
			String externalReferenceCode, long groupId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().removeByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Returns the number of client extension entry rels where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching client extension entry rels
	 */
	public static int countByERC_G(String externalReferenceCode, long groupId) {
		return getPersistence().countByERC_G(externalReferenceCode, groupId);
	}

	/**
	 * Caches the client extension entry rel in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntryRel the client extension entry rel
	 */
	public static void cacheResult(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		getPersistence().cacheResult(clientExtensionEntryRel);
	}

	/**
	 * Caches the client extension entry rels in the entity cache if it is enabled.
	 *
	 * @param clientExtensionEntryRels the client extension entry rels
	 */
	public static void cacheResult(
		List<ClientExtensionEntryRel> clientExtensionEntryRels) {

		getPersistence().cacheResult(clientExtensionEntryRels);
	}

	/**
	 * Creates a new client extension entry rel with the primary key. Does not add the client extension entry rel to the database.
	 *
	 * @param clientExtensionEntryRelId the primary key for the new client extension entry rel
	 * @return the new client extension entry rel
	 */
	public static ClientExtensionEntryRel create(
		long clientExtensionEntryRelId) {

		return getPersistence().create(clientExtensionEntryRelId);
	}

	/**
	 * Removes the client extension entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel that was removed
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel remove(long clientExtensionEntryRelId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().remove(clientExtensionEntryRelId);
	}

	public static ClientExtensionEntryRel updateImpl(
		ClientExtensionEntryRel clientExtensionEntryRel) {

		return getPersistence().updateImpl(clientExtensionEntryRel);
	}

	/**
	 * Returns the client extension entry rel with the primary key or throws a <code>NoSuchClientExtensionEntryRelException</code> if it could not be found.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel
	 * @throws NoSuchClientExtensionEntryRelException if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel findByPrimaryKey(
			long clientExtensionEntryRelId)
		throws com.liferay.client.extension.exception.
			NoSuchClientExtensionEntryRelException {

		return getPersistence().findByPrimaryKey(clientExtensionEntryRelId);
	}

	/**
	 * Returns the client extension entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel, or <code>null</code> if a client extension entry rel with the primary key could not be found
	 */
	public static ClientExtensionEntryRel fetchByPrimaryKey(
		long clientExtensionEntryRelId) {

		return getPersistence().fetchByPrimaryKey(clientExtensionEntryRelId);
	}

	/**
	 * Returns all the client extension entry rels.
	 *
	 * @return the client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findAll(
		int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of client extension entry rels
	 */
	public static List<ClientExtensionEntryRel> findAll(
		int start, int end,
		OrderByComparator<ClientExtensionEntryRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the client extension entry rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of client extension entry rels.
	 *
	 * @return the number of client extension entry rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ClientExtensionEntryRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ClientExtensionEntryRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ClientExtensionEntryRelPersistence _persistence;

}