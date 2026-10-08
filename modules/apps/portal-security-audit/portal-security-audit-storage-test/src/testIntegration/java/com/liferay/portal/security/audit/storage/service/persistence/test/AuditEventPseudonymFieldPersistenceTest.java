/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service.persistence.test;

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
import com.liferay.portal.security.audit.storage.exception.NoSuchEventPseudonymFieldException;
import com.liferay.portal.security.audit.storage.model.AuditEventPseudonymField;
import com.liferay.portal.security.audit.storage.service.AuditEventPseudonymFieldLocalServiceUtil;
import com.liferay.portal.security.audit.storage.service.persistence.AuditEventPseudonymFieldPersistence;
import com.liferay.portal.security.audit.storage.service.persistence.AuditEventPseudonymFieldUtil;
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
public class AuditEventPseudonymFieldPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.security.audit.storage.service"));

	@Before
	public void setUp() {
		_persistence = AuditEventPseudonymFieldUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AuditEventPseudonymField> iterator =
			_auditEventPseudonymFields.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AuditEventPseudonymField auditEventPseudonymField = _persistence.create(
			pk);

		Assert.assertNotNull(auditEventPseudonymField);

		Assert.assertEquals(auditEventPseudonymField.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		_persistence.remove(newAuditEventPseudonymField);

		AuditEventPseudonymField existingAuditEventPseudonymField =
			_persistence.fetchByPrimaryKey(
				newAuditEventPseudonymField.getPrimaryKey());

		Assert.assertNull(existingAuditEventPseudonymField);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAuditEventPseudonymField();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		newAuditEventPseudonymField.setCompanyId(RandomTestUtil.nextLong());

		newAuditEventPseudonymField.setCreateDate(RandomTestUtil.nextDate());

		newAuditEventPseudonymField.setContextName(
			RandomTestUtil.randomString());

		newAuditEventPseudonymField.setName(RandomTestUtil.randomString());

		newAuditEventPseudonymField.setValue(RandomTestUtil.randomString());

		newAuditEventPseudonymField.setValueHash(RandomTestUtil.randomString());

		newAuditEventPseudonymField = _persistence.update(
			newAuditEventPseudonymField);

		_auditEventPseudonymFields.add(newAuditEventPseudonymField);

		AuditEventPseudonymField existingAuditEventPseudonymField =
			_persistence.findByPrimaryKey(
				newAuditEventPseudonymField.getPrimaryKey());

		Assert.assertEquals(
			existingAuditEventPseudonymField.getAuditEventPseudonymFieldId(),
			newAuditEventPseudonymField.getAuditEventPseudonymFieldId());
		Assert.assertEquals(
			existingAuditEventPseudonymField.getCompanyId(),
			newAuditEventPseudonymField.getCompanyId());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAuditEventPseudonymField.getCreateDate()),
			Time.getShortTimestamp(
				newAuditEventPseudonymField.getCreateDate()));
		Assert.assertEquals(
			existingAuditEventPseudonymField.getContextName(),
			newAuditEventPseudonymField.getContextName());
		Assert.assertEquals(
			existingAuditEventPseudonymField.getName(),
			newAuditEventPseudonymField.getName());
		Assert.assertEquals(
			existingAuditEventPseudonymField.getValue(),
			newAuditEventPseudonymField.getValue());
		Assert.assertEquals(
			existingAuditEventPseudonymField.getValueHash(),
			newAuditEventPseudonymField.getValueHash());
	}

	@Test
	public void testCountByC_CN_N_VH() throws Exception {
		_persistence.countByC_CN_N_VH(RandomTestUtil.nextLong(), "", "", "");

		_persistence.countByC_CN_N_VH(0L, "null", "null", "null");

		_persistence.countByC_CN_N_VH(
			0L, (String)null, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		AuditEventPseudonymField existingAuditEventPseudonymField =
			_persistence.findByPrimaryKey(
				newAuditEventPseudonymField.getPrimaryKey());

		Assert.assertEquals(
			existingAuditEventPseudonymField, newAuditEventPseudonymField);
	}

	@Test(expected = NoSuchEventPseudonymFieldException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AuditEventPseudonymField>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"Audit_AuditEventPseudonymField", "auditEventPseudonymFieldId",
			true, "companyId", true, "createDate", true, "contextName", true,
			"name", true, "value", true, "valueHash", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		AuditEventPseudonymField existingAuditEventPseudonymField =
			_persistence.fetchByPrimaryKey(
				newAuditEventPseudonymField.getPrimaryKey());

		Assert.assertEquals(
			existingAuditEventPseudonymField, newAuditEventPseudonymField);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AuditEventPseudonymField missingAuditEventPseudonymField =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAuditEventPseudonymField);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AuditEventPseudonymField newAuditEventPseudonymField1 =
			addAuditEventPseudonymField();
		AuditEventPseudonymField newAuditEventPseudonymField2 =
			addAuditEventPseudonymField();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAuditEventPseudonymField1.getPrimaryKey());
		primaryKeys.add(newAuditEventPseudonymField2.getPrimaryKey());

		Map<Serializable, AuditEventPseudonymField> auditEventPseudonymFields =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, auditEventPseudonymFields.size());
		Assert.assertEquals(
			newAuditEventPseudonymField1,
			auditEventPseudonymFields.get(
				newAuditEventPseudonymField1.getPrimaryKey()));
		Assert.assertEquals(
			newAuditEventPseudonymField2,
			auditEventPseudonymFields.get(
				newAuditEventPseudonymField2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AuditEventPseudonymField> auditEventPseudonymFields =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(auditEventPseudonymFields.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAuditEventPseudonymField.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AuditEventPseudonymField> auditEventPseudonymFields =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, auditEventPseudonymFields.size());
		Assert.assertEquals(
			newAuditEventPseudonymField,
			auditEventPseudonymFields.get(
				newAuditEventPseudonymField.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AuditEventPseudonymField> auditEventPseudonymFields =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(auditEventPseudonymFields.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAuditEventPseudonymField.getPrimaryKey());

		Map<Serializable, AuditEventPseudonymField> auditEventPseudonymFields =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, auditEventPseudonymFields.size());
		Assert.assertEquals(
			newAuditEventPseudonymField,
			auditEventPseudonymFields.get(
				newAuditEventPseudonymField.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AuditEventPseudonymFieldLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AuditEventPseudonymField>() {

				@Override
				public void performAction(
					AuditEventPseudonymField auditEventPseudonymField) {

					Assert.assertNotNull(auditEventPseudonymField);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AuditEventPseudonymField.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"auditEventPseudonymFieldId",
				newAuditEventPseudonymField.getAuditEventPseudonymFieldId()));

		List<AuditEventPseudonymField> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AuditEventPseudonymField existingAuditEventPseudonymField = result.get(
			0);

		Assert.assertEquals(
			existingAuditEventPseudonymField, newAuditEventPseudonymField);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AuditEventPseudonymField.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"auditEventPseudonymFieldId", RandomTestUtil.nextLong()));

		List<AuditEventPseudonymField> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AuditEventPseudonymField.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("auditEventPseudonymFieldId"));

		Object newAuditEventPseudonymFieldId =
			newAuditEventPseudonymField.getAuditEventPseudonymFieldId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"auditEventPseudonymFieldId",
				new Object[] {newAuditEventPseudonymFieldId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAuditEventPseudonymFieldId = result.get(0);

		Assert.assertEquals(
			existingAuditEventPseudonymFieldId, newAuditEventPseudonymFieldId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AuditEventPseudonymField.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("auditEventPseudonymFieldId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"auditEventPseudonymFieldId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newAuditEventPseudonymField.getPrimaryKey()));
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

		AuditEventPseudonymField newAuditEventPseudonymField =
			addAuditEventPseudonymField();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AuditEventPseudonymField.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"auditEventPseudonymFieldId",
				newAuditEventPseudonymField.getAuditEventPseudonymFieldId()));

		List<AuditEventPseudonymField> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		AuditEventPseudonymField auditEventPseudonymField) {

		Assert.assertEquals(
			Long.valueOf(auditEventPseudonymField.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				auditEventPseudonymField, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			auditEventPseudonymField.getContextName(),
			ReflectionTestUtil.invoke(
				auditEventPseudonymField, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "contextName"));
		Assert.assertEquals(
			auditEventPseudonymField.getName(),
			ReflectionTestUtil.invoke(
				auditEventPseudonymField, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "name"));
		Assert.assertEquals(
			auditEventPseudonymField.getValueHash(),
			ReflectionTestUtil.invoke(
				auditEventPseudonymField, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "valueHash"));
	}

	protected AuditEventPseudonymField addAuditEventPseudonymField()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AuditEventPseudonymField auditEventPseudonymField = _persistence.create(
			pk);

		auditEventPseudonymField.setCompanyId(RandomTestUtil.nextLong());

		auditEventPseudonymField.setCreateDate(RandomTestUtil.nextDate());

		auditEventPseudonymField.setContextName(RandomTestUtil.randomString());

		auditEventPseudonymField.setName(RandomTestUtil.randomString());

		auditEventPseudonymField.setValue(RandomTestUtil.randomString());

		auditEventPseudonymField.setValueHash(RandomTestUtil.randomString());

		_auditEventPseudonymFields.add(
			_persistence.update(auditEventPseudonymField));

		return auditEventPseudonymField;
	}

	private List<AuditEventPseudonymField> _auditEventPseudonymFields =
		new ArrayList<AuditEventPseudonymField>();
	private AuditEventPseudonymFieldPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:815846391