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

import com.liferay.dynamic.data.mapping.model.DDMTemplate;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the d d m template service. This utility wraps {@link com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplatePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplatePersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplatePersistenceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateUtil {
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
	public static void clearCache(DDMTemplate ddmTemplate) {
		getPersistence().clearCache(ddmTemplate);
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
	public static List<DDMTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static DDMTemplate update(DDMTemplate ddmTemplate) {
		return getPersistence().update(ddmTemplate);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static DDMTemplate update(DDMTemplate ddmTemplate,
		ServiceContext serviceContext) {
		return getPersistence().update(ddmTemplate, serviceContext);
	}

	/**
	* Returns all the d d m templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByUuid_PrevAndNext(long templateId,
		java.lang.String uuid, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_PrevAndNext(templateId, uuid, orderByComparator);
	}

	/**
	* Removes all the d d m templates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of d d m templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m templates
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the d d m template where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m template that was removed
	*/
	public static DDMTemplate removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of d d m templates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m templates
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByUuid_C_PrevAndNext(long templateId,
		java.lang.String uuid, long companyId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(templateId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the d d m templates where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m templates
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the d d m templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByGroupId_First(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByGroupId_First(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByGroupId_Last(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByGroupId_PrevAndNext(long templateId,
		long groupId, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(templateId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] filterFindByGroupId_PrevAndNext(
		long templateId, long groupId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(templateId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the d d m templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m templates
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the d d m templates where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByClassPK(long classPK) {
		return getPersistence().findByClassPK(classPK);
	}

	/**
	* Returns a range of all the d d m templates where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByClassPK(long classPK, int start,
		int end) {
		return getPersistence().findByClassPK(classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByClassPK(long classPK, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByClassPK(classPK, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByClassPK(long classPK, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByClassPK(classPK, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByClassPK_First(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByClassPK_First(classPK, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByClassPK_First(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByClassPK_First(classPK, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByClassPK_Last(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByClassPK_Last(classPK, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByClassPK_Last(long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByClassPK_Last(classPK, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByClassPK_PrevAndNext(long templateId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByClassPK_PrevAndNext(templateId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the d d m templates where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	*/
	public static void removeByClassPK(long classPK) {
		getPersistence().removeByClassPK(classPK);
	}

	/**
	* Returns the number of d d m templates where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public static int countByClassPK(long classPK) {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	* Returns all the d d m templates where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey) {
		return getPersistence().findByTemplateKey(templateKey);
	}

	/**
	* Returns a range of all the d d m templates where templateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateKey the template key
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey, int start, int end) {
		return getPersistence().findByTemplateKey(templateKey, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where templateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateKey the template key
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByTemplateKey(templateKey, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where templateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateKey the template key
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTemplateKey(templateKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByTemplateKey_First(
		java.lang.String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByTemplateKey_First(templateKey, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByTemplateKey_First(
		java.lang.String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByTemplateKey_First(templateKey, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByTemplateKey_Last(
		java.lang.String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByTemplateKey_Last(templateKey, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByTemplateKey_Last(
		java.lang.String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByTemplateKey_Last(templateKey, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByTemplateKey_PrevAndNext(long templateId,
		java.lang.String templateKey,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByTemplateKey_PrevAndNext(templateId, templateKey,
			orderByComparator);
	}

	/**
	* Removes all the d d m templates where templateKey = &#63; from the database.
	*
	* @param templateKey the template key
	*/
	public static void removeByTemplateKey(java.lang.String templateKey) {
		getPersistence().removeByTemplateKey(templateKey);
	}

	/**
	* Returns the number of d d m templates where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @return the number of matching d d m templates
	*/
	public static int countByTemplateKey(java.lang.String templateKey) {
		return getPersistence().countByTemplateKey(templateKey);
	}

	/**
	* Returns all the d d m templates where type = &#63;.
	*
	* @param type the type
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByType(java.lang.String type) {
		return getPersistence().findByType(type);
	}

	/**
	* Returns a range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByType(java.lang.String type,
		int start, int end) {
		return getPersistence().findByType(type, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByType(java.lang.String type,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().findByType(type, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByType(java.lang.String type,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByType(type, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByType_First(java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByType_First(type, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByType_First(java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByType_First(type, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByType_Last(java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByType_Last(type, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByType_Last(java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByType_Last(type, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByType_PrevAndNext(long templateId,
		java.lang.String type, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByType_PrevAndNext(templateId, type, orderByComparator);
	}

	/**
	* Removes all the d d m templates where type = &#63; from the database.
	*
	* @param type the type
	*/
	public static void removeByType(java.lang.String type) {
		getPersistence().removeByType(type);
	}

	/**
	* Returns the number of d d m templates where type = &#63;.
	*
	* @param type the type
	* @return the number of matching d d m templates
	*/
	public static int countByType(java.lang.String type) {
		return getPersistence().countByType(type);
	}

	/**
	* Returns all the d d m templates where language = &#63;.
	*
	* @param language the language
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByLanguage(java.lang.String language) {
		return getPersistence().findByLanguage(language);
	}

	/**
	* Returns a range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByLanguage(java.lang.String language,
		int start, int end) {
		return getPersistence().findByLanguage(language, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByLanguage(java.lang.String language,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByLanguage(language, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByLanguage(java.lang.String language,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLanguage(language, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByLanguage_First(java.lang.String language,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByLanguage_First(language, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByLanguage_First(java.lang.String language,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByLanguage_First(language, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByLanguage_Last(java.lang.String language,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByLanguage_Last(language, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByLanguage_Last(java.lang.String language,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().fetchByLanguage_Last(language, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where language = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByLanguage_PrevAndNext(long templateId,
		java.lang.String language,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByLanguage_PrevAndNext(templateId, language,
			orderByComparator);
	}

	/**
	* Removes all the d d m templates where language = &#63; from the database.
	*
	* @param language the language
	*/
	public static void removeByLanguage(java.lang.String language) {
		getPersistence().removeByLanguage(language);
	}

	/**
	* Returns the number of d d m templates where language = &#63;.
	*
	* @param language the language
	* @return the number of matching d d m templates
	*/
	public static int countByLanguage(java.lang.String language) {
		return getPersistence().countByLanguage(language);
	}

	/**
	* Returns the d d m template where smallImageId = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findBySmallImageId(long smallImageId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	/**
	* Returns the d d m template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchBySmallImageId(long smallImageId) {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	/**
	* Returns the d d m template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchBySmallImageId(smallImageId, retrieveFromCache);
	}

	/**
	* Removes the d d m template where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @return the d d m template that was removed
	*/
	public static DDMTemplate removeBySmallImageId(long smallImageId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().removeBySmallImageId(smallImageId);
	}

	/**
	* Returns the number of d d m templates where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching d d m templates
	*/
	public static int countBySmallImageId(long smallImageId) {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C(long groupId, long classNameId) {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByG_C_PrevAndNext(long templateId,
		long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_PrevAndNext(templateId, groupId, classNameId,
			orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C(long groupId,
		long classNameId) {
		return getPersistence().filterFindByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end) {
		return getPersistence().filterFindByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] filterFindByG_C_PrevAndNext(long templateId,
		long groupId, long classNameId,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByG_C_PrevAndNext(templateId, groupId,
			classNameId, orderByComparator);
	}

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m templates
	*/
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_C(long groupId, long classNameId) {
		return getPersistence().filterCountByG_C(groupId, classNameId);
	}

	/**
	* Returns all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long groupId, long classPK) {
		return getPersistence().findByG_CPK(groupId, classPK);
	}

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long groupId, long classPK,
		int start, int end) {
		return getPersistence().findByG_CPK(groupId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long groupId, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_CPK(groupId, classPK, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long groupId, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_CPK(groupId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_CPK_First(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_CPK_First(groupId, classPK, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_CPK_First(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_CPK_First(groupId, classPK, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_CPK_Last(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_CPK_Last(groupId, classPK, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_CPK_Last(long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_CPK_Last(groupId, classPK, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByG_CPK_PrevAndNext(long templateId,
		long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_CPK_PrevAndNext(templateId, groupId, classPK,
			orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_CPK(long groupId, long classPK) {
		return getPersistence().filterFindByG_CPK(groupId, classPK);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_CPK(long groupId,
		long classPK, int start, int end) {
		return getPersistence().filterFindByG_CPK(groupId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_CPK(long groupId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_CPK(groupId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] filterFindByG_CPK_PrevAndNext(long templateId,
		long groupId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByG_CPK_PrevAndNext(templateId, groupId, classPK,
			orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_CPK(long[] groupIds,
		long classPK) {
		return getPersistence().filterFindByG_CPK(groupIds, classPK);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_CPK(long[] groupIds,
		long classPK, int start, int end) {
		return getPersistence().filterFindByG_CPK(groupIds, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_CPK(long[] groupIds,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_CPK(groupIds, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns all the d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK) {
		return getPersistence().findByG_CPK(groupIds, classPK);
	}

	/**
	* Returns a range of all the d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK,
		int start, int end) {
		return getPersistence().findByG_CPK(groupIds, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_CPK(groupIds, classPK, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_CPK(groupIds, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m templates where groupId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	*/
	public static void removeByG_CPK(long groupId, long classPK) {
		getPersistence().removeByG_CPK(groupId, classPK);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public static int countByG_CPK(long groupId, long classPK) {
		return getPersistence().countByG_CPK(groupId, classPK);
	}

	/**
	* Returns the number of d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public static int countByG_CPK(long[] groupIds, long classPK) {
		return getPersistence().countByG_CPK(groupIds, classPK);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_CPK(long groupId, long classPK) {
		return getPersistence().filterCountByG_CPK(groupId, classPK);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_CPK(long[] groupIds, long classPK) {
		return getPersistence().filterCountByG_CPK(groupIds, classPK);
	}

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK) {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long groupId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_C_First(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByG_C_C_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(templateId, groupId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().filterFindByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_C(groupId, classNameId, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] filterFindByG_C_C_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByG_C_C_PrevAndNext(templateId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK) {
		return getPersistence().filterFindByG_C_C(groupIds, classNameId, classPK);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_C(groupIds, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_C(groupIds, classNameId, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK) {
		return getPersistence().findByG_C_C(groupIds, classNameId, classPK);
	}

	/**
	* Returns a range of all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupIds, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupIds, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupIds, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByG_C_C(long groupId, long classNameId,
		long classPK) {
		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public static int countByG_C_C(long[] groupIds, long classNameId,
		long classPK) {
		return getPersistence().countByG_C_C(groupIds, classNameId, classPK);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_C_C(long groupId, long classNameId,
		long classPK) {
		return getPersistence().filterCountByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_C_C(long[] groupIds, long classNameId,
		long classPK) {
		return getPersistence()
				   .filterCountByG_C_C(groupIds, classNameId, classPK);
	}

	/**
	* Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByG_C_T(groupId, classNameId, templateKey);
	}

	/**
	* Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey) {
		return getPersistence().fetchByG_C_T(groupId, classNameId, templateKey);
	}

	/**
	* Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_T(groupId, classNameId, templateKey,
			retrieveFromCache);
	}

	/**
	* Removes the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the d d m template that was removed
	*/
	public static DDMTemplate removeByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().removeByG_C_T(groupId, classNameId, templateKey);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and templateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the number of matching d d m templates
	*/
	public static int countByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey) {
		return getPersistence().countByG_C_T(groupId, classNameId, templateKey);
	}

	/**
	* Returns all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		java.lang.String type) {
		return getPersistence().findByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns a range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		java.lang.String type, int start, int end) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		java.lang.String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByC_C_T(long classNameId, long classPK,
		java.lang.String type, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_T(classNameId, classPK, type, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByC_C_T_First(long classNameId, long classPK,
		java.lang.String type, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByC_C_T_First(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByC_C_T_First(long classNameId,
		long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_First(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByC_C_T_Last(long classNameId, long classPK,
		java.lang.String type, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByC_C_T_Last(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByC_C_T_Last(long classNameId, long classPK,
		java.lang.String type, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_T_Last(classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByC_C_T_PrevAndNext(long templateId,
		long classNameId, long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByC_C_T_PrevAndNext(templateId, classNameId, classPK,
			type, orderByComparator);
	}

	/**
	* Removes all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public static void removeByC_C_T(long classNameId, long classPK,
		java.lang.String type) {
		getPersistence().removeByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns the number of d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching d d m templates
	*/
	public static int countByC_C_T(long classNameId, long classPK,
		java.lang.String type) {
		return getPersistence().countByC_C_T(classNameId, classPK, type);
	}

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type, start,
			end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_T(groupId, classNameId, classPK, type, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_C_T_First(long groupId,
		long classNameId, long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_T_First(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_C_T_First(long groupId,
		long classNameId, long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_T_First(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_C_T_Last(long groupId,
		long classNameId, long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_T_Last(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_C_T_Last(long groupId,
		long classNameId, long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_T_Last(groupId, classNameId, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByG_C_C_T_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, java.lang.String type,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_T_PrevAndNext(templateId, groupId, classNameId,
			classPK, type, orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type) {
		return getPersistence()
				   .filterFindByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end) {
		return getPersistence()
				   .filterFindByG_C_C_T(groupId, classNameId, classPK, type,
			start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_C_T(groupId, classNameId, classPK, type,
			start, end, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] filterFindByG_C_C_T_PrevAndNext(
		long templateId, long groupId, long classNameId, long classPK,
		java.lang.String type, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByG_C_C_T_PrevAndNext(templateId, groupId,
			classNameId, classPK, type, orderByComparator);
	}

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public static void removeByG_C_C_T(long groupId, long classNameId,
		long classPK, java.lang.String type) {
		getPersistence().removeByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching d d m templates
	*/
	public static int countByG_C_C_T(long groupId, long classNameId,
		long classPK, java.lang.String type) {
		return getPersistence()
				   .countByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_C_C_T(long groupId, long classNameId,
		long classPK, java.lang.String type) {
		return getPersistence()
				   .filterCountByG_C_C_T(groupId, classNameId, classPK, type);
	}

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode) {
		return getPersistence()
				   .findByG_C_C_T_M(groupId, classNameId, classPK, type, mode);
	}

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end) {
		return getPersistence()
				   .findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public static List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_T_M(groupId, classNameId, classPK, type, mode,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_C_T_M_First(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_T_M_First(groupId, classNameId, classPK, type,
			mode, orderByComparator);
	}

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_C_T_M_First(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_T_M_First(groupId, classNameId, classPK, type,
			mode, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a matching d d m template could not be found
	*/
	public static DDMTemplate findByG_C_C_T_M_Last(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_T_M_Last(groupId, classNameId, classPK, type,
			mode, orderByComparator);
	}

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public static DDMTemplate fetchByG_C_C_T_M_Last(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_T_M_Last(groupId, classNameId, classPK, type,
			mode, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] findByG_C_C_T_M_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .findByG_C_C_T_M_PrevAndNext(templateId, groupId,
			classNameId, classPK, type, mode, orderByComparator);
	}

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode) {
		return getPersistence()
				   .filterFindByG_C_C_T_M(groupId, classNameId, classPK, type,
			mode);
	}

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end) {
		return getPersistence()
				   .filterFindByG_C_C_T_M(groupId, classNameId, classPK, type,
			mode, start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public static List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence()
				   .filterFindByG_C_C_T_M(groupId, classNameId, classPK, type,
			mode, start, end, orderByComparator);
	}

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate[] filterFindByG_C_C_T_M_PrevAndNext(
		long templateId, long groupId, long classNameId, long classPK,
		java.lang.String type, java.lang.String mode,
		OrderByComparator<DDMTemplate> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByG_C_C_T_M_PrevAndNext(templateId, groupId,
			classNameId, classPK, type, mode, orderByComparator);
	}

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	*/
	public static void removeByG_C_C_T_M(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode) {
		getPersistence()
			.removeByG_C_C_T_M(groupId, classNameId, classPK, type, mode);
	}

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the number of matching d d m templates
	*/
	public static int countByG_C_C_T_M(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode) {
		return getPersistence()
				   .countByG_C_C_T_M(groupId, classNameId, classPK, type, mode);
	}

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public static int filterCountByG_C_C_T_M(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode) {
		return getPersistence()
				   .filterCountByG_C_C_T_M(groupId, classNameId, classPK, type,
			mode);
	}

	/**
	* Caches the d d m template in the entity cache if it is enabled.
	*
	* @param ddmTemplate the d d m template
	*/
	public static void cacheResult(DDMTemplate ddmTemplate) {
		getPersistence().cacheResult(ddmTemplate);
	}

	/**
	* Caches the d d m templates in the entity cache if it is enabled.
	*
	* @param ddmTemplates the d d m templates
	*/
	public static void cacheResult(List<DDMTemplate> ddmTemplates) {
		getPersistence().cacheResult(ddmTemplates);
	}

	/**
	* Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	*
	* @param templateId the primary key for the new d d m template
	* @return the new d d m template
	*/
	public static DDMTemplate create(long templateId) {
		return getPersistence().create(templateId);
	}

	/**
	* Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template that was removed
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate remove(long templateId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().remove(templateId);
	}

	public static DDMTemplate updateImpl(DDMTemplate ddmTemplate) {
		return getPersistence().updateImpl(ddmTemplate);
	}

	/**
	* Returns the d d m template with the primary key or throws a {@link com.liferay.dynamic.data.mapping.NoSuchTemplateException} if it could not be found.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template
	* @throws com.liferay.dynamic.data.mapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate findByPrimaryKey(long templateId)
		throws com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException {
		return getPersistence().findByPrimaryKey(templateId);
	}

	/**
	* Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	*/
	public static DDMTemplate fetchByPrimaryKey(long templateId) {
		return getPersistence().fetchByPrimaryKey(templateId);
	}

	public static java.util.Map<java.io.Serializable, DDMTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the d d m templates.
	*
	* @return the d d m templates
	*/
	public static List<DDMTemplate> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of d d m templates
	*/
	public static List<DDMTemplate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m templates
	*/
	public static List<DDMTemplate> findAll(int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m templates
	*/
	public static List<DDMTemplate> findAll(int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the d d m templates from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of d d m templates.
	*
	* @return the number of d d m templates
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static DDMTemplatePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMTemplatePersistence, DDMTemplatePersistence> _serviceTracker =
		ServiceTrackerFactory.open(DDMTemplatePersistence.class);
}