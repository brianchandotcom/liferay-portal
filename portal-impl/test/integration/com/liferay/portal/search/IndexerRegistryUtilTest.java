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

package com.liferay.portal.search;

import static org.junit.Assert.assertNotNull;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * @author Gregory Amerson
 */
public class IndexerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final TestRule rule = new LiferayIntegrationTestRule();

	@Test
	public void testGetIndexerByIndexerClassName()
		throws Exception {

		Indexer mbMessageIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portlet.messageboards.util.MBMessageIndexer");

		assertNotNull(mbMessageIndexer);

		Indexer mbThreadIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portlet.messageboards.util.MBThreadIndexer");

		assertNotNull(mbThreadIndexer);
	}

	@Test
	public void testGetIndexerByModelClassName()
		throws Exception {

		Indexer userIndexer =
			IndexerRegistryUtil.getIndexer("com.liferay.portal.model.User");

		assertNotNull(userIndexer);

		Indexer userGroupIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portal.model.UserGroup");

		assertNotNull(userGroupIndexer);
	}

}
