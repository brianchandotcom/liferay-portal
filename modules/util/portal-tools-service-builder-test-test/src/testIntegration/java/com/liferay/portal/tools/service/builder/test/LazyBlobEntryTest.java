/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntry;
import com.liferay.portal.tools.service.builder.test.service.LazyBlobEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntryPersistence;

import java.sql.Blob;

import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class LazyBlobEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() throws Exception {
		_blobContent = RandomTestUtil.randomString();

		_lazyBlobEntry = _lazyBlobEntryLocalService.addLazyBlobEntry(
			TestPropsValues.getGroupId(), _blobContent.getBytes(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));

		_assertLazyBlobEntry(_blobContent, _lazyBlobEntry);
	}

	@Test
	public void testLazyBlobLoadedValueIsNull() {
		_lazyBlobEntryPersistence.clearCache();

		Session session = _lazyBlobEntryPersistence.openSession();

		try {
			LazyBlobEntry loadedLazyBlobEntry = (LazyBlobEntry)session.load(
				_lazyBlobEntry.getClass(), _lazyBlobEntry.getLazyBlobEntryId());

			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(
					loadedLazyBlobEntry, "_blob1BlobModel"));
			Assert.assertNull(
				ReflectionTestUtil.getFieldValue(
					loadedLazyBlobEntry, "_blob2BlobModel"));

			SessionImplementor sessionImplementor =
				(SessionImplementor)session.getWrappedSession();

			PersistenceContext persistenceContext =
				sessionImplementor.getPersistenceContext();

			EntityEntry entityEntry = persistenceContext.getEntry(
				loadedLazyBlobEntry);

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
	}

	@Test
	public void testSerialize() throws Exception {
		Serializer serializer = new Serializer();

		serializer.writeObject(_lazyBlobEntry);

		Deserializer deserializer = new Deserializer(serializer.toByteBuffer());

		LazyBlobEntry serializedLazyBlobEntry = deserializer.readObject();

		Assert.assertEquals(_lazyBlobEntry, serializedLazyBlobEntry);

		_assertLazyBlobEntry(_blobContent, serializedLazyBlobEntry);
	}

	private void _assertLazyBlobEntry(
			String expectedContent, LazyBlobEntry lazyBlobEntry)
		throws Exception {

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(lazyBlobEntry, "_blob1BlobModel"));

		Blob blob1 = lazyBlobEntry.getBlob1();

		Assert.assertEquals(
			expectedContent,
			new String(blob1.getBytes(1, (int)blob1.length())));

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(lazyBlobEntry, "_blob1BlobModel"));

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(lazyBlobEntry, "_blob2BlobModel"));

		Blob blob2 = lazyBlobEntry.getBlob2();

		Assert.assertEquals(
			expectedContent,
			new String(blob2.getBytes(1, (int)blob2.length())));

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(lazyBlobEntry, "_blob2BlobModel"));
	}

	private String _blobContent;

	@DeleteAfterTestRun
	private LazyBlobEntry _lazyBlobEntry;

	@Inject
	private LazyBlobEntryLocalService _lazyBlobEntryLocalService;

	@Inject
	private LazyBlobEntryPersistence _lazyBlobEntryPersistence;

}