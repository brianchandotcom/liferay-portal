/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.report.exception.NoSuchImportReportEntryException;
import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.exportimport.report.service.ImportReportEntryLocalServiceUtil;
import com.liferay.exportimport.report.service.persistence.ImportReportEntryPersistence;
import com.liferay.exportimport.report.service.persistence.ImportReportEntryUtil;
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
public class ImportReportEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.exportimport.report.service"));

	@Before
	public void setUp() {
		_persistence = ImportReportEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ImportReportEntry> iterator = _importReportEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ImportReportEntry importReportEntry = _persistence.create(pk);

		Assert.assertNotNull(importReportEntry);

		Assert.assertEquals(importReportEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ImportReportEntry newImportReportEntry = addImportReportEntry();

		_persistence.remove(newImportReportEntry);

		ImportReportEntry existingImportReportEntry =
			_persistence.fetchByPrimaryKey(
				newImportReportEntry.getPrimaryKey());

		Assert.assertNull(existingImportReportEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addImportReportEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ImportReportEntry newImportReportEntry = _persistence.create(pk);

		newImportReportEntry.setMvccVersion(RandomTestUtil.nextLong());

		newImportReportEntry.setCompanyId(RandomTestUtil.nextLong());

		newImportReportEntry.setCreateDate(RandomTestUtil.nextDate());

		newImportReportEntry.setModifiedDate(RandomTestUtil.nextDate());

		newImportReportEntry.setClassNameId(RandomTestUtil.nextLong());

		newImportReportEntry.setClassPK(RandomTestUtil.nextLong());

		newImportReportEntry.setEntityClassNameId(RandomTestUtil.nextLong());

		newImportReportEntry.setEntityExternalReferenceCode(
			RandomTestUtil.randomString());

		newImportReportEntry.setError(RandomTestUtil.randomString());

		newImportReportEntry.setResolved(RandomTestUtil.randomBoolean());

		newImportReportEntry.setType(RandomTestUtil.nextInt());

		_importReportEntries.add(_persistence.update(newImportReportEntry));

		ImportReportEntry existingImportReportEntry =
			_persistence.findByPrimaryKey(newImportReportEntry.getPrimaryKey());

		Assert.assertEquals(
			existingImportReportEntry.getMvccVersion(),
			newImportReportEntry.getMvccVersion());
		Assert.assertEquals(
			existingImportReportEntry.getImportReportEntryId(),
			newImportReportEntry.getImportReportEntryId());
		Assert.assertEquals(
			existingImportReportEntry.getCompanyId(),
			newImportReportEntry.getCompanyId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingImportReportEntry.getCreateDate()),
			Time.getShortTimestamp(newImportReportEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingImportReportEntry.getModifiedDate()),
			Time.getShortTimestamp(newImportReportEntry.getModifiedDate()));
		Assert.assertEquals(
			existingImportReportEntry.getClassNameId(),
			newImportReportEntry.getClassNameId());
		Assert.assertEquals(
			existingImportReportEntry.getClassPK(),
			newImportReportEntry.getClassPK());
		Assert.assertEquals(
			existingImportReportEntry.getEntityClassNameId(),
			newImportReportEntry.getEntityClassNameId());
		Assert.assertEquals(
			existingImportReportEntry.getEntityExternalReferenceCode(),
			newImportReportEntry.getEntityExternalReferenceCode());
		Assert.assertEquals(
			existingImportReportEntry.getError(),
			newImportReportEntry.getError());
		Assert.assertEquals(
			existingImportReportEntry.isResolved(),
			newImportReportEntry.isResolved());
		Assert.assertEquals(
			existingImportReportEntry.getType(),
			newImportReportEntry.getType());
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ImportReportEntry newImportReportEntry = addImportReportEntry();

		ImportReportEntry existingImportReportEntry =
			_persistence.findByPrimaryKey(newImportReportEntry.getPrimaryKey());

		Assert.assertEquals(existingImportReportEntry, newImportReportEntry);
	}

	@Test(expected = NoSuchImportReportEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ImportReportEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ImportReportEntry", "mvccVersion", true, "importReportEntryId",
			true, "companyId", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "entityClassNameId", true,
			"entityExternalReferenceCode", true, "resolved", true, "type",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ImportReportEntry newImportReportEntry = addImportReportEntry();

		ImportReportEntry existingImportReportEntry =
			_persistence.fetchByPrimaryKey(
				newImportReportEntry.getPrimaryKey());

		Assert.assertEquals(existingImportReportEntry, newImportReportEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ImportReportEntry missingImportReportEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingImportReportEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ImportReportEntry newImportReportEntry1 = addImportReportEntry();
		ImportReportEntry newImportReportEntry2 = addImportReportEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newImportReportEntry1.getPrimaryKey());
		primaryKeys.add(newImportReportEntry2.getPrimaryKey());

		Map<Serializable, ImportReportEntry> importReportEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, importReportEntries.size());
		Assert.assertEquals(
			newImportReportEntry1,
			importReportEntries.get(newImportReportEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newImportReportEntry2,
			importReportEntries.get(newImportReportEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ImportReportEntry> importReportEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(importReportEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ImportReportEntry newImportReportEntry = addImportReportEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newImportReportEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ImportReportEntry> importReportEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, importReportEntries.size());
		Assert.assertEquals(
			newImportReportEntry,
			importReportEntries.get(newImportReportEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ImportReportEntry> importReportEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(importReportEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ImportReportEntry newImportReportEntry = addImportReportEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newImportReportEntry.getPrimaryKey());

		Map<Serializable, ImportReportEntry> importReportEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, importReportEntries.size());
		Assert.assertEquals(
			newImportReportEntry,
			importReportEntries.get(newImportReportEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ImportReportEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ImportReportEntry>() {

				@Override
				public void performAction(ImportReportEntry importReportEntry) {
					Assert.assertNotNull(importReportEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ImportReportEntry newImportReportEntry = addImportReportEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ImportReportEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"importReportEntryId",
				newImportReportEntry.getImportReportEntryId()));

		List<ImportReportEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ImportReportEntry existingImportReportEntry = result.get(0);

		Assert.assertEquals(existingImportReportEntry, newImportReportEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ImportReportEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"importReportEntryId", RandomTestUtil.nextLong()));

		List<ImportReportEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ImportReportEntry newImportReportEntry = addImportReportEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ImportReportEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("importReportEntryId"));

		Object newImportReportEntryId =
			newImportReportEntry.getImportReportEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"importReportEntryId", new Object[] {newImportReportEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingImportReportEntryId = result.get(0);

		Assert.assertEquals(
			existingImportReportEntryId, newImportReportEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ImportReportEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("importReportEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"importReportEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ImportReportEntry addImportReportEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ImportReportEntry importReportEntry = _persistence.create(pk);

		importReportEntry.setMvccVersion(RandomTestUtil.nextLong());

		importReportEntry.setCompanyId(RandomTestUtil.nextLong());

		importReportEntry.setCreateDate(RandomTestUtil.nextDate());

		importReportEntry.setModifiedDate(RandomTestUtil.nextDate());

		importReportEntry.setClassNameId(RandomTestUtil.nextLong());

		importReportEntry.setClassPK(RandomTestUtil.nextLong());

		importReportEntry.setEntityClassNameId(RandomTestUtil.nextLong());

		importReportEntry.setEntityExternalReferenceCode(
			RandomTestUtil.randomString());

		importReportEntry.setError(RandomTestUtil.randomString());

		importReportEntry.setResolved(RandomTestUtil.randomBoolean());

		importReportEntry.setType(RandomTestUtil.nextInt());

		_importReportEntries.add(_persistence.update(importReportEntry));

		return importReportEntry;
	}

	private List<ImportReportEntry> _importReportEntries =
		new ArrayList<ImportReportEntry>();
	private ImportReportEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}