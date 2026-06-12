/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.audience.exception.DuplicateAudienceEntryExternalReferenceCodeException;
import com.liferay.audience.exception.NoSuchAudienceEntryException;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.AudienceEntryLocalServiceUtil;
import com.liferay.audience.service.persistence.AudienceEntryPersistence;
import com.liferay.audience.service.persistence.AudienceEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
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
public class AudienceEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.audience.service"));

	@Before
	public void setUp() {
		_persistence = AudienceEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AudienceEntry> iterator = _audienceEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AudienceEntry audienceEntry = _persistence.create(pk);

		Assert.assertNotNull(audienceEntry);

		Assert.assertEquals(audienceEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		_persistence.remove(newAudienceEntry);

		AudienceEntry existingAudienceEntry = _persistence.fetchByPrimaryKey(
			newAudienceEntry.getPrimaryKey());

		Assert.assertNull(existingAudienceEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAudienceEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		newAudienceEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newAudienceEntry.setCompanyId(RandomTestUtil.nextLong());

		newAudienceEntry.setUserId(RandomTestUtil.nextLong());

		newAudienceEntry.setUserName(RandomTestUtil.randomString());

		newAudienceEntry.setCreateDate(RandomTestUtil.nextDate());

		newAudienceEntry.setModifiedDate(RandomTestUtil.nextDate());

		newAudienceEntry.setJSON(RandomTestUtil.randomString());

		newAudienceEntry.setName(RandomTestUtil.randomString());

		_audienceEntries.add(_persistence.update(newAudienceEntry));

		AudienceEntry existingAudienceEntry = _persistence.findByPrimaryKey(
			newAudienceEntry.getPrimaryKey());

		Assert.assertEquals(
			existingAudienceEntry.getMvccVersion(),
			newAudienceEntry.getMvccVersion());
		Assert.assertEquals(
			existingAudienceEntry.getExternalReferenceCode(),
			newAudienceEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingAudienceEntry.getAudienceEntryId(),
			newAudienceEntry.getAudienceEntryId());
		Assert.assertEquals(
			existingAudienceEntry.getCompanyId(),
			newAudienceEntry.getCompanyId());
		Assert.assertEquals(
			existingAudienceEntry.getUserId(), newAudienceEntry.getUserId());
		Assert.assertEquals(
			existingAudienceEntry.getUserName(),
			newAudienceEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAudienceEntry.getCreateDate()),
			Time.getShortTimestamp(newAudienceEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAudienceEntry.getModifiedDate()),
			Time.getShortTimestamp(newAudienceEntry.getModifiedDate()));
		Assert.assertEquals(
			existingAudienceEntry.getJSON(), newAudienceEntry.getJSON());
		Assert.assertEquals(
			existingAudienceEntry.getName(), newAudienceEntry.getName());
	}

	@Test(expected = DuplicateAudienceEntryExternalReferenceCodeException.class)
	public void testUpdateWithExistingExternalReferenceCode() throws Exception {
		AudienceEntry audienceEntry = addAudienceEntry();

		AudienceEntry newAudienceEntry = addAudienceEntry();

		newAudienceEntry.setCompanyId(audienceEntry.getCompanyId());

		newAudienceEntry = _persistence.update(newAudienceEntry);

		Session session = _persistence.getCurrentSession();

		session.evict(newAudienceEntry);

		newAudienceEntry.setExternalReferenceCode(
			audienceEntry.getExternalReferenceCode());

		_persistence.update(newAudienceEntry);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_LikeN() throws Exception {
		_persistence.countByC_LikeN(RandomTestUtil.nextLong(), "");

		_persistence.countByC_LikeN(0L, "null");

		_persistence.countByC_LikeN(0L, (String)null);
	}

	@Test
	public void testCountByERC_C() throws Exception {
		_persistence.countByERC_C("", RandomTestUtil.nextLong());

		_persistence.countByERC_C("null", 0L);

		_persistence.countByERC_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		AudienceEntry existingAudienceEntry = _persistence.findByPrimaryKey(
			newAudienceEntry.getPrimaryKey());

		Assert.assertEquals(existingAudienceEntry, newAudienceEntry);
	}

	@Test(expected = NoSuchAudienceEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AudienceEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AudienceEntry", "mvccVersion", true, "externalReferenceCode", true,
			"audienceEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		AudienceEntry existingAudienceEntry = _persistence.fetchByPrimaryKey(
			newAudienceEntry.getPrimaryKey());

		Assert.assertEquals(existingAudienceEntry, newAudienceEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AudienceEntry missingAudienceEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAudienceEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AudienceEntry newAudienceEntry1 = addAudienceEntry();
		AudienceEntry newAudienceEntry2 = addAudienceEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAudienceEntry1.getPrimaryKey());
		primaryKeys.add(newAudienceEntry2.getPrimaryKey());

		Map<Serializable, AudienceEntry> audienceEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, audienceEntries.size());
		Assert.assertEquals(
			newAudienceEntry1,
			audienceEntries.get(newAudienceEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newAudienceEntry2,
			audienceEntries.get(newAudienceEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AudienceEntry> audienceEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(audienceEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AudienceEntry newAudienceEntry = addAudienceEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAudienceEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AudienceEntry> audienceEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, audienceEntries.size());
		Assert.assertEquals(
			newAudienceEntry,
			audienceEntries.get(newAudienceEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AudienceEntry> audienceEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(audienceEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAudienceEntry.getPrimaryKey());

		Map<Serializable, AudienceEntry> audienceEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, audienceEntries.size());
		Assert.assertEquals(
			newAudienceEntry,
			audienceEntries.get(newAudienceEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AudienceEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AudienceEntry>() {

				@Override
				public void performAction(AudienceEntry audienceEntry) {
					Assert.assertNotNull(audienceEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AudienceEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"audienceEntryId", newAudienceEntry.getAudienceEntryId()));

		List<AudienceEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AudienceEntry existingAudienceEntry = result.get(0);

		Assert.assertEquals(existingAudienceEntry, newAudienceEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AudienceEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"audienceEntryId", RandomTestUtil.nextLong()));

		List<AudienceEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AudienceEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("audienceEntryId"));

		Object newAudienceEntryId = newAudienceEntry.getAudienceEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"audienceEntryId", new Object[] {newAudienceEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAudienceEntryId = result.get(0);

		Assert.assertEquals(existingAudienceEntryId, newAudienceEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AudienceEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("audienceEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"audienceEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AudienceEntry newAudienceEntry = addAudienceEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newAudienceEntry.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		AudienceEntry newAudienceEntry = addAudienceEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AudienceEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"audienceEntryId", newAudienceEntry.getAudienceEntryId()));

		List<AudienceEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(AudienceEntry audienceEntry) {
		Assert.assertEquals(
			audienceEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				audienceEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
		Assert.assertEquals(
			Long.valueOf(audienceEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				audienceEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
	}

	protected AudienceEntry addAudienceEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AudienceEntry audienceEntry = _persistence.create(pk);

		audienceEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		audienceEntry.setCompanyId(RandomTestUtil.nextLong());

		audienceEntry.setUserId(RandomTestUtil.nextLong());

		audienceEntry.setUserName(RandomTestUtil.randomString());

		audienceEntry.setCreateDate(RandomTestUtil.nextDate());

		audienceEntry.setModifiedDate(RandomTestUtil.nextDate());

		audienceEntry.setJSON(RandomTestUtil.randomString());

		audienceEntry.setName(RandomTestUtil.randomString());

		_audienceEntries.add(_persistence.update(audienceEntry));

		return audienceEntry;
	}

	private List<AudienceEntry> _audienceEntries =
		new ArrayList<AudienceEntry>();
	private AudienceEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:2001438598