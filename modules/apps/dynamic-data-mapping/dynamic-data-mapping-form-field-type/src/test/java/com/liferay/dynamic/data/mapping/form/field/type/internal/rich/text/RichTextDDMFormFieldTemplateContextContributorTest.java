/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.ai.creator.openai.manager.AICreatorOpenAIManager;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Carolina Barbosa
 */
public class RichTextDDMFormFieldTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		AICreatorOpenAIManager aiCreatorOpenAIManager = Mockito.mock(
			AICreatorOpenAIManager.class);

		Mockito.when(
			aiCreatorOpenAIManager.isAICreatorToolbarEnabled(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomBoolean()
		);

		ReflectionTestUtil.setFieldValue(
			_richTextDDMFormFieldTemplateContextContributor,
			"_aiCreatorOpenAIManager", aiCreatorOpenAIManager);
	}

	@After
	public void tearDown() {
		_editorConfigurationFactoryUtilMockedStatic.close();
	}

	@Test
	public void testGetData() {
		EditorConfiguration editorConfiguration = Mockito.mock(
			EditorConfiguration.class);

		String value1 = RandomTestUtil.randomString();

		Mockito.when(
			editorConfiguration.getData()
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"key1", value1
			).put(
				"key2", RandomTestUtil.randomString()
			).build()
		);

		Mockito.when(
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				Mockito.any(), Mockito.eq(DDMFormFieldTypeConstants.TEXT),
				Mockito.eq("ckeditor_classic"), Mockito.any(), Mockito.any(),
				Mockito.any())
		).thenReturn(
			editorConfiguration
		);

		DDMFormField ddmFormField = Mockito.mock(DDMFormField.class);

		String value2 = RandomTestUtil.randomString();

		Mockito.when(
			ddmFormField.getProperties()
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"key2", value2
			).put(
				"key3", RandomTestUtil.randomString()
			).build()
		);

		Map<String, Object> data =
			_richTextDDMFormFieldTemplateContextContributor.getData(
				ddmFormField, _getDDMFormFieldRenderingContext(),
				DDMFormFieldTypeConstants.TEXT);

		Assert.assertEquals(data.toString(), 2, data.size());

		Assert.assertEquals(value1, data.get("key1"));
		Assert.assertEquals(value2, data.get("key2"));
	}

	private DDMFormFieldRenderingContext _getDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, Mockito.mock(ThemeDisplay.class));

		ddmFormFieldRenderingContext.setHttpServletRequest(
			mockHttpServletRequest);

		return ddmFormFieldRenderingContext;
	}

	private final MockedStatic<EditorConfigurationFactoryUtil>
		_editorConfigurationFactoryUtilMockedStatic = Mockito.mockStatic(
			EditorConfigurationFactoryUtil.class);
	private final RichTextDDMFormFieldTemplateContextContributor
		_richTextDDMFormFieldTemplateContextContributor =
			new RichTextDDMFormFieldTemplateContextContributor();

}