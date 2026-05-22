/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.ai.hub.util.AccountEntryUtil;
import com.liferay.ai.hub.web.internal.test.util.DisplayContextTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
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
public class EditInstructionDefinitionDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		DisplayContextTestUtil.setUpThemeDisplay(
			_company, _group, _httpServletRequest);

		Mockito.when(
			_httpServletRequest.getParameter("externalReferenceCode")
		).thenReturn(
			"L_TEST_INSTRUCTION"
		);

		_editInstructionDefinitionDisplayContext =
			new EditInstructionDefinitionDisplayContext(_httpServletRequest);
	}

	@Test
	public void testGetReactData() throws Exception {
		_assertReadOnly(true, false);
		_assertReadOnly(false, true);
	}

	private void _assertReadOnly(boolean hasUpdatePermission, boolean readOnly)
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

			DisplayContextTestUtil.setUpReadOnlyMocks(
				objectDefinitionLocalServiceUtilMockedStatic,
				objectEntryServiceUtilMockedStatic, _objectDefinition,
				_objectEntry, hasUpdatePermission);

			Map<String, Object> reactData =
				_editInstructionDefinitionDisplayContext.getReactData();

			Assert.assertEquals(readOnly, reactData.get("readOnly"));
		}
	}

	private final Company _company = Mockito.mock(Company.class);
	private EditInstructionDefinitionDisplayContext
		_editInstructionDefinitionDisplayContext;
	private final Group _group = Mockito.mock(Group.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectEntry _objectEntry = Mockito.mock(ObjectEntry.class);

}