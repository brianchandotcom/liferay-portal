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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;

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
public class JournalArticlePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<JournalArticle> modelListener : _modelListeners) {
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

		for (ModelListener<JournalArticle> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		Assert.assertNotNull(journalArticle);

		Assert.assertEquals(journalArticle.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.remove(newJournalArticle);

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertNull(existingJournalArticle);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalArticle();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle newJournalArticle = _persistence.create(pk);

		newJournalArticle.setUuid(ServiceTestUtil.randomString());

		newJournalArticle.setResourcePrimKey(ServiceTestUtil.nextLong());

		newJournalArticle.setGroupId(ServiceTestUtil.nextLong());

		newJournalArticle.setCompanyId(ServiceTestUtil.nextLong());

		newJournalArticle.setUserId(ServiceTestUtil.nextLong());

		newJournalArticle.setUserName(ServiceTestUtil.randomString());

		newJournalArticle.setCreateDate(ServiceTestUtil.nextDate());

		newJournalArticle.setModifiedDate(ServiceTestUtil.nextDate());

		newJournalArticle.setFolderId(ServiceTestUtil.nextLong());

		newJournalArticle.setClassNameId(ServiceTestUtil.nextLong());

		newJournalArticle.setClassPK(ServiceTestUtil.nextLong());

		newJournalArticle.setTreePath(ServiceTestUtil.randomString());

		newJournalArticle.setArticleId(ServiceTestUtil.randomString());

		newJournalArticle.setVersion(ServiceTestUtil.nextDouble());

		newJournalArticle.setTitle(ServiceTestUtil.randomString());

		newJournalArticle.setUrlTitle(ServiceTestUtil.randomString());

		newJournalArticle.setDescription(ServiceTestUtil.randomString());

		newJournalArticle.setContent(ServiceTestUtil.randomString());

		newJournalArticle.setType(ServiceTestUtil.randomString());

		newJournalArticle.setStructureId(ServiceTestUtil.randomString());

		newJournalArticle.setTemplateId(ServiceTestUtil.randomString());

		newJournalArticle.setLayoutUuid(ServiceTestUtil.randomString());

		newJournalArticle.setDisplayDate(ServiceTestUtil.nextDate());

		newJournalArticle.setExpirationDate(ServiceTestUtil.nextDate());

		newJournalArticle.setReviewDate(ServiceTestUtil.nextDate());

		newJournalArticle.setIndexable(ServiceTestUtil.randomBoolean());

		newJournalArticle.setSmallImage(ServiceTestUtil.randomBoolean());

		newJournalArticle.setSmallImageId(ServiceTestUtil.nextLong());

		newJournalArticle.setSmallImageURL(ServiceTestUtil.randomString());

		newJournalArticle.setStatus(ServiceTestUtil.nextInt());

		newJournalArticle.setStatusByUserId(ServiceTestUtil.nextLong());

		newJournalArticle.setStatusByUserName(ServiceTestUtil.randomString());

		newJournalArticle.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newJournalArticle);

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertEquals(existingJournalArticle.getUuid(),
			newJournalArticle.getUuid());
		Assert.assertEquals(existingJournalArticle.getId(),
			newJournalArticle.getId());
		Assert.assertEquals(existingJournalArticle.getResourcePrimKey(),
			newJournalArticle.getResourcePrimKey());
		Assert.assertEquals(existingJournalArticle.getGroupId(),
			newJournalArticle.getGroupId());
		Assert.assertEquals(existingJournalArticle.getCompanyId(),
			newJournalArticle.getCompanyId());
		Assert.assertEquals(existingJournalArticle.getUserId(),
			newJournalArticle.getUserId());
		Assert.assertEquals(existingJournalArticle.getUserName(),
			newJournalArticle.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getCreateDate()),
			Time.getShortTimestamp(newJournalArticle.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getModifiedDate()),
			Time.getShortTimestamp(newJournalArticle.getModifiedDate()));
		Assert.assertEquals(existingJournalArticle.getFolderId(),
			newJournalArticle.getFolderId());
		Assert.assertEquals(existingJournalArticle.getClassNameId(),
			newJournalArticle.getClassNameId());
		Assert.assertEquals(existingJournalArticle.getClassPK(),
			newJournalArticle.getClassPK());
		Assert.assertEquals(existingJournalArticle.getTreePath(),
			newJournalArticle.getTreePath());
		Assert.assertEquals(existingJournalArticle.getArticleId(),
			newJournalArticle.getArticleId());
		AssertUtils.assertEquals(existingJournalArticle.getVersion(),
			newJournalArticle.getVersion());
		Assert.assertEquals(existingJournalArticle.getTitle(),
			newJournalArticle.getTitle());
		Assert.assertEquals(existingJournalArticle.getUrlTitle(),
			newJournalArticle.getUrlTitle());
		Assert.assertEquals(existingJournalArticle.getDescription(),
			newJournalArticle.getDescription());
		Assert.assertEquals(existingJournalArticle.getContent(),
			newJournalArticle.getContent());
		Assert.assertEquals(existingJournalArticle.getType(),
			newJournalArticle.getType());
		Assert.assertEquals(existingJournalArticle.getStructureId(),
			newJournalArticle.getStructureId());
		Assert.assertEquals(existingJournalArticle.getTemplateId(),
			newJournalArticle.getTemplateId());
		Assert.assertEquals(existingJournalArticle.getLayoutUuid(),
			newJournalArticle.getLayoutUuid());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getDisplayDate()),
			Time.getShortTimestamp(newJournalArticle.getDisplayDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getExpirationDate()),
			Time.getShortTimestamp(newJournalArticle.getExpirationDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getReviewDate()),
			Time.getShortTimestamp(newJournalArticle.getReviewDate()));
		Assert.assertEquals(existingJournalArticle.getIndexable(),
			newJournalArticle.getIndexable());
		Assert.assertEquals(existingJournalArticle.getSmallImage(),
			newJournalArticle.getSmallImage());
		Assert.assertEquals(existingJournalArticle.getSmallImageId(),
			newJournalArticle.getSmallImageId());
		Assert.assertEquals(existingJournalArticle.getSmallImageURL(),
			newJournalArticle.getSmallImageURL());
		Assert.assertEquals(existingJournalArticle.getStatus(),
			newJournalArticle.getStatus());
		Assert.assertEquals(existingJournalArticle.getStatusByUserId(),
			newJournalArticle.getStatusByUserId());
		Assert.assertEquals(existingJournalArticle.getStatusByUserName(),
			newJournalArticle.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getStatusDate()),
			Time.getShortTimestamp(newJournalArticle.getStatusDate()));
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
				ServiceTestUtil.nextLong());

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
				ServiceTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByResourcePrimKey() {
		try {
			_persistence.countByResourcePrimKey(ServiceTestUtil.nextLong());

			_persistence.countByResourcePrimKey(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(ServiceTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(ServiceTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByStructureId() {
		try {
			_persistence.countByStructureId(StringPool.BLANK);

			_persistence.countByStructureId(StringPool.NULL);

			_persistence.countByStructureId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByStructureIdArrayable() {
		try {
			_persistence.countByStructureId(new String[] {
					ServiceTestUtil.randomString(), StringPool.BLANK,
					StringPool.NULL, null, null
				});
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByTemplateId() {
		try {
			_persistence.countByTemplateId(StringPool.BLANK);

			_persistence.countByTemplateId(StringPool.NULL);

			_persistence.countByTemplateId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLayoutUuid() {
		try {
			_persistence.countByLayoutUuid(StringPool.BLANK);

			_persistence.countByLayoutUuid(StringPool.NULL);

			_persistence.countByLayoutUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountBySmallImageId() {
		try {
			_persistence.countBySmallImageId(ServiceTestUtil.nextLong());

			_persistence.countBySmallImageId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_I() {
		try {
			_persistence.countByR_I(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean());

			_persistence.countByR_I(0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_ST() {
		try {
			_persistence.countByR_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByR_ST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U() {
		try {
			_persistence.countByG_U(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByG_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F() {
		try {
			_persistence.countByG_F(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByG_F(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_FArrayable() {
		try {
			_persistence.countByG_F(ServiceTestUtil.nextLong(),
				new long[] { ServiceTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_A(0L, StringPool.NULL);

			_persistence.countByG_A(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_UT() {
		try {
			_persistence.countByG_UT(ServiceTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByG_UT(0L, StringPool.NULL);

			_persistence.countByG_UT(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_S(0L, StringPool.NULL);

			_persistence.countByG_S(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T() {
		try {
			_persistence.countByG_T(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_T(0L, StringPool.NULL);

			_persistence.countByG_T(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_L() {
		try {
			_persistence.countByG_L(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_L(0L, StringPool.NULL);

			_persistence.countByG_L(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_ST() {
		try {
			_persistence.countByG_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_ST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_V() {
		try {
			_persistence.countByC_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextDouble());

			_persistence.countByC_V(0L, 0D);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_ST() {
		try {
			_persistence.countByC_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByC_ST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_NotST() {
		try {
			_persistence.countByC_NotST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByC_NotST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLtD_S() {
		try {
			_persistence.countByLtD_S(ServiceTestUtil.nextDate(),
				ServiceTestUtil.nextInt());

			_persistence.countByLtD_S(ServiceTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_I_S() {
		try {
			_persistence.countByR_I_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextInt());

			_persistence.countByR_I_S(0L, ServiceTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_I_SArrayable() {
		try {
			_persistence.countByR_I_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(),
				new int[] { ServiceTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_C() {
		try {
			_persistence.countByG_U_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

			_persistence.countByG_U_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_ST() {
		try {
			_persistence.countByG_F_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());

			_persistence.countByG_F_ST(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_STArrayable() {
		try {
			_persistence.countByG_F_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(),
				new int[] { ServiceTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C() {
		try {
			_persistence.countByG_C_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

			_persistence.countByG_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_S() {
		try {
			_persistence.countByG_C_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_C_S(0L, 0L, StringPool.NULL);

			_persistence.countByG_C_S(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_T() {
		try {
			_persistence.countByG_C_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_C_T(0L, 0L, StringPool.NULL);

			_persistence.countByG_C_T(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_L() {
		try {
			_persistence.countByG_C_L(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_C_L(0L, 0L, StringPool.NULL);

			_persistence.countByG_C_L(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A_V() {
		try {
			_persistence.countByG_A_V(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextDouble());

			_persistence.countByG_A_V(0L, StringPool.NULL, 0D);

			_persistence.countByG_A_V(0L, (String)null, 0D);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A_ST() {
		try {
			_persistence.countByG_A_ST(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextInt());

			_persistence.countByG_A_ST(0L, StringPool.NULL, 0);

			_persistence.countByG_A_ST(0L, (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A_STArrayable() {
		try {
			_persistence.countByG_A_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomString(),
				new int[] { ServiceTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A_NotST() {
		try {
			_persistence.countByG_A_NotST(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextInt());

			_persistence.countByG_A_NotST(0L, StringPool.NULL, 0);

			_persistence.countByG_A_NotST(0L, (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_UT_ST() {
		try {
			_persistence.countByG_UT_ST(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextInt());

			_persistence.countByG_UT_ST(0L, StringPool.NULL, 0);

			_persistence.countByG_UT_ST(0L, (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_V_ST() {
		try {
			_persistence.countByC_V_ST(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextDouble(), ServiceTestUtil.nextInt());

			_persistence.countByC_V_ST(0L, 0D, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertEquals(existingJournalArticle, newJournalArticle);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchArticleException");
		}
		catch (NoSuchArticleException nsee) {
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
		return OrderByComparatorFactoryUtil.create("JournalArticle", "uuid",
			true, "id", true, "resourcePrimKey", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "folderId", true, "classNameId", true,
			"classPK", true, "treePath", true, "articleId", true, "version",
			true, "title", true, "urlTitle", true, "description", true,
			"content", true, "type", true, "structureId", true, "templateId",
			true, "layoutUuid", true, "displayDate", true, "expirationDate",
			true, "reviewDate", true, "indexable", true, "smallImage", true,
			"smallImageId", true, "smallImageURL", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertEquals(existingJournalArticle, newJournalArticle);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle missingJournalArticle = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalArticle);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new JournalArticleActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					JournalArticle journalArticle = (JournalArticle)object;

					Assert.assertNotNull(journalArticle);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalArticle.getId()));

		List<JournalArticle> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalArticle existingJournalArticle = result.get(0);

		Assert.assertEquals(existingJournalArticle, newJournalArticle);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				ServiceTestUtil.nextLong()));

		List<JournalArticle> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalArticle.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingId = result.get(0);

		Assert.assertEquals(existingId, newId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.clearCache();

		JournalArticleModelImpl existingJournalArticleModelImpl = (JournalArticleModelImpl)_persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getUuid(),
				existingJournalArticleModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingJournalArticleModelImpl.getClassNameId(),
			existingJournalArticleModelImpl.getOriginalClassNameId());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getStructureId(),
				existingJournalArticleModelImpl.getOriginalStructureId()));

		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getArticleId(),
				existingJournalArticleModelImpl.getOriginalArticleId()));
		AssertUtils.assertEquals(existingJournalArticleModelImpl.getVersion(),
			existingJournalArticleModelImpl.getOriginalVersion());
	}

	protected JournalArticle addJournalArticle() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		journalArticle.setUuid(ServiceTestUtil.randomString());

		journalArticle.setResourcePrimKey(ServiceTestUtil.nextLong());

		journalArticle.setGroupId(ServiceTestUtil.nextLong());

		journalArticle.setCompanyId(ServiceTestUtil.nextLong());

		journalArticle.setUserId(ServiceTestUtil.nextLong());

		journalArticle.setUserName(ServiceTestUtil.randomString());

		journalArticle.setCreateDate(ServiceTestUtil.nextDate());

		journalArticle.setModifiedDate(ServiceTestUtil.nextDate());

		journalArticle.setFolderId(ServiceTestUtil.nextLong());

		journalArticle.setClassNameId(ServiceTestUtil.nextLong());

		journalArticle.setClassPK(ServiceTestUtil.nextLong());

		journalArticle.setTreePath(ServiceTestUtil.randomString());

		journalArticle.setArticleId(ServiceTestUtil.randomString());

		journalArticle.setVersion(ServiceTestUtil.nextDouble());

		journalArticle.setTitle(ServiceTestUtil.randomString());

		journalArticle.setUrlTitle(ServiceTestUtil.randomString());

		journalArticle.setDescription(ServiceTestUtil.randomString());

		journalArticle.setContent(ServiceTestUtil.randomString());

		journalArticle.setType(ServiceTestUtil.randomString());

		journalArticle.setStructureId(ServiceTestUtil.randomString());

		journalArticle.setTemplateId(ServiceTestUtil.randomString());

		journalArticle.setLayoutUuid(ServiceTestUtil.randomString());

		journalArticle.setDisplayDate(ServiceTestUtil.nextDate());

		journalArticle.setExpirationDate(ServiceTestUtil.nextDate());

		journalArticle.setReviewDate(ServiceTestUtil.nextDate());

		journalArticle.setIndexable(ServiceTestUtil.randomBoolean());

		journalArticle.setSmallImage(ServiceTestUtil.randomBoolean());

		journalArticle.setSmallImageId(ServiceTestUtil.nextLong());

		journalArticle.setSmallImageURL(ServiceTestUtil.randomString());

		journalArticle.setStatus(ServiceTestUtil.nextInt());

		journalArticle.setStatusByUserId(ServiceTestUtil.nextLong());

		journalArticle.setStatusByUserName(ServiceTestUtil.randomString());

		journalArticle.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(journalArticle);

		return journalArticle;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceTest.class);
	private ModelListener<JournalArticle>[] _modelListeners;
	private JournalArticlePersistence _persistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}