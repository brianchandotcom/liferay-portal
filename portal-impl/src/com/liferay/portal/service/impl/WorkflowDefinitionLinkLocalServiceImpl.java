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

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.service.base.WorkflowDefinitionLinkLocalServiceBaseImpl;

import java.util.Collections;
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
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long workflowDefinitionLinkId = counterLocalService.increment();

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.create(workflowDefinitionLinkId);

		workflowDefinitionLink.setGroupId(StagingUtil.getLiveGroupId(groupId));
		workflowDefinitionLink.setCompanyId(companyId);
		workflowDefinitionLink.setUserId(userId);
		workflowDefinitionLink.setUserName(user.getFullName());
		workflowDefinitionLink.setClassNameId(
			classNameLocalService.getClassNameId(className));
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
		long typePK) {

		WorkflowDefinitionLink workflowDefinitionLink =
			fetchWorkflowDefinitionLink(
				companyId, groupId, className, classPK, typePK, true);

		if (workflowDefinitionLink != null) {
			deleteWorkflowDefinitionLink(workflowDefinitionLink);
		}
	}

	@Override
	public WorkflowDefinitionLink fetchDefaultWorkflowDefinitionLink(
		long companyId, String className, long classPK, long typePK) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return null;
		}

		return workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
			WorkflowConstants.DEFAULT_GROUP_ID, companyId,
			classNameLocalService.getClassNameId(className), classPK, typePK);
	}

	@Override
	public WorkflowDefinitionLink fetchWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK,
		long typePK) {

		return fetchWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK, false);
	}

	@Override
	public WorkflowDefinitionLink fetchWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK,
		long typePK, boolean strict) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return null;
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
				StagingUtil.getLiveGroupId(groupId), companyId,
				classNameLocalService.getClassNameId(className), classPK,
				typePK);

		if (!strict && (workflowDefinitionLink == null)) {
			workflowDefinitionLink =
				workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
					WorkflowConstants.DEFAULT_GROUP_ID, companyId,
					classNameLocalService.getClassNameId(className), classPK,
					typePK);
		}

		return workflowDefinitionLink;
	}

	@Override
	public List<WorkflowDefinitionLink> fetchWorkflowDefinitionLinks(
		long companyId, long groupId, String className, long classPK) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return Collections.emptyList();
		}

		return workflowDefinitionLinkPersistence.findByG_C_C_C(
			StagingUtil.getLiveGroupId(groupId), companyId,
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public WorkflowDefinitionLink getDefaultWorkflowDefinitionLink(
			long companyId, String className, long classPK, long typePK)
		throws PortalException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		return workflowDefinitionLinkPersistence.findByG_C_C_C_T(
			WorkflowConstants.DEFAULT_GROUP_ID, companyId,
			classNameLocalService.getClassNameId(className), classPK, typePK);
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK)
		throws PortalException {

		return getWorkflowDefinitionLink(
			companyId, groupId, className, classPK, typePK, false);
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, String className, long classPK,
			long typePK, boolean strict)
		throws PortalException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			fetchWorkflowDefinitionLink(
				companyId, groupId, className, classPK, typePK, strict);

		if (workflowDefinitionLink == null) {
			throw new NoSuchWorkflowDefinitionLinkException(
				StringBundler.concat(
					"No workflow exists with the key {groupId=",
					StagingUtil.getLiveGroupId(groupId), ", companyId=",
					companyId, ", and className=", className, "}"));
		}

		return workflowDefinitionLink;
	}

	@Override
	public List<WorkflowDefinitionLink> getWorkflowDefinitionLinks(
			long companyId, long groupId, String className, long classPK)
		throws PortalException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		return workflowDefinitionLinkPersistence.findByG_C_C_C(
			companyId, StagingUtil.getLiveGroupId(groupId),
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<WorkflowDefinitionLink> getWorkflowDefinitionLinks(
			long companyId, String workflowDefinitionName,
			int workflowDefinitionVersion)
		throws PortalException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		return workflowDefinitionLinkPersistence.findByC_W_W(
			companyId, workflowDefinitionName, workflowDefinitionVersion);
	}

	@Override
	public int getWorkflowDefinitionLinksCount(
		long companyId, long groupId, String className) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return 0;
		}

		return workflowDefinitionLinkPersistence.countByG_C_C(
			StagingUtil.getLiveGroupId(groupId), companyId,
			classNameLocalService.getClassNameId(className));
	}

	@Override
	public int getWorkflowDefinitionLinksCount(
		long companyId, String workflowDefinitionName,
		int workflowDefinitionVersion) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return 0;
		}

		return workflowDefinitionLinkPersistence.countByC_W_W(
			companyId, workflowDefinitionName, workflowDefinitionVersion);
	}

	@Override
	@Transactional(enabled = false)
	public boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String className) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		int count =
			workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount(
				companyId, groupId, className);

		if (count > 0) {
			return true;
		}

		count =
			workflowDefinitionLinkLocalService.getWorkflowDefinitionLinksCount(
				companyId, GroupConstants.DEFAULT_LIVE_GROUP_ID, className);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		int count = workflowDefinitionLinkPersistence.countByG_C_C_C(
			StagingUtil.getLiveGroupId(groupId), companyId,
			classNameLocalService.getClassNameId(className), classPK);

		if (count > 0) {
			return true;
		}

		count = workflowDefinitionLinkPersistence.countByG_C_C_C(
			GroupConstants.DEFAULT_LIVE_GROUP_ID, companyId,
			classNameLocalService.getClassNameId(className), classPK);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String className, long classPK,
		long typePK) {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		int count = workflowDefinitionLinkPersistence.countByG_C_C_C_T(
			StagingUtil.getLiveGroupId(groupId), companyId,
			classNameLocalService.getClassNameId(className), classPK, typePK);

		if (count > 0) {
			return true;
		}

		count = workflowDefinitionLinkPersistence.countByG_C_C_C_T(
			WorkflowConstants.DEFAULT_GROUP_ID, companyId,
			classNameLocalService.getClassNameId(className), classPK, typePK);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void updateWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long typePK, String workflowDefinition)
		throws PortalException {

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
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.fetchByG_C_C_C_T(
				StagingUtil.getLiveGroupId(groupId), companyId,
				classNameLocalService.getClassNameId(className), classPK,
				typePK);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink = addWorkflowDefinitionLink(
				userId, companyId, StagingUtil.getLiveGroupId(groupId),
				className, classPK, typePK, workflowDefinitionName,
				workflowDefinitionVersion);
		}

		workflowDefinitionLink.setGroupId(StagingUtil.getLiveGroupId(groupId));
		workflowDefinitionLink.setCompanyId(companyId);
		workflowDefinitionLink.setUserId(userId);
		workflowDefinitionLink.setUserName(user.getFullName());
		workflowDefinitionLink.setClassNameId(
			classNameLocalService.getClassNameId(className));
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
		throws PortalException {

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