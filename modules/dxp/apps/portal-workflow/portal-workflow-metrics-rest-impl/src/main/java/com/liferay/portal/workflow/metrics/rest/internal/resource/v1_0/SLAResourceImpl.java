/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.NodeKey;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.PauseNodeKeys;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.StartNodeKeys;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.StopNodeKeys;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sla.properties",
	scope = ServiceScope.PROTOTYPE, service = SLAResource.class
)
public class SLAResourceImpl extends BaseSLAResourceImpl {

	@Override
	public void deleteSLA(Long slaId) throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			deactivateWorkflowMetricsSLADefinition(
				slaId, _createServiceContext());
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), for backwards compatibility as the path is incorrect, the id is not needed
	 *
	 * Invoke this method with the command line:
	 * curl -X 'DELETE' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}/batch'  -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@DELETE
	@Deprecated
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "slaId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/slas/{slaId}/batch")
	@Produces("application/json")
	@Tags({@Tag(name = "SLA")})
	public Response deleteSLABatch(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		return deleteSLABatch(callbackURL, object);
	}

	@Override
	public Page<SLA> getProcessSLAsPage(
			Long processId, Integer status, Pagination pagination)
		throws Exception {

		if (status != null) {
			return Page.of(
				transform(
					_workflowMetricsSLADefinitionLocalService.
						getWorkflowMetricsSLADefinitions(
							contextCompany.getCompanyId(), true, processId,
							status, pagination.getStartPosition(),
							pagination.getEndPosition(), null),
					this::_toSLA),
				pagination,
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitionsCount(
						contextCompany.getCompanyId(), true, processId,
						status));
		}

		int draftCount =
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitionsCount(
					contextCompany.getCompanyId(), true, processId,
					WorkflowConstants.STATUS_DRAFT);

		if (draftCount == 0) {
			return Page.of(
				transform(
					_workflowMetricsSLADefinitionLocalService.
						getWorkflowMetricsSLADefinitions(
							contextCompany.getCompanyId(), true, processId,
							WorkflowConstants.STATUS_APPROVED,
							pagination.getStartPosition(),
							pagination.getEndPosition(), null),
					this::_toSLA),
				pagination,
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitionsCount(
						contextCompany.getCompanyId(), true, processId));
		}

		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions =
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitions(
					contextCompany.getCompanyId(), true, processId,
					WorkflowConstants.STATUS_DRAFT,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		if (workflowMetricsSLADefinitions.size() < pagination.getPageSize()) {
			workflowMetricsSLADefinitions = new ArrayList<>(
				workflowMetricsSLADefinitions);

			workflowMetricsSLADefinitions.addAll(
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinitions(
						contextCompany.getCompanyId(), true, processId,
						WorkflowConstants.STATUS_APPROVED,
						pagination.getStartPosition() +
							workflowMetricsSLADefinitions.size() - draftCount,
						pagination.getEndPosition() - draftCount, null));
		}

		return Page.of(
			transform(workflowMetricsSLADefinitions, this::_toSLA), pagination,
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitionsCount(
					contextCompany.getCompanyId(), true, processId));
	}

	@Override
	public SLA getSLA(Long slaId) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinition(slaId, true));
	}

	@Override
	public SLA postProcessSLA(Long processId, SLA sla) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					sla.getCalendarKey(), sla.getDescription(),
					sla.getDuration(), sla.getName(),
					_toStringArray(sla.getPauseNodeKeys()), processId,
					_toStringArray(sla.getStartNodeKeys()),
					_toStringArray(sla.getStopNodeKeys()),
					_createServiceContext()));
	}

	@Override
	public SLA putSLA(Long slaId, SLA sla) throws Exception {
		return _toSLA(
			_workflowMetricsSLADefinitionLocalService.
				updateWorkflowMetricsSLADefinition(
					slaId, sla.getCalendarKey(), sla.getDescription(),
					sla.getDuration(), sla.getName(),
					_toStringArray(sla.getPauseNodeKeys()),
					_toStringArray(sla.getStartNodeKeys()),
					_toStringArray(sla.getStopNodeKeys()),
					GetterUtil.getInteger(
						sla.getStatus(), WorkflowConstants.STATUS_APPROVED),
					_createServiceContext()));
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), for backwards compatibility as the path is incorrect, the id is not needed
	 *
	 * Invoke this method with the command line:
	 * curl -X 'PUT' 'http://localhost:8080/o/portal-workflow-metrics/v1.0/slas/{slaId}/batch' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@Consumes("application/json")
	@Deprecated
	@Parameters(
		{
			@Parameter(in = ParameterIn.PATH, name = "slaId"),
			@Parameter(in = ParameterIn.QUERY, name = "callbackURL")
		}
	)
	@Path("/slas/{slaId}/batch")
	@Produces("application/json")
	@PUT
	@Tags({@Tag(name = "SLA")})
	public Response putSLABatch(
			@NotNull @Parameter(hidden = true) @PathParam("slaId") Long slaId,
			SLA sla,
			@Parameter(hidden = true) @QueryParam("callbackURL") String
				callbackURL,
			Object object)
		throws Exception {

		return putSLABatch(callbackURL, object);
	}

	private ServiceContext _createServiceContext() {
		return new ServiceContext() {
			{
				setCompanyId(contextCompany.getCompanyId());
				setUserId(_getUserId());
			}
		};
	}

	private long _getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	private NodeKey[] _toNodeKeys(String nodeKeysString) {
		return transform(
			StringUtil.split(nodeKeysString),
			nodeKey -> {
				String[] nodeKeyStringParts = StringUtil.split(
					nodeKey, StringPool.COLON);

				return new NodeKey() {
					{
						setExecutionType(
							() -> {
								if (nodeKeyStringParts.length == 1) {
									return StringPool.BLANK;
								}

								return nodeKeyStringParts[1];
							});
						setId(() -> nodeKeyStringParts[0]);
					}
				};
			},
			NodeKey.class);
	}

	private SLA _toSLA(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		return new SLA() {
			{
				setCalendarKey(workflowMetricsSLADefinition::getCalendarKey);
				setDateModified(workflowMetricsSLADefinition::getModifiedDate);
				setDescription(workflowMetricsSLADefinition::getDescription);
				setDuration(workflowMetricsSLADefinition::getDuration);
				setId(workflowMetricsSLADefinition::getPrimaryKey);
				setName(workflowMetricsSLADefinition::getName);
				setPauseNodeKeys(
					() -> {
						String nodeKeysString =
							workflowMetricsSLADefinition.getPauseNodeKeys();

						if (Validator.isNull(nodeKeysString)) {
							return null;
						}

						return new PauseNodeKeys() {
							{
								setNodeKeys(() -> _toNodeKeys(nodeKeysString));
								setStatus(
									() -> WorkflowConstants.STATUS_APPROVED);
							}
						};
					});
				setProcessId(workflowMetricsSLADefinition::getProcessId);
				setStartNodeKeys(
					() -> {
						String nodeKeysString =
							workflowMetricsSLADefinition.getStartNodeKeys();

						if (Validator.isNull(nodeKeysString)) {
							return null;
						}

						return new StartNodeKeys() {
							{
								setNodeKeys(() -> _toNodeKeys(nodeKeysString));
								setStatus(() -> _toStatus(nodeKeysString));
							}
						};
					});
				setStatus(workflowMetricsSLADefinition::getStatus);
				setStopNodeKeys(
					() -> {
						String nodeKeysString =
							workflowMetricsSLADefinition.getStopNodeKeys();

						if (Validator.isNull(nodeKeysString)) {
							return null;
						}

						return new StopNodeKeys() {
							{
								setNodeKeys(() -> _toNodeKeys(nodeKeysString));
								setStatus(() -> _toStatus(nodeKeysString));
							}
						};
					});
			}
		};
	}

	private int _toStatus(String nodeKeysString) {
		if (Validator.isNull(nodeKeysString)) {
			return WorkflowConstants.STATUS_DRAFT;
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	private String[] _toStringArray(NodeKey[] nodeKeys) {
		return transform(
			nodeKeys,
			nodeKey -> {
				if (Validator.isNull(nodeKey.getExecutionType())) {
					return nodeKey.getId();
				}

				return StringBundler.concat(
					nodeKey.getId(), CharPool.COLON,
					nodeKey.getExecutionType());
			},
			String.class);
	}

	private String[] _toStringArray(PauseNodeKeys pauseNodeKeys) {
		if (pauseNodeKeys == null) {
			return null;
		}

		return _toStringArray(pauseNodeKeys.getNodeKeys());
	}

	private String[] _toStringArray(StartNodeKeys startNodeKeys) {
		if (startNodeKeys == null) {
			return null;
		}

		return _toStringArray(startNodeKeys.getNodeKeys());
	}

	private String[] _toStringArray(StopNodeKeys stopNodeKeys) {
		if (stopNodeKeys == null) {
			return null;
		}

		return _toStringArray(stopNodeKeys.getNodeKeys());
	}

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}