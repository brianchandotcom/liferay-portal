/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.item.selector.web.internal.item.selector;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.GlobalJSCET;
import com.liferay.client.extension.type.item.selector.criterion.CETItemSelectorCriterion;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Thiago Buarque
 */
public class CETItemSelectorViewDescriptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_cetManager = Mockito.mock(CETManager.class);

		_cetItemSelectorCriterion = Mockito.mock(
			CETItemSelectorCriterion.class);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST,
			new MockLiferayResourceRequest());
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, Mockito.mock(ThemeDisplay.class));

		_cetItemSelectorViewDescriptor = new CETItemSelectorViewDescriptor(
			_cetManager, _cetItemSelectorCriterion, mockHttpServletRequest,
			new MockLiferayPortletURL());
	}

	@Test
	public void testGetSearchContainer() throws PortalException {
		Mockito.when(
			_cetItemSelectorCriterion.getType()
		).thenReturn(
			ClientExtensionEntryConstants.TYPE_GLOBAL_JS
		);

		GlobalJSCET globalJSCET1 = Mockito.mock(GlobalJSCET.class);

		Mockito.when(
			globalJSCET1.getScope()
		).thenReturn(
			"company"
		);

		GlobalJSCET globalJSCET2 = Mockito.mock(GlobalJSCET.class);

		Mockito.when(
			globalJSCET2.getScope()
		).thenReturn(
			""
		);

		Mockito.when(
			_cetManager.getCETs(
				Mockito.anyLong(), Mockito.any(),
				Mockito.eq(ClientExtensionEntryConstants.TYPE_GLOBAL_JS),
				Mockito.any(), Mockito.any())
		).thenReturn(
			Arrays.asList(globalJSCET1, globalJSCET2)
		);

		SearchContainer<CET> searchContainer =
			_cetItemSelectorViewDescriptor.getSearchContainer();

		List<CET> results = searchContainer.getResults();

		Assert.assertEquals(results.toString(), 1, results.size());

		GlobalJSCET globalJSCET3 = (GlobalJSCET)results.get(0);

		Assert.assertEquals("", globalJSCET3.getScope());
	}

	private static CETItemSelectorCriterion _cetItemSelectorCriterion;
	private static CETItemSelectorViewDescriptor _cetItemSelectorViewDescriptor;
	private static CETManager _cetManager;

}