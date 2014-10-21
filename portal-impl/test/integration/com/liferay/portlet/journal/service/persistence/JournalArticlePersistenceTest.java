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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.PersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

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
@RunWith(PersistenceIntegrationJUnitTestRunner.class)
public class JournalArticlePersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<JournalArticle> iterator = _journalArticles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		JournalArticle newJournalArticle = _persistence.create(pk);

		newJournalArticle.setUuid(RandomTestUtil.randomString());

		newJournalArticle.setResourcePrimKey(RandomTestUtil.nextLong());

		newJournalArticle.setGroupId(RandomTestUtil.nextLong());

		newJournalArticle.setCompanyId(RandomTestUtil.nextLong());

		newJournalArticle.setUserId(RandomTestUtil.nextLong());

		newJournalArticle.setUserName(RandomTestUtil.randomString());

		newJournalArticle.setCreateDate(RandomTestUtil.nextDate());

		newJournalArticle.setModifiedDate(RandomTestUtil.nextDate());

		newJournalArticle.setFolderId(RandomTestUtil.nextLong());

		newJournalArticle.setClassNameId(RandomTestUtil.nextLong());

		newJournalArticle.setClassPK(RandomTestUtil.nextLong());

		newJournalArticle.setTreePath(RandomTestUtil.randomString());

		newJournalArticle.setArticleId(RandomTestUtil.randomString());

		newJournalArticle.setVersion(RandomTestUtil.nextDouble());

		newJournalArticle.setTitle(RandomTestUtil.randomString());

		newJournalArticle.setUrlTitle(RandomTestUtil.randomString());

		newJournalArticle.setDescription(RandomTestUtil.randomString());

		newJournalArticle.setContent(RandomTestUtil.randomString());

		newJournalArticle.setDDMStructureKey(RandomTestUtil.randomString());

		newJournalArticle.setDDMTemplateKey(RandomTestUtil.randomString());

		newJournalArticle.setLayoutUuid(RandomTestUtil.randomString());

		newJournalArticle.setDisplayDate(RandomTestUtil.nextDate());

		newJournalArticle.setExpirationDate(RandomTestUtil.nextDate());

		newJournalArticle.setReviewDate(RandomTestUtil.nextDate());

		newJournalArticle.setIndexable(RandomTestUtil.randomBoolean());

		newJournalArticle.setSmallImage(RandomTestUtil.randomBoolean());

		newJournalArticle.setSmallImageId(RandomTestUtil.nextLong());

		newJournalArticle.setSmallImageURL(RandomTestUtil.randomString());

		newJournalArticle.setStatus(RandomTestUtil.nextInt());

		newJournalArticle.setStatusByUserId(RandomTestUtil.nextLong());

		newJournalArticle.setStatusByUserName(RandomTestUtil.randomString());

		newJournalArticle.setStatusDate(RandomTestUtil.nextDate());

		_journalArticles.add(_persistence.update(newJournalArticle));

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
		Assert.assertEquals(existingJournalArticle.getDDMStructureKey(),
			newJournalArticle.getDDMStructureKey());
		Assert.assertEquals(existingJournalArticle.getDDMTemplateKey(),
			newJournalArticle.getDDMTemplateKey());
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
	public void testCountByResourcePrimKey() {
		try {
			_persistence.countByResourcePrimKey(RandomTestUtil.nextLong());

			_persistence.countByResourcePrimKey(0L);
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
	public void testCountByDDMStructureKey() {
		try {
			_persistence.countByDDMStructureKey(StringPool.BLANK);

			_persistence.countByDDMStructureKey(StringPool.NULL);

			_persistence.countByDDMStructureKey((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByDDMStructureKeyArrayable() {
		try {
			_persistence.countByDDMStructureKey(new String[] {
					RandomTestUtil.randomString(), StringPool.BLANK,
					StringPool.NULL, null, null
				});
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByDDMTemplateKey() {
		try {
			_persistence.countByDDMTemplateKey(StringPool.BLANK);

			_persistence.countByDDMTemplateKey(StringPool.NULL);

			_persistence.countByDDMTemplateKey((String)null);
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
			_persistence.countBySmallImageId(RandomTestUtil.nextLong());

			_persistence.countBySmallImageId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_I() {
		try {
			_persistence.countByR_I(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByR_I(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_ST() {
		try {
			_persistence.countByR_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByR_ST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_STArrayable() {
		try {
			_persistence.countByR_ST(RandomTestUtil.nextLong(),
				new int[] { RandomTestUtil.nextInt(), 0 });
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
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(RandomTestUtil.nextLong(), StringPool.BLANK);

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
			_persistence.countByG_UT(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_UT(0L, StringPool.NULL);

			_persistence.countByG_UT(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_DDMSK() {
		try {
			_persistence.countByG_DDMSK(RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByG_DDMSK(0L, StringPool.NULL);

			_persistence.countByG_DDMSK(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_DDMTK() {
		try {
			_persistence.countByG_DDMTK(RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByG_DDMTK(0L, StringPool.NULL);

			_persistence.countByG_DDMTK(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_L() {
		try {
			_persistence.countByG_L(RandomTestUtil.nextLong(), StringPool.BLANK);

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
			_persistence.countByG_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_ST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_V() {
		try {
			_persistence.countByC_V(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDouble());

			_persistence.countByC_V(0L, 0D);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_ST() {
		try {
			_persistence.countByC_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_ST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_NotST() {
		try {
			_persistence.countByC_NotST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_NotST(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_DDMTK() {
		try {
			_persistence.countByC_DDMTK(RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByC_DDMTK(0L, StringPool.NULL);

			_persistence.countByC_DDMTK(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLtD_S() {
		try {
			_persistence.countByLtD_S(RandomTestUtil.nextDate(),
				RandomTestUtil.nextInt());

			_persistence.countByLtD_S(RandomTestUtil.nextDate(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_I_S() {
		try {
			_persistence.countByR_I_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt());

			_persistence.countByR_I_S(0L, RandomTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_I_SArrayable() {
		try {
			_persistence.countByR_I_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(),
				new int[] { RandomTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_C() {
		try {
			_persistence.countByG_U_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByG_U_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_ST() {
		try {
			_persistence.countByG_F_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_F_ST(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F_STArrayable() {
		try {
			_persistence.countByG_F_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(),
				new int[] { RandomTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C() {
		try {
			_persistence.countByG_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByG_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_DDMSK() {
		try {
			_persistence.countByG_C_DDMSK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_C_DDMSK(0L, 0L, StringPool.NULL);

			_persistence.countByG_C_DDMSK(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_DDMTK() {
		try {
			_persistence.countByG_C_DDMTK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_C_DDMTK(0L, 0L, StringPool.NULL);

			_persistence.countByG_C_DDMTK(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_L() {
		try {
			_persistence.countByG_C_L(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

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
			_persistence.countByG_A_V(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextDouble());

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
			_persistence.countByG_A_ST(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt());

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
			_persistence.countByG_A_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.randomString(),
				new int[] { RandomTestUtil.nextInt(), 0 });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A_NotST() {
		try {
			_persistence.countByG_A_NotST(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt());

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
			_persistence.countByG_UT_ST(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt());

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
			_persistence.countByC_V_ST(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDouble(), RandomTestUtil.nextInt());

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
		long pk = RandomTestUtil.nextLong();

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

	protected OrderByComparator<JournalArticle> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("JournalArticle", "uuid",
			true, "id", true, "resourcePrimKey", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "folderId", true, "classNameId", true,
			"classPK", true, "treePath", true, "articleId", true, "version",
			true, "title", true, "urlTitle", true, "description", true,
			"content", true, "DDMStructureKey", true, "DDMTemplateKey", true,
			"layoutUuid", true, "displayDate", true, "expirationDate", true,
			"reviewDate", true, "indexable", true, "smallImage", true,
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
		long pk = RandomTestUtil.nextLong();

		JournalArticle missingJournalArticle = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalArticle);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		JournalArticle newJournalArticle1 = addJournalArticle();
		JournalArticle newJournalArticle2 = addJournalArticle();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticle1.getPrimaryKey());
		primaryKeys.add(newJournalArticle2.getPrimaryKey());

		Map<Serializable, JournalArticle> journalArticles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, journalArticles.size());
		Assert.assertEquals(newJournalArticle1,
			journalArticles.get(newJournalArticle1.getPrimaryKey()));
		Assert.assertEquals(newJournalArticle2,
			journalArticles.get(newJournalArticle2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, JournalArticle> journalArticles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(journalArticles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticle.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, JournalArticle> journalArticles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, journalArticles.size());
		Assert.assertEquals(newJournalArticle,
			journalArticles.get(newJournalArticle.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, JournalArticle> journalArticles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(journalArticles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticle.getPrimaryKey());

		Map<Serializable, JournalArticle> journalArticles = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, journalArticles.size());
		Assert.assertEquals(newJournalArticle,
			journalArticles.get(newJournalArticle.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = JournalArticleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					JournalArticle journalArticle = (JournalArticle)object;

					Assert.assertNotNull(journalArticle);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

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
				existingJournalArticleModelImpl.getDDMStructureKey(),
				existingJournalArticleModelImpl.getOriginalDDMStructureKey()));

		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getArticleId(),
				existingJournalArticleModelImpl.getOriginalArticleId()));
		AssertUtils.assertEquals(existingJournalArticleModelImpl.getVersion(),
			existingJournalArticleModelImpl.getOriginalVersion());
	}

	protected JournalArticle addJournalArticle() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		journalArticle.setUuid(RandomTestUtil.randomString());

		journalArticle.setResourcePrimKey(RandomTestUtil.nextLong());

		journalArticle.setGroupId(RandomTestUtil.nextLong());

		journalArticle.setCompanyId(RandomTestUtil.nextLong());

		journalArticle.setUserId(RandomTestUtil.nextLong());

		journalArticle.setUserName(RandomTestUtil.randomString());

		journalArticle.setCreateDate(RandomTestUtil.nextDate());

		journalArticle.setModifiedDate(RandomTestUtil.nextDate());

		journalArticle.setFolderId(RandomTestUtil.nextLong());

		journalArticle.setClassNameId(RandomTestUtil.nextLong());

		journalArticle.setClassPK(RandomTestUtil.nextLong());

		journalArticle.setTreePath(RandomTestUtil.randomString());

		journalArticle.setArticleId(RandomTestUtil.randomString());

		journalArticle.setVersion(RandomTestUtil.nextDouble());

		journalArticle.setTitle(RandomTestUtil.randomString());

		journalArticle.setUrlTitle(RandomTestUtil.randomString());

		journalArticle.setDescription(RandomTestUtil.randomString());

		journalArticle.setContent(RandomTestUtil.randomString());

		journalArticle.setDDMStructureKey(RandomTestUtil.randomString());

		journalArticle.setDDMTemplateKey(RandomTestUtil.randomString());

		journalArticle.setLayoutUuid(RandomTestUtil.randomString());

		journalArticle.setDisplayDate(RandomTestUtil.nextDate());

		journalArticle.setExpirationDate(RandomTestUtil.nextDate());

		journalArticle.setReviewDate(RandomTestUtil.nextDate());

		journalArticle.setIndexable(RandomTestUtil.randomBoolean());

		journalArticle.setSmallImage(RandomTestUtil.randomBoolean());

		journalArticle.setSmallImageId(RandomTestUtil.nextLong());

		journalArticle.setSmallImageURL(RandomTestUtil.randomString());

		journalArticle.setStatus(RandomTestUtil.nextInt());

		journalArticle.setStatusByUserId(RandomTestUtil.nextLong());

		journalArticle.setStatusByUserName(RandomTestUtil.randomString());

		journalArticle.setStatusDate(RandomTestUtil.nextDate());

		_journalArticles.add(_persistence.update(journalArticle));

		return journalArticle;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceTest.class);
	private List<JournalArticle> _journalArticles = new ArrayList<JournalArticle>();
	private JournalArticlePersistence _persistence = JournalArticleUtil.getPersistence();
}