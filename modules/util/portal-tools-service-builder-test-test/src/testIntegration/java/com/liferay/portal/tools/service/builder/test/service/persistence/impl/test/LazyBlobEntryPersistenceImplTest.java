/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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