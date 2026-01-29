/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.agent.AgentsFactory;
import com.liferay.ai.hub.rest.dto.v1_0.TaskDefinition;
import com.liferay.ai.hub.rest.manager.v1_0.TaskDefinitionManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = AgentsFactory.class)
public class AgentsFactoryImpl implements AgentsFactory {

	@Override
	public Object[] create(AgentContext agentContext) {
		try {
			ServiceContext serviceContext = agentContext.getServiceContext();

			Page<TaskDefinition> page =
				_taskDefinitionManager.getTaskDefinitions(
					agentContext.getCompanyId(),
					agentContext.getDTOConverterContext(), null,
					_toFilter(serviceContext.getLocale()), Pagination.of(1, 20),
					null);

			return TransformUtil.transformToArray(
				page.getItems(),
				taskDefinition -> new AgentSpecsProviderImpl(
					agentContext, taskDefinition.getDescription(),
					taskDefinition.getName(), taskDefinition.getVersion(),
					_workflowInstanceManager),
				Object.class);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return new AgentSpecsProviderImpl[0];
	}

	private Filter _toFilter(Locale locale) {
		try {
			FilterParser filterParser = _filterParserProvider.provide(
				_entityModel);

			com.liferay.portal.odata.filter.Filter oDataFilter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse("(active eq 1)"));

			return _expressionConvert.convert(
				oDataFilter.getExpression(), locale, _entityModel);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AgentsFactoryImpl.class);

	@Reference(target = "(entity.model.name=TaskDefinition)")
	private EntityModel _entityModel;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private TaskDefinitionManager _taskDefinitionManager;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

}