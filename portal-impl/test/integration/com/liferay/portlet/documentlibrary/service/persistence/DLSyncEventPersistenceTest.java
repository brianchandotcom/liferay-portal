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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchSyncEventException;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.model.impl.DLSyncEventModelImpl;

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
public class DLSyncEventPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DLSyncEvent> modelListener : _modelListeners) {
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

		for (ModelListener<DLSyncEvent> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSyncEvent dlSyncEvent = _persistence.create(pk);

		Assert.assertNotNull(dlSyncEvent);

		Assert.assertEquals(dlSyncEvent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		_persistence.remove(newDLSyncEvent);

		DLSyncEvent existingDLSyncEvent = _persistence.fetchByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertNull(existingDLSyncEvent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLSyncEvent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSyncEvent newDLSyncEvent = _persistence.create(pk);

		newDLSyncEvent.setModifiedTime(ServiceTestUtil.nextLong());

		newDLSyncEvent.setEvent(ServiceTestUtil.randomString());

		newDLSyncEvent.setType(ServiceTestUtil.randomString());

		newDLSyncEvent.setTypePK(ServiceTestUtil.nextLong());

		_persistence.update(newDLSyncEvent);

		DLSyncEvent existingDLSyncEvent = _persistence.findByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent.getSyncEventId(),
			newDLSyncEvent.getSyncEventId());
		Assert.assertEquals(existingDLSyncEvent.getModifiedTime(),
			newDLSyncEvent.getModifiedTime());
		Assert.assertEquals(existingDLSyncEvent.getEvent(),
			newDLSyncEvent.getEvent());
		Assert.assertEquals(existingDLSyncEvent.getType(),
			newDLSyncEvent.getType());
		Assert.assertEquals(existingDLSyncEvent.getTypePK(),
			newDLSyncEvent.getTypePK());
	}

	@Test
	public void testCountByModifiedTime() {
		try {
			_persistence.countByModifiedTime(ServiceTestUtil.nextLong());

			_persistence.countByModifiedTime(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByTypePK() {
		try {
			_persistence.countByTypePK(ServiceTestUtil.nextLong());

			_persistence.countByTypePK(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DLSyncEvent existingDLSyncEvent = _persistence.findByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent, newDLSyncEvent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchSyncEventException");
		}
		catch (NoSuchSyncEventException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DLSyncEvent",
			"syncEventId", true, "modifiedTime", true, "event", true, "type",
			true, "typePK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DLSyncEvent existingDLSyncEvent = _persistence.fetchByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEvent, newDLSyncEvent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSyncEvent missingDLSyncEvent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLSyncEvent);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DLSyncEventActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DLSyncEvent dlSyncEvent = (DLSyncEvent)object;

					Assert.assertNotNull(dlSyncEvent);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				DLSyncEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncEventId",
				newDLSyncEvent.getSyncEventId()));

		List<DLSyncEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLSyncEvent existingDLSyncEvent = result.get(0);

		Assert.assertEquals(existingDLSyncEvent, newDLSyncEvent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				DLSyncEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("syncEventId",
				ServiceTestUtil.nextLong()));

		List<DLSyncEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				DLSyncEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("syncEventId"));

		Object newSyncEventId = newDLSyncEvent.getSyncEventId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncEventId",
				new Object[] { newSyncEventId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSyncEventId = result.get(0);

		Assert.assertEquals(existingSyncEventId, newSyncEventId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLSyncEvent.class,
				DLSyncEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("syncEventId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("syncEventId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLSyncEvent newDLSyncEvent = addDLSyncEvent();

		_persistence.clearCache();

		DLSyncEventModelImpl existingDLSyncEventModelImpl = (DLSyncEventModelImpl)_persistence.findByPrimaryKey(newDLSyncEvent.getPrimaryKey());

		Assert.assertEquals(existingDLSyncEventModelImpl.getTypePK(),
			existingDLSyncEventModelImpl.getOriginalTypePK());
	}

	protected DLSyncEvent addDLSyncEvent() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLSyncEvent dlSyncEvent = _persistence.create(pk);

		dlSyncEvent.setModifiedTime(ServiceTestUtil.nextLong());

		dlSyncEvent.setEvent(ServiceTestUtil.randomString());

		dlSyncEvent.setType(ServiceTestUtil.randomString());

		dlSyncEvent.setTypePK(ServiceTestUtil.nextLong());

		_persistence.update(dlSyncEvent);

		return dlSyncEvent;
	}

	private static Log _log = LogFactoryUtil.getLog(DLSyncEventPersistenceTest.class);
	private ModelListener<DLSyncEvent>[] _modelListeners;
	private DLSyncEventPersistence _persistence = (DLSyncEventPersistence)PortalBeanLocatorUtil.locate(DLSyncEventPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}