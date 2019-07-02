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

package com.liferay.portlet.documentlibrary.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryMetadataException;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataUtil;
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
public class DLFileEntryMetadataPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DLFileEntryMetadataUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DLFileEntryMetadata> iterator =
			_dlFileEntryMetadatas.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata dlFileEntryMetadata = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntryMetadata);

		Assert.assertEquals(dlFileEntryMetadata.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		_persistence.remove(newDLFileEntryMetadata);

		DLFileEntryMetadata existingDLFileEntryMetadata =
			_persistence.fetchByPrimaryKey(
				newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertNull(existingDLFileEntryMetadata);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntryMetadata();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata newDLFileEntryMetadata = _persistence.create(pk);

		newDLFileEntryMetadata.setMvccVersion(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setUuid(RandomTestUtil.randomString());

		newDLFileEntryMetadata.setCompanyId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setDDMStorageId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setDDMStructureId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setFileEntryId(RandomTestUtil.nextLong());

		newDLFileEntryMetadata.setFileVersionId(RandomTestUtil.nextLong());

		_dlFileEntryMetadatas.add(_persistence.update(newDLFileEntryMetadata));

		DLFileEntryMetadata existingDLFileEntryMetadata =
			_persistence.findByPrimaryKey(
				newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(
			existingDLFileEntryMetadata.getMvccVersion(),
			newDLFileEntryMetadata.getMvccVersion());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getUuid(),
			newDLFileEntryMetadata.getUuid());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getFileEntryMetadataId(),
			newDLFileEntryMetadata.getFileEntryMetadataId());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getCompanyId(),
			newDLFileEntryMetadata.getCompanyId());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getDDMStorageId(),
			newDLFileEntryMetadata.getDDMStorageId());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getDDMStructureId(),
			newDLFileEntryMetadata.getDDMStructureId());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getFileEntryId(),
			newDLFileEntryMetadata.getFileEntryId());
		Assert.assertEquals(
			existingDLFileEntryMetadata.getFileVersionId(),
			newDLFileEntryMetadata.getFileVersionId());
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
	public void testCountByFileEntryId() throws Exception {
		_persistence.countByFileEntryId(RandomTestUtil.nextLong());

		_persistence.countByFileEntryId(0L);
	}

	@Test
	public void testCountByFileVersionId() throws Exception {
		_persistence.countByFileVersionId(RandomTestUtil.nextLong());

		_persistence.countByFileVersionId(0L);
	}

	@Test
	public void testCountByD_F() throws Exception {
		_persistence.countByD_F(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByD_F(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DLFileEntryMetadata existingDLFileEntryMetadata =
			_persistence.findByPrimaryKey(
				newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(
			existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	@Test(expected = NoSuchFileEntryMetadataException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DLFileEntryMetadata> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DLFileEntryMetadata", "mvccVersion", true, "uuid", true,
			"fileEntryMetadataId", true, "companyId", true, "DDMStorageId",
			true, "DDMStructureId", true, "fileEntryId", true, "fileVersionId",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DLFileEntryMetadata existingDLFileEntryMetadata =
			_persistence.fetchByPrimaryKey(
				newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(
			existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata missingDLFileEntryMetadata =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntryMetadata);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DLFileEntryMetadata newDLFileEntryMetadata1 = addDLFileEntryMetadata();
		DLFileEntryMetadata newDLFileEntryMetadata2 = addDLFileEntryMetadata();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryMetadata1.getPrimaryKey());
		primaryKeys.add(newDLFileEntryMetadata2.getPrimaryKey());

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, dlFileEntryMetadatas.size());
		Assert.assertEquals(
			newDLFileEntryMetadata1,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata1.getPrimaryKey()));
		Assert.assertEquals(
			newDLFileEntryMetadata2,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryMetadatas.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryMetadata.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryMetadatas.size());
		Assert.assertEquals(
			newDLFileEntryMetadata,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryMetadatas.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryMetadata.getPrimaryKey());

		Map<Serializable, DLFileEntryMetadata> dlFileEntryMetadatas =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryMetadatas.size());
		Assert.assertEquals(
			newDLFileEntryMetadata,
			dlFileEntryMetadatas.get(newDLFileEntryMetadata.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryMetadataLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<DLFileEntryMetadata>() {

				@Override
				public void performAction(
					DLFileEntryMetadata dlFileEntryMetadata) {

					Assert.assertNotNull(dlFileEntryMetadata);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFileEntryMetadata.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fileEntryMetadataId",
				newDLFileEntryMetadata.getFileEntryMetadataId()));

		List<DLFileEntryMetadata> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntryMetadata existingDLFileEntryMetadata = result.get(0);

		Assert.assertEquals(
			existingDLFileEntryMetadata, newDLFileEntryMetadata);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFileEntryMetadata.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fileEntryMetadataId", RandomTestUtil.nextLong()));

		List<DLFileEntryMetadata> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFileEntryMetadata.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fileEntryMetadataId"));

		Object newFileEntryMetadataId =
			newDLFileEntryMetadata.getFileEntryMetadataId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fileEntryMetadataId", new Object[] {newFileEntryMetadataId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryMetadataId = result.get(0);

		Assert.assertEquals(
			existingFileEntryMetadataId, newFileEntryMetadataId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DLFileEntryMetadata.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fileEntryMetadataId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fileEntryMetadataId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DLFileEntryMetadata newDLFileEntryMetadata = addDLFileEntryMetadata();

		_persistence.clearCache();

		DLFileEntryMetadata existingDLFileEntryMetadata =
			_persistence.findByPrimaryKey(
				newDLFileEntryMetadata.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingDLFileEntryMetadata.getDDMStructureId()),
			ReflectionTestUtil.<Long>invoke(
				existingDLFileEntryMetadata, "getOriginalDDMStructureId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingDLFileEntryMetadata.getFileVersionId()),
			ReflectionTestUtil.<Long>invoke(
				existingDLFileEntryMetadata, "getOriginalFileVersionId",
				new Class<?>[0]));
	}

	protected DLFileEntryMetadata addDLFileEntryMetadata() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryMetadata dlFileEntryMetadata = _persistence.create(pk);

		dlFileEntryMetadata.setMvccVersion(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setUuid(RandomTestUtil.randomString());

		dlFileEntryMetadata.setCompanyId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setDDMStorageId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setDDMStructureId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setFileEntryId(RandomTestUtil.nextLong());

		dlFileEntryMetadata.setFileVersionId(RandomTestUtil.nextLong());

		_dlFileEntryMetadatas.add(_persistence.update(dlFileEntryMetadata));

		return dlFileEntryMetadata;
	}

	private List<DLFileEntryMetadata> _dlFileEntryMetadatas =
		new ArrayList<DLFileEntryMetadata>();
	private DLFileEntryMetadataPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}