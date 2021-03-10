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

package com.liferay.headless.admin.content.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class SitePageResourceTest extends BaseSitePageResourceTestCase {

	@Override
	@Test
	public void testDeleteSiteSitePage() throws Exception {
		Layout layout = _addLayout(testGroup.getGroupId());

		String friendlyURL = layout.getFriendlyURL();

		sitePageResource.deleteSiteSitePage(
			testGroup.getGroupId(), friendlyURL.substring(1));

		Assert.assertNull(_layoutLocalService.fetchLayout(layout.getPlid()));
	}

	@Override
	@Test
	public void testGraphQLDeleteSiteSitePage() throws Exception {
		Layout layout = _addLayout(testGroup.getGroupId());

		String friendlyURL = layout.getFriendlyURL();

		JSONObject jsonObject = invokeGraphQLMutation(
			new GraphQLField(
				"admin",
				new GraphQLField(
					"deleteSiteSitePage",
					HashMapBuilder.<String, Object>put(
						"friendlyUrlPath",
						"\"" + friendlyURL.substring(1) + "\""
					).put(
						"siteKey", "\"" + testGroup.getGroupId() + "\""
					).build())));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertNotNull(dataJSONObject);

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				dataJSONObject, "JSONObject/admin",
				"Object/deleteSiteSitePage"));
	}

	private Layout _addLayout(long groupId) throws Exception {
		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false, null,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	@Inject
	private LayoutLocalService _layoutLocalService;

}