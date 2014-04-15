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

import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageModelImpl;

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
public class WikiPagePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<WikiPage> modelListener : _modelListeners) {
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

		for (ModelListener<WikiPage> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		WikiPage newWikiPage = _persistence.create(pk);

		newWikiPage.setUuid(ServiceTestUtil.randomString());

		newWikiPage.setResourcePrimKey(ServiceTestUtil.nextLong());

		newWikiPage.setGroupId(ServiceTestUtil.nextLong());

		newWikiPage.setCompanyId(ServiceTestUtil.nextLong());

		newWikiPage.setUserId(ServiceTestUtil.nextLong());

		newWikiPage.setUserName(ServiceTestUtil.randomString());

		newWikiPage.setCreateDate(ServiceTestUtil.nextDate());

		newWikiPage.setModifiedDate(ServiceTestUtil.nextDate());

		newWikiPage.setNodeId(ServiceTestUtil.nextLong());

		newWikiPage.setTitle(ServiceTestUtil.randomString());

		newWikiPage.setVersion(ServiceTestUtil.nextDouble());

		newWikiPage.setMinorEdit(ServiceTestUtil.randomBoolean());

		newWikiPage.setContent(ServiceTestUtil.randomString());

		newWikiPage.setSummary(ServiceTestUtil.randomString());

		newWikiPage.setFormat(ServiceTestUtil.randomString());

		newWikiPage.setHead(ServiceTestUtil.randomBoolean());

		newWikiPage.setParentTitle(ServiceTestUtil.randomString());

		newWikiPage.setRedirectTitle(ServiceTestUtil.randomString());

		newWikiPage.setStatus(ServiceTestUtil.nextInt());

		newWikiPage.setStatusByUserId(ServiceTestUtil.nextLong());

		newWikiPage.setStatusByUserName(ServiceTestUtil.randomString());

		newWikiPage.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newWikiPage);

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
	public void testCountByNodeId() {
		try {
			_persistence.countByNodeId(ServiceTestUtil.nextLong());

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
			_persistence.countByR_N(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByR_N(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_S() {
		try {
			_persistence.countByR_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByR_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T() {
		try {
			_persistence.countByN_T(ServiceTestUtil.nextLong(), StringPool.BLANK);

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
			_persistence.countByN_H(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean());

			_persistence.countByN_H(0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_P() {
		try {
			_persistence.countByN_P(ServiceTestUtil.nextLong(), StringPool.BLANK);

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
			_persistence.countByN_R(ServiceTestUtil.nextLong(), StringPool.BLANK);

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
			_persistence.countByN_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByN_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N_V() {
		try {
			_persistence.countByR_N_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextDouble());

			_persistence.countByR_N_V(0L, 0L, 0D);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N_H() {
		try {
			_persistence.countByR_N_H(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.randomBoolean());

			_persistence.countByR_N_H(0L, 0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_N_S() {
		try {
			_persistence.countByR_N_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());

			_persistence.countByR_N_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_H() {
		try {
			_persistence.countByG_N_H(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.randomBoolean());

			_persistence.countByG_N_H(0L, 0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_S() {
		try {
			_persistence.countByG_N_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());

			_persistence.countByG_N_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU_N_S() {
		try {
			_persistence.countByU_N_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());

			_persistence.countByU_N_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T_V() {
		try {
			_persistence.countByN_T_V(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextDouble());

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
			_persistence.countByN_T_H(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.randomBoolean());

			_persistence.countByN_T_H(0L, StringPool.NULL,
				ServiceTestUtil.randomBoolean());

			_persistence.countByN_T_H(0L, (String)null,
				ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_T_S() {
		try {
			_persistence.countByN_T_S(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextInt());

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
			_persistence.countByN_H_P(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByN_H_P(0L, ServiceTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByN_H_P(0L, ServiceTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_S() {
		try {
			_persistence.countByN_H_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextInt());

			_persistence.countByN_H_S(0L, ServiceTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_NotS() {
		try {
			_persistence.countByN_H_NotS(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextInt());

			_persistence.countByN_H_NotS(0L, ServiceTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_N_S() {
		try {
			_persistence.countByG_U_N_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_U_N_S(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_T_H() {
		try {
			_persistence.countByG_N_T_H(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK,
				ServiceTestUtil.randomBoolean());

			_persistence.countByG_N_T_H(0L, 0L, StringPool.NULL,
				ServiceTestUtil.randomBoolean());

			_persistence.countByG_N_T_H(0L, 0L, (String)null,
				ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_H_S() {
		try {
			_persistence.countByG_N_H_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.randomBoolean(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_N_H_S(0L, 0L,
				ServiceTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_P_S() {
		try {
			_persistence.countByN_H_P_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), StringPool.BLANK,
				ServiceTestUtil.nextInt());

			_persistence.countByN_H_P_S(0L, ServiceTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_P_S(0L, ServiceTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_P_NotS() {
		try {
			_persistence.countByN_H_P_NotS(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), StringPool.BLANK,
				ServiceTestUtil.nextInt());

			_persistence.countByN_H_P_NotS(0L, ServiceTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_P_NotS(0L, ServiceTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_R_S() {
		try {
			_persistence.countByN_H_R_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), StringPool.BLANK,
				ServiceTestUtil.nextInt());

			_persistence.countByN_H_R_S(0L, ServiceTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_R_S(0L, ServiceTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_H_R_NotS() {
		try {
			_persistence.countByN_H_R_NotS(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), StringPool.BLANK,
				ServiceTestUtil.nextInt());

			_persistence.countByN_H_R_NotS(0L, ServiceTestUtil.randomBoolean(),
				StringPool.NULL, 0);

			_persistence.countByN_H_R_NotS(0L, ServiceTestUtil.randomBoolean(),
				(String)null, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N_H_P_S() {
		try {
			_persistence.countByG_N_H_P_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.randomBoolean(),
				StringPool.BLANK, ServiceTestUtil.nextInt());

			_persistence.countByG_N_H_P_S(0L, 0L,
				ServiceTestUtil.randomBoolean(), StringPool.NULL, 0);

			_persistence.countByG_N_H_P_S(0L, 0L,
				ServiceTestUtil.randomBoolean(), (String)null, 0);
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
		long pk = ServiceTestUtil.nextLong();

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

	protected OrderByComparator getOrderByComparator() {
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
		long pk = ServiceTestUtil.nextLong();

		WikiPage missingWikiPage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWikiPage);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new WikiPageActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					WikiPage wikiPage = (WikiPage)object;

					Assert.assertNotNull(wikiPage);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		wikiPage.setUuid(ServiceTestUtil.randomString());

		wikiPage.setResourcePrimKey(ServiceTestUtil.nextLong());

		wikiPage.setGroupId(ServiceTestUtil.nextLong());

		wikiPage.setCompanyId(ServiceTestUtil.nextLong());

		wikiPage.setUserId(ServiceTestUtil.nextLong());

		wikiPage.setUserName(ServiceTestUtil.randomString());

		wikiPage.setCreateDate(ServiceTestUtil.nextDate());

		wikiPage.setModifiedDate(ServiceTestUtil.nextDate());

		wikiPage.setNodeId(ServiceTestUtil.nextLong());

		wikiPage.setTitle(ServiceTestUtil.randomString());

		wikiPage.setVersion(ServiceTestUtil.nextDouble());

		wikiPage.setMinorEdit(ServiceTestUtil.randomBoolean());

		wikiPage.setContent(ServiceTestUtil.randomString());

		wikiPage.setSummary(ServiceTestUtil.randomString());

		wikiPage.setFormat(ServiceTestUtil.randomString());

		wikiPage.setHead(ServiceTestUtil.randomBoolean());

		wikiPage.setParentTitle(ServiceTestUtil.randomString());

		wikiPage.setRedirectTitle(ServiceTestUtil.randomString());

		wikiPage.setStatus(ServiceTestUtil.nextInt());

		wikiPage.setStatusByUserId(ServiceTestUtil.nextLong());

		wikiPage.setStatusByUserName(ServiceTestUtil.randomString());

		wikiPage.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(wikiPage);

		return wikiPage;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPagePersistenceTest.class);
	private ModelListener<WikiPage>[] _modelListeners;
	private WikiPagePersistence _persistence = (WikiPagePersistence)PortalBeanLocatorUtil.locate(WikiPagePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}