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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.log.CaptureAppender;
import com.liferay.portal.log.Log4JLoggerTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PersistenceNestedSetsTreeManagerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		_assetCategoryPersistence = AssetCategoryUtil.getPersistence();

		_sessionFactoryInvocationHandler = new SessionFactoryInvocationHandler(
			ReflectionTestUtil.getFieldValue(
				_assetCategoryPersistence, "_sessionFactory"));

		ReflectionTestUtil.setFieldValue(
			_assetCategoryPersistence, "_sessionFactory",
			ProxyUtil.newProxyInstance(
				SessionFactory.class.getClassLoader(),
				new Class<?>[] {SessionFactory.class},
				_sessionFactoryInvocationHandler));

		_assetCategoryPersistence.setRebuildTreeEnabled(false);

		_nestedSetsTreeManager =
			new PersistenceNestedSetsTreeManager<AssetCategory>(
				(BasePersistenceImpl<?>)_assetCategoryPersistence,
				"AssetCategory", "AssetCategory", AssetCategoryImpl.class,
				"categoryId", "groupId", "leftCategoryId", "rightCategoryId");

		_group = GroupTestUtil.addGroup();

		_assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		_assetCategories = new AssetCategory[9];

		for (int i = 0; i < 9; i++) {
			_assetCategories[i] = AssetTestUtil.addCategory(
				_group.getGroupId(), _assetVocabulary.getVocabularyId());
		}

		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = false;
	}

	@After
	public void tearDown() throws PortalException {
		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = true;

		GroupLocalServiceUtil.deleteGroup(_group);

		_assetCategoryPersistence.setRebuildTreeEnabled(true);
	}

	@Test
	public void testCountAncestors() {
		testInsert();

		assertCountAncestors(1, _assetCategories[0]);
		assertCountAncestors(1, _assetCategories[1]);
		assertCountAncestors(1, _assetCategories[2]);
		assertCountAncestors(2, _assetCategories[3]);
		assertCountAncestors(2, _assetCategories[4]);
		assertCountAncestors(3, _assetCategories[5]);
		assertCountAncestors(2, _assetCategories[6]);
		assertCountAncestors(3, _assetCategories[7]);
		assertCountAncestors(3, _assetCategories[8]);
	}

	@Test
	public void testCountChildren() {
		testInsert();

		assertCountChildren(5, _assetCategories[0]);
		assertCountChildren(3, _assetCategories[1]);
		assertCountChildren(1, _assetCategories[2]);
		assertCountChildren(2, _assetCategories[3]);
		assertCountChildren(2, _assetCategories[4]);
		assertCountChildren(1, _assetCategories[5]);
		assertCountChildren(2, _assetCategories[6]);
		assertCountChildren(1, _assetCategories[7]);
		assertCountChildren(1, _assetCategories[8]);
	}

	@Test
	public void testDelete() {
		testInsert();

		_nestedSetsTreeManager.delete(_assetCategories[7]);

		synchronizeAssetCategories(_assetCategories[7], true);

		assertLeftAndRight(_assetCategories[0], 1, 10);
		assertLeftAndRight(_assetCategories[1], 11, 14);
		assertLeftAndRight(_assetCategories[2], 15, 16);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 9);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 12, 13);
		assertLeftAndRight(_assetCategories[8], 7, 8);

		_nestedSetsTreeManager.delete(_assetCategories[4]);

		synchronizeAssetCategories(_assetCategories[4], true);

		assertLeftAndRight(_assetCategories[0], 1, 8);
		assertLeftAndRight(_assetCategories[1], 9, 12);
		assertLeftAndRight(_assetCategories[2], 13, 14);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 10, 11);
		assertLeftAndRight(_assetCategories[8], 6, 7);

		_nestedSetsTreeManager.delete(_assetCategories[0]);

		synchronizeAssetCategories(_assetCategories[0], true);

		assertLeftAndRight(_assetCategories[1], 7, 10);
		assertLeftAndRight(_assetCategories[2], 11, 12);
		assertLeftAndRight(_assetCategories[3], 1, 4);
		assertLeftAndRight(_assetCategories[5], 2, 3);
		assertLeftAndRight(_assetCategories[6], 8, 9);
		assertLeftAndRight(_assetCategories[8], 5, 6);

		_nestedSetsTreeManager.delete(_assetCategories[8]);

		synchronizeAssetCategories(_assetCategories[8], true);

		assertLeftAndRight(_assetCategories[1], 5, 8);
		assertLeftAndRight(_assetCategories[2], 9, 10);
		assertLeftAndRight(_assetCategories[3], 1, 4);
		assertLeftAndRight(_assetCategories[5], 2, 3);
		assertLeftAndRight(_assetCategories[6], 6, 7);

		_nestedSetsTreeManager.delete(_assetCategories[2]);

		synchronizeAssetCategories(_assetCategories[2], true);

		assertLeftAndRight(_assetCategories[1], 5, 8);
		assertLeftAndRight(_assetCategories[3], 1, 4);
		assertLeftAndRight(_assetCategories[5], 2, 3);
		assertLeftAndRight(_assetCategories[6], 6, 7);

		_nestedSetsTreeManager.delete(_assetCategories[5]);

		synchronizeAssetCategories(_assetCategories[5], true);

		assertLeftAndRight(_assetCategories[1], 3, 6);
		assertLeftAndRight(_assetCategories[3], 1, 2);
		assertLeftAndRight(_assetCategories[6], 4, 5);

		_nestedSetsTreeManager.delete(_assetCategories[1]);

		synchronizeAssetCategories(_assetCategories[1], true);

		assertLeftAndRight(_assetCategories[3], 1, 2);
		assertLeftAndRight(_assetCategories[6], 3, 4);

		_nestedSetsTreeManager.delete(_assetCategories[6]);

		synchronizeAssetCategories(_assetCategories[6], true);

		assertLeftAndRight(_assetCategories[3], 1, 2);

		synchronizeAssetCategories(_assetCategories[3], true);

		for (AssetCategory assetCategorie : _assetCategories) {
			Assert.assertNull(assetCategorie);
		}
	}

	@Test
	public void testError() {
		_sessionFactoryInvocationHandler.setFailOpenSession(true);

		CaptureAppender captureAppender =
			Log4JLoggerTestUtil.configureLog4JLogger(
				BasePersistenceImpl.class.getName(), Level.OFF);

		try {
			try {
				_nestedSetsTreeManager.doCountAncestors(0, 0, 0);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}

			try {
				_nestedSetsTreeManager.doCountDescendants(0, 0, 0);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}

			try {
				_nestedSetsTreeManager.doGetAncestors(0, 0, 0);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}

			try {
				_nestedSetsTreeManager.doGetDescendants(0, 0, 0);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}

			try {
				_nestedSetsTreeManager.doUpdate(0, true, 0, 0, true);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}

			try {
				_nestedSetsTreeManager.doUpdate(0, 0, 0, true, 0, true, null);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}

			try {
				_nestedSetsTreeManager.getMaxNestedSetsTreeNodeRight(0);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable t = se.getCause();

				t = t.getCause();

				Assert.assertEquals("Unable to open session", t.getMessage());
			}
		}
		finally {
			captureAppender.close();

			_sessionFactoryInvocationHandler.setFailOpenSession(false);
		}
	}

	@Test
	public void testGetAncestors() {
		testInsert();

		assertGetAncestors(_assetCategories[0]);
		assertGetAncestors(_assetCategories[1]);
		assertGetAncestors(_assetCategories[2]);
		assertGetAncestors(_assetCategories[3], _assetCategories[0]);
		assertGetAncestors(_assetCategories[4], _assetCategories[0]);
		assertGetAncestors(
			_assetCategories[5], _assetCategories[3], _assetCategories[0]);
		assertGetAncestors(_assetCategories[6], _assetCategories[1]);
		assertGetAncestors(
			_assetCategories[7], _assetCategories[6], _assetCategories[1]);
		assertGetAncestors(
			_assetCategories[8], _assetCategories[4], _assetCategories[0]);
	}

	@Test
	public void testGetDescendants() {
		testInsert();

		assertGetDescendants(
			_assetCategories[0], _assetCategories[3], _assetCategories[4],
			_assetCategories[5], _assetCategories[8]);
		assertGetDescendants(
			_assetCategories[1], _assetCategories[6], _assetCategories[7]);
		assertGetDescendants(_assetCategories[2]);
		assertGetDescendants(_assetCategories[3], _assetCategories[5]);
		assertGetDescendants(_assetCategories[4], _assetCategories[8]);
		assertGetDescendants(_assetCategories[5]);
		assertGetDescendants(_assetCategories[6], _assetCategories[7]);
		assertGetDescendants(_assetCategories[7]);
		assertGetDescendants(_assetCategories[8]);
	}

	@Test
	public void testInsert() {

		// (0)

		_nestedSetsTreeManager.insert(_assetCategories[0], null);

		synchronizeAssetCategories(_assetCategories[0]);

		assertLeftAndRight(_assetCategories[0], 1, 2);

		// (0, 1)

		_nestedSetsTreeManager.insert(_assetCategories[1], null);

		synchronizeAssetCategories(_assetCategories[1]);

		assertLeftAndRight(_assetCategories[0], 1, 2);
		assertLeftAndRight(_assetCategories[1], 3, 4);

		// (0, 1, 2)

		_nestedSetsTreeManager.insert(_assetCategories[2], null);

		synchronizeAssetCategories(_assetCategories[2]);

		assertLeftAndRight(_assetCategories[0], 1, 2);
		assertLeftAndRight(_assetCategories[1], 3, 4);
		assertLeftAndRight(_assetCategories[2], 5, 6);

		// (0(3), 1, 2)

		_nestedSetsTreeManager.insert(_assetCategories[3], _assetCategories[0]);

		synchronizeAssetCategories(_assetCategories[3]);

		assertLeftAndRight(_assetCategories[0], 1, 4);
		assertLeftAndRight(_assetCategories[1], 5, 6);
		assertLeftAndRight(_assetCategories[2], 7, 8);
		assertLeftAndRight(_assetCategories[3], 2, 3);

		// (0(3, 4), 1, 2)

		_nestedSetsTreeManager.insert(_assetCategories[4], _assetCategories[0]);

		synchronizeAssetCategories(_assetCategories[4]);

		assertLeftAndRight(_assetCategories[0], 1, 6);
		assertLeftAndRight(_assetCategories[1], 7, 8);
		assertLeftAndRight(_assetCategories[2], 9, 10);
		assertLeftAndRight(_assetCategories[3], 2, 3);
		assertLeftAndRight(_assetCategories[4], 4, 5);

		// (0(3(5), 4), 1, 2)

		_nestedSetsTreeManager.insert(_assetCategories[5], _assetCategories[3]);

		synchronizeAssetCategories(_assetCategories[5]);

		assertLeftAndRight(_assetCategories[0], 1, 8);
		assertLeftAndRight(_assetCategories[1], 9, 10);
		assertLeftAndRight(_assetCategories[2], 11, 12);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 7);
		assertLeftAndRight(_assetCategories[5], 3, 4);

		// (0(3(5), 4), 1(6), 2)

		_nestedSetsTreeManager.insert(_assetCategories[6], _assetCategories[1]);

		synchronizeAssetCategories(_assetCategories[6]);

		assertLeftAndRight(_assetCategories[0], 1, 8);
		assertLeftAndRight(_assetCategories[1], 9, 12);
		assertLeftAndRight(_assetCategories[2], 13, 14);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 7);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 10, 11);

		// (0(3(5), 4), 1(6(7)), 2)

		_nestedSetsTreeManager.insert(_assetCategories[7], _assetCategories[6]);

		synchronizeAssetCategories(_assetCategories[7]);

		assertLeftAndRight(_assetCategories[0], 1, 8);
		assertLeftAndRight(_assetCategories[1], 9, 14);
		assertLeftAndRight(_assetCategories[2], 15, 16);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 7);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 10, 13);
		assertLeftAndRight(_assetCategories[7], 11, 12);

		// (0(3(5), 4(8)), 1(6(7)), 2)

		_nestedSetsTreeManager.insert(_assetCategories[8], _assetCategories[4]);

		synchronizeAssetCategories(_assetCategories[8]);

		assertLeftAndRight(_assetCategories[0], 1, 10);
		assertLeftAndRight(_assetCategories[1], 11, 16);
		assertLeftAndRight(_assetCategories[2], 17, 18);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 9);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 12, 15);
		assertLeftAndRight(_assetCategories[7], 13, 14);
		assertLeftAndRight(_assetCategories[8], 7, 8);
	}

	@Test
	public void testMove() {
		testInsert();

		_nestedSetsTreeManager.move(_assetCategories[4], null, null);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 10);
		assertLeftAndRight(_assetCategories[1], 11, 16);
		assertLeftAndRight(_assetCategories[2], 17, 18);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 9);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 12, 15);
		assertLeftAndRight(_assetCategories[7], 13, 14);
		assertLeftAndRight(_assetCategories[8], 7, 8);

		_nestedSetsTreeManager.move(
			_assetCategories[4], _assetCategories[0], _assetCategories[0]);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 10);
		assertLeftAndRight(_assetCategories[1], 11, 16);
		assertLeftAndRight(_assetCategories[2], 17, 18);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 6, 9);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 12, 15);
		assertLeftAndRight(_assetCategories[7], 13, 14);
		assertLeftAndRight(_assetCategories[8], 7, 8);

		_nestedSetsTreeManager.move(
			_assetCategories[4], _assetCategories[0], _assetCategories[2]);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 6);
		assertLeftAndRight(_assetCategories[1], 7, 12);
		assertLeftAndRight(_assetCategories[2], 13, 18);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 14, 17);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 8, 11);
		assertLeftAndRight(_assetCategories[7], 9, 10);
		assertLeftAndRight(_assetCategories[8], 15, 16);

		_nestedSetsTreeManager.move(
			_assetCategories[2], null, _assetCategories[0]);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 12);
		assertLeftAndRight(_assetCategories[1], 13, 18);
		assertLeftAndRight(_assetCategories[2], 6, 11);
		assertLeftAndRight(_assetCategories[3], 2, 5);
		assertLeftAndRight(_assetCategories[4], 7, 10);
		assertLeftAndRight(_assetCategories[5], 3, 4);
		assertLeftAndRight(_assetCategories[6], 14, 17);
		assertLeftAndRight(_assetCategories[7], 15, 16);
		assertLeftAndRight(_assetCategories[8], 8, 9);

		_nestedSetsTreeManager.move(
			_assetCategories[3], _assetCategories[0], null);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 8);
		assertLeftAndRight(_assetCategories[1], 9, 14);
		assertLeftAndRight(_assetCategories[2], 2, 7);
		assertLeftAndRight(_assetCategories[3], 15, 18);
		assertLeftAndRight(_assetCategories[4], 3, 6);
		assertLeftAndRight(_assetCategories[5], 16, 17);
		assertLeftAndRight(_assetCategories[6], 10, 13);
		assertLeftAndRight(_assetCategories[7], 11, 12);
		assertLeftAndRight(_assetCategories[8], 4, 5);

		_nestedSetsTreeManager.move(
			_assetCategories[1], null, _assetCategories[0]);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 14);
		assertLeftAndRight(_assetCategories[1], 8, 13);
		assertLeftAndRight(_assetCategories[2], 2, 7);
		assertLeftAndRight(_assetCategories[3], 15, 18);
		assertLeftAndRight(_assetCategories[4], 3, 6);
		assertLeftAndRight(_assetCategories[5], 16, 17);
		assertLeftAndRight(_assetCategories[6], 9, 12);
		assertLeftAndRight(_assetCategories[7], 10, 11);
		assertLeftAndRight(_assetCategories[8], 4, 5);

		_nestedSetsTreeManager.move(
			_assetCategories[3], null, _assetCategories[1]);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 18);
		assertLeftAndRight(_assetCategories[1], 8, 17);
		assertLeftAndRight(_assetCategories[2], 2, 7);
		assertLeftAndRight(_assetCategories[3], 13, 16);
		assertLeftAndRight(_assetCategories[4], 3, 6);
		assertLeftAndRight(_assetCategories[5], 14, 15);
		assertLeftAndRight(_assetCategories[6], 9, 12);
		assertLeftAndRight(_assetCategories[7], 10, 11);
		assertLeftAndRight(_assetCategories[8], 4, 5);

		_nestedSetsTreeManager.move(
			_assetCategories[2], _assetCategories[0], _assetCategories[3]);

		synchronizeAssetCategories(null);

		assertLeftAndRight(_assetCategories[0], 1, 18);
		assertLeftAndRight(_assetCategories[1], 2, 17);
		assertLeftAndRight(_assetCategories[2], 10, 15);
		assertLeftAndRight(_assetCategories[3], 7, 16);
		assertLeftAndRight(_assetCategories[4], 11, 14);
		assertLeftAndRight(_assetCategories[5], 8, 9);
		assertLeftAndRight(_assetCategories[6], 3, 6);
		assertLeftAndRight(_assetCategories[7], 4, 5);
		assertLeftAndRight(_assetCategories[8], 12, 13);
	}

	protected void assertCountAncestors(
		long ancestorsCount, AssetCategory assetCategory) {

		Assert.assertEquals(
			ancestorsCount,
			_nestedSetsTreeManager.countAncestors(assetCategory));
	}

	protected void assertCountChildren(
		long childrenCount, AssetCategory assetCategory) {

		Assert.assertEquals(
			childrenCount,
			_nestedSetsTreeManager.countDescendants(assetCategory));
	}

	protected void assertGetAncestors(
		AssetCategory assetCategory, AssetCategory... ancestorAssetCategories) {

		List<AssetCategory> expectedAssetCategories =
			new ArrayList<AssetCategory>(
				Arrays.asList(ancestorAssetCategories));

		expectedAssetCategories.add(assetCategory);

		Collections.sort(expectedAssetCategories);

		List<AssetCategory> actualAssetCategories =
			new ArrayList<AssetCategory>(
				_nestedSetsTreeManager.getAncestors(assetCategory));

		Collections.sort(actualAssetCategories);

		Assert.assertEquals(expectedAssetCategories, actualAssetCategories);
	}

	protected void assertGetDescendants(
		AssetCategory assetCategory, AssetCategory... childAssetCategories) {

		List<AssetCategory> expectedAssetCategories =
			new ArrayList<AssetCategory>(Arrays.asList(childAssetCategories));

		expectedAssetCategories.add(assetCategory);

		Collections.sort(expectedAssetCategories);

		List<AssetCategory> actualAssetCategories =
			new ArrayList<AssetCategory>(
				_nestedSetsTreeManager.getDescendants(assetCategory));

		Collections.sort(actualAssetCategories);

		Assert.assertEquals(expectedAssetCategories, actualAssetCategories);
	}

	protected void assertLeftAndRight(
		AssetCategory assetCategory, long leftCategoryId,
		long rightCategoryId) {

		Assert.assertEquals(leftCategoryId, assetCategory.getLeftCategoryId());
		Assert.assertEquals(
			rightCategoryId, assetCategory.getRightCategoryId());

		_assetCategoryPersistence.update(assetCategory);
	}

	protected void synchronizeAssetCategories(AssetCategory assetCategory) {
		synchronizeAssetCategories(assetCategory, false);
	}

	protected void synchronizeAssetCategories(
		AssetCategory assetCategory, boolean delete) {

		if (assetCategory != null) {
			if (delete) {
				_assetCategoryPersistence.remove(assetCategory);
			}
			else {
				_assetCategoryPersistence.update(assetCategory);
			}
		}

		_assetCategoryPersistence.clearCache();

		for (int i = 0; i < _assetCategories.length; i++) {
			assetCategory = _assetCategories[i];

			if (assetCategory != null) {
				_assetCategories[i] =
					_assetCategoryPersistence.fetchByPrimaryKey(
						assetCategory.getCategoryId());
			}
		}
	}

	private AssetCategory[] _assetCategories;
	private AssetCategoryPersistence _assetCategoryPersistence;
	private AssetVocabulary _assetVocabulary;
	private Group _group;
	private NestedSetsTreeManager<AssetCategory> _nestedSetsTreeManager;
	private SessionFactoryInvocationHandler _sessionFactoryInvocationHandler;

	private static class SessionFactoryInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("openSession") && _failOpenSession) {
				throw new Exception("Unable to open session");
			}

			return method.invoke(_target, args);
		}

		public void setFailOpenSession(boolean failOpenSession) {
			_failOpenSession = failOpenSession;
		}

		private SessionFactoryInvocationHandler(Object target) {
			_target = target;
		}

		private boolean _failOpenSession;
		private Object _target;

	}

}