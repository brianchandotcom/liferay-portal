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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLazyBlobEntityException;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the lazy blob entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntityUtil
 * @generated
 */
@ProviderType
public interface LazyBlobEntityPersistence
	extends BasePersistence<LazyBlobEntity> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LazyBlobEntityUtil} to access the lazy blob entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the lazy blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findByUuid(String uuid);

	/**
	 * Returns a range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of matching lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
				orderByComparator)
		throws NoSuchLazyBlobEntityException;

	/**
	 * Returns the first lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
			orderByComparator);

	/**
	 * Returns the last lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
				orderByComparator)
		throws NoSuchLazyBlobEntityException;

	/**
	 * Returns the last lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
			orderByComparator);

	/**
	 * Returns the lazy blob entities before and after the current lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param lazyBlobEntityId the primary key of the current lazy blob entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	public LazyBlobEntity[] findByUuid_PrevAndNext(
			long lazyBlobEntityId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
				orderByComparator)
		throws NoSuchLazyBlobEntityException;

	/**
	 * Removes all the lazy blob entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of lazy blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lazy blob entities
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLazyBlobEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity findByUUID_G(String uuid, long groupId)
		throws NoSuchLazyBlobEntityException;

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public LazyBlobEntity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the lazy blob entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the lazy blob entity that was removed
	 */
	public LazyBlobEntity removeByUUID_G(String uuid, long groupId)
		throws NoSuchLazyBlobEntityException;

	/**
	 * Returns the number of lazy blob entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lazy blob entities
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Caches the lazy blob entity in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 */
	public void cacheResult(LazyBlobEntity lazyBlobEntity);

	/**
	 * Caches the lazy blob entities in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntities the lazy blob entities
	 */
	public void cacheResult(java.util.List<LazyBlobEntity> lazyBlobEntities);

	/**
	 * Creates a new lazy blob entity with the primary key. Does not add the lazy blob entity to the database.
	 *
	 * @param lazyBlobEntityId the primary key for the new lazy blob entity
	 * @return the new lazy blob entity
	 */
	public LazyBlobEntity create(long lazyBlobEntityId);

	/**
	 * Removes the lazy blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity that was removed
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	public LazyBlobEntity remove(long lazyBlobEntityId)
		throws NoSuchLazyBlobEntityException;

	public LazyBlobEntity updateImpl(LazyBlobEntity lazyBlobEntity);

	/**
	 * Returns the lazy blob entity with the primary key or throws a <code>NoSuchLazyBlobEntityException</code> if it could not be found.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	public LazyBlobEntity findByPrimaryKey(long lazyBlobEntityId)
		throws NoSuchLazyBlobEntityException;

	/**
	 * Returns the lazy blob entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity, or <code>null</code> if a lazy blob entity with the primary key could not be found
	 */
	public LazyBlobEntity fetchByPrimaryKey(long lazyBlobEntityId);

	/**
	 * Returns all the lazy blob entities.
	 *
	 * @return the lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findAll();

	/**
	 * Returns a range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
			orderByComparator);

	/**
	 * Returns an ordered range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lazy blob entities
	 */
	public java.util.List<LazyBlobEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LazyBlobEntity>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lazy blob entities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of lazy blob entities.
	 *
	 * @return the number of lazy blob entities
	 */
	public int countAll();

}