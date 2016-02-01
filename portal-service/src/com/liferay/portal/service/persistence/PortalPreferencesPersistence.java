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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.exception.NoSuchPreferencesException;
import com.liferay.portal.model.PortalPreferences;

/**
 * The persistence interface for the portal preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.PortalPreferencesPersistenceImpl
 * @see PortalPreferencesUtil
 * @generated
 */
@ProviderType
public interface PortalPreferencesPersistence extends BasePersistence<PortalPreferences> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortalPreferencesUtil} to access the portal preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or throws a {@link NoSuchPreferencesException} if it could not be found.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @return the matching portal preferences
	* @throws NoSuchPreferencesException if a matching portal preferences could not be found
	*/
	public PortalPreferences findByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException;

	/**
	* Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	*/
	public PortalPreferences fetchByO_O(long ownerId, int ownerType);

	/**
	* Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	*/
	public PortalPreferences fetchByO_O(long ownerId, int ownerType,
		boolean retrieveFromCache);

	/**
	* Removes the portal preferences where ownerId = &#63; and ownerType = &#63; from the database.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @return the portal preferences that was removed
	*/
	public PortalPreferences removeByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException;

	/**
	* Returns the number of portal preferenceses where ownerId = &#63; and ownerType = &#63;.
	*
	* @param ownerId the owner ID
	* @param ownerType the owner type
	* @return the number of matching portal preferenceses
	*/
	public int countByO_O(long ownerId, int ownerType);

	/**
	* Caches the portal preferences in the entity cache if it is enabled.
	*
	* @param portalPreferences the portal preferences
	*/
	public void cacheResult(PortalPreferences portalPreferences);

	/**
	* Caches the portal preferenceses in the entity cache if it is enabled.
	*
	* @param portalPreferenceses the portal preferenceses
	*/
	public void cacheResult(
		java.util.List<PortalPreferences> portalPreferenceses);

	/**
	* Creates a new portal preferences with the primary key. Does not add the portal preferences to the database.
	*
	* @param portalPreferencesId the primary key for the new portal preferences
	* @return the new portal preferences
	*/
	public PortalPreferences create(long portalPreferencesId);

	/**
	* Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portalPreferencesId the primary key of the portal preferences
	* @return the portal preferences that was removed
	* @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	*/
	public PortalPreferences remove(long portalPreferencesId)
		throws NoSuchPreferencesException;

	public PortalPreferences updateImpl(PortalPreferences portalPreferences);

	/**
	* Returns the portal preferences with the primary key or throws a {@link NoSuchPreferencesException} if it could not be found.
	*
	* @param portalPreferencesId the primary key of the portal preferences
	* @return the portal preferences
	* @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	*/
	public PortalPreferences findByPrimaryKey(long portalPreferencesId)
		throws NoSuchPreferencesException;

	/**
	* Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portalPreferencesId the primary key of the portal preferences
	* @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	*/
	public PortalPreferences fetchByPrimaryKey(long portalPreferencesId);

	@Override
	public java.util.Map<java.io.Serializable, PortalPreferences> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the portal preferenceses.
	*
	* @return the portal preferenceses
	*/
	public java.util.List<PortalPreferences> findAll();

	/**
	* Returns a range of all the portal preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortalPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portal preferenceses
	* @param end the upper bound of the range of portal preferenceses (not inclusive)
	* @return the range of portal preferenceses
	*/
	public java.util.List<PortalPreferences> findAll(int start, int end);

	/**
	* Returns an ordered range of all the portal preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortalPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portal preferenceses
	* @param end the upper bound of the range of portal preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portal preferenceses
	*/
	public java.util.List<PortalPreferences> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortalPreferences> orderByComparator);

	/**
	* Returns an ordered range of all the portal preferenceses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PortalPreferencesModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portal preferenceses
	* @param end the upper bound of the range of portal preferenceses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of portal preferenceses
	*/
	public java.util.List<PortalPreferences> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortalPreferences> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the portal preferenceses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of portal preferenceses.
	*
	* @return the number of portal preferenceses
	*/
	public int countAll();
}