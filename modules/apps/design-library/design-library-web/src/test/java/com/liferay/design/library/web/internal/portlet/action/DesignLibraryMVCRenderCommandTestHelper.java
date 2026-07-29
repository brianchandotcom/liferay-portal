/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.portlet.action;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockRenderRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.http.HttpServletRequest;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Thiago Buarque
 */
public class DesignLibraryMVCRenderCommandTestHelper {

	public MockRenderRequest createMockRenderRequest(long depotEntryId) {
		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			themeDisplay.getPermissionChecker()
		).thenReturn(
			_permissionChecker
		);

		mockRenderRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockRenderRequest.setParameter(
			"designLibraryEntryId", String.valueOf(depotEntryId));

		return mockRenderRequest;
	}

	public void denyViewPermission(DepotEntry depotEntry)
		throws PortalException {

		long depotEntryId = depotEntry.getDepotEntryId();

		Mockito.doThrow(
			new PrincipalException.MustHavePermission(
				_permissionChecker, DepotEntry.class.getName(), depotEntryId,
				ActionKeys.VIEW)
		).when(
			_depotEntryService
		).getDepotEntry(
			depotEntryId
		);
	}

	public DepotEntry mockDepotEntry(int type) throws PortalException {
		DepotEntry depotEntry = Mockito.mock(DepotEntry.class);

		Mockito.when(
			depotEntry.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		long depotEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			depotEntry.getDepotEntryId()
		).thenReturn(
			depotEntryId
		);

		Mockito.when(
			depotEntry.getType()
		).thenReturn(
			type
		);

		Mockito.when(
			_depotEntryService.getDepotEntry(depotEntryId)
		).thenReturn(
			depotEntry
		);

		return depotEntry;
	}

	public void setUp(
		BaseDesignLibraryMVCRenderCommand baseDesignLibraryMVCRenderCommand) {

		_portalUtilMockedStatic = Mockito.mockStatic(PortalUtil.class);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getHttpServletRequest(
				Mockito.any(PortletRequest.class))
		).thenReturn(
			mockHttpServletRequest
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getLiferayPortletRequest(
				Mockito.any(PortletRequest.class))
		).thenReturn(
			Mockito.mock(LiferayPortletRequest.class)
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getOriginalServletRequest(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			mockHttpServletRequest
		);

		ReflectionTestUtil.setFieldValue(
			baseDesignLibraryMVCRenderCommand, "depotEntryService",
			_depotEntryService);
	}

	public void tearDown() {
		_portalUtilMockedStatic.close();
	}

	private static final long _COMPANY_ID = 1234;

	private final DepotEntryService _depotEntryService = Mockito.mock(
		DepotEntryService.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private MockedStatic<PortalUtil> _portalUtilMockedStatic;

}