/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.ai.hub.web.internal.test.util.DisplayContextTestUtil;
import com.liferay.object.field.attachment.AttachmentManager;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Mario Gomes
 */
public class EditChatbotDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		HttpServletRequest httpServletRequest =
			DisplayContextTestUtil.setUpHttpServletRequest();

		DisplayContextTestUtil.setUpThemeDisplay(
			Mockito.mock(Company.class), Mockito.mock(Group.class),
			httpServletRequest);

		_editChatbotDisplayContext = new EditChatbotDisplayContext(
			Mockito.mock(AttachmentManager.class), httpServletRequest,
			Mockito.mock(Language.class));
	}

	@Test
	public void testGetReactData() throws Exception {
		_testGetReactData(true, false);
		_testGetReactData(false, true);
	}

	private void _testGetReactData(
			boolean hasUpdatePermission, boolean readOnly)
		throws Exception {

		try (MockedStatic<AccountEntryUtil> accountEntryUtilMockedStatic =
				Mockito.mockStatic(AccountEntryUtil.class);
			MockedStatic<ObjectDefinitionLocalServiceUtil>
				objectDefinitionLocalServiceUtilMockedStatic =
					Mockito.mockStatic(ObjectDefinitionLocalServiceUtil.class);
			MockedStatic<ObjectEntryServiceUtil>
				objectEntryServiceUtilMockedStatic = Mockito.mockStatic(
					ObjectEntryServiceUtil.class)) {

			accountEntryUtilMockedStatic.when(
				() -> AccountEntryUtil.getUserAccountEntry(Mockito.anyLong())
			).thenReturn(
				null
			);

			DisplayContextTestUtil.setGetReactDataMocks(
				objectDefinitionLocalServiceUtilMockedStatic,
				objectEntryServiceUtilMockedStatic, hasUpdatePermission, false);

			Map<String, Object> reactData =
				_editChatbotDisplayContext.getReactData();

			Assert.assertEquals(readOnly, reactData.get("readOnly"));
		}
	}

	private EditChatbotDisplayContext _editChatbotDisplayContext;

}