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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalTemplate;

/**
 * The persistence interface for the journal template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplatePersistenceImpl
 * @see JournalTemplateUtil
 * @generated
 */
public interface JournalTemplatePersistence extends BasePersistence<JournalTemplate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalTemplateUtil} to access the journal template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the journal template in the entity cache if it is enabled.
	*
	* @param journalTemplate the journal template
	*/
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate);

	/**
	* Caches the journal templates in the entity cache if it is enabled.
	*
	* @param journalTemplates the journal templates
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalTemplate> journalTemplates);

	/**
	* Creates a new journal template with the primary key. Does not add the journal template to the database.
	*
	* @param id the primary key for the new journal template
	* @return the new journal template
	*/
	public com.liferay.portlet.journal.model.JournalTemplate create(long id);

	/**
	* Removes the journal template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal template
	* @return the journal template that was removed
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate remove(long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	public com.liferay.portlet.journal.model.JournalTemplate updateImpl(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal template with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param id the primary key of the journal template
	* @return the journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal template
	* @return the journal template, or <code>null</code> if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal template in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the last journal template in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal template
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal template where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal template in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the last journal template in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns all the journal templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalTemplate[] filterFindByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns all the journal templates where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByTemplateId(
		java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal template in the ordered set where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByTemplateId_First(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the last journal template in the ordered set where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByTemplateId_Last(
		java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal template
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate[] findByTemplateId_PrevAndNext(
		long id, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns all the journal templates where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal template in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the last journal template in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal template
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate[] findByStructureId_PrevAndNext(
		long id, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal template where smallImageId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal template where groupId = &#63; and templateId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchTemplateException} if it could not be found.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the journal template where groupId = &#63; and templateId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching journal template, or <code>null</code> if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate fetchByG_T(
		long groupId, java.lang.String templateId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the journal templates where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the last journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a matching journal template could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate findByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns the journal templates before and after the current journal template in the ordered set where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current journal template
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal template
	* @throws com.liferay.portlet.journal.NoSuchTemplateException if a journal template with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate[] findByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates that the user has permissions to view where groupId = &#63; and structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> filterFindByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.journal.model.JournalTemplate[] filterFindByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Returns all the journal templates.
	*
	* @return the journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the journal templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @return the range of journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the journal templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of journal templates
	* @param end the upper bound of the range of journal templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal templates
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.journal.model.JournalTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal templates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal template where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Removes all the journal templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal templates where templateId = &#63; from the database.
	*
	* @param templateId the template ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByTemplateId(java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal templates where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the journal template where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @return the journal template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate removeBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Removes the journal template where groupId = &#63; and templateId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the journal template that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.journal.model.JournalTemplate removeByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchTemplateException;

	/**
	* Removes all the journal templates where groupId = &#63; and structureId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the journal templates from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByTemplateId(java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where groupId = &#63; and templateId = &#63;.
	*
	* @param groupId the group ID
	* @param templateId the template ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the number of matching journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates that the user has permission to view where groupId = &#63; and structureId = &#63;.
	*
	* @param groupId the group ID
	* @param structureId the structure ID
	* @return the number of matching journal templates that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of journal templates.
	*
	* @return the number of journal templates
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}