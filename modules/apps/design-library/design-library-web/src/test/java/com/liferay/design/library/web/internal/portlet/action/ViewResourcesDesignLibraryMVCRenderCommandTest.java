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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Thiago Buarque
 */
public class ViewResourcesDesignLibraryMVCRenderCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_designLibraryMVCRenderCommandTestHelper.setUp(
			_viewResourcesDesignLibraryMVCRenderCommand);
	}

	@After
	public void tearDown() {
		_designLibraryMVCRenderCommandTestHelper.tearDown();
	}

	@Test
	public void testRender() throws Exception {
		_testRender();
		_testRenderWithAssetLibrary();
		_testRenderWithDepotEntryFromAnotherCompany();
		_testRenderWithNonexistentDesignLibrary();
		_testRenderWithoutViewPermission();
	}

	private void _assertError(long depotEntryId) throws Exception {
		MockRenderRequest mockRenderRequest =
			_designLibraryMVCRenderCommandTestHelper.createMockRenderRequest(
				depotEntryId);

		Assert.assertEquals(
			"/error.jsp",
			_viewResourcesDesignLibraryMVCRenderCommand.render(
				mockRenderRequest, new MockRenderResponse()));
		Assert.assertNull(
			mockRenderRequest.getAttribute(
				DesignLibraryWebKeys.DESIGN_LIBRARY_ENTRY));
		Assert.assertTrue(
			SessionErrors.contains(
				mockRenderRequest,
				PrincipalException.MustHavePermission.class));
	}

	private void _testRender() throws Exception {
		DepotEntry depotEntry =
			_designLibraryMVCRenderCommandTestHelper.mockDepotEntry(
				DepotConstants.TYPE_DESIGN_LIBRARY);

		MockRenderRequest mockRenderRequest =
			_designLibraryMVCRenderCommandTestHelper.createMockRenderRequest(
				depotEntry.getDepotEntryId());

		Assert.assertEquals(
			"/view_resources.jsp",
			_viewResourcesDesignLibraryMVCRenderCommand.render(
				mockRenderRequest, new MockRenderResponse()));
		Assert.assertSame(
			depotEntry,
			mockRenderRequest.getAttribute(
				DesignLibraryWebKeys.DESIGN_LIBRARY_ENTRY));
	}

	private void _testRenderWithAssetLibrary() throws Exception {
		DepotEntry depotEntry =
			_designLibraryMVCRenderCommandTestHelper.mockDepotEntry(
				DepotConstants.TYPE_ASSET_LIBRARY);

		_assertError(depotEntry.getDepotEntryId());
	}

	private void _testRenderWithDepotEntryFromAnotherCompany()
		throws Exception {

		DepotEntry depotEntry =
			_designLibraryMVCRenderCommandTestHelper.mockDepotEntry(
				DepotConstants.TYPE_DESIGN_LIBRARY);

		Mockito.when(
			depotEntry.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_assertError(depotEntry.getDepotEntryId());
	}

	private void _testRenderWithNonexistentDesignLibrary() throws Exception {
		_assertError(RandomTestUtil.randomLong());
	}

	private void _testRenderWithoutViewPermission() throws Exception {
		DepotEntry depotEntry =
			_designLibraryMVCRenderCommandTestHelper.mockDepotEntry(
				DepotConstants.TYPE_DESIGN_LIBRARY);

		_designLibraryMVCRenderCommandTestHelper.denyViewPermission(depotEntry);

		_assertError(depotEntry.getDepotEntryId());
	}

	private final DesignLibraryMVCRenderCommandTestHelper
		_designLibraryMVCRenderCommandTestHelper =
			new DesignLibraryMVCRenderCommandTestHelper();
	private final ViewResourcesDesignLibraryMVCRenderCommand
		_viewResourcesDesignLibraryMVCRenderCommand =
			new ViewResourcesDesignLibraryMVCRenderCommand();

}