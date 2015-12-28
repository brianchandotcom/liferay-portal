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
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the workflow instance link service. This utility wraps {@link com.liferay.portal.service.persistence.impl.WorkflowInstanceLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkPersistence
 * @see com.liferay.portal.service.persistence.impl.WorkflowInstanceLinkPersistenceImpl
 * @generated
 */
@ProviderType
public class WorkflowInstanceLinkUtil {
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
	public static void clearCache(WorkflowInstanceLink workflowInstanceLink) {
		getPersistence().clearCache(workflowInstanceLink);
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
	public static List<WorkflowInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WorkflowInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WorkflowInstanceLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WorkflowInstanceLink> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static WorkflowInstanceLink update(
		WorkflowInstanceLink workflowInstanceLink) {
		return getPersistence().update(workflowInstanceLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static WorkflowInstanceLink update(
		WorkflowInstanceLink workflowInstanceLink, ServiceContext serviceContext) {
		return getPersistence().update(workflowInstanceLink, serviceContext);
	}

	/**
	* Returns all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching workflow instance links
	*/
	public static List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	/**
	* Returns a range of all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK,
			start, end);
	}

	/**
	* Returns an ordered range of all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<WorkflowInstanceLink> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching workflow instance links
	*/
	public static List<WorkflowInstanceLink> findByG_C_C_C(long groupId,
		long companyId, long classNameId, long classPK, int start, int end,
		OrderByComparator<WorkflowInstanceLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching workflow instance link
	* @throws NoSuchWorkflowInstanceLinkException if a matching workflow instance link could not be found
	*/
	public static WorkflowInstanceLink findByG_C_C_C_First(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator<WorkflowInstanceLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException {
		return getPersistence()
				   .findByG_C_C_C_First(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

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
	public static WorkflowInstanceLink fetchByG_C_C_C_First(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator<WorkflowInstanceLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_C_First(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns the last workflow instance link in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching workflow instance link
	* @throws NoSuchWorkflowInstanceLinkException if a matching workflow instance link could not be found
	*/
	public static WorkflowInstanceLink findByG_C_C_C_Last(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator<WorkflowInstanceLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException {
		return getPersistence()
				   .findByG_C_C_C_Last(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

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
	public static WorkflowInstanceLink fetchByG_C_C_C_Last(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator<WorkflowInstanceLink> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_C_Last(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

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
	* @throws NoSuchWorkflowInstanceLinkException if a workflow instance link with the primary key could not be found
	*/
	public static WorkflowInstanceLink[] findByG_C_C_C_PrevAndNext(
		long workflowInstanceLinkId, long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<WorkflowInstanceLink> orderByComparator)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException {
		return getPersistence()
				   .findByG_C_C_C_PrevAndNext(workflowInstanceLinkId, groupId,
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public static void removeByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) {
		getPersistence()
			.removeByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	/**
	* Returns the number of workflow instance links where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching workflow instance links
	*/
	public static int countByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) {
		return getPersistence()
				   .countByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	/**
	* Caches the workflow instance link in the entity cache if it is enabled.
	*
	* @param workflowInstanceLink the workflow instance link
	*/
	public static void cacheResult(WorkflowInstanceLink workflowInstanceLink) {
		getPersistence().cacheResult(workflowInstanceLink);
	}

	/**
	* Caches the workflow instance links in the entity cache if it is enabled.
	*
	* @param workflowInstanceLinks the workflow instance links
	*/
	public static void cacheResult(
		List<WorkflowInstanceLink> workflowInstanceLinks) {
		getPersistence().cacheResult(workflowInstanceLinks);
	}

	/**
	* Creates a new workflow instance link with the primary key. Does not add the workflow instance link to the database.
	*
	* @param workflowInstanceLinkId the primary key for the new workflow instance link
	* @return the new workflow instance link
	*/
	public static WorkflowInstanceLink create(long workflowInstanceLinkId) {
		return getPersistence().create(workflowInstanceLinkId);
	}

	/**
	* Removes the workflow instance link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link that was removed
	* @throws NoSuchWorkflowInstanceLinkException if a workflow instance link with the primary key could not be found
	*/
	public static WorkflowInstanceLink remove(long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException {
		return getPersistence().remove(workflowInstanceLinkId);
	}

	public static WorkflowInstanceLink updateImpl(
		WorkflowInstanceLink workflowInstanceLink) {
		return getPersistence().updateImpl(workflowInstanceLink);
	}

	/**
	* Returns the workflow instance link with the primary key or throws a {@link NoSuchWorkflowInstanceLinkException} if it could not be found.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link
	* @throws NoSuchWorkflowInstanceLinkException if a workflow instance link with the primary key could not be found
	*/
	public static WorkflowInstanceLink findByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException {
		return getPersistence().findByPrimaryKey(workflowInstanceLinkId);
	}

	/**
	* Returns the workflow instance link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param workflowInstanceLinkId the primary key of the workflow instance link
	* @return the workflow instance link, or <code>null</code> if a workflow instance link with the primary key could not be found
	*/
	public static WorkflowInstanceLink fetchByPrimaryKey(
		long workflowInstanceLinkId) {
		return getPersistence().fetchByPrimaryKey(workflowInstanceLinkId);
	}

	public static java.util.Map<java.io.Serializable, WorkflowInstanceLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the workflow instance links.
	*
	* @return the workflow instance links
	*/
	public static List<WorkflowInstanceLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @return the range of workflow instance links
	*/
	public static List<WorkflowInstanceLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of workflow instance links
	*/
	public static List<WorkflowInstanceLink> findAll(int start, int end,
		OrderByComparator<WorkflowInstanceLink> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the workflow instance links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WorkflowInstanceLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of workflow instance links
	* @param end the upper bound of the range of workflow instance links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of workflow instance links
	*/
	public static List<WorkflowInstanceLink> findAll(int start, int end,
		OrderByComparator<WorkflowInstanceLink> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the workflow instance links from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of workflow instance links.
	*
	* @return the number of workflow instance links
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WorkflowInstanceLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WorkflowInstanceLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(WorkflowInstanceLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static WorkflowInstanceLinkPersistence _persistence;
}