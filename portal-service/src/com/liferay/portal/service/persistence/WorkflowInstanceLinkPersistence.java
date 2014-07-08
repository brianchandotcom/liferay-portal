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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.WorkflowInstanceLink;

/**
 * The persistence interface for the workflow instance link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkPersistenceImpl
 * @see WorkflowInstanceLinkUtil
 * @generated
 */
@ProviderType
public interface WorkflowInstanceLinkPersistence extends BasePersistence<WorkflowInstanceLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WorkflowInstanceLinkUtil} to access the workflow instance link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching workflow instance links
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK);

	/**
	* Returns a range of all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @return the range of matching workflow instance links
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		int start, int end);

	/**
	* Returns an ordered range of all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching workflow instance links
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow instance link
	* @throws com.liferay.portal.NoSuchWorkflowInstanceLinkException if a matching workflow instance link could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink findByG_C_C_C_First(
		long groupId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException;

	/**
	* Returns the first workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow instance link, or <code>null</code> if a matching workflow instance link could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C_First(
		long groupId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow instance link
	* @throws com.liferay.portal.NoSuchWorkflowInstanceLinkException if a matching workflow instance link could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink findByG_C_C_C_Last(
		long groupId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException;

	/**
	* Returns the last workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow instance link, or <code>null</code> if a matching workflow instance link could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C_Last(
		long groupId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the workflow instance links before and after the current workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param workflowInstanceLinkId the primary key of the current workflow instance link
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next workflow instance link
	* @throws com.liferay.portal.NoSuchWorkflowInstanceLinkException if a workflow instance link with the primary key could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink[] findByG_C_C_C_PrevAndNext(
		long workflowInstanceLinkId, long groupId, long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException;

	/**
	* Removes all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK);

	/**
	* Returns the number of workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching workflow instance links
	*/
	public int countByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK);

	/**
	* Caches the workflow instance link in the entity cache if it is enabled.
	*
	* @param workflowInstanceLink the workflow instance link
	*/
	public void cacheResult(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink);

	/**
	* Caches the workflow instance links in the entity cache if it is enabled.
	*
	* @param workflowInstanceLinks the workflow instance links
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowInstanceLink> workflowInstanceLinks);

	/**
	* Creates a new workflow instance link with the primary key. Does not add the workflow instance link to the database.
	*
	* @param workflowInstanceLinkId the primary key for the new workflow instance link
	* @return the new workflow instance link
	*/
	public com.liferay.portal.model.WorkflowInstanceLink create(
		long workflowInstanceLinkId);

	/**
	* Removes the workflow instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link that was removed
	* @throws com.liferay.portal.NoSuchWorkflowInstanceLinkException if a workflow instance link with the primary key could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink remove(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException;

	public com.liferay.portal.model.WorkflowInstanceLink updateImpl(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink);

	/**
	* Returns the workflow instance link with the primary key or throws a {@link com.liferay.portal.NoSuchWorkflowInstanceLinkException} if it could not be found.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link
	* @throws com.liferay.portal.NoSuchWorkflowInstanceLinkException if a workflow instance link with the primary key could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink findByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException;

	/**
	* Returns the workflow instance link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link, or <code>null</code> if a workflow instance link with the primary key could not be found
	*/
	public com.liferay.portal.model.WorkflowInstanceLink fetchByPrimaryKey(
		long workflowInstanceLinkId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.WorkflowInstanceLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the workflow instance links.
	*
	* @return the workflow instance links
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll();

	/**
	* Returns a range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @return the range of workflow instance links
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of workflow instance links
	*/
	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the workflow instance links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of workflow instance links.
	*
	* @return the number of workflow instance links
	*/
	public int countAll();
}