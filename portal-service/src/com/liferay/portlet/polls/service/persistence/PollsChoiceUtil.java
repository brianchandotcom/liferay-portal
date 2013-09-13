/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.polls.model.PollsChoice;

import java.util.List;

/**
 * The persistence utility for the polls choice service. This utility wraps {@link PollsChoicePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoicePersistence
 * @see PollsChoicePersistenceImpl
 * @generated
 */
public class PollsChoiceUtil {
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
	public static void clearCache(PollsChoice pollsChoice) {
		getPersistence().clearCache(pollsChoice);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PollsChoice> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PollsChoice> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PollsChoice> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PollsChoice update(PollsChoice pollsChoice)
		throws SystemException {
		return getPersistence().update(pollsChoice);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PollsChoice update(PollsChoice pollsChoice,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(pollsChoice, serviceContext);
	}

	/**
	* Returns all the polls choices where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the polls choices where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @return the range of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the polls choices where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first polls choice in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first polls choice in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last polls choice in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last polls choice in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the polls choices before and after the current polls choice in the ordered set where uuid = &#63;.
	*
	* @param choiceId the primary key of the current polls choice
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice[] findByUuid_PrevAndNext(
		long choiceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByUuid_PrevAndNext(choiceId, uuid, orderByComparator);
	}

	/**
	* Removes all the polls choices where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of polls choices where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the polls choice where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.polls.NoSuchChoiceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the polls choice where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the polls choice where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the polls choice where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the polls choice that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of polls choices where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the polls choices where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the polls choices where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @return the range of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the polls choices before and after the current polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param choiceId the primary key of the current polls choice
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice[] findByUuid_C_PrevAndNext(
		long choiceId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(choiceId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the polls choices where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of polls choices where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the polls choices where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId);
	}

	/**
	* Returns a range of all the polls choices where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @return the range of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByQuestionId(questionId, start, end);
	}

	/**
	* Returns an ordered range of all the polls choices where questionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param questionId the question ID
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByQuestionId(questionId, start, end, orderByComparator);
	}

	/**
	* Returns the first polls choice in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByQuestionId_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByQuestionId_First(questionId, orderByComparator);
	}

	/**
	* Returns the first polls choice in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByQuestionId_First(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByQuestionId_First(questionId, orderByComparator);
	}

	/**
	* Returns the last polls choice in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByQuestionId_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByQuestionId_Last(questionId, orderByComparator);
	}

	/**
	* Returns the last polls choice in the ordered set where questionId = &#63;.
	*
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByQuestionId_Last(
		long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByQuestionId_Last(questionId, orderByComparator);
	}

	/**
	* Returns the polls choices before and after the current polls choice in the ordered set where questionId = &#63;.
	*
	* @param choiceId the primary key of the current polls choice
	* @param questionId the question ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice[] findByQuestionId_PrevAndNext(
		long choiceId, long questionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence()
				   .findByQuestionId_PrevAndNext(choiceId, questionId,
			orderByComparator);
	}

	/**
	* Removes all the polls choices where questionId = &#63; from the database.
	*
	* @param questionId the question ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByQuestionId(questionId);
	}

	/**
	* Returns the number of polls choices where questionId = &#63;.
	*
	* @param questionId the question ID
	* @return the number of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static int countByQuestionId(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQuestionId(questionId);
	}

	/**
	* Returns the polls choice where questionId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.polls.NoSuchChoiceException} if it could not be found.
	*
	* @param questionId the question ID
	* @param name the name
	* @return the matching polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByQ_N(questionId, name);
	}

	/**
	* Returns the polls choice where questionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param questionId the question ID
	* @param name the name
	* @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByQ_N(questionId, name);
	}

	/**
	* Returns the polls choice where questionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param questionId the question ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByQ_N(
		long questionId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByQ_N(questionId, name, retrieveFromCache);
	}

	/**
	* Removes the polls choice where questionId = &#63; and name = &#63; from the database.
	*
	* @param questionId the question ID
	* @param name the name
	* @return the polls choice that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice removeByQ_N(
		long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().removeByQ_N(questionId, name);
	}

	/**
	* Returns the number of polls choices where questionId = &#63; and name = &#63;.
	*
	* @param questionId the question ID
	* @param name the name
	* @return the number of matching polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static int countByQ_N(long questionId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByQ_N(questionId, name);
	}

	/**
	* Caches the polls choice in the entity cache if it is enabled.
	*
	* @param pollsChoice the polls choice
	*/
	public static void cacheResult(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice) {
		getPersistence().cacheResult(pollsChoice);
	}

	/**
	* Caches the polls choices in the entity cache if it is enabled.
	*
	* @param pollsChoices the polls choices
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.polls.model.PollsChoice> pollsChoices) {
		getPersistence().cacheResult(pollsChoices);
	}

	/**
	* Creates a new polls choice with the primary key. Does not add the polls choice to the database.
	*
	* @param choiceId the primary key for the new polls choice
	* @return the new polls choice
	*/
	public static com.liferay.portlet.polls.model.PollsChoice create(
		long choiceId) {
		return getPersistence().create(choiceId);
	}

	/**
	* Removes the polls choice with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param choiceId the primary key of the polls choice
	* @return the polls choice that was removed
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice remove(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().remove(choiceId);
	}

	public static com.liferay.portlet.polls.model.PollsChoice updateImpl(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(pollsChoice);
	}

	/**
	* Returns the polls choice with the primary key or throws a {@link com.liferay.portlet.polls.NoSuchChoiceException} if it could not be found.
	*
	* @param choiceId the primary key of the polls choice
	* @return the polls choice
	* @throws com.liferay.portlet.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice findByPrimaryKey(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.polls.NoSuchChoiceException {
		return getPersistence().findByPrimaryKey(choiceId);
	}

	/**
	* Returns the polls choice with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param choiceId the primary key of the polls choice
	* @return the polls choice, or <code>null</code> if a polls choice with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.polls.model.PollsChoice fetchByPrimaryKey(
		long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(choiceId);
	}

	/**
	* Returns all the polls choices.
	*
	* @return the polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the polls choices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @return the range of polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the polls choices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of polls choices
	* @param end the upper bound of the range of polls choices (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the polls choices from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of polls choices.
	*
	* @return the number of polls choices
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PollsChoicePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PollsChoicePersistence)PortalBeanLocatorUtil.locate(PollsChoicePersistence.class.getName());

			ReferenceRegistry.registerReference(PollsChoiceUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(PollsChoicePersistence persistence) {
	}

	private static PollsChoicePersistence _persistence;
}