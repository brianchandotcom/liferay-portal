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

package com.liferay.friendly.url.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLMappingException;
import com.liferay.friendly.url.model.FriendlyURLMapping;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the friendly url mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLMappingPersistenceImpl
 * @see FriendlyURLMappingUtil
 * @generated
 */
@ProviderType
public interface FriendlyURLMappingPersistence extends BasePersistence<FriendlyURLMapping> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLMappingUtil} to access the friendly url mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the friendly url mapping in the entity cache if it is enabled.
	*
	* @param friendlyURLMapping the friendly url mapping
	*/
	public void cacheResult(FriendlyURLMapping friendlyURLMapping);

	/**
	* Caches the friendly url mappings in the entity cache if it is enabled.
	*
	* @param friendlyURLMappings the friendly url mappings
	*/
	public void cacheResult(
		java.util.List<FriendlyURLMapping> friendlyURLMappings);

	/**
	* Creates a new friendly url mapping with the primary key. Does not add the friendly url mapping to the database.
	*
	* @param friendlyURLMappingPK the primary key for the new friendly url mapping
	* @return the new friendly url mapping
	*/
	public FriendlyURLMapping create(FriendlyURLMappingPK friendlyURLMappingPK);

	/**
	* Removes the friendly url mapping with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLMappingPK the primary key of the friendly url mapping
	* @return the friendly url mapping that was removed
	* @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	*/
	public FriendlyURLMapping remove(FriendlyURLMappingPK friendlyURLMappingPK)
		throws NoSuchFriendlyURLMappingException;

	public FriendlyURLMapping updateImpl(FriendlyURLMapping friendlyURLMapping);

	/**
	* Returns the friendly url mapping with the primary key or throws a {@link NoSuchFriendlyURLMappingException} if it could not be found.
	*
	* @param friendlyURLMappingPK the primary key of the friendly url mapping
	* @return the friendly url mapping
	* @throws NoSuchFriendlyURLMappingException if a friendly url mapping with the primary key could not be found
	*/
	public FriendlyURLMapping findByPrimaryKey(
		FriendlyURLMappingPK friendlyURLMappingPK)
		throws NoSuchFriendlyURLMappingException;

	/**
	* Returns the friendly url mapping with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLMappingPK the primary key of the friendly url mapping
	* @return the friendly url mapping, or <code>null</code> if a friendly url mapping with the primary key could not be found
	*/
	public FriendlyURLMapping fetchByPrimaryKey(
		FriendlyURLMappingPK friendlyURLMappingPK);

	@Override
	public java.util.Map<java.io.Serializable, FriendlyURLMapping> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the friendly url mappings.
	*
	* @return the friendly url mappings
	*/
	public java.util.List<FriendlyURLMapping> findAll();

	/**
	* Returns a range of all the friendly url mappings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url mappings
	* @param end the upper bound of the range of friendly url mappings (not inclusive)
	* @return the range of friendly url mappings
	*/
	public java.util.List<FriendlyURLMapping> findAll(int start, int end);

	/**
	* Returns an ordered range of all the friendly url mappings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url mappings
	* @param end the upper bound of the range of friendly url mappings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly url mappings
	*/
	public java.util.List<FriendlyURLMapping> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLMapping> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url mappings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLMappingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url mappings
	* @param end the upper bound of the range of friendly url mappings (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly url mappings
	*/
	public java.util.List<FriendlyURLMapping> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLMapping> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the friendly url mappings from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of friendly url mappings.
	*
	* @return the number of friendly url mappings
	*/
	public int countAll();
}