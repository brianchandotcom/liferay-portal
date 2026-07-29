/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.library.web.internal.portlet.action;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.design.library.web.internal.constants.DesignLibraryWebKeys;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockRenderResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Thiago Buarque
 */
public class DesignLibrarySettingsMVCRenderCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_designLibraryMVCRenderCommandTestHelper.setUp(
			_designLibrarySettingsMVCRenderCommand);
	}

	@After
	public void tearDown() {
		_designLibraryMVCRenderCommandTestHelper.tearDown();
	}

	@Test
	public void testRender() throws Exception {
		DepotEntry depotEntry =
			_designLibraryMVCRenderCommandTestHelper.mockDepotEntry(
				DepotConstants.TYPE_DESIGN_LIBRARY);

		MockRenderRequest mockRenderRequest =
			_designLibraryMVCRenderCommandTestHelper.createMockRenderRequest(
				depotEntry.getDepotEntryId());

		Assert.assertEquals(
			"/view_settings.jsp",
			_designLibrarySettingsMVCRenderCommand.render(
				mockRenderRequest, new MockRenderResponse()));
		Assert.assertSame(
			depotEntry,
			mockRenderRequest.getAttribute(
				DesignLibraryWebKeys.DESIGN_LIBRARY_ENTRY));
	}

	@Test
	public void testRenderWithoutViewPermission() throws Exception {
		DepotEntry depotEntry =
			_designLibraryMVCRenderCommandTestHelper.mockDepotEntry(
				DepotConstants.TYPE_DESIGN_LIBRARY);

		_designLibraryMVCRenderCommandTestHelper.denyViewPermission(depotEntry);

		MockRenderRequest mockRenderRequest =
			_designLibraryMVCRenderCommandTestHelper.createMockRenderRequest(
				depotEntry.getDepotEntryId());

		Assert.assertEquals(
			"/error.jsp",
			_designLibrarySettingsMVCRenderCommand.render(
				mockRenderRequest, new MockRenderResponse()));
		Assert.assertTrue(
			SessionErrors.contains(
				mockRenderRequest,
				PrincipalException.MustHavePermission.class));
	}

	private final DesignLibraryMVCRenderCommandTestHelper
		_designLibraryMVCRenderCommandTestHelper =
			new DesignLibraryMVCRenderCommandTestHelper();
	private final DesignLibrarySettingsMVCRenderCommand
		_designLibrarySettingsMVCRenderCommand =
			new DesignLibrarySettingsMVCRenderCommand();

}