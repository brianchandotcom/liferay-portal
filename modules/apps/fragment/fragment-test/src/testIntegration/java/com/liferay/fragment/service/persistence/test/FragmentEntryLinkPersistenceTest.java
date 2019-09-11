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

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryLinkUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() {
		_persistence = FragmentEntryLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FragmentEntryLink> iterator = _fragmentEntryLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryLink fragmentEntryLink = _persistence.create(pk);

		Assert.assertNotNull(fragmentEntryLink);

		Assert.assertEquals(fragmentEntryLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		_persistence.remove(newFragmentEntryLink);

		FragmentEntryLink existingFragmentEntryLink =
			_persistence.fetchByPrimaryKey(
				newFragmentEntryLink.getPrimaryKey());

		Assert.assertNull(existingFragmentEntryLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFragmentEntryLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryLink newFragmentEntryLink = _persistence.create(pk);

		newFragmentEntryLink.setMvccVersion(RandomTestUtil.nextLong());

		newFragmentEntryLink.setUuid(RandomTestUtil.randomString());

		newFragmentEntryLink.setGroupId(RandomTestUtil.nextLong());

		newFragmentEntryLink.setCompanyId(RandomTestUtil.nextLong());

		newFragmentEntryLink.setUserId(RandomTestUtil.nextLong());

		newFragmentEntryLink.setUserName(RandomTestUtil.randomString());

		newFragmentEntryLink.setCreateDate(RandomTestUtil.nextDate());

		newFragmentEntryLink.setModifiedDate(RandomTestUtil.nextDate());

		newFragmentEntryLink.setOriginalFragmentEntryLinkId(
			RandomTestUtil.nextLong());

		newFragmentEntryLink.setFragmentEntryId(RandomTestUtil.nextLong());

		newFragmentEntryLink.setClassNameId(RandomTestUtil.nextLong());

		newFragmentEntryLink.setClassPK(RandomTestUtil.nextLong());

		newFragmentEntryLink.setCss(RandomTestUtil.randomString());

		newFragmentEntryLink.setHtml(RandomTestUtil.randomString());

		newFragmentEntryLink.setJs(RandomTestUtil.randomString());

		newFragmentEntryLink.setConfiguration(RandomTestUtil.randomString());

		newFragmentEntryLink.setEditableValues(RandomTestUtil.randomString());

		newFragmentEntryLink.setNamespace(RandomTestUtil.randomString());

		newFragmentEntryLink.setPosition(RandomTestUtil.nextInt());

		newFragmentEntryLink.setRendererKey(RandomTestUtil.randomString());

		newFragmentEntryLink.setLastPropagationDate(RandomTestUtil.nextDate());

		newFragmentEntryLink.setLastPublishDate(RandomTestUtil.nextDate());

		_fragmentEntryLinks.add(_persistence.update(newFragmentEntryLink));

		FragmentEntryLink existingFragmentEntryLink =
			_persistence.findByPrimaryKey(newFragmentEntryLink.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentEntryLink.getMvccVersion(),
			newFragmentEntryLink.getMvccVersion());
		Assert.assertEquals(
			existingFragmentEntryLink.getUuid(),
			newFragmentEntryLink.getUuid());
		Assert.assertEquals(
			existingFragmentEntryLink.getFragmentEntryLinkId(),
			newFragmentEntryLink.getFragmentEntryLinkId());
		Assert.assertEquals(
			existingFragmentEntryLink.getGroupId(),
			newFragmentEntryLink.getGroupId());
		Assert.assertEquals(
			existingFragmentEntryLink.getCompanyId(),
			newFragmentEntryLink.getCompanyId());
		Assert.assertEquals(
			existingFragmentEntryLink.getUserId(),
			newFragmentEntryLink.getUserId());
		Assert.assertEquals(
			existingFragmentEntryLink.getUserName(),
			newFragmentEntryLink.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentEntryLink.getCreateDate()),
			Time.getShortTimestamp(newFragmentEntryLink.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentEntryLink.getModifiedDate()),
			Time.getShortTimestamp(newFragmentEntryLink.getModifiedDate()));
		Assert.assertEquals(
			existingFragmentEntryLink.getOriginalFragmentEntryLinkId(),
			newFragmentEntryLink.getOriginalFragmentEntryLinkId());
		Assert.assertEquals(
			existingFragmentEntryLink.getFragmentEntryId(),
			newFragmentEntryLink.getFragmentEntryId());
		Assert.assertEquals(
			existingFragmentEntryLink.getClassNameId(),
			newFragmentEntryLink.getClassNameId());
		Assert.assertEquals(
			existingFragmentEntryLink.getClassPK(),
			newFragmentEntryLink.getClassPK());
		Assert.assertEquals(
			existingFragmentEntryLink.getCss(), newFragmentEntryLink.getCss());
		Assert.assertEquals(
			existingFragmentEntryLink.getHtml(),
			newFragmentEntryLink.getHtml());
		Assert.assertEquals(
			existingFragmentEntryLink.getJs(), newFragmentEntryLink.getJs());
		Assert.assertEquals(
			existingFragmentEntryLink.getConfiguration(),
			newFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			existingFragmentEntryLink.getEditableValues(),
			newFragmentEntryLink.getEditableValues());
		Assert.assertEquals(
			existingFragmentEntryLink.getNamespace(),
			newFragmentEntryLink.getNamespace());
		Assert.assertEquals(
			existingFragmentEntryLink.getPosition(),
			newFragmentEntryLink.getPosition());
		Assert.assertEquals(
			existingFragmentEntryLink.getRendererKey(),
			newFragmentEntryLink.getRendererKey());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentEntryLink.getLastPropagationDate()),
			Time.getShortTimestamp(
				newFragmentEntryLink.getLastPropagationDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentEntryLink.getLastPublishDate()),
			Time.getShortTimestamp(newFragmentEntryLink.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByFragmentEntryId() throws Exception {
		_persistence.countByFragmentEntryId(RandomTestUtil.nextLong());

		_persistence.countByFragmentEntryId(0L);
	}

	@Test
	public void testCountByRendererKey() throws Exception {
		_persistence.countByRendererKey("");

		_persistence.countByRendererKey("null");

		_persistence.countByRendererKey((String)null);
	}

	@Test
	public void testCountByG_F() throws Exception {
		_persistence.countByG_F(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_F(0L, 0L);
	}

	@Test
	public void testCountByG_F_C() throws Exception {
		_persistence.countByG_F_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_F_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByG_F_C_C() throws Exception {
		_persistence.countByG_F_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_F_C_C(0L, 0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		FragmentEntryLink existingFragmentEntryLink =
			_persistence.findByPrimaryKey(newFragmentEntryLink.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntryLink, newFragmentEntryLink);
	}

	@Test(expected = NoSuchEntryLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FragmentEntryLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FragmentEntryLink", "mvccVersion", true, "uuid", true,
			"fragmentEntryLinkId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "originalFragmentEntryLinkId", true,
			"fragmentEntryId", true, "classNameId", true, "classPK", true,
			"namespace", true, "position", true, "rendererKey", true,
			"lastPropagationDate", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		FragmentEntryLink existingFragmentEntryLink =
			_persistence.fetchByPrimaryKey(
				newFragmentEntryLink.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntryLink, newFragmentEntryLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryLink missingFragmentEntryLink =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFragmentEntryLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FragmentEntryLink newFragmentEntryLink1 = addFragmentEntryLink();
		FragmentEntryLink newFragmentEntryLink2 = addFragmentEntryLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryLink1.getPrimaryKey());
		primaryKeys.add(newFragmentEntryLink2.getPrimaryKey());

		Map<Serializable, FragmentEntryLink> fragmentEntryLinks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fragmentEntryLinks.size());
		Assert.assertEquals(
			newFragmentEntryLink1,
			fragmentEntryLinks.get(newFragmentEntryLink1.getPrimaryKey()));
		Assert.assertEquals(
			newFragmentEntryLink2,
			fragmentEntryLinks.get(newFragmentEntryLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FragmentEntryLink> fragmentEntryLinks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntryLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FragmentEntryLink> fragmentEntryLinks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntryLinks.size());
		Assert.assertEquals(
			newFragmentEntryLink,
			fragmentEntryLinks.get(newFragmentEntryLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FragmentEntryLink> fragmentEntryLinks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntryLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryLink.getPrimaryKey());

		Map<Serializable, FragmentEntryLink> fragmentEntryLinks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntryLinks.size());
		Assert.assertEquals(
			newFragmentEntryLink,
			fragmentEntryLinks.get(newFragmentEntryLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FragmentEntryLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FragmentEntryLink>() {

				@Override
				public void performAction(FragmentEntryLink fragmentEntryLink) {
					Assert.assertNotNull(fragmentEntryLink);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryLink.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryLinkId",
				newFragmentEntryLink.getFragmentEntryLinkId()));

		List<FragmentEntryLink> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FragmentEntryLink existingFragmentEntryLink = result.get(0);

		Assert.assertEquals(existingFragmentEntryLink, newFragmentEntryLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryLink.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryLinkId", RandomTestUtil.nextLong()));

		List<FragmentEntryLink> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryLink.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentEntryLinkId"));

		Object newFragmentEntryLinkId =
			newFragmentEntryLink.getFragmentEntryLinkId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentEntryLinkId", new Object[] {newFragmentEntryLinkId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFragmentEntryLinkId = result.get(0);

		Assert.assertEquals(
			existingFragmentEntryLinkId, newFragmentEntryLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntryLink.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentEntryLinkId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentEntryLinkId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FragmentEntryLink newFragmentEntryLink = addFragmentEntryLink();

		_persistence.clearCache();

		FragmentEntryLink existingFragmentEntryLink =
			_persistence.findByPrimaryKey(newFragmentEntryLink.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingFragmentEntryLink.getUuid(),
				ReflectionTestUtil.invoke(
					existingFragmentEntryLink, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingFragmentEntryLink.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFragmentEntryLink, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected FragmentEntryLink addFragmentEntryLink() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryLink fragmentEntryLink = _persistence.create(pk);

		fragmentEntryLink.setMvccVersion(RandomTestUtil.nextLong());

		fragmentEntryLink.setUuid(RandomTestUtil.randomString());

		fragmentEntryLink.setGroupId(RandomTestUtil.nextLong());

		fragmentEntryLink.setCompanyId(RandomTestUtil.nextLong());

		fragmentEntryLink.setUserId(RandomTestUtil.nextLong());

		fragmentEntryLink.setUserName(RandomTestUtil.randomString());

		fragmentEntryLink.setCreateDate(RandomTestUtil.nextDate());

		fragmentEntryLink.setModifiedDate(RandomTestUtil.nextDate());

		fragmentEntryLink.setOriginalFragmentEntryLinkId(
			RandomTestUtil.nextLong());

		fragmentEntryLink.setFragmentEntryId(RandomTestUtil.nextLong());

		fragmentEntryLink.setClassNameId(RandomTestUtil.nextLong());

		fragmentEntryLink.setClassPK(RandomTestUtil.nextLong());

		fragmentEntryLink.setCss(RandomTestUtil.randomString());

		fragmentEntryLink.setHtml(RandomTestUtil.randomString());

		fragmentEntryLink.setJs(RandomTestUtil.randomString());

		fragmentEntryLink.setConfiguration(RandomTestUtil.randomString());

		fragmentEntryLink.setEditableValues(RandomTestUtil.randomString());

		fragmentEntryLink.setNamespace(RandomTestUtil.randomString());

		fragmentEntryLink.setPosition(RandomTestUtil.nextInt());

		fragmentEntryLink.setRendererKey(RandomTestUtil.randomString());

		fragmentEntryLink.setLastPropagationDate(RandomTestUtil.nextDate());

		fragmentEntryLink.setLastPublishDate(RandomTestUtil.nextDate());

		_fragmentEntryLinks.add(_persistence.update(fragmentEntryLink));

		return fragmentEntryLink;
	}

	private List<FragmentEntryLink> _fragmentEntryLinks =
		new ArrayList<FragmentEntryLink>();
	private FragmentEntryLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}