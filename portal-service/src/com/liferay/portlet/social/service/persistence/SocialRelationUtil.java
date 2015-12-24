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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialRelation;

import java.util.List;

/**
 * The persistence utility for the social relation service. This utility wraps {@link com.liferay.portlet.social.service.persistence.impl.SocialRelationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationPersistence
 * @see com.liferay.portlet.social.service.persistence.impl.SocialRelationPersistenceImpl
 * @generated
 */
@ProviderType
public class SocialRelationUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SocialRelation socialRelation) {
		getPersistence().clearCache(socialRelation);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SocialRelation> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialRelation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialRelation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SocialRelation update(SocialRelation socialRelation) {
		return getPersistence().update(socialRelation);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SocialRelation update(SocialRelation socialRelation,
		ServiceContext serviceContext) {
		return getPersistence().update(socialRelation, serviceContext);
	}

	/**
	* Returns all the social relations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

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
	public static List<SocialRelation> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static List<SocialRelation> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUuid_First(java.lang.String uuid,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUuid_Last(java.lang.String uuid,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the social relations before and after the current social relation in the ordered set where uuid = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation[] findByUuid_PrevAndNext(long relationId,
		java.lang.String uuid,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUuid_PrevAndNext(relationId, uuid, orderByComparator);
	}

	/**
	* Removes all the social relations where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of social relations where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching social relations
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the social relations where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

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
	public static List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

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
	public static List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

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
	public static SocialRelation[] findByUuid_C_PrevAndNext(long relationId,
		java.lang.String uuid, long companyId,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(relationId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the social relations where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of social relations where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching social relations
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the social relations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

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
	public static List<SocialRelation> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

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
	public static List<SocialRelation> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByCompanyId_First(long companyId,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByCompanyId_First(long companyId,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByCompanyId_Last(long companyId,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByCompanyId_Last(long companyId,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the social relations before and after the current social relation in the ordered set where companyId = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation[] findByCompanyId_PrevAndNext(
		long relationId, long companyId,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(relationId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the social relations where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of social relations where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching social relations
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the social relations where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByUserId1(long userId1) {
		return getPersistence().findByUserId1(userId1);
	}

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
	public static List<SocialRelation> findByUserId1(long userId1, int start,
		int end) {
		return getPersistence().findByUserId1(userId1, start, end);
	}

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
	public static List<SocialRelation> findByUserId1(long userId1, int start,
		int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByUserId1(userId1, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByUserId1(long userId1, int start,
		int end, OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId1(userId1, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUserId1_First(long userId1,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId1_First(userId1, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUserId1_First(long userId1,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByUserId1_First(userId1, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUserId1_Last(long userId1,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId1_Last(userId1, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUserId1_Last(long userId1,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByUserId1_Last(userId1, orderByComparator);
	}

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId1 = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId1 the user id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation[] findByUserId1_PrevAndNext(long relationId,
		long userId1, OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUserId1_PrevAndNext(relationId, userId1,
			orderByComparator);
	}

	/**
	* Removes all the social relations where userId1 = &#63; from the database.
	*
	* @param userId1 the user id1
	*/
	public static void removeByUserId1(long userId1) {
		getPersistence().removeByUserId1(userId1);
	}

	/**
	* Returns the number of social relations where userId1 = &#63;.
	*
	* @param userId1 the user id1
	* @return the number of matching social relations
	*/
	public static int countByUserId1(long userId1) {
		return getPersistence().countByUserId1(userId1);
	}

	/**
	* Returns all the social relations where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByUserId2(long userId2) {
		return getPersistence().findByUserId2(userId2);
	}

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
	public static List<SocialRelation> findByUserId2(long userId2, int start,
		int end) {
		return getPersistence().findByUserId2(userId2, start, end);
	}

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
	public static List<SocialRelation> findByUserId2(long userId2, int start,
		int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByUserId2(userId2, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByUserId2(long userId2, int start,
		int end, OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUserId2(userId2, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUserId2_First(long userId2,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId2_First(userId2, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUserId2_First(long userId2,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByUserId2_First(userId2, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByUserId2_Last(long userId2,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByUserId2_Last(userId2, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByUserId2_Last(long userId2,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByUserId2_Last(userId2, orderByComparator);
	}

	/**
	* Returns the social relations before and after the current social relation in the ordered set where userId2 = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation[] findByUserId2_PrevAndNext(long relationId,
		long userId2, OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByUserId2_PrevAndNext(relationId, userId2,
			orderByComparator);
	}

	/**
	* Removes all the social relations where userId2 = &#63; from the database.
	*
	* @param userId2 the user id2
	*/
	public static void removeByUserId2(long userId2) {
		getPersistence().removeByUserId2(userId2);
	}

	/**
	* Returns the number of social relations where userId2 = &#63;.
	*
	* @param userId2 the user id2
	* @return the number of matching social relations
	*/
	public static int countByUserId2(long userId2) {
		return getPersistence().countByUserId2(userId2);
	}

	/**
	* Returns all the social relations where type = &#63;.
	*
	* @param type the type
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByType(int type) {
		return getPersistence().findByType(type);
	}

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
	public static List<SocialRelation> findByType(int type, int start, int end) {
		return getPersistence().findByType(type, start, end);
	}

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
	public static List<SocialRelation> findByType(int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByType(int type, int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByType(type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByType_First(int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByType_First(int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByType_Last(int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByType_Last(int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the social relations before and after the current social relation in the ordered set where type = &#63;.
	*
	* @param relationId the primary key of the current social relation
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation[] findByType_PrevAndNext(long relationId,
		int type, OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByType_PrevAndNext(relationId, type, orderByComparator);
	}

	/**
	* Removes all the social relations where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(int type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of social relations where type = &#63;.
	*
	* @param type the type
	* @return the number of matching social relations
	*/
	public static int countByType(int type) {
		return getPersistence().countByType(type);
	}

	/**
	* Returns all the social relations where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByC_T(long companyId, int type) {
		return getPersistence().findByC_T(companyId, type);
	}

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
	public static List<SocialRelation> findByC_T(long companyId, int type,
		int start, int end) {
		return getPersistence().findByC_T(companyId, type, start, end);
	}

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
	public static List<SocialRelation> findByC_T(long companyId, int type,
		int start, int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByC_T(companyId, type, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByC_T(long companyId, int type,
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_T(companyId, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByC_T_First(long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_First(companyId, type, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByC_T_First(long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByC_T_First(companyId, type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByC_T_Last(long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_Last(companyId, type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByC_T_Last(long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByC_T_Last(companyId, type, orderByComparator);
	}

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
	public static SocialRelation[] findByC_T_PrevAndNext(long relationId,
		long companyId, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByC_T_PrevAndNext(relationId, companyId, type,
			orderByComparator);
	}

	/**
	* Removes all the social relations where companyId = &#63; and type = &#63; from the database.
	*
	* @param companyId the company ID
	* @param type the type
	*/
	public static void removeByC_T(long companyId, int type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	* Returns the number of social relations where companyId = &#63; and type = &#63;.
	*
	* @param companyId the company ID
	* @param type the type
	* @return the number of matching social relations
	*/
	public static int countByC_T(long companyId, int type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	* Returns all the social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByU1_U2(long userId1, long userId2) {
		return getPersistence().findByU1_U2(userId1, userId2);
	}

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
	public static List<SocialRelation> findByU1_U2(long userId1, long userId2,
		int start, int end) {
		return getPersistence().findByU1_U2(userId1, userId2, start, end);
	}

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
	public static List<SocialRelation> findByU1_U2(long userId1, long userId2,
		int start, int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByU1_U2(userId1, userId2, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByU1_U2(long userId1, long userId2,
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU1_U2(userId1, userId2, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU1_U2_First(long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_U2_First(userId1, userId2, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU1_U2_First(long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByU1_U2_First(userId1, userId2, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU1_U2_Last(long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_U2_Last(userId1, userId2, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU1_U2_Last(long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByU1_U2_Last(userId1, userId2, orderByComparator);
	}

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
	public static SocialRelation[] findByU1_U2_PrevAndNext(long relationId,
		long userId1, long userId2,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_U2_PrevAndNext(relationId, userId1, userId2,
			orderByComparator);
	}

	/**
	* Removes all the social relations where userId1 = &#63; and userId2 = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	*/
	public static void removeByU1_U2(long userId1, long userId2) {
		getPersistence().removeByU1_U2(userId1, userId2);
	}

	/**
	* Returns the number of social relations where userId1 = &#63; and userId2 = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @return the number of matching social relations
	*/
	public static int countByU1_U2(long userId1, long userId2) {
		return getPersistence().countByU1_U2(userId1, userId2);
	}

	/**
	* Returns all the social relations where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByU1_T(long userId1, int type) {
		return getPersistence().findByU1_T(userId1, type);
	}

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
	public static List<SocialRelation> findByU1_T(long userId1, int type,
		int start, int end) {
		return getPersistence().findByU1_T(userId1, type, start, end);
	}

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
	public static List<SocialRelation> findByU1_T(long userId1, int type,
		int start, int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByU1_T(userId1, type, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByU1_T(long userId1, int type,
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU1_T(userId1, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU1_T_First(long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_T_First(userId1, type, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU1_T_First(long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByU1_T_First(userId1, type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU1_T_Last(long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_T_Last(userId1, type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU1_T_Last(long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByU1_T_Last(userId1, type, orderByComparator);
	}

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
	public static SocialRelation[] findByU1_T_PrevAndNext(long relationId,
		long userId1, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU1_T_PrevAndNext(relationId, userId1, type,
			orderByComparator);
	}

	/**
	* Removes all the social relations where userId1 = &#63; and type = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param type the type
	*/
	public static void removeByU1_T(long userId1, int type) {
		getPersistence().removeByU1_T(userId1, type);
	}

	/**
	* Returns the number of social relations where userId1 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param type the type
	* @return the number of matching social relations
	*/
	public static int countByU1_T(long userId1, int type) {
		return getPersistence().countByU1_T(userId1, type);
	}

	/**
	* Returns all the social relations where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relations
	*/
	public static List<SocialRelation> findByU2_T(long userId2, int type) {
		return getPersistence().findByU2_T(userId2, type);
	}

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
	public static List<SocialRelation> findByU2_T(long userId2, int type,
		int start, int end) {
		return getPersistence().findByU2_T(userId2, type, start, end);
	}

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
	public static List<SocialRelation> findByU2_T(long userId2, int type,
		int start, int end, OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .findByU2_T(userId2, type, start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findByU2_T(long userId2, int type,
		int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU2_T(userId2, type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU2_T_First(long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU2_T_First(userId2, type, orderByComparator);
	}

	/**
	* Returns the first social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU2_T_First(long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByU2_T_First(userId2, type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU2_T_Last(long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU2_T_Last(userId2, type, orderByComparator);
	}

	/**
	* Returns the last social relation in the ordered set where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU2_T_Last(long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence()
				   .fetchByU2_T_Last(userId2, type, orderByComparator);
	}

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
	public static SocialRelation[] findByU2_T_PrevAndNext(long relationId,
		long userId2, int type,
		OrderByComparator<SocialRelation> orderByComparator)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence()
				   .findByU2_T_PrevAndNext(relationId, userId2, type,
			orderByComparator);
	}

	/**
	* Removes all the social relations where userId2 = &#63; and type = &#63; from the database.
	*
	* @param userId2 the user id2
	* @param type the type
	*/
	public static void removeByU2_T(long userId2, int type) {
		getPersistence().removeByU2_T(userId2, type);
	}

	/**
	* Returns the number of social relations where userId2 = &#63; and type = &#63;.
	*
	* @param userId2 the user id2
	* @param type the type
	* @return the number of matching social relations
	*/
	public static int countByU2_T(long userId2, int type) {
		return getPersistence().countByU2_T(userId2, type);
	}

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or throws a {@link NoSuchRelationException} if it could not be found.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relation
	* @throws NoSuchRelationException if a matching social relation could not be found
	*/
	public static SocialRelation findByU1_U2_T(long userId1, long userId2,
		int type) throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByU1_U2_T(userId1, userId2, type);
	}

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU1_U2_T(long userId1, long userId2,
		int type) {
		return getPersistence().fetchByU1_U2_T(userId1, userId2, type);
	}

	/**
	* Returns the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching social relation, or <code>null</code> if a matching social relation could not be found
	*/
	public static SocialRelation fetchByU1_U2_T(long userId1, long userId2,
		int type, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByU1_U2_T(userId1, userId2, type, retrieveFromCache);
	}

	/**
	* Removes the social relation where userId1 = &#63; and userId2 = &#63; and type = &#63; from the database.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the social relation that was removed
	*/
	public static SocialRelation removeByU1_U2_T(long userId1, long userId2,
		int type) throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().removeByU1_U2_T(userId1, userId2, type);
	}

	/**
	* Returns the number of social relations where userId1 = &#63; and userId2 = &#63; and type = &#63;.
	*
	* @param userId1 the user id1
	* @param userId2 the user id2
	* @param type the type
	* @return the number of matching social relations
	*/
	public static int countByU1_U2_T(long userId1, long userId2, int type) {
		return getPersistence().countByU1_U2_T(userId1, userId2, type);
	}

	/**
	* Caches the social relation in the entity cache if it is enabled.
	*
	* @param socialRelation the social relation
	*/
	public static void cacheResult(SocialRelation socialRelation) {
		getPersistence().cacheResult(socialRelation);
	}

	/**
	* Caches the social relations in the entity cache if it is enabled.
	*
	* @param socialRelations the social relations
	*/
	public static void cacheResult(List<SocialRelation> socialRelations) {
		getPersistence().cacheResult(socialRelations);
	}

	/**
	* Creates a new social relation with the primary key. Does not add the social relation to the database.
	*
	* @param relationId the primary key for the new social relation
	* @return the new social relation
	*/
	public static SocialRelation create(long relationId) {
		return getPersistence().create(relationId);
	}

	/**
	* Removes the social relation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation that was removed
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation remove(long relationId)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().remove(relationId);
	}

	public static SocialRelation updateImpl(SocialRelation socialRelation) {
		return getPersistence().updateImpl(socialRelation);
	}

	/**
	* Returns the social relation with the primary key or throws a {@link NoSuchRelationException} if it could not be found.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation
	* @throws NoSuchRelationException if a social relation with the primary key could not be found
	*/
	public static SocialRelation findByPrimaryKey(long relationId)
		throws com.liferay.portlet.social.NoSuchRelationException {
		return getPersistence().findByPrimaryKey(relationId);
	}

	/**
	* Returns the social relation with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param relationId the primary key of the social relation
	* @return the social relation, or <code>null</code> if a social relation with the primary key could not be found
	*/
	public static SocialRelation fetchByPrimaryKey(long relationId) {
		return getPersistence().fetchByPrimaryKey(relationId);
	}

	public static java.util.Map<java.io.Serializable, SocialRelation> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the social relations.
	*
	* @return the social relations
	*/
	public static List<SocialRelation> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<SocialRelation> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<SocialRelation> findAll(int start, int end,
		OrderByComparator<SocialRelation> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<SocialRelation> findAll(int start, int end,
		OrderByComparator<SocialRelation> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the social relations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social relations.
	*
	* @return the number of social relations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SocialRelationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialRelationPersistence)PortalBeanLocatorUtil.locate(SocialRelationPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialRelationUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static SocialRelationPersistence _persistence;
}