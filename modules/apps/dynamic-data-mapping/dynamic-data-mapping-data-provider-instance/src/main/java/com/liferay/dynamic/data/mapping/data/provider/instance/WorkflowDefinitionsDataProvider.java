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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseOutput;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = "ddm.data.provider.instance.id=workflow-definitions"
)
public class WorkflowDefinitionsDataProvider implements DDMDataProvider {

	@Override
	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		return Collections.emptyList();
	}

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		List<KeyValuePair> data = new ArrayList<>();

		Locale locale = getLocale(
			ddmDataProviderRequest.getHttpServletRequest());

		data.add(
			new KeyValuePair(
				"no-workflow", LanguageUtil.get(locale, "no-workflow")));

		if (_workflowDefinitionManager == null) {
			return DDMDataProviderResponse.of(
				DDMDataProviderResponseOutput.of(
					"Default-Output", "list", data));
		}

		try {
			long companyId = getCompanyId(
				ddmDataProviderRequest.getHttpServletRequest());

			List<WorkflowDefinition> workflowDefinitions =
				_workflowDefinitionManager.getActiveWorkflowDefinitions(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			String languageId = LocaleUtil.toLanguageId(locale);

			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
				String value =
					workflowDefinition.getName() + StringPool.AT +
						workflowDefinition.getVersion();

				data.add(
					new KeyValuePair(
						value, workflowDefinition.getTitle(languageId)));
			}
		}
		catch (WorkflowException we) {
			throw new DDMDataProviderException(we);
		}

		return DDMDataProviderResponse.of(
			DDMDataProviderResponseOutput.of("Default-Output", "list", data));
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	protected long getCompanyId(HttpServletRequest httpServletRequest) {
		return _portal.getCompanyId(httpServletRequest);
	}

	protected Locale getLocale(HttpServletRequest httpServletRequest) {
		return _portal.getLocale(httpServletRequest);
	}

	@Reference
	private Portal _portal;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(proxy.bean=false)"
	)
	private WorkflowDefinitionManager _workflowDefinitionManager;

}