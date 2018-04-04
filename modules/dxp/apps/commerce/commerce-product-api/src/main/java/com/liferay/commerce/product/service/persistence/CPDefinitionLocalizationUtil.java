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

import com.liferay.commerce.product.model.CPDefinitionLocalization;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition localization service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDefinitionLocalizationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionLocalizationPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionLocalizationPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionLocalizationUtil {
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
		CPDefinitionLocalization cpDefinitionLocalization) {
		getPersistence().clearCache(cpDefinitionLocalization);
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
	public static List<CPDefinitionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionLocalization update(
		CPDefinitionLocalization cpDefinitionLocalization) {
		return getPersistence().update(cpDefinitionLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionLocalization update(
		CPDefinitionLocalization cpDefinitionLocalization,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionLocalization, serviceContext);
	}

	/**
	* Returns all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns a range of all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @return the range of matching cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition localizations where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the cp definition localizations before and after the current cp definition localization in the ordered set where CPDefinitionId = &#63;.
	*
	* @param cpDefinitionLocalizationId the primary key of the current cp definition localization
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	*/
	public static CPDefinitionLocalization[] findByCPDefinitionId_PrevAndNext(
		long cpDefinitionLocalizationId, long CPDefinitionId,
		OrderByComparator<CPDefinitionLocalization> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(cpDefinitionLocalizationId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the cp definition localizations where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition localizations where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition localizations
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; or throws a {@link NoSuchCPDefinitionLocalizationException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the matching cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization findByCPDefinitionId_LanguageId(
		long CPDefinitionId, java.lang.String languageId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence()
				   .findByCPDefinitionId_LanguageId(CPDefinitionId, languageId);
	}

	/**
	* Returns the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization fetchByCPDefinitionId_LanguageId(
		long CPDefinitionId, java.lang.String languageId) {
		return getPersistence()
				   .fetchByCPDefinitionId_LanguageId(CPDefinitionId, languageId);
	}

	/**
	* Returns the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition localization, or <code>null</code> if a matching cp definition localization could not be found
	*/
	public static CPDefinitionLocalization fetchByCPDefinitionId_LanguageId(
		long CPDefinitionId, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByCPDefinitionId_LanguageId(CPDefinitionId,
			languageId, retrieveFromCache);
	}

	/**
	* Removes the cp definition localization where CPDefinitionId = &#63; and languageId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the cp definition localization that was removed
	*/
	public static CPDefinitionLocalization removeByCPDefinitionId_LanguageId(
		long CPDefinitionId, java.lang.String languageId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence()
				   .removeByCPDefinitionId_LanguageId(CPDefinitionId, languageId);
	}

	/**
	* Returns the number of cp definition localizations where CPDefinitionId = &#63; and languageId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param languageId the language ID
	* @return the number of matching cp definition localizations
	*/
	public static int countByCPDefinitionId_LanguageId(long CPDefinitionId,
		java.lang.String languageId) {
		return getPersistence()
				   .countByCPDefinitionId_LanguageId(CPDefinitionId, languageId);
	}

	/**
	* Caches the cp definition localization in the entity cache if it is enabled.
	*
	* @param cpDefinitionLocalization the cp definition localization
	*/
	public static void cacheResult(
		CPDefinitionLocalization cpDefinitionLocalization) {
		getPersistence().cacheResult(cpDefinitionLocalization);
	}

	/**
	* Caches the cp definition localizations in the entity cache if it is enabled.
	*
	* @param cpDefinitionLocalizations the cp definition localizations
	*/
	public static void cacheResult(
		List<CPDefinitionLocalization> cpDefinitionLocalizations) {
		getPersistence().cacheResult(cpDefinitionLocalizations);
	}

	/**
	* Creates a new cp definition localization with the primary key. Does not add the cp definition localization to the database.
	*
	* @param cpDefinitionLocalizationId the primary key for the new cp definition localization
	* @return the new cp definition localization
	*/
	public static CPDefinitionLocalization create(
		long cpDefinitionLocalizationId) {
		return getPersistence().create(cpDefinitionLocalizationId);
	}

	/**
	* Removes the cp definition localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionLocalizationId the primary key of the cp definition localization
	* @return the cp definition localization that was removed
	* @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	*/
	public static CPDefinitionLocalization remove(
		long cpDefinitionLocalizationId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence().remove(cpDefinitionLocalizationId);
	}

	public static CPDefinitionLocalization updateImpl(
		CPDefinitionLocalization cpDefinitionLocalization) {
		return getPersistence().updateImpl(cpDefinitionLocalization);
	}

	/**
	* Returns the cp definition localization with the primary key or throws a {@link NoSuchCPDefinitionLocalizationException} if it could not be found.
	*
	* @param cpDefinitionLocalizationId the primary key of the cp definition localization
	* @return the cp definition localization
	* @throws NoSuchCPDefinitionLocalizationException if a cp definition localization with the primary key could not be found
	*/
	public static CPDefinitionLocalization findByPrimaryKey(
		long cpDefinitionLocalizationId)
		throws com.liferay.commerce.product.exception.NoSuchCPDefinitionLocalizationException {
		return getPersistence().findByPrimaryKey(cpDefinitionLocalizationId);
	}

	/**
	* Returns the cp definition localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param cpDefinitionLocalizationId the primary key of the cp definition localization
	* @return the cp definition localization, or <code>null</code> if a cp definition localization with the primary key could not be found
	*/
	public static CPDefinitionLocalization fetchByPrimaryKey(
		long cpDefinitionLocalizationId) {
		return getPersistence().fetchByPrimaryKey(cpDefinitionLocalizationId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition localizations.
	*
	* @return the cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @return the range of cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findAll(int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition localizations
	* @param end the upper bound of the range of cp definition localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition localizations
	*/
	public static List<CPDefinitionLocalization> findAll(int start, int end,
		OrderByComparator<CPDefinitionLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition localizations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition localizations.
	*
	* @return the number of cp definition localizations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CPDefinitionLocalizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionLocalizationPersistence, CPDefinitionLocalizationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionLocalizationPersistence.class);

		ServiceTracker<CPDefinitionLocalizationPersistence, CPDefinitionLocalizationPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionLocalizationPersistence, CPDefinitionLocalizationPersistence>(bundle.getBundleContext(),
				CPDefinitionLocalizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}