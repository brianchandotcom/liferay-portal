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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.WorkflowDefinitionLink;

/**
 * The persistence interface for the workflow definition link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLinkPersistenceImpl
 * @see WorkflowDefinitionLinkUtil
 * @generated
 */
public interface WorkflowDefinitionLinkPersistence extends BasePersistence<WorkflowDefinitionLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowDefinitionLinkUtil} to access the workflow definition link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the workflow definition link in the entity cache if it is enabled.
	*
	* @param workflowDefinitionLink the workflow definition link
	*/
	public void cacheResult(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink);

	/**
	* Caches the workflow definition links in the entity cache if it is enabled.
	*
	* @param workflowDefinitionLinks the workflow definition links
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> workflowDefinitionLinks);

	/**
	* Creates a new workflow definition link with the primary key. Does not add the workflow definition link to the database.
	*
	* @param workflowDefinitionLinkId the primary key for the new workflow definition link
	* @return the new workflow definition link
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink create(
		long workflowDefinitionLinkId);

	/**
	* Removes the workflow definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link that was removed
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink remove(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowDefinitionLink updateImpl(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition link with the primary key or throws a {@link com.liferay.portal.NoSuchWorkflowDefinitionLinkException} if it could not be found.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink findByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link, or <code>null</code> if a workflow definition link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink fetchByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the workflow definition links where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the workflow definition links where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the workflow definition links where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first workflow definition link in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last workflow definition link in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition links before and after the current workflow definition link in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param workflowDefinitionLinkId the primary key of the current workflow definition link
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink[] findByCompanyId_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @return the matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByC_W_W(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByC_W_W(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findByC_W_W(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink findByC_W_W_First(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink findByC_W_W_Last(
		long companyId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition links before and after the current workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param workflowDefinitionLinkId the primary key of the current workflow definition link
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink[] findByC_W_W_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; or throws a {@link com.liferay.portal.NoSuchWorkflowDefinitionLinkException} if it could not be found.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the matching workflow definition link
	* @throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink findByG_C_C_C_T(
		long groupId, long companyId, long classNameId, long classPK,
		long typePK)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C_C_T(
		long groupId, long companyId, long classNameId, long classPK,
		long typePK) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink fetchByG_C_C_C_T(
		long groupId, long companyId, long classNameId, long classPK,
		long typePK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the workflow definition links.
	*
	* @return the workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the workflow definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the workflow definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the workflow definition links where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63; from the database.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the workflow definition link that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WorkflowDefinitionLink removeByG_C_C_C_T(
		long groupId, long companyId, long classNameId, long classPK,
		long typePK)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the workflow definition links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of workflow definition links where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @return the number of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the number of matching workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_C_C_T(long groupId, long companyId, long classNameId,
		long classPK, long typePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of workflow definition links.
	*
	* @return the number of workflow definition links
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}