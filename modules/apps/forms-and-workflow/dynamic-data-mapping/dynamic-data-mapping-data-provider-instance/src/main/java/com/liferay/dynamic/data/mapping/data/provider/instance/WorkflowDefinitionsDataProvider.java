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

package com.liferay.dynamic.data.mapping.data.provider.instance;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = "ddm.data.provider.instance.id=workflow-definitions"
)
public class WorkflowDefinitionsDataProvider implements DDMDataProvider {

	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		return Collections.emptyList();
	}

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		List<Map<Object, Object>> data = new ArrayList<>();

		DDMDataProviderContext ddmDataProviderContext =
			ddmDataProviderRequest.getDDMDataProviderContext();

		Locale locale = getLocale(
			ddmDataProviderContext.getHttpServletRequest());

		data.add(
			createMap(
				LanguageUtil.get(locale, "no-workflow"), StringPool.BLANK));

		if (!_workflowEngineManager.isDeployed()) {
			return new DDMDataProviderResponse(data);
		}

		try {
			long companyId = getCompanyId(
				ddmDataProviderContext.getHttpServletRequest());

			List<WorkflowDefinition> workflowDefinitions =
				_workflowDefinitionManager.getActiveWorkflowDefinitions(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
				String version = LanguageUtil.format(
					locale, "version-x", workflowDefinition.getVersion(),
					false);

				String label =
					workflowDefinition.getName() + " (" + version + ")";

				String value =
					workflowDefinition.getName() + StringPool.AT +
						workflowDefinition.getVersion();

				data.add(createMap(label, value));
			}
		}
		catch (WorkflowException we) {
			throw new DDMDataProviderException(we);
		}

		return new DDMDataProviderResponse(data);
	}

	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	protected Map<Object, Object> createMap(String label, String value) {
		Map<Object, Object> map = new HashMap<>();

		map.put(label, value);

		return map;
	}

	protected long getCompanyId(HttpServletRequest httpServletRequest) {
		return PortalUtil.getCompanyId(httpServletRequest);
	}

	protected Locale getLocale(HttpServletRequest httpServletRequest) {
		return PortalUtil.getLocale(httpServletRequest);
	}

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference
	private WorkflowEngineManager _workflowEngineManager;

}