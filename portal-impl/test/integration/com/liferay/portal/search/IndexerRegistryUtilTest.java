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

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.usersadmin.util.ContactIndexer;
import com.liferay.portlet.usersadmin.util.OrganizationIndexer;

import org.junit.Assert;
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
	public void testGetIndexerByIndexerClassName() throws Exception {
		Indexer<Organization> organizationIndexer =
			IndexerRegistryUtil.getIndexer(OrganizationIndexer.class.getName());

		Assert.assertNotNull(organizationIndexer);

		Indexer<Contact> contactIndexer = IndexerRegistryUtil.getIndexer(
			ContactIndexer.class.getName());

		Assert.assertNotNull(contactIndexer);
	}

	@Test
	public void testGetIndexerByModelClassName() throws Exception {
		Indexer<User> userIndexer = IndexerRegistryUtil.getIndexer(
			User.class.getName());

		Assert.assertNotNull(userIndexer);

		Indexer<UserGroup> userGroupIndexer = IndexerRegistryUtil.getIndexer(
			UserGroup.class.getName());

		Assert.assertNotNull(userGroupIndexer);
	}

}