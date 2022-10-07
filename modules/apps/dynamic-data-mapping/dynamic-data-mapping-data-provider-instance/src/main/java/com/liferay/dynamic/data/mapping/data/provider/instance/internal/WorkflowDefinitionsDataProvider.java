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

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.data.provider.instance.id=workflow-definitions",
	service = DDMDataProvider.class
)
public class WorkflowDefinitionsDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		Locale locale = ddmDataProviderRequest.getLocale();

		keyValuePairs.add(
			new KeyValuePair(
				"no-workflow", _language.get(locale, "no-workflow")));

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		if (workflowDefinitionManager == null) {
			builder = builder.withOutput("Default-Output", keyValuePairs);

			return builder.build();
		}

		try {
			List<WorkflowDefinition> workflowDefinitions =
				workflowDefinitionManager.getActiveWorkflowDefinitions(
					ddmDataProviderRequest.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			String languageId = LocaleUtil.toLanguageId(locale);

			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
				String value = workflowDefinition.getName();

				keyValuePairs.add(
					new KeyValuePair(
						value, workflowDefinition.getTitle(languageId)));
			}

			builder = builder.withOutput("Default-Output", keyValuePairs);
		}
		catch (WorkflowException workflowException) {
			throw new DDMDataProviderException(workflowException);
		}

		return builder.build();
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(proxy.bean=false)"
	)
	protected volatile WorkflowDefinitionManager workflowDefinitionManager;

	@Reference
	private Language _language;

}