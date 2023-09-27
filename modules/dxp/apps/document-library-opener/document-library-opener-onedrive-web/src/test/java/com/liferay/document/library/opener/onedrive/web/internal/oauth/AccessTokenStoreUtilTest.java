/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Assert.assertNull(
			AccessTokenStoreUtil.getAccessToken(companyId, userId));
	}

	@Test
	public void testGetWithEmptyAccessTokenStore() {
		Assert.assertNull(
			AccessTokenStoreUtil.getAccessToken(
				RandomTestUtil.randomInt(), RandomTestUtil.randomInt()));
	}

}