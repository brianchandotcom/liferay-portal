/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.learn.LearnMessageUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class LearnResourcesTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGlobalServerServesLearnResources() throws Exception {

		// Every learn message in the product renders from the resource files
		// this global server serves, so when it stops serving them, learn
		// links silently vanish portal wide. Guard the server itself here
		// instead of leaving its outages to surface as unrelated UI test
		// failures.

		Http.Options options = new Http.Options();

		options.setLocation(
			"https://s3.amazonaws.com/learn-resources.liferay.com" +
				"/marketplace-store-web.json");

		String body = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		Assert.assertEquals(body, 200, response.getResponseCode());

		JSONObject jsonObject = LearnMessageUtil.getJSONObject(
			"marketplace-store-web");

		Assert.assertTrue(
			jsonObject.toString(),
			jsonObject.keys(
			).hasNext());
	}

	@Inject
	private Http _http;

}