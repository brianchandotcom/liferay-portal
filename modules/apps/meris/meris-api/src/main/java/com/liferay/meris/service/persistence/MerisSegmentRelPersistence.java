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

package com.liferay.meris.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.meris.exception.NoSuchSegmentRelException;
import com.liferay.meris.model.MerisSegmentRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the meris segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see com.liferay.meris.service.persistence.impl.MerisSegmentRelPersistenceImpl
 * @see MerisSegmentRelUtil
 * @generated
 */
@ProviderType
public interface MerisSegmentRelPersistence extends BasePersistence<MerisSegmentRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MerisSegmentRelUtil} to access the meris segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the meris segment rels where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @return the matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId);

	/**
	* Returns a range of all the meris segment rels where merisSegmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param merisSegmentId the meris segment ID
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @return the range of matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId, int start, int end);

	/**
	* Returns an ordered range of all the meris segment rels where merisSegmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param merisSegmentId the meris segment ID
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the meris segment rels where merisSegmentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param merisSegmentId the meris segment ID
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByMerisSegmentId(
		long merisSegmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel findByMerisSegmentId_First(long merisSegmentId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	* Returns the first meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel fetchByMerisSegmentId_First(long merisSegmentId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns the last meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel findByMerisSegmentId_Last(long merisSegmentId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	* Returns the last meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel fetchByMerisSegmentId_Last(long merisSegmentId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns the meris segment rels before and after the current meris segment rel in the ordered set where merisSegmentId = &#63;.
	*
	* @param merisSegmentRelId the primary key of the current meris segment rel
	* @param merisSegmentId the meris segment ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment rel
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public MerisSegmentRel[] findByMerisSegmentId_PrevAndNext(
		long merisSegmentRelId, long merisSegmentId,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	* Removes all the meris segment rels where merisSegmentId = &#63; from the database.
	*
	* @param merisSegmentId the meris segment ID
	*/
	public void removeByMerisSegmentId(long merisSegmentId);

	/**
	* Returns the number of meris segment rels where merisSegmentId = &#63;.
	*
	* @param merisSegmentId the meris segment ID
	* @return the number of matching meris segment rels
	*/
	public int countByMerisSegmentId(long merisSegmentId);

	/**
	* Returns all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK);

	/**
	* Returns a range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @return the range of matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findByCN_CPK(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel findByCN_CPK_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	* Returns the first meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel fetchByCN_CPK_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns the last meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel
	* @throws NoSuchSegmentRelException if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel findByCN_CPK_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	* Returns the last meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching meris segment rel, or <code>null</code> if a matching meris segment rel could not be found
	*/
	public MerisSegmentRel fetchByCN_CPK_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns the meris segment rels before and after the current meris segment rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param merisSegmentRelId the primary key of the current meris segment rel
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next meris segment rel
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public MerisSegmentRel[] findByCN_CPK_PrevAndNext(long merisSegmentRelId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator)
		throws NoSuchSegmentRelException;

	/**
	* Removes all the meris segment rels where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByCN_CPK(long classNameId, long classPK);

	/**
	* Returns the number of meris segment rels where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching meris segment rels
	*/
	public int countByCN_CPK(long classNameId, long classPK);

	/**
	* Caches the meris segment rel in the entity cache if it is enabled.
	*
	* @param merisSegmentRel the meris segment rel
	*/
	public void cacheResult(MerisSegmentRel merisSegmentRel);

	/**
	* Caches the meris segment rels in the entity cache if it is enabled.
	*
	* @param merisSegmentRels the meris segment rels
	*/
	public void cacheResult(java.util.List<MerisSegmentRel> merisSegmentRels);

	/**
	* Creates a new meris segment rel with the primary key. Does not add the meris segment rel to the database.
	*
	* @param merisSegmentRelId the primary key for the new meris segment rel
	* @return the new meris segment rel
	*/
	public MerisSegmentRel create(long merisSegmentRelId);

	/**
	* Removes the meris segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param merisSegmentRelId the primary key of the meris segment rel
	* @return the meris segment rel that was removed
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public MerisSegmentRel remove(long merisSegmentRelId)
		throws NoSuchSegmentRelException;

	public MerisSegmentRel updateImpl(MerisSegmentRel merisSegmentRel);

	/**
	* Returns the meris segment rel with the primary key or throws a {@link NoSuchSegmentRelException} if it could not be found.
	*
	* @param merisSegmentRelId the primary key of the meris segment rel
	* @return the meris segment rel
	* @throws NoSuchSegmentRelException if a meris segment rel with the primary key could not be found
	*/
	public MerisSegmentRel findByPrimaryKey(long merisSegmentRelId)
		throws NoSuchSegmentRelException;

	/**
	* Returns the meris segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param merisSegmentRelId the primary key of the meris segment rel
	* @return the meris segment rel, or <code>null</code> if a meris segment rel with the primary key could not be found
	*/
	public MerisSegmentRel fetchByPrimaryKey(long merisSegmentRelId);

	@Override
	public java.util.Map<java.io.Serializable, MerisSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the meris segment rels.
	*
	* @return the meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findAll();

	/**
	* Returns a range of all the meris segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @return the range of meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findAll(int start, int end);

	/**
	* Returns an ordered range of all the meris segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the meris segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MerisSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of meris segment rels
	* @param end the upper bound of the range of meris segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of meris segment rels
	*/
	public java.util.List<MerisSegmentRel> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MerisSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the meris segment rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of meris segment rels.
	*
	* @return the number of meris segment rels
	*/
	public int countAll();
}