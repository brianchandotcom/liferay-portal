/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the plugin setting service. This utility wraps {@link PluginSettingPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PluginSettingPersistence
 * @see PluginSettingPersistenceImpl
 * @generated
 */
@ProviderType
public class PluginSettingUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(PluginSetting pluginSetting) {
		getPersistence().clearCache(pluginSetting);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PluginSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PluginSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PluginSetting> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PluginSetting update(PluginSetting pluginSetting) {
		return getPersistence().update(pluginSetting);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PluginSetting update(PluginSetting pluginSetting,
		ServiceContext serviceContext) {
		return getPersistence().update(pluginSetting, serviceContext);
	}

	/**
	* Returns all the plugin settings where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching plugin settings
	*/
	public static java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the plugin settings where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of plugin settings
	* @param end the upper bound of the range of plugin settings (not inclusive)
	* @return the range of matching plugin settings
	*/
	public static java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the plugin settings where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of plugin settings
	* @param end the upper bound of the range of plugin settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching plugin settings
	*/
	public static java.util.List<com.liferay.portal.model.PluginSetting> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first plugin setting in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching plugin setting
	* @throws com.liferay.portal.NoSuchPluginSettingException if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first plugin setting in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last plugin setting in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching plugin setting
	* @throws com.liferay.portal.NoSuchPluginSettingException if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last plugin setting in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the plugin settings before and after the current plugin setting in the ordered set where companyId = &#63;.
	*
	* @param pluginSettingId the primary key of the current plugin setting
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next plugin setting
	* @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	*/
	public static com.liferay.portal.model.PluginSetting[] findByCompanyId_PrevAndNext(
		long pluginSettingId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(pluginSettingId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the plugin settings where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of plugin settings where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching plugin settings
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or throws a {@link com.liferay.portal.NoSuchPluginSettingException} if it could not be found.
	*
	* @param companyId the company ID
	* @param pluginId the plugin ID
	* @param pluginType the plugin type
	* @return the matching plugin setting
	* @throws com.liferay.portal.NoSuchPluginSettingException if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting findByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().findByC_I_T(companyId, pluginId, pluginType);
	}

	/**
	* Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param pluginId the plugin ID
	* @param pluginType the plugin type
	* @return the matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting fetchByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType) {
		return getPersistence().fetchByC_I_T(companyId, pluginId, pluginType);
	}

	/**
	* Returns the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param pluginId the plugin ID
	* @param pluginType the plugin type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching plugin setting, or <code>null</code> if a matching plugin setting could not be found
	*/
	public static com.liferay.portal.model.PluginSetting fetchByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_I_T(companyId, pluginId, pluginType,
			retrieveFromCache);
	}

	/**
	* Removes the plugin setting where companyId = &#63; and pluginId = &#63; and pluginType = &#63; from the database.
	*
	* @param companyId the company ID
	* @param pluginId the plugin ID
	* @param pluginType the plugin type
	* @return the plugin setting that was removed
	*/
	public static com.liferay.portal.model.PluginSetting removeByC_I_T(
		long companyId, java.lang.String pluginId, java.lang.String pluginType)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().removeByC_I_T(companyId, pluginId, pluginType);
	}

	/**
	* Returns the number of plugin settings where companyId = &#63; and pluginId = &#63; and pluginType = &#63;.
	*
	* @param companyId the company ID
	* @param pluginId the plugin ID
	* @param pluginType the plugin type
	* @return the number of matching plugin settings
	*/
	public static int countByC_I_T(long companyId, java.lang.String pluginId,
		java.lang.String pluginType) {
		return getPersistence().countByC_I_T(companyId, pluginId, pluginType);
	}

	/**
	* Caches the plugin setting in the entity cache if it is enabled.
	*
	* @param pluginSetting the plugin setting
	*/
	public static void cacheResult(
		com.liferay.portal.model.PluginSetting pluginSetting) {
		getPersistence().cacheResult(pluginSetting);
	}

	/**
	* Caches the plugin settings in the entity cache if it is enabled.
	*
	* @param pluginSettings the plugin settings
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PluginSetting> pluginSettings) {
		getPersistence().cacheResult(pluginSettings);
	}

	/**
	* Creates a new plugin setting with the primary key. Does not add the plugin setting to the database.
	*
	* @param pluginSettingId the primary key for the new plugin setting
	* @return the new plugin setting
	*/
	public static com.liferay.portal.model.PluginSetting create(
		long pluginSettingId) {
		return getPersistence().create(pluginSettingId);
	}

	/**
	* Removes the plugin setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pluginSettingId the primary key of the plugin setting
	* @return the plugin setting that was removed
	* @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	*/
	public static com.liferay.portal.model.PluginSetting remove(
		long pluginSettingId)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().remove(pluginSettingId);
	}

	public static com.liferay.portal.model.PluginSetting updateImpl(
		com.liferay.portal.model.PluginSetting pluginSetting) {
		return getPersistence().updateImpl(pluginSetting);
	}

	/**
	* Returns the plugin setting with the primary key or throws a {@link com.liferay.portal.NoSuchPluginSettingException} if it could not be found.
	*
	* @param pluginSettingId the primary key of the plugin setting
	* @return the plugin setting
	* @throws com.liferay.portal.NoSuchPluginSettingException if a plugin setting with the primary key could not be found
	*/
	public static com.liferay.portal.model.PluginSetting findByPrimaryKey(
		long pluginSettingId)
		throws com.liferay.portal.NoSuchPluginSettingException {
		return getPersistence().findByPrimaryKey(pluginSettingId);
	}

	/**
	* Returns the plugin setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pluginSettingId the primary key of the plugin setting
	* @return the plugin setting, or <code>null</code> if a plugin setting with the primary key could not be found
	*/
	public static com.liferay.portal.model.PluginSetting fetchByPrimaryKey(
		long pluginSettingId) {
		return getPersistence().fetchByPrimaryKey(pluginSettingId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.PluginSetting> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the plugin settings.
	*
	* @return the plugin settings
	*/
	public static java.util.List<com.liferay.portal.model.PluginSetting> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the plugin settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of plugin settings
	* @param end the upper bound of the range of plugin settings (not inclusive)
	* @return the range of plugin settings
	*/
	public static java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the plugin settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PluginSettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of plugin settings
	* @param end the upper bound of the range of plugin settings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of plugin settings
	*/
	public static java.util.List<com.liferay.portal.model.PluginSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the plugin settings from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of plugin settings.
	*
	* @return the number of plugin settings
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PluginSettingPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PluginSettingPersistence)PortalBeanLocatorUtil.locate(PluginSettingPersistence.class.getName());

			ReferenceRegistry.registerReference(PluginSettingUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(PluginSettingPersistence persistence) {
	}

	private static PluginSettingPersistence _persistence;
}