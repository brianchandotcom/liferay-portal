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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderInstanceLinkException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLink;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the ddm data provider instance link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMDataProviderInstanceLinkPersistenceImpl
 * @see DDMDataProviderInstanceLinkUtil
 * @generated
 */
@ProviderType
public interface DDMDataProviderInstanceLinkPersistence extends BasePersistence<DDMDataProviderInstanceLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMDataProviderInstanceLinkUtil} to access the ddm data provider instance link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @return the matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId);

	/**
	* Returns a range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @return the range of matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns an ordered range of all the ddm data provider instance links where dataProviderInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByDataProviderInstanceId(
		long dataProviderInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink findByDataProviderInstanceId_First(
		long dataProviderInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the first ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink fetchByDataProviderInstanceId_First(
		long dataProviderInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns the last ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink findByDataProviderInstanceId_Last(
		long dataProviderInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the last ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink fetchByDataProviderInstanceId_Last(
		long dataProviderInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns the ddm data provider instance links before and after the current ddm data provider instance link in the ordered set where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceLinkId the primary key of the current ddm data provider instance link
	* @param dataProviderInstanceId the data provider instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	*/
	public DDMDataProviderInstanceLink[] findByDataProviderInstanceId_PrevAndNext(
		long dataProviderInstanceLinkId, long dataProviderInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Removes all the ddm data provider instance links where dataProviderInstanceId = &#63; from the database.
	*
	* @param dataProviderInstanceId the data provider instance ID
	*/
	public void removeByDataProviderInstanceId(long dataProviderInstanceId);

	/**
	* Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @return the number of matching ddm data provider instance links
	*/
	public int countByDataProviderInstanceId(long dataProviderInstanceId);

	/**
	* Returns all the ddm data provider instance links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId);

	/**
	* Returns a range of all the ddm data provider instance links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @return the range of matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end);

	/**
	* Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns an ordered range of all the ddm data provider instance links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink findByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the first ddm data provider instance link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink fetchByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns the last ddm data provider instance link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink findByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the last ddm data provider instance link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink fetchByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns the ddm data provider instance links before and after the current ddm data provider instance link in the ordered set where structureId = &#63;.
	*
	* @param dataProviderInstanceLinkId the primary key of the current ddm data provider instance link
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	*/
	public DDMDataProviderInstanceLink[] findByStructureId_PrevAndNext(
		long dataProviderInstanceLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Removes all the ddm data provider instance links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	*/
	public void removeByStructureId(long structureId);

	/**
	* Returns the number of ddm data provider instance links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching ddm data provider instance links
	*/
	public int countByStructureId(long structureId);

	/**
	* Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or throws a {@link NoSuchDataProviderInstanceLinkException} if it could not be found.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param structureId the structure ID
	* @return the matching ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink findByD_S(long dataProviderInstanceId,
		long structureId) throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param structureId the structure ID
	* @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink fetchByD_S(long dataProviderInstanceId,
		long structureId);

	/**
	* Returns the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param structureId the structure ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching ddm data provider instance link, or <code>null</code> if a matching ddm data provider instance link could not be found
	*/
	public DDMDataProviderInstanceLink fetchByD_S(long dataProviderInstanceId,
		long structureId, boolean retrieveFromCache);

	/**
	* Removes the ddm data provider instance link where dataProviderInstanceId = &#63; and structureId = &#63; from the database.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param structureId the structure ID
	* @return the ddm data provider instance link that was removed
	*/
	public DDMDataProviderInstanceLink removeByD_S(
		long dataProviderInstanceId, long structureId)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the number of ddm data provider instance links where dataProviderInstanceId = &#63; and structureId = &#63;.
	*
	* @param dataProviderInstanceId the data provider instance ID
	* @param structureId the structure ID
	* @return the number of matching ddm data provider instance links
	*/
	public int countByD_S(long dataProviderInstanceId, long structureId);

	/**
	* Caches the ddm data provider instance link in the entity cache if it is enabled.
	*
	* @param ddmDataProviderInstanceLink the ddm data provider instance link
	*/
	public void cacheResult(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink);

	/**
	* Caches the ddm data provider instance links in the entity cache if it is enabled.
	*
	* @param ddmDataProviderInstanceLinks the ddm data provider instance links
	*/
	public void cacheResult(
		java.util.List<DDMDataProviderInstanceLink> ddmDataProviderInstanceLinks);

	/**
	* Creates a new ddm data provider instance link with the primary key. Does not add the ddm data provider instance link to the database.
	*
	* @param dataProviderInstanceLinkId the primary key for the new ddm data provider instance link
	* @return the new ddm data provider instance link
	*/
	public DDMDataProviderInstanceLink create(long dataProviderInstanceLinkId);

	/**
	* Removes the ddm data provider instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	* @return the ddm data provider instance link that was removed
	* @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	*/
	public DDMDataProviderInstanceLink remove(long dataProviderInstanceLinkId)
		throws NoSuchDataProviderInstanceLinkException;

	public DDMDataProviderInstanceLink updateImpl(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink);

	/**
	* Returns the ddm data provider instance link with the primary key or throws a {@link NoSuchDataProviderInstanceLinkException} if it could not be found.
	*
	* @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	* @return the ddm data provider instance link
	* @throws NoSuchDataProviderInstanceLinkException if a ddm data provider instance link with the primary key could not be found
	*/
	public DDMDataProviderInstanceLink findByPrimaryKey(
		long dataProviderInstanceLinkId)
		throws NoSuchDataProviderInstanceLinkException;

	/**
	* Returns the ddm data provider instance link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param dataProviderInstanceLinkId the primary key of the ddm data provider instance link
	* @return the ddm data provider instance link, or <code>null</code> if a ddm data provider instance link with the primary key could not be found
	*/
	public DDMDataProviderInstanceLink fetchByPrimaryKey(
		long dataProviderInstanceLinkId);

	@Override
	public java.util.Map<java.io.Serializable, DDMDataProviderInstanceLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the ddm data provider instance links.
	*
	* @return the ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findAll();

	/**
	* Returns a range of all the ddm data provider instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @return the range of ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the ddm data provider instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator);

	/**
	* Returns an ordered range of all the ddm data provider instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMDataProviderInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm data provider instance links
	* @param end the upper bound of the range of ddm data provider instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of ddm data provider instance links
	*/
	public java.util.List<DDMDataProviderInstanceLink> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMDataProviderInstanceLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the ddm data provider instance links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of ddm data provider instance links.
	*
	* @return the number of ddm data provider instance links
	*/
	public int countAll();
}