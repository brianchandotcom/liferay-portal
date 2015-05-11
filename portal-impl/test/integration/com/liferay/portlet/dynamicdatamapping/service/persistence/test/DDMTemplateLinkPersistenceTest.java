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

package com.liferay.portlet.dynamicdatamapping.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateLink;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateLinkPersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateLinkUtil;

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
public class DDMTemplateLinkPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDMTemplateLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDMTemplateLink> iterator = _ddmTemplateLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateLink ddmTemplateLink = _persistence.create(pk);

		Assert.assertNotNull(ddmTemplateLink);

		Assert.assertEquals(ddmTemplateLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		_persistence.remove(newDDMTemplateLink);

		DDMTemplateLink existingDDMTemplateLink = _persistence.fetchByPrimaryKey(newDDMTemplateLink.getPrimaryKey());

		Assert.assertNull(existingDDMTemplateLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMTemplateLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateLink newDDMTemplateLink = _persistence.create(pk);

		newDDMTemplateLink.setClassNameId(RandomTestUtil.nextLong());

		newDDMTemplateLink.setClassPK(RandomTestUtil.nextLong());

		newDDMTemplateLink.setTemplateId(RandomTestUtil.nextLong());

		_ddmTemplateLinks.add(_persistence.update(newDDMTemplateLink));

		DDMTemplateLink existingDDMTemplateLink = _persistence.findByPrimaryKey(newDDMTemplateLink.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateLink.getTemplateLinkId(),
			newDDMTemplateLink.getTemplateLinkId());
		Assert.assertEquals(existingDDMTemplateLink.getClassNameId(),
			newDDMTemplateLink.getClassNameId());
		Assert.assertEquals(existingDDMTemplateLink.getClassPK(),
			newDDMTemplateLink.getClassPK());
		Assert.assertEquals(existingDDMTemplateLink.getTemplateId(),
			newDDMTemplateLink.getTemplateId());
	}

	@Test
	public void testCountByClassNameId() throws Exception {
		_persistence.countByClassNameId(RandomTestUtil.nextLong());

		_persistence.countByClassNameId(0L);
	}

	@Test
	public void testCountByTemplateId() throws Exception {
		_persistence.countByTemplateId(RandomTestUtil.nextLong());

		_persistence.countByTemplateId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		DDMTemplateLink existingDDMTemplateLink = _persistence.findByPrimaryKey(newDDMTemplateLink.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateLink, newDDMTemplateLink);
	}

	@Test(expected = NoSuchTemplateLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DDMTemplateLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMTemplateLink",
			"templateLinkId", true, "classNameId", true, "classPK", true,
			"templateId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		DDMTemplateLink existingDDMTemplateLink = _persistence.fetchByPrimaryKey(newDDMTemplateLink.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateLink, newDDMTemplateLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateLink missingDDMTemplateLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMTemplateLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMTemplateLink newDDMTemplateLink1 = addDDMTemplateLink();
		DDMTemplateLink newDDMTemplateLink2 = addDDMTemplateLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMTemplateLink1.getPrimaryKey());
		primaryKeys.add(newDDMTemplateLink2.getPrimaryKey());

		Map<Serializable, DDMTemplateLink> ddmTemplateLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmTemplateLinks.size());
		Assert.assertEquals(newDDMTemplateLink1,
			ddmTemplateLinks.get(newDDMTemplateLink1.getPrimaryKey()));
		Assert.assertEquals(newDDMTemplateLink2,
			ddmTemplateLinks.get(newDDMTemplateLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMTemplateLink> ddmTemplateLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmTemplateLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMTemplateLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMTemplateLink> ddmTemplateLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmTemplateLinks.size());
		Assert.assertEquals(newDDMTemplateLink,
			ddmTemplateLinks.get(newDDMTemplateLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMTemplateLink> ddmTemplateLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmTemplateLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMTemplateLink.getPrimaryKey());

		Map<Serializable, DDMTemplateLink> ddmTemplateLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmTemplateLinks.size());
		Assert.assertEquals(newDDMTemplateLink,
			ddmTemplateLinks.get(newDDMTemplateLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMTemplateLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDMTemplateLink ddmTemplateLink = (DDMTemplateLink)object;

					Assert.assertNotNull(ddmTemplateLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateLinkId",
				newDDMTemplateLink.getTemplateLinkId()));

		List<DDMTemplateLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMTemplateLink existingDDMTemplateLink = result.get(0);

		Assert.assertEquals(existingDDMTemplateLink, newDDMTemplateLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateLinkId",
				RandomTestUtil.nextLong()));

		List<DDMTemplateLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"templateLinkId"));

		Object newTemplateLinkId = newDDMTemplateLink.getTemplateLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateLinkId",
				new Object[] { newTemplateLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTemplateLinkId = result.get(0);

		Assert.assertEquals(existingTemplateLinkId, newTemplateLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"templateLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateLinkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDMTemplateLink newDDMTemplateLink = addDDMTemplateLink();

		_persistence.clearCache();

		DDMTemplateLink existingDDMTemplateLink = _persistence.findByPrimaryKey(newDDMTemplateLink.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateLink.getClassNameId(),
			ReflectionTestUtil.invoke(existingDDMTemplateLink,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(existingDDMTemplateLink.getClassPK(),
			ReflectionTestUtil.invoke(existingDDMTemplateLink,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected DDMTemplateLink addDDMTemplateLink() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateLink ddmTemplateLink = _persistence.create(pk);

		ddmTemplateLink.setClassNameId(RandomTestUtil.nextLong());

		ddmTemplateLink.setClassPK(RandomTestUtil.nextLong());

		ddmTemplateLink.setTemplateId(RandomTestUtil.nextLong());

		_ddmTemplateLinks.add(_persistence.update(ddmTemplateLink));

		return ddmTemplateLink;
	}

	private List<DDMTemplateLink> _ddmTemplateLinks = new ArrayList<DDMTemplateLink>();
	private DDMTemplateLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}