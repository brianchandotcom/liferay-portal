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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchEagerBlobEntityException;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the eager blob entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntityUtil
 * @generated
 */
@ProviderType
public interface EagerBlobEntityPersistence
	extends BasePersistence<EagerBlobEntity> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link EagerBlobEntityUtil} to access the eager blob entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the eager blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findByUuid(String uuid);

	/**
	 * Returns a range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of matching eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
				orderByComparator)
		throws NoSuchEagerBlobEntityException;

	/**
	 * Returns the first eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
			orderByComparator);

	/**
	 * Returns the last eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
				orderByComparator)
		throws NoSuchEagerBlobEntityException;

	/**
	 * Returns the last eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
			orderByComparator);

	/**
	 * Returns the eager blob entities before and after the current eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param eagerBlobEntityId the primary key of the current eager blob entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	public EagerBlobEntity[] findByUuid_PrevAndNext(
			long eagerBlobEntityId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
				orderByComparator)
		throws NoSuchEagerBlobEntityException;

	/**
	 * Removes all the eager blob entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of eager blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching eager blob entities
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEagerBlobEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity findByUUID_G(String uuid, long groupId)
		throws NoSuchEagerBlobEntityException;

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public EagerBlobEntity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the eager blob entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the eager blob entity that was removed
	 */
	public EagerBlobEntity removeByUUID_G(String uuid, long groupId)
		throws NoSuchEagerBlobEntityException;

	/**
	 * Returns the number of eager blob entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching eager blob entities
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Caches the eager blob entity in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 */
	public void cacheResult(EagerBlobEntity eagerBlobEntity);

	/**
	 * Caches the eager blob entities in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntities the eager blob entities
	 */
	public void cacheResult(java.util.List<EagerBlobEntity> eagerBlobEntities);

	/**
	 * Creates a new eager blob entity with the primary key. Does not add the eager blob entity to the database.
	 *
	 * @param eagerBlobEntityId the primary key for the new eager blob entity
	 * @return the new eager blob entity
	 */
	public EagerBlobEntity create(long eagerBlobEntityId);

	/**
	 * Removes the eager blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity that was removed
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	public EagerBlobEntity remove(long eagerBlobEntityId)
		throws NoSuchEagerBlobEntityException;

	public EagerBlobEntity updateImpl(EagerBlobEntity eagerBlobEntity);

	/**
	 * Returns the eager blob entity with the primary key or throws a <code>NoSuchEagerBlobEntityException</code> if it could not be found.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	public EagerBlobEntity findByPrimaryKey(long eagerBlobEntityId)
		throws NoSuchEagerBlobEntityException;

	/**
	 * Returns the eager blob entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity, or <code>null</code> if a eager blob entity with the primary key could not be found
	 */
	public EagerBlobEntity fetchByPrimaryKey(long eagerBlobEntityId);

	/**
	 * Returns all the eager blob entities.
	 *
	 * @return the eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findAll();

	/**
	 * Returns a range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of eager blob entities
	 */
	public java.util.List<EagerBlobEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<EagerBlobEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the eager blob entities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of eager blob entities.
	 *
	 * @return the number of eager blob entities
	 */
	public int countAll();

}