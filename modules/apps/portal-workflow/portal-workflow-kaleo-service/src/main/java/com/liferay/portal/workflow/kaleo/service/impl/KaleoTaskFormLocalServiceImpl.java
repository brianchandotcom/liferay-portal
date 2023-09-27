/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.workflow.kaleo.definition.TaskForm;
import com.liferay.portal.workflow.kaleo.definition.TaskFormReference;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.service.base.KaleoTaskFormLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long kaleoNodeId, KaleoTask kaleoTask, TaskForm taskForm,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(
			serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoTaskFormId = counterLocalService.increment();

		KaleoTaskForm kaleoTaskForm = kaleoTaskFormPersistence.create(
			kaleoTaskFormId);

		kaleoTaskForm.setGroupId(kaleoTask.getGroupId());
		kaleoTaskForm.setCompanyId(user.getCompanyId());
		kaleoTaskForm.setUserId(user.getUserId());
		kaleoTaskForm.setUserName(user.getFullName());
		kaleoTaskForm.setCreateDate(date);
		kaleoTaskForm.setModifiedDate(date);
		kaleoTaskForm.setKaleoDefinitionId(kaleoDefinitionId);
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

		return kaleoTaskFormPersistence.update(kaleoTaskForm);
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

	@Reference
	private UserLocalService _userLocalService;

}