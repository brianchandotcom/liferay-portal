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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;

import java.util.List;

/**
 * The persistence utility for the d d m structure link service. This utility wraps {@link DDMStructureLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistence
 * @see DDMStructureLinkPersistenceImpl
 * @generated
 */
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
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink)
		throws SystemException {
		return getPersistence().update(ddmStructureLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMStructureLink update(DDMStructureLink ddmStructureLink,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ddmStructureLink, serviceContext);
	}

	/**
	* Returns all the d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	* Returns a range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] findByClassNameId_PrevAndNext(
		long structureLinkId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(structureLinkId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structure links where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	* Returns the number of d d m structure links where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	* Returns the d d m structure link where classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param classPK the class p k
	* @return the matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().findByClassPK(classPK);
	}

	/**
	* Returns the d d m structure link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classPK the class p k
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByClassPK(classPK);
	}

	/**
	* Returns the d d m structure link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByClassPK(
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByClassPK(classPK, retrieveFromCache);
	}

	/**
	* Removes the d d m structure link where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	* @return the d d m structure link that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink removeByClassPK(
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().removeByClassPK(classPK);
	}

	/**
	* Returns the number of d d m structure links where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByClassPK(long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	* Returns all the d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Returns a range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the first d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the last d d m structure link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(structureLinkId, structureId,
			orderByComparator);
	}

	/**
	* Removes all the d d m structure links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Returns the number of d d m structure links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Caches the d d m structure link in the entity cache if it is enabled.
	*
	* @param ddmStructureLink the d d m structure link
	*/
	public static void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink) {
		getPersistence().cacheResult(ddmStructureLink);
	}

	/**
	* Caches the d d m structure links in the entity cache if it is enabled.
	*
	* @param ddmStructureLinks the d d m structure links
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> ddmStructureLinks) {
		getPersistence().cacheResult(ddmStructureLinks);
	}

	/**
	* Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	*
	* @param structureLinkId the primary key for the new d d m structure link
	* @return the new d d m structure link
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink create(
		long structureLinkId) {
		return getPersistence().create(structureLinkId);
	}

	/**
	* Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink remove(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().remove(structureLinkId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ddmStructureLink);
	}

	/**
	* Returns the d d m structure link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink findByPrimaryKey(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException {
		return getPersistence().findByPrimaryKey(structureLinkId);
	}

	/**
	* Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureLinkId the primary key of the d d m structure link
	* @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink fetchByPrimaryKey(
		long structureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(structureLinkId);
	}

	/**
	* Returns all the d d m structure links.
	*
	* @return the d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @return the range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure links
	* @param end the upper bound of the range of d d m structure links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d d m structure links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m structure links.
	*
	* @return the number of d d m structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DDMStructureLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DDMStructureLinkPersistence)PortalBeanLocatorUtil.locate(DDMStructureLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(DDMStructureLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(DDMStructureLinkPersistence persistence) {
	}

	private static DDMStructureLinkPersistence _persistence;
}