/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@FeatureFlags("LPD-17564")
public class ContentsSectionDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		Mockito.when(
			_language.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenAnswer(
			(Answer<String>)invocationOnMock -> invocationOnMock.getArgument(
				1, String.class)
		);
	}

	@Before
	public void setUp() throws Exception {
		_contentsSectionDisplayContext = new ContentsSectionDisplayContext(
			null, _getHttpServletRequest(), _language,
			_objectDefinitionService);
	}

	@Test
	@TestInfo("LPD-50664")
	public void testGetCreationMenu() {
		Mockito.when(
			_objectDefinitionService.getObjectDefinitions(
				0, QueryUtil.ALL_POS, QueryUtil.ALL_POS)
		).thenReturn(
			Collections.emptyList()
		);

		_testGetCreationMenu(
			LinkedHashMapBuilder.put(
				"basic-content", StringPool.BLANK
			).put(
				"blog", StringPool.BLANK
			).put(
				"knowledge-base", StringPool.BLANK
			).put(
				"folder", StringPool.BLANK
			).build());

		ObjectDefinition objectDefinition = _getObjectDefinition(
			true, "site", false);

		List<ObjectDefinition> objectDefinitions = ListUtil.fromArray(
			_getObjectDefinition(true, "company", false),
			_getObjectDefinition(false, "site", false), objectDefinition,
			_getObjectDefinition(true, "site", true));

		Mockito.when(
			_objectDefinitionService.getObjectDefinitions(
				0, QueryUtil.ALL_POS, QueryUtil.ALL_POS)
		).thenReturn(
			objectDefinitions
		);

		_testGetCreationMenu(
			LinkedHashMapBuilder.put(
				"basic-content", StringPool.BLANK
			).put(
				"blog", StringPool.BLANK
			).put(
				"knowledge-base", StringPool.BLANK
			).put(
				objectDefinition.getLabel(LocaleUtil.US),
				"http://localhost:8080/c/cms/add_structured_content_item?" +
					"groupId=0&objectDefinitionId=" +
						objectDefinition.getObjectDefinitionId()
			).put(
				"folder", StringPool.BLANK
			).build());
	}

	private HttpServletRequest _getHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			themeDisplay.getPathMain()
		).thenReturn(
			"/c"
		);

		Mockito.when(
			themeDisplay.getPortalURL()
		).thenReturn(
			"http://localhost:8080"
		);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private ObjectDefinition _getObjectDefinition(
		boolean enableObjectEntryDraft, String scope, boolean system) {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getLabel(LocaleUtil.US)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			objectDefinition.getObjectDefinitionId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			objectDefinition.isEnableObjectEntryDraft()
		).thenReturn(
			enableObjectEntryDraft
		);

		Mockito.when(
			objectDefinition.isSystem()
		).thenReturn(
			system
		);

		Mockito.when(
			objectDefinition.getScope()
		).thenReturn(
			scope
		);

		return objectDefinition;
	}

	private void _testGetCreationMenu(Map<String, String> map) {
		CreationMenu creationMenu =
			_contentsSectionDisplayContext.getCreationMenu();

		List<DropdownItem> dropdownItems = (List<DropdownItem>)creationMenu.get(
			"primaryItems");

		Assert.assertEquals(
			dropdownItems.toString(), map.size(), dropdownItems.size());

		int index = 0;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			DropdownItem dropdownItem = dropdownItems.get(index);

			Assert.assertEquals(entry.getKey(), dropdownItem.get("label"));

			if (Validator.isNull(entry.getValue())) {
				Assert.assertNull(dropdownItem.get("href"));
			}
			else {
				Assert.assertEquals(entry.getValue(), dropdownItem.get("href"));
			}

			index++;
		}
	}

	private static final Language _language = Mockito.mock(Language.class);

	private ContentsSectionDisplayContext _contentsSectionDisplayContext;
	private final ObjectDefinitionService _objectDefinitionService =
		Mockito.mock(ObjectDefinitionService.class);

}