/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import com.liferay.search.experiences.exception.NoSuchSXPElementException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalServiceUtil;
import com.liferay.search.experiences.service.persistence.SXPElementPersistence;
import com.liferay.search.experiences.service.persistence.SXPElementUtil;

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
public class SXPElementPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.search.experiences.service"));

	@Before
	public void setUp() {
		_persistence = SXPElementUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SXPElement> iterator = _sxpElements.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SXPElement sxpElement = _persistence.create(pk);

		Assert.assertNotNull(sxpElement);

		Assert.assertEquals(sxpElement.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SXPElement newSXPElement = addSXPElement();

		_persistence.remove(newSXPElement);

		SXPElement existingSXPElement = _persistence.fetchByPrimaryKey(
			newSXPElement.getPrimaryKey());

		Assert.assertNull(existingSXPElement);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSXPElement();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SXPElement newSXPElement = _persistence.create(pk);

		newSXPElement.setMvccVersion(RandomTestUtil.nextLong());

		newSXPElement.setUuid(RandomTestUtil.randomString());

		newSXPElement.setCompanyId(RandomTestUtil.nextLong());

		newSXPElement.setUserId(RandomTestUtil.nextLong());

		newSXPElement.setUserName(RandomTestUtil.randomString());

		newSXPElement.setCreateDate(RandomTestUtil.nextDate());

		newSXPElement.setModifiedDate(RandomTestUtil.nextDate());

		newSXPElement.setDescription(RandomTestUtil.randomString());

		newSXPElement.setElementDefinitionJSON(RandomTestUtil.randomString());

		newSXPElement.setHidden(RandomTestUtil.randomBoolean());

		newSXPElement.setReadOnly(RandomTestUtil.randomBoolean());

		newSXPElement.setSchemaVersion(RandomTestUtil.randomString());

		newSXPElement.setTitle(RandomTestUtil.randomString());

		newSXPElement.setType(RandomTestUtil.nextInt());

		newSXPElement.setStatus(RandomTestUtil.nextInt());

		_sxpElements.add(_persistence.update(newSXPElement));

		SXPElement existingSXPElement = _persistence.findByPrimaryKey(
			newSXPElement.getPrimaryKey());

		Assert.assertEquals(
			existingSXPElement.getMvccVersion(),
			newSXPElement.getMvccVersion());
		Assert.assertEquals(
			existingSXPElement.getUuid(), newSXPElement.getUuid());
		Assert.assertEquals(
			existingSXPElement.getSXPElementId(),
			newSXPElement.getSXPElementId());
		Assert.assertEquals(
			existingSXPElement.getCompanyId(), newSXPElement.getCompanyId());
		Assert.assertEquals(
			existingSXPElement.getUserId(), newSXPElement.getUserId());
		Assert.assertEquals(
			existingSXPElement.getUserName(), newSXPElement.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSXPElement.getCreateDate()),
			Time.getShortTimestamp(newSXPElement.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSXPElement.getModifiedDate()),
			Time.getShortTimestamp(newSXPElement.getModifiedDate()));
		Assert.assertEquals(
			existingSXPElement.getDescription(),
			newSXPElement.getDescription());
		Assert.assertEquals(
			existingSXPElement.getElementDefinitionJSON(),
			newSXPElement.getElementDefinitionJSON());
		Assert.assertEquals(
			existingSXPElement.isHidden(), newSXPElement.isHidden());
		Assert.assertEquals(
			existingSXPElement.isReadOnly(), newSXPElement.isReadOnly());
		Assert.assertEquals(
			existingSXPElement.getSchemaVersion(),
			newSXPElement.getSchemaVersion());
		Assert.assertEquals(
			existingSXPElement.getTitle(), newSXPElement.getTitle());
		Assert.assertEquals(
			existingSXPElement.getType(), newSXPElement.getType());
		Assert.assertEquals(
			existingSXPElement.getStatus(), newSXPElement.getStatus());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_R() throws Exception {
		_persistence.countByC_R(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByC_R(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_T(0L, 0);
	}

	@Test
	public void testCountByC_T_S() throws Exception {
		_persistence.countByC_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByC_T_S(0L, 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SXPElement newSXPElement = addSXPElement();

		SXPElement existingSXPElement = _persistence.findByPrimaryKey(
			newSXPElement.getPrimaryKey());

		Assert.assertEquals(existingSXPElement, newSXPElement);
	}

	@Test(expected = NoSuchSXPElementException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SXPElement> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SXPElement", "mvccVersion", true, "uuid", true, "sxpElementId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "description", true,
			"hidden", true, "readOnly", true, "schemaVersion", true, "title",
			true, "type", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SXPElement newSXPElement = addSXPElement();

		SXPElement existingSXPElement = _persistence.fetchByPrimaryKey(
			newSXPElement.getPrimaryKey());

		Assert.assertEquals(existingSXPElement, newSXPElement);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SXPElement missingSXPElement = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSXPElement);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SXPElement newSXPElement1 = addSXPElement();
		SXPElement newSXPElement2 = addSXPElement();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSXPElement1.getPrimaryKey());
		primaryKeys.add(newSXPElement2.getPrimaryKey());

		Map<Serializable, SXPElement> sxpElements =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, sxpElements.size());
		Assert.assertEquals(
			newSXPElement1, sxpElements.get(newSXPElement1.getPrimaryKey()));
		Assert.assertEquals(
			newSXPElement2, sxpElements.get(newSXPElement2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SXPElement> sxpElements =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sxpElements.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SXPElement newSXPElement = addSXPElement();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSXPElement.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SXPElement> sxpElements =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sxpElements.size());
		Assert.assertEquals(
			newSXPElement, sxpElements.get(newSXPElement.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SXPElement> sxpElements =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sxpElements.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SXPElement newSXPElement = addSXPElement();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSXPElement.getPrimaryKey());

		Map<Serializable, SXPElement> sxpElements =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sxpElements.size());
		Assert.assertEquals(
			newSXPElement, sxpElements.get(newSXPElement.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SXPElementLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SXPElement>() {

				@Override
				public void performAction(SXPElement sxpElement) {
					Assert.assertNotNull(sxpElement);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SXPElement newSXPElement = addSXPElement();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SXPElement.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sxpElementId", newSXPElement.getSXPElementId()));

		List<SXPElement> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SXPElement existingSXPElement = result.get(0);

		Assert.assertEquals(existingSXPElement, newSXPElement);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SXPElement.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sxpElementId", RandomTestUtil.nextLong()));

		List<SXPElement> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SXPElement newSXPElement = addSXPElement();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SXPElement.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("sxpElementId"));

		Object newSXPElementId = newSXPElement.getSXPElementId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"sxpElementId", new Object[] {newSXPElementId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSXPElementId = result.get(0);

		Assert.assertEquals(existingSXPElementId, newSXPElementId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SXPElement.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("sxpElementId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"sxpElementId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SXPElement addSXPElement() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SXPElement sxpElement = _persistence.create(pk);

		sxpElement.setMvccVersion(RandomTestUtil.nextLong());

		sxpElement.setUuid(RandomTestUtil.randomString());

		sxpElement.setCompanyId(RandomTestUtil.nextLong());

		sxpElement.setUserId(RandomTestUtil.nextLong());

		sxpElement.setUserName(RandomTestUtil.randomString());

		sxpElement.setCreateDate(RandomTestUtil.nextDate());

		sxpElement.setModifiedDate(RandomTestUtil.nextDate());

		sxpElement.setDescription(RandomTestUtil.randomString());

		sxpElement.setElementDefinitionJSON(RandomTestUtil.randomString());

		sxpElement.setHidden(RandomTestUtil.randomBoolean());

		sxpElement.setReadOnly(RandomTestUtil.randomBoolean());

		sxpElement.setSchemaVersion(RandomTestUtil.randomString());

		sxpElement.setTitle(RandomTestUtil.randomString());

		sxpElement.setType(RandomTestUtil.nextInt());

		sxpElement.setStatus(RandomTestUtil.nextInt());

		_sxpElements.add(_persistence.update(sxpElement));

		return sxpElement;
	}

	private List<SXPElement> _sxpElements = new ArrayList<SXPElement>();
	private SXPElementPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}