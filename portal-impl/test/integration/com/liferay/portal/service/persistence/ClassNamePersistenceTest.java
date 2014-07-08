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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchClassNameException;
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
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ClassNameModelImpl;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ClassNamePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ClassName> modelListener : _modelListeners) {
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

		for (ModelListener<ClassName> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClassName className = _persistence.create(pk);

		Assert.assertNotNull(className);

		Assert.assertEquals(className.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ClassName newClassName = addClassName();

		_persistence.remove(newClassName);

		ClassName existingClassName = _persistence.fetchByPrimaryKey(newClassName.getPrimaryKey());

		Assert.assertNull(existingClassName);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addClassName();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClassName newClassName = _persistence.create(pk);

		newClassName.setMvccVersion(RandomTestUtil.nextLong());

		newClassName.setValue(RandomTestUtil.randomString());

		_persistence.update(newClassName);

		ClassName existingClassName = _persistence.findByPrimaryKey(newClassName.getPrimaryKey());

		Assert.assertEquals(existingClassName.getMvccVersion(),
			newClassName.getMvccVersion());
		Assert.assertEquals(existingClassName.getClassNameId(),
			newClassName.getClassNameId());
		Assert.assertEquals(existingClassName.getValue(),
			newClassName.getValue());
	}

	@Test
	public void testCountByValue() {
		try {
			_persistence.countByValue(StringPool.BLANK);

			_persistence.countByValue(StringPool.NULL);

			_persistence.countByValue((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ClassName newClassName = addClassName();

		ClassName existingClassName = _persistence.findByPrimaryKey(newClassName.getPrimaryKey());

		Assert.assertEquals(existingClassName, newClassName);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchClassNameException");
		}
		catch (NoSuchClassNameException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ClassName_", "mvccVersion",
			true, "classNameId", true, "value", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ClassName newClassName = addClassName();

		ClassName existingClassName = _persistence.fetchByPrimaryKey(newClassName.getPrimaryKey());

		Assert.assertEquals(existingClassName, newClassName);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClassName missingClassName = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingClassName);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ClassName newClassName1 = addClassName();
		ClassName newClassName2 = addClassName();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClassName1.getPrimaryKey());
		primaryKeys.add(newClassName2.getPrimaryKey());

		Map<Serializable, ClassName> classNames = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, classNames.size());
		Assert.assertEquals(newClassName1,
			classNames.get(newClassName1.getPrimaryKey()));
		Assert.assertEquals(newClassName2,
			classNames.get(newClassName2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ClassName> classNames = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(classNames.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ClassName newClassName = addClassName();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClassName.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ClassName> classNames = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, classNames.size());
		Assert.assertEquals(newClassName,
			classNames.get(newClassName.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ClassName> classNames = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(classNames.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ClassName newClassName = addClassName();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClassName.getPrimaryKey());

		Map<Serializable, ClassName> classNames = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, classNames.size());
		Assert.assertEquals(newClassName,
			classNames.get(newClassName.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ClassNameLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ClassName className = (ClassName)object;

					Assert.assertNotNull(className);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ClassName newClassName = addClassName();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("classNameId",
				newClassName.getClassNameId()));

		List<ClassName> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ClassName existingClassName = result.get(0);

		Assert.assertEquals(existingClassName, newClassName);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("classNameId",
				RandomTestUtil.nextLong()));

		List<ClassName> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ClassName newClassName = addClassName();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("classNameId"));

		Object newClassNameId = newClassName.getClassNameId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("classNameId",
				new Object[] { newClassNameId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClassNameId = result.get(0);

		Assert.assertEquals(existingClassNameId, newClassNameId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("classNameId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("classNameId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ClassName newClassName = addClassName();

		_persistence.clearCache();

		ClassNameModelImpl existingClassNameModelImpl = (ClassNameModelImpl)_persistence.findByPrimaryKey(newClassName.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingClassNameModelImpl.getValue(),
				existingClassNameModelImpl.getOriginalValue()));
	}

	protected ClassName addClassName() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClassName className = _persistence.create(pk);

		className.setMvccVersion(RandomTestUtil.nextLong());

		className.setValue(RandomTestUtil.randomString());

		_persistence.update(className);

		return className;
	}

	private static Log _log = LogFactoryUtil.getLog(ClassNamePersistenceTest.class);
	private ModelListener<ClassName>[] _modelListeners;
	private ClassNamePersistence _persistence = (ClassNamePersistence)PortalBeanLocatorUtil.locate(ClassNamePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}