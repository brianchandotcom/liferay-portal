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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntryPersistence;

import java.io.ByteArrayInputStream;

import java.sql.Blob;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class LazyBlobEntryPersistenceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Test
	public void testUpdateExisting() throws Throwable {
		Session session = null;

		try {
			LazyBlobEntry lazyBlobEntry = _lazyBlobEntryPersistence.create(
				RandomTestUtil.nextLong());

			lazyBlobEntry.setUuid(RandomTestUtil.randomString());
			lazyBlobEntry.setGroupId(RandomTestUtil.nextLong());

			Blob blob = new OutputBlob(
				new ByteArrayInputStream(new byte[0]), 0);

			lazyBlobEntry.setBlob1(blob);
			lazyBlobEntry.setBlob2(blob);

			lazyBlobEntry = _lazyBlobEntryPersistence.update(lazyBlobEntry);

			session = _lazyBlobEntryPersistence.getCurrentSession();

			Object sessionObject = session.get(
				lazyBlobEntry.getClass(), lazyBlobEntry.getPrimaryKey());

			LazyBlobEntry existingLazyBlobEntry =
				_lazyBlobEntryPersistence.findByPrimaryKey(
					lazyBlobEntry.getPrimaryKey());

			Assert.assertEquals(sessionObject, existingLazyBlobEntry);
			Assert.assertNotSame(sessionObject, existingLazyBlobEntry);

			existingLazyBlobEntry.setGroupId(RandomTestUtil.nextLong());

			existingLazyBlobEntry = _lazyBlobEntryPersistence.update(
				existingLazyBlobEntry);

			LazyBlobEntry newExistingLazyBlobEntry =
				_lazyBlobEntryPersistence.findByPrimaryKey(
					lazyBlobEntry.getPrimaryKey());

			Assert.assertEquals(
				existingLazyBlobEntry.getGroupId(),
				newExistingLazyBlobEntry.getGroupId());
		}
		finally {
			if (session != null) {
				session.clear();
			}
		}
	}

	@Inject
	private LazyBlobEntryPersistence _lazyBlobEntryPersistence;

}