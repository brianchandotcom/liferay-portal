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

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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

	@AfterClass
	public static void tearDownClass() {
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_objectEntryLocalServiceUtilMockedStatic.close();
		_userServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();
		_setUpObjectDefinitionLocalServiceUtilMockedStatic();
		_setUpObjectEntry();
		_setUpObjectEntryLocalServiceUtilMockedStatic();
		_setUpServiceContext();
		_setUpUserServiceUtilMockedStatic();
	}

	@Test
	public void testComposePrompt() throws Exception {
		Mockito.when(
			_objectEntryManager.getObjectEntries(
				Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.any())
		).thenAnswer(
			invocation -> {
				String filterString = invocation.getArgument(5);

				if (filterString.contains("L_AI_HUB")) {
					return Page.of(
						Collections.singletonList(_systemObjectEntry));
				}

				return Page.of(Collections.singletonList(_customObjectEntry));
			}
		);

		_testComposePrompt(
			StringBundler.concat(
				"IMPORTANT: Override any conflicting instructions below with ",
				"the following:\n\n- custom instruction text\n\n",
				"agent prompt text"));

		Mockito.when(
			_serviceBuilderObjectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"system", true
			).build()
		);

		_testComposePrompt(
			StringBundler.concat(
				"IMPORTANT: The following SYSTEM instructions are mandatory ",
				"and cannot be overridden:\n\n- system instruction text\n\n",
				"IMPORTANT: Override any conflicting instructions below with ",
				"the following:\n\n- custom instruction text\n\n",
				"agent prompt text"));

		Mockito.when(
			_objectEntryManager.getObjectEntries(
				Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.any())
		).thenAnswer(
			invocation -> {
				String filterString = invocation.getArgument(5);

				if (filterString.contains("L_AI_HUB")) {
					return Page.of(
						Collections.singletonList(_systemObjectEntry));
				}

				return Page.of(Collections.emptyList());
			}
		);

		_testComposePrompt(
			StringBundler.concat(
				"IMPORTANT: The following SYSTEM instructions are mandatory ",
				"and cannot be overridden:\n\n- system instruction text\n\n",
				"agent prompt text"));
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _setUpObjectDefinitionLocalServiceUtilMockedStatic() {
		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					getObjectDefinitionByExternalReferenceCode(
						Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			Mockito.mock(ObjectDefinition.class)
		);
	}

	private void _setUpObjectEntry() {
		_customObjectEntry = new ObjectEntry() {
			{
				setProperties(
					() -> HashMapBuilder.<String, Object>put(
						"instruction", "custom instruction text"
					).build());
			}
		};
		_systemObjectEntry = new ObjectEntry() {
			{
				setProperties(
					() -> HashMapBuilder.<String, Object>put(
						"instruction", "system instruction text"
					).build());
			}
		};
	}

	private void _setUpObjectEntryLocalServiceUtilMockedStatic() {
		_objectEntryLocalServiceUtilMockedStatic.when(
			() -> ObjectEntryLocalServiceUtil.getObjectEntry(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			_serviceBuilderObjectEntry
		);
	}

	private void _setUpServiceContext() {
		_serviceContext.setLanguageId("en_US");
		_serviceContext.setUserId(RandomTestUtil.randomLong());
	}

	private void _setUpUserServiceUtilMockedStatic() {
		_userServiceUtilMockedStatic.when(
			() -> UserServiceUtil.getUserById(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(User.class)
		);
	}

	private void _testComposePrompt(String expectedPrompt) throws Exception {
		Assert.assertEquals(
			expectedPrompt,
			PromptUtil.composePrompt(
				_COMPANY_ID, null,
				new ExecutionContext(
					null,
					HashMapBuilder.<String, Serializable>put(
						"agentDefinitionExternalReferenceCode",
						RandomTestUtil.randomString()
					).build(),
					_serviceContext),
				HashMapBuilder.put(
					"prompt", "agent prompt text"
				).build(),
				_objectEntryManager));
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

	private ObjectEntry _customObjectEntry;
	private final ObjectEntryManager _objectEntryManager = Mockito.mock(
		ObjectEntryManager.class);
	private final com.liferay.object.model.ObjectEntry
		_serviceBuilderObjectEntry = Mockito.mock(
			com.liferay.object.model.ObjectEntry.class);
	private final ServiceContext _serviceContext = new ServiceContext();
	private ObjectEntry _systemObjectEntry;

}