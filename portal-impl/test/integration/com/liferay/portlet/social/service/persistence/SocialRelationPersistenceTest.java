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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialRelationPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialRelationPersistence)PortalBeanLocatorUtil.locate(SocialRelationPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		assertNotNull(socialRelation);

		assertEquals(socialRelation.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.remove(newSocialRelation);

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertNull(existingSocialRelation);
	}

	public void testUpdateNew() throws Exception {
		addSocialRelation();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialRelation newSocialRelation = _persistence.create(pk);

		newSocialRelation.setUuid(randomString());

		newSocialRelation.setCompanyId(nextLong());

		newSocialRelation.setCreateDate(nextLong());

		newSocialRelation.setUserId1(nextLong());

		newSocialRelation.setUserId2(nextLong());

		newSocialRelation.setType(nextInt());

		_persistence.update(newSocialRelation, false);

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelation.getUuid(),
			newSocialRelation.getUuid());
		assertEquals(existingSocialRelation.getRelationId(),
			newSocialRelation.getRelationId());
		assertEquals(existingSocialRelation.getCompanyId(),
			newSocialRelation.getCompanyId());
		assertEquals(existingSocialRelation.getCreateDate(),
			newSocialRelation.getCreateDate());
		assertEquals(existingSocialRelation.getUserId1(),
			newSocialRelation.getUserId1());
		assertEquals(existingSocialRelation.getUserId2(),
			newSocialRelation.getUserId2());
		assertEquals(existingSocialRelation.getType(),
			newSocialRelation.getType());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelation, newSocialRelation);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRelationException");
		}
		catch (NoSuchRelationException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelation, newSocialRelation);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialRelation missingSocialRelation = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialRelation);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId",
				newSocialRelation.getRelationId()));

		List<SocialRelation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialRelation existingSocialRelation = result.get(0);

		assertEquals(existingSocialRelation, newSocialRelation);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId", nextLong()));

		List<SocialRelation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("relationId"));

		Object newRelationId = newSocialRelation.getRelationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("relationId",
				new Object[] { newRelationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRelationId = result.get(0);

		assertEquals(existingRelationId, newRelationId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("relationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("relationId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.clearCache();

		SocialRelationModelImpl existingSocialRelationModelImpl = (SocialRelationModelImpl)_persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelationModelImpl.getUserId1(),
			existingSocialRelationModelImpl.getOriginalUserId1());
		assertEquals(existingSocialRelationModelImpl.getUserId2(),
			existingSocialRelationModelImpl.getOriginalUserId2());
		assertEquals(existingSocialRelationModelImpl.getType(),
			existingSocialRelationModelImpl.getOriginalType());
	}

	protected SocialRelation addSocialRelation() throws Exception {
		long pk = nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		socialRelation.setUuid(randomString());

		socialRelation.setCompanyId(nextLong());

		socialRelation.setCreateDate(nextLong());

		socialRelation.setUserId1(nextLong());

		socialRelation.setUserId2(nextLong());

		socialRelation.setType(nextInt());

		_persistence.update(socialRelation, false);

		return socialRelation;
	}

	private SocialRelationPersistence _persistence;
}