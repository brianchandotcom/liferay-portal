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

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntry;
import com.liferay.portal.tools.service.builder.test.service.LazyBlobEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntryPersistence;

import org.hibernate.engine.EntityEntry;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionImplementor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class LazyBlobEntryLoadedStateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Test
	public void testLoadedState() throws Exception {
		_group = GroupTestUtil.addGroup();

		LazyBlobEntry addedLazyBlobEntry =
			_lazyBlobEntryLocalService.addLazyBlobEntry(
				_group.getGroupId(), _BYTES,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_lazyBlobEntryPersistence.clearCache();

		Session session = _lazyBlobEntryPersistence.openSession();

		try {
			SessionImplementor hibernateSession = _unwrapSession(session);

			LazyBlobEntry fetchedLazyBlobEntry = (LazyBlobEntry)session.load(
				addedLazyBlobEntry.getClass(),
				addedLazyBlobEntry.getLazyBlobEntryId());

			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(
					fetchedLazyBlobEntry, "_blob1BlobModel"));
			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(
					fetchedLazyBlobEntry, "_blob2BlobModel"));

			PersistenceContext persistenceContext =
				hibernateSession.getPersistenceContext();

			EntityEntry entityEntry = persistenceContext.getEntry(
				fetchedLazyBlobEntry);

			Assert.assertNull(
				"Loaded value of lazy blob property should be null.",
				entityEntry.getLoadedValue("blob1BlobModel"));
			Assert.assertNull(
				"Loaded value of lazy blob property should be null.",
				entityEntry.getLoadedValue("blob2BlobModel"));
		}
		finally {
			_lazyBlobEntryPersistence.closeSession(session);
		}

		_lazyBlobEntryLocalService.deleteLazyBlobEntry(
			addedLazyBlobEntry.getLazyBlobEntryId());
	}

	private SessionImplementor _unwrapSession(Session session) {
		Object wrapped = null;

		while (!((wrapped = session.getWrappedSession()) instanceof
					SessionImplementor));

		return (SessionImplementor)wrapped;
	}

	private static final byte[] _BYTES = "Data abc xyz".getBytes();

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LazyBlobEntryLocalService _lazyBlobEntryLocalService;

	@Inject
	private LazyBlobEntryPersistence _lazyBlobEntryPersistence;

}