/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object field setting service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectFieldSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFieldSettingPersistence
 * @generated
 */
public class ObjectFieldSettingUtil {

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
	public static void clearCache(ObjectFieldSetting objectFieldSetting) {
		getPersistence().clearCache(objectFieldSetting);
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
	public static Map<Serializable, ObjectFieldSetting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectFieldSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectFieldSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectFieldSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectFieldSetting update(
		ObjectFieldSetting objectFieldSetting) {

		return getPersistence().update(objectFieldSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectFieldSetting update(
		ObjectFieldSetting objectFieldSetting, ServiceContext serviceContext) {

		return getPersistence().update(objectFieldSetting, serviceContext);
	}

	/**
	 * Returns all the object field settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByUuid_First(
			String uuid,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting[] findByUuid_PrevAndNext(
			long objectFieldSettingId, String uuid,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByUuid_PrevAndNext(
			objectFieldSettingId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object field settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object field settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object field settings
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting[] findByUuid_C_PrevAndNext(
			long objectFieldSettingId, String uuid, long companyId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectFieldSettingId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object field settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object field settings
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object field settings where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object field settings
	 */
	public static List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId) {

		return getPersistence().findByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns a range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end) {

		return getPersistence().findByObjectFieldId(objectFieldId, start, end);
	}

	/**
	 * Returns an ordered range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().findByObjectFieldId(
			objectFieldId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	public static List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectFieldId(
			objectFieldId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByObjectFieldId_First(
			long objectFieldId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByObjectFieldId_First(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the first object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByObjectFieldId_First(
		long objectFieldId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().fetchByObjectFieldId_First(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the last object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByObjectFieldId_Last(
			long objectFieldId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByObjectFieldId_Last(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the last object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByObjectFieldId_Last(
		long objectFieldId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().fetchByObjectFieldId_Last(
			objectFieldId, orderByComparator);
	}

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting[] findByObjectFieldId_PrevAndNext(
			long objectFieldSettingId, long objectFieldId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByObjectFieldId_PrevAndNext(
			objectFieldSettingId, objectFieldId, orderByComparator);
	}

	/**
	 * Removes all the object field settings where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	public static void removeByObjectFieldId(long objectFieldId) {
		getPersistence().removeByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the number of object field settings where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object field settings
	 */
	public static int countByObjectFieldId(long objectFieldId) {
		return getPersistence().countByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or throws a <code>NoSuchObjectFieldSettingException</code> if it could not be found.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting findByOFI_N(
			long objectFieldId, String name)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByOFI_N(objectFieldId, name);
	}

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByOFI_N(
		long objectFieldId, String name) {

		return getPersistence().fetchByOFI_N(objectFieldId, name);
	}

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchByOFI_N(
		long objectFieldId, String name, boolean useFinderCache) {

		return getPersistence().fetchByOFI_N(
			objectFieldId, name, useFinderCache);
	}

	/**
	 * Removes the object field setting where objectFieldId = &#63; and name = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the object field setting that was removed
	 */
	public static ObjectFieldSetting removeByOFI_N(
			long objectFieldId, String name)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().removeByOFI_N(objectFieldId, name);
	}

	/**
	 * Returns the number of object field settings where objectFieldId = &#63; and name = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the number of matching object field settings
	 */
	public static int countByOFI_N(long objectFieldId, String name) {
		return getPersistence().countByOFI_N(objectFieldId, name);
	}

	/**
	 * Caches the object field setting in the entity cache if it is enabled.
	 *
	 * @param objectFieldSetting the object field setting
	 */
	public static void cacheResult(ObjectFieldSetting objectFieldSetting) {
		getPersistence().cacheResult(objectFieldSetting);
	}

	/**
	 * Caches the object field settings in the entity cache if it is enabled.
	 *
	 * @param objectFieldSettings the object field settings
	 */
	public static void cacheResult(
		List<ObjectFieldSetting> objectFieldSettings) {

		getPersistence().cacheResult(objectFieldSettings);
	}

	/**
	 * Creates a new object field setting with the primary key. Does not add the object field setting to the database.
	 *
	 * @param objectFieldSettingId the primary key for the new object field setting
	 * @return the new object field setting
	 */
	public static ObjectFieldSetting create(long objectFieldSettingId) {
		return getPersistence().create(objectFieldSettingId);
	}

	/**
	 * Removes the object field setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting that was removed
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting remove(long objectFieldSettingId)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().remove(objectFieldSettingId);
	}

	public static ObjectFieldSetting updateImpl(
		ObjectFieldSetting objectFieldSetting) {

		return getPersistence().updateImpl(objectFieldSetting);
	}

	/**
	 * Returns the object field setting with the primary key or throws a <code>NoSuchObjectFieldSettingException</code> if it could not be found.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting findByPrimaryKey(long objectFieldSettingId)
		throws com.liferay.object.exception.NoSuchObjectFieldSettingException {

		return getPersistence().findByPrimaryKey(objectFieldSettingId);
	}

	/**
	 * Returns the object field setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting, or <code>null</code> if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting fetchByPrimaryKey(
		long objectFieldSettingId) {

		return getPersistence().fetchByPrimaryKey(objectFieldSettingId);
	}

	/**
	 * Returns all the object field settings.
	 *
	 * @return the object field settings
	 */
	public static List<ObjectFieldSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of object field settings
	 */
	public static List<ObjectFieldSetting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object field settings
	 */
	public static List<ObjectFieldSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object field settings
	 */
	public static List<ObjectFieldSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object field settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object field settings.
	 *
	 * @return the number of object field settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectFieldSettingPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ObjectFieldSettingPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ObjectFieldSettingPersistence _persistence;

}