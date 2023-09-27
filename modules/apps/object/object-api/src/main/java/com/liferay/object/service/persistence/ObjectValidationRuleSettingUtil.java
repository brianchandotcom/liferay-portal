/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object validation rule setting service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectValidationRuleSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectValidationRuleSettingPersistence
 * @generated
 */
public class ObjectValidationRuleSettingUtil {

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
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		getPersistence().clearCache(objectValidationRuleSetting);
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
	public static Map<Serializable, ObjectValidationRuleSetting>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectValidationRuleSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectValidationRuleSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectValidationRuleSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectValidationRuleSetting update(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		return getPersistence().update(objectValidationRuleSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectValidationRuleSetting update(
		ObjectValidationRuleSetting objectValidationRuleSetting,
		ServiceContext serviceContext) {

		return getPersistence().update(
			objectValidationRuleSetting, serviceContext);
	}

	/**
	 * Returns all the object validation rule settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByUuid_First(
			String uuid,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting[] findByUuid_PrevAndNext(
			long objectValidationRuleSettingId, String uuid,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByUuid_PrevAndNext(
			objectValidationRuleSettingId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object validation rule settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object validation rule settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object validation rule settings
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting[] findByUuid_C_PrevAndNext(
			long objectValidationRuleSettingId, String uuid, long companyId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectValidationRuleSettingId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object validation rule settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object validation rule settings
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @return the matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(long objectValidationRuleId) {

		return getPersistence().findByObjectValidationRuleId(
			objectValidationRuleId);
	}

	/**
	 * Returns a range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(
			long objectValidationRuleId, int start, int end) {

		return getPersistence().findByObjectValidationRuleId(
			objectValidationRuleId, start, end);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(
			long objectValidationRuleId, int start, int end,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().findByObjectValidationRuleId(
			objectValidationRuleId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(
			long objectValidationRuleId, int start, int end,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
			boolean useFinderCache) {

		return getPersistence().findByObjectValidationRuleId(
			objectValidationRuleId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting
			findByObjectValidationRuleId_First(
				long objectValidationRuleId,
				OrderByComparator<ObjectValidationRuleSetting>
					orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByObjectValidationRuleId_First(
			objectValidationRuleId, orderByComparator);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting
		fetchByObjectValidationRuleId_First(
			long objectValidationRuleId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByObjectValidationRuleId_First(
			objectValidationRuleId, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByObjectValidationRuleId_Last(
			long objectValidationRuleId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByObjectValidationRuleId_Last(
			objectValidationRuleId, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting
		fetchByObjectValidationRuleId_Last(
			long objectValidationRuleId,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByObjectValidationRuleId_Last(
			objectValidationRuleId, orderByComparator);
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting[]
			findByObjectValidationRuleId_PrevAndNext(
				long objectValidationRuleSettingId, long objectValidationRuleId,
				OrderByComparator<ObjectValidationRuleSetting>
					orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByObjectValidationRuleId_PrevAndNext(
			objectValidationRuleSettingId, objectValidationRuleId,
			orderByComparator);
	}

	/**
	 * Removes all the object validation rule settings where objectValidationRuleId = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 */
	public static void removeByObjectValidationRuleId(
		long objectValidationRuleId) {

		getPersistence().removeByObjectValidationRuleId(objectValidationRuleId);
	}

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @return the number of matching object validation rule settings
	 */
	public static int countByObjectValidationRuleId(
		long objectValidationRuleId) {

		return getPersistence().countByObjectValidationRuleId(
			objectValidationRuleId);
	}

	/**
	 * Returns all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @return the matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name) {

		return getPersistence().findByOVRI_N(objectValidationRuleId, name);
	}

	/**
	 * Returns a range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end) {

		return getPersistence().findByOVRI_N(
			objectValidationRuleId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().findByOVRI_N(
			objectValidationRuleId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByOVRI_N(
			objectValidationRuleId, name, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByOVRI_N_First(
			long objectValidationRuleId, String name,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByOVRI_N_First(
			objectValidationRuleId, name, orderByComparator);
	}

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByOVRI_N_First(
		long objectValidationRuleId, String name,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByOVRI_N_First(
			objectValidationRuleId, name, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByOVRI_N_Last(
			long objectValidationRuleId, String name,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByOVRI_N_Last(
			objectValidationRuleId, name, orderByComparator);
	}

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByOVRI_N_Last(
		long objectValidationRuleId, String name,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().fetchByOVRI_N_Last(
			objectValidationRuleId, name, orderByComparator);
	}

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting[] findByOVRI_N_PrevAndNext(
			long objectValidationRuleSettingId, long objectValidationRuleId,
			String name,
			OrderByComparator<ObjectValidationRuleSetting> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByOVRI_N_PrevAndNext(
			objectValidationRuleSettingId, objectValidationRuleId, name,
			orderByComparator);
	}

	/**
	 * Removes all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 */
	public static void removeByOVRI_N(
		long objectValidationRuleId, String name) {

		getPersistence().removeByOVRI_N(objectValidationRuleId, name);
	}

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @return the number of matching object validation rule settings
	 */
	public static int countByOVRI_N(long objectValidationRuleId, String name) {
		return getPersistence().countByOVRI_N(objectValidationRuleId, name);
	}

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or throws a <code>NoSuchObjectValidationRuleSettingException</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting findByOVRI_N_V(
			long objectValidationRuleId, String name, String value)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByOVRI_N_V(
			objectValidationRuleId, name, value);
	}

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByOVRI_N_V(
		long objectValidationRuleId, String name, String value) {

		return getPersistence().fetchByOVRI_N_V(
			objectValidationRuleId, name, value);
	}

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public static ObjectValidationRuleSetting fetchByOVRI_N_V(
		long objectValidationRuleId, String name, String value,
		boolean useFinderCache) {

		return getPersistence().fetchByOVRI_N_V(
			objectValidationRuleId, name, value, useFinderCache);
	}

	/**
	 * Removes the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the object validation rule setting that was removed
	 */
	public static ObjectValidationRuleSetting removeByOVRI_N_V(
			long objectValidationRuleId, String name, String value)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().removeByOVRI_N_V(
			objectValidationRuleId, name, value);
	}

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63; and name = &#63; and value = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the number of matching object validation rule settings
	 */
	public static int countByOVRI_N_V(
		long objectValidationRuleId, String name, String value) {

		return getPersistence().countByOVRI_N_V(
			objectValidationRuleId, name, value);
	}

