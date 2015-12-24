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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the workflow definition link service. This utility wraps {@link com.liferay.portal.service.persistence.impl.WorkflowDefinitionLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLinkPersistence
 * @see com.liferay.portal.service.persistence.impl.WorkflowDefinitionLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class WorkflowDefinitionLinkUtil {
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
	public static void clearCache(WorkflowDefinitionLink workflowDefinitionLink) {
		getPersistence().clearCache(workflowDefinitionLink);
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
	public static List<WorkflowDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowDefinitionLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static WorkflowDefinitionLink update(
		WorkflowDefinitionLink workflowDefinitionLink) {
		return getPersistence().update(workflowDefinitionLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static WorkflowDefinitionLink update(
		WorkflowDefinitionLink workflowDefinitionLink,
		ServiceContext serviceContext) {
		return getPersistence().update(workflowDefinitionLink, serviceContext);
	}

	/**
	* Returns all the workflow definition links where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the workflow definition links where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the workflow definition links where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the workflow definition links where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first workflow definition link in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByCompanyId_First(long companyId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first workflow definition link in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByCompanyId_First(
		long companyId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last workflow definition link in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByCompanyId_Last(long companyId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last workflow definition link in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByCompanyId_Last(long companyId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the workflow definition links before and after the current workflow definition link in the ordered set where companyId = &#63;.
	*
	* @param workflowDefinitionLinkId the primary key of the current workflow definition link
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	*/
	public static WorkflowDefinitionLink[] findByCompanyId_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(workflowDefinitionLinkId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the workflow definition links where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of workflow definition links where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching workflow definition links
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByG_C_C(long groupId,
		long companyId, long classNameId) {
		return getPersistence().findByG_C_C(groupId, companyId, classNameId);
	}

	/**
	* Returns a range of all the workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByG_C_C(long groupId,
		long companyId, long classNameId, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupId, companyId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByG_C_C(long groupId,
		long companyId, long classNameId, int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupId, companyId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByG_C_C(long groupId,
		long companyId, long classNameId, int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupId, companyId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first workflow definition link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByG_C_C_First(long groupId,
		long companyId, long classNameId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByG_C_C_First(groupId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Returns the first workflow definition link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByG_C_C_First(long groupId,
		long companyId, long classNameId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Returns the last workflow definition link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByG_C_C_Last(long groupId,
		long companyId, long classNameId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Returns the last workflow definition link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByG_C_C_Last(long groupId,
		long companyId, long classNameId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Returns the workflow definition links before and after the current workflow definition link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param workflowDefinitionLinkId the primary key of the current workflow definition link
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	*/
	public static WorkflowDefinitionLink[] findByG_C_C_PrevAndNext(
		long workflowDefinitionLinkId, long groupId, long companyId,
		long classNameId,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(workflowDefinitionLinkId, groupId,
			companyId, classNameId, orderByComparator);
	}

	/**
	* Removes all the workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public static void removeByG_C_C(long groupId, long companyId,
		long classNameId) {
		getPersistence().removeByG_C_C(groupId, companyId, classNameId);
	}

	/**
	* Returns the number of workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching workflow definition links
	*/
	public static int countByG_C_C(long groupId, long companyId,
		long classNameId) {
		return getPersistence().countByG_C_C(groupId, companyId, classNameId);
	}

	/**
	* Returns all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @return the matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion) {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	/**
	* Returns a range of all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		int start, int end) {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion, start, end);
	}

	/**
	* Returns an ordered range of all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByC_W_W_First(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByC_W_W_First(companyId, workflowDefinitionName,
			workflowDefinitionVersion, orderByComparator);
	}

	/**
	* Returns the first workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByC_W_W_First(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByC_W_W_First(companyId, workflowDefinitionName,
			workflowDefinitionVersion, orderByComparator);
	}

	/**
	* Returns the last workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByC_W_W_Last(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByC_W_W_Last(companyId, workflowDefinitionName,
			workflowDefinitionVersion, orderByComparator);
	}

	/**
	* Returns the last workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByC_W_W_Last(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence()
				   .fetchByC_W_W_Last(companyId, workflowDefinitionName,
			workflowDefinitionVersion, orderByComparator);
	}

	/**
	* Returns the workflow definition links before and after the current workflow definition link in the ordered set where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param workflowDefinitionLinkId the primary key of the current workflow definition link
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	*/
	public static WorkflowDefinitionLink[] findByC_W_W_PrevAndNext(
		long workflowDefinitionLinkId, long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByC_W_W_PrevAndNext(workflowDefinitionLinkId,
			companyId, workflowDefinitionName, workflowDefinitionVersion,
			orderByComparator);
	}

	/**
	* Removes all the workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63; from the database.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	*/
	public static void removeByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion) {
		getPersistence()
			.removeByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	/**
	* Returns the number of workflow definition links where companyId = &#63; and workflowDefinitionName = &#63; and workflowDefinitionVersion = &#63;.
	*
	* @param companyId the company ID
	* @param workflowDefinitionName the workflow definition name
	* @param workflowDefinitionVersion the workflow definition version
	* @return the number of matching workflow definition links
	*/
	public static int countByC_W_W(long companyId,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion) {
		return getPersistence()
				   .countByC_W_W(companyId, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	/**
	* Returns the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; or throws a {@link NoSuchWorkflowDefinitionLinkException} if it could not be found.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the matching workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink findByG_C_C_C_T(long groupId,
		long companyId, long classNameId, long classPK, long typePK)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .findByG_C_C_C_T(groupId, companyId, classNameId, classPK,
			typePK);
	}

	/**
	* Returns the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByG_C_C_C_T(long groupId,
		long companyId, long classNameId, long classPK, long typePK) {
		return getPersistence()
				   .fetchByG_C_C_C_T(groupId, companyId, classNameId, classPK,
			typePK);
	}

	/**
	* Returns the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching workflow definition link, or <code>null</code> if a matching workflow definition link could not be found
	*/
	public static WorkflowDefinitionLink fetchByG_C_C_C_T(long groupId,
		long companyId, long classNameId, long classPK, long typePK,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_C_T(groupId, companyId, classNameId, classPK,
			typePK, retrieveFromCache);
	}

	/**
	* Removes the workflow definition link where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the workflow definition link that was removed
	*/
	public static WorkflowDefinitionLink removeByG_C_C_C_T(long groupId,
		long companyId, long classNameId, long classPK, long typePK)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence()
				   .removeByG_C_C_C_T(groupId, companyId, classNameId, classPK,
			typePK);
	}

	/**
	* Returns the number of workflow definition links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and typePK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param typePK the type p k
	* @return the number of matching workflow definition links
	*/
	public static int countByG_C_C_C_T(long groupId, long companyId,
		long classNameId, long classPK, long typePK) {
		return getPersistence()
				   .countByG_C_C_C_T(groupId, companyId, classNameId, classPK,
			typePK);
	}

	/**
	* Caches the workflow definition link in the entity cache if it is enabled.
	*
	* @param workflowDefinitionLink the workflow definition link
	*/
	public static void cacheResult(
		WorkflowDefinitionLink workflowDefinitionLink) {
		getPersistence().cacheResult(workflowDefinitionLink);
	}

	/**
	* Caches the workflow definition links in the entity cache if it is enabled.
	*
	* @param workflowDefinitionLinks the workflow definition links
	*/
	public static void cacheResult(
		List<WorkflowDefinitionLink> workflowDefinitionLinks) {
		getPersistence().cacheResult(workflowDefinitionLinks);
	}

	/**
	* Creates a new workflow definition link with the primary key. Does not add the workflow definition link to the database.
	*
	* @param workflowDefinitionLinkId the primary key for the new workflow definition link
	* @return the new workflow definition link
	*/
	public static WorkflowDefinitionLink create(long workflowDefinitionLinkId) {
		return getPersistence().create(workflowDefinitionLinkId);
	}

	/**
	* Removes the workflow definition link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link that was removed
	* @throws NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	*/
	public static WorkflowDefinitionLink remove(long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence().remove(workflowDefinitionLinkId);
	}

	public static WorkflowDefinitionLink updateImpl(
		WorkflowDefinitionLink workflowDefinitionLink) {
		return getPersistence().updateImpl(workflowDefinitionLink);
	}

	/**
	* Returns the workflow definition link with the primary key or throws a {@link NoSuchWorkflowDefinitionLinkException} if it could not be found.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link
	* @throws NoSuchWorkflowDefinitionLinkException if a workflow definition link with the primary key could not be found
	*/
	public static WorkflowDefinitionLink findByPrimaryKey(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.NoSuchWorkflowDefinitionLinkException {
		return getPersistence().findByPrimaryKey(workflowDefinitionLinkId);
	}

	/**
	* Returns the workflow definition link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param workflowDefinitionLinkId the primary key of the workflow definition link
	* @return the workflow definition link, or <code>null</code> if a workflow definition link with the primary key could not be found
	*/
	public static WorkflowDefinitionLink fetchByPrimaryKey(
		long workflowDefinitionLinkId) {
		return getPersistence().fetchByPrimaryKey(workflowDefinitionLinkId);
	}

	public static java.util.Map<java.io.Serializable, WorkflowDefinitionLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the workflow definition links.
	*
	* @return the workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the workflow definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @return the range of workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the workflow definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findAll(int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the workflow definition links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowDefinitionLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow definition links
	* @param end the upper bound of the range of workflow definition links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of workflow definition links
	*/
	public static List<WorkflowDefinitionLink> findAll(int start, int end,
		OrderByComparator<WorkflowDefinitionLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the workflow definition links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of workflow definition links.
	*
	* @return the number of workflow definition links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WorkflowDefinitionLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WorkflowDefinitionLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowDefinitionLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(WorkflowDefinitionLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static WorkflowDefinitionLinkPersistence _persistence;
}