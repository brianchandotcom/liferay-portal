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

package com.liferay.portal.store.s3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.store.test.BaseStoreTestCase;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class S3StoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule(),
			TransactionalTestRule.INSTANCE);

	public static void assume() {
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		String s3StoreClassName = "com.liferay.portal.store.s3.S3Store";

		Assume.assumeTrue(
			"Property \"" + PropsKeys.DL_STORE_IMPL + "\" is not set to \"" +
				s3StoreClassName + "\"",
			dlStoreImpl.equals(s3StoreClassName));
	}

	@Override
	@Test
	public void testUpdateFileWithNewFileName() throws Exception {
		updateFileShouldNotUpdateFile();
	}

	@Override
	@Test
	public void testUpdateFileWithNewRepositoryId() throws Exception {
		updateFileShouldNotUpdateFile();
	}

	@Override
	protected String getStoreType() {
		return "com.liferay.portal.store.s3.S3Store";
	}

	protected void updateFileShouldNotUpdateFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.updateFile(
			companyId, repositoryId, fileName, RandomTestUtil.randomString());

		Assert.assertFalse(store.hasFile(companyId, repositoryId, fileName));
	}

}