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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.Node;
import com.liferay.headless.admin.workflow.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.NodeUtil;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.TransitionUtil;
import com.liferay.headless.admin.workflow.internal.odata.entity.v1_0.WorkflowDefinitionEntityModel;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowDefinitionResource.class
)
public class WorkflowDefinitionResourceImpl
	extends BaseWorkflowDefinitionResourceImpl implements EntityModelResource {

	@Override
	public void deleteWorkflowDefinitionUndeploy(String name, String version)
		throws Exception {

		_workflowDefinitionManager.undeployWorkflowDefinition(
			contextCompany.getCompanyId(), contextUser.getUserId(), name,
			GetterUtil.getInteger(version));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public WorkflowDefinition getWorkflowDefinitionByName(
			String name, Integer version)
		throws Exception {

		if (version == null) {
			return _toWorkflowDefinition(
				_workflowDefinitionManager.getLatestWorkflowDefinition(
					contextCompany.getCompanyId(), name));
		}

		return _toWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				contextCompany.getCompanyId(), name, version));
	}

	@Override
	public Page<WorkflowDefinition> getWorkflowDefinitionsPage(
			Boolean active, Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_workflowDefinitionManager.getLatestWorkflowDefinitions(
					active, contextCompany.getCompanyId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					_toOrderByComparator((Sort)ArrayUtil.getValue(sorts, 0))),
				this::_toWorkflowDefinition),
			pagination,
			_workflowDefinitionManager.getLatestWorkflowDefinitionsCount(
				active, contextCompany.getCompanyId()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionDeploy(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		String content = workflowDefinition.getContent();

		return _toWorkflowDefinition(
			_workflowDefinitionManager.deployWorkflowDefinition(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				_getTitle(workflowDefinition), workflowDefinition.getName(),
				content.getBytes()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionSave(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		String content = workflowDefinition.getContent();

		return _toWorkflowDefinition(
			_workflowDefinitionManager.saveWorkflowDefinition(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				_getTitle(workflowDefinition), workflowDefinition.getName(),
				content.getBytes()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionUpdateActive(
			Boolean active, String name, String version)
		throws Exception {

		return _toWorkflowDefinition(
			_workflowDefinitionManager.updateActive(
				contextCompany.getCompanyId(), contextUser.getUserId(), name,
				GetterUtil.getInteger(version), active));
	}

	private String _getTitle(WorkflowDefinition workflowDefinition)
		throws Exception {

		if (MapUtil.isEmpty(workflowDefinition.getTitle_i18n())) {
			return workflowDefinition.getTitle();
		}

		return _localization.getXml(
			workflowDefinition.getTitle_i18n(),
			_language.getLanguageId(contextCompany.getLocale()), "title");
	}

	private OrderByComparator
		<com.liferay.portal.kernel.workflow.WorkflowDefinition>
			_toOrderByComparator(Sort sort) {

		if (sort == null) {
			return _workflowComparatorFactory.
				getDefinitionModifiedDateComparator(false);
		}

		if (StringUtil.equals(sort.getFieldName(), "name")) {
			return _workflowComparatorFactory.getDefinitionNameComparator(
				!sort.isReverse());
		}

		return _workflowComparatorFactory.getDefinitionModifiedDateComparator(
			!sort.isReverse());
	}

	private WorkflowDefinition _toWorkflowDefinition(
		com.liferay.portal.kernel.workflow.WorkflowDefinition
			workflowDefinition) {

		return new WorkflowDefinition() {
			{
				active = workflowDefinition.isActive();
				content = workflowDefinition.getContent();
				dateCreated = workflowDefinition.getCreateDate();
				dateModified = workflowDefinition.getModifiedDate();
				description = workflowDefinition.getDescription();
				id = workflowDefinition.getWorkflowDefinitionId();
				name = workflowDefinition.getName();
				nodes = transformToArray(
					workflowDefinition.getWorkflowNodes(),
					workflowNode -> NodeUtil.toNode(
						contextAcceptLanguage.getPreferredLocale(),
						workflowNode),
					Node.class);
				title = workflowDefinition.getTitle(
					_language.getLanguageId(
						contextAcceptLanguage.getPreferredLocale()));
				title_i18n = Stream.of(
					_localization.getLocalizationMap(
						workflowDefinition.getTitle())
				).map(
					Map::entrySet
				).flatMap(
					Set::stream
				).collect(
					Collectors.toMap(
						entry -> _language.getLanguageId(entry.getKey()),
						Map.Entry::getValue)
				);
				transitions = transformToArray(
					workflowDefinition.getWorkflowTransitions(),
					workflowTransition -> TransitionUtil.toTransition(
						contextAcceptLanguage.getPreferredLocale(),
						workflowTransition),
					Transition.class);
				version = String.valueOf(workflowDefinition.getVersion());
			}
		};
	}

	private static final EntityModel _entityModel =
		new WorkflowDefinitionEntityModel();

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

	@Reference
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}