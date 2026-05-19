/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.workflow.kaleo.runtime.node.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Alberto Sousa
 */
public class PromptUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					fetchObjectDefinitionByExternalReferenceCode(
						Mockito.eq("L_AI_HUB_INSTRUCTION_DEFINITION"),
						Mockito.anyLong())
		).thenReturn(
			Mockito.mock(ObjectDefinition.class)
		);

		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					getObjectDefinitionByExternalReferenceCode(
						Mockito.eq("L_AI_HUB_AGENT_DEFINITION"),
						Mockito.anyLong())
		).thenReturn(
			Mockito.mock(ObjectDefinition.class)
		);

		_userServiceUtilMockedStatic.when(
			() -> UserServiceUtil.getUserById(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(User.class)
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_objectEntryLocalServiceUtilMockedStatic.close();
		_userServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();

		_objectEntryManager = Mockito.mock(ObjectEntryManager.class);

		_serviceContext = new ServiceContext();

		_serviceContext.setLanguageId("en_US");
		_serviceContext.setUserId(RandomTestUtil.randomLong());
	}

	@Test
	public void testComposePrompt() throws Exception {
		_testComposePrompt(
			Arrays.asList(_createObjectEntry("custom instruction text")),
			StringBundler.concat(
				"IMPORTANT: Override any conflicting instructions below with ",
				"the following:\n\n- custom instruction text\n\n",
				"agent prompt text"),
			false);
		_testComposePrompt(
			Arrays.asList(_createObjectEntry("custom instruction text")),
			StringBundler.concat(
				"IMPORTANT: The following SYSTEM instructions are mandatory ",
				"and cannot be overridden:\n\n- system instruction text\n\n",
				"IMPORTANT: Override any conflicting instructions below with ",
				"the following:\n\n- custom instruction text\n\n",
				"agent prompt text"),
			true);
		_testComposePrompt(
			Collections.emptyList(),
			StringBundler.concat(
				"IMPORTANT: The following SYSTEM instructions are mandatory ",
				"and cannot be overridden:\n\n- system instruction text\n\n",
				"agent prompt text"),
			true);

		_testComposePromptWithDifferentScope(
			"active eq true and scope eq 'everywhere' and system eq false",
			null);
		_testComposePromptWithDifferentScope(
			"active eq true and scope in ('everywhere', 'clickToChat') and " +
				"system eq false",
			"clickToChat");
		_testComposePromptWithDifferentScope(
			"active eq true and scope in ('everywhere', 'CMS') and system eq " +
				"false",
			"CMS");
		_testComposePromptWithDifferentScope(
			"active eq true and scope in ('everywhere', 'everywhere') and " +
				"system eq false",
			"everywhere");
	}

	private ExecutionContext _createExecutionContext(
		String externalReferenceCode, String scope) {

		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				"agentDefinitionExternalReferenceCode", externalReferenceCode
			).put(
				"instructionDefinitionScope", scope
			).build();

		return new ExecutionContext(null, workflowContext, _serviceContext);
	}

	private ObjectEntry _createObjectEntry(String instruction) {
		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"instruction", instruction
			).build());

		return objectEntry;
	}

	private void _mockObjectEntryManager(
			List<ObjectEntry> customObjectEntries,
			List<ObjectEntry> systemObjectEntries)
		throws Exception {

		Mockito.when(
			_objectEntryManager.getObjectEntries(
				Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.any())
		).thenAnswer(
			invocation -> {
				String filterString = invocation.getArgument(5);

				if (filterString.contains("L_AI_HUB")) {
					return Page.of(systemObjectEntries);
				}

				return Page.of(customObjectEntries);
			}
		);
	}

	private void _mockServiceBuilderObjectEntry(boolean system) {
		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			Mockito.mock(com.liferay.object.model.ObjectEntry.class);

		Mockito.when(
			serviceBuilderObjectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"system", system
			).build()
		);

		_objectEntryLocalServiceUtilMockedStatic.when(
			() -> ObjectEntryLocalServiceUtil.getObjectEntry(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			serviceBuilderObjectEntry
		);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _testComposePrompt(
			List<ObjectEntry> customObjectEntries, String expected,
			boolean system)
		throws Exception {

		_mockServiceBuilderObjectEntry(system);
		_mockObjectEntryManager(
			customObjectEntries,
			Arrays.asList(_createObjectEntry("system instruction text")));

		Assert.assertEquals(
			expected,
			PromptUtil.composePrompt(
				_COMPANY_ID, null,
				_createExecutionContext("L_CHANGE_TONE", null),
				HashMapBuilder.put(
					"prompt", "agent prompt text"
				).build(),
				_objectEntryManager));
	}

	private void _testComposePromptWithDifferentScope(
			String expectedFilterString, String scope)
		throws Exception {

		Mockito.clearInvocations(_objectEntryManager);

		_mockServiceBuilderObjectEntry(true);
		_mockObjectEntryManager(
			Collections.emptyList(),
			Arrays.asList(_createObjectEntry("system instruction text")));

		PromptUtil.composePrompt(
			_COMPANY_ID, null, _createExecutionContext("L_CHANGE_TONE", scope),
			Collections.emptyMap(), _objectEntryManager);

		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			_objectEntryManager, Mockito.times(2)
		).getObjectEntries(
			Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any(),
			Mockito.any(), argumentCaptor.capture(), Mockito.any(),
			Mockito.any(), Mockito.any()
		);

		List<String> filterStrings = argumentCaptor.getAllValues();

		String customFilterString = filterStrings.get(1);

		if (customFilterString.contains("L_AI_HUB")) {
			customFilterString = filterStrings.get(0);
		}

		Assert.assertEquals(expectedFilterString, customFilterString);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectDefinitionLocalServiceUtil.class);
	private static final MockedStatic<ObjectEntryLocalServiceUtil>
		_objectEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectEntryLocalServiceUtil.class);
	private static final MockedStatic<UserServiceUtil>
		_userServiceUtilMockedStatic = Mockito.mockStatic(
			UserServiceUtil.class);

	private ObjectEntryManager _objectEntryManager;
	private ServiceContext _serviceContext;

}