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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the lv entry localization service. This utility wraps {@link com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryLocalizationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryLocalizationPersistenceImpl
 * @generated
 */
@ProviderType
public class LVEntryLocalizationUtil {
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
	public static void clearCache(LVEntryLocalization lvEntryLocalization) {
		getPersistence().clearCache(lvEntryLocalization);
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
	public static List<LVEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LVEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LVEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LVEntryLocalization update(
		LVEntryLocalization lvEntryLocalization) {
		return getPersistence().update(lvEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LVEntryLocalization update(
		LVEntryLocalization lvEntryLocalization, ServiceContext serviceContext) {
		return getPersistence().update(lvEntryLocalization, serviceContext);
	}

	/**
	* Returns all the lv entry localizations where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching lv entry localizations
	*/
	public static List<LVEntryLocalization> findByEntryId(long entryId) {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Returns a range of all the lv entry localizations where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of lv entry localizations
	* @param end the upper bound of the range of lv entry localizations (not inclusive)
	* @return the range of matching lv entry localizations
	*/
	public static List<LVEntryLocalization> findByEntryId(long entryId,
		int start, int end) {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Returns an ordered range of all the lv entry localizations where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of lv entry localizations
	* @param end the upper bound of the range of lv entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lv entry localizations
	*/
	public static List<LVEntryLocalization> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entry localizations where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of lv entry localizations
	* @param end the upper bound of the range of lv entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching lv entry localizations
	*/
	public static List<LVEntryLocalization> findByEntryId(long entryId,
		int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first lv entry localization in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry localization
	* @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization findByEntryId_First(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the first lv entry localization in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization fetchByEntryId_First(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		return getPersistence().fetchByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the last lv entry localization in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry localization
	* @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization findByEntryId_Last(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the last lv entry localization in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization fetchByEntryId_Last(long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		return getPersistence().fetchByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the lv entry localizations before and after the current lv entry localization in the ordered set where entryId = &#63;.
	*
	* @param lvEntryLocalizationId the primary key of the current lv entry localization
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lv entry localization
	* @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	*/
	public static LVEntryLocalization[] findByEntryId_PrevAndNext(
		long lvEntryLocalizationId, long entryId,
		OrderByComparator<LVEntryLocalization> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(lvEntryLocalizationId, entryId,
			orderByComparator);
	}

	/**
	* Removes all the lv entry localizations where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public static void removeByEntryId(long entryId) {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Returns the number of lv entry localizations where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching lv entry localizations
	*/
	public static int countByEntryId(long entryId) {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Returns the lv entry localization where entryId = &#63; and languageId = &#63; or throws a {@link NoSuchLVEntryLocalizationException} if it could not be found.
	*
	* @param entryId the entry ID
	* @param languageId the language ID
	* @return the matching lv entry localization
	* @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization findByEntryId_LanguageId(long entryId,
		String languageId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().findByEntryId_LanguageId(entryId, languageId);
	}

	/**
	* Returns the lv entry localization where entryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId the entry ID
	* @param languageId the language ID
	* @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization fetchByEntryId_LanguageId(long entryId,
		String languageId) {
		return getPersistence().fetchByEntryId_LanguageId(entryId, languageId);
	}

	/**
	* Returns the lv entry localization where entryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId the entry ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization fetchByEntryId_LanguageId(long entryId,
		String languageId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByEntryId_LanguageId(entryId, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the lv entry localization where entryId = &#63; and languageId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param languageId the language ID
	* @return the lv entry localization that was removed
	*/
	public static LVEntryLocalization removeByEntryId_LanguageId(long entryId,
		String languageId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().removeByEntryId_LanguageId(entryId, languageId);
	}

	/**
	* Returns the number of lv entry localizations where entryId = &#63; and languageId = &#63;.
	*
	* @param entryId the entry ID
	* @param languageId the language ID
	* @return the number of matching lv entry localizations
	*/
	public static int countByEntryId_LanguageId(long entryId, String languageId) {
		return getPersistence().countByEntryId_LanguageId(entryId, languageId);
	}

	/**
	* Returns the lv entry localization where headId = &#63; or throws a {@link NoSuchLVEntryLocalizationException} if it could not be found.
	*
	* @param headId the head ID
	* @return the matching lv entry localization
	* @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization findByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().findByHeadId(headId);
	}

	/**
	* Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param headId the head ID
	* @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization fetchByHeadId(long headId) {
		return getPersistence().fetchByHeadId(headId);
	}

	/**
	* Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param headId the head ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	*/
	public static LVEntryLocalization fetchByHeadId(long headId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByHeadId(headId, retrieveFromCache);
	}

	/**
	* Removes the lv entry localization where headId = &#63; from the database.
	*
	* @param headId the head ID
	* @return the lv entry localization that was removed
	*/
	public static LVEntryLocalization removeByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().removeByHeadId(headId);
	}

	/**
	* Returns the number of lv entry localizations where headId = &#63;.
	*
	* @param headId the head ID
	* @return the number of matching lv entry localizations
	*/
	public static int countByHeadId(long headId) {
		return getPersistence().countByHeadId(headId);
	}

	/**
	* Caches the lv entry localization in the entity cache if it is enabled.
	*
	* @param lvEntryLocalization the lv entry localization
	*/
	public static void cacheResult(LVEntryLocalization lvEntryLocalization) {
		getPersistence().cacheResult(lvEntryLocalization);
	}

	/**
	* Caches the lv entry localizations in the entity cache if it is enabled.
	*
	* @param lvEntryLocalizations the lv entry localizations
	*/
	public static void cacheResult(
		List<LVEntryLocalization> lvEntryLocalizations) {
		getPersistence().cacheResult(lvEntryLocalizations);
	}

	/**
	* Creates a new lv entry localization with the primary key. Does not add the lv entry localization to the database.
	*
	* @param lvEntryLocalizationId the primary key for the new lv entry localization
	* @return the new lv entry localization
	*/
	public static LVEntryLocalization create(long lvEntryLocalizationId) {
		return getPersistence().create(lvEntryLocalizationId);
	}

	/**
	* Removes the lv entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param lvEntryLocalizationId the primary key of the lv entry localization
	* @return the lv entry localization that was removed
	* @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	*/
	public static LVEntryLocalization remove(long lvEntryLocalizationId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().remove(lvEntryLocalizationId);
	}

	public static LVEntryLocalization updateImpl(
		LVEntryLocalization lvEntryLocalization) {
		return getPersistence().updateImpl(lvEntryLocalization);
	}

	/**
	* Returns the lv entry localization with the primary key or throws a {@link NoSuchLVEntryLocalizationException} if it could not be found.
	*
	* @param lvEntryLocalizationId the primary key of the lv entry localization
	* @return the lv entry localization
	* @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	*/
	public static LVEntryLocalization findByPrimaryKey(
		long lvEntryLocalizationId)
		throws com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException {
		return getPersistence().findByPrimaryKey(lvEntryLocalizationId);
	}

	/**
	* Returns the lv entry localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param lvEntryLocalizationId the primary key of the lv entry localization
	* @return the lv entry localization, or <code>null</code> if a lv entry localization with the primary key could not be found
	*/
	public static LVEntryLocalization fetchByPrimaryKey(
		long lvEntryLocalizationId) {
		return getPersistence().fetchByPrimaryKey(lvEntryLocalizationId);
	}

	public static java.util.Map<java.io.Serializable, LVEntryLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the lv entry localizations.
	*
	* @return the lv entry localizations
	*/
	public static List<LVEntryLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the lv entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry localizations
	* @param end the upper bound of the range of lv entry localizations (not inclusive)
	* @return the range of lv entry localizations
	*/
	public static List<LVEntryLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the lv entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry localizations
	* @param end the upper bound of the range of lv entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lv entry localizations
	*/
	public static List<LVEntryLocalization> findAll(int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the lv entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of lv entry localizations
	* @param end the upper bound of the range of lv entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of lv entry localizations
	*/
	public static List<LVEntryLocalization> findAll(int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the lv entry localizations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of lv entry localizations.
	*
	* @return the number of lv entry localizations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LVEntryLocalizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LVEntryLocalizationPersistence, LVEntryLocalizationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LVEntryLocalizationPersistence.class);

		ServiceTracker<LVEntryLocalizationPersistence, LVEntryLocalizationPersistence> serviceTracker =
			new ServiceTracker<LVEntryLocalizationPersistence, LVEntryLocalizationPersistence>(bundle.getBundleContext(),
				LVEntryLocalizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}