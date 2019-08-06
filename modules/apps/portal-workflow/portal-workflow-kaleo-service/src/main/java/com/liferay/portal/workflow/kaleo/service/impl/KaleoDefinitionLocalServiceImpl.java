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

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoDefinitionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoDefinition",
	service = AopService.class
)
public class KaleoDefinitionLocalServiceImpl
	extends KaleoDefinitionLocalServiceBaseImpl {

	@Override
	public void activateKaleoDefinition(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			long startKaleoNodeId, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByPrimaryKey(kaleoDefinitionId);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(true);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionPersistence.findByPrimaryKey(
				kaleoDefinitionVersionId);

		kaleoDefinitionVersion.setModifiedDate(new Date());
		kaleoDefinitionVersion.setStartKaleoNodeId(startKaleoNodeId);

		kaleoDefinitionVersionPersistence.update(kaleoDefinitionVersion);
	}

	@Override
	public void activateKaleoDefinition(
			long kaleoDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByPrimaryKey(kaleoDefinitionId);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(true);

		kaleoDefinitionPersistence.update(kaleoDefinition);
	}

	@Override
	public void activateKaleoDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(true);

		kaleoDefinitionPersistence.update(kaleoDefinition);
	}

	@Override
	public KaleoDefinition addKaleoDefinition(
			String name, String title, String description, String content,
			int version, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoDefinitionId = counterLocalService.increment();

		KaleoDefinition kaleoDefinition = kaleoDefinitionPersistence.create(
			kaleoDefinitionId);

		long groupId = _staging.getLiveGroupId(
			serviceContext.getScopeGroupId());

		kaleoDefinition.setGroupId(groupId);

		kaleoDefinition.setCompanyId(user.getCompanyId());
		kaleoDefinition.setUserId(user.getUserId());
		kaleoDefinition.setUserName(user.getFullName());
		kaleoDefinition.setCreateDate(now);
		kaleoDefinition.setModifiedDate(now);
		kaleoDefinition.setName(name);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setDescription(description);
		kaleoDefinition.setContent(content);
		kaleoDefinition.setVersion(version);
		kaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		_kaleoDefinitionVersionLocalService.addKaleoDefinitionVersion(
			name, title, description, content, getVersion(version),
			serviceContext);

		return kaleoDefinition;
	}

	@Override
	public void deactivateKaleoDefinition(
			String name, int version, ServiceContext serviceContext)
		throws PortalException {

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByC_N_V(
				serviceContext.getCompanyId(), name, version);

		kaleoDefinition.setModifiedDate(new Date());
		kaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(kaleoDefinition);
	}

	@Override
	public void deleteCompanyKaleoDefinitions(long companyId) {

		// Kaleo definitions

		kaleoDefinitionPersistence.removeByCompanyId(companyId);

		// Kaleo definition version

		kaleoDefinitionVersionPersistence.removeByCompanyId(companyId);

		// Kaleo condition

		_kaleoConditionLocalService.deleteCompanyKaleoConditions(companyId);

		// Kaleo instances

		_kaleoInstanceLocalService.deleteCompanyKaleoInstances(companyId);

		// Kaleo nodes

		_kaleoNodeLocalService.deleteCompanyKaleoNodes(companyId);

		// Kaleo tasks

		_kaleoTaskLocalService.deleteCompanyKaleoTasks(companyId);

		// Kaleo transitions

		_kaleoTransitionLocalService.deleteCompanyKaleoTransitions(companyId);
	}

	@Override
	public void deleteKaleoDefinition(
			String name, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		KaleoDefinition kaleoDefinition = getKaleoDefinition(
			name, serviceContext);

		if (kaleoDefinition.isActive()) {
			throw new WorkflowException(
				"Cannot delete active workflow definition " +
					kaleoDefinition.getKaleoDefinitionId());
		}

		kaleoDefinitionPersistence.remove(kaleoDefinition);

		// Kaleo definition version

		_kaleoDefinitionVersionLocalService.deleteKaleoDefinitionVersions(
			kaleoDefinition.getKaleoDefinitionVersions());
	}

	@Override
	public KaleoDefinition fetchKaleoDefinition(
		String name, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.fetchByC_N(
			serviceContext.getCompanyId(), name);
	}

	@Override
	public KaleoDefinition getKaleoDefinition(
			String name, ServiceContext serviceContext)
		throws PortalException {

		return kaleoDefinitionPersistence.findByC_N(
			serviceContext.getCompanyId(), name);
	}

	@Override
	public List<KaleoDefinition> getKaleoDefinitions(
		boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.findByC_A(
			serviceContext.getCompanyId(), active, start, end,
			orderByComparator);
	}

	@Override
	public List<KaleoDefinition> getKaleoDefinitions(
		int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.findByCompanyId(
			serviceContext.getCompanyId(), start, end, orderByComparator);
	}

	@Override
	public int getKaleoDefinitionsCount(
		boolean active, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.countByC_A(
			serviceContext.getCompanyId(), active);
	}

	@Override
	public int getKaleoDefinitionsCount(ServiceContext serviceContext) {
		return kaleoDefinitionPersistence.countByCompanyId(
			serviceContext.getCompanyId());
	}

	@Override
	public int getKaleoDefinitionsCount(
		String name, boolean active, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.countByC_N_A(
			serviceContext.getCompanyId(), name, active);
	}

	@Override
	public int getKaleoDefinitionsCount(
		String name, ServiceContext serviceContext) {

		return kaleoDefinitionPersistence.countByC_N(
			serviceContext.getCompanyId(), name);
	}

	@Override
	public KaleoDefinition updatedKaleoDefinition(
			long kaleoDefinitionId, String title, String description,
			String content, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo definition

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionPersistence.findByPrimaryKey(kaleoDefinitionId);

		long groupId = _staging.getLiveGroupId(
			serviceContext.getScopeGroupId());

		kaleoDefinition.setGroupId(groupId);

		kaleoDefinition.setUserId(user.getUserId());
		kaleoDefinition.setUserName(user.getFullName());
		kaleoDefinition.setCreateDate(now);
		kaleoDefinition.setModifiedDate(now);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setDescription(description);
		kaleoDefinition.setContent(content);

		int nextVersion = kaleoDefinition.getVersion() + 1;

		kaleoDefinition.setVersion(nextVersion);

		kaleoDefinition.setActive(false);

		kaleoDefinitionPersistence.update(kaleoDefinition);

		// Kaleo definition version

		_kaleoDefinitionVersionLocalService.addKaleoDefinitionVersion(
			kaleoDefinition.getName(), title, description, content,
			getVersion(nextVersion), serviceContext);

		return kaleoDefinition;
	}

	protected String getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	@Reference
	private KaleoConditionLocalService _kaleoConditionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private KaleoTransitionLocalService _kaleoTransitionLocalService;

	@Reference
	private Staging _staging;

}