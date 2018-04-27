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

package com.liferay.commerce.user.segment.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce user segment criterion service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.user.segment.service.persistence.impl.CommerceUserSegmentCriterionPersistenceImpl
 * @see CommerceUserSegmentCriterionUtil
 * @generated
 */
@ProviderType
public interface CommerceUserSegmentCriterionPersistence extends BasePersistence<CommerceUserSegmentCriterion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceUserSegmentCriterionUtil} to access the commerce user segment criterion persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns a range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @return the range of matching commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator);

	/**
	* Returns an ordered range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a matching commerce user segment criterion could not be found
	*/
	public CommerceUserSegmentCriterion findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws NoSuchUserSegmentCriterionException;

	/**
	* Returns the first commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment criterion, or <code>null</code> if a matching commerce user segment criterion could not be found
	*/
	public CommerceUserSegmentCriterion fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator);

	/**
	* Returns the last commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a matching commerce user segment criterion could not be found
	*/
	public CommerceUserSegmentCriterion findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws NoSuchUserSegmentCriterionException;

	/**
	* Returns the last commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment criterion, or <code>null</code> if a matching commerce user segment criterion could not be found
	*/
	public CommerceUserSegmentCriterion fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator);

	/**
	* Returns the commerce user segment criterions before and after the current commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentCriterionId the primary key of the current commerce user segment criterion
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	*/
	public CommerceUserSegmentCriterion[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceUserSegmentCriterionId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws NoSuchUserSegmentCriterionException;

	/**
	* Removes all the commerce user segment criterions where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns the number of commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce user segment criterions
	*/
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Caches the commerce user segment criterion in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentCriterion the commerce user segment criterion
	*/
	public void cacheResult(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion);

	/**
	* Caches the commerce user segment criterions in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentCriterions the commerce user segment criterions
	*/
	public void cacheResult(
		java.util.List<CommerceUserSegmentCriterion> commerceUserSegmentCriterions);

	/**
	* Creates a new commerce user segment criterion with the primary key. Does not add the commerce user segment criterion to the database.
	*
	* @param commerceUserSegmentCriterionId the primary key for the new commerce user segment criterion
	* @return the new commerce user segment criterion
	*/
	public CommerceUserSegmentCriterion create(
		long commerceUserSegmentCriterionId);

	/**
	* Removes the commerce user segment criterion with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion that was removed
	* @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	*/
	public CommerceUserSegmentCriterion remove(
		long commerceUserSegmentCriterionId)
		throws NoSuchUserSegmentCriterionException;

	public CommerceUserSegmentCriterion updateImpl(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion);

	/**
	* Returns the commerce user segment criterion with the primary key or throws a {@link NoSuchUserSegmentCriterionException} if it could not be found.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	*/
	public CommerceUserSegmentCriterion findByPrimaryKey(
		long commerceUserSegmentCriterionId)
		throws NoSuchUserSegmentCriterionException;

	/**
	* Returns the commerce user segment criterion with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion, or <code>null</code> if a commerce user segment criterion with the primary key could not be found
	*/
	public CommerceUserSegmentCriterion fetchByPrimaryKey(
		long commerceUserSegmentCriterionId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceUserSegmentCriterion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce user segment criterions.
	*
	* @return the commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findAll();

	/**
	* Returns a range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @return the range of commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator);

	/**
	* Returns an ordered range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce user segment criterions
	*/
	public java.util.List<CommerceUserSegmentCriterion> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce user segment criterions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce user segment criterions.
	*
	* @return the number of commerce user segment criterions
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}