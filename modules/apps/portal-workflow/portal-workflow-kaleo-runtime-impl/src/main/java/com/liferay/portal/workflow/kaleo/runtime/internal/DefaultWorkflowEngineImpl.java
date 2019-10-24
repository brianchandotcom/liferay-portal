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

package com.liferay.portal.workflow.kaleo.runtime.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.definition.Definition;
import com.liferay.portal.workflow.kaleo.definition.deployment.WorkflowDeployer;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowModelParser;
import com.liferay.portal.workflow.kaleo.definition.parser.WorkflowValidator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.KaleoSignaler;
import com.liferay.portal.workflow.kaleo.runtime.WorkflowEngine;
import com.liferay.portal.workflow.kaleo.runtime.internal.node.NodeExecutorFactory;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoInstanceOrderByComparator;

import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = AopService.class)
@Transactional(
	isolation = Isolation.PORTAL, propagation = Propagation.REQUIRED,
	rollbackFor = Exception.class
)
public class DefaultWorkflowEngineImpl
	extends BaseKaleoBean implements AopService, WorkflowEngine {

	@Override
	public void deleteWorkflowDefinition(
			String name, int version, ServiceContext serviceContext)
		throws WorkflowException {

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionLocalService.fetchKaleoDefinition(
					name, serviceContext);

			if (kaleoDefinition != null) {
				kaleoDefinitionLocalService.deleteKaleoDefinition(
					name, serviceContext);
			}
			else {
				kaleoDefinitionVersionLocalService.
					deleteKaleoDefinitionVersions(
						serviceContext.getCompanyId(), name);
			}
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public void deleteWorkflowInstance(
			long workflowInstanceId, ServiceContext serviceContext)
		throws WorkflowException {

		try {
			kaleoInstanceLocalService.deleteKaleoInstance(workflowInstanceId);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowDefinition deployWorkflowDefinition(
			String title, String name, InputStream inputStream,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			Definition definition = _workflowModelParser.parse(inputStream);

			if (_workflowValidator != null) {
				_workflowValidator.validate(definition);
			}

			String definitionName = getDefinitionName(definition, name);

			KaleoDefinition kaleoDefinition =
				kaleoDefinitionLocalService.fetchKaleoDefinition(
					definitionName, serviceContext);

			WorkflowDefinition workflowDefinition = _workflowDeployer.deploy(
				title, definitionName, definition, serviceContext);

			if (kaleoDefinition != null) {
				List<WorkflowDefinitionLink> workflowDefinitionLinks =
					workflowDefinitionLinkLocalService.
						getWorkflowDefinitionLinks(
							serviceContext.getCompanyId(),
							kaleoDefinition.getName(),
							kaleoDefinition.getVersion());

				for (WorkflowDefinitionLink workflowDefinitionLink :
						workflowDefinitionLinks) {

					workflowDefinitionLink.setWorkflowDefinitionVersion(
						workflowDefinition.getVersion());

					workflowDefinitionLinkLocalService.
						updateWorkflowDefinitionLink(workflowDefinitionLink);
				}
			}

			return workflowDefinition;
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (PortalException pe) {
			throw new WorkflowException(pe);
		}
	}

	@Override
	public ExecutionContext executeTimerWorkflowInstance(
			long kaleoTimerInstanceTokenId, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws WorkflowException {

		try {
			KaleoTimerInstanceToken kaleoTimerInstanceToken =
				kaleoTimerInstanceTokenLocalService.getKaleoTimerInstanceToken(
					kaleoTimerInstanceTokenId);

			KaleoInstanceToken kaleoInstanceToken =
				kaleoTimerInstanceToken.getKaleoInstanceToken();

			final ExecutionContext executionContext = new ExecutionContext(
				kaleoInstanceToken, kaleoTimerInstanceToken, workflowContext,
				serviceContext);

			executionContext.setKaleoTaskInstanceToken(
				kaleoTimerInstanceToken.getKaleoTaskInstanceToken());

			final KaleoNode currentKaleoNode =
				kaleoInstanceToken.getCurrentKaleoNode();

			NodeExecutor nodeExecutor = _nodeExecutorFactory.getNodeExecutor(
				currentKaleoNode.getType());

			nodeExecutor.executeTimer(currentKaleoNode, executionContext);

			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_kaleoSignaler.signalExecute(
							currentKaleoNode, executionContext);

						return null;
					}

				});

			return executionContext;
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<String> getNextTransitionNames(
			long workflowInstanceId, ServiceContext serviceContext)
		throws WorkflowException {

		try {
			KaleoInstance kaleoInstance =
				kaleoInstanceLocalService.getKaleoInstance(workflowInstanceId);

			KaleoInstanceToken rootKaleoInstanceToken =
				kaleoInstance.getRootKaleoInstanceToken(null, serviceContext);

			List<String> transitionNames = new ArrayList<>();

			getNextTransitionNames(rootKaleoInstanceToken, transitionNames);

			return transitionNames;
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowInstance getWorkflowInstance(
			long workflowInstanceId, ServiceContext serviceContext)
		throws WorkflowException {

		try {
			KaleoInstance kaleoInstance =
				kaleoInstanceLocalService.getKaleoInstance(workflowInstanceId);

			return _kaleoWorkflowModelConverter.toWorkflowInstance(
				kaleoInstance,
				kaleoInstance.getRootKaleoInstanceToken(serviceContext));
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowInstanceCount(
			Long userId, String assetClassName, Long assetClassPK,
			Boolean completed, ServiceContext serviceContext)
		throws WorkflowException {

		try {
			return kaleoInstanceLocalService.getKaleoInstancesCount(
				userId, assetClassName, assetClassPK, completed,
				serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowInstanceCount(
			Long userId, String[] assetClassNames, Boolean completed,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			return kaleoInstanceLocalService.getKaleoInstancesCount(
				userId, assetClassNames, completed, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public int getWorkflowInstanceCount(
			String workflowDefinitionName, int workflowDefinitionVersion,
			boolean completed, ServiceContext serviceContext)
		throws WorkflowException {

		try {
			return kaleoInstanceLocalService.getKaleoInstancesCount(
				workflowDefinitionName, workflowDefinitionVersion, completed,
				serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowInstance> getWorkflowInstances(
			Long userId, String assetClassName, Long assetClassPK,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			List<KaleoInstance> kaleoInstances =
				kaleoInstanceLocalService.getKaleoInstances(
					userId, assetClassName, assetClassPK, completed, start, end,
					KaleoInstanceOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter,
						serviceContext),
					serviceContext);

			return toWorkflowInstances(kaleoInstances, serviceContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowInstance> getWorkflowInstances(
			Long userId, String[] assetClassNames, Boolean completed, int start,
			int end, OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			List<KaleoInstance> kaleoInstances =
				kaleoInstanceLocalService.getKaleoInstances(
					userId, assetClassNames, completed, start, end,
					KaleoInstanceOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter,
						serviceContext),
					serviceContext);

			return toWorkflowInstances(kaleoInstances, serviceContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public List<WorkflowInstance> getWorkflowInstances(
			String workflowDefinitionName, int workflowDefinitionVersion,
			boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			List<KaleoInstance> kaleoInstances =
				kaleoInstanceLocalService.getKaleoInstances(
					workflowDefinitionName, workflowDefinitionVersion,
					completed, start, end,
					KaleoInstanceOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter,
						serviceContext),
					serviceContext);

			return toWorkflowInstances(kaleoInstances, serviceContext);
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
			String title, String name, byte[] bytes,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			Definition definition = getDefinition(bytes);

			String definitionName = _getDefinitionName(
				definition, name, serviceContext);

			return _workflowDeployer.save(
				title, definitionName, definition, serviceContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #search(Long,
	 *             String, String, String, String, String, Boolean, int, int,
	 *             OrderByComparator, ServiceContext)}
	 */
	@Deprecated
	@Override
	public List<WorkflowInstance> search(
			Long userId, String assetClassName, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException {

		return search(
			userId, assetClassName, null, null, nodeName, kaleoDefinitionName,
			completed, start, end, orderByComparator, serviceContext);
	}

	@Override
	public List<WorkflowInstance> search(
			Long userId, String assetClassName, String assetTitle,
			String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			List<KaleoInstance> kaleoInstances =
				kaleoInstanceLocalService.search(
					userId, assetClassName, assetTitle, assetDescription,
					nodeName, kaleoDefinitionName, completed, start, end,
					KaleoInstanceOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter,
						serviceContext),
					serviceContext);

			return toWorkflowInstances(kaleoInstances, serviceContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #searchCount(Long,
	 *             String, String, String, String, String, Boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public int searchCount(
			Long userId, String assetClassName, String nodeName,
			String kaleoDefinitionName, Boolean completed,
			ServiceContext serviceContext)
		throws WorkflowException {

		return searchCount(
			userId, assetClassName, null, null, nodeName, kaleoDefinitionName,
			completed, serviceContext);
	}

	@Override
	public int searchCount(
			Long userId, String assetClassName, String assetTitle,
			String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			return kaleoInstanceLocalService.searchCount(
				userId, assetClassName, assetTitle, assetDescription, nodeName,
				kaleoDefinitionName, completed, serviceContext);
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowInstance signalWorkflowInstance(
			long workflowInstanceId, final String transitionName,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			KaleoInstance kaleoInstance = doUpdateContext(
				workflowInstanceId, workflowContext, serviceContext);

			KaleoInstanceToken kaleoInstanceToken =
				kaleoInstance.getRootKaleoInstanceToken(serviceContext);

			if (Validator.isNotNull(transitionName)) {

				// Validate that the transition actually exists before moving
				// forward

				KaleoNode currentKaleoNode =
					kaleoInstanceToken.getCurrentKaleoNode();

				currentKaleoNode.getKaleoTransition(transitionName);
			}

			serviceContext.setScopeGroupId(kaleoInstanceToken.getGroupId());

			final ExecutionContext executionContext = new ExecutionContext(
				kaleoInstanceToken, workflowContext, serviceContext);

			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						try {
							_kaleoSignaler.signalExit(
								transitionName, executionContext);
						}
						catch (Exception e) {
							throw new WorkflowException(
								"Unable to signal next transition", e);
						}

						return null;
					}

				});

			return _kaleoWorkflowModelConverter.toWorkflowInstance(
				kaleoInstance, kaleoInstanceToken, workflowContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowInstance startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			final String transitionName,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionLocalService.getKaleoDefinition(
					workflowDefinitionName, serviceContext);

			if (!kaleoDefinition.isActive()) {
				throw new WorkflowException(
					StringBundler.concat(
						"Inactive workflow definition with name ",
						workflowDefinitionName, " and version ",
						workflowDefinitionVersion));
			}

			KaleoDefinitionVersion kaleoDefinitionVersion =
				kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					serviceContext.getCompanyId(), workflowDefinitionName,
					getVersion(workflowDefinitionVersion));

			KaleoNode kaleoStartNode =
				kaleoDefinitionVersion.getKaleoStartNode();

			if (Validator.isNotNull(transitionName)) {

				// Validate that the transition actually exists before moving
				// forward

				kaleoStartNode.getKaleoTransition(transitionName);
			}

			long scopeGroupId = serviceContext.getScopeGroupId();

			if (scopeGroupId != WorkflowConstants.DEFAULT_GROUP_ID) {
				Group group = _groupLocalService.getGroup(scopeGroupId);

				if (group.isLayout()) {
					group = _groupLocalService.getGroup(
						group.getParentGroupId());

					serviceContext.setScopeGroupId(group.getGroupId());
				}
			}

			KaleoInstance kaleoInstance =
				kaleoInstanceLocalService.addKaleoInstance(
					kaleoDefinitionVersion.getKaleoDefinitionVersionId(),
					kaleoDefinitionVersion.getName(),
					getVersion(kaleoDefinitionVersion.getVersion()),
					workflowContext, serviceContext);

			KaleoInstanceToken rootKaleoInstanceToken =
				kaleoInstance.getRootKaleoInstanceToken(
					workflowContext, serviceContext);

			rootKaleoInstanceToken.setCurrentKaleoNode(kaleoStartNode);

			kaleoLogLocalService.addWorkflowInstanceStartKaleoLog(
				rootKaleoInstanceToken, serviceContext);

			final ExecutionContext executionContext = new ExecutionContext(
				rootKaleoInstanceToken, workflowContext, serviceContext);

			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						try {
							_kaleoSignaler.signalEntry(
								transitionName, executionContext);
						}
						catch (Exception e) {
							throw new WorkflowException(
								"Unable to start workflow", e);
						}

						return null;
					}

				});

			return _kaleoWorkflowModelConverter.toWorkflowInstance(
				kaleoInstance, rootKaleoInstanceToken, workflowContext);
		}
		catch (WorkflowException we) {
			throw we;
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public WorkflowInstance updateContext(
			long workflowInstanceId, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException {

		try {
			KaleoInstance kaleoInstance = doUpdateContext(
				workflowInstanceId, workflowContext, serviceContext);

			return _kaleoWorkflowModelConverter.toWorkflowInstance(
				kaleoInstance,
				kaleoInstance.getRootKaleoInstanceToken(serviceContext));
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	@Override
	public void validateWorkflowDefinition(InputStream inputStream)
		throws WorkflowException {

		try {
			if (_workflowValidator != null) {
				Definition definition = _workflowModelParser.parse(inputStream);

				_workflowValidator.validate(definition);
			}
		}
		catch (Exception e) {
			throw new WorkflowException(e);
		}
	}

	protected KaleoInstance doUpdateContext(
			long workflowInstanceId, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws Exception {

		return kaleoInstanceLocalService.updateKaleoInstance(
			workflowInstanceId, workflowContext, serviceContext);
	}

	protected Definition getDefinition(byte[] bytes) throws WorkflowException {
		try {
			_workflowModelParser.setValidate(false);

			return _workflowModelParser.parse(
				new UnsyncByteArrayInputStream(bytes));
		}
		catch (WorkflowDefinitionFileException wdfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(wdfe, wdfe);
			}

			try {
				return new Definition(
					StringPool.BLANK, StringPool.BLANK,
					new String(bytes, "UTF-8"), 0);
			}
			catch (UnsupportedEncodingException uee) {
				throw new WorkflowException(uee);
			}
		}
		catch (WorkflowException we) {
			throw new WorkflowException(we);
		}
		finally {
			_workflowModelParser.setValidate(true);
		}
	}

	protected String getDefinitionName(Definition definition, String name) {
		if (Validator.isNotNull(name)) {
			return name;
		}

		if (Validator.isNotNull(definition.getName())) {
			return definition.getName();
		}

		return portalUUID.generate();
	}

	protected void getNextTransitionNames(
			KaleoInstanceToken kaleoInstanceToken, List<String> transitionNames)
		throws Exception {

		if (kaleoInstanceToken.hasIncompleteChildrenKaleoInstanceToken()) {
			List<KaleoInstanceToken> incompleteChildrenKaleoInstanceTokens =
				kaleoInstanceToken.getIncompleteChildrenKaleoInstanceTokens();

			for (KaleoInstanceToken incompleteChildrenKaleoInstanceToken :
					incompleteChildrenKaleoInstanceTokens) {

				getNextTransitionNames(
					incompleteChildrenKaleoInstanceToken, transitionNames);
			}
		}
		else {
			KaleoNode kaleoNode = kaleoInstanceToken.getCurrentKaleoNode();

			List<KaleoTransition> kaleoTransitions =
				kaleoNode.getKaleoTransitions();

			for (KaleoTransition kaleoTransition : kaleoTransitions) {
				transitionNames.add(kaleoTransition.getName());
			}
		}
	}

	protected String getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	protected int getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

	protected List<WorkflowInstance> toWorkflowInstances(
			List<KaleoInstance> kaleoInstances, ServiceContext serviceContext)
		throws PortalException {

		List<WorkflowInstance> workflowInstances = new ArrayList<>(
			kaleoInstances.size());

		for (KaleoInstance kaleoInstance : kaleoInstances) {
			workflowInstances.add(
				_kaleoWorkflowModelConverter.toWorkflowInstance(
					kaleoInstance,
					kaleoInstance.getRootKaleoInstanceToken(serviceContext)));
		}

		return workflowInstances;
	}

	@Reference
	protected PortalUUID portalUUID;

	private String _getDefinitionName(
		Definition definition, String name, ServiceContext serviceContext) {

		if (Validator.isNotNull(name)) {
			return name;
		}

		if (Validator.isNotNull(definition.getName())) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionLocalService.fetchKaleoDefinition(
					definition.getName(), serviceContext);

			if ((kaleoDefinition != null) && kaleoDefinition.isActive()) {
				return portalUUID.generate();
			}

			return definition.getName();
		}

		return portalUUID.generate();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultWorkflowEngineImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private KaleoSignaler _kaleoSignaler;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference
	private NodeExecutorFactory _nodeExecutorFactory;

	@Reference
	private WorkflowDeployer _workflowDeployer;

	@Reference
	private WorkflowModelParser _workflowModelParser;

	@Reference
	private WorkflowValidator _workflowValidator;

}