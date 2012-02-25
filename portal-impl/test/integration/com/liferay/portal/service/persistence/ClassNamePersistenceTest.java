/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.impl.ClassNameModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassNamePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ClassNamePersistence)PortalBeanLocatorUtil.locate(ClassNamePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ClassName className = _persistence.create(pk);

		assertNotNull(className);

		assertEquals(className.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ClassName newClassName = addClassName();

		_persistence.remove(newClassName);

		ClassName existingClassName = _persistence.fetchByPrimaryKey(newClassName.getPrimaryKey());

		assertNull(existingClassName);
	}

	public void testUpdateNew() throws Exception {
		addClassName();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ClassName newClassName = _persistence.create(pk);

		newClassName.setValue(randomString());

		_persistence.update(newClassName, false);

		ClassName existingClassName = _persistence.findByPrimaryKey(newClassName.getPrimaryKey());

		assertEquals(existingClassName.getClassNameId(),
			newClassName.getClassNameId());
		assertEquals(existingClassName.getValue(), newClassName.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ClassName newClassName = addClassName();

		ClassName existingClassName = _persistence.findByPrimaryKey(newClassName.getPrimaryKey());

		assertEquals(existingClassName, newClassName);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchClassNameException");
		}
		catch (NoSuchClassNameException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ClassName newClassName = addClassName();

		ClassName existingClassName = _persistence.fetchByPrimaryKey(newClassName.getPrimaryKey());

		assertEquals(existingClassName, newClassName);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ClassName missingClassName = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingClassName);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ClassName newClassName = addClassName();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("classNameId",
				newClassName.getClassNameId()));

		List<ClassName> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ClassName existingClassName = result.get(0);

		assertEquals(existingClassName, newClassName);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("classNameId", nextLong()));

		List<ClassName> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingClassNameId = result.get(0);

		assertEquals(existingClassNameId, newClassNameId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClassName.class,
				ClassName.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("classNameId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("classNameId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ClassName newClassName = addClassName();

		_persistence.clearCache();

		ClassNameModelImpl existingClassNameModelImpl = (ClassNameModelImpl)_persistence.findByPrimaryKey(newClassName.getPrimaryKey());

		assertTrue(Validator.equals(existingClassNameModelImpl.getValue(),
				existingClassNameModelImpl.getOriginalValue()));
	}

	protected ClassName addClassName() throws Exception {
		long pk = nextLong();

		ClassName className = _persistence.create(pk);

		className.setValue(randomString());

		_persistence.update(className, false);

		return className;
	}

	private ClassNamePersistence _persistence;
}