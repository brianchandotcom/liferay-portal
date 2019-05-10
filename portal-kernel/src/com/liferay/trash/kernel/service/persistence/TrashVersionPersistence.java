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

package com.liferay.trash.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.trash.kernel.exception.NoSuchVersionException;
import com.liferay.trash.kernel.model.TrashVersion;

/**
 * The persistence interface for the trash version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.trash.service.persistence.impl.TrashVersionPersistenceImpl
 * @see TrashVersionUtil
 * @deprecated As of Judson (7.1.x), replaced by {@link
com.liferay.trash.model.impl.TrashVersionImpl}
 * @generated
 */
@Deprecated
@ProviderType
public interface TrashVersionPersistence extends BasePersistence<TrashVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashVersionUtil} to access the trash version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the trash versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching trash versions
	*/
	public java.util.List<TrashVersion> findByEntryId(long entryId);

	/**
	* Returns a range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	*/
	public java.util.List<TrashVersion> findByEntryId(long entryId, int start,
		int end);

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	*/
	public java.util.List<TrashVersion> findByEntryId(long entryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching trash versions
	*/
	public java.util.List<TrashVersion> findByEntryId(long entryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws NoSuchVersionException if a matching trash version could not be found
	*/
	public TrashVersion findByEntryId_First(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException;

	/**
	* Returns the first trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	*/
	public TrashVersion fetchByEntryId_First(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns the last trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws NoSuchVersionException if a matching trash version could not be found
	*/
	public TrashVersion findByEntryId_Last(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException;

	/**
	* Returns the last trash version in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	*/
	public TrashVersion fetchByEntryId_Last(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws NoSuchVersionException if a trash version with the primary key could not be found
	*/
	public TrashVersion[] findByEntryId_PrevAndNext(long versionId,
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException;

	/**
	* Removes all the trash versions where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public void removeByEntryId(long entryId);

	/**
	* Returns the number of trash versions where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching trash versions
	*/
	public int countByEntryId(long entryId);

	/**
	* Returns all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @return the matching trash versions
	*/
	public java.util.List<TrashVersion> findByE_C(long entryId, long classNameId);

	/**
	* Returns a range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of matching trash versions
	*/
	public java.util.List<TrashVersion> findByE_C(long entryId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching trash versions
	*/
	public java.util.List<TrashVersion> findByE_C(long entryId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching trash versions
	*/
	public java.util.List<TrashVersion> findByE_C(long entryId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version
	* @throws NoSuchVersionException if a matching trash version could not be found
	*/
	public TrashVersion findByE_C_First(long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException;

	/**
	* Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	*/
	public TrashVersion fetchByE_C_First(long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version
	* @throws NoSuchVersionException if a matching trash version could not be found
	*/
	public TrashVersion findByE_C_Last(long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException;

	/**
	* Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	*/
	public TrashVersion fetchByE_C_Last(long entryId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	*
	* @param versionId the primary key of the current trash version
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next trash version
	* @throws NoSuchVersionException if a trash version with the primary key could not be found
	*/
	public TrashVersion[] findByE_C_PrevAndNext(long versionId, long entryId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException;

	/**
	* Removes all the trash versions where entryId = &#63; and classNameId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	*/
	public void removeByE_C(long entryId, long classNameId);

	/**
	* Returns the number of trash versions where entryId = &#63; and classNameId = &#63;.
	*
	* @param entryId the entry ID
	* @param classNameId the class name ID
	* @return the number of matching trash versions
	*/
	public int countByE_C(long entryId, long classNameId);

	/**
	* Returns the trash version where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchVersionException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching trash version
	* @throws NoSuchVersionException if a matching trash version could not be found
	*/
	public TrashVersion findByC_C(long classNameId, long classPK)
		throws NoSuchVersionException;

	/**
	* Returns the trash version where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	*/
	public TrashVersion fetchByC_C(long classNameId, long classPK);

	/**
	* Returns the trash version where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	*/
	public TrashVersion fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the trash version where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the trash version that was removed
	*/
	public TrashVersion removeByC_C(long classNameId, long classPK)
		throws NoSuchVersionException;

	/**
	* Returns the number of trash versions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching trash versions
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the trash version in the entity cache if it is enabled.
	*
	* @param trashVersion the trash version
	*/
	public void cacheResult(TrashVersion trashVersion);

	/**
	* Caches the trash versions in the entity cache if it is enabled.
	*
	* @param trashVersions the trash versions
	*/
	public void cacheResult(java.util.List<TrashVersion> trashVersions);

	/**
	* Creates a new trash version with the primary key. Does not add the trash version to the database.
	*
	* @param versionId the primary key for the new trash version
	* @return the new trash version
	*/
	public TrashVersion create(long versionId);

	/**
	* Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version that was removed
	* @throws NoSuchVersionException if a trash version with the primary key could not be found
	*/
	public TrashVersion remove(long versionId) throws NoSuchVersionException;

	public TrashVersion updateImpl(TrashVersion trashVersion);

	/**
	* Returns the trash version with the primary key or throws a {@link NoSuchVersionException} if it could not be found.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version
	* @throws NoSuchVersionException if a trash version with the primary key could not be found
	*/
	public TrashVersion findByPrimaryKey(long versionId)
		throws NoSuchVersionException;

	/**
	* Returns the trash version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionId the primary key of the trash version
	* @return the trash version, or <code>null</code> if a trash version with the primary key could not be found
	*/
	public TrashVersion fetchByPrimaryKey(long versionId);

	@Override
	public java.util.Map<java.io.Serializable, TrashVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the trash versions.
	*
	* @return the trash versions
	*/
	public java.util.List<TrashVersion> findAll();

	/**
	* Returns a range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @return the range of trash versions
	*/
	public java.util.List<TrashVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of trash versions
	*/
	public java.util.List<TrashVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator);

	/**
	* Returns an ordered range of all the trash versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TrashVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash versions
	* @param end the upper bound of the range of trash versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of trash versions
	*/
	public java.util.List<TrashVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<TrashVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the trash versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of trash versions.
	*
	* @return the number of trash versions
	*/
	public int countAll();
}