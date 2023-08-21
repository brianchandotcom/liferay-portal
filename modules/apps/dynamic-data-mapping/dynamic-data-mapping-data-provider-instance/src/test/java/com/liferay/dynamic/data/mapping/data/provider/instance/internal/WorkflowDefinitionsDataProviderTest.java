/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.data.provider.instance.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionsDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_workflowDefinitionManagerServiceRegistration =
			bundleContext.registerService(
				WorkflowDefinitionManager.class, _workflowDefinitionManager,
				null);

		_workflowDefinitionsDataProvider =
			new WorkflowDefinitionsDataProvider();

		Mockito.when(
			_language.get(_locale, "no-workflow")
		).thenReturn(
			"No Workflow"
		);

		ReflectionTestUtil.setFieldValue(
			_workflowDefinitionsDataProvider, "_language", _language);
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
		_workflowDefinitionManagerServiceRegistration.unregister();
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

		WorkflowDefinition workflowDefinition1 = Mockito.mock(
			WorkflowDefinition.class);

		_setUpWorkflowDefinition(
			workflowDefinition1, "definition1", "Definition 1");

		WorkflowDefinition workflowDefinition2 = Mockito.mock(
			WorkflowDefinition.class);

		_setUpWorkflowDefinition(
			workflowDefinition2, "definition2", "Definition 2");

		Mockito.when(
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			Arrays.asList(workflowDefinition1, workflowDefinition2)
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_workflowDefinitionsDataProvider.getData(ddmDataProviderRequest);

		Assert.assertTrue(ddmDataProviderResponse.hasOutput("Default-Output"));

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"Default-Output", List.class);

		Assert.assertNotNull(keyValuePairs);

		List<KeyValuePair> expectedKeyValuePairs =
			new ArrayList<KeyValuePair>() {
				{
					add(new KeyValuePair("no-workflow", "No Workflow"));
					add(new KeyValuePair("definition1", "Definition 1"));
					add(new KeyValuePair("definition2", "Definition 2"));
				}
			};

		Assert.assertEquals(expectedKeyValuePairs, keyValuePairs);
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

		List<KeyValuePair> keyValuePairs = ddmDataProviderResponse.getOutput(
			"Default-Output", List.class);

		Assert.assertNotNull(keyValuePairs);

		List<KeyValuePair> expectedKeyValuePairs =
			new ArrayList<KeyValuePair>() {
				{
					add(new KeyValuePair("no-workflow", "No Workflow"));
				}
			};

		Assert.assertEquals(expectedKeyValuePairs, keyValuePairs);
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

		Mockito.when(
			_workflowDefinitionManager.getActiveWorkflowDefinitions(
				1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenThrow(
			WorkflowException.class
		);

		_workflowDefinitionsDataProvider.getData(ddmDataProviderRequest);
	}

	private void _setUpWorkflowDefinition(
		WorkflowDefinition workflowDefinition, String name, String title) {

		Mockito.when(
			workflowDefinition.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			workflowDefinition.getTitle("en_US")
		).thenReturn(
			title
		);
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static final Language _language = Mockito.mock(Language.class);
	private static final Locale _locale = new Locale("en", "US");
	private static final WorkflowDefinitionManager _workflowDefinitionManager =
		Mockito.mock(WorkflowDefinitionManager.class);
	private static ServiceRegistration<WorkflowDefinitionManager>
		_workflowDefinitionManagerServiceRegistration;
	private static WorkflowDefinitionsDataProvider
		_workflowDefinitionsDataProvider;

}