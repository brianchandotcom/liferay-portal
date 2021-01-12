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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the erc company entity service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.ERCCompanyEntityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntityPersistence
 * @generated
 */
public class ERCCompanyEntityUtil {

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
	public static void clearCache(ERCCompanyEntity ercCompanyEntity) {
		getPersistence().clearCache(ercCompanyEntity);
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
	public static Map<Serializable, ERCCompanyEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ERCCompanyEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ERCCompanyEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ERCCompanyEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ERCCompanyEntity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ERCCompanyEntity update(ERCCompanyEntity ercCompanyEntity) {
		return getPersistence().update(ercCompanyEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ERCCompanyEntity update(
		ERCCompanyEntity ercCompanyEntity, ServiceContext serviceContext) {

		return getPersistence().update(ercCompanyEntity, serviceContext);
	}

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCCompanyEntityException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entity
	 * @throws NoSuchERCCompanyEntityException if a matching erc company entity could not be found
	 */
	public static ERCCompanyEntity findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntityException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	public static ERCCompanyEntity fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	public static ERCCompanyEntity fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the erc company entity where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc company entity that was removed
	 */
	public static ERCCompanyEntity removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntityException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of erc company entities where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc company entities
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the erc company entity in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntity the erc company entity
	 */
	public static void cacheResult(ERCCompanyEntity ercCompanyEntity) {
		getPersistence().cacheResult(ercCompanyEntity);
	}

	/**
	 * Caches the erc company entities in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntities the erc company entities
	 */
	public static void cacheResult(List<ERCCompanyEntity> ercCompanyEntities) {
		getPersistence().cacheResult(ercCompanyEntities);
	}

	/**
	 * Creates a new erc company entity with the primary key. Does not add the erc company entity to the database.
	 *
	 * @param ercCompanyEntityId the primary key for the new erc company entity
	 * @return the new erc company entity
	 */
	public static ERCCompanyEntity create(long ercCompanyEntityId) {
		return getPersistence().create(ercCompanyEntityId);
	}

	/**
	 * Removes the erc company entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity that was removed
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	public static ERCCompanyEntity remove(long ercCompanyEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntityException {

		return getPersistence().remove(ercCompanyEntityId);
	}

	public static ERCCompanyEntity updateImpl(
		ERCCompanyEntity ercCompanyEntity) {

		return getPersistence().updateImpl(ercCompanyEntity);
	}

	/**
	 * Returns the erc company entity with the primary key or throws a <code>NoSuchERCCompanyEntityException</code> if it could not be found.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	public static ERCCompanyEntity findByPrimaryKey(long ercCompanyEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCCompanyEntityException {

		return getPersistence().findByPrimaryKey(ercCompanyEntityId);
	}

	/**
	 * Returns the erc company entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity, or <code>null</code> if a erc company entity with the primary key could not be found
	 */
	public static ERCCompanyEntity fetchByPrimaryKey(long ercCompanyEntityId) {
		return getPersistence().fetchByPrimaryKey(ercCompanyEntityId);
	}

	/**
	 * Returns all the erc company entities.
	 *
	 * @return the erc company entities
	 */
	public static List<ERCCompanyEntity> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @return the range of erc company entities
	 */
	public static List<ERCCompanyEntity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc company entities
	 */
	public static List<ERCCompanyEntity> findAll(
		int start, int end,
		OrderByComparator<ERCCompanyEntity> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc company entities
	 */
	public static List<ERCCompanyEntity> findAll(
		int start, int end,
		OrderByComparator<ERCCompanyEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the erc company entities from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of erc company entities.
	 *
	 * @return the number of erc company entities
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ERCCompanyEntityPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ERCCompanyEntityPersistence, ERCCompanyEntityPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ERCCompanyEntityPersistence.class);

		ServiceTracker<ERCCompanyEntityPersistence, ERCCompanyEntityPersistence>
			serviceTracker =
				new ServiceTracker
					<ERCCompanyEntityPersistence, ERCCompanyEntityPersistence>(
						bundle.getBundleContext(),
						ERCCompanyEntityPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}