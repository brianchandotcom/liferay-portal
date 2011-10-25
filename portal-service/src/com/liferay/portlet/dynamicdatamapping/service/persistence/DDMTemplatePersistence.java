/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

/**
 * The persistence interface for the d d m template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplatePersistenceImpl
 * @see DDMTemplateUtil
 * @generated
 */
public interface DDMTemplatePersistence extends BasePersistence<DDMTemplate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateUtil} to access the d d m template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d d m template in the entity cache if it is enabled.
	*
	* @param ddmTemplate the d d m template
	*/
	public void cacheResult(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate);

	/**
	* Caches the d d m templates in the entity cache if it is enabled.
	*
	* @param ddmTemplates the d d m templates
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> ddmTemplates);

	/**
	* Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	*
	* @param templateId the primary key for the new d d m template
	* @return the new d d m template
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate create(
		long templateId);

	/**
	* Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template that was removed
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate remove(
		long templateId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m template with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException} if it could not be found.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByPrimaryKey(
		long templateId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchByPrimaryKey(
		long templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByUuid_PrevAndNext(
		long templateId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the d d m templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByGroupId_PrevAndNext(
		long templateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] filterFindByGroupId_PrevAndNext(
		long templateId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByStructureId(
		long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByStructureId(
		long structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByStructureId(
		long structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByStructureId_First(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByStructureId_Last(
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByStructureId_PrevAndNext(
		long templateId, long structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates where type = &#63;.
	*
	* @param type the type
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByType(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByType(
		java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByType(
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByType_First(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByType_Last(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByType_PrevAndNext(
		long templateId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates where language = &#63;.
	*
	* @param language the language
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByLanguage(
		java.lang.String language)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByLanguage(
		java.lang.String language, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByLanguage(
		java.lang.String language, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByLanguage_First(
		java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByLanguage_Last(
		java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByLanguage_PrevAndNext(
		long templateId, java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates where structureId = &#63; and type = &#63;.
	*
	* @param structureId the structure ID
	* @param type the type
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByS_T(
		long structureId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where structureId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByS_T(
		long structureId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where structureId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByS_T(
		long structureId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where structureId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByS_T_First(
		long structureId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where structureId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByS_T_Last(
		long structureId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where structureId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param structureId the structure ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByS_T_PrevAndNext(
		long templateId, long structureId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @return the matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByS_T_M(
		long structureId, java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByS_T_M(
		long structureId, java.lang.String type, java.lang.String mode,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findByS_T_M(
		long structureId, java.lang.String type, java.lang.String mode,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first d d m template in the ordered set where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByS_T_M_First(
		long structureId, java.lang.String type, java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a matching d d m template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate findByS_T_M_Last(
		long structureId, java.lang.String type, java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the primary key of the current d d m template
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException if a d d m template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMTemplate[] findByS_T_M_PrevAndNext(
		long templateId, long structureId, java.lang.String type,
		java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Returns all the d d m templates.
	*
	* @return the d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d d m template where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;

	/**
	* Removes all the d d m templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates where type = &#63; from the database.
	*
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByType(java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates where language = &#63; from the database.
	*
	* @param language the language
	* @throws SystemException if a system exception occurred
	*/
	public void removeByLanguage(java.lang.String language)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates where structureId = &#63; and type = &#63; from the database.
	*
	* @param structureId the structure ID
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByS_T(long structureId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates where structureId = &#63; and type = &#63; and mode = &#63; from the database.
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @throws SystemException if a system exception occurred
	*/
	public void removeByS_T_M(long structureId, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d d m templates from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByStructureId(long structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where type = &#63;.
	*
	* @param type the type
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByType(java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where language = &#63;.
	*
	* @param language the language
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByLanguage(java.lang.String language)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where structureId = &#63; and type = &#63;.
	*
	* @param structureId the structure ID
	* @param type the type
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByS_T(long structureId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates where structureId = &#63; and type = &#63; and mode = &#63;.
	*
	* @param structureId the structure ID
	* @param type the type
	* @param mode the mode
	* @return the number of matching d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByS_T_M(long structureId, java.lang.String type,
		java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of d d m templates.
	*
	* @return the number of d d m templates
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DDMTemplate remove(DDMTemplate ddmTemplate)
		throws SystemException;
}