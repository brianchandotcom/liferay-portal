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

package com.liferay.portal.workflow.kaleo.designer.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the kaleo draft definition service. This utility wraps {@link com.liferay.portal.workflow.kaleo.designer.service.persistence.impl.KaleoDraftDefinitionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Lundgren
 * @see KaleoDraftDefinitionPersistence
 * @see com.liferay.portal.workflow.kaleo.designer.service.persistence.impl.KaleoDraftDefinitionPersistenceImpl
 * @generated
 */
@ProviderType
public class KaleoDraftDefinitionUtil {
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
	public static void clearCache(KaleoDraftDefinition kaleoDraftDefinition) {
		getPersistence().clearCache(kaleoDraftDefinition);
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
	public static List<KaleoDraftDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoDraftDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoDraftDefinition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoDraftDefinition update(
		KaleoDraftDefinition kaleoDraftDefinition) {
		return getPersistence().update(kaleoDraftDefinition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoDraftDefinition update(
		KaleoDraftDefinition kaleoDraftDefinition, ServiceContext serviceContext) {
		return getPersistence().update(kaleoDraftDefinition, serviceContext);
	}

	/**
	* Returns all the kaleo draft definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the kaleo draft definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition findByCompanyId_First(long companyId,
		OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition fetchByCompanyId_First(long companyId,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition findByCompanyId_Last(long companyId,
		OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition fetchByCompanyId_Last(long companyId,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the kaleo draft definitions before and after the current kaleo draft definition in the ordered set where companyId = &#63;.
	*
	* @param kaleoDraftDefinitionId the primary key of the current kaleo draft definition
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public static KaleoDraftDefinition[] findByCompanyId_PrevAndNext(
		long kaleoDraftDefinitionId, long companyId,
		OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(kaleoDraftDefinitionId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the kaleo draft definitions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of kaleo draft definitions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching kaleo draft definitions
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @return the matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version) {
		return getPersistence().findByC_N_V(companyId, name, version);
	}

	/**
	* Returns a range of all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version, int start, int end) {
		return getPersistence().findByC_N_V(companyId, name, version, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version, int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .findByC_N_V(companyId, name, version, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findByC_N_V(long companyId,
		java.lang.String name, int version, int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_N_V(companyId, name, version, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition findByC_N_V_First(long companyId,
		java.lang.String name, int version,
		OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByC_N_V_First(companyId, name, version,
			orderByComparator);
	}

	/**
	* Returns the first kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition fetchByC_N_V_First(long companyId,
		java.lang.String name, int version,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByC_N_V_First(companyId, name, version,
			orderByComparator);
	}

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition findByC_N_V_Last(long companyId,
		java.lang.String name, int version,
		OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByC_N_V_Last(companyId, name, version, orderByComparator);
	}

	/**
	* Returns the last kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition fetchByC_N_V_Last(long companyId,
		java.lang.String name, int version,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence()
				   .fetchByC_N_V_Last(companyId, name, version,
			orderByComparator);
	}

	/**
	* Returns the kaleo draft definitions before and after the current kaleo draft definition in the ordered set where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param kaleoDraftDefinitionId the primary key of the current kaleo draft definition
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public static KaleoDraftDefinition[] findByC_N_V_PrevAndNext(
		long kaleoDraftDefinitionId, long companyId, java.lang.String name,
		int version, OrderByComparator<KaleoDraftDefinition> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByC_N_V_PrevAndNext(kaleoDraftDefinitionId, companyId,
			name, version, orderByComparator);
	}

	/**
	* Removes all the kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	*/
	public static void removeByC_N_V(long companyId, java.lang.String name,
		int version) {
		getPersistence().removeByC_N_V(companyId, name, version);
	}

	/**
	* Returns the number of kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @return the number of matching kaleo draft definitions
	*/
	public static int countByC_N_V(long companyId, java.lang.String name,
		int version) {
		return getPersistence().countByC_N_V(companyId, name, version);
	}

	/**
	* Returns the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; or throws a {@link NoSuchKaleoDraftDefinitionException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the matching kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition findByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .findByC_N_V_D(companyId, name, version, draftVersion);
	}

	/**
	* Returns the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition fetchByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion) {
		return getPersistence()
				   .fetchByC_N_V_D(companyId, name, version, draftVersion);
	}

	/**
	* Returns the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching kaleo draft definition, or <code>null</code> if a matching kaleo draft definition could not be found
	*/
	public static KaleoDraftDefinition fetchByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_N_V_D(companyId, name, version, draftVersion,
			retrieveFromCache);
	}

	/**
	* Removes the kaleo draft definition where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the kaleo draft definition that was removed
	*/
	public static KaleoDraftDefinition removeByC_N_V_D(long companyId,
		java.lang.String name, int version, int draftVersion)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence()
				   .removeByC_N_V_D(companyId, name, version, draftVersion);
	}

	/**
	* Returns the number of kaleo draft definitions where companyId = &#63; and name = &#63; and version = &#63; and draftVersion = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @param version the version
	* @param draftVersion the draft version
	* @return the number of matching kaleo draft definitions
	*/
	public static int countByC_N_V_D(long companyId, java.lang.String name,
		int version, int draftVersion) {
		return getPersistence()
				   .countByC_N_V_D(companyId, name, version, draftVersion);
	}

	/**
	* Caches the kaleo draft definition in the entity cache if it is enabled.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	*/
	public static void cacheResult(KaleoDraftDefinition kaleoDraftDefinition) {
		getPersistence().cacheResult(kaleoDraftDefinition);
	}

	/**
	* Caches the kaleo draft definitions in the entity cache if it is enabled.
	*
	* @param kaleoDraftDefinitions the kaleo draft definitions
	*/
	public static void cacheResult(
		List<KaleoDraftDefinition> kaleoDraftDefinitions) {
		getPersistence().cacheResult(kaleoDraftDefinitions);
	}

	/**
	* Creates a new kaleo draft definition with the primary key. Does not add the kaleo draft definition to the database.
	*
	* @param kaleoDraftDefinitionId the primary key for the new kaleo draft definition
	* @return the new kaleo draft definition
	*/
	public static KaleoDraftDefinition create(long kaleoDraftDefinitionId) {
		return getPersistence().create(kaleoDraftDefinitionId);
	}

	/**
	* Removes the kaleo draft definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition that was removed
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public static KaleoDraftDefinition remove(long kaleoDraftDefinitionId)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence().remove(kaleoDraftDefinitionId);
	}

	public static KaleoDraftDefinition updateImpl(
		KaleoDraftDefinition kaleoDraftDefinition) {
		return getPersistence().updateImpl(kaleoDraftDefinition);
	}

	/**
	* Returns the kaleo draft definition with the primary key or throws a {@link NoSuchKaleoDraftDefinitionException} if it could not be found.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition
	* @throws NoSuchKaleoDraftDefinitionException if a kaleo draft definition with the primary key could not be found
	*/
	public static KaleoDraftDefinition findByPrimaryKey(
		long kaleoDraftDefinitionId)
		throws com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException {
		return getPersistence().findByPrimaryKey(kaleoDraftDefinitionId);
	}

	/**
	* Returns the kaleo draft definition with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition, or <code>null</code> if a kaleo draft definition with the primary key could not be found
	*/
	public static KaleoDraftDefinition fetchByPrimaryKey(
		long kaleoDraftDefinitionId) {
		return getPersistence().fetchByPrimaryKey(kaleoDraftDefinitionId);
	}

	public static java.util.Map<java.io.Serializable, KaleoDraftDefinition> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the kaleo draft definitions.
	*
	* @return the kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findAll(int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of kaleo draft definitions
	*/
	public static List<KaleoDraftDefinition> findAll(int start, int end,
		OrderByComparator<KaleoDraftDefinition> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the kaleo draft definitions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of kaleo draft definitions.
	*
	* @return the number of kaleo draft definitions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoDraftDefinitionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoDraftDefinitionPersistence, KaleoDraftDefinitionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(KaleoDraftDefinitionPersistence.class);
}