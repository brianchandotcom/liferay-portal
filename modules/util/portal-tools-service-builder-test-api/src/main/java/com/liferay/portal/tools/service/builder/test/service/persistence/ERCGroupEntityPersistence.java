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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCGroupEntityException;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the erc group entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntityUtil
 * @generated
 */
@ProviderType
public interface ERCGroupEntityPersistence
	extends BasePersistence<ERCGroupEntity> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ERCGroupEntityUtil} to access the erc group entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCGroupEntityException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entity
	 * @throws NoSuchERCGroupEntityException if a matching erc group entity could not be found
	 */
	public ERCGroupEntity findByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntityException;

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	public ERCGroupEntity fetchByG_ERC(
		long groupId, String externalReferenceCode);

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	public ERCGroupEntity fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the erc group entity where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc group entity that was removed
	 */
	public ERCGroupEntity removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws NoSuchERCGroupEntityException;

	/**
	 * Returns the number of erc group entities where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc group entities
	 */
	public int countByG_ERC(long groupId, String externalReferenceCode);

	/**
	 * Caches the erc group entity in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntity the erc group entity
	 */
	public void cacheResult(ERCGroupEntity ercGroupEntity);

	/**
	 * Caches the erc group entities in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntities the erc group entities
	 */
	public void cacheResult(java.util.List<ERCGroupEntity> ercGroupEntities);

	/**
	 * Creates a new erc group entity with the primary key. Does not add the erc group entity to the database.
	 *
	 * @param ercGroupEntityId the primary key for the new erc group entity
	 * @return the new erc group entity
	 */
	public ERCGroupEntity create(long ercGroupEntityId);

	/**
	 * Removes the erc group entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity that was removed
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	public ERCGroupEntity remove(long ercGroupEntityId)
		throws NoSuchERCGroupEntityException;

	public ERCGroupEntity updateImpl(ERCGroupEntity ercGroupEntity);

	/**
	 * Returns the erc group entity with the primary key or throws a <code>NoSuchERCGroupEntityException</code> if it could not be found.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	public ERCGroupEntity findByPrimaryKey(long ercGroupEntityId)
		throws NoSuchERCGroupEntityException;

	/**
	 * Returns the erc group entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity, or <code>null</code> if a erc group entity with the primary key could not be found
	 */
	public ERCGroupEntity fetchByPrimaryKey(long ercGroupEntityId);

	/**
	 * Returns all the erc group entities.
	 *
	 * @return the erc group entities
	 */
	public java.util.List<ERCGroupEntity> findAll();

	/**
	 * Returns a range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @return the range of erc group entities
	 */
	public java.util.List<ERCGroupEntity> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc group entities
	 */
	public java.util.List<ERCGroupEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCGroupEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc group entities
	 */
	public java.util.List<ERCGroupEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ERCGroupEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the erc group entities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of erc group entities.
	 *
	 * @return the number of erc group entities
	 */
	public int countAll();

}