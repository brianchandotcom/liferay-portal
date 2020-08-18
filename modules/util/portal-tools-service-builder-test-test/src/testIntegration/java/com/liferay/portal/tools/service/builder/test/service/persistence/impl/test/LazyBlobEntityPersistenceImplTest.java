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
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntityPersistence;

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
public class LazyBlobEntityPersistenceImplTest {

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
			LazyBlobEntity lazyBlobEntity = _lazyBlobEntityPersistence.create(
				RandomTestUtil.nextLong());

			lazyBlobEntity.setUuid(RandomTestUtil.randomString());
			lazyBlobEntity.setGroupId(RandomTestUtil.nextLong());

			Blob blob = new OutputBlob(
				new ByteArrayInputStream(new byte[0]), 0);

			lazyBlobEntity.setBlob1(blob);
			lazyBlobEntity.setBlob2(blob);

			lazyBlobEntity = _lazyBlobEntityPersistence.update(lazyBlobEntity);

			session = _lazyBlobEntityPersistence.getCurrentSession();

			Object sessionObject = session.get(
				lazyBlobEntity.getClass(), lazyBlobEntity.getPrimaryKey());

			LazyBlobEntity existingLazyBlobEntity =
				_lazyBlobEntityPersistence.findByPrimaryKey(
					lazyBlobEntity.getPrimaryKey());

			Assert.assertEquals(sessionObject, existingLazyBlobEntity);
			Assert.assertNotSame(sessionObject, existingLazyBlobEntity);

			existingLazyBlobEntity.setGroupId(RandomTestUtil.nextLong());

			existingLazyBlobEntity = _lazyBlobEntityPersistence.update(
				existingLazyBlobEntity);

			LazyBlobEntity newExistingLazyBlobEntity =
				_lazyBlobEntityPersistence.findByPrimaryKey(
					lazyBlobEntity.getPrimaryKey());

			Assert.assertEquals(
				existingLazyBlobEntity.getGroupId(),
				newExistingLazyBlobEntity.getGroupId());
		}
		finally {
			if (session != null) {
				session.clear();
			}
		}
	}

	@Inject
	private LazyBlobEntityPersistence _lazyBlobEntityPersistence;

}