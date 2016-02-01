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

import com.liferay.portlet.social.exception.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;

/**
 * The persistence interface for the social relation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.persistence.impl.SocialRelationPersistenceImpl
 * @see SocialRelationUtil
 * @generated
 */
@ProviderType
public interface SocialRelationPersistence extends BasePersistence<SocialRelation> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialRelationUtil} to access the social relation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the social relations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the social relations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where uuid = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByUuid_PrevAndNext(long relationId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of social relations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching social relations
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the social relations where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the social relations where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the social relations where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByUuid_C_PrevAndNext(long relationId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of social relations where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching social relations
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the social relations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByCompanyId(long companyId);

	/**
	* Returns a range of all the social relations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where companyId = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByCompanyId_PrevAndNext(long relationId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of social relations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social relations
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the social relations where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId1(long userId1);

	/**
	* Returns a range of all the social relations where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId1(long userId1,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId1(long userId1,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId1(long userId1,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUserId1_First(long userId1,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUserId1_First(long userId1,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUserId1_Last(long userId1,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUserId1_Last(long userId1,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByUserId1_PrevAndNext(long relationId,
		long userId1,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where userId1 = &#63; from the database.
	*
	* @param userId1 the user id1
	*/
	public void removeByUserId1(long userId1);

	/**
	* Returns the number of social relations where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @return the number of matching social relations
	*/
	public int countByUserId1(long userId1);

	/**
	* Returns all the social relations where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId2(long userId2);

	/**
	* Returns a range of all the social relations where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId2(long userId2,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId2(long userId2,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByUserId2(long userId2,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUserId2_First(long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUserId2_First(long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByUserId2_Last(long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByUserId2_Last(long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByUserId2_PrevAndNext(long relationId,
		long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where userId2 = &#63; from the database.
	*
	* @param userId2 the user id2
	*/
	public void removeByUserId2(long userId2);

	/**
	* Returns the number of social relations where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @return the number of matching social relations
	*/
	public int countByUserId2(long userId2);

	/**
	* Returns all the social relations where type = &#63;.
	*
	* @param type the type
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByType(int type);

	/**
	* Returns a range of all the social relations where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByType(int type, int start,
		int end);

	/**
	* Returns an ordered range of all the social relations where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByType(int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByType(int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByType_First(int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByType_First(int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByType_Last(int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByType_Last(int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where type = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByType_PrevAndNext(long relationId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where type = &#63; from the database.
	*
	* @param type the type
	*/
	public void removeByType(int type);

	/**
	* Returns the number of social relations where type = &#63;.
	*
	* @param type the type
	* @return the number of matching social relations
	*/
	public int countByType(int type);

	/**
	* Returns all the social relations where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByC_T(long companyId, int type);

	/**
	* Returns a range of all the social relations where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByC_T(long companyId, int type,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByC_T(long companyId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where companyId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByC_T(long companyId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByC_T_First(long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByC_T_First(long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByC_T_Last(long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByC_T_Last(long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByC_T_PrevAndNext(long relationId,
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where companyId = &#63; and type = &#63; from the database.
	*
	* @param companyId the company ID
	* @param type the type
	*/
	public void removeByC_T(long companyId, int type);

	/**
	* Returns the number of social relations where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the number of matching social relations
	*/
	public int countByC_T(long companyId, int type);

	/**
	* Returns all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_U2(long userId1, long userId2);

	/**
	* Returns a range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_U2(long userId1,
		long userId2, int start, int end);

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_U2(long userId1,
		long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_U2(long userId1,
		long userId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU1_U2_First(long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU1_U2_First(long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU1_U2_Last(long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU1_U2_Last(long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByU1_U2_PrevAndNext(long relationId,
		long userId1, long userId2,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where userId1 = &#63; and userId2 = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	*/
	public void removeByU1_U2(long userId1, long userId2);

	/**
	* Returns the number of social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @return the number of matching social relations
	*/
	public int countByU1_U2(long userId1, long userId2);

	/**
	* Returns all the social relations where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_T(long userId1, int type);

	/**
	* Returns a range of all the social relations where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_T(long userId1, int type,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_T(long userId1, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where userId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId1 the user id1
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU1_T(long userId1, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU1_T_First(long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU1_T_First(long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU1_T_Last(long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU1_T_Last(long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByU1_T_PrevAndNext(long relationId,
		long userId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where userId1 = &#63; and type = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param type the type
	*/
	public void removeByU1_T(long userId1, int type);

	/**
	* Returns the number of social relations where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @return the number of matching social relations
	*/
	public int countByU1_T(long userId1, int type);

	/**
	* Returns all the social relations where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relations
	*/
	public java.util.List<SocialRelation> findByU2_T(long userId2, int type);

	/**
	* Returns a range of all the social relations where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU2_T(long userId2, int type,
		int start, int end);

	/**
	* Returns an ordered range of all the social relations where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU2_T(long userId2, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations where userId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId2 the user id2
	* @param type the type
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching social relations
	*/
	public java.util.List<SocialRelation> findByU2_T(long userId2, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU2_T_First(long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU2_T_First(long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU2_T_Last(long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU2_T_Last(long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation[] findByU2_T_PrevAndNext(long relationId,
		long userId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator)
		throws NoSuchRelationException;

	/**
	* Removes all the social relations where userId2 = &#63; and type = &#63; from the database.
	*
	* @param userId2 the user id2
	* @param type the type
	*/
	public void removeByU2_T(long userId2, int type);

	/**
	* Returns the number of social relations where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @return the number of matching social relations
	*/
	public int countByU2_T(long userId2, int type);

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or throws a {@link NoSuchRelationException} if it could not be found.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public SocialRelation findByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException;

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type);

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public SocialRelation fetchByU1_U2_T(long userId1, long userId2, int type,
		boolean retrieveFromCache);

	/**
	* Removes the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the social relation that was removed
	*/
	public SocialRelation removeByU1_U2_T(long userId1, long userId2, int type)
		throws NoSuchRelationException;

	/**
	* Returns the number of social relations where userId1 = &#63; and userId2 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the number of matching social relations
	*/
	public int countByU1_U2_T(long userId1, long userId2, int type);

	/**
	* Caches the social relation in the entity cache if it is enabled.
	*
	* @param socialRelation the social relation
	*/
	public void cacheResult(SocialRelation socialRelation);

	/**
	* Caches the social relations in the entity cache if it is enabled.
	*
	* @param socialRelations the social relations
	*/
	public void cacheResult(java.util.List<SocialRelation> socialRelations);

	/**
	* Creates a new social relation with the primary key. Does not add the social relation to the database.
	*
	* @param relationId the primary key for the new social relation
	* @return the new social relation
	*/
	public SocialRelation create(long relationId);

	/**
	* Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation that was removed
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation remove(long relationId)
		throws NoSuchRelationException;

	public SocialRelation updateImpl(SocialRelation socialRelation);

	/**
	* Returns the social relation with the primary key or throws a {@link NoSuchRelationException} if it could not be found.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public SocialRelation findByPrimaryKey(long relationId)
		throws NoSuchRelationException;

	/**
	* Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	*/
	public SocialRelation fetchByPrimaryKey(long relationId);

	@Override
	public java.util.Map<java.io.Serializable, SocialRelation> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the social relations.
	*
	* @return the social relations
	*/
	public java.util.List<SocialRelation> findAll();

	/**
	* Returns a range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @return the range of social relations
	*/
	public java.util.List<SocialRelation> findAll(int start, int end);

	/**
	* Returns an ordered range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social relations
	*/
	public java.util.List<SocialRelation> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator);

	/**
	* Returns an ordered range of all the social relations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SocialRelationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social relations
	* @param end the upper bound of the range of social relations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of social relations
	*/
	public java.util.List<SocialRelation> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the social relations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of social relations.
	*
	* @return the number of social relations
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}