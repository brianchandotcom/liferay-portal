/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class FriendlyUrlHistoryResourceTest
	extends BaseFriendlyUrlHistoryResourceTestCase {

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			testGroup.getGroupId());
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		for (int i = 0; i < 3; i++) {
			_layoutLocalService.updateFriendlyURL(
				TestPropsValues.getUserId(), layout.getPlid(),
				"/" + RandomTestUtil.randomString(), defaultLanguageId);
		}

		FriendlyUrlHistory friendlyUrlHistory =
			friendlyUrlHistoryResource.
				getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode());

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		Assert.assertEquals(1, jsonObject.length());

		JSONArray jsonArray = jsonObject.getJSONArray(
			LocaleUtil.toBCP47LanguageId(defaultLanguageId));

		Assert.assertEquals(4, jsonArray.length());
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

}