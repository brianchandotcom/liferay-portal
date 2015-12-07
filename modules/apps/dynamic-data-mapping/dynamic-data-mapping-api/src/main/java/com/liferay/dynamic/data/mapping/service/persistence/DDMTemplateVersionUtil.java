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

import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d m template version service. This utility wraps {@link com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplateVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplateVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateVersionUtil {
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
	public static void clearCache(DDMTemplateVersion ddmTemplateVersion) {
		getPersistence().clearCache(ddmTemplateVersion);
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
	public static List<DDMTemplateVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMTemplateVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMTemplateVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMTemplateVersion update(
		DDMTemplateVersion ddmTemplateVersion) {
		return getPersistence().update(ddmTemplateVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMTemplateVersion update(
		DDMTemplateVersion ddmTemplateVersion, ServiceContext serviceContext) {
		return getPersistence().update(ddmTemplateVersion, serviceContext);
	}

	/**
	* Returns all the d d m template versions where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByTemplateId(long templateId) {
		return getPersistence().findByTemplateId(templateId);
	}

	/**
	* Returns a range of all the d d m template versions where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end) {
		return getPersistence().findByTemplateId(templateId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .findByTemplateId(templateId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByTemplateId(long templateId,
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTemplateId(templateId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion findByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence()
				   .findByTemplateId_First(templateId, orderByComparator);
	}

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion fetchByTemplateId_First(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .fetchByTemplateId_First(templateId, orderByComparator);
	}

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion findByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence()
				   .findByTemplateId_Last(templateId, orderByComparator);
	}

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion fetchByTemplateId_Last(long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .fetchByTemplateId_Last(templateId, orderByComparator);
	}

	/**
	* Returns the d d m template versions before and after the current d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateVersionId the primary key of the current d d m template version
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public static DDMTemplateVersion[] findByTemplateId_PrevAndNext(
		long templateVersionId, long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence()
				   .findByTemplateId_PrevAndNext(templateVersionId, templateId,
			orderByComparator);
	}

	/**
	* Removes all the d d m template versions where templateId = &#63; from the database.
	*
	* @param templateId the template ID
	*/
	public static void removeByTemplateId(long templateId) {
		getPersistence().removeByTemplateId(templateId);
	}

	/**
	* Returns the number of d d m template versions where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the number of matching d d m template versions
	*/
	public static int countByTemplateId(long templateId) {
		return getPersistence().countByTemplateId(templateId);
	}

	/**
	* Returns the d d m template version where templateId = &#63; and version = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException} if it could not be found.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the matching d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion findByT_V(long templateId,
		java.lang.String version)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence().findByT_V(templateId, version);
	}

	/**
	* Returns the d d m template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion fetchByT_V(long templateId,
		java.lang.String version) {
		return getPersistence().fetchByT_V(templateId, version);
	}

	/**
	* Returns the d d m template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param templateId the template ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion fetchByT_V(long templateId,
		java.lang.String version, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByT_V(templateId, version, retrieveFromCache);
	}

	/**
	* Removes the d d m template version where templateId = &#63; and version = &#63; from the database.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the d d m template version that was removed
	*/
	public static DDMTemplateVersion removeByT_V(long templateId,
		java.lang.String version)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence().removeByT_V(templateId, version);
	}

	/**
	* Returns the number of d d m template versions where templateId = &#63; and version = &#63;.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the number of matching d d m template versions
	*/
	public static int countByT_V(long templateId, java.lang.String version) {
		return getPersistence().countByT_V(templateId, version);
	}

	/**
	* Returns all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @return the matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByT_S(long templateId, int status) {
		return getPersistence().findByT_S(templateId, status);
	}

	/**
	* Returns a range of all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param status the status
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByT_S(long templateId,
		int status, int start, int end) {
		return getPersistence().findByT_S(templateId, status, start, end);
	}

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param status the status
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByT_S(long templateId,
		int status, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .findByT_S(templateId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param status the status
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m template versions
	*/
	public static List<DDMTemplateVersion> findByT_S(long templateId,
		int status, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByT_S(templateId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion findByT_S_First(long templateId,
		int status, OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence()
				   .findByT_S_First(templateId, status, orderByComparator);
	}

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion fetchByT_S_First(long templateId,
		int status, OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .fetchByT_S_First(templateId, status, orderByComparator);
	}

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion findByT_S_Last(long templateId,
		int status, OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence()
				   .findByT_S_Last(templateId, status, orderByComparator);
	}

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public static DDMTemplateVersion fetchByT_S_Last(long templateId,
		int status, OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence()
				   .fetchByT_S_Last(templateId, status, orderByComparator);
	}

	/**
	* Returns the d d m template versions before and after the current d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateVersionId the primary key of the current d d m template version
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public static DDMTemplateVersion[] findByT_S_PrevAndNext(
		long templateVersionId, long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence()
				   .findByT_S_PrevAndNext(templateVersionId, templateId,
			status, orderByComparator);
	}

	/**
	* Removes all the d d m template versions where templateId = &#63; and status = &#63; from the database.
	*
	* @param templateId the template ID
	* @param status the status
	*/
	public static void removeByT_S(long templateId, int status) {
		getPersistence().removeByT_S(templateId, status);
	}

	/**
	* Returns the number of d d m template versions where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @return the number of matching d d m template versions
	*/
	public static int countByT_S(long templateId, int status) {
		return getPersistence().countByT_S(templateId, status);
	}

	/**
	* Caches the d d m template version in the entity cache if it is enabled.
	*
	* @param ddmTemplateVersion the d d m template version
	*/
	public static void cacheResult(DDMTemplateVersion ddmTemplateVersion) {
		getPersistence().cacheResult(ddmTemplateVersion);
	}

	/**
	* Caches the d d m template versions in the entity cache if it is enabled.
	*
	* @param ddmTemplateVersions the d d m template versions
	*/
	public static void cacheResult(List<DDMTemplateVersion> ddmTemplateVersions) {
		getPersistence().cacheResult(ddmTemplateVersions);
	}

	/**
	* Creates a new d d m template version with the primary key. Does not add the d d m template version to the database.
	*
	* @param templateVersionId the primary key for the new d d m template version
	* @return the new d d m template version
	*/
	public static DDMTemplateVersion create(long templateVersionId) {
		return getPersistence().create(templateVersionId);
	}

	/**
	* Removes the d d m template version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public static DDMTemplateVersion remove(long templateVersionId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence().remove(templateVersionId);
	}

	public static DDMTemplateVersion updateImpl(
		DDMTemplateVersion ddmTemplateVersion) {
		return getPersistence().updateImpl(ddmTemplateVersion);
	}

	/**
	* Returns the d d m template version with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException} if it could not be found.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public static DDMTemplateVersion findByPrimaryKey(long templateVersionId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException {
		return getPersistence().findByPrimaryKey(templateVersionId);
	}

	/**
	* Returns the d d m template version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version, or <code>null</code> if a d d m template version with the primary key could not be found
	*/
	public static DDMTemplateVersion fetchByPrimaryKey(long templateVersionId) {
		return getPersistence().fetchByPrimaryKey(templateVersionId);
	}

	public static java.util.Map<java.io.Serializable, DDMTemplateVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m template versions.
	*
	* @return the d d m template versions
	*/
	public static List<DDMTemplateVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of d d m template versions
	*/
	public static List<DDMTemplateVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m template versions
	*/
	public static List<DDMTemplateVersion> findAll(int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m template versions
	*/
	public static List<DDMTemplateVersion> findAll(int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m template versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m template versions.
	*
	* @return the number of d d m template versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMTemplateVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(DDMTemplateVersionPersistence persistence) {
	}

	private static ServiceTracker<DDMTemplateVersionPersistence, DDMTemplateVersionPersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDMTemplateVersionPersistence.class);
}