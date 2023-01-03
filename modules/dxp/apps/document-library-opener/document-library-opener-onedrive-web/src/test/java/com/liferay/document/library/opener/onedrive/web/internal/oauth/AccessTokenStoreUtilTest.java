/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.github.scribejava.core.model.OAuth2AccessToken;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class AccessTokenStoreUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAdd() {
		AccessToken initialAccessToken = new AccessToken(
			new OAuth2AccessToken(RandomTestUtil.randomString()));

		long companyId = RandomTestUtil.randomInt();
		long userId = RandomTestUtil.randomInt();

		AccessTokenStoreUtil.add(companyId, userId, initialAccessToken);

		AccessToken actualAccessToken = AccessTokenStoreUtil.getAccessToken(
			companyId, userId);

		Assert.assertEquals(
			initialAccessToken.getAccessToken(),
			actualAccessToken.getAccessToken());
	}

	@Test
	public void testDelete() {
		AccessToken initialAccessToken = new AccessToken(
			new OAuth2AccessToken(RandomTestUtil.randomString()));

		long companyId = RandomTestUtil.randomInt();
		long userId = RandomTestUtil.randomInt();

		AccessTokenStoreUtil.add(companyId, userId, initialAccessToken);

		AccessTokenStoreUtil.delete(companyId, userId);

		AccessToken accessToken = AccessTokenStoreUtil.getAccessToken(
			companyId, userId);

		Assert.assertTrue(accessToken == null);
	}

	@Test
	public void testGetWithEmptyAccessTokenStore() {
		AccessToken accessToken = AccessTokenStoreUtil.getAccessToken(
			RandomTestUtil.randomInt(), RandomTestUtil.randomInt());

		Assert.assertTrue(accessToken == null);
	}

}