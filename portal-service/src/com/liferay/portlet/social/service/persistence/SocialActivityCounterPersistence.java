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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.exception.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialActivityCounter;

/**
 * The persistence interface for the social activity counter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialActivityCounterPersistenceImpl
 * @see SocialActivityCounterUtil
 * @generated
 */
@ProviderType
public interface SocialActivityCounterPersistence extends BasePersistence<SocialActivityCounter> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivityCounterUtil} to access the social activity counter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social activity counters where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByGroupId(long groupId);

	/**
	* Returns a range of all the social activity counters where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63;.
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public SocialActivityCounter[] findByGroupId_PrevAndNext(
		long activityCounterId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Removes all the social activity counters where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of social activity counters where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching social activity counters
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns an ordered range of all the social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Returns the first social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByC_C_First(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Returns the last social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByC_C_Last(long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public SocialActivityCounter[] findByC_C_PrevAndNext(
		long activityCounterId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Removes all the social activity counters where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of social activity counters where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching social activity counters
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @return the matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType);

	/**
	* Returns a range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end);

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns an ordered range of all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social activity counters
	*/
	public java.util.List<SocialActivityCounter> findByG_C_C_O(long groupId,
		long classNameId, long classPK, int ownerType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Returns the first social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByG_C_C_O_First(long groupId,
		long classNameId, long classPK, int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Returns the last social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByG_C_C_O_Last(long groupId,
		long classNameId, long classPK, int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns the social activity counters before and after the current social activity counter in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param activityCounterId the primary key of the current social activity counter
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public SocialActivityCounter[] findByG_C_C_O_PrevAndNext(
		long activityCounterId, long groupId, long classNameId, long classPK,
		int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator)
		throws NoSuchActivityCounterException;

	/**
	* Removes all the social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	*/
	public void removeByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType);

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and ownerType = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ownerType the owner type
	* @return the number of matching social activity counters
	*/
	public int countByG_C_C_O(long groupId, long classNameId, long classPK,
		int ownerType);

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or throws a {@link NoSuchActivityCounterException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod) throws NoSuchActivityCounterException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod);

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod, boolean retrieveFromCache);

	/**
	* Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the social activity counter that was removed
	*/
	public SocialActivityCounter removeByG_C_C_N_O_S(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int startPeriod) throws NoSuchActivityCounterException;

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and startPeriod = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param startPeriod the start period
	* @return the number of matching social activity counters
	*/
	public int countByG_C_C_N_O_S(long groupId, long classNameId, long classPK,
		java.lang.String name, int ownerType, int startPeriod);

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or throws a {@link NoSuchActivityCounterException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the matching social activity counter
	* @throws NoSuchActivityCounterException if a matching social activity counter could not be found
	*/
	public SocialActivityCounter findByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod) throws NoSuchActivityCounterException;

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod);

	/**
	* Returns the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social activity counter, or <code>null</code> if a matching social activity counter could not be found
	*/
	public SocialActivityCounter fetchByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod, boolean retrieveFromCache);

	/**
	* Removes the social activity counter where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the social activity counter that was removed
	*/
	public SocialActivityCounter removeByG_C_C_N_O_E(long groupId,
		long classNameId, long classPK, java.lang.String name, int ownerType,
		int endPeriod) throws NoSuchActivityCounterException;

	/**
	* Returns the number of social activity counters where groupId = &#63; and classNameId = &#63; and classPK = &#63; and name = &#63; and ownerType = &#63; and endPeriod = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param name the name
	* @param ownerType the owner type
	* @param endPeriod the end period
	* @return the number of matching social activity counters
	*/
	public int countByG_C_C_N_O_E(long groupId, long classNameId, long classPK,
		java.lang.String name, int ownerType, int endPeriod);

	/**
	* Caches the social activity counter in the entity cache if it is enabled.
	*
	* @param socialActivityCounter the social activity counter
	*/
	public void cacheResult(SocialActivityCounter socialActivityCounter);

	/**
	* Caches the social activity counters in the entity cache if it is enabled.
	*
	* @param socialActivityCounters the social activity counters
	*/
	public void cacheResult(
		java.util.List<SocialActivityCounter> socialActivityCounters);

	/**
	* Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	*
	* @param activityCounterId the primary key for the new social activity counter
	* @return the new social activity counter
	*/
	public SocialActivityCounter create(long activityCounterId);

	/**
	* Removes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter that was removed
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public SocialActivityCounter remove(long activityCounterId)
		throws NoSuchActivityCounterException;

	public SocialActivityCounter updateImpl(
		SocialActivityCounter socialActivityCounter);

	/**
	* Returns the social activity counter with the primary key or throws a {@link NoSuchActivityCounterException} if it could not be found.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter
	* @throws NoSuchActivityCounterException if a social activity counter with the primary key could not be found
	*/
	public SocialActivityCounter findByPrimaryKey(long activityCounterId)
		throws NoSuchActivityCounterException;

	/**
	* Returns the social activity counter with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter, or <code>null</code> if a social activity counter with the primary key could not be found
	*/
	public SocialActivityCounter fetchByPrimaryKey(long activityCounterId);

	@Override
	public java.util.Map<java.io.Serializable, SocialActivityCounter> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social activity counters.
	*
	* @return the social activity counters
	*/
	public java.util.List<SocialActivityCounter> findAll();

	/**
	* Returns a range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of social activity counters
	*/
	public java.util.List<SocialActivityCounter> findAll(int start, int end);

	/**
	* Returns an ordered range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social activity counters
	*/
	public java.util.List<SocialActivityCounter> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator);

	/**
	* Returns an ordered range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialActivityCounterModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social activity counters
	*/
	public java.util.List<SocialActivityCounter> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialActivityCounter> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social activity counters from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social activity counters.
	*
	* @return the number of social activity counters
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}