/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.web.internal.test.util;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Mario Gomes
 */
public class DisplayContextTestUtil {

	public static void assertFDSActionDropdownItem(
		FDSActionDropdownItem fdsActionDropdownItem, String href, String icon,
		String id, String label, String method, String target) {

		Map<String, String> data =
			(Map<String, String>)fdsActionDropdownItem.get("data");

		Assert.assertEquals(id, data.get("id"));
		Assert.assertEquals(method, data.get("method"));

		Assert.assertEquals(href, fdsActionDropdownItem.get("href"));
		Assert.assertEquals(icon, fdsActionDropdownItem.get("icon"));
		Assert.assertEquals(label, fdsActionDropdownItem.get("label"));
		Assert.assertEquals(target, fdsActionDropdownItem.get("target"));
	}

	public static void setGetReactDataMocks(
		MockedStatic<ObjectDefinitionLocalServiceUtil>
			objectDefinitionLocalServiceUtilMockedStatic,
		MockedStatic<ObjectEntryServiceUtil> objectEntryServiceUtilMockedStatic,
		boolean hasUpdatePermission, boolean system) {

		objectDefinitionLocalServiceUtilMockedStatic.when(
			() ->
				ObjectDefinitionLocalServiceUtil.
					getObjectDefinitionByExternalReferenceCode(
						Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			Mockito.mock(ObjectDefinition.class)
		);

		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"system", system
			).build()
		);

		objectEntryServiceUtilMockedStatic.when(
			() -> ObjectEntryServiceUtil.getObjectEntry(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			objectEntry
		);

		objectEntryServiceUtilMockedStatic.when(
			() -> ObjectEntryServiceUtil.hasModelResourcePermission(
				objectEntry, ActionKeys.UPDATE)
		).thenReturn(
			hasUpdatePermission
		);
	}

	public static HttpServletRequest setUpHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("externalReferenceCode")
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return httpServletRequest;
	}

	public static void setUpThemeDisplay(
			Company company, Group group, HttpServletRequest httpServletRequest)
		throws Exception {

		Mockito.when(
			company.getPortalURL(Mockito.anyLong())
		).thenReturn(
			"http://localhost:8080"
		);

		Mockito.when(
			group.getFriendlyURL()
		).thenReturn(
			"/test"
		);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getCompany()
		).thenReturn(
			company
		);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			1L
		);

		Mockito.when(
			themeDisplay.getScopeGroup()
		).thenReturn(
			group
		);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			1L
		);

		Mockito.when(
			themeDisplay.getUserId()
		).thenReturn(
			1L
		);
	}

}