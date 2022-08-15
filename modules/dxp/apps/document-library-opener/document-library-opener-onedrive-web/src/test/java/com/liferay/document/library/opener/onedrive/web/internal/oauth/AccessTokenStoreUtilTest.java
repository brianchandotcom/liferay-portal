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

import java.util.Optional;

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

		Optional<AccessToken> accessTokenOptional =
			AccessTokenStoreUtil.getAccessTokenOptional(companyId, userId);

		AccessToken actualAccessToken = accessTokenOptional.get();

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

		Optional<AccessToken> accessTokenOptional =
			AccessTokenStoreUtil.getAccessTokenOptional(companyId, userId);

		Assert.assertTrue(!accessTokenOptional.isPresent());
	}

	@Test
	public void testGetWithEmptyAccessTokenStore() {
		Optional<AccessToken> accessTokenOptional =
			AccessTokenStoreUtil.getAccessTokenOptional(
				RandomTestUtil.randomInt(), RandomTestUtil.randomInt());

		Assert.assertTrue(!accessTokenOptional.isPresent());
	}

}