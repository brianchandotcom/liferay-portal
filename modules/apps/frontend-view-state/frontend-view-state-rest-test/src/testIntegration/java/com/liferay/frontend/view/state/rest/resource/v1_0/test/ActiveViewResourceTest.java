/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.service.FVSActiveEntryLocalService;
import com.liferay.frontend.view.state.service.FVSEntryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class ActiveViewResourceTest extends BaseActiveViewResourceTestCase {

	@Override
	@Test
	public void testGetActiveViewPageLayoutPortlet() throws Exception {
		String activeViewId = RandomTestUtil.randomString();
		String string = RandomTestUtil.randomString();

		activeViewResource.putActiveViewPageLayoutPortlet(
			activeViewId, 0L, "-", string);

		Assert.assertEquals(
			string,
			activeViewResource.getActiveViewPageLayoutPortlet(
				activeViewId, 0L, "-"));
	}

	@Override
	@Test
	public void testPutActiveViewPageLayoutPortlet() throws Exception {
		String activeViewId = RandomTestUtil.randomString();
		String string = RandomTestUtil.randomString();

		activeViewResource.putActiveViewPageLayoutPortlet(
			activeViewId, 0L, "-", string);

		FVSActiveEntry fvsActiveEntry =
			_fvsActiveEntryLocalService.fetchFVSActiveEntry(
				TestPropsValues.getUserId(), activeViewId, 0L, "-");

		FVSEntry fvsEntry = _fvsEntryLocalService.getFVSEntry(
			fvsActiveEntry.getFvsEntryId());

		Assert.assertEquals(string, fvsEntry.getViewState());
	}

	@Inject
	private FVSActiveEntryLocalService _fvsActiveEntryLocalService;

	@Inject
	private FVSEntryLocalService _fvsEntryLocalService;

}