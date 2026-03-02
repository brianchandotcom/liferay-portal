/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.ai.hub.rest.dto.v1_0.AgentDefinition;
import com.liferay.ai.hub.rest.dto.v1_0.Variable;
import com.liferay.ai.hub.rest.internal.resource.v1_0.AgentDefinitionResourceImpl;
import com.liferay.ai.hub.rest.manager.v1_0.AgentDefinitionManager;
import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = AgentDefinitionManager.class)
public class AgentDefinitionManagerImpl implements AgentDefinitionManager {

	@Override
	public void deleteAgentDefinition(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);

		_objectEntryManager.deleteObjectEntry(
			companyId, dtoConverterContext,
			objectEntry.getExternalReferenceCode(),
			_getObjectDefinition(companyId), null);

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				companyId,
				GetterUtil.getString(
					objectEntry.getPropertyValue("workflowDefinitionName")));

		_workflowDefinitionManager.updateActive(
			workflowDefinition.getCompanyId(), dtoConverterContext.getUserId(),
			workflowDefinition.getName(), workflowDefinition.getVersion(),
			false);

		_workflowDefinitionManager.undeployWorkflowDefinition(
			workflowDefinition.getCompanyId(), dtoConverterContext.getUserId(),
			workflowDefinition.getName(), workflowDefinition.getVersion());
	}

	@Override
	public Page<AgentDefinition> getAgentDefinitionsPage(
			long companyId, DTOConverterContext dtoConverterContext,
			String filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		Map<String, Map<String, String>> actions = null;

		if (dtoConverterContext != null) {
			actions = HashMapBuilder.<String, Map<String, String>>put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, AgentDefinitionResourceImpl.class, null,
					"getAgentDefinitionsPage",
					_kaleoDefinitionModelResourcePermission, (Long)null,
					dtoConverterContext.getUriInfo())
			).build();
		}

		Page<ObjectEntry> page = _objectEntryManager.getObjectEntries(
			companyId, _getObjectDefinition(companyId), null, null,
			dtoConverterContext, filter, pagination, search, sorts);

		return Page.of(
			actions,
			TransformUtil.transform(
				page.getItems(),
				objectEntry -> _toAgentDefinition(
					companyId, dtoConverterContext, objectEntry)),
			pagination, page.getTotalCount());
	}

	@Override
	public AgentDefinition patchAgentDefinitionUpdateActive(
			boolean active, long companyId,
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryManager.partialUpdateObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId),
			new ObjectEntry() {
				{
					setProperties(() -> Map.of("active", active));
				}
			},
			null);

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				companyId,
				GetterUtil.getString(
					objectEntry.getPropertyValue("workflowDefinitionName")));

		_workflowDefinitionManager.updateActive(
			companyId, dtoConverterContext.getUserId(),
			workflowDefinition.getName(), workflowDefinition.getVersion(),
			active);

		return _toAgentDefinition(companyId, dtoConverterContext, objectEntry);
	}

	@Override
	public AgentDefinition postAgentDefinitionCopy(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			_getObjectDefinition(companyId), null);

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				companyId,
				GetterUtil.getString(
					objectEntry.getPropertyValue("workflowDefinitionName")));

		String content = workflowDefinition.getContent();

		Locale locale = dtoConverterContext.getLocale();

		String workflowDefinitionName = PortalUUIDUtil.generate();

		_workflowDefinitionManager.deployWorkflowDefinition(
			null, companyId, workflowDefinition.getGroupId(),
			dtoConverterContext.getUserId(),
			LanguageUtil.format(
				locale, "copy-of-x",
				workflowDefinition.getTitle(locale.getDisplayLanguage())),
			workflowDefinitionName, WorkflowDefinitionConstants.SCOPE_AI,
			content.getBytes());

		AccountEntry accountEntry = AccountEntryUtil.getUserAccountEntry(
			dtoConverterContext.getUserId());

		long accountEntryId =
			(accountEntry != null) ? accountEntry.getAccountEntryId() : 0;

		Map<String, String> title =
			(Map<String, String>)objectEntry.getPropertyValue("title_i18n");

		title.replaceAll(
			(key, value) -> LanguageUtil.format(locale, "copy-of-x", value));

		return _toAgentDefinition(
			companyId, dtoConverterContext,
			_objectEntryManager.addObjectEntry(
				dtoConverterContext, _getObjectDefinition(companyId),
				new ObjectEntry() {
					{
						setProperties(
							() -> Map.of(
								"active",
								GetterUtil.getBoolean(
									objectEntry.getPropertyValue("active")),
								"description",
								GetterUtil.getString(
									objectEntry.getPropertyValue(
										"description")),
								"inputVariables",
								GetterUtil.getString(
									objectEntry.getPropertyValue(
										"inputVariables")),
								"outputVariable",
								GetterUtil.getString(
									objectEntry.getPropertyValue(
										"outputVariable")),
								"r_accountToAIHubAgentDefinitions_" +
									"accountEntryId",
								accountEntryId, "title_i18n", title,
								"workflowDefinitionName",
								workflowDefinitionName));
					}
				},
				null));
	}

	private Map<String, String> _addAction(
		DTOConverterContext dtoConverterContext, String methodName,
		WorkflowDefinition workflowDefinition) {

		if (!Objects.equals(
				methodName, "postAgentDefinitionByExternalReferenceCodeCopy") &&
			workflowDefinition.isSystem()) {

			return null;
		}

		return ActionUtil.addAction(
			ActionKeys.ADD_DEFINITION, AgentDefinitionResourceImpl.class,
			workflowDefinition.getWorkflowDefinitionId(), methodName,
			_kaleoDefinitionModelResourcePermission, (Long)null,
			dtoConverterContext.getUriInfo());
	}

	private ObjectDefinition _getObjectDefinition(long companyId)
		throws Exception {

		return _objectDefinitionLocalService.getObjectDefinition(
			companyId, "AIHubAgentDefinition");
	}

	private AgentDefinition _toAgentDefinition(
			long companyId, DTOConverterContext dtoConverterContext,
			ObjectEntry objectEntry)
		throws PortalException {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				companyId,
				GetterUtil.getString(
					objectEntry.getPropertyValue("workflowDefinitionName")));

		return new AgentDefinition() {
			{
				if (dtoConverterContext != null) {
					setActions(
						() -> HashMapBuilder.put(
							"activate",
							() -> {
								if (workflowDefinition.isActive()) {
									return null;
								}

								return _addAction(
									dtoConverterContext,
									"patchAgentDefinitionByExternalReference" +
										"CodeUpdateActive",
									workflowDefinition);
							}
						).put(
							"copy",
							_addAction(
								dtoConverterContext,
								"postAgentDefinitionByExternalReferenceCode" +
									"Copy",
								workflowDefinition)
						).put(
							"deactivate",
							() -> {
								if (!workflowDefinition.isActive()) {
									return null;
								}

								return _addAction(
									dtoConverterContext,
									"patchAgentDefinitionByExternalReference" +
										"CodeUpdateActive",
									workflowDefinition);
							}
						).put(
							"delete",
							() -> _addAction(
								dtoConverterContext,
								"deleteAgentDefinitionByExternalReferenceCode",
								workflowDefinition)
						).build());
				}

				setActive(
					() -> GetterUtil.getBoolean(
						objectEntry.getPropertyValue("active")));
				setDescription(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("description")));
				setExternalReferenceCode(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("externalReferenceCode")));
				setId(
					() -> GetterUtil.getLong(
						objectEntry.getPropertyValue("id")));
				setInputVariables(
					() -> TransformUtil.transform(
						StringUtil.split(
							GetterUtil.getString(
								objectEntry.getPropertyValue(
									"inputVariables"))),
						inputVariable -> _toVariable(inputVariable),
						Variable.class));
				setOutputVariable(
					() -> _toVariable(
						GetterUtil.getString(
							objectEntry.getPropertyValue("outputVariable"))));
				setTitle(
					() -> GetterUtil.getString(
						objectEntry.getPropertyValue("title")));
				setVersion(workflowDefinition::getVersion);
				setWorkflowDefinitionName(workflowDefinition::getName);
			}
		};
	}

	private Variable _toVariable(String variableName) {
		return new Variable() {
			{
				setName(() -> variableName);
				setType(() -> "string");
			}
		};
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoDefinition)"
	)
	private ModelResourcePermission<KaleoDefinition>
		_kaleoDefinitionModelResourcePermission;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}