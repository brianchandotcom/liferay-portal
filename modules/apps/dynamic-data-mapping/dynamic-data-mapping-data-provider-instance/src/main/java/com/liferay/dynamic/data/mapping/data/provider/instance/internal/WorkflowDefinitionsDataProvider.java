/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		WorkflowDefinitionManager workflowDefinitionManager =
			workflowDefinitionManagerSnapshot.get();

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

	protected static final Snapshot<WorkflowDefinitionManager>
		workflowDefinitionManagerSnapshot = new Snapshot<>(
			WorkflowDefinitionsDataProvider.class,
			WorkflowDefinitionManager.class, null, true);

	@Reference
	private Language _language;

}