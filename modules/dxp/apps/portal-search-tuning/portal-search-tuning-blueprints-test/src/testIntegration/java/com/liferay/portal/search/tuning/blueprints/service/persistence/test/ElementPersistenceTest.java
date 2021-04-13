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

package com.liferay.portal.search.tuning.blueprints.service.persistence.test;

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
import com.liferay.portal.search.tuning.blueprints.exception.NoSuchElementException;
import com.liferay.portal.search.tuning.blueprints.model.Element;
import com.liferay.portal.search.tuning.blueprints.service.ElementLocalServiceUtil;
import com.liferay.portal.search.tuning.blueprints.service.persistence.ElementPersistence;
import com.liferay.portal.search.tuning.blueprints.service.persistence.ElementUtil;
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
public class ElementPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.search.tuning.blueprints.service"));

	@Before
	public void setUp() {
		_persistence = ElementUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Element> iterator = _elements.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Element element = _persistence.create(pk);

		Assert.assertNotNull(element);

		Assert.assertEquals(element.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Element newElement = addElement();

		_persistence.remove(newElement);

		Element existingElement = _persistence.fetchByPrimaryKey(
			newElement.getPrimaryKey());

		Assert.assertNull(existingElement);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addElement();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Element newElement = _persistence.create(pk);

		newElement.setMvccVersion(RandomTestUtil.nextLong());

		newElement.setUuid(RandomTestUtil.randomString());

		newElement.setGroupId(RandomTestUtil.nextLong());

		newElement.setCompanyId(RandomTestUtil.nextLong());

		newElement.setUserId(RandomTestUtil.nextLong());

		newElement.setUserName(RandomTestUtil.randomString());

		newElement.setCreateDate(RandomTestUtil.nextDate());

		newElement.setModifiedDate(RandomTestUtil.nextDate());

		newElement.setStatus(RandomTestUtil.nextInt());

		newElement.setTitle(RandomTestUtil.randomString());

		newElement.setDescription(RandomTestUtil.randomString());

		newElement.setConfiguration(RandomTestUtil.randomString());

		newElement.setType(RandomTestUtil.nextInt());

		_elements.add(_persistence.update(newElement));

		Element existingElement = _persistence.findByPrimaryKey(
			newElement.getPrimaryKey());

		Assert.assertEquals(
			existingElement.getMvccVersion(), newElement.getMvccVersion());
		Assert.assertEquals(existingElement.getUuid(), newElement.getUuid());
		Assert.assertEquals(
			existingElement.getElementId(), newElement.getElementId());
		Assert.assertEquals(
			existingElement.getGroupId(), newElement.getGroupId());
		Assert.assertEquals(
			existingElement.getCompanyId(), newElement.getCompanyId());
		Assert.assertEquals(
			existingElement.getUserId(), newElement.getUserId());
		Assert.assertEquals(
			existingElement.getUserName(), newElement.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingElement.getCreateDate()),
			Time.getShortTimestamp(newElement.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingElement.getModifiedDate()),
			Time.getShortTimestamp(newElement.getModifiedDate()));
		Assert.assertEquals(
			existingElement.getStatus(), newElement.getStatus());
		Assert.assertEquals(existingElement.getTitle(), newElement.getTitle());
		Assert.assertEquals(
			existingElement.getDescription(), newElement.getDescription());
		Assert.assertEquals(
			existingElement.getConfiguration(), newElement.getConfiguration());
		Assert.assertEquals(existingElement.getType(), newElement.getType());
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
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_S(0L, 0);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_T(0L, 0);
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_T(0L, 0);
	}

	@Test
	public void testCountByG_S_T() throws Exception {
		_persistence.countByG_S_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_S_T(0L, 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Element newElement = addElement();

		Element existingElement = _persistence.findByPrimaryKey(
			newElement.getPrimaryKey());

		Assert.assertEquals(existingElement, newElement);
	}

	@Test(expected = NoSuchElementException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Element> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Element", "mvccVersion", true, "uuid", true, "elementId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "status", true,
			"title", true, "description", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Element newElement = addElement();

		Element existingElement = _persistence.fetchByPrimaryKey(
			newElement.getPrimaryKey());

		Assert.assertEquals(existingElement, newElement);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Element missingElement = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingElement);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Element newElement1 = addElement();
		Element newElement2 = addElement();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newElement1.getPrimaryKey());
		primaryKeys.add(newElement2.getPrimaryKey());

		Map<Serializable, Element> elements = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, elements.size());
		Assert.assertEquals(
			newElement1, elements.get(newElement1.getPrimaryKey()));
		Assert.assertEquals(
			newElement2, elements.get(newElement2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Element> elements = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(elements.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Element newElement = addElement();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newElement.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Element> elements = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, elements.size());
		Assert.assertEquals(
			newElement, elements.get(newElement.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Element> elements = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(elements.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Element newElement = addElement();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newElement.getPrimaryKey());

		Map<Serializable, Element> elements = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, elements.size());
		Assert.assertEquals(
			newElement, elements.get(newElement.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ElementLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Element>() {

				@Override
				public void performAction(Element element) {
					Assert.assertNotNull(element);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Element newElement = addElement();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Element.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("elementId", newElement.getElementId()));

		List<Element> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Element existingElement = result.get(0);

		Assert.assertEquals(existingElement, newElement);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Element.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("elementId", RandomTestUtil.nextLong()));

		List<Element> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Element newElement = addElement();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Element.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("elementId"));

		Object newElementId = newElement.getElementId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"elementId", new Object[] {newElementId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingElementId = result.get(0);

		Assert.assertEquals(existingElementId, newElementId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Element.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("elementId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"elementId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Element newElement = addElement();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newElement.getPrimaryKey()));
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

		Element newElement = addElement();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Element.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("elementId", newElement.getElementId()));

		List<Element> result = _persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(Element element) {
		Assert.assertEquals(
			element.getUuid(),
			ReflectionTestUtil.invoke(
				element, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(element.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				element, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected Element addElement() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Element element = _persistence.create(pk);

		element.setMvccVersion(RandomTestUtil.nextLong());

		element.setUuid(RandomTestUtil.randomString());

		element.setGroupId(RandomTestUtil.nextLong());

		element.setCompanyId(RandomTestUtil.nextLong());

		element.setUserId(RandomTestUtil.nextLong());

		element.setUserName(RandomTestUtil.randomString());

		element.setCreateDate(RandomTestUtil.nextDate());

		element.setModifiedDate(RandomTestUtil.nextDate());

		element.setStatus(RandomTestUtil.nextInt());

		element.setTitle(RandomTestUtil.randomString());

		element.setDescription(RandomTestUtil.randomString());

		element.setConfiguration(RandomTestUtil.randomString());

		element.setType(RandomTestUtil.nextInt());

		_elements.add(_persistence.update(element));

		return element;
	}

	private List<Element> _elements = new ArrayList<Element>();
	private ElementPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}