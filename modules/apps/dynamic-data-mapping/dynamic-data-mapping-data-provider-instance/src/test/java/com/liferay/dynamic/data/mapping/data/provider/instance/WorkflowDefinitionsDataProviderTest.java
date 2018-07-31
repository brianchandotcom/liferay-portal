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

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(LocaleUtil.class)
@RunWith(PowerMockRunner.class)
public class WorkflowDefinitionsDataProviderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();
		_setUpLocaleUtil();

		_workflowDefinitionsDataProvider =
			new WorkflowDefinitionsDataProvider();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetSettings() {
		_workflowDefinitionsDataProvider.getSettings();
	}

	@Test
	public void testGetWorkflowDefinitions() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withLocale(
			_locale
		).withCompanyId(
			1L
		).build();

		_workflowDefinitionsDataProvider.workflowDefinitionManager =
			_workflowDefinitionManager;

		WorkflowDefinition workflowDefinition1 = mock(WorkflowDefinition.class);

		_setUpWorkflowDefinition(
			workflowDefinition1, "definition1", 1, "Definition 1");

		WorkflowDefinition workflowDefinition2 = mock(WorkflowDefinition.class);

		_setUpWorkflowDefinition(
			workflowDefinition2, "definition2", 2, "Definition 2");

		when(
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			Arrays.asList(workflowDefinition1, workflowDefinition2)
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_workflowDefinitionsDataProvider.getData(ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutput("Default-Output", List.class);

		Assert.assertTrue(optional.isPresent());

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("no-workflow", "No Workflow"));
				add(new KeyValuePair("definition1@1", "Definition 1"));
				add(new KeyValuePair("definition2@2", "Definition 2"));
			}
		};

		Assert.assertEquals(keyValuePairs, optional.get());
	}

	@Test
	public void testNullWorkflowDefinitionManager() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withLocale(
			_locale
		).build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_workflowDefinitionsDataProvider.getData(ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutput("Default-Output", List.class);

		Assert.assertTrue(optional.isPresent());

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("no-workflow", "No Workflow"));
			}
		};

		Assert.assertEquals(keyValuePairs, optional.get());
	}

	@Test(expected = DDMDataProviderException.class)
	public void testThrowDDMDataProviderException() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withLocale(
			_locale
		).withCompanyId(
			1L
		).build();

		_workflowDefinitionsDataProvider.workflowDefinitionManager =
			_workflowDefinitionManager;

		when(
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenThrow(
			WorkflowException.class
		);

		_workflowDefinitionsDataProvider.getData(ddmDataProviderRequest);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		when(
			_language.get(_locale, "no-workflow")
		).thenReturn(
			"No Workflow"
		);
	}

	private void _setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.toLanguageId(_locale)
		).thenReturn(
			"en_US"
		);
	}

	private void _setUpWorkflowDefinition(
		WorkflowDefinition workflowDefinition1, String name, int version,
		String title) {

		when(
			workflowDefinition1.getName()
		).thenReturn(
			name
		);

		when(
			workflowDefinition1.getVersion()
		).thenReturn(
			version
		);

		when(
			workflowDefinition1.getTitle("en_US")
		).thenReturn(
			title
		);
	}

	private static final Locale _locale = new Locale("en", "US");

	@Mock
	private Language _language;

	@Mock
	private WorkflowDefinitionManager _workflowDefinitionManager;

	private WorkflowDefinitionsDataProvider _workflowDefinitionsDataProvider;

}