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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.announcements.NoSuchDeliveryException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsDeliveryPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AnnouncementsDeliveryPersistence)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery announcementsDelivery = _persistence.create(pk);

		assertNotNull(announcementsDelivery);

		assertEquals(announcementsDelivery.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		_persistence.remove(newAnnouncementsDelivery);

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.fetchByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertNull(existingAnnouncementsDelivery);
	}

	public void testUpdateNew() throws Exception {
		addAnnouncementsDelivery();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery newAnnouncementsDelivery = _persistence.create(pk);

		newAnnouncementsDelivery.setCompanyId(nextLong());

		newAnnouncementsDelivery.setUserId(nextLong());

		newAnnouncementsDelivery.setType(randomString());

		newAnnouncementsDelivery.setEmail(randomBoolean());

		newAnnouncementsDelivery.setSms(randomBoolean());

		newAnnouncementsDelivery.setWebsite(randomBoolean());

		_persistence.update(newAnnouncementsDelivery, false);

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.findByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDelivery.getDeliveryId(),
			newAnnouncementsDelivery.getDeliveryId());
		assertEquals(existingAnnouncementsDelivery.getCompanyId(),
			newAnnouncementsDelivery.getCompanyId());
		assertEquals(existingAnnouncementsDelivery.getUserId(),
			newAnnouncementsDelivery.getUserId());
		assertEquals(existingAnnouncementsDelivery.getType(),
			newAnnouncementsDelivery.getType());
		assertEquals(existingAnnouncementsDelivery.getEmail(),
			newAnnouncementsDelivery.getEmail());
		assertEquals(existingAnnouncementsDelivery.getSms(),
			newAnnouncementsDelivery.getSms());
		assertEquals(existingAnnouncementsDelivery.getWebsite(),
			newAnnouncementsDelivery.getWebsite());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.findByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDelivery, newAnnouncementsDelivery);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchDeliveryException");
		}
		catch (NoSuchDeliveryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		AnnouncementsDelivery existingAnnouncementsDelivery = _persistence.fetchByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDelivery, newAnnouncementsDelivery);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery missingAnnouncementsDelivery = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAnnouncementsDelivery);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsDelivery.class,
				AnnouncementsDelivery.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deliveryId",
				newAnnouncementsDelivery.getDeliveryId()));

		List<AnnouncementsDelivery> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AnnouncementsDelivery existingAnnouncementsDelivery = result.get(0);

		assertEquals(existingAnnouncementsDelivery, newAnnouncementsDelivery);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsDelivery.class,
				AnnouncementsDelivery.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("deliveryId", nextLong()));

		List<AnnouncementsDelivery> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsDelivery.class,
				AnnouncementsDelivery.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("deliveryId"));

		Object newDeliveryId = newAnnouncementsDelivery.getDeliveryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("deliveryId",
				new Object[] { newDeliveryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingDeliveryId = result.get(0);

		assertEquals(existingDeliveryId, newDeliveryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsDelivery.class,
				AnnouncementsDelivery.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("deliveryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("deliveryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AnnouncementsDelivery newAnnouncementsDelivery = addAnnouncementsDelivery();

		_persistence.clearCache();

		AnnouncementsDeliveryModelImpl existingAnnouncementsDeliveryModelImpl = (AnnouncementsDeliveryModelImpl)_persistence.findByPrimaryKey(newAnnouncementsDelivery.getPrimaryKey());

		assertEquals(existingAnnouncementsDeliveryModelImpl.getUserId(),
			existingAnnouncementsDeliveryModelImpl.getOriginalUserId());
		assertTrue(Validator.equals(
				existingAnnouncementsDeliveryModelImpl.getType(),
				existingAnnouncementsDeliveryModelImpl.getOriginalType()));
	}

	protected AnnouncementsDelivery addAnnouncementsDelivery()
		throws Exception {
		long pk = nextLong();

		AnnouncementsDelivery announcementsDelivery = _persistence.create(pk);

		announcementsDelivery.setCompanyId(nextLong());

		announcementsDelivery.setUserId(nextLong());

		announcementsDelivery.setType(randomString());

		announcementsDelivery.setEmail(randomBoolean());

		announcementsDelivery.setSms(randomBoolean());

		announcementsDelivery.setWebsite(randomBoolean());

		_persistence.update(announcementsDelivery, false);

		return announcementsDelivery;
	}

	private AnnouncementsDeliveryPersistence _persistence;
}