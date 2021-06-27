/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

		Object activeViewPageLayoutPortlet =
			activeViewResource.getActiveViewPageLayoutPortlet(
				activeViewId, 0L, "-");

		Assert.assertEquals(string, activeViewPageLayoutPortlet);
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