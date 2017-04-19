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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CommerceProductDefinitionLocalization;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce product definition localization service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionLocalizationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionLocalizationPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductDefinitionLocalizationPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionLocalizationUtil {
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
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		getPersistence().clearCache(commerceProductDefinitionLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceProductDefinitionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceProductDefinitionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceProductDefinitionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceProductDefinitionLocalization update(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		return getPersistence().update(commerceProductDefinitionLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceProductDefinitionLocalization update(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceProductDefinitionLocalization, serviceContext);
	}

	/**
	* Returns all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @return the matching commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK) {
		return getPersistence()
				   .findByCommerceProductDefinitionPK(commerceProductDefinitionPK);
	}

	/**
	* Returns a range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @return the range of matching commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end) {
		return getPersistence()
				   .findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK, int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceProductDefinitionPK(commerceProductDefinitionPK,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization findByCommerceProductDefinitionPK_First(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence()
				   .findByCommerceProductDefinitionPK_First(commerceProductDefinitionPK,
			orderByComparator);
	}

	/**
	* Returns the first commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization fetchByCommerceProductDefinitionPK_First(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceProductDefinitionPK_First(commerceProductDefinitionPK,
			orderByComparator);
	}

	/**
	* Returns the last commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization findByCommerceProductDefinitionPK_Last(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence()
				   .findByCommerceProductDefinitionPK_Last(commerceProductDefinitionPK,
			orderByComparator);
	}

	/**
	* Returns the last commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization fetchByCommerceProductDefinitionPK_Last(
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceProductDefinitionPK_Last(commerceProductDefinitionPK,
			orderByComparator);
	}

	/**
	* Returns the commerce product definition localizations before and after the current commerce product definition localization in the ordered set where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the current commerce product definition localization
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	*/
	public static CommerceProductDefinitionLocalization[] findByCommerceProductDefinitionPK_PrevAndNext(
		long commerceProductDefinitionLocalizationId,
		long commerceProductDefinitionPK,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence()
				   .findByCommerceProductDefinitionPK_PrevAndNext(commerceProductDefinitionLocalizationId,
			commerceProductDefinitionPK, orderByComparator);
	}

	/**
	* Removes all the commerce product definition localizations where commerceProductDefinitionPK = &#63; from the database.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	*/
	public static void removeByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK) {
		getPersistence()
			.removeByCommerceProductDefinitionPK(commerceProductDefinitionPK);
	}

	/**
	* Returns the number of commerce product definition localizations where commerceProductDefinitionPK = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @return the number of matching commerce product definition localizations
	*/
	public static int countByCommerceProductDefinitionPK(
		long commerceProductDefinitionPK) {
		return getPersistence()
				   .countByCommerceProductDefinitionPK(commerceProductDefinitionPK);
	}

	/**
	* Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or throws a {@link NoSuchProductDefinitionLocalizationException} if it could not be found.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the matching commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization findByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence()
				   .findByCPD_L(commerceProductDefinitionPK, languageId);
	}

	/**
	* Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization fetchByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId) {
		return getPersistence()
				   .fetchByCPD_L(commerceProductDefinitionPK, languageId);
	}

	/**
	* Returns the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce product definition localization, or <code>null</code> if a matching commerce product definition localization could not be found
	*/
	public static CommerceProductDefinitionLocalization fetchByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPD_L(commerceProductDefinitionPK, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the commerce product definition localization where commerceProductDefinitionPK = &#63; and languageId = &#63; from the database.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the commerce product definition localization that was removed
	*/
	public static CommerceProductDefinitionLocalization removeByCPD_L(
		long commerceProductDefinitionPK, java.lang.String languageId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence()
				   .removeByCPD_L(commerceProductDefinitionPK, languageId);
	}

	/**
	* Returns the number of commerce product definition localizations where commerceProductDefinitionPK = &#63; and languageId = &#63;.
	*
	* @param commerceProductDefinitionPK the commerce product definition pk
	* @param languageId the language ID
	* @return the number of matching commerce product definition localizations
	*/
	public static int countByCPD_L(long commerceProductDefinitionPK,
		java.lang.String languageId) {
		return getPersistence()
				   .countByCPD_L(commerceProductDefinitionPK, languageId);
	}

	/**
	* Caches the commerce product definition localization in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionLocalization the commerce product definition localization
	*/
	public static void cacheResult(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		getPersistence().cacheResult(commerceProductDefinitionLocalization);
	}

	/**
	* Caches the commerce product definition localizations in the entity cache if it is enabled.
	*
	* @param commerceProductDefinitionLocalizations the commerce product definition localizations
	*/
	public static void cacheResult(
		List<CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations) {
		getPersistence().cacheResult(commerceProductDefinitionLocalizations);
	}

	/**
	* Creates a new commerce product definition localization with the primary key. Does not add the commerce product definition localization to the database.
	*
	* @param commerceProductDefinitionLocalizationId the primary key for the new commerce product definition localization
	* @return the new commerce product definition localization
	*/
	public static CommerceProductDefinitionLocalization create(
		long commerceProductDefinitionLocalizationId) {
		return getPersistence().create(commerceProductDefinitionLocalizationId);
	}

	/**
	* Removes the commerce product definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	* @return the commerce product definition localization that was removed
	* @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	*/
	public static CommerceProductDefinitionLocalization remove(
		long commerceProductDefinitionLocalizationId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence().remove(commerceProductDefinitionLocalizationId);
	}

	public static CommerceProductDefinitionLocalization updateImpl(
		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization) {
		return getPersistence().updateImpl(commerceProductDefinitionLocalization);
	}

	/**
	* Returns the commerce product definition localization with the primary key or throws a {@link NoSuchProductDefinitionLocalizationException} if it could not be found.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	* @return the commerce product definition localization
	* @throws NoSuchProductDefinitionLocalizationException if a commerce product definition localization with the primary key could not be found
	*/
	public static CommerceProductDefinitionLocalization findByPrimaryKey(
		long commerceProductDefinitionLocalizationId)
		throws com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException {
		return getPersistence()
				   .findByPrimaryKey(commerceProductDefinitionLocalizationId);
	}

	/**
	* Returns the commerce product definition localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductDefinitionLocalizationId the primary key of the commerce product definition localization
	* @return the commerce product definition localization, or <code>null</code> if a commerce product definition localization with the primary key could not be found
	*/
	public static CommerceProductDefinitionLocalization fetchByPrimaryKey(
		long commerceProductDefinitionLocalizationId) {
		return getPersistence()
				   .fetchByPrimaryKey(commerceProductDefinitionLocalizationId);
	}

	public static java.util.Map<java.io.Serializable, CommerceProductDefinitionLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce product definition localizations.
	*
	* @return the commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce product definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @return the range of commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce product definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findAll(
		int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce product definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition localizations
	* @param end the upper bound of the range of commerce product definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product definition localizations
	*/
	public static List<CommerceProductDefinitionLocalization> findAll(
		int start, int end,
		OrderByComparator<CommerceProductDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce product definition localizations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce product definition localizations.
	*
	* @return the number of commerce product definition localizations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceProductDefinitionLocalizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductDefinitionLocalizationPersistence, CommerceProductDefinitionLocalizationPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductDefinitionLocalizationPersistence.class);
}