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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLargeAmountColumnEntityException;
import com.liferay.portal.tools.service.builder.test.model.LargeAmountColumnEntity;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the large amount column entity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LargeAmountColumnEntityUtil
 * @generated
 */
@ProviderType
public interface LargeAmountColumnEntityPersistence
	extends BasePersistence<LargeAmountColumnEntity> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LargeAmountColumnEntityUtil} to access the large amount column entity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the large amount column entity in the entity cache if it is enabled.
	 *
	 * @param largeAmountColumnEntity the large amount column entity
	 */
	public void cacheResult(LargeAmountColumnEntity largeAmountColumnEntity);

	/**
	 * Caches the large amount column entities in the entity cache if it is enabled.
	 *
	 * @param largeAmountColumnEntities the large amount column entities
	 */
	public void cacheResult(
		java.util.List<LargeAmountColumnEntity> largeAmountColumnEntities);

	/**
	 * Creates a new large amount column entity with the primary key. Does not add the large amount column entity to the database.
	 *
	 * @param largeAmountColumnEntityId the primary key for the new large amount column entity
	 * @return the new large amount column entity
	 */
	public LargeAmountColumnEntity create(long largeAmountColumnEntityId);

	/**
	 * Removes the large amount column entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity that was removed
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	public LargeAmountColumnEntity remove(long largeAmountColumnEntityId)
		throws NoSuchLargeAmountColumnEntityException;

	public LargeAmountColumnEntity updateImpl(
		LargeAmountColumnEntity largeAmountColumnEntity);

	/**
	 * Returns the large amount column entity with the primary key or throws a <code>NoSuchLargeAmountColumnEntityException</code> if it could not be found.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	public LargeAmountColumnEntity findByPrimaryKey(
			long largeAmountColumnEntityId)
		throws NoSuchLargeAmountColumnEntityException;

	/**
	 * Returns the large amount column entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity, or <code>null</code> if a large amount column entity with the primary key could not be found
	 */
	public LargeAmountColumnEntity fetchByPrimaryKey(
		long largeAmountColumnEntityId);

	/**
	 * Returns all the large amount column entities.
	 *
	 * @return the large amount column entities
	 */
	public java.util.List<LargeAmountColumnEntity> findAll();

	/**
	 * Returns a range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @return the range of large amount column entities
	 */
	public java.util.List<LargeAmountColumnEntity> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of large amount column entities
	 */
	public java.util.List<LargeAmountColumnEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LargeAmountColumnEntity> orderByComparator);

	/**
	 * Returns an ordered range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of large amount column entities
	 */
	public java.util.List<LargeAmountColumnEntity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LargeAmountColumnEntity> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the large amount column entities from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of large amount column entities.
	 *
	 * @return the number of large amount column entities
	 */
	public int countAll();

}