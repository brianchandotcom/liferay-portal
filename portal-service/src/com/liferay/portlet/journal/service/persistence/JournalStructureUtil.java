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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.journal.model.JournalStructure;

import java.util.List;

/**
 * The persistence utility for the journal structure service. This utility wraps {@link JournalStructurePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalStructurePersistence
 * @see JournalStructurePersistenceImpl
 * @generated
 */
public class JournalStructureUtil {
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
	public static void clearCache(JournalStructure journalStructure) {
		getPersistence().clearCache(journalStructure);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalStructure> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalStructure> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalStructure> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalStructure update(JournalStructure journalStructure,
		boolean merge) throws SystemException {
		return getPersistence().update(journalStructure, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static JournalStructure update(JournalStructure journalStructure,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(journalStructure, merge, serviceContext);
	}

	/**
	* Caches the journal structure in the entity cache if it is enabled.
	*
	* @param journalStructure the journal structure
	*/
	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalStructure journalStructure) {
		getPersistence().cacheResult(journalStructure);
	}

	/**
	* Caches the journal structures in the entity cache if it is enabled.
	*
	* @param journalStructures the journal structures
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalStructure> journalStructures) {
		getPersistence().cacheResult(journalStructures);
	}

	/**
	* Creates a new journal structure with the primary key. Does not add the journal structure to the database.
	*
	* @param id the primary key for the new journal structure
	* @return the new journal structure
	*/
	public static com.liferay.portlet.journal.model.JournalStructure create(
		long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the journal structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal structure
	* @return the journal structure that was removed
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure remove(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portlet.journal.model.JournalStructure updateImpl(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalStructure, merge);
	}

	/**
	* Returns the journal structure with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchStructureException} if it could not be found.
	*
	* @param id the primary key of the journal structure
	* @return the journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Returns the journal structure with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal structure
	* @return the journal structure, or <code>null</code> if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(id);
	}

	/**
	* Returns all the journal structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the journal structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first journal structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last journal structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal structure
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByUuid_PrevAndNext(id, uuid, orderByComparator);
	}

	/**
	* Returns the journal structure where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchStructureException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the journal structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(id, groupId, orderByComparator);
	}

	/**
	* Returns all the journal structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal structures that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set of journal structures that the user has permission to view where groupId = &#63;.
	*
	* @param id the primary key of the current journal structure
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] filterFindByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(id, groupId,
			orderByComparator);
	}

	/**
	* Returns all the journal structures where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Returns a range of all the journal structures where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal structure in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the last journal structure in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal structure
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] findByStructureId_PrevAndNext(
		long id, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(id, structureId,
			orderByComparator);
	}

	/**
	* Returns all the journal structures where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @return the matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByParentStructureId(
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByParentStructureId(parentStructureId);
	}

	/**
	* Returns a range of all the journal structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByParentStructureId(
		java.lang.String parentStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParentStructureId(parentStructureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByParentStructureId(
		java.lang.String parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParentStructureId(parentStructureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first journal structure in the ordered set where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByParentStructureId_First(
		java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByParentStructureId_First(parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the last journal structure in the ordered set where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByParentStructureId_Last(
		java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByParentStructureId_Last(parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set where parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal structure
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] findByParentStructureId_PrevAndNext(
		long id, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByParentStructureId_PrevAndNext(id, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the journal structure where groupId = &#63; and structureId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchStructureException} if it could not be found.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().findByG_S(groupId, structureId);
	}

	/**
	* Returns the journal structure where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure fetchByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_S(groupId, structureId);
	}

	/**
	* Returns the journal structure where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure fetchByG_S(
		long groupId, java.lang.String structureId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S(groupId, structureId, retrieveFromCache);
	}

	/**
	* Returns all the journal structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByG_P(
		long groupId, java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentStructureId);
	}

	/**
	* Returns a range of all the journal structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByG_P(
		long groupId, java.lang.String parentStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentStructureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findByG_P(
		long groupId, java.lang.String parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentStructureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByG_P_First(
		long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_First(groupId, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns the last journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure findByG_P_Last(
		long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentStructureId, orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal structure
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] findByG_P_PrevAndNext(
		long id, long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .findByG_P_PrevAndNext(id, groupId, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns all the journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> filterFindByG_P(
		long groupId, java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentStructureId);
	}

	/**
	* Returns a range of all the journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> filterFindByG_P(
		long groupId, java.lang.String parentStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentStructureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal structures that the user has permissions to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> filterFindByG_P(
		long groupId, java.lang.String parentStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentStructureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the journal structures before and after the current journal structure in the ordered set of journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param id the primary key of the current journal structure
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal structure
	* @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure[] filterFindByG_P_PrevAndNext(
		long id, long groupId, java.lang.String parentStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(id, groupId, parentStructureId,
			orderByComparator);
	}

	/**
	* Returns all the journal structures.
	*
	* @return the journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @return the range of journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal structures
	* @param end the upper bound of the range of journal structures (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalStructure> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the journal structures where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the journal structure where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal structure that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the journal structures where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the journal structures where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Removes all the journal structures where parentStructureId = &#63; from the database.
	*
	* @param parentStructureId the parent structure ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByParentStructureId(
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByParentStructureId(parentStructureId);
	}

	/**
	* Removes the journal structure where groupId = &#63; and structureId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the journal structure that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalStructure removeByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchStructureException {
		return getPersistence().removeByG_S(groupId, structureId);
	}

	/**
	* Removes all the journal structures where groupId = &#63; and parentStructureId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P(long groupId,
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, parentStructureId);
	}

	/**
	* Removes all the journal structures from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal structures where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of journal structures where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of journal structures where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of journal structures that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of journal structures where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Returns the number of journal structures where parentStructureId = &#63;.
	*
	* @param parentStructureId the parent structure ID
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByParentStructureId(
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByParentStructureId(parentStructureId);
	}

	/**
	* Returns the number of journal structures where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, structureId);
	}

	/**
	* Returns the number of journal structures where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the number of matching journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId,
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentStructureId);
	}

	/**
	* Returns the number of journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	*
	* @param groupId the group ID
	* @param parentStructureId the parent structure ID
	* @return the number of matching journal structures that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId,
		java.lang.String parentStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentStructureId);
	}

	/**
	* Returns the number of journal structures.
	*
	* @return the number of journal structures
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalStructurePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalStructurePersistence)PortalBeanLocatorUtil.locate(JournalStructurePersistence.class.getName());

			ReferenceRegistry.registerReference(JournalStructureUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(JournalStructurePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(JournalStructureUtil.class,
			"_persistence");
	}

	private static JournalStructurePersistence _persistence;
}