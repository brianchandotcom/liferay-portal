/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the kaleo node setting service. This utility wraps <code>com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoNodeSettingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSettingPersistence
 * @generated
 */
public class KaleoNodeSettingUtil {

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
	public static void clearCache(KaleoNodeSetting kaleoNodeSetting) {
		getPersistence().clearCache(kaleoNodeSetting);
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
	public static Map<Serializable, KaleoNodeSetting> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoNodeSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoNodeSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoNodeSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoNodeSetting> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoNodeSetting update(KaleoNodeSetting kaleoNodeSetting) {
		return getPersistence().update(kaleoNodeSetting);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoNodeSetting update(
		KaleoNodeSetting kaleoNodeSetting, ServiceContext serviceContext) {

		return getPersistence().update(kaleoNodeSetting, serviceContext);
	}

	/**
	 * Returns all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the matching kaleo node settings
	 */
	public static List<KaleoNodeSetting> findByKaleoNodeId(long kaleoNodeId) {
		return getPersistence().findByKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Returns a range of all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @return the range of matching kaleo node settings
	 */
	public static List<KaleoNodeSetting> findByKaleoNodeId(
		long kaleoNodeId, int start, int end) {

		return getPersistence().findByKaleoNodeId(kaleoNodeId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo node settings
	 */
	public static List<KaleoNodeSetting> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		OrderByComparator<KaleoNodeSetting> orderByComparator) {

		return getPersistence().findByKaleoNodeId(
			kaleoNodeId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo node settings
	 */
	public static List<KaleoNodeSetting> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		OrderByComparator<KaleoNodeSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByKaleoNodeId(
			kaleoNodeId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node setting
	 * @throws NoSuchNodeSettingException if a matching kaleo node setting could not be found
	 */
	public static KaleoNodeSetting findByKaleoNodeId_First(
			long kaleoNodeId,
			OrderByComparator<KaleoNodeSetting> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchNodeSettingException {

		return getPersistence().findByKaleoNodeId_First(
			kaleoNodeId, orderByComparator);
	}

	/**
	 * Returns the first kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public static KaleoNodeSetting fetchByKaleoNodeId_First(
		long kaleoNodeId,
		OrderByComparator<KaleoNodeSetting> orderByComparator) {

		return getPersistence().fetchByKaleoNodeId_First(
			kaleoNodeId, orderByComparator);
	}

	/**
	 * Removes all the kaleo node settings where kaleoNodeId = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 */
	public static void removeByKaleoNodeId(long kaleoNodeId) {
		getPersistence().removeByKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Returns the number of kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the number of matching kaleo node settings
	 */
	public static int countByKaleoNodeId(long kaleoNodeId) {
		return getPersistence().countByKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Returns the kaleo node setting where kaleoNodeId = &#63; and name = &#63; or throws a <code>NoSuchNodeSettingException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the matching kaleo node setting
	 * @throws NoSuchNodeSettingException if a matching kaleo node setting could not be found
	 */
	public static KaleoNodeSetting findByKNI_N(long kaleoNodeId, String name)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchNodeSettingException {

		return getPersistence().findByKNI_N(kaleoNodeId, name);
	}

	/**
	 * Returns the kaleo node setting where kaleoNodeId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public static KaleoNodeSetting fetchByKNI_N(long kaleoNodeId, String name) {
		return getPersistence().fetchByKNI_N(kaleoNodeId, name);
	}

	/**
	 * Returns the kaleo node setting where kaleoNodeId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public static KaleoNodeSetting fetchByKNI_N(
		long kaleoNodeId, String name, boolean useFinderCache) {

		return getPersistence().fetchByKNI_N(kaleoNodeId, name, useFinderCache);
	}

	/**
	 * Removes the kaleo node setting where kaleoNodeId = &#63; and name = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the kaleo node setting that was removed
	 */
	public static KaleoNodeSetting removeByKNI_N(long kaleoNodeId, String name)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchNodeSettingException {

		return getPersistence().removeByKNI_N(kaleoNodeId, name);
	}

	/**
	 * Returns the number of kaleo node settings where kaleoNodeId = &#63; and name = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the number of matching kaleo node settings
	 */
	public static int countByKNI_N(long kaleoNodeId, String name) {
		return getPersistence().countByKNI_N(kaleoNodeId, name);
	}

	/**
	 * Caches the kaleo node setting in the entity cache if it is enabled.
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 */
	public static void cacheResult(KaleoNodeSetting kaleoNodeSetting) {
		getPersistence().cacheResult(kaleoNodeSetting);
	}

	/**
	 * Caches the kaleo node settings in the entity cache if it is enabled.
	 *
	 * @param kaleoNodeSettings the kaleo node settings
	 */
	public static void cacheResult(List<KaleoNodeSetting> kaleoNodeSettings) {
		getPersistence().cacheResult(kaleoNodeSettings);
	}

	/**
	 * Creates a new kaleo node setting with the primary key. Does not add the kaleo node setting to the database.
	 *
	 * @param kaleoNodeSettingId the primary key for the new kaleo node setting
	 * @return the new kaleo node setting
	 */
	public static KaleoNodeSetting create(long kaleoNodeSettingId) {
		return getPersistence().create(kaleoNodeSettingId);
	}

	/**
	 * Removes the kaleo node setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting that was removed
	 * @throws NoSuchNodeSettingException if a kaleo node setting with the primary key could not be found
	 */
	public static KaleoNodeSetting remove(long kaleoNodeSettingId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchNodeSettingException {

		return getPersistence().remove(kaleoNodeSettingId);
	}

	public static KaleoNodeSetting updateImpl(
		KaleoNodeSetting kaleoNodeSetting) {

		return getPersistence().updateImpl(kaleoNodeSetting);
	}

	/**
	 * Returns the kaleo node setting with the primary key or throws a <code>NoSuchNodeSettingException</code> if it could not be found.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting
	 * @throws NoSuchNodeSettingException if a kaleo node setting with the primary key could not be found
	 */
	public static KaleoNodeSetting findByPrimaryKey(long kaleoNodeSettingId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchNodeSettingException {

		return getPersistence().findByPrimaryKey(kaleoNodeSettingId);
	}

	/**
	 * Returns the kaleo node setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting, or <code>null</code> if a kaleo node setting with the primary key could not be found
	 */
	public static KaleoNodeSetting fetchByPrimaryKey(long kaleoNodeSettingId) {
		return getPersistence().fetchByPrimaryKey(kaleoNodeSettingId);
	}

	/**
	 * Returns all the kaleo node settings.
	 *
	 * @return the kaleo node settings
	 */
	public static List<KaleoNodeSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @return the range of kaleo node settings
	 */
	public static List<KaleoNodeSetting> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo node settings
	 */
	public static List<KaleoNodeSetting> findAll(
		int start, int end,
		OrderByComparator<KaleoNodeSetting> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo node settings
	 */
	public static List<KaleoNodeSetting> findAll(
		int start, int end,
		OrderByComparator<KaleoNodeSetting> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the kaleo node settings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of kaleo node settings.
	 *
	 * @return the number of kaleo node settings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoNodeSettingPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(KaleoNodeSettingPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile KaleoNodeSettingPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:463135857