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
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Locale;
import java.util.Map;

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
				workflowDefinition.getTitle(), workflowDefinition.getName(),
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
				workflowDefinition.getTitle(), workflowDefinition.getName(),
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

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			workflowDefinition.getTitle());

		return new WorkflowDefinition() {
			{
				active = workflowDefinition.isActive();
				content = workflowDefinition.getContent();
				dateCreated = workflowDefinition.getCreateDate();
				dateModified = workflowDefinition.getModifiedDate();
				description = workflowDefinition.getDescription();
				name = workflowDefinition.getName();
				nodes = transformToArray(
					workflowDefinition.getWorkflowNodes(),
					workflowNode -> NodeUtil.toNode(
						_language, workflowNode.getName(),
						ResourceBundleUtil.getModuleAndPortalResourceBundle(
							contextAcceptLanguage.getPreferredLocale(),
							WorkflowDefinitionResourceImpl.class),
						workflowNode.getType()),
					Node.class);
				title = titleMap.get(
					contextAcceptLanguage.getPreferredLocale());
				title_i18n = LocalizedMapUtil.getI18nMap(
					contextAcceptLanguage.isAcceptAllLanguages(),
					LocalizationUtil.getLocalizationMap(
						workflowDefinition.getTitle()));
				transitions = transformToArray(
					workflowDefinition.getWorkflowTransitions(),
					workflowTransition -> TransitionUtil.toTransition(
						_language, workflowTransition.getName(),
						ResourceBundleUtil.getModuleAndPortalResourceBundle(
							contextAcceptLanguage.getPreferredLocale(),
							WorkflowDefinitionResourceImpl.class),
						workflowTransition.getSourceNodeName(),
						workflowTransition.getTargetNodeName()),
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
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference(target = "(proxy.bean=false)")
	private WorkflowDefinitionManager _workflowDefinitionManager;

}