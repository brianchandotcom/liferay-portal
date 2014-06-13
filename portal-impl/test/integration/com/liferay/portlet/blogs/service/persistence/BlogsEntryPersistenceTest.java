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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryModelImpl;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class BlogsEntryPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<BlogsEntry> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<BlogsEntry> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsEntry blogsEntry = _persistence.create(pk);

		Assert.assertNotNull(blogsEntry);

		Assert.assertEquals(blogsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		_persistence.remove(newBlogsEntry);

		BlogsEntry existingBlogsEntry = _persistence.fetchByPrimaryKey(newBlogsEntry.getPrimaryKey());

		Assert.assertNull(existingBlogsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBlogsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsEntry newBlogsEntry = _persistence.create(pk);

		newBlogsEntry.setUuid(RandomTestUtil.randomString());

		newBlogsEntry.setGroupId(RandomTestUtil.nextLong());

		newBlogsEntry.setCompanyId(RandomTestUtil.nextLong());

		newBlogsEntry.setUserId(RandomTestUtil.nextLong());

		newBlogsEntry.setUserName(RandomTestUtil.randomString());

		newBlogsEntry.setCreateDate(RandomTestUtil.nextDate());

		newBlogsEntry.setModifiedDate(RandomTestUtil.nextDate());

		newBlogsEntry.setTitle(RandomTestUtil.randomString());

		newBlogsEntry.setUrlTitle(RandomTestUtil.randomString());

		newBlogsEntry.setDeckTitle(RandomTestUtil.randomString());

		newBlogsEntry.setDescription(RandomTestUtil.randomString());

		newBlogsEntry.setContent(RandomTestUtil.randomString());

		newBlogsEntry.setDisplayDate(RandomTestUtil.nextDate());

		newBlogsEntry.setAllowPingbacks(RandomTestUtil.randomBoolean());

		newBlogsEntry.setAllowTrackbacks(RandomTestUtil.randomBoolean());

		newBlogsEntry.setTrackbacks(RandomTestUtil.randomString());

		newBlogsEntry.setSmallImage(RandomTestUtil.randomBoolean());

		newBlogsEntry.setSmallImageId(RandomTestUtil.nextLong());

		newBlogsEntry.setSmallImageURL(RandomTestUtil.randomString());

		newBlogsEntry.setStatus(RandomTestUtil.nextInt());

		newBlogsEntry.setStatusByUserId(RandomTestUtil.nextLong());

		newBlogsEntry.setStatusByUserName(RandomTestUtil.randomString());

		newBlogsEntry.setStatusDate(RandomTestUtil.nextDate());

		_persistence.update(newBlogsEntry);

		BlogsEntry existingBlogsEntry = _persistence.findByPrimaryKey(newBlogsEntry.getPrimaryKey());

		Assert.assertEquals(existingBlogsEntry.getUuid(),
			newBlogsEntry.getUuid());
		Assert.assertEquals(existingBlogsEntry.getEntryId(),
			newBlogsEntry.getEntryId());
		Assert.assertEquals(existingBlogsEntry.getGroupId(),
			newBlogsEntry.getGroupId());
		Assert.assertEquals(existingBlogsEntry.getCompanyId(),
			newBlogsEntry.getCompanyId());
		Assert.assertEquals(existingBlogsEntry.getUserId(),
			newBlogsEntry.getUserId());
		Assert.assertEquals(existingBlogsEntry.getUserName(),
			newBlogsEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBlogsEntry.getCreateDate()),
			Time.getShortTimestamp(newBlogsEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingBlogsEntry.getModifiedDate()),
			Time.getShortTimestamp(newBlogsEntry.getModifiedDate()));
		Assert.assertEquals(existingBlogsEntry.getTitle(),
			newBlogsEntry.getTitle());
		Assert.assertEquals(existingBlogsEntry.getUrlTitle(),
			newBlogsEntry.getUrlTitle());
		Assert.assertEquals(existingBlogsEntry.getDeckTitle(),
			newBlogsEntry.getDeckTitle());
		Assert.assertEquals(existingBlogsEntry.getDescription(),
			newBlogsEntry.getDescription());
		Assert.assertEquals(existingBlogsEntry.getContent(),
			newBlogsEntry.getContent());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBlogsEntry.getDisplayDate()),
			Time.getShortTimestamp(newBlogsEntry.getDisplayDate()));
		Assert.assertEquals(existingBlogsEntry.getAllowPingbacks(),
			newBlogsEntry.getAllowPingbacks());
		Assert.assertEquals(existingBlogsEntry.getAllowTrackbacks(),
			newBlogsEntry.getAllowTrackbacks());
		Assert.assertEquals(existingBlogsEntry.getTrackbacks(),
			newBlogsEntry.getTrackbacks());
		Assert.assertEquals(existingBlogsEntry.getSmallImage(),
			newBlogsEntry.getSmallImage());
		Assert.assertEquals(existingBlogsEntry.getSmallImageId(),
			newBlogsEntry.getSmallImageId());
		Assert.assertEquals(existingBlogsEntry.getSmallImageURL(),
			newBlogsEntry.getSmallImageURL());
		Assert.assertEquals(existingBlogsEntry.getStatus(),
			newBlogsEntry.getStatus());
		Assert.assertEquals(existingBlogsEntry.getStatusByUserId(),
			newBlogsEntry.getStatusByUserId());
		Assert.assertEquals(existingBlogsEntry.getStatusByUserName(),
			newBlogsEntry.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBlogsEntry.getStatusDate()),
			Time.getShortTimestamp(newBlogsEntry.getStatusDate()));
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_U() {
		try {
			_persistence.countByC_U(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_LtD() {
		try {
			_persistence.countByC_LtD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate());

			_persistence.countByC_LtD(0L, RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_NotS() {
		try {
			_persistence.countByC_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_NotS(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_S() {
		try {
			_persistence.countByC_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_UT() {
		try {
			_persistence.countByG_UT(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_UT(0L, StringPool.NULL);

			_persistence.countByG_UT(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LtD() {
		try {
			_persistence.countByG_LtD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate());

			_persistence.countByG_LtD(0L, RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_NotS() {
		try {
			_persistence.countByG_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_NotS(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLtD_S() {
		try {
			_persistence.countByLtD_S(RandomTestUtil.nextDate(),
				RandomTestUtil.nextInt());

			_persistence.countByLtD_S(RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_U_NotS() {
		try {
			_persistence.countByC_U_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByC_U_NotS(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_U_S() {
		try {
			_persistence.countByC_U_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByC_U_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_LtD_NotS() {
		try {
			_persistence.countByC_LtD_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

			_persistence.countByC_LtD_NotS(0L, RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_LtD_S() {
		try {
			_persistence.countByC_LtD_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

			_persistence.countByC_LtD_S(0L, RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_LtD() {
		try {
			_persistence.countByG_U_LtD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextDate());

			_persistence.countByG_U_LtD(0L, 0L, RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_NotS() {
		try {
			_persistence.countByG_U_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_U_NotS(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_S() {
		try {
			_persistence.countByG_U_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_U_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LtD_NotS() {
		try {
			_persistence.countByG_LtD_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

			_persistence.countByG_LtD_NotS(0L, RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LtD_S() {
		try {
			_persistence.countByG_LtD_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

			_persistence.countByG_LtD_S(0L, RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_LtD_NotS() {
		try {
			_persistence.countByG_U_LtD_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextDate(),
				RandomTestUtil.nextInt());

			_persistence.countByG_U_LtD_NotS(0L, 0L, RandomTestUtil.nextDate(),
				0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_LtD_S() {
		try {
			_persistence.countByG_U_LtD_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextDate(),
				RandomTestUtil.nextInt());

			_persistence.countByG_U_LtD_S(0L, 0L, RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		BlogsEntry existingBlogsEntry = _persistence.findByPrimaryKey(newBlogsEntry.getPrimaryKey());

		Assert.assertEquals(existingBlogsEntry, newBlogsEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("BlogsEntry", "uuid", true,
			"entryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"title", true, "urlTitle", true, "deckTitle", true, "description",
			true, "content", true, "displayDate", true, "allowPingbacks", true,
			"allowTrackbacks", true, "trackbacks", true, "smallImage", true,
			"smallImageId", true, "smallImageURL", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		BlogsEntry existingBlogsEntry = _persistence.fetchByPrimaryKey(newBlogsEntry.getPrimaryKey());

		Assert.assertEquals(existingBlogsEntry, newBlogsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsEntry missingBlogsEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBlogsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BlogsEntry newBlogsEntry1 = addBlogsEntry();
		BlogsEntry newBlogsEntry2 = addBlogsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogsEntry1.getPrimaryKey());
		primaryKeys.add(newBlogsEntry2.getPrimaryKey());

		Map<Serializable, BlogsEntry> blogsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, blogsEntries.size());
		Assert.assertEquals(newBlogsEntry1,
			blogsEntries.get(newBlogsEntry1.getPrimaryKey()));
		Assert.assertEquals(newBlogsEntry2,
			blogsEntries.get(newBlogsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BlogsEntry> blogsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blogsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BlogsEntry> blogsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blogsEntries.size());
		Assert.assertEquals(newBlogsEntry,
			blogsEntries.get(newBlogsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BlogsEntry> blogsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blogsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogsEntry.getPrimaryKey());

		Map<Serializable, BlogsEntry> blogsEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blogsEntries.size());
		Assert.assertEquals(newBlogsEntry,
			blogsEntries.get(newBlogsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BlogsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					BlogsEntry blogsEntry = (BlogsEntry)object;

					Assert.assertNotNull(blogsEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newBlogsEntry.getEntryId()));

		List<BlogsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BlogsEntry existingBlogsEntry = result.get(0);

		Assert.assertEquals(existingBlogsEntry, newBlogsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<BlogsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BlogsEntry newBlogsEntry = addBlogsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newBlogsEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsEntry.class,
				BlogsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BlogsEntry newBlogsEntry = addBlogsEntry();

		_persistence.clearCache();

		BlogsEntryModelImpl existingBlogsEntryModelImpl = (BlogsEntryModelImpl)_persistence.findByPrimaryKey(newBlogsEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingBlogsEntryModelImpl.getUuid(),
				existingBlogsEntryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingBlogsEntryModelImpl.getGroupId(),
			existingBlogsEntryModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingBlogsEntryModelImpl.getGroupId(),
			existingBlogsEntryModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingBlogsEntryModelImpl.getUrlTitle(),
				existingBlogsEntryModelImpl.getOriginalUrlTitle()));
	}

	protected BlogsEntry addBlogsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsEntry blogsEntry = _persistence.create(pk);

		blogsEntry.setUuid(RandomTestUtil.randomString());

		blogsEntry.setGroupId(RandomTestUtil.nextLong());

		blogsEntry.setCompanyId(RandomTestUtil.nextLong());

		blogsEntry.setUserId(RandomTestUtil.nextLong());

		blogsEntry.setUserName(RandomTestUtil.randomString());

		blogsEntry.setCreateDate(RandomTestUtil.nextDate());

		blogsEntry.setModifiedDate(RandomTestUtil.nextDate());

		blogsEntry.setTitle(RandomTestUtil.randomString());

		blogsEntry.setUrlTitle(RandomTestUtil.randomString());

		blogsEntry.setDeckTitle(RandomTestUtil.randomString());

		blogsEntry.setDescription(RandomTestUtil.randomString());

		blogsEntry.setContent(RandomTestUtil.randomString());

		blogsEntry.setDisplayDate(RandomTestUtil.nextDate());

		blogsEntry.setAllowPingbacks(RandomTestUtil.randomBoolean());

		blogsEntry.setAllowTrackbacks(RandomTestUtil.randomBoolean());

		blogsEntry.setTrackbacks(RandomTestUtil.randomString());

		blogsEntry.setSmallImage(RandomTestUtil.randomBoolean());

		blogsEntry.setSmallImageId(RandomTestUtil.nextLong());

		blogsEntry.setSmallImageURL(RandomTestUtil.randomString());

		blogsEntry.setStatus(RandomTestUtil.nextInt());

		blogsEntry.setStatusByUserId(RandomTestUtil.nextLong());

		blogsEntry.setStatusByUserName(RandomTestUtil.randomString());

		blogsEntry.setStatusDate(RandomTestUtil.nextDate());

		_persistence.update(blogsEntry);

		return blogsEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(BlogsEntryPersistenceTest.class);
	private ModelListener<BlogsEntry>[] _modelListeners;
	private BlogsEntryPersistence _persistence = (BlogsEntryPersistence)PortalBeanLocatorUtil.locate(BlogsEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}