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

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d m structure link service. This utility wraps {@link com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStructureLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class DDMStructureLinkUtil {
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
	public static void clearCache(DDMStructureLink ddmStructureLink) {
		getPersistence().clearCache(ddmStructureLink);
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
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink) {
		return getPersistence().update(ddmStructureLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink,
		ServiceContext serviceContext) {
		return getPersistence().update(ddmStructureLink, serviceContext);
	}

	/**
	* Returns all the d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching d d m structure links
	*/
	public static List<DDMStructureLink> findByClassNameId(long classNameId) {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	* Returns a range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByClassNameId(long classNameId,
		int start, int end) {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByClassNameId(long classNameId,
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByClassNameId(long classNameId,
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByClassNameId_First(long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByClassNameId_First(long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByClassNameId_Last(long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByClassNameId_Last(long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public static DDMStructureLink[] findByClassNameId_PrevAndNext(
		long structureLinkId, long classNameId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(structureLinkId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structure links where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public static void removeByClassNameId(long classNameId) {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	* Returns the number of d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching d d m structure links
	*/
	public static int countByClassNameId(long classNameId) {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	* Returns all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m structure links
	*/
	public static List<DDMStructureLink> findByStructureId(long structureId) {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Returns a range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end) {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByStructureId(structureId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByStructureId_First(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByStructureId_First(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .fetchByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByStructureId_Last(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByStructureId_Last(long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .fetchByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public static DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(structureLinkId, structureId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structure links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	*/
	public static void removeByStructureId(long structureId) {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Returns the number of d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m structure links
	*/
	public static int countByStructureId(long structureId) {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Returns all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m structure links
	*/
	public static List<DDMStructureLink> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m structure links
	*/
	public static List<DDMStructureLink> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByC_C_First(long classNameId,
		long classPK, OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param structureLinkId the primary key of the current d d m structure link
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public static DDMStructureLink[] findByC_C_PrevAndNext(
		long structureLinkId, long classNameId, long classPK,
		OrderByComparator<DDMStructureLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence()
				   .findByC_C_PrevAndNext(structureLinkId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the d d m structure links where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of d d m structure links where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m structure links
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the matching d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink findByC_C_S(long classNameId, long classPK,
		long structureId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence().findByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByC_C_S(long classNameId, long classPK,
		long structureId) {
		return getPersistence().fetchByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Returns the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	*/
	public static DDMStructureLink fetchByC_C_S(long classNameId, long classPK,
		long structureId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_S(classNameId, classPK, structureId,
			retrieveFromCache);
	}

	/**
	* Removes the d d m structure link where classNameId = &#63; and classPK = &#63; and structureId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the d d m structure link that was removed
	*/
	public static DDMStructureLink removeByC_C_S(long classNameId,
		long classPK, long structureId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence().removeByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Returns the number of d d m structure links where classNameId = &#63; and classPK = &#63; and structureId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param structureId the structure ID
	* @return the number of matching d d m structure links
	*/
	public static int countByC_C_S(long classNameId, long classPK,
		long structureId) {
		return getPersistence().countByC_C_S(classNameId, classPK, structureId);
	}

	/**
	* Caches the d d m structure link in the entity cache if it is enabled.
	*
	* @param ddmStructureLink the d d m structure link
	*/
	public static void cacheResult(DDMStructureLink ddmStructureLink) {
		getPersistence().cacheResult(ddmStructureLink);
	}

	/**
	* Caches the d d m structure links in the entity cache if it is enabled.
	*
	* @param ddmStructureLinks the d d m structure links
	*/
	public static void cacheResult(List<DDMStructureLink> ddmStructureLinks) {
		getPersistence().cacheResult(ddmStructureLinks);
	}

	/**
	* Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	*
	* @param structureLinkId the primary key for the new d d m structure link
	* @return the new d d m structure link
	*/
	public static DDMStructureLink create(long structureLinkId) {
		return getPersistence().create(structureLinkId);
	}

	/**
	* Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public static DDMStructureLink remove(long structureLinkId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence().remove(structureLinkId);
	}

	public static DDMStructureLink updateImpl(DDMStructureLink ddmStructureLink) {
		return getPersistence().updateImpl(ddmStructureLink);
	}

	/**
	* Returns the d d m structure link with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link
	* @throws com.liferay.dynamic.data.mapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	*/
	public static DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException {
		return getPersistence().findByPrimaryKey(structureLinkId);
	}

	/**
	* Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	*/
	public static DDMStructureLink fetchByPrimaryKey(long structureLinkId) {
		return getPersistence().fetchByPrimaryKey(structureLinkId);
	}

	public static java.util.Map<java.io.Serializable, DDMStructureLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m structure links.
	*
	* @return the d d m structure links
	*/
	public static List<DDMStructureLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of d d m structure links
	*/
	public static List<DDMStructureLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure links
	*/
	public static List<DDMStructureLink> findAll(int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m structure links
	*/
	public static List<DDMStructureLink> findAll(int start, int end,
		OrderByComparator<DDMStructureLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m structure links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m structure links.
	*
	* @return the number of d d m structure links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMStructureLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMStructureLinkPersistence, DDMStructureLinkPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDMStructureLinkPersistence.class);
}