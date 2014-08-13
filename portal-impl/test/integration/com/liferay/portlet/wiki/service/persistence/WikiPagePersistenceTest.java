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

package com.liferay.portlet.wiki.service.persistence;

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

import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageModelImpl;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

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
public class WikiPagePersistenceTest {
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
		Iterator<WikiPage> iterator = _wikiPages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		Assert.assertNotNull(wikiPage);

		Assert.assertEquals(wikiPage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		_persistence.remove(newWikiPage);

		WikiPage existingWikiPage = _persistence.fetchByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertNull(existingWikiPage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWikiPage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiPage newWikiPage = _persistence.create(pk);

		newWikiPage.setUuid(RandomTestUtil.randomString());

		newWikiPage.setResourcePrimKey(RandomTestUtil.nextLong());

		newWikiPage.setGroupId(RandomTestUtil.nextLong());

		newWikiPage.setCompanyId(RandomTestUtil.nextLong());

		newWikiPage.setUserId(RandomTestUtil.nextLong());

		newWikiPage.setUserName(RandomTestUtil.randomString());

		newWikiPage.setCreateDate(RandomTestUtil.nextDate());

		newWikiPage.setModifiedDate(RandomTestUtil.nextDate());

		newWikiPage.setNodeId(RandomTestUtil.nextLong());

		newWikiPage.setTitle(RandomTestUtil.randomString());

		newWikiPage.setVersion(RandomTestUtil.nextDouble());

		newWikiPage.setMinorEdit(RandomTestUtil.randomBoolean());

		newWikiPage.setContent(RandomTestUtil.randomString());

		newWikiPage.setSummary(RandomTestUtil.randomString());

		newWikiPage.setFormat(RandomTestUtil.randomString());

		newWikiPage.setHead(RandomTestUtil.randomBoolean());

		newWikiPage.setParentTitle(RandomTestUtil.randomString());

		newWikiPage.setRedirectTitle(RandomTestUtil.randomString());

		newWikiPage.setStatus(RandomTestUtil.nextInt());

		newWikiPage.setStatusByUserId(RandomTestUtil.nextLong());

		newWikiPage.setStatusByUserName(RandomTestUtil.randomString());

		newWikiPage.setStatusDate(RandomTestUtil.nextDate());

		_wikiPages.add(_persistence.update(newWikiPage));

		WikiPage existingWikiPage = _persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertEquals(existingWikiPage.getUuid(), newWikiPage.getUuid());
		Assert.assertEquals(existingWikiPage.getPageId(),
			newWikiPage.getPageId());
		Assert.assertEquals(existingWikiPage.getResourcePrimKey(),
			newWikiPage.getResourcePrimKey());
		Assert.assertEquals(existingWikiPage.getGroupId(),
			newWikiPage.getGroupId());
		Assert.assertEquals(existingWikiPage.getCompanyId(),
			newWikiPage.getCompanyId());
		Assert.assertEquals(existingWikiPage.getUserId(),
			newWikiPage.getUserId());
		Assert.assertEquals(existingWikiPage.getUserName(),
			newWikiPage.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiPage.getCreateDate()),
			Time.getShortTimestamp(newWikiPage.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiPage.getModifiedDate()),
			Time.getShortTimestamp(newWikiPage.getModifiedDate()));
		Assert.assertEquals(existingWikiPage.getNodeId(),
			newWikiPage.getNodeId());
		Assert.assertEquals(existingWikiPage.getTitle(), newWikiPage.getTitle());
		AssertUtils.assertEquals(existingWikiPage.getVersion(),
			newWikiPage.getVersion());
		Assert.assertEquals(existingWikiPage.getMinorEdit(),
			newWikiPage.getMinorEdit());
		Assert.assertEquals(existingWikiPage.getContent(),
			newWikiPage.getContent());
		Assert.assertEquals(existingWikiPage.getSummary(),
			newWikiPage.getSummary());
		Assert.assertEquals(existingWikiPage.getFormat(),
			newWikiPage.getFormat());
		Assert.assertEquals(existingWikiPage.getHead(), newWikiPage.getHead());
		Assert.assertEquals(existingWikiPage.getParentTitle(),
			newWikiPage.getParentTitle());
		Assert.assertEquals(existingWikiPage.getRedirectTitle(),
			newWikiPage.getRedirectTitle());
		Assert.assertEquals(existingWikiPage.getStatus(),
			newWikiPage.getStatus());
		Assert.assertEquals(existingWikiPage.getStatusByUserId(),
			newWikiPage.getStatusByUserId());
		Assert.assertEquals(existingWikiPage.getStatusByUserName(),
			newWikiPage.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiPage.getStatusDate()),
			Time.getShortTimestamp(newWikiPage.getStatusDate()));
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
	public void testCountByNodeId() {
		try {
			_persistence.countByNodeId(RandomTestUtil.nextLong());

			_persistence.countByNodeId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByFormat() {
		try {
			_persistence.countByFormat(StringPool.BLANK);

			_persistence.countByFormat(StringPool.NULL);

			_persistence.countByFormat((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N() {
		try {
			_persistence.countByR_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByR_N(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_S() {
		try {
			_persistence.countByR_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByR_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T() {
		try {
			_persistence.countByN_T(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByN_T(0L, StringPool.NULL);

			_persistence.countByN_T(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H() {
		try {
			_persistence.countByN_H(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByN_H(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_P() {
		try {
			_persistence.countByN_P(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByN_P(0L, StringPool.NULL);

			_persistence.countByN_P(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_R() {
		try {
			_persistence.countByN_R(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByN_R(0L, StringPool.NULL);

			_persistence.countByN_R(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_S() {
		try {
			_persistence.countByN_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByN_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N_V() {
		try {
			_persistence.countByR_N_V(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextDouble());

			_persistence.countByR_N_V(0L, 0L, 0D);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N_H() {
		try {
			_persistence.countByR_N_H(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

			_persistence.countByR_N_H(0L, 0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N_S() {
		try {
			_persistence.countByR_N_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByR_N_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_H() {
		try {
			_persistence.countByG_N_H(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

			_persistence.countByG_N_H(0L, 0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_S() {
		try {
			_persistence.countByG_N_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_N_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU_N_S() {
		try {
			_persistence.countByU_N_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByU_N_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T_V() {
		try {
			_persistence.countByN_T_V(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextDouble());

			_persistence.countByN_T_V(0L, StringPool.NULL, 0D);

			_persistence.countByN_T_V(0L, (String)null, 0D);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T_H() {
		try {
			_persistence.countByN_T_H(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.randomBoolean());

			_persistence.countByN_T_H(0L, StringPool.NULL,
				RandomTestUtil.randomBoolean());

			_persistence.countByN_T_H(0L, (String)null,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T_S() {
		try {
			_persistence.countByN_T_S(RandomTestUtil.nextLong(),
				StringPool.BLANK, RandomTestUtil.nextInt());

			_persistence.countByN_T_S(0L, StringPool.NULL, 0);

			_persistence.countByN_T_S(0L, (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_P() {
		try {
			_persistence.countByN_H_P(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByN_H_P(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByN_H_P(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_R() {
		try {
			_persistence.countByN_H_R(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByN_H_R(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByN_H_R(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_S() {
		try {
			_persistence.countByN_H_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt());

			_persistence.countByN_H_S(0L, RandomTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_NotS() {
		try {
			_persistence.countByN_H_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt());

			_persistence.countByN_H_NotS(0L, RandomTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_N_S() {
		try {
			_persistence.countByG_U_N_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_U_N_S(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_T_H() {
		try {
			_persistence.countByG_N_T_H(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK,
				RandomTestUtil.randomBoolean());

			_persistence.countByG_N_T_H(0L, 0L, StringPool.NULL,
				RandomTestUtil.randomBoolean());

			_persistence.countByG_N_T_H(0L, 0L, (String)null,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_H_S() {
		try {
			_persistence.countByG_N_H_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
				RandomTestUtil.nextInt());

			_persistence.countByG_N_H_S(0L, 0L, RandomTestUtil.randomBoolean(),
				0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_P_S() {
		try {
			_persistence.countByN_H_P_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK,
				RandomTestUtil.nextInt());

			_persistence.countByN_H_P_S(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_P_S(0L, RandomTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_P_NotS() {
		try {
			_persistence.countByN_H_P_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK,
				RandomTestUtil.nextInt());

			_persistence.countByN_H_P_NotS(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_P_NotS(0L, RandomTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_R_S() {
		try {
			_persistence.countByN_H_R_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK,
				RandomTestUtil.nextInt());

			_persistence.countByN_H_R_S(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_R_S(0L, RandomTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_R_NotS() {
		try {
			_persistence.countByN_H_R_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK,
				RandomTestUtil.nextInt());

			_persistence.countByN_H_R_NotS(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_R_NotS(0L, RandomTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_H_P_S() {
		try {
			_persistence.countByG_N_H_P_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
				StringPool.BLANK, RandomTestUtil.nextInt());

			_persistence.countByG_N_H_P_S(0L, 0L,
				RandomTestUtil.randomBoolean(), StringPool.NULL, 0);

			_persistence.countByG_N_H_P_S(0L, 0L,
				RandomTestUtil.randomBoolean(), (String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		WikiPage existingWikiPage = _persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertEquals(existingWikiPage, newWikiPage);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchPageException");
		}
		catch (NoSuchPageException nsee) {
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

	protected OrderByComparator<WikiPage> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WikiPage", "uuid", true,
			"pageId", true, "resourcePrimKey", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "nodeId", true, "title", true,
			"version", true, "minorEdit", true, "content", true, "summary",
			true, "format", true, "head", true, "parentTitle", true,
			"redirectTitle", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		WikiPage existingWikiPage = _persistence.fetchByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertEquals(existingWikiPage, newWikiPage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiPage missingWikiPage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWikiPage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WikiPage newWikiPage1 = addWikiPage();
		WikiPage newWikiPage2 = addWikiPage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWikiPage1.getPrimaryKey());
		primaryKeys.add(newWikiPage2.getPrimaryKey());

		Map<Serializable, WikiPage> wikiPages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, wikiPages.size());
		Assert.assertEquals(newWikiPage1,
			wikiPages.get(newWikiPage1.getPrimaryKey()));
		Assert.assertEquals(newWikiPage2,
			wikiPages.get(newWikiPage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WikiPage> wikiPages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wikiPages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWikiPage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WikiPage> wikiPages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wikiPages.size());
		Assert.assertEquals(newWikiPage,
			wikiPages.get(newWikiPage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WikiPage> wikiPages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wikiPages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWikiPage.getPrimaryKey());

		Map<Serializable, WikiPage> wikiPages = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wikiPages.size());
		Assert.assertEquals(newWikiPage,
			wikiPages.get(newWikiPage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WikiPageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					WikiPage wikiPage = (WikiPage)object;

					Assert.assertNotNull(wikiPage);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pageId",
				newWikiPage.getPageId()));

		List<WikiPage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WikiPage existingWikiPage = result.get(0);

		Assert.assertEquals(existingWikiPage, newWikiPage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pageId",
				RandomTestUtil.nextLong()));

		List<WikiPage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("pageId"));

		Object newPageId = newWikiPage.getPageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("pageId",
				new Object[] { newPageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPageId = result.get(0);

		Assert.assertEquals(existingPageId, newPageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("pageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("pageId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WikiPage newWikiPage = addWikiPage();

		_persistence.clearCache();

		WikiPageModelImpl existingWikiPageModelImpl = (WikiPageModelImpl)_persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingWikiPageModelImpl.getUuid(),
				existingWikiPageModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingWikiPageModelImpl.getGroupId(),
			existingWikiPageModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingWikiPageModelImpl.getResourcePrimKey(),
			existingWikiPageModelImpl.getOriginalResourcePrimKey());
		Assert.assertEquals(existingWikiPageModelImpl.getNodeId(),
			existingWikiPageModelImpl.getOriginalNodeId());
		AssertUtils.assertEquals(existingWikiPageModelImpl.getVersion(),
			existingWikiPageModelImpl.getOriginalVersion());

		Assert.assertEquals(existingWikiPageModelImpl.getNodeId(),
			existingWikiPageModelImpl.getOriginalNodeId());
		Assert.assertTrue(Validator.equals(
				existingWikiPageModelImpl.getTitle(),
				existingWikiPageModelImpl.getOriginalTitle()));
		AssertUtils.assertEquals(existingWikiPageModelImpl.getVersion(),
			existingWikiPageModelImpl.getOriginalVersion());
	}

	protected WikiPage addWikiPage() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		wikiPage.setUuid(RandomTestUtil.randomString());

		wikiPage.setResourcePrimKey(RandomTestUtil.nextLong());

		wikiPage.setGroupId(RandomTestUtil.nextLong());

		wikiPage.setCompanyId(RandomTestUtil.nextLong());

		wikiPage.setUserId(RandomTestUtil.nextLong());

		wikiPage.setUserName(RandomTestUtil.randomString());

		wikiPage.setCreateDate(RandomTestUtil.nextDate());

		wikiPage.setModifiedDate(RandomTestUtil.nextDate());

		wikiPage.setNodeId(RandomTestUtil.nextLong());

		wikiPage.setTitle(RandomTestUtil.randomString());

		wikiPage.setVersion(RandomTestUtil.nextDouble());

		wikiPage.setMinorEdit(RandomTestUtil.randomBoolean());

		wikiPage.setContent(RandomTestUtil.randomString());

		wikiPage.setSummary(RandomTestUtil.randomString());

		wikiPage.setFormat(RandomTestUtil.randomString());

		wikiPage.setHead(RandomTestUtil.randomBoolean());

		wikiPage.setParentTitle(RandomTestUtil.randomString());

		wikiPage.setRedirectTitle(RandomTestUtil.randomString());

		wikiPage.setStatus(RandomTestUtil.nextInt());

		wikiPage.setStatusByUserId(RandomTestUtil.nextLong());

		wikiPage.setStatusByUserName(RandomTestUtil.randomString());

		wikiPage.setStatusDate(RandomTestUtil.nextDate());

		_wikiPages.add(_persistence.update(wikiPage));

		return wikiPage;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPagePersistenceTest.class);
	private List<WikiPage> _wikiPages = new ArrayList<WikiPage>();
	private WikiPagePersistence _persistence = WikiPageUtil.getPersistence();
}