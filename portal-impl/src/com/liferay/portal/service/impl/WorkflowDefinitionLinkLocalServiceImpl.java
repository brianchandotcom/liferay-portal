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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.base.WorkflowDefinitionLinkLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 * @author Marcellus Tavares
 */
public class WorkflowDefinitionLinkLocalServiceImpl
	extends WorkflowDefinitionLinkLocalServiceBaseImpl {

	@Override
	public WorkflowDefinitionLink addWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long typePK, String workflowDefinitionName,
			int workflowDefinitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long workflowDefinitionLinkId = counterLocalService.increment();

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.create(workflowDefinitionLinkId);

		workflowDefinitionLink.setCreateDate(now);
		workflowDefinitionLink.setModifiedDate(now);
		workflowDefinitionLink.setUserId(userId);
		workflowDefinitionLink.setUserName(user.getFullName());
		workflowDefinitionLink.setGroupId(groupId);
		workflowDefinitionLink.setCompanyId(companyId);
		workflowDefinitionLink.setClassNameId(classNameId);
		workflowDefinitionLink.setClassPK(classPK);
		workflowDefinitionLink.setTypePK(typePK);
		workflowDefinitionLink.setWorkflowDefinitionName(
			workflowDefinitionName);
		workflowDefinitionLink.setWorkflowDefinitionVersion(
			workflowDefinitionVersion);

		workflowDefinitionLinkPersistence.update(workflowDefinitionLink);

		return workflowDefinitionLink;
	}

	@Override
	public void deleteWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK)
		throws PortalException, SystemException {

		WorkflowDefinitionLink workflowDefinitionLink =
			fetchWorkflowDefinitionLink(
				companyId, groupId, className, classPK, typePK, true);

		if (workflowDefinitionLink != null) {
			deleteWorkflowDefinitionLink(workflowDefinitionLink);
		}
	}

	@Override
	public WorkflowDefinitionLink fetchDefaultWorkflowDefinitionLink(
			long companyId, String className, long classPK, long typePK)
		throws SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return null;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		return workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
			WorkflowConstants.DEFAULT_GROUP_ID, companyId, classNameId, classPK,
			typePK);
	}

	@Override
	public WorkflowDefinitionLink fetchWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK)
		throws PortalException, SystemException {

		return fetchWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK, false);
	}

	@Override
	public WorkflowDefinitionLink fetchWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK, boolean strict)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return null;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		WorkflowDefinitionLink workflowDefinitionLink = null;

		if (groupId > 0) {
			groupId = StagingUtil.getLiveGroupId(groupId);
		}

		workflowDefinitionLink =
			workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
				groupId, companyId, classNameId, classPK, typePK);

		if (!strict && (workflowDefinitionLink == null)) {
			workflowDefinitionLink =
				workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
					WorkflowConstants.DEFAULT_GROUP_ID, companyId, classNameId,
					classPK, typePK);
		}

		return workflowDefinitionLink;
	}

	@Override
	public WorkflowDefinitionLink getDefaultWorkflowDefinitionLink(
			long companyId, String className, long classPK, long typePK)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		long classNameId = PortalUtil.getClassNameId(className);

		return workflowDefinitionLinkPersistence.findByG_C_C_C_T(
			WorkflowConstants.DEFAULT_GROUP_ID, companyId, classNameId, classPK,
			typePK);
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK)
		throws PortalException, SystemException {

		return getWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK, false);
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK, boolean strict)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			fetchWorkflowDefinitionLink(
				companyId, groupId, className, classPK, typePK, strict);

		if (workflowDefinitionLink == null) {
			throw new NoSuchWorkflowDefinitionLinkException(
				"No workflow for groupId=" + groupId + ", companyId=" +
					companyId + " and className=" + className);
		}

		return workflowDefinitionLink;
	}

	@Override
	public int getWorkflowDefinitionLinksCount(
			long companyId, String workflowDefinitionName,
			int workflowDefinitionVersion)
		throws SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return 0;
		}

		return workflowDefinitionLinkPersistence.countByC_W_W(
			companyId, workflowDefinitionName, workflowDefinitionVersion);
	}

	@Override
	@Skip
	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, String className)
		throws PortalException, SystemException {

		return hasWorkflowDefinitionLink(companyId, groupId, className, 0);
	}

	@Override
	@Skip
	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		return hasWorkflowDefinitionLink(
			companyId, groupId, className, classPK, 0);
	}

	@Override
	@Skip
	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				companyId, groupId, className, classPK, typePK);

		if (workflowDefinitionLink != null) {
			return true;
		}

		return false;
	}

	@Override
	public void updateWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long typePK, String workflowDefinition)
		throws PortalException, SystemException {

		if (Validator.isNull(workflowDefinition)) {
			deleteWorkflowDefinitionLink(
				companyId, groupId, className, classPK, typePK);
		}
		else {
			String[] workflowDefinitionParts = StringUtil.split(
				workflowDefinition, CharPool.AT);

			String workflowDefinitionName = workflowDefinitionParts[0];
			int workflowDefinitionVersion = GetterUtil.getInteger(
				workflowDefinitionParts[1]);

			updateWorkflowDefinitionLink(
				userId, companyId, groupId, className, classPK, typePK,
				workflowDefinitionName, workflowDefinitionVersion);
		}
	}

	@Override
	public WorkflowDefinitionLink updateWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long typePK, String workflowDefinitionName,
			int workflowDefinitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
				groupId, companyId, classNameId, classPK, typePK);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink = addWorkflowDefinitionLink(
				userId, companyId, groupId, className, classPK, typePK,
				workflowDefinitionName, workflowDefinitionVersion);
		}

		workflowDefinitionLink.setModifiedDate(now);
		workflowDefinitionLink.setUserId(userId);
		workflowDefinitionLink.setUserName(user.getFullName());
		workflowDefinitionLink.setGroupId(groupId);
		workflowDefinitionLink.setCompanyId(companyId);
		workflowDefinitionLink.setClassNameId(classNameId);
		workflowDefinitionLink.setClassPK(classPK);
		workflowDefinitionLink.setTypePK(typePK);
		workflowDefinitionLink.setWorkflowDefinitionName(
			workflowDefinitionName);
		workflowDefinitionLink.setWorkflowDefinitionVersion(
			workflowDefinitionVersion);

		workflowDefinitionLinkPersistence.update(workflowDefinitionLink);

		return workflowDefinitionLink;
	}

	@Override
	public void updateWorkflowDefinitionLinks(
			long userId, long companyId, long groupId, String className,
			long classPK,
			List<ObjectValuePair<Long, String>> workflowDefinitionOVPs)
		throws PortalException, SystemException {

		for (ObjectValuePair<Long, String> workflowDefinitionOVP :
				workflowDefinitionOVPs) {

			long typePK = workflowDefinitionOVP.getKey();
			String workflowDefinitionName = workflowDefinitionOVP.getValue();

			if (Validator.isNull(workflowDefinitionName)) {
				deleteWorkflowDefinitionLink(
					companyId, groupId, className, classPK, typePK);
			}
			else {
				updateWorkflowDefinitionLink(
					userId, companyId, groupId, className, classPK, typePK,
					workflowDefinitionName);
			}
		}
	}

}