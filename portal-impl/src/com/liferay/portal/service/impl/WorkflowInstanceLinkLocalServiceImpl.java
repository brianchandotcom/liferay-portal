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

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.base.WorkflowInstanceLinkLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.Collections;
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
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		long workflowInstanceLinkId = counterLocalService.increment();

		WorkflowInstanceLink workflowInstanceLink =
			workflowInstanceLinkPersistence.create(workflowInstanceLinkId);

		workflowInstanceLink.setGroupId(groupId);
		workflowInstanceLink.setCompanyId(companyId);
		workflowInstanceLink.setUserId(userId);
		workflowInstanceLink.setUserName(user.getFullName());
		workflowInstanceLink.setClassNameId(
			_classNameLocalService.getClassNameId(className));
		workflowInstanceLink.setClassPK(classPK);
		workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);

		return workflowInstanceLinkPersistence.update(workflowInstanceLink);
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			long workflowInstanceLinkId)
		throws PortalException {

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			workflowInstanceLinkId);

		return deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		WorkflowInstanceLink workflowInstanceLink = fetchWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		return deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	@Override
	public WorkflowInstanceLink deleteWorkflowInstanceLink(
			WorkflowInstanceLink workflowInstanceLink)
		throws PortalException {

		if (workflowInstanceLink == null) {
			return null;
		}

		super.deleteWorkflowInstanceLink(workflowInstanceLink);

		WorkflowInstanceManagerUtil.deleteWorkflowInstance(
			workflowInstanceLink.getCompanyId(),
			workflowInstanceLink.getWorkflowInstanceId());

		return workflowInstanceLink;
	}

	@Override
	public void deleteWorkflowInstanceLinks(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		for (WorkflowInstanceLink workflowInstanceLink :
				workflowInstanceLinks) {

			deleteWorkflowInstanceLink(workflowInstanceLink);
		}
	}

	@Override
	public WorkflowInstanceLink fetchWorkflowInstanceLink(
		long companyId, long groupId, String className, long classPK) {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		if (!workflowInstanceLinks.isEmpty()) {
			return workflowInstanceLinks.get(0);
		}

		return null;
	}

	@Override
	public String getState(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());

		List<String> currentNodeNames = workflowInstance.getCurrentNodeNames();

		if (ListUtil.isNotEmpty(currentNodeNames)) {
			return currentNodeNames.get(0);
		}

		return StringPool.BLANK;
	}

	@Override
	public WorkflowInstanceLink getWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		if (workflowInstanceLinks.isEmpty()) {
			throw new NoSuchWorkflowInstanceLinkException(
				StringBundler.concat(
					"{companyId=", companyId, ", groupId=", groupId,
					", className=", className, ", classPK=", classPK, "}"));
		}

		return workflowInstanceLinks.get(0);
	}

	@Override
	public List<WorkflowInstanceLink> getWorkflowInstanceLinks(
		long companyId, long groupId, String className, long classPK) {

		long classNameId = _classNameLocalService.getClassNameId(className);

		int count = workflowInstanceLinkPersistence.countByG_C_C(
			groupId, companyId, classNameId);

		if (count == 0) {
			return Collections.emptyList();
		}

		return workflowInstanceLinkPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	@Override
	public boolean hasWorkflowInstanceLink(
		long companyId, long groupId, String className, long classPK) {

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
		throws PortalException {

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
		throws PortalException {

		startWorkflowInstance(
			companyId, groupId, userId, className, classPK, workflowContext,
			false);
	}

	@Override
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK, Map<String, Serializable> workflowContext,
			boolean waitForCompletion)
		throws PortalException {

		if (!WorkflowThreadLocal.isEnabled()) {
			return;
		}

		if (userId == 0) {
			userId = _userLocalService.getDefaultUserId(companyId);
		}

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowHandler.getWorkflowDefinitionLink(
				companyId, groupId, classPK);

		if (workflowContext != null) {
			workflowContext = new HashMap<>(workflowContext);
		}
		else {
			workflowContext = new HashMap<>();
		}

		workflowContext.put(
			WorkflowConstants.CONTEXT_COMPANY_ID, String.valueOf(companyId));
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME, className);
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK, String.valueOf(classPK));
		workflowContext.put(
			WorkflowConstants.CONTEXT_ENTRY_TYPE,
			workflowHandler.getType(LocaleUtil.getDefault()));
		workflowContext.put(
			WorkflowConstants.CONTEXT_GROUP_ID, String.valueOf(groupId));

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.startWorkflowInstance(
				companyId, groupId, userId,
				workflowDefinitionLink.getWorkflowDefinitionName(),
				workflowDefinitionLink.getWorkflowDefinitionVersion(), null,
				workflowContext, waitForCompletion);

		addWorkflowInstanceLink(
			userId, companyId, groupId, className, classPK,
			workflowInstance.getWorkflowInstanceId());
	}

	@Override
	public void updateClassPK(
			long companyId, long groupId, String className, long oldClassPK,
			long newClassPK)
		throws PortalException {

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

			workflowInstanceLink = workflowInstanceLinkPersistence.update(
				workflowInstanceLink);

			WorkflowInstanceManagerUtil.updateWorkflowContext(
				workflowInstanceLink.getCompanyId(),
				workflowInstanceLink.getWorkflowInstanceId(),
				HashMapBuilder.create(
					workflowInstance.getWorkflowContext()
				).put(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
					String.valueOf(newClassPK)
				).build());
		}
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}