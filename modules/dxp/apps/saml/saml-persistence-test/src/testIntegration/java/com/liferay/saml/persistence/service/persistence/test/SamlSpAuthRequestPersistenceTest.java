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

package com.liferay.saml.persistence.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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

import com.liferay.saml.persistence.exception.NoSuchSpAuthRequestException;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalServiceUtil;
import com.liferay.saml.persistence.service.persistence.SamlSpAuthRequestPersistence;
import com.liferay.saml.persistence.service.persistence.SamlSpAuthRequestUtil;

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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class SamlSpAuthRequestPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.saml.persistence.service"));

	@Before
	public void setUp() {
		_persistence = SamlSpAuthRequestUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SamlSpAuthRequest> iterator = _samlSpAuthRequests.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlSpAuthRequest samlSpAuthRequest = _persistence.create(pk);

		Assert.assertNotNull(samlSpAuthRequest);

		Assert.assertEquals(samlSpAuthRequest.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		_persistence.remove(newSamlSpAuthRequest);

		SamlSpAuthRequest existingSamlSpAuthRequest = _persistence.fetchByPrimaryKey(newSamlSpAuthRequest.getPrimaryKey());

		Assert.assertNull(existingSamlSpAuthRequest);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSamlSpAuthRequest();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlSpAuthRequest newSamlSpAuthRequest = _persistence.create(pk);

		newSamlSpAuthRequest.setCompanyId(RandomTestUtil.nextLong());

		newSamlSpAuthRequest.setCreateDate(RandomTestUtil.nextDate());

		newSamlSpAuthRequest.setSamlIdpEntityId(RandomTestUtil.randomString());

		newSamlSpAuthRequest.setSamlSpAuthRequestKey(RandomTestUtil.randomString());

		_samlSpAuthRequests.add(_persistence.update(newSamlSpAuthRequest));

		SamlSpAuthRequest existingSamlSpAuthRequest = _persistence.findByPrimaryKey(newSamlSpAuthRequest.getPrimaryKey());

		Assert.assertEquals(existingSamlSpAuthRequest.getSamlSpAuthnRequestId(),
			newSamlSpAuthRequest.getSamlSpAuthnRequestId());
		Assert.assertEquals(existingSamlSpAuthRequest.getCompanyId(),
			newSamlSpAuthRequest.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSamlSpAuthRequest.getCreateDate()),
			Time.getShortTimestamp(newSamlSpAuthRequest.getCreateDate()));
		Assert.assertEquals(existingSamlSpAuthRequest.getSamlIdpEntityId(),
			newSamlSpAuthRequest.getSamlIdpEntityId());
		Assert.assertEquals(existingSamlSpAuthRequest.getSamlSpAuthRequestKey(),
			newSamlSpAuthRequest.getSamlSpAuthRequestKey());
	}

	@Test
	public void testCountByCreateDate() throws Exception {
		_persistence.countByCreateDate(RandomTestUtil.nextDate());

		_persistence.countByCreateDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountBySIEI_SSARK() throws Exception {
		_persistence.countBySIEI_SSARK("", "");

		_persistence.countBySIEI_SSARK("null", "null");

		_persistence.countBySIEI_SSARK((String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		SamlSpAuthRequest existingSamlSpAuthRequest = _persistence.findByPrimaryKey(newSamlSpAuthRequest.getPrimaryKey());

		Assert.assertEquals(existingSamlSpAuthRequest, newSamlSpAuthRequest);
	}

	@Test(expected = NoSuchSpAuthRequestException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SamlSpAuthRequest> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SamlSpAuthRequest",
			"samlSpAuthnRequestId", true, "companyId", true, "createDate",
			true, "samlIdpEntityId", true, "samlSpAuthRequestKey", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		SamlSpAuthRequest existingSamlSpAuthRequest = _persistence.fetchByPrimaryKey(newSamlSpAuthRequest.getPrimaryKey());

		Assert.assertEquals(existingSamlSpAuthRequest, newSamlSpAuthRequest);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlSpAuthRequest missingSamlSpAuthRequest = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSamlSpAuthRequest);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest1 = addSamlSpAuthRequest();
		SamlSpAuthRequest newSamlSpAuthRequest2 = addSamlSpAuthRequest();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSamlSpAuthRequest1.getPrimaryKey());
		primaryKeys.add(newSamlSpAuthRequest2.getPrimaryKey());

		Map<Serializable, SamlSpAuthRequest> samlSpAuthRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, samlSpAuthRequests.size());
		Assert.assertEquals(newSamlSpAuthRequest1,
			samlSpAuthRequests.get(newSamlSpAuthRequest1.getPrimaryKey()));
		Assert.assertEquals(newSamlSpAuthRequest2,
			samlSpAuthRequests.get(newSamlSpAuthRequest2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SamlSpAuthRequest> samlSpAuthRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(samlSpAuthRequests.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSamlSpAuthRequest.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SamlSpAuthRequest> samlSpAuthRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, samlSpAuthRequests.size());
		Assert.assertEquals(newSamlSpAuthRequest,
			samlSpAuthRequests.get(newSamlSpAuthRequest.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SamlSpAuthRequest> samlSpAuthRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(samlSpAuthRequests.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSamlSpAuthRequest.getPrimaryKey());

		Map<Serializable, SamlSpAuthRequest> samlSpAuthRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, samlSpAuthRequests.size());
		Assert.assertEquals(newSamlSpAuthRequest,
			samlSpAuthRequests.get(newSamlSpAuthRequest.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SamlSpAuthRequestLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<SamlSpAuthRequest>() {
				@Override
				public void performAction(SamlSpAuthRequest samlSpAuthRequest) {
					Assert.assertNotNull(samlSpAuthRequest);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlSpAuthRequest.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("samlSpAuthnRequestId",
				newSamlSpAuthRequest.getSamlSpAuthnRequestId()));

		List<SamlSpAuthRequest> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SamlSpAuthRequest existingSamlSpAuthRequest = result.get(0);

		Assert.assertEquals(existingSamlSpAuthRequest, newSamlSpAuthRequest);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlSpAuthRequest.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("samlSpAuthnRequestId",
				RandomTestUtil.nextLong()));

		List<SamlSpAuthRequest> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlSpAuthRequest.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"samlSpAuthnRequestId"));

		Object newSamlSpAuthnRequestId = newSamlSpAuthRequest.getSamlSpAuthnRequestId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("samlSpAuthnRequestId",
				new Object[] { newSamlSpAuthnRequestId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSamlSpAuthnRequestId = result.get(0);

		Assert.assertEquals(existingSamlSpAuthnRequestId,
			newSamlSpAuthnRequestId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlSpAuthRequest.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"samlSpAuthnRequestId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("samlSpAuthnRequestId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SamlSpAuthRequest newSamlSpAuthRequest = addSamlSpAuthRequest();

		_persistence.clearCache();

		SamlSpAuthRequest existingSamlSpAuthRequest = _persistence.findByPrimaryKey(newSamlSpAuthRequest.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingSamlSpAuthRequest.getSamlIdpEntityId(),
				ReflectionTestUtil.invoke(existingSamlSpAuthRequest,
					"getOriginalSamlIdpEntityId", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingSamlSpAuthRequest.getSamlSpAuthRequestKey(),
				ReflectionTestUtil.invoke(existingSamlSpAuthRequest,
					"getOriginalSamlSpAuthRequestKey", new Class<?>[0])));
	}

	protected SamlSpAuthRequest addSamlSpAuthRequest()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlSpAuthRequest samlSpAuthRequest = _persistence.create(pk);

		samlSpAuthRequest.setCompanyId(RandomTestUtil.nextLong());

		samlSpAuthRequest.setCreateDate(RandomTestUtil.nextDate());

		samlSpAuthRequest.setSamlIdpEntityId(RandomTestUtil.randomString());

		samlSpAuthRequest.setSamlSpAuthRequestKey(RandomTestUtil.randomString());

		_samlSpAuthRequests.add(_persistence.update(samlSpAuthRequest));

		return samlSpAuthRequest;
	}

	private List<SamlSpAuthRequest> _samlSpAuthRequests = new ArrayList<SamlSpAuthRequest>();
	private SamlSpAuthRequestPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}