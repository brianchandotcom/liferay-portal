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

package com.liferay.html.preview.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.html.preview.exception.NoSuchHtmlPreviewException;
import com.liferay.html.preview.model.HtmlPreview;
import com.liferay.html.preview.service.HtmlPreviewLocalServiceUtil;
import com.liferay.html.preview.service.persistence.HtmlPreviewPersistence;
import com.liferay.html.preview.service.persistence.HtmlPreviewUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

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
@RunWith(Arquillian.class)
public class HtmlPreviewPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.html.preview.service"));

	@Before
	public void setUp() {
		_persistence = HtmlPreviewUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<HtmlPreview> iterator = _htmlPreviews.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreview htmlPreview = _persistence.create(pk);

		Assert.assertNotNull(htmlPreview);

		Assert.assertEquals(htmlPreview.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		_persistence.remove(newHtmlPreview);

		HtmlPreview existingHtmlPreview = _persistence.fetchByPrimaryKey(newHtmlPreview.getPrimaryKey());

		Assert.assertNull(existingHtmlPreview);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addHtmlPreview();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreview newHtmlPreview = _persistence.create(pk);

		newHtmlPreview.setGroupId(RandomTestUtil.nextLong());

		newHtmlPreview.setCompanyId(RandomTestUtil.nextLong());

		newHtmlPreview.setUserId(RandomTestUtil.nextLong());

		newHtmlPreview.setUserName(RandomTestUtil.randomString());

		newHtmlPreview.setCreateDate(RandomTestUtil.nextDate());

		newHtmlPreview.setModifiedDate(RandomTestUtil.nextDate());

		newHtmlPreview.setClassNameId(RandomTestUtil.nextLong());

		newHtmlPreview.setClassPK(RandomTestUtil.nextLong());

		newHtmlPreview.setFileEntryId(RandomTestUtil.nextLong());

		_htmlPreviews.add(_persistence.update(newHtmlPreview));

		HtmlPreview existingHtmlPreview = _persistence.findByPrimaryKey(newHtmlPreview.getPrimaryKey());

		Assert.assertEquals(existingHtmlPreview.getHtmlPreviewId(),
			newHtmlPreview.getHtmlPreviewId());
		Assert.assertEquals(existingHtmlPreview.getGroupId(),
			newHtmlPreview.getGroupId());
		Assert.assertEquals(existingHtmlPreview.getCompanyId(),
			newHtmlPreview.getCompanyId());
		Assert.assertEquals(existingHtmlPreview.getUserId(),
			newHtmlPreview.getUserId());
		Assert.assertEquals(existingHtmlPreview.getUserName(),
			newHtmlPreview.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingHtmlPreview.getCreateDate()),
			Time.getShortTimestamp(newHtmlPreview.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingHtmlPreview.getModifiedDate()),
			Time.getShortTimestamp(newHtmlPreview.getModifiedDate()));
		Assert.assertEquals(existingHtmlPreview.getClassNameId(),
			newHtmlPreview.getClassNameId());
		Assert.assertEquals(existingHtmlPreview.getClassPK(),
			newHtmlPreview.getClassPK());
		Assert.assertEquals(existingHtmlPreview.getFileEntryId(),
			newHtmlPreview.getFileEntryId());
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		HtmlPreview existingHtmlPreview = _persistence.findByPrimaryKey(newHtmlPreview.getPrimaryKey());

		Assert.assertEquals(existingHtmlPreview, newHtmlPreview);
	}

	@Test(expected = NoSuchHtmlPreviewException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<HtmlPreview> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("HtmlPreview",
			"htmlPreviewId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"fileEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		HtmlPreview existingHtmlPreview = _persistence.fetchByPrimaryKey(newHtmlPreview.getPrimaryKey());

		Assert.assertEquals(existingHtmlPreview, newHtmlPreview);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreview missingHtmlPreview = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingHtmlPreview);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		HtmlPreview newHtmlPreview1 = addHtmlPreview();
		HtmlPreview newHtmlPreview2 = addHtmlPreview();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newHtmlPreview1.getPrimaryKey());
		primaryKeys.add(newHtmlPreview2.getPrimaryKey());

		Map<Serializable, HtmlPreview> htmlPreviews = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, htmlPreviews.size());
		Assert.assertEquals(newHtmlPreview1,
			htmlPreviews.get(newHtmlPreview1.getPrimaryKey()));
		Assert.assertEquals(newHtmlPreview2,
			htmlPreviews.get(newHtmlPreview2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, HtmlPreview> htmlPreviews = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(htmlPreviews.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newHtmlPreview.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, HtmlPreview> htmlPreviews = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, htmlPreviews.size());
		Assert.assertEquals(newHtmlPreview,
			htmlPreviews.get(newHtmlPreview.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, HtmlPreview> htmlPreviews = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(htmlPreviews.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newHtmlPreview.getPrimaryKey());

		Map<Serializable, HtmlPreview> htmlPreviews = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, htmlPreviews.size());
		Assert.assertEquals(newHtmlPreview,
			htmlPreviews.get(newHtmlPreview.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = HtmlPreviewLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<HtmlPreview>() {
				@Override
				public void performAction(HtmlPreview htmlPreview) {
					Assert.assertNotNull(htmlPreview);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(HtmlPreview.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("htmlPreviewId",
				newHtmlPreview.getHtmlPreviewId()));

		List<HtmlPreview> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		HtmlPreview existingHtmlPreview = result.get(0);

		Assert.assertEquals(existingHtmlPreview, newHtmlPreview);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(HtmlPreview.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("htmlPreviewId",
				RandomTestUtil.nextLong()));

		List<HtmlPreview> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(HtmlPreview.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"htmlPreviewId"));

		Object newHtmlPreviewId = newHtmlPreview.getHtmlPreviewId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("htmlPreviewId",
				new Object[] { newHtmlPreviewId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingHtmlPreviewId = result.get(0);

		Assert.assertEquals(existingHtmlPreviewId, newHtmlPreviewId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(HtmlPreview.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"htmlPreviewId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("htmlPreviewId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		HtmlPreview newHtmlPreview = addHtmlPreview();

		_persistence.clearCache();

		HtmlPreview existingHtmlPreview = _persistence.findByPrimaryKey(newHtmlPreview.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingHtmlPreview.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingHtmlPreview,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingHtmlPreview.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingHtmlPreview,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingHtmlPreview.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingHtmlPreview,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected HtmlPreview addHtmlPreview() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreview htmlPreview = _persistence.create(pk);

		htmlPreview.setGroupId(RandomTestUtil.nextLong());

		htmlPreview.setCompanyId(RandomTestUtil.nextLong());

		htmlPreview.setUserId(RandomTestUtil.nextLong());

		htmlPreview.setUserName(RandomTestUtil.randomString());

		htmlPreview.setCreateDate(RandomTestUtil.nextDate());

		htmlPreview.setModifiedDate(RandomTestUtil.nextDate());

		htmlPreview.setClassNameId(RandomTestUtil.nextLong());

		htmlPreview.setClassPK(RandomTestUtil.nextLong());

		htmlPreview.setFileEntryId(RandomTestUtil.nextLong());

		_htmlPreviews.add(_persistence.update(htmlPreview));

		return htmlPreview;
	}

	private List<HtmlPreview> _htmlPreviews = new ArrayList<HtmlPreview>();
	private HtmlPreviewPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}