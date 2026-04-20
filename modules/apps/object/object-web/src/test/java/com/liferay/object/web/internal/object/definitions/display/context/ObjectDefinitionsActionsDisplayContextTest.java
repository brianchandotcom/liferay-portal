/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.action.trigger.ObjectActionTrigger;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.web.internal.object.definitions.display.context.util.ObjectCodeEditorUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.script.management.configuration.helper.ScriptManagementConfigurationHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Nathaly Gomes
 */
public class ObjectDefinitionsActionsDisplayContextTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();
	}

	@After
	public void tearDown() {
		if (_objectFieldLocalServiceServiceRegistration != null) {
			_objectFieldLocalServiceServiceRegistration.unregister();
		}
	}

	@Test
	@TestInfo("LPD-82766")
	public void testGetObjectActionCodeEditorElements() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		Mockito.when(
			_objectDefinition.getObjectDefinitionId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		ObjectField objectField1 = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField1.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)
		).thenReturn(
			true
		);

		ObjectField objectField2 = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField2.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)
		).thenReturn(
			false
		);

		String objectFieldName = RandomTestUtil.randomString();

		Mockito.when(
			objectField2.getName()
		).thenReturn(
			objectFieldName
		);

		Mockito.when(
			_objectFieldLocalService.getObjectFields(Mockito.anyLong())
		).thenReturn(
			Arrays.asList(objectField1, objectField2)
		);

		_objectFieldLocalServiceServiceRegistration =
			bundleContext.registerService(
				ObjectFieldLocalService.class, _objectFieldLocalService, null);

		Map<String, List<Map<String, String>>>
			objectActionCodeEditorElementsItems = new HashMap<>();

		ObjectDefinitionsActionsDisplayContext
			objectDefinitionsActionsDisplayContext =
				new ObjectDefinitionsActionsDisplayContext(
					_getHttpServletRequest(), new JSONFactoryImpl(),
					Mockito.mock(NotificationTemplateLocalService.class),
					Mockito.mock(ObjectActionExecutorRegistry.class),
					Mockito.mock(ObjectActionTriggerRegistry.class),
					Mockito.mock(ObjectDefinitionLocalService.class),
					Mockito.mock(ModelResourcePermission.class),
					_objectFieldLocalService,
					Mockito.mock(ObjectFolderLocalService.class),
					Mockito.mock(ScriptManagementConfigurationHelper.class));

		List<Map<String, Object>> objectActionCodeEditorElements =
			objectDefinitionsActionsDisplayContext.
				getObjectActionCodeEditorElements();

		for (Map<String, Object> objectActionCodeEditorElement :
				objectActionCodeEditorElements) {

			objectActionCodeEditorElementsItems.put(
				(String)objectActionCodeEditorElement.get("key"),
				(List<Map<String, String>>)objectActionCodeEditorElement.get(
					"items"));
		}

		Assert.assertEquals(
			objectActionCodeEditorElementsItems.toString(), 4,
			objectActionCodeEditorElementsItems.size());

		List<Map<String, String>> fields =
			objectActionCodeEditorElementsItems.get("fields");

		Assert.assertEquals(fields.toString(), 1, fields.size());
		Assert.assertTrue(_hasContent(objectFieldName, fields));

		List<Map<String, String>> functions =
			objectActionCodeEditorElementsItems.get("functions");

		ObjectCodeEditorUtil.DDMExpressionFunction[] ddmExpressionFunctions =
			ObjectCodeEditorUtil.DDMExpressionFunction.values();

		Assert.assertEquals(
			functions.toString(), ddmExpressionFunctions.length - 1,
			functions.size());

		Assert.assertFalse(
			_hasContent("pow(field_name, parameter)", functions));

		Assert.assertEquals(
			new HashSet<>(Arrays.asList("currentDate", "currentUserId")),
			_getContents(
				objectActionCodeEditorElementsItems.get("general-variables")));

		List<Map<String, String>> operators =
			objectActionCodeEditorElementsItems.get("operators");

		ObjectCodeEditorUtil.DDMExpressionOperator[] ddmExpressionOperators =
			ObjectCodeEditorUtil.DDMExpressionOperator.values();

		Assert.assertEquals(
			operators.toString(), ddmExpressionOperators.length,
			operators.size());
	}

	@Test
	public void testGetObjectActionTriggersJSONArray() {
		ObjectActionTriggerRegistry objectActionTriggerRegistry = Mockito.mock(
			ObjectActionTriggerRegistry.class);

		ObjectActionTrigger objectActionTrigger1 = Mockito.mock(
			ObjectActionTrigger.class);
		ObjectActionTrigger objectActionTrigger2 = Mockito.mock(
			ObjectActionTrigger.class);

		Mockito.when(
			_objectDefinition.getClassName()
		).thenReturn(
			User.class.getName()
		);

		Mockito.when(
			objectActionTriggerRegistry.getObjectActionTriggers(
				_objectDefinition.getClassName())
		).thenReturn(
			Arrays.asList(objectActionTrigger1, objectActionTrigger2)
		);

		Mockito.when(
			objectActionTrigger1.getKey()
		).thenReturn(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD
		);

		Mockito.when(
			objectActionTrigger2.getKey()
		).thenReturn(
			ObjectActionTriggerConstants.KEY_ON_AFTER_LOGIN
		);

		ObjectDefinitionsActionsDisplayContext
			objectDefinitionsActionsDisplayContext =
				new ObjectDefinitionsActionsDisplayContext(
					_getHttpServletRequest(), new JSONFactoryImpl(),
					Mockito.mock(NotificationTemplateLocalService.class),
					Mockito.mock(ObjectActionExecutorRegistry.class),
					objectActionTriggerRegistry,
					Mockito.mock(ObjectDefinitionLocalService.class),
					Mockito.mock(ModelResourcePermission.class),
					Mockito.mock(ObjectFieldLocalService.class),
					Mockito.mock(ObjectFolderLocalService.class),
					Mockito.mock(ScriptManagementConfigurationHelper.class));

		JSONArray jsonArray =
			objectDefinitionsActionsDisplayContext.
				getObjectActionTriggersJSONArray();

		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			JSONUtil.getValue(jsonArray, "JSONObject/0", "Object/value"));
		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_LOGIN,
			JSONUtil.getValue(jsonArray, "JSONObject/1", "Object/value"));

		Mockito.when(
			_objectDefinition.getClassName()
		).thenReturn(
			Organization.class.getName()
		);

		Mockito.when(
			objectActionTriggerRegistry.getObjectActionTriggers(
				_objectDefinition.getClassName())
		).thenReturn(
			Arrays.asList(objectActionTrigger1, objectActionTrigger2)
		);

		jsonArray =
			objectDefinitionsActionsDisplayContext.
				getObjectActionTriggersJSONArray();

		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			JSONUtil.getValue(jsonArray, "JSONObject/0", "Object/value"));
		Assert.assertNull(
			JSONUtil.getValue(jsonArray, "JSONObject/1", "Object/value"));
	}

	private boolean _hasContent(
		String content, List<Map<String, String>> items) {

		for (Map<String, String> item : items) {
			if (content.equals(item.get("content"))) {
				return true;
			}
		}

		return false;
	}

	private Set<String> _getContents(List<Map<String, String>> items) {
		Set<String> contents = new HashSet<>();

		for (Map<String, String> item : items) {
			contents.add(item.get("content"));
		}

		return contents;
	}

	private HttpServletRequest _getHttpServletRequest() {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			ObjectWebKeys.OBJECT_DEFINITION, _objectDefinition);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.US);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectFieldLocalService _objectFieldLocalService =
		Mockito.mock(ObjectFieldLocalService.class);
	private ServiceRegistration<ObjectFieldLocalService>
		_objectFieldLocalServiceServiceRegistration;

}