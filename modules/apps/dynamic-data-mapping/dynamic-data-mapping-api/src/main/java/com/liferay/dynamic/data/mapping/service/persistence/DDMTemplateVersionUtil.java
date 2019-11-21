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

import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ddm template version service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplateVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionPersistence
 * @generated
 */
public class DDMTemplateVersionUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(DDMTemplateVersion ddmTemplateVersion) {
		getPersistence().clearCache(ddmTemplateVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, DDMTemplateVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMTemplateVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMTemplateVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMTemplateVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMTemplateVersion update(
		DDMTemplateVersion ddmTemplateVersion) {

		return getPersistence().update(ddmTemplateVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMTemplateVersion update(
		DDMTemplateVersion ddmTemplateVersion, ServiceContext serviceContext) {

		return getPersistence().update(ddmTemplateVersion, serviceContext);
	}

	/**
	 * Returns all the ddm template versions where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByTemplateId(long templateId) {
		return getPersistence().findByTemplateId(templateId);
	}

	/**
	 * Returns a range of all the ddm template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @return the range of matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByTemplateId(
		long templateId, int start, int end) {

		return getPersistence().findByTemplateId(templateId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByTemplateId(
		long templateId, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().findByTemplateId(
			templateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByTemplateId(
		long templateId, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByTemplateId(
			templateId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion findByTemplateId_First(
			long templateId,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByTemplateId_First(
			templateId, orderByComparator);
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion fetchByTemplateId_First(
		long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().fetchByTemplateId_First(
			templateId, orderByComparator);
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion findByTemplateId_Last(
			long templateId,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByTemplateId_Last(
			templateId, orderByComparator);
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion fetchByTemplateId_Last(
		long templateId,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().fetchByTemplateId_Last(
			templateId, orderByComparator);
	}

	/**
	 * Returns the ddm template versions before and after the current ddm template version in the ordered set where templateId = &#63;.
	 *
	 * @param templateVersionId the primary key of the current ddm template version
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	public static DDMTemplateVersion[] findByTemplateId_PrevAndNext(
			long templateVersionId, long templateId,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByTemplateId_PrevAndNext(
			templateVersionId, templateId, orderByComparator);
	}

	/**
	 * Removes all the ddm template versions where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 */
	public static void removeByTemplateId(long templateId) {
		getPersistence().removeByTemplateId(templateId);
	}

	/**
	 * Returns the number of ddm template versions where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching ddm template versions
	 */
	public static int countByTemplateId(long templateId) {
		return getPersistence().countByTemplateId(templateId);
	}

	/**
	 * Returns the ddm template version where templateId = &#63; and version = &#63; or throws a <code>NoSuchTemplateVersionException</code> if it could not be found.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion findByT_V(long templateId, String version)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByT_V(templateId, version);
	}

	/**
	 * Returns the ddm template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion fetchByT_V(
		long templateId, String version) {

		return getPersistence().fetchByT_V(templateId, version);
	}

	/**
	 * Returns the ddm template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion fetchByT_V(
		long templateId, String version, boolean useFinderCache) {

		return getPersistence().fetchByT_V(templateId, version, useFinderCache);
	}

	/**
	 * Removes the ddm template version where templateId = &#63; and version = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the ddm template version that was removed
	 */
	public static DDMTemplateVersion removeByT_V(
			long templateId, String version)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().removeByT_V(templateId, version);
	}

	/**
	 * Returns the number of ddm template versions where templateId = &#63; and version = &#63;.
	 *
	 * @param templateId the template ID
	 * @param version the version
	 * @return the number of matching ddm template versions
	 */
	public static int countByT_V(long templateId, String version) {
		return getPersistence().countByT_V(templateId, version);
	}

	/**
	 * Returns all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @return the matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByT_S(
		long templateId, int status) {

		return getPersistence().findByT_S(templateId, status);
	}

	/**
	 * Returns a range of all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @return the range of matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByT_S(
		long templateId, int status, int start, int end) {

		return getPersistence().findByT_S(templateId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByT_S(
		long templateId, int status, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().findByT_S(
			templateId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm template versions
	 */
	public static List<DDMTemplateVersion> findByT_S(
		long templateId, int status, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByT_S(
			templateId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion findByT_S_First(
			long templateId, int status,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByT_S_First(
			templateId, status, orderByComparator);
	}

	/**
	 * Returns the first ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion fetchByT_S_First(
		long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().fetchByT_S_First(
			templateId, status, orderByComparator);
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version
	 * @throws NoSuchTemplateVersionException if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion findByT_S_Last(
			long templateId, int status,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByT_S_Last(
			templateId, status, orderByComparator);
	}

	/**
	 * Returns the last ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template version, or <code>null</code> if a matching ddm template version could not be found
	 */
	public static DDMTemplateVersion fetchByT_S_Last(
		long templateId, int status,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().fetchByT_S_Last(
			templateId, status, orderByComparator);
	}

	/**
	 * Returns the ddm template versions before and after the current ddm template version in the ordered set where templateId = &#63; and status = &#63;.
	 *
	 * @param templateVersionId the primary key of the current ddm template version
	 * @param templateId the template ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	public static DDMTemplateVersion[] findByT_S_PrevAndNext(
			long templateVersionId, long templateId, int status,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByT_S_PrevAndNext(
			templateVersionId, templateId, status, orderByComparator);
	}

	/**
	 * Removes all the ddm template versions where templateId = &#63; and status = &#63; from the database.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 */
	public static void removeByT_S(long templateId, int status) {
		getPersistence().removeByT_S(templateId, status);
	}

	/**
	 * Returns the number of ddm template versions where templateId = &#63; and status = &#63;.
	 *
	 * @param templateId the template ID
	 * @param status the status
	 * @return the number of matching ddm template versions
	 */
	public static int countByT_S(long templateId, int status) {
		return getPersistence().countByT_S(templateId, status);
	}

	/**
	 * Caches the ddm template version in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateVersion the ddm template version
	 */
	public static void cacheResult(DDMTemplateVersion ddmTemplateVersion) {
		getPersistence().cacheResult(ddmTemplateVersion);
	}

	/**
	 * Caches the ddm template versions in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateVersions the ddm template versions
	 */
	public static void cacheResult(
		List<DDMTemplateVersion> ddmTemplateVersions) {

		getPersistence().cacheResult(ddmTemplateVersions);
	}

	/**
	 * Creates a new ddm template version with the primary key. Does not add the ddm template version to the database.
	 *
	 * @param templateVersionId the primary key for the new ddm template version
	 * @return the new ddm template version
	 */
	public static DDMTemplateVersion create(long templateVersionId) {
		return getPersistence().create(templateVersionId);
	}

	/**
	 * Removes the ddm template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateVersionId the primary key of the ddm template version
	 * @return the ddm template version that was removed
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	public static DDMTemplateVersion remove(long templateVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().remove(templateVersionId);
	}

	public static DDMTemplateVersion updateImpl(
		DDMTemplateVersion ddmTemplateVersion) {

		return getPersistence().updateImpl(ddmTemplateVersion);
	}

	/**
	 * Returns the ddm template version with the primary key or throws a <code>NoSuchTemplateVersionException</code> if it could not be found.
	 *
	 * @param templateVersionId the primary key of the ddm template version
	 * @return the ddm template version
	 * @throws NoSuchTemplateVersionException if a ddm template version with the primary key could not be found
	 */
	public static DDMTemplateVersion findByPrimaryKey(long templateVersionId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchTemplateVersionException {

		return getPersistence().findByPrimaryKey(templateVersionId);
	}

	/**
	 * Returns the ddm template version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateVersionId the primary key of the ddm template version
	 * @return the ddm template version, or <code>null</code> if a ddm template version with the primary key could not be found
	 */
	public static DDMTemplateVersion fetchByPrimaryKey(long templateVersionId) {
		return getPersistence().fetchByPrimaryKey(templateVersionId);
	}

	/**
	 * Returns all the ddm template versions.
	 *
	 * @return the ddm template versions
	 */
	public static List<DDMTemplateVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @return the range of ddm template versions
	 */
	public static List<DDMTemplateVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm template versions
	 */
	public static List<DDMTemplateVersion> findAll(
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template versions
	 * @param end the upper bound of the range of ddm template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm template versions
	 */
	public static List<DDMTemplateVersion> findAll(
		int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm template versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm template versions.
	 *
	 * @return the number of ddm template versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMTemplateVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMTemplateVersionPersistence, DDMTemplateVersionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMTemplateVersionPersistence.class);

		ServiceTracker
			<DDMTemplateVersionPersistence, DDMTemplateVersionPersistence>
				serviceTracker =
					new ServiceTracker
						<DDMTemplateVersionPersistence,
						 DDMTemplateVersionPersistence>(
							 bundle.getBundleContext(),
							 DDMTemplateVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}