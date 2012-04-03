/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialRelation;

/**
 * The persistence interface for the social relation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationPersistenceImpl
 * @see SocialRelationUtil
 * @generated
 */
public interface SocialRelationPersistence extends BasePersistence<SocialRelation> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialRelationUtil} to access the social relation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social relation in the entity cache if it is enabled.
	*
	* @param socialRelation the social relation
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialRelation socialRelation);

	/**
	* Caches the social relations in the entity cache if it is enabled.
	*
	* @param socialRelations the social relations
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialRelation> socialRelations);

	/**
	* Creates a new social relation with the primary key. Does not add the social relation to the database.
	*
	* @param relationId the primary key for the new social relation
	* @return the new social relation
	*/
	public com.liferay.portlet.social.model.SocialRelation create(
		long relationId);

	/**
	* Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation that was removed
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation remove(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	public com.liferay.portlet.social.model.SocialRelation updateImpl(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social relation with the primary key or throws a {@link com.liferay.portlet.social.NoSuchRelationException} if it could not be found.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByPrimaryKey(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation fetchByPrimaryKey(
		long relationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social relations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByUuid_PrevAndNext(
		long relationId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByCompanyId_PrevAndNext(
		long relationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId1(
		long userId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByUserId1_First(
		long userId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByUserId1_Last(
		long userId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByUserId1_PrevAndNext(
		long relationId, long userId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByUserId2(
		long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByUserId2_First(
		long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByUserId2_Last(
		long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByUserId2_PrevAndNext(
		long relationId, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where type = &#63;.
	*
	* @param type the type
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByType(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByType_First(
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByType_Last(
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByType_PrevAndNext(
		long relationId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByC_T_PrevAndNext(
		long relationId, long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_U2(
		long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_U2(
		long userId1, long userId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_U2(
		long userId1, long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU1_U2_First(
		long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU1_U2_Last(
		long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByU1_U2_PrevAndNext(
		long relationId, long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU1_T(
		long userId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU1_T_First(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU1_T_Last(
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByU1_T_PrevAndNext(
		long relationId, long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns all the social relations where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findByU2_T(
		long userId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU2_T_First(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU2_T_Last(
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param relationId the primary key of the current social relation
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a social relation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation[] findByU2_T_PrevAndNext(
		long relationId, long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchRelationException} if it could not be found.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relation
	* @throws com.liferay.portlet.social.NoSuchRelationException if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation findByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation fetchByU1_U2_T(
		long userId1, long userId2, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the social relations.
	*
	* @return the social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social relations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRelation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where userId1 = &#63; from the database.
	*
	* @param userId1 the user id1
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId1(long userId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where userId2 = &#63; from the database.
	*
	* @param userId2 the user id2
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId2(long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where type = &#63; from the database.
	*
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByType(int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where companyId = &#63; and type = &#63; from the database.
	*
	* @param companyId the company ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_T(long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where userId1 = &#63; and userId2 = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU1_U2(long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where userId1 = &#63; and type = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU1_T(long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social relations where userId2 = &#63; and type = &#63; from the database.
	*
	* @param userId2 the user id2
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByU2_T(long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the social relation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation removeByU1_U2_T(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchRelationException;

	/**
	* Removes all the social relations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId1(long userId1)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId2(long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where type = &#63;.
	*
	* @param type the type
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByType(int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_T(long companyId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByU1_U2(long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByU1_T(long userId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByU2_T(long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations where userId1 = &#63; and userId2 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the number of matching social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countByU1_U2_T(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of social relations.
	*
	* @return the number of social relations
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}