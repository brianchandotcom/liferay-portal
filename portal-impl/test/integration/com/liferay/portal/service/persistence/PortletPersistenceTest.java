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

import com.liferay.portal.NoSuchPortletException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PortletPersistence)PortalBeanLocatorUtil.locate(PortletPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Portlet portlet = _persistence.create(pk);

		assertNotNull(portlet);

		assertEquals(portlet.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Portlet newPortlet = addPortlet();

		_persistence.remove(newPortlet);

		Portlet existingPortlet = _persistence.fetchByPrimaryKey(newPortlet.getPrimaryKey());

		assertNull(existingPortlet);
	}

	public void testUpdateNew() throws Exception {
		addPortlet();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Portlet newPortlet = _persistence.create(pk);

		newPortlet.setCompanyId(nextLong());

		newPortlet.setPortletId(randomString());

		newPortlet.setRoles(randomString());

		newPortlet.setActive(randomBoolean());

		_persistence.update(newPortlet, false);

		Portlet existingPortlet = _persistence.findByPrimaryKey(newPortlet.getPrimaryKey());

		assertEquals(existingPortlet.getId(), newPortlet.getId());
		assertEquals(existingPortlet.getCompanyId(), newPortlet.getCompanyId());
		assertEquals(existingPortlet.getPortletId(), newPortlet.getPortletId());
		assertEquals(existingPortlet.getRoles(), newPortlet.getRoles());
		assertEquals(existingPortlet.getActive(), newPortlet.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Portlet newPortlet = addPortlet();

		Portlet existingPortlet = _persistence.findByPrimaryKey(newPortlet.getPrimaryKey());

		assertEquals(existingPortlet, newPortlet);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPortletException");
		}
		catch (NoSuchPortletException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Portlet newPortlet = addPortlet();

		Portlet existingPortlet = _persistence.fetchByPrimaryKey(newPortlet.getPrimaryKey());

		assertEquals(existingPortlet, newPortlet);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Portlet missingPortlet = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPortlet);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Portlet newPortlet = addPortlet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", newPortlet.getId()));

		List<Portlet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Portlet existingPortlet = result.get(0);

		assertEquals(existingPortlet, newPortlet);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<Portlet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Portlet newPortlet = addPortlet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newPortlet.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingId = result.get(0);

		assertEquals(existingId, newId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portlet.class,
				Portlet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Portlet newPortlet = addPortlet();

		_persistence.clearCache();

		PortletModelImpl existingPortletModelImpl = (PortletModelImpl)_persistence.findByPrimaryKey(newPortlet.getPrimaryKey());

		assertEquals(existingPortletModelImpl.getCompanyId(),
			existingPortletModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingPortletModelImpl.getPortletId(),
				existingPortletModelImpl.getOriginalPortletId()));
	}

	protected Portlet addPortlet() throws Exception {
		long pk = nextLong();

		Portlet portlet = _persistence.create(pk);

		portlet.setCompanyId(nextLong());

		portlet.setPortletId(randomString());

		portlet.setRoles(randomString());

		portlet.setActive(randomBoolean());

		_persistence.update(portlet, false);

		return portlet;
	}

	private PortletPersistence _persistence;
}