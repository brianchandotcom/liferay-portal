/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.base.WorkflowInstanceLinkLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class WorkflowInstanceLinkLocalServiceImpl
	extends WorkflowInstanceLinkLocalServiceBaseImpl {

	@Override
	public WorkflowInstanceLink addWorkflowInstanceLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long workflowInstanceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long workflowInstanceLinkId = counterLocalService.increment();

		WorkflowInstanceLink workflowInstanceLink =
			workflowInstanceLinkPersistence.create(workflowInstanceLinkId);

		workflowInstanceLink.setCreateDate(now);
		workflowInstanceLink.setModifiedDate(now);
		workflowInstanceLink.setUserId(userId);
		workflowInstanceLink.setUserName(user.getFullName());
		workflowInstanceLink.setGroupId(groupId);
		workflowInstanceLink.setCompanyId(companyId);
		workflowInstanceLink.setClassNameId(classNameId);
		workflowInstanceLink.setClassPK(classPK);
		workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);

		workflowInstanceLinkPersistence.update(workflowInstanceLink);

		return workflowInstanceLink;
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			long workflowInstanceLinkId)
		throws PortalException, SystemException {

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			workflowInstanceLinkId);

		return deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		return deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			WorkflowInstanceLink workflowInstanceLink)
		throws PortalException, SystemException {

		if (workflowInstanceLink == null) {
			return null;
		}

		super.deleteWorkflowInstanceLink(workflowInstanceLink);

		subscriptionLocalService.deleteSubscriptions(
			workflowInstanceLink.getCompanyId(),
			WorkflowInstance.class.getName(),
			workflowInstanceLink.getWorkflowInstanceId());

		WorkflowInstanceManagerUtil.deleteWorkflowInstance(
			workflowInstanceLink.getCompanyId(),
			workflowInstanceLink.getWorkflowInstanceId());

		return workflowInstanceLink;
	}

	@Override
	public void deleteWorkflowInstanceLinks(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		for (WorkflowInstanceLink workflowInstanceLink :
				workflowInstanceLinks) {

			deleteWorkflowInstanceLink(workflowInstanceLink);
		}
	}

	@Override
	public WorkflowInstanceLink fetchWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws SystemException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		if (!workflowInstanceLinks.isEmpty()) {
			return workflowInstanceLinks.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public String getState(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());

		return workflowInstance.getState();
	}

	@Override
	public WorkflowInstanceLink getWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		if (workflowInstanceLinks.isEmpty()) {
			throw new NoSuchWorkflowInstanceLinkException();
		}
		else {
			return workflowInstanceLinks.get(0);
		}
	}

	@Override
	public List<WorkflowInstanceLink> getWorkflowInstanceLinks(
			long companyId, long groupId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return workflowInstanceLinkPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	@Override
	public boolean hasWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws SystemException {

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		if (workflowInstanceLink != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isEnded(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		if (workflowInstanceLink == null) {
			return false;
		}

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());

		if (workflowInstance.getEndDate() != null) {
			return true;
		}

		return false;
	}

	@Override
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		if (!WorkflowThreadLocal.isEnabled()) {
			return;
		}

		if (userId == 0) {
			userId = userLocalService.getDefaultUserId(companyId);
		}

		WorkflowHandler workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowHandler.getWorkflowDefinitionLink(
				companyId, groupId, classPK);

		String workflowDefinitionName =
			workflowDefinitionLink.getWorkflowDefinitionName();
		int workflowDefinitionVersion =
			workflowDefinitionLink.getWorkflowDefinitionVersion();

		if (workflowContext != null) {
			workflowContext = new HashMap<String, Serializable>(
				workflowContext);
		}
		else {
			workflowContext = new HashMap<String, Serializable>();
		}

		workflowContext.put(
			WorkflowConstants.CONTEXT_COMPANY_ID, String.valueOf(companyId));
		workflowContext.put(
			WorkflowConstants.CONTEXT_GROUP_ID, String.valueOf(groupId));
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME, className);
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK, String.valueOf(classPK));
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_TYPE,
			workflowHandler.getType(LocaleUtil.getDefault()));

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.startWorkflowInstance(
				companyId, groupId, userId, workflowDefinitionName,
				workflowDefinitionVersion, null, workflowContext);

		addWorkflowInstanceLink(
			userId, companyId, groupId, className, classPK,
			workflowInstance.getWorkflowInstanceId());
	}

	@Override
	public void updateClassPK(
			long companyId, long groupId, String className, long oldClassPK,
			long newClassPK)
		throws PortalException, SystemException {

		if (!WorkflowThreadLocal.isEnabled()) {
			return;
		}

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, oldClassPK);

		for (WorkflowInstanceLink workflowInstanceLink :
				workflowInstanceLinks) {

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					workflowInstanceLink.getCompanyId(),
					workflowInstanceLink.getWorkflowInstanceId());

			workflowInstanceLink.setClassPK(newClassPK);

			workflowInstanceLinkPersistence.update(workflowInstanceLink);

			Map<String, Serializable> workflowContext =
				new HashMap<String, Serializable>(
					workflowInstance.getWorkflowContext());

			workflowContext.put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(newClassPK));

			WorkflowInstanceManagerUtil.updateWorkflowContext(
				workflowInstanceLink.getCompanyId(),
				workflowInstanceLink.getWorkflowInstanceId(), workflowContext);
		}
	}

}