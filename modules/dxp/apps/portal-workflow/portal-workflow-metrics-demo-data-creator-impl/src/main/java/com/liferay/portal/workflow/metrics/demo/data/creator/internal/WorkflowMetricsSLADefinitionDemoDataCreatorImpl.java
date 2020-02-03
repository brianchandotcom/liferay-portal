/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.demo.data.creator.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.demo.data.creator.WorkflowMetricsSLADefinitionDemoDataCreator;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.util.NodeUtil;
import com.liferay.portal.workflow.metrics.rest.spi.resource.SPINodeResource;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = WorkflowMetricsSLADefinitionDemoDataCreator.class)
public class WorkflowMetricsSLADefinitionDemoDataCreatorImpl
	implements WorkflowMetricsSLADefinitionDemoDataCreator {

	@Override
	public void create(
			long companyId, Date createDate, long userId,
			long workflowDefinitionId)
		throws Exception {

		create(
			companyId, null, createDate, null, 172800000, "Review", null,
			workflowDefinitionId,
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Underwriter final review"),
					"enter"),
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId, "Underwriter review"),
					"enter")),
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Underwriter final review"),
					"leave"),
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId, "Underwriter review"),
					"leave")),
			userId);
		create(
			companyId, null, createDate, null, 259200000, "Payment", null,
			workflowDefinitionId,
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId, "Insurance payment"),
					"enter"),
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Manual billing process"),
					"enter")),
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId, "Insurance payment"),
					"leave"),
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Manual billing process"),
					"leave")),
			userId);
		create(
			companyId, null, createDate, null, 86400000, "Questions", null,
			workflowDefinitionId,
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Additional rating questions"),
					"enter")),
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Additional rating questions"),
					"leave")),
			userId);
		create(
			companyId, null, createDate, null, 604800000, "Application", null,
			workflowDefinitionId,
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Start online application"),
					"begin")),
			_toStringArray(
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Application Complete"),
					"end"),
				_getNodeKey(
					_getNodeId(
						companyId, workflowDefinitionId,
						"Application canceled"),
					"end")),
			userId);
	}

	@Override
	public WorkflowMetricsSLADefinition create(
			long companyId, String calendarKey, Date createDate,
			String description, long duration, String name,
			String[] pauseNodeKeys, long processId, String[] startNodeKeys,
			String[] stopNodeKeys, long userId)
		throws PortalException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					calendarKey, description, duration, name, pauseNodeKeys,
					processId, startNodeKeys, stopNodeKeys,
					_createServiceContext(companyId, userId));

		workflowMetricsSLADefinition.setCreateDate(createDate);
		workflowMetricsSLADefinition.setModifiedDate(createDate);

		_workflowMetricsSLADefinitionLocalService.
			updateWorkflowMetricsSLADefinition(workflowMetricsSLADefinition);

		_workflowMetricsSLADefinitionIds.add(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());

		return workflowMetricsSLADefinition;
	}

	@Override
	public void delete() throws PortalException {
		for (Long workflowMetricsSLADefinitionId :
				_workflowMetricsSLADefinitionIds) {

			_workflowMetricsSLADefinitionLocalService.
				deleteWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinitionId);
		}
	}

	private ServiceContext _createServiceContext(long companyId, long userId) {
		return new ServiceContext() {
			{
				setCompanyId(companyId);
				setUserId(userId);
			}
		};
	}

	private long _getNodeId(
			long companyId, long workflowDefinitionId, String name)
		throws Exception {

		SPINodeResource<Node> spiNodeResource = _getSPINodeResource(companyId);

		Page<Node> nodePage = spiNodeResource.getProcessNodesPage(
			workflowDefinitionId);

		Collection<Node> nodes = nodePage.getItems();

		for (Node node : nodes) {
			if (Objects.equals(node.getName(), name)) {
				return node.getId();
			}
		}

		return 0L;
	}

	private String _getNodeKey(long nodeId, String action) {
		return StringBundler.concat(nodeId, CharPool.COLON, action);
	}

	private SPINodeResource<Node> _getSPINodeResource(long companyId) {
		return new SPINodeResource<>(
			companyId, _queries, _searchRequestExecutor,
			document -> NodeUtil.toNode(
				document, _language,
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					LocaleUtil.getMostRelevantLocale(),
					WorkflowMetricsSLADefinitionDemoDataCreatorImpl.class)));
	}

	private String[] _toStringArray(String... nodeKeys) {
		if (nodeKeys == null) {
			return new String[0];
		}

		return nodeKeys;
	}

	@Reference
	private Language _language;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	private final List<Long> _workflowMetricsSLADefinitionIds =
		new CopyOnWriteArrayList<>();

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}