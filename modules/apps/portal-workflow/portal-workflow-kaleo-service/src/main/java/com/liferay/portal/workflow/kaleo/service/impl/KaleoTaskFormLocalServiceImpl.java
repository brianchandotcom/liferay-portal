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

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.workflow.kaleo.definition.TaskForm;
import com.liferay.portal.workflow.kaleo.definition.TaskFormReference;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.service.base.KaleoTaskFormLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoTaskForm",
	service = AopService.class
)
public class KaleoTaskFormLocalServiceImpl
	extends KaleoTaskFormLocalServiceBaseImpl {

	@Override
	public KaleoTaskForm addKaleoTaskForm(
			long kaleoDefinitionVersionId, long kaleoNodeId,
			KaleoTask kaleoTask, TaskForm taskForm,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoTaskFormId = counterLocalService.increment();

		KaleoTaskForm kaleoTaskForm = kaleoTaskFormPersistence.create(
			kaleoTaskFormId);

		kaleoTaskForm.setGroupId(kaleoTask.getGroupId());
		kaleoTaskForm.setCompanyId(user.getCompanyId());
		kaleoTaskForm.setUserId(user.getUserId());
		kaleoTaskForm.setUserName(user.getFullName());
		kaleoTaskForm.setCreateDate(now);
		kaleoTaskForm.setModifiedDate(now);
		kaleoTaskForm.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoTaskForm.setKaleoNodeId(kaleoNodeId);
		kaleoTaskForm.setKaleoTaskId(kaleoTask.getKaleoTaskId());
		kaleoTaskForm.setKaleoTaskName(kaleoTask.getName());
		kaleoTaskForm.setName(taskForm.getName());
		kaleoTaskForm.setDescription(taskForm.getDescription());
		kaleoTaskForm.setFormDefinition(taskForm.getFormDefinition());
		kaleoTaskForm.setMetadata(taskForm.getMetadata());
		kaleoTaskForm.setPriority(taskForm.getPriority());

		TaskFormReference taskFormReference = taskForm.getTaskFormReference();

		kaleoTaskForm.setFormCompanyId(taskFormReference.getCompanyId());
		kaleoTaskForm.setFormGroupId(taskFormReference.getGroupId());
		kaleoTaskForm.setFormId(taskFormReference.getFormId());
		kaleoTaskForm.setFormUuid(taskFormReference.getFormUuid());

		kaleoTaskFormPersistence.update(kaleoTaskForm);

		return kaleoTaskForm;
	}

	@Override
	public void deleteCompanyKaleoTaskForms(long companyId) {
		kaleoTaskFormPersistence.removeByCompanyId(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskForms(
		long kaleoDefinitionVersionId) {

		kaleoTaskFormPersistence.removeByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
	}

	@Override
	public KaleoTaskForm fetchKaleoTaskForm(long kaleoTaskFormId) {
		return kaleoTaskFormPersistence.fetchByPrimaryKey(kaleoTaskFormId);
	}

	@Override
	public List<KaleoTaskForm> getKaleoTaskForms(long kaleoTaskId)
		throws PortalException {

		return kaleoTaskFormPersistence.findByKaleoTaskId(kaleoTaskId);
	}

}