	/**
	 * Caches the object validation rule setting in the entity cache if it is enabled.
	 *
	 * @param objectValidationRuleSetting the object validation rule setting
	 */
	public static void cacheResult(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		getPersistence().cacheResult(objectValidationRuleSetting);
	}

	/**
	 * Caches the object validation rule settings in the entity cache if it is enabled.
	 *
	 * @param objectValidationRuleSettings the object validation rule settings
	 */
	public static void cacheResult(
		List<ObjectValidationRuleSetting> objectValidationRuleSettings) {

		getPersistence().cacheResult(objectValidationRuleSettings);
	}

	/**
	 * Creates a new object validation rule setting with the primary key. Does not add the object validation rule setting to the database.
	 *
	 * @param objectValidationRuleSettingId the primary key for the new object validation rule setting
	 * @return the new object validation rule setting
	 */
	public static ObjectValidationRuleSetting create(
		long objectValidationRuleSettingId) {

		return getPersistence().create(objectValidationRuleSettingId);
	}

	/**
	 * Removes the object validation rule setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting that was removed
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting remove(
			long objectValidationRuleSettingId)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().remove(objectValidationRuleSettingId);
	}

	public static ObjectValidationRuleSetting updateImpl(
		ObjectValidationRuleSetting objectValidationRuleSetting) {

		return getPersistence().updateImpl(objectValidationRuleSetting);
	}

	/**
	 * Returns the object validation rule setting with the primary key or throws a <code>NoSuchObjectValidationRuleSettingException</code> if it could not be found.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting findByPrimaryKey(
			long objectValidationRuleSettingId)
		throws com.liferay.object.exception.
			NoSuchObjectValidationRuleSettingException {

		return getPersistence().findByPrimaryKey(objectValidationRuleSettingId);
	}

	/**
	 * Returns the object validation rule setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting, or <code>null</code> if a object validation rule setting with the primary key could not be found
	 */
	public static ObjectValidationRuleSetting fetchByPrimaryKey(
		long objectValidationRuleSettingId) {

		return getPersistence().fetchByPrimaryKey(
			objectValidationRuleSettingId);
	}

	/**
	 * Returns all the object validation rule settings.
	 *
	 * @return the object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object validation rule settings
	 */
	public static List<ObjectValidationRuleSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object validation rule settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object validation rule settings.
	 *
	 * @return the number of object validation rule settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectValidationRuleSettingPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ObjectValidationRuleSettingPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ObjectValidationRuleSettingPersistence _persistence;

}