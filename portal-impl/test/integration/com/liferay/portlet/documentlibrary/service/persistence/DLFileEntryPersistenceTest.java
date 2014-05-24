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

package com.liferay.portlet.documentlibrary.service.persistence;

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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class DLFileEntryPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DLFileEntry> modelListener : _modelListeners) {
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

		for (ModelListener<DLFileEntry> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntry);

		Assert.assertEquals(dlFileEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.remove(newDLFileEntry);

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertNull(existingDLFileEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntry newDLFileEntry = _persistence.create(pk);

		newDLFileEntry.setUuid(RandomTestUtil.randomString());

		newDLFileEntry.setGroupId(RandomTestUtil.nextLong());

		newDLFileEntry.setCompanyId(RandomTestUtil.nextLong());

		newDLFileEntry.setUserId(RandomTestUtil.nextLong());

		newDLFileEntry.setUserName(RandomTestUtil.randomString());

		newDLFileEntry.setCreateDate(RandomTestUtil.nextDate());

		newDLFileEntry.setModifiedDate(RandomTestUtil.nextDate());

		newDLFileEntry.setClassNameId(RandomTestUtil.nextLong());

		newDLFileEntry.setClassPK(RandomTestUtil.nextLong());

		newDLFileEntry.setRepositoryId(RandomTestUtil.nextLong());

		newDLFileEntry.setFolderId(RandomTestUtil.nextLong());

		newDLFileEntry.setTreePath(RandomTestUtil.randomString());

		newDLFileEntry.setName(RandomTestUtil.randomString());

		newDLFileEntry.setExtension(RandomTestUtil.randomString());

		newDLFileEntry.setMimeType(RandomTestUtil.randomString());

		newDLFileEntry.setTitle(RandomTestUtil.randomString());

		newDLFileEntry.setDescription(RandomTestUtil.randomString());

		newDLFileEntry.setExtraSettings(RandomTestUtil.randomString());

		newDLFileEntry.setFileEntryTypeId(RandomTestUtil.nextLong());

		newDLFileEntry.setVersion(RandomTestUtil.randomString());

		newDLFileEntry.setSize(RandomTestUtil.nextLong());

		newDLFileEntry.setReadCount(RandomTestUtil.nextInt());

		newDLFileEntry.setSmallImageId(RandomTestUtil.nextLong());

		newDLFileEntry.setLargeImageId(RandomTestUtil.nextLong());

		newDLFileEntry.setCustom1ImageId(RandomTestUtil.nextLong());

		newDLFileEntry.setCustom2ImageId(RandomTestUtil.nextLong());

		newDLFileEntry.setManualCheckInRequired(RandomTestUtil.randomBoolean());

		_persistence.update(newDLFileEntry);

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntry.getUuid(),
			newDLFileEntry.getUuid());
		Assert.assertEquals(existingDLFileEntry.getFileEntryId(),
			newDLFileEntry.getFileEntryId());
		Assert.assertEquals(existingDLFileEntry.getGroupId(),
			newDLFileEntry.getGroupId());
		Assert.assertEquals(existingDLFileEntry.getCompanyId(),
			newDLFileEntry.getCompanyId());
		Assert.assertEquals(existingDLFileEntry.getUserId(),
			newDLFileEntry.getUserId());
		Assert.assertEquals(existingDLFileEntry.getUserName(),
			newDLFileEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntry.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntry.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntry.getModifiedDate()));
		Assert.assertEquals(existingDLFileEntry.getClassNameId(),
			newDLFileEntry.getClassNameId());
		Assert.assertEquals(existingDLFileEntry.getClassPK(),
			newDLFileEntry.getClassPK());
		Assert.assertEquals(existingDLFileEntry.getRepositoryId(),
			newDLFileEntry.getRepositoryId());
		Assert.assertEquals(existingDLFileEntry.getFolderId(),
			newDLFileEntry.getFolderId());
		Assert.assertEquals(existingDLFileEntry.getTreePath(),
			newDLFileEntry.getTreePath());
		Assert.assertEquals(existingDLFileEntry.getName(),
			newDLFileEntry.getName());
		Assert.assertEquals(existingDLFileEntry.getExtension(),
			newDLFileEntry.getExtension());
		Assert.assertEquals(existingDLFileEntry.getMimeType(),
			newDLFileEntry.getMimeType());
		Assert.assertEquals(existingDLFileEntry.getTitle(),
			newDLFileEntry.getTitle());
		Assert.assertEquals(existingDLFileEntry.getDescription(),
			newDLFileEntry.getDescription());
		Assert.assertEquals(existingDLFileEntry.getExtraSettings(),
			newDLFileEntry.getExtraSettings());
		Assert.assertEquals(existingDLFileEntry.getFileEntryTypeId(),
			newDLFileEntry.getFileEntryTypeId());
		Assert.assertEquals(existingDLFileEntry.getVersion(),
			newDLFileEntry.getVersion());
		Assert.assertEquals(existingDLFileEntry.getSize(),
			newDLFileEntry.getSize());
		Assert.assertEquals(existingDLFileEntry.getReadCount(),
			newDLFileEntry.getReadCount());
		Assert.assertEquals(existingDLFileEntry.getSmallImageId(),
			newDLFileEntry.getSmallImageId());
		Assert.assertEquals(existingDLFileEntry.getLargeImageId(),
			newDLFileEntry.getLargeImageId());
		Assert.assertEquals(existingDLFileEntry.getCustom1ImageId(),
			newDLFileEntry.getCustom1ImageId());
		Assert.assertEquals(existingDLFileEntry.getCustom2ImageId(),
			newDLFileEntry.getCustom2ImageId());
		Assert.assertEquals(existingDLFileEntry.getManualCheckInRequired(),
			newDLFileEntry.getManualCheckInRequired());
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
	public void testCountByMimeType() {
		try {
			_persistence.countByMimeType(StringPool.BLANK);

			_persistence.countByMimeType(StringPool.NULL);

			_persistence.countByMimeType((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByFileEntryTypeId() {
		try {
			_persistence.countByFileEntryTypeId(RandomTestUtil.nextLong());

			_persistence.countByFileEntryTypeId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByF_N() {
		try {
			_persistence.countByF_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByF_N(0L, StringPool.NULL);

			_persistence.countByF_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U() {
		try {
			_persistence.countByG_U(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F() {
		try {
			_persistence.countByG_F(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_F(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_FArrayable() {
		try {
			_persistence.countByG_F(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_F() {
		try {
			_persistence.countByG_U_F(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByG_U_F(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_FArrayable() {
		try {
			_persistence.countByG_U_F(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_N() {
		try {
			_persistence.countByG_F_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_F_N(0L, 0L, StringPool.NULL);

			_persistence.countByG_F_N(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_T() {
		try {
			_persistence.countByG_F_T(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_F_T(0L, 0L, StringPool.NULL);

			_persistence.countByG_F_T(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_F() {
		try {
			_persistence.countByG_F_F(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByG_F_F(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_FArrayable() {
		try {
			_persistence.countByG_F_F(RandomTestUtil.nextLong(),
				new long[] { RandomTestUtil.nextLong(), 0L },
				RandomTestUtil.nextLong());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFileEntryException");
		}
		catch (NoSuchFileEntryException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DLFileEntry", "uuid", true,
			"fileEntryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "repositoryId", true,
			"folderId", true, "treePath", true, "name", true, "extension",
			true, "mimeType", true, "title", true, "description", true,
			"extraSettings", true, "fileEntryTypeId", true, "version", true,
			"size", true, "readCount", true, "smallImageId", true,
			"largeImageId", true, "custom1ImageId", true, "custom2ImageId",
			true, "manualCheckInRequired", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntry missingDLFileEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntry);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DLFileEntry dlFileEntry = (DLFileEntry)object;

					Assert.assertNotNull(dlFileEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId",
				newDLFileEntry.getFileEntryId()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntry existingDLFileEntry = result.get(0);

		Assert.assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId",
				RandomTestUtil.nextLong()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileEntryId"));

		Object newFileEntryId = newDLFileEntry.getFileEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryId",
				new Object[] { newFileEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryId = result.get(0);

		Assert.assertEquals(existingFileEntryId, newFileEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.clearCache();

		DLFileEntryModelImpl existingDLFileEntryModelImpl = (DLFileEntryModelImpl)_persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFileEntryModelImpl.getUuid(),
				existingDLFileEntryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingDLFileEntryModelImpl.getFolderId(),
			existingDLFileEntryModelImpl.getOriginalFolderId());
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryModelImpl.getName(),
				existingDLFileEntryModelImpl.getOriginalName()));

		Assert.assertEquals(existingDLFileEntryModelImpl.getGroupId(),
			existingDLFileEntryModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingDLFileEntryModelImpl.getFolderId(),
			existingDLFileEntryModelImpl.getOriginalFolderId());
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryModelImpl.getTitle(),
				existingDLFileEntryModelImpl.getOriginalTitle()));
	}

	protected DLFileEntry addDLFileEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		dlFileEntry.setUuid(RandomTestUtil.randomString());

		dlFileEntry.setGroupId(RandomTestUtil.nextLong());

		dlFileEntry.setCompanyId(RandomTestUtil.nextLong());

		dlFileEntry.setUserId(RandomTestUtil.nextLong());

		dlFileEntry.setUserName(RandomTestUtil.randomString());

		dlFileEntry.setCreateDate(RandomTestUtil.nextDate());

		dlFileEntry.setModifiedDate(RandomTestUtil.nextDate());

		dlFileEntry.setClassNameId(RandomTestUtil.nextLong());

		dlFileEntry.setClassPK(RandomTestUtil.nextLong());

		dlFileEntry.setRepositoryId(RandomTestUtil.nextLong());

		dlFileEntry.setFolderId(RandomTestUtil.nextLong());

		dlFileEntry.setTreePath(RandomTestUtil.randomString());

		dlFileEntry.setName(RandomTestUtil.randomString());

		dlFileEntry.setExtension(RandomTestUtil.randomString());

		dlFileEntry.setMimeType(RandomTestUtil.randomString());

		dlFileEntry.setTitle(RandomTestUtil.randomString());

		dlFileEntry.setDescription(RandomTestUtil.randomString());

		dlFileEntry.setExtraSettings(RandomTestUtil.randomString());

		dlFileEntry.setFileEntryTypeId(RandomTestUtil.nextLong());

		dlFileEntry.setVersion(RandomTestUtil.randomString());

		dlFileEntry.setSize(RandomTestUtil.nextLong());

		dlFileEntry.setReadCount(RandomTestUtil.nextInt());

		dlFileEntry.setSmallImageId(RandomTestUtil.nextLong());

		dlFileEntry.setLargeImageId(RandomTestUtil.nextLong());

		dlFileEntry.setCustom1ImageId(RandomTestUtil.nextLong());

		dlFileEntry.setCustom2ImageId(RandomTestUtil.nextLong());

		dlFileEntry.setManualCheckInRequired(RandomTestUtil.randomBoolean());

		_persistence.update(dlFileEntry);

		return dlFileEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceTest.class);
	private ModelListener<DLFileEntry>[] _modelListeners;
	private DLFileEntryPersistence _persistence = (DLFileEntryPersistence)PortalBeanLocatorUtil.locate(DLFileEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}