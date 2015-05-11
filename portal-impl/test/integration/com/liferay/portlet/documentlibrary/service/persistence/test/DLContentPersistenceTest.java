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

package com.liferay.portlet.documentlibrary.service.persistence.test;

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.service.DLContentLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLContentPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLContentUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class DLContentPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DLContentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DLContent> iterator = _dlContents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLContent dlContent = _persistence.create(pk);

		Assert.assertNotNull(dlContent);

		Assert.assertEquals(dlContent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLContent newDLContent = addDLContent();

		_persistence.remove(newDLContent);

		DLContent existingDLContent = _persistence.fetchByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertNull(existingDLContent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLContent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLContent newDLContent = _persistence.create(pk);

		newDLContent.setGroupId(RandomTestUtil.nextLong());

		newDLContent.setCompanyId(RandomTestUtil.nextLong());

		newDLContent.setRepositoryId(RandomTestUtil.nextLong());

		newDLContent.setPath(RandomTestUtil.randomString());

		newDLContent.setVersion(RandomTestUtil.randomString());

		String newDataString = RandomTestUtil.randomString();

		byte[] newDataBytes = newDataString.getBytes(StringPool.UTF8);

		Blob newDataBlob = new OutputBlob(new UnsyncByteArrayInputStream(
					newDataBytes), newDataBytes.length);

		newDLContent.setData(newDataBlob);

		newDLContent.setSize(RandomTestUtil.nextLong());

		_dlContents.add(_persistence.update(newDLContent));

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent.getContentId(),
			newDLContent.getContentId());
		Assert.assertEquals(existingDLContent.getGroupId(),
			newDLContent.getGroupId());
		Assert.assertEquals(existingDLContent.getCompanyId(),
			newDLContent.getCompanyId());
		Assert.assertEquals(existingDLContent.getRepositoryId(),
			newDLContent.getRepositoryId());
		Assert.assertEquals(existingDLContent.getPath(), newDLContent.getPath());
		Assert.assertEquals(existingDLContent.getVersion(),
			newDLContent.getVersion());

		Blob existingData = existingDLContent.getData();

		Assert.assertTrue(Arrays.equals(existingData.getBytes(1,
					(int)existingData.length()), newDataBytes));
		Assert.assertEquals(existingDLContent.getSize(), newDLContent.getSize());
	}

	@Test
	public void testCountByC_R() throws Exception {
		_persistence.countByC_R(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_R(0L, 0L);
	}

	@Test
	public void testCountByC_R_P() throws Exception {
		_persistence.countByC_R_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_R_P(0L, 0L, StringPool.NULL);

		_persistence.countByC_R_P(0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_R_LikeP() throws Exception {
		_persistence.countByC_R_LikeP(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_R_LikeP(0L, 0L, StringPool.NULL);

		_persistence.countByC_R_LikeP(0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_R_P_V() throws Exception {
		_persistence.countByC_R_P_V(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK, StringPool.BLANK);

		_persistence.countByC_R_P_V(0L, 0L, StringPool.NULL, StringPool.NULL);

		_persistence.countByC_R_P_V(0L, 0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLContent newDLContent = addDLContent();

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent, newDLContent);
	}

	@Test(expected = NoSuchContentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DLContent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLContent", "contentId",
			true, "groupId", true, "companyId", true, "repositoryId", true,
			"path", true, "version", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLContent newDLContent = addDLContent();

		DLContent existingDLContent = _persistence.fetchByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent, newDLContent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLContent missingDLContent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLContent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DLContent newDLContent1 = addDLContent();
		DLContent newDLContent2 = addDLContent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLContent1.getPrimaryKey());
		primaryKeys.add(newDLContent2.getPrimaryKey());

		Map<Serializable, DLContent> dlContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, dlContents.size());
		Assert.assertEquals(newDLContent1,
			dlContents.get(newDLContent1.getPrimaryKey()));
		Assert.assertEquals(newDLContent2,
			dlContents.get(newDLContent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DLContent> dlContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlContents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DLContent newDLContent = addDLContent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLContent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DLContent> dlContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlContents.size());
		Assert.assertEquals(newDLContent,
			dlContents.get(newDLContent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DLContent> dlContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlContents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DLContent newDLContent = addDLContent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLContent.getPrimaryKey());

		Map<Serializable, DLContent> dlContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlContents.size());
		Assert.assertEquals(newDLContent,
			dlContents.get(newDLContent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DLContentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DLContent dlContent = (DLContent)object;

					Assert.assertNotNull(dlContent);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLContent newDLContent = addDLContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDLContent.getContentId()));

		List<DLContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLContent existingDLContent = result.get(0);

		Assert.assertEquals(existingDLContent, newDLContent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				RandomTestUtil.nextLong()));

		List<DLContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLContent newDLContent = addDLContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		Object newContentId = newDLContent.getContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { newContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingContentId = result.get(0);

		Assert.assertEquals(existingContentId, newContentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DLContent newDLContent = addDLContent();

		_persistence.clearCache();

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent.getCompanyId(),
			ReflectionTestUtil.invoke(existingDLContent,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(existingDLContent.getRepositoryId(),
			ReflectionTestUtil.invoke(existingDLContent,
				"getOriginalRepositoryId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingDLContent.getPath(),
				ReflectionTestUtil.invoke(existingDLContent, "getOriginalPath",
					new Class<?>[0])));
		Assert.assertTrue(Validator.equals(existingDLContent.getVersion(),
				ReflectionTestUtil.invoke(existingDLContent,
					"getOriginalVersion", new Class<?>[0])));
	}

	protected DLContent addDLContent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLContent dlContent = _persistence.create(pk);

		dlContent.setGroupId(RandomTestUtil.nextLong());

		dlContent.setCompanyId(RandomTestUtil.nextLong());

		dlContent.setRepositoryId(RandomTestUtil.nextLong());

		dlContent.setPath(RandomTestUtil.randomString());

		dlContent.setVersion(RandomTestUtil.randomString());

		String dataString = RandomTestUtil.randomString();

		byte[] dataBytes = dataString.getBytes(StringPool.UTF8);

		Blob dataBlob = new OutputBlob(new UnsyncByteArrayInputStream(dataBytes),
				dataBytes.length);

		dlContent.setData(dataBlob);

		dlContent.setSize(RandomTestUtil.nextLong());

		_dlContents.add(_persistence.update(dlContent));

		return dlContent;
	}

	private List<DLContent> _dlContents = new ArrayList<DLContent>();
	private DLContentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}