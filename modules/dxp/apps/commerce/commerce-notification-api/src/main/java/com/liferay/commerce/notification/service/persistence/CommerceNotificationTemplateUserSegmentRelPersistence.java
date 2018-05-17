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

package com.liferay.commerce.notification.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce notification template user segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.notification.service.persistence.impl.CommerceNotificationTemplateUserSegmentRelPersistenceImpl
 * @see CommerceNotificationTemplateUserSegmentRelUtil
 * @generated
 */
@ProviderType
public interface CommerceNotificationTemplateUserSegmentRelPersistence
	extends BasePersistence<CommerceNotificationTemplateUserSegmentRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceNotificationTemplateUserSegmentRelUtil} to access the commerce notification template user segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @return the matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	* Returns a range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end);

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel findByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel findByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns the commerce notification template user segment rels before and after the current commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the current commerce notification template user segment rel
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel[] findByCommerceNotificationTemplateId_PrevAndNext(
		long commerceNotificationTemplateUserSegmentRelId,
		long commerceNotificationTemplateId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Removes all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63; from the database.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	*/
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	* Returns the number of commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @return the number of matching commerce notification template user segment rels
	*/
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId);

	/**
	* Returns all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns a range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the first commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the last commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns the commerce notification template user segment rels before and after the current commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the current commerce notification template user segment rel
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceNotificationTemplateUserSegmentRelId,
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Removes all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns the number of commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce notification template user segment rels
	*/
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId);

	/**
	* Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or throws a {@link NoSuchNotificationTemplateUserSegmentRelException} if it could not be found.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel findByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId);

	/**
	* Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the commerce notification template user segment rel that was removed
	*/
	public CommerceNotificationTemplateUserSegmentRel removeByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the number of commerce notification template user segment rels where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceNotificationTemplateId the commerce notification template ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce notification template user segment rels
	*/
	public int countByC_C(long commerceNotificationTemplateId,
		long commerceUserSegmentEntryId);

	/**
	* Caches the commerce notification template user segment rel in the entity cache if it is enabled.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	*/
	public void cacheResult(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel);

	/**
	* Caches the commerce notification template user segment rels in the entity cache if it is enabled.
	*
	* @param commerceNotificationTemplateUserSegmentRels the commerce notification template user segment rels
	*/
	public void cacheResult(
		java.util.List<CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels);

	/**
	* Creates a new commerce notification template user segment rel with the primary key. Does not add the commerce notification template user segment rel to the database.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key for the new commerce notification template user segment rel
	* @return the new commerce notification template user segment rel
	*/
	public CommerceNotificationTemplateUserSegmentRel create(
		long commerceNotificationTemplateUserSegmentRelId);

	/**
	* Removes the commerce notification template user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was removed
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel remove(
		long commerceNotificationTemplateUserSegmentRelId)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	public CommerceNotificationTemplateUserSegmentRel updateImpl(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel);

	/**
	* Returns the commerce notification template user segment rel with the primary key or throws a {@link NoSuchNotificationTemplateUserSegmentRelException} if it could not be found.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel
	* @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel findByPrimaryKey(
		long commerceNotificationTemplateUserSegmentRelId)
		throws NoSuchNotificationTemplateUserSegmentRelException;

	/**
	* Returns the commerce notification template user segment rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel, or <code>null</code> if a commerce notification template user segment rel with the primary key could not be found
	*/
	public CommerceNotificationTemplateUserSegmentRel fetchByPrimaryKey(
		long commerceNotificationTemplateUserSegmentRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceNotificationTemplateUserSegmentRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce notification template user segment rels.
	*
	* @return the commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findAll();

	/**
	* Returns a range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce notification template user segment rels
	*/
	public java.util.List<CommerceNotificationTemplateUserSegmentRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce notification template user segment rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce notification template user segment rels.
	*
	* @return the number of commerce notification template user segment rels
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}