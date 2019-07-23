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

package com.liferay.document.library.opener.one.drive.web.internal.oauth;

import com.github.scribejava.core.model.OAuth2AccessToken;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class AccessTokenStoreTest {

	@Test
	public void testAdd() {
		AccessTokenStore accessTokenStore = new AccessTokenStore();

		AccessToken initialAccessToken = new AccessToken(
			new OAuth2AccessToken(RandomTestUtil.randomString()));

		long companyId = RandomTestUtil.randomInt();
		long userId = RandomTestUtil.randomInt();

		accessTokenStore.add(companyId, userId, initialAccessToken);

		Optional<AccessToken> accessTokenOptional =
			accessTokenStore.getAccessTokenOptional(companyId, userId);

		AccessToken actualAccessToken = accessTokenOptional.get();

		Assert.assertEquals(
			initialAccessToken.getAccessToken(),
			actualAccessToken.getAccessToken());
	}

	@Test
	public void testDelete() {
		AccessTokenStore accessTokenStore = new AccessTokenStore();

		AccessToken initialAccessToken = new AccessToken(
			new OAuth2AccessToken(RandomTestUtil.randomString()));

		long companyId = RandomTestUtil.randomInt();
		long userId = RandomTestUtil.randomInt();

		accessTokenStore.add(companyId, userId, initialAccessToken);

		accessTokenStore.delete(companyId, userId);

		Optional<AccessToken> accessTokenOptional =
			accessTokenStore.getAccessTokenOptional(companyId, userId);

		Assert.assertTrue(!accessTokenOptional.isPresent());
	}

	@Test
	public void testGetWithEmptyAccessTokenStore() {
		AccessTokenStore accessTokenStore = new AccessTokenStore();

		Optional<AccessToken> accessTokenOptional =
			accessTokenStore.getAccessTokenOptional(
				RandomTestUtil.randomInt(), RandomTestUtil.randomInt());

		Assert.assertTrue(!accessTokenOptional.isPresent());
	}

}