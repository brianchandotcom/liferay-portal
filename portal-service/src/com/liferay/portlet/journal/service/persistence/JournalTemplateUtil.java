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

import com.liferay.portlet.journal.model.JournalTemplate;

import java.util.List;

/**
 * The persistence utility for the journal template service. This utility wraps {@link JournalTemplatePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplatePersistence
 * @see JournalTemplatePersistenceImpl
 * @generated
 */
public class JournalTemplateUtil {
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
	public static void clearCache(JournalTemplate journalTemplate) {
		getPersistence().clearCache(journalTemplate);
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
	public static List<JournalTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalTemplate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static JournalTemplate update(JournalTemplate journalTemplate)
		throws SystemException {
		return getPersistence().update(journalTemplate);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static JournalTemplate update(JournalTemplate journalTemplate,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(journalTemplate, serviceContext);
	}

	/**
	* Returns all the journal templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the journal templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where uuid = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_PrevAndNext(id, uuid, orderByComparator);
	}

	/**
	* Removes all the journal templates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of journal templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the journal template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the journal template where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of journal templates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the journal templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the journal templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] findByUuid_C_PrevAndNext(
		long id, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(id, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the journal templates where uuid = &#63; and companyId = &#63; from the database.
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
	* Returns the number of journal templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the journal templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(id, groupId, orderByComparator);
	}

	/**
	* Returns all the journal templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set of journal templates that the user has permission to view where groupId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] filterFindByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(id, groupId,
			orderByComparator);
	}

	/**
	* Removes all the journal templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of journal templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of journal templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the journal templates where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTemplateId(templateId);
	}

	/**
	* Returns a range of all the journal templates where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTemplateId(templateId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByTemplateId(templateId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByTemplateId_First(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByTemplateId_First(templateId, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByTemplateId_First(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByTemplateId_First(templateId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByTemplateId_Last(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByTemplateId_Last(templateId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByTemplateId_Last(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByTemplateId_Last(templateId, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where templateId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] findByTemplateId_PrevAndNext(
		long id, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByTemplateId_PrevAndNext(id, templateId,
			orderByComparator);
	}

	/**
	* Removes all the journal templates where templateId = &#63; from the database.
	*
	* @param templateId the template ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByTemplateId(java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTemplateId(templateId);
	}

	/**
	* Returns the number of journal templates where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTemplateId(java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTemplateId(templateId);
	}

	/**
	* Returns all the journal templates where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Returns a range of all the journal templates where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where structureId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] findByStructureId_PrevAndNext(
		long id, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(id, structureId,
			orderByComparator);
	}

	/**
	* Removes all the journal templates where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Returns the number of journal templates where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Returns the journal template where smallImageId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	/**
	* Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	/**
	* Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBySmallImageId(smallImageId, retrieveFromCache);
	}

	/**
	* Removes the journal template where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @return the journal template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate removeBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().removeBySmallImageId(smallImageId);
	}

	/**
	* Returns the number of journal templates where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	/**
	* Returns the journal template where groupId = &#63; and templateId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByG_T(groupId, templateId);
	}

	/**
	* Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_T(groupId, templateId);
	}

	/**
	* Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByG_T(
		long groupId, java.lang.String templateId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_T(groupId, templateId, retrieveFromCache);
	}

	/**
	* Removes the journal template where groupId = &#63; and templateId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the journal template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate removeByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().removeByG_T(groupId, templateId);
	}

	/**
	* Returns the number of journal templates where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_T(groupId, templateId);
	}

	/**
	* Returns all the journal templates where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, structureId);
	}

	/**
	* Returns a range of all the journal templates where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, structureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_S(groupId, structureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByG_S_First(groupId, structureId, orderByComparator);
	}

	/**
	* Returns the first journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S_First(groupId, structureId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByG_S_Last(groupId, structureId, orderByComparator);
	}

	/**
	* Returns the last journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_S_Last(groupId, structureId, orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] findByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .findByG_S_PrevAndNext(id, groupId, structureId,
			orderByComparator);
	}

	/**
	* Returns all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_S(groupId, structureId);
	}

	/**
	* Returns a range of all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_S(groupId, structureId, start, end);
	}

	/**
	* Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_S(groupId, structureId, start, end,
			orderByComparator);
	}

	/**
	* Returns the journal templates before and after the current journal template in the ordered set of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* @param id the primary key of the current journal template
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate[] filterFindByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence()
				   .filterFindByG_S_PrevAndNext(id, groupId, structureId,
			orderByComparator);
	}

	/**
	* Removes all the journal templates where groupId = &#63; and structureId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S(groupId, structureId);
	}

	/**
	* Returns the number of journal templates where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, structureId);
	}

	/**
	* Returns the number of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the number of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_S(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_S(groupId, structureId);
	}

	/**
	* Caches the journal template in the entity cache if it is enabled.
	*
	* @param journalTemplate the journal template
	*/
	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate) {
		getPersistence().cacheResult(journalTemplate);
	}

	/**
	* Caches the journal templates in the entity cache if it is enabled.
	*
	* @param journalTemplates the journal templates
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalTemplate> journalTemplates) {
		getPersistence().cacheResult(journalTemplates);
	}

	/**
	* Creates a new journal template with the primary key. Does not add the journal template to the database.
	*
	* @param id the primary key for the new journal template
	* @return the new journal template
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate create(
		long id) {
		return getPersistence().create(id);
	}

	/**
	* Removes the journal template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal template
	* @return the journal template that was removed
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate remove(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(journalTemplate);
	}

	/**
	* Returns the journal template with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param id the primary key of the journal template
	* @return the journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException {
		return getPersistence().findByPrimaryKey(id);
	}

	/**
	* Returns the journal template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal template
	* @return the journal template, or <code>null</code> if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.journal.model.JournalTemplate fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(id);
	}

	/**
	* Returns all the journal templates.
	*
	* @return the journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the journal templates from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal templates.
	*
	* @return the number of journal templates
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static JournalTemplatePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalTemplatePersistence)PortalBeanLocatorUtil.locate(JournalTemplatePersistence.class.getName());

			ReferenceRegistry.registerReference(JournalTemplateUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(JournalTemplatePersistence persistence) {
	}

	private static JournalTemplatePersistence _persistence;
}