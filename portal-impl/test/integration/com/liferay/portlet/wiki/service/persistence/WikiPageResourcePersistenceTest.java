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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl;

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
public class WikiPageResourcePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<WikiPageResource> modelListener : _modelListeners) {
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

		for (ModelListener<WikiPageResource> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPageResource wikiPageResource = _persistence.create(pk);

		Assert.assertNotNull(wikiPageResource);

		Assert.assertEquals(wikiPageResource.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		_persistence.remove(newWikiPageResource);

		WikiPageResource existingWikiPageResource = _persistence.fetchByPrimaryKey(newWikiPageResource.getPrimaryKey());

		Assert.assertNull(existingWikiPageResource);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWikiPageResource();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPageResource newWikiPageResource = _persistence.create(pk);

		newWikiPageResource.setUuid(ServiceTestUtil.randomString());

		newWikiPageResource.setNodeId(ServiceTestUtil.nextLong());

		newWikiPageResource.setTitle(ServiceTestUtil.randomString());

		_persistence.update(newWikiPageResource);

		WikiPageResource existingWikiPageResource = _persistence.findByPrimaryKey(newWikiPageResource.getPrimaryKey());

		Assert.assertEquals(existingWikiPageResource.getUuid(),
			newWikiPageResource.getUuid());
		Assert.assertEquals(existingWikiPageResource.getResourcePrimKey(),
			newWikiPageResource.getResourcePrimKey());
		Assert.assertEquals(existingWikiPageResource.getNodeId(),
			newWikiPageResource.getNodeId());
		Assert.assertEquals(existingWikiPageResource.getTitle(),
			newWikiPageResource.getTitle());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		WikiPageResource existingWikiPageResource = _persistence.findByPrimaryKey(newWikiPageResource.getPrimaryKey());

		Assert.assertEquals(existingWikiPageResource, newWikiPageResource);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPageResourceException");
		}
		catch (NoSuchPageResourceException nsee) {
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
		return OrderByComparatorFactoryUtil.create("WikiPageResource", "uuid",
			true, "resourcePrimKey", true, "nodeId", true, "title", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		WikiPageResource existingWikiPageResource = _persistence.fetchByPrimaryKey(newWikiPageResource.getPrimaryKey());

		Assert.assertEquals(existingWikiPageResource, newWikiPageResource);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPageResource missingWikiPageResource = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWikiPageResource);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new WikiPageResourceActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					WikiPageResource wikiPageResource = (WikiPageResource)object;

					Assert.assertNotNull(wikiPageResource);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePrimKey",
				newWikiPageResource.getResourcePrimKey()));

		List<WikiPageResource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WikiPageResource existingWikiPageResource = result.get(0);

		Assert.assertEquals(existingWikiPageResource, newWikiPageResource);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePrimKey",
				ServiceTestUtil.nextLong()));

		List<WikiPageResource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePrimKey"));

		Object newResourcePrimKey = newWikiPageResource.getResourcePrimKey();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePrimKey",
				new Object[] { newResourcePrimKey }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourcePrimKey = result.get(0);

		Assert.assertEquals(existingResourcePrimKey, newResourcePrimKey);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePrimKey"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePrimKey",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WikiPageResource newWikiPageResource = addWikiPageResource();

		_persistence.clearCache();

		WikiPageResourceModelImpl existingWikiPageResourceModelImpl = (WikiPageResourceModelImpl)_persistence.findByPrimaryKey(newWikiPageResource.getPrimaryKey());

		Assert.assertEquals(existingWikiPageResourceModelImpl.getNodeId(),
			existingWikiPageResourceModelImpl.getOriginalNodeId());
		Assert.assertTrue(Validator.equals(
				existingWikiPageResourceModelImpl.getTitle(),
				existingWikiPageResourceModelImpl.getOriginalTitle()));
	}

	protected WikiPageResource addWikiPageResource() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPageResource wikiPageResource = _persistence.create(pk);

		wikiPageResource.setUuid(ServiceTestUtil.randomString());

		wikiPageResource.setNodeId(ServiceTestUtil.nextLong());

		wikiPageResource.setTitle(ServiceTestUtil.randomString());

		_persistence.update(wikiPageResource);

		return wikiPageResource;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageResourcePersistenceTest.class);
	private ModelListener<WikiPageResource>[] _modelListeners;
	private WikiPageResourcePersistence _persistence = (WikiPageResourcePersistence)PortalBeanLocatorUtil.locate(WikiPageResourcePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}