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

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCCompanyEntityException;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the erc company entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntityUtil
 * @generated
 */
@ProviderType
public interface ERCCompanyEntityPersistence
	extends BasePersistence<ERCCompanyEntity> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ERCCompanyEntityUtil} to access the erc company entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCCompanyEntityException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entity
	 * @throws NoSuchERCCompanyEntityException if a matching erc company entity could not be found
	 */
	public ERCCompanyEntity findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchERCCompanyEntityException;

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	public ERCCompanyEntity fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the erc company entity where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	public ERCCompanyEntity fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the erc company entity where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc company entity that was removed
	 */
	public ERCCompanyEntity removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchERCCompanyEntityException;

	/**
	 * Returns the number of erc company entities where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc company entities
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the erc company entity in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntity the erc company entity
	 */
	public void cacheResult(ERCCompanyEntity ercCompanyEntity);

	/**
	 * Caches the erc company entities in the entity cache if it is enabled.
	 *
	 * @param ercCompanyEntities the erc company entities
	 */
	public void cacheResult(
		java.util.List<ERCCompanyEntity> ercCompanyEntities);

	/**
	 * Creates a new erc company entity with the primary key. Does not add the erc company entity to the database.
	 *
	 * @param ercCompanyEntityId the primary key for the new erc company entity
	 * @return the new erc company entity
	 */
	public ERCCompanyEntity create(long ercCompanyEntityId);

	/**
	 * Removes the erc company entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity that was removed
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	public ERCCompanyEntity remove(long ercCompanyEntityId)
		throws NoSuchERCCompanyEntityException;

	public ERCCompanyEntity updateImpl(ERCCompanyEntity ercCompanyEntity);

	/**
	 * Returns the erc company entity with the primary key or throws a <code>NoSuchERCCompanyEntityException</code> if it could not be found.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity
	 * @throws NoSuchERCCompanyEntityException if a erc company entity with the primary key could not be found
	 */
	public ERCCompanyEntity findByPrimaryKey(long ercCompanyEntityId)
		throws NoSuchERCCompanyEntityException;

	/**
	 * Returns the erc company entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity, or <code>null</code> if a erc company entity with the primary key could not be found
	 */
	public ERCCompanyEntity fetchByPrimaryKey(long ercCompanyEntityId);

	/**
	 * Returns all the erc company entities.
	 *
	 * @return the erc company entities
	 */
	public java.util.List<ERCCompanyEntity> findAll();

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
	public java.util.List<ERCCompanyEntity> findAll(int start, int end);

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
	public java.util.List<ERCCompanyEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCCompanyEntity>
			orderByComparator);

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
	public java.util.List<ERCCompanyEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCCompanyEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the erc company entities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of erc company entities.
	 *
	 * @return the number of erc company entities
	 */
	public int countAll();

}