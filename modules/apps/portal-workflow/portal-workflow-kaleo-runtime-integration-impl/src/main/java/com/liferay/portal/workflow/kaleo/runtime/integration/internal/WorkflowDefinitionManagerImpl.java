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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.runtime.WorkflowEngine;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoDefinitionOrderByComparator;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoDefinitionVersionOrderByComparator;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true, property = "proxy.bean=false",
	service = WorkflowDefinitionManager.class
)
public class WorkflowDefinitionManagerImpl
	implements WorkflowDefinitionManager {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #deployWorkflowDefinition(long, long, String, String,
	 *             byte[])}
	 */
	@Deprecated
	@Override
	public WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, String title, byte[] bytes)
		throws WorkflowException {

		Definition definition = _workflowModelParser.parse(
			new UnsyncByteArrayInputStream(bytes));

		return deployWorkflowDefinition(
			companyId, userId, title, definition.getName(), bytes);
	}

	@Override
	public WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _workflowEngine.deployWorkflowDefinition(
			title, name, new UnsyncByteArrayInputStream(bytes), serviceContext);
	}

	@Override
	public int getActiveWorkflowDefinitionCount(long companyId)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			return _kaleoDefinitionLocalService.getKaleoDefinitionsCount(
				true, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getActiveWorkflowDefinitionCount(long companyId, String name)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			return _kaleoDefinitionLocalService.getKaleoDefinitionsCount(
				name, true, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		try {
			if (orderByComparator == null) {
				orderByComparator =
					_workflowComparatorFactory.getDefinitionNameComparator(
						true);
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoDefinition> kaleoDefinitions =
				_kaleoDefinitionLocalService.getKaleoDefinitions(
					true, start, end,
					KaleoDefinitionOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			int size = kaleoDefinitions.size();

			return toWorkflowDefinitions(
				kaleoDefinitions.toArray(new KaleoDefinition[size]),
				orderByComparator);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoDefinition> kaleoDefinitions = new ArrayList<>();

			KaleoDefinition kaleoDefinition =
				_kaleoDefinitionLocalService.getKaleoDefinition(
					name, serviceContext);

			if (kaleoDefinition.isActive()) {
				kaleoDefinitions.add(kaleoDefinition);
			}

			int size = kaleoDefinitions.size();

			return toWorkflowDefinitions(
				kaleoDefinitions.toArray(new KaleoDefinition[size]),
				orderByComparator);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getLatestWorkflowDefinition(long, String)}
	 */
	@Deprecated
	@Override
	public WorkflowDefinition getLatestKaleoDefinition(
			long companyId, String name)
		throws WorkflowException {

		return getLatestWorkflowDefinition(companyId, name);
	}

	@Override
	public WorkflowDefinition getLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			KaleoDefinition kaleoDefinition =
				_kaleoDefinitionLocalService.getKaleoDefinition(
					name, serviceContext);

			return _kaleoWorkflowModelConverter.toWorkflowDefinition(
				kaleoDefinition);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowDefinition> getLatestWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoDefinition> kaleoDefinitions =
				_kaleoDefinitionLocalService.getKaleoDefinitions(
					start, end,
					KaleoDefinitionOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter),
					serviceContext);

			int size = kaleoDefinitions.size();

			return toWorkflowDefinitions(
				kaleoDefinitions.toArray(new KaleoDefinition[size]),
				orderByComparator);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws WorkflowException {

		try {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					companyId, name, getVersion(version));

			return _kaleoWorkflowModelConverter.toWorkflowDefinition(
				kaleoDefinitionVersion);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowDefinitionCount(long companyId)
		throws WorkflowException {

		try {
			return _kaleoDefinitionVersionLocalService.
				getKaleoDefinitionVersionsCount(companyId);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowDefinitionCount(long companyId, String name)
		throws WorkflowException {

		try {
			return _kaleoDefinitionVersionLocalService.
				getKaleoDefinitionVersionsCount(companyId, name);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);

			List<KaleoDefinitionVersion> kaleoDefinitionVersions =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersions(
					companyId, start, end,
					KaleoDefinitionVersionOrderByComparator.
						getOrderByComparator(
							orderByComparator, _kaleoWorkflowModelConverter));

			int size = kaleoDefinitionVersions.size();

			return toWorkflowDefinitions(
				kaleoDefinitionVersions.toArray(
					new KaleoDefinitionVersion[size]),
				orderByComparator);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		try {
			List<KaleoDefinitionVersion> kaleoDefinitionVersions =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersions(
					companyId, name);

			int size = kaleoDefinitionVersions.size();

			return toWorkflowDefinitions(
				kaleoDefinitionVersions.toArray(
					new KaleoDefinitionVersion[size]),
				orderByComparator);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowDefinition saveWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userId);

		return _workflowEngine.saveWorkflowDefinition(
			title, name, bytes, serviceContext);
	}

	@Override
	public void undeployWorkflowDefinition(
			long companyId, long userId, String name, int version)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			_workflowEngine.deleteWorkflowDefinition(
				name, version, serviceContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowDefinition updateActive(
			long companyId, long userId, String name, int version,
			boolean active)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			if (active) {
				_kaleoDefinitionLocalService.activateKaleoDefinition(
					name, version, serviceContext);
			}
			else {
				_kaleoDefinitionLocalService.deactivateKaleoDefinition(
					name, version, serviceContext);
			}

			return getWorkflowDefinition(companyId, name, version);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowDefinition updateTitle(
			long companyId, long userId, String name, int version, String title)
		throws WorkflowException {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setUserId(userId);

			KaleoDefinition kaleoDefinition =
				_kaleoDefinitionLocalService.getKaleoDefinition(
					name, serviceContext);

			String content = kaleoDefinition.getContent();

			return _workflowEngine.deployWorkflowDefinition(
				title, name, new UnsyncByteArrayInputStream(content.getBytes()),
				serviceContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public void validateWorkflowDefinition(byte[] bytes)
		throws WorkflowException {

		_workflowEngine.validateWorkflowDefinition(
			new UnsyncByteArrayInputStream(bytes));
	}

	protected String getNextVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return String.valueOf(++versionParts[0]);
	}

	protected String getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	protected List<WorkflowDefinition> toWorkflowDefinitions(
		KaleoDefinition[] kaleoDefinitions,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		List<WorkflowDefinition> workflowDefinitions = new ArrayList<>(
			kaleoDefinitions.length);

		for (KaleoDefinition kaleoDefinition : kaleoDefinitions) {
			WorkflowDefinition workflowDefinition =
				_kaleoWorkflowModelConverter.toWorkflowDefinition(
					kaleoDefinition);

			workflowDefinitions.add(workflowDefinition);
		}

		if (orderByComparator != null) {
			Collections.sort(workflowDefinitions, orderByComparator);
		}

		return workflowDefinitions;
	}

	protected List<WorkflowDefinition> toWorkflowDefinitions(
			KaleoDefinitionVersion[] kaleoDefinitionVersions,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws PortalException {

		List<WorkflowDefinition> workflowDefinitions = new ArrayList<>(
			kaleoDefinitionVersions.length);

		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				kaleoDefinitionVersions) {

			WorkflowDefinition workflowDefinition =
				_kaleoWorkflowModelConverter.toWorkflowDefinition(
					kaleoDefinitionVersion);

			workflowDefinitions.add(workflowDefinition);
		}

		if (orderByComparator != null) {
			Collections.sort(workflowDefinitions, orderByComparator);
		}

		return workflowDefinitions;
	}

	@Reference
	protected PortalUUID portalUUID;

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile WorkflowEngine _workflowEngine;

	@Reference
	private WorkflowModelParser _workflowModelParser;

}