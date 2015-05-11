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

package com.liferay.portlet.journal.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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

import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class JournalArticleImagePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = JournalArticleImageUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<JournalArticleImage> iterator = _journalArticleImages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleImage journalArticleImage = _persistence.create(pk);

		Assert.assertNotNull(journalArticleImage);

		Assert.assertEquals(journalArticleImage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		_persistence.remove(newJournalArticleImage);

		JournalArticleImage existingJournalArticleImage = _persistence.fetchByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertNull(existingJournalArticleImage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalArticleImage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleImage newJournalArticleImage = _persistence.create(pk);

		newJournalArticleImage.setGroupId(RandomTestUtil.nextLong());

		newJournalArticleImage.setArticleId(RandomTestUtil.randomString());

		newJournalArticleImage.setVersion(RandomTestUtil.nextDouble());

		newJournalArticleImage.setElInstanceId(RandomTestUtil.randomString());

		newJournalArticleImage.setElName(RandomTestUtil.randomString());

		newJournalArticleImage.setLanguageId(RandomTestUtil.randomString());

		newJournalArticleImage.setTempImage(RandomTestUtil.randomBoolean());

		_journalArticleImages.add(_persistence.update(newJournalArticleImage));

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage.getArticleImageId(),
			newJournalArticleImage.getArticleImageId());
		Assert.assertEquals(existingJournalArticleImage.getGroupId(),
			newJournalArticleImage.getGroupId());
		Assert.assertEquals(existingJournalArticleImage.getArticleId(),
			newJournalArticleImage.getArticleId());
		AssertUtils.assertEquals(existingJournalArticleImage.getVersion(),
			newJournalArticleImage.getVersion());
		Assert.assertEquals(existingJournalArticleImage.getElInstanceId(),
			newJournalArticleImage.getElInstanceId());
		Assert.assertEquals(existingJournalArticleImage.getElName(),
			newJournalArticleImage.getElName());
		Assert.assertEquals(existingJournalArticleImage.getLanguageId(),
			newJournalArticleImage.getLanguageId());
		Assert.assertEquals(existingJournalArticleImage.getTempImage(),
			newJournalArticleImage.getTempImage());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByTempImage() throws Exception {
		_persistence.countByTempImage(RandomTestUtil.randomBoolean());

		_persistence.countByTempImage(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_A_V() throws Exception {
		_persistence.countByG_A_V(RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.nextDouble());

		_persistence.countByG_A_V(0L, StringPool.NULL, 0D);

		_persistence.countByG_A_V(0L, (String)null, 0D);
	}

	@Test
	public void testCountByG_A_V_E_E_L() throws Exception {
		_persistence.countByG_A_V_E_E_L(RandomTestUtil.nextLong(),
			StringPool.BLANK, RandomTestUtil.nextDouble(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK);

		_persistence.countByG_A_V_E_E_L(0L, StringPool.NULL, 0D,
			StringPool.NULL, StringPool.NULL, StringPool.NULL);

		_persistence.countByG_A_V_E_E_L(0L, (String)null, 0D, (String)null,
			(String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	@Test(expected = NoSuchArticleImageException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<JournalArticleImage> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("JournalArticleImage",
			"articleImageId", true, "groupId", true, "articleId", true,
			"version", true, "elInstanceId", true, "elName", true,
			"languageId", true, "tempImage", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		JournalArticleImage existingJournalArticleImage = _persistence.fetchByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleImage missingJournalArticleImage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalArticleImage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		JournalArticleImage newJournalArticleImage1 = addJournalArticleImage();
		JournalArticleImage newJournalArticleImage2 = addJournalArticleImage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticleImage1.getPrimaryKey());
		primaryKeys.add(newJournalArticleImage2.getPrimaryKey());

		Map<Serializable, JournalArticleImage> journalArticleImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, journalArticleImages.size());
		Assert.assertEquals(newJournalArticleImage1,
			journalArticleImages.get(newJournalArticleImage1.getPrimaryKey()));
		Assert.assertEquals(newJournalArticleImage2,
			journalArticleImages.get(newJournalArticleImage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, JournalArticleImage> journalArticleImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(journalArticleImages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticleImage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, JournalArticleImage> journalArticleImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, journalArticleImages.size());
		Assert.assertEquals(newJournalArticleImage,
			journalArticleImages.get(newJournalArticleImage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, JournalArticleImage> journalArticleImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(journalArticleImages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticleImage.getPrimaryKey());

		Map<Serializable, JournalArticleImage> journalArticleImages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, journalArticleImages.size());
		Assert.assertEquals(newJournalArticleImage,
			journalArticleImages.get(newJournalArticleImage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = JournalArticleImageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					JournalArticleImage journalArticleImage = (JournalArticleImage)object;

					Assert.assertNotNull(journalArticleImage);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("articleImageId",
				newJournalArticleImage.getArticleImageId()));

		List<JournalArticleImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalArticleImage existingJournalArticleImage = result.get(0);

		Assert.assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("articleImageId",
				RandomTestUtil.nextLong()));

		List<JournalArticleImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"articleImageId"));

		Object newArticleImageId = newJournalArticleImage.getArticleImageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("articleImageId",
				new Object[] { newArticleImageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingArticleImageId = result.get(0);

		Assert.assertEquals(existingArticleImageId, newArticleImageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"articleImageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("articleImageId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		_persistence.clearCache();

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage.getGroupId(),
			ReflectionTestUtil.invoke(existingJournalArticleImage,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImage.getArticleId(),
				ReflectionTestUtil.invoke(existingJournalArticleImage,
					"getOriginalArticleId", new Class<?>[0])));
		AssertUtils.assertEquals(existingJournalArticleImage.getVersion(),
			ReflectionTestUtil.<Double>invoke(existingJournalArticleImage,
				"getOriginalVersion", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImage.getElInstanceId(),
				ReflectionTestUtil.invoke(existingJournalArticleImage,
					"getOriginalElInstanceId", new Class<?>[0])));
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImage.getElName(),
				ReflectionTestUtil.invoke(existingJournalArticleImage,
					"getOriginalElName", new Class<?>[0])));
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImage.getLanguageId(),
				ReflectionTestUtil.invoke(existingJournalArticleImage,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected JournalArticleImage addJournalArticleImage()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleImage journalArticleImage = _persistence.create(pk);

		journalArticleImage.setGroupId(RandomTestUtil.nextLong());

		journalArticleImage.setArticleId(RandomTestUtil.randomString());

		journalArticleImage.setVersion(RandomTestUtil.nextDouble());

		journalArticleImage.setElInstanceId(RandomTestUtil.randomString());

		journalArticleImage.setElName(RandomTestUtil.randomString());

		journalArticleImage.setLanguageId(RandomTestUtil.randomString());

		journalArticleImage.setTempImage(RandomTestUtil.randomBoolean());

		_journalArticleImages.add(_persistence.update(journalArticleImage));

		return journalArticleImage;
	}

	private List<JournalArticleImage> _journalArticleImages = new ArrayList<JournalArticleImage>();
	private JournalArticleImagePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}