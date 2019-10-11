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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryMappingException;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the friendly url entry mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMappingUtil
 * @generated
 */
@ProviderType
public interface FriendlyURLEntryMappingPersistence
	extends BasePersistence<FriendlyURLEntryMapping> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLEntryMappingUtil} to access the friendly url entry mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the friendly url entry mapping where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchFriendlyURLEntryMappingException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching friendly url entry mapping
	 * @throws NoSuchFriendlyURLEntryMappingException if a matching friendly url entry mapping could not be found
	 */
	public FriendlyURLEntryMapping findByC_C(long classNameId, long classPK)
		throws NoSuchFriendlyURLEntryMappingException;

	/**
	 * Returns the friendly url entry mapping where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching friendly url entry mapping, or <code>null</code> if a matching friendly url entry mapping could not be found
	 */
	public FriendlyURLEntryMapping fetchByC_C(long classNameId, long classPK);

	/**
	 * Returns the friendly url entry mapping where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching friendly url entry mapping, or <code>null</code> if a matching friendly url entry mapping could not be found
	 */
	public FriendlyURLEntryMapping fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the friendly url entry mapping where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the friendly url entry mapping that was removed
	 */
	public FriendlyURLEntryMapping removeByC_C(long classNameId, long classPK)
		throws NoSuchFriendlyURLEntryMappingException;

	/**
	 * Returns the number of friendly url entry mappings where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching friendly url entry mappings
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Caches the friendly url entry mapping in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryMapping the friendly url entry mapping
	 */
	public void cacheResult(FriendlyURLEntryMapping friendlyURLEntryMapping);

	/**
	 * Caches the friendly url entry mappings in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryMappings the friendly url entry mappings
	 */
	public void cacheResult(
		java.util.List<FriendlyURLEntryMapping> friendlyURLEntryMappings);

	/**
	 * Creates a new friendly url entry mapping with the primary key. Does not add the friendly url entry mapping to the database.
	 *
	 * @param friendlyURLEntryMappingId the primary key for the new friendly url entry mapping
	 * @return the new friendly url entry mapping
	 */
	public FriendlyURLEntryMapping create(long friendlyURLEntryMappingId);

	/**
	 * Removes the friendly url entry mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLEntryMappingId the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping that was removed
	 * @throws NoSuchFriendlyURLEntryMappingException if a friendly url entry mapping with the primary key could not be found
	 */
	public FriendlyURLEntryMapping remove(long friendlyURLEntryMappingId)
		throws NoSuchFriendlyURLEntryMappingException;

	public FriendlyURLEntryMapping updateImpl(
		FriendlyURLEntryMapping friendlyURLEntryMapping);

	/**
	 * Returns the friendly url entry mapping with the primary key or throws a <code>NoSuchFriendlyURLEntryMappingException</code> if it could not be found.
	 *
	 * @param friendlyURLEntryMappingId the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping
	 * @throws NoSuchFriendlyURLEntryMappingException if a friendly url entry mapping with the primary key could not be found
	 */
	public FriendlyURLEntryMapping findByPrimaryKey(
			long friendlyURLEntryMappingId)
		throws NoSuchFriendlyURLEntryMappingException;

	/**
	 * Returns the friendly url entry mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLEntryMappingId the primary key of the friendly url entry mapping
	 * @return the friendly url entry mapping, or <code>null</code> if a friendly url entry mapping with the primary key could not be found
	 */
	public FriendlyURLEntryMapping fetchByPrimaryKey(
		long friendlyURLEntryMappingId);

	/**
	 * Returns all the friendly url entry mappings.
	 *
	 * @return the friendly url entry mappings
	 */
	public java.util.List<FriendlyURLEntryMapping> findAll();

	/**
	 * Returns a range of all the friendly url entry mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry mappings
	 * @param end the upper bound of the range of friendly url entry mappings (not inclusive)
	 * @return the range of friendly url entry mappings
	 */
	public java.util.List<FriendlyURLEntryMapping> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the friendly url entry mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry mappings
	 * @param end the upper bound of the range of friendly url entry mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url entry mappings
	 */
	public java.util.List<FriendlyURLEntryMapping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FriendlyURLEntryMapping> orderByComparator);

	/**
	 * Returns an ordered range of all the friendly url entry mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FriendlyURLEntryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry mappings
	 * @param end the upper bound of the range of friendly url entry mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of friendly url entry mappings
	 */
	public java.util.List<FriendlyURLEntryMapping> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FriendlyURLEntryMapping> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the friendly url entry mappings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of friendly url entry mappings.
	 *
	 * @return the number of friendly url entry mappings
	 */
	public int countAll();

}