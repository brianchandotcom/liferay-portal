/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.display.context;

import com.liferay.ai.hub.web.internal.test.util.DisplayContextTestUtil;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
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
public class EditContentRetrieverDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		HttpServletRequest httpServletRequest =
			DisplayContextTestUtil.setUpHttpServletRequest();

		DisplayContextTestUtil.setUpThemeDisplay(
			Mockito.mock(Company.class), _group, httpServletRequest);

		Mockito.when(
			_groupLocalService.getGroup(1L)
		).thenReturn(
			_group
		);

		_editContentRetrieverDisplayContext =
			new EditContentRetrieverDisplayContext(
				_groupLocalService, httpServletRequest);
	}

	@Test
	public void testGetReactData() throws Exception {
		_testGetReactData(true, false);
		_testGetReactData(false, true);
	}

	private void _testGetReactData(
			boolean hasUpdatePermission, boolean readOnly)
		throws Exception {

		try (MockedStatic<ObjectDefinitionLocalServiceUtil>
				objectDefinitionLocalServiceUtilMockedStatic =
					Mockito.mockStatic(ObjectDefinitionLocalServiceUtil.class);
			MockedStatic<ObjectEntryServiceUtil>
				objectEntryServiceUtilMockedStatic = Mockito.mockStatic(
					ObjectEntryServiceUtil.class)) {

			DisplayContextTestUtil.setGetReactDataMocks(
				objectDefinitionLocalServiceUtilMockedStatic,
				objectEntryServiceUtilMockedStatic, hasUpdatePermission, false);

			Map<String, Object> reactData =
				_editContentRetrieverDisplayContext.getReactData();

			Assert.assertEquals(readOnly, reactData.get("readOnly"));
		}
	}

	private EditContentRetrieverDisplayContext
		_editContentRetrieverDisplayContext;
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);

}