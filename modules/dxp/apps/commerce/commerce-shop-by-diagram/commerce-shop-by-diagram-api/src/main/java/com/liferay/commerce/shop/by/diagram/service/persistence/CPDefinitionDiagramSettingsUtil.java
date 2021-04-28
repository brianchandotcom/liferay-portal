/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cp definition diagram settings service. This utility wraps <code>com.liferay.commerce.shop.by.diagram.service.persistence.impl.CPDefinitionDiagramSettingsPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsPersistence
 * @generated
 */
public class CPDefinitionDiagramSettingsUtil {

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
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		getPersistence().clearCache(cpDefinitionDiagramSettings);
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
	public static Map<Serializable, CPDefinitionDiagramSettings>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinitionDiagramSettings> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionDiagramSettings> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionDiagramSettings> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionDiagramSettings update(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return getPersistence().update(cpDefinitionDiagramSettings);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionDiagramSettings update(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings,
		ServiceContext serviceContext) {

		return getPersistence().update(
			cpDefinitionDiagramSettings, serviceContext);
	}

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionDiagramSettingsException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings findByCPDefinitionId(
			long CPDefinitionId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings fetchByCPDefinitionId(
		long CPDefinitionId) {

		return getPersistence().fetchByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache) {

		return getPersistence().fetchByCPDefinitionId(
			CPDefinitionId, useFinderCache);
	}

	/**
	 * Removes the cp definition diagram settings where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition diagram settings that was removed
	 */
	public static CPDefinitionDiagramSettings removeByCPDefinitionId(
			long CPDefinitionId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns the number of cp definition diagram settingses where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram settingses
	 */
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Returns all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId) {

		return getPersistence().findByAssetCategoryId(assetCategoryId);
	}

	/**
	 * Returns a range of all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @return the range of matching cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end) {

		return getPersistence().findByAssetCategoryId(
			assetCategoryId, start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return getPersistence().findByAssetCategoryId(
			assetCategoryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAssetCategoryId(
			assetCategoryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings findByAssetCategoryId_First(
			long assetCategoryId,
			OrderByComparator<CPDefinitionDiagramSettings> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().findByAssetCategoryId_First(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the first cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings fetchByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return getPersistence().fetchByAssetCategoryId_First(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings findByAssetCategoryId_Last(
			long assetCategoryId,
			OrderByComparator<CPDefinitionDiagramSettings> orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().findByAssetCategoryId_Last(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the last cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public static CPDefinitionDiagramSettings fetchByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return getPersistence().fetchByAssetCategoryId_Last(
			assetCategoryId, orderByComparator);
	}

	/**
	 * Returns the cp definition diagram settingses before and after the current cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the current cp definition diagram settings
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	public static CPDefinitionDiagramSettings[]
			findByAssetCategoryId_PrevAndNext(
				long CPDefinitionDiagramSettingsId, long assetCategoryId,
				OrderByComparator<CPDefinitionDiagramSettings>
					orderByComparator)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().findByAssetCategoryId_PrevAndNext(
			CPDefinitionDiagramSettingsId, assetCategoryId, orderByComparator);
	}

	/**
	 * Removes all the cp definition diagram settingses where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	public static void removeByAssetCategoryId(long assetCategoryId) {
		getPersistence().removeByAssetCategoryId(assetCategoryId);
	}

	/**
	 * Returns the number of cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching cp definition diagram settingses
	 */
	public static int countByAssetCategoryId(long assetCategoryId) {
		return getPersistence().countByAssetCategoryId(assetCategoryId);
	}

	/**
	 * Caches the cp definition diagram settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 */
	public static void cacheResult(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		getPersistence().cacheResult(cpDefinitionDiagramSettings);
	}

	/**
	 * Caches the cp definition diagram settingses in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettingses the cp definition diagram settingses
	 */
	public static void cacheResult(
		List<CPDefinitionDiagramSettings> cpDefinitionDiagramSettingses) {

		getPersistence().cacheResult(cpDefinitionDiagramSettingses);
	}

	/**
	 * Creates a new cp definition diagram settings with the primary key. Does not add the cp definition diagram settings to the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key for the new cp definition diagram settings
	 * @return the new cp definition diagram settings
	 */
	public static CPDefinitionDiagramSettings create(
		long CPDefinitionDiagramSettingsId) {

		return getPersistence().create(CPDefinitionDiagramSettingsId);
	}

	/**
	 * Removes the cp definition diagram settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	public static CPDefinitionDiagramSettings remove(
			long CPDefinitionDiagramSettingsId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().remove(CPDefinitionDiagramSettingsId);
	}

	public static CPDefinitionDiagramSettings updateImpl(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		return getPersistence().updateImpl(cpDefinitionDiagramSettings);
	}

	/**
	 * Returns the cp definition diagram settings with the primary key or throws a <code>NoSuchCPDefinitionDiagramSettingsException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	public static CPDefinitionDiagramSettings findByPrimaryKey(
			long CPDefinitionDiagramSettingsId)
		throws com.liferay.commerce.shop.by.diagram.exception.
			NoSuchCPDefinitionDiagramSettingsException {

		return getPersistence().findByPrimaryKey(CPDefinitionDiagramSettingsId);
	}

	/**
	 * Returns the cp definition diagram settings with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings, or <code>null</code> if a cp definition diagram settings with the primary key could not be found
	 */
	public static CPDefinitionDiagramSettings fetchByPrimaryKey(
		long CPDefinitionDiagramSettingsId) {

		return getPersistence().fetchByPrimaryKey(
			CPDefinitionDiagramSettingsId);
	}

	/**
	 * Returns all the cp definition diagram settingses.
	 *
	 * @return the cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @return the range of cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findAll(
		int start, int end) {

		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram settingses
	 */
	public static List<CPDefinitionDiagramSettings> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cp definition diagram settingses from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cp definition diagram settingses.
	 *
	 * @return the number of cp definition diagram settingses
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDefinitionDiagramSettingsPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CPDefinitionDiagramSettingsPersistence,
		 CPDefinitionDiagramSettingsPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionDiagramSettingsPersistence.class);

		ServiceTracker
			<CPDefinitionDiagramSettingsPersistence,
			 CPDefinitionDiagramSettingsPersistence> serviceTracker =
				new ServiceTracker
					<CPDefinitionDiagramSettingsPersistence,
					 CPDefinitionDiagramSettingsPersistence>(
						 bundle.getBundleContext(),
						 CPDefinitionDiagramSettingsPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}