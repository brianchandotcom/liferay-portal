/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.style.book.exception.DuplicateStyleBookTokenSetExternalReferenceCodeException;
import com.liferay.style.book.exception.NoSuchTokenSetException;
import com.liferay.style.book.model.StyleBookTokenSet;
import com.liferay.style.book.service.StyleBookTokenSetLocalServiceUtil;
import com.liferay.style.book.service.persistence.StyleBookTokenSetPersistence;
import com.liferay.style.book.service.persistence.StyleBookTokenSetUtil;

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
public class StyleBookTokenSetPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.style.book.service"));

	@Before
	public void setUp() {
		_persistence = StyleBookTokenSetUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<StyleBookTokenSet> iterator = _styleBookTokenSets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		StyleBookTokenSet styleBookTokenSet = _persistence.create(pk);

		Assert.assertNotNull(styleBookTokenSet);

		Assert.assertEquals(styleBookTokenSet.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		_persistence.remove(newStyleBookTokenSet);

		StyleBookTokenSet existingStyleBookTokenSet =
			_persistence.fetchByPrimaryKey(
				newStyleBookTokenSet.getPrimaryKey());

		Assert.assertNull(existingStyleBookTokenSet);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addStyleBookTokenSet();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		StyleBookTokenSet newStyleBookTokenSet = _persistence.create(pk);

		newStyleBookTokenSet.setMvccVersion(RandomTestUtil.nextLong());

		newStyleBookTokenSet.setCtCollectionId(RandomTestUtil.nextLong());

		newStyleBookTokenSet.setUuid(RandomTestUtil.randomString());

		newStyleBookTokenSet.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newStyleBookTokenSet.setGroupId(RandomTestUtil.nextLong());

		newStyleBookTokenSet.setCompanyId(RandomTestUtil.nextLong());

		newStyleBookTokenSet.setUserId(RandomTestUtil.nextLong());

		newStyleBookTokenSet.setUserName(RandomTestUtil.randomString());

		newStyleBookTokenSet.setCreateDate(RandomTestUtil.nextDate());

		newStyleBookTokenSet.setModifiedDate(RandomTestUtil.nextDate());

		newStyleBookTokenSet.setDescription(RandomTestUtil.randomString());

		newStyleBookTokenSet.setFrontendTokenCategoryName(
			RandomTestUtil.randomString());

		newStyleBookTokenSet.setFrontendTokenDefinitionId(
			RandomTestUtil.randomString());

		newStyleBookTokenSet.setName(RandomTestUtil.randomString());

		newStyleBookTokenSet.setStyleBookEntryId(RandomTestUtil.nextLong());

		_styleBookTokenSets.add(_persistence.update(newStyleBookTokenSet));

		StyleBookTokenSet existingStyleBookTokenSet =
			_persistence.findByPrimaryKey(newStyleBookTokenSet.getPrimaryKey());

		Assert.assertEquals(
			existingStyleBookTokenSet.getMvccVersion(),
			newStyleBookTokenSet.getMvccVersion());
		Assert.assertEquals(
			existingStyleBookTokenSet.getCtCollectionId(),
			newStyleBookTokenSet.getCtCollectionId());
		Assert.assertEquals(
			existingStyleBookTokenSet.getUuid(),
			newStyleBookTokenSet.getUuid());
		Assert.assertEquals(
			existingStyleBookTokenSet.getExternalReferenceCode(),
			newStyleBookTokenSet.getExternalReferenceCode());
		Assert.assertEquals(
			existingStyleBookTokenSet.getStyleBookTokenSetId(),
			newStyleBookTokenSet.getStyleBookTokenSetId());
		Assert.assertEquals(
			existingStyleBookTokenSet.getGroupId(),
			newStyleBookTokenSet.getGroupId());
		Assert.assertEquals(
			existingStyleBookTokenSet.getCompanyId(),
			newStyleBookTokenSet.getCompanyId());
		Assert.assertEquals(
			existingStyleBookTokenSet.getUserId(),
			newStyleBookTokenSet.getUserId());
		Assert.assertEquals(
			existingStyleBookTokenSet.getUserName(),
			newStyleBookTokenSet.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingStyleBookTokenSet.getCreateDate()),
			Time.getShortTimestamp(newStyleBookTokenSet.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingStyleBookTokenSet.getModifiedDate()),
			Time.getShortTimestamp(newStyleBookTokenSet.getModifiedDate()));
		Assert.assertEquals(
			existingStyleBookTokenSet.getDescription(),
			newStyleBookTokenSet.getDescription());
		Assert.assertEquals(
			existingStyleBookTokenSet.getFrontendTokenCategoryName(),
			newStyleBookTokenSet.getFrontendTokenCategoryName());
		Assert.assertEquals(
			existingStyleBookTokenSet.getFrontendTokenDefinitionId(),
			newStyleBookTokenSet.getFrontendTokenDefinitionId());
		Assert.assertEquals(
			existingStyleBookTokenSet.getName(),
			newStyleBookTokenSet.getName());
		Assert.assertEquals(
			existingStyleBookTokenSet.getStyleBookEntryId(),
			newStyleBookTokenSet.getStyleBookEntryId());
	}

	@Test(
		expected = DuplicateStyleBookTokenSetExternalReferenceCodeException.class
	)
	public void testUpdateWithExistingExternalReferenceCode() throws Exception {
		StyleBookTokenSet styleBookTokenSet = addStyleBookTokenSet();

		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		newStyleBookTokenSet.setGroupId(styleBookTokenSet.getGroupId());

		newStyleBookTokenSet = _persistence.update(newStyleBookTokenSet);

		Session session = _persistence.getCurrentSession();

		session.evict(newStyleBookTokenSet);

		newStyleBookTokenSet.setExternalReferenceCode(
			styleBookTokenSet.getExternalReferenceCode());

		_persistence.update(newStyleBookTokenSet);
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
	public void testCountByStyleBookEntryId() throws Exception {
		_persistence.countByStyleBookEntryId(RandomTestUtil.nextLong());

		_persistence.countByStyleBookEntryId(0L);
	}

	@Test
	public void testCountByFTCN_FTDI_N_SBEI() throws Exception {
		_persistence.countByFTCN_FTDI_N_SBEI(
			"", "", "", RandomTestUtil.nextLong());

		_persistence.countByFTCN_FTDI_N_SBEI("null", "null", "null", 0L);

		_persistence.countByFTCN_FTDI_N_SBEI(
			(String)null, (String)null, (String)null, 0L);
	}

	@Test
	public void testCountByERC_G() throws Exception {
		_persistence.countByERC_G("", RandomTestUtil.nextLong());

		_persistence.countByERC_G("null", 0L);

		_persistence.countByERC_G((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		StyleBookTokenSet existingStyleBookTokenSet =
			_persistence.findByPrimaryKey(newStyleBookTokenSet.getPrimaryKey());

		Assert.assertEquals(existingStyleBookTokenSet, newStyleBookTokenSet);
	}

	@Test(expected = NoSuchTokenSetException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<StyleBookTokenSet> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"StyleBookTokenSet", "mvccVersion", true, "ctCollectionId", true,
			"uuid", true, "externalReferenceCode", true, "styleBookTokenSetId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"description", true, "frontendTokenCategoryName", true,
			"frontendTokenDefinitionId", true, "name", true, "styleBookEntryId",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		StyleBookTokenSet existingStyleBookTokenSet =
			_persistence.fetchByPrimaryKey(
				newStyleBookTokenSet.getPrimaryKey());

		Assert.assertEquals(existingStyleBookTokenSet, newStyleBookTokenSet);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		StyleBookTokenSet missingStyleBookTokenSet =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingStyleBookTokenSet);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		StyleBookTokenSet newStyleBookTokenSet1 = addStyleBookTokenSet();
		StyleBookTokenSet newStyleBookTokenSet2 = addStyleBookTokenSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStyleBookTokenSet1.getPrimaryKey());
		primaryKeys.add(newStyleBookTokenSet2.getPrimaryKey());

		Map<Serializable, StyleBookTokenSet> styleBookTokenSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, styleBookTokenSets.size());
		Assert.assertEquals(
			newStyleBookTokenSet1,
			styleBookTokenSets.get(newStyleBookTokenSet1.getPrimaryKey()));
		Assert.assertEquals(
			newStyleBookTokenSet2,
			styleBookTokenSets.get(newStyleBookTokenSet2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, StyleBookTokenSet> styleBookTokenSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(styleBookTokenSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStyleBookTokenSet.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, StyleBookTokenSet> styleBookTokenSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, styleBookTokenSets.size());
		Assert.assertEquals(
			newStyleBookTokenSet,
			styleBookTokenSets.get(newStyleBookTokenSet.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, StyleBookTokenSet> styleBookTokenSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(styleBookTokenSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStyleBookTokenSet.getPrimaryKey());

		Map<Serializable, StyleBookTokenSet> styleBookTokenSets =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, styleBookTokenSets.size());
		Assert.assertEquals(
			newStyleBookTokenSet,
			styleBookTokenSets.get(newStyleBookTokenSet.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			StyleBookTokenSetLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<StyleBookTokenSet>() {

				@Override
				public void performAction(StyleBookTokenSet styleBookTokenSet) {
					Assert.assertNotNull(styleBookTokenSet);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			StyleBookTokenSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"styleBookTokenSetId",
				newStyleBookTokenSet.getStyleBookTokenSetId()));

		List<StyleBookTokenSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		StyleBookTokenSet existingStyleBookTokenSet = result.get(0);

		Assert.assertEquals(existingStyleBookTokenSet, newStyleBookTokenSet);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			StyleBookTokenSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"styleBookTokenSetId", RandomTestUtil.nextLong()));

		List<StyleBookTokenSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			StyleBookTokenSet.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("styleBookTokenSetId"));

		Object newStyleBookTokenSetId =
			newStyleBookTokenSet.getStyleBookTokenSetId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"styleBookTokenSetId", new Object[] {newStyleBookTokenSetId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStyleBookTokenSetId = result.get(0);

		Assert.assertEquals(
			existingStyleBookTokenSetId, newStyleBookTokenSetId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			StyleBookTokenSet.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("styleBookTokenSetId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"styleBookTokenSetId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newStyleBookTokenSet.getPrimaryKey()));
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

		StyleBookTokenSet newStyleBookTokenSet = addStyleBookTokenSet();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			StyleBookTokenSet.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"styleBookTokenSetId",
				newStyleBookTokenSet.getStyleBookTokenSetId()));

		List<StyleBookTokenSet> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(StyleBookTokenSet styleBookTokenSet) {
		Assert.assertEquals(
			styleBookTokenSet.getUuid(),
			ReflectionTestUtil.invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(styleBookTokenSet.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			styleBookTokenSet.getFrontendTokenCategoryName(),
			ReflectionTestUtil.invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "frontendTokenCategoryName"));
		Assert.assertEquals(
			styleBookTokenSet.getFrontendTokenDefinitionId(),
			ReflectionTestUtil.invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "frontendTokenDefinitionId"));
		Assert.assertEquals(
			styleBookTokenSet.getName(),
			ReflectionTestUtil.invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "name"));
		Assert.assertEquals(
			Long.valueOf(styleBookTokenSet.getStyleBookEntryId()),
			ReflectionTestUtil.<Long>invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "styleBookEntryId"));

		Assert.assertEquals(
			styleBookTokenSet.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
		Assert.assertEquals(
			Long.valueOf(styleBookTokenSet.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				styleBookTokenSet, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected StyleBookTokenSet addStyleBookTokenSet() throws Exception {
		long pk = RandomTestUtil.nextLong();

		StyleBookTokenSet styleBookTokenSet = _persistence.create(pk);

		styleBookTokenSet.setMvccVersion(RandomTestUtil.nextLong());

		styleBookTokenSet.setCtCollectionId(RandomTestUtil.nextLong());

		styleBookTokenSet.setUuid(RandomTestUtil.randomString());

		styleBookTokenSet.setExternalReferenceCode(
			RandomTestUtil.randomString());

		styleBookTokenSet.setGroupId(RandomTestUtil.nextLong());

		styleBookTokenSet.setCompanyId(RandomTestUtil.nextLong());

		styleBookTokenSet.setUserId(RandomTestUtil.nextLong());

		styleBookTokenSet.setUserName(RandomTestUtil.randomString());

		styleBookTokenSet.setCreateDate(RandomTestUtil.nextDate());

		styleBookTokenSet.setModifiedDate(RandomTestUtil.nextDate());

		styleBookTokenSet.setDescription(RandomTestUtil.randomString());

		styleBookTokenSet.setFrontendTokenCategoryName(
			RandomTestUtil.randomString());

		styleBookTokenSet.setFrontendTokenDefinitionId(
			RandomTestUtil.randomString());

		styleBookTokenSet.setName(RandomTestUtil.randomString());

		styleBookTokenSet.setStyleBookEntryId(RandomTestUtil.nextLong());

		_styleBookTokenSets.add(_persistence.update(styleBookTokenSet));

		return styleBookTokenSet;
	}

	private List<StyleBookTokenSet> _styleBookTokenSets =
		new ArrayList<StyleBookTokenSet>();
	private StyleBookTokenSetPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:1820257750