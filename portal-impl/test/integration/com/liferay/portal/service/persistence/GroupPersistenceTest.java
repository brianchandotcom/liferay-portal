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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (GroupPersistence)PortalBeanLocatorUtil.locate(GroupPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Group group = _persistence.create(pk);

		assertNotNull(group);

		assertEquals(group.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Group newGroup = addGroup();

		_persistence.remove(newGroup);

		Group existingGroup = _persistence.fetchByPrimaryKey(newGroup.getPrimaryKey());

		assertNull(existingGroup);
	}

	public void testUpdateNew() throws Exception {
		addGroup();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Group newGroup = _persistence.create(pk);

		newGroup.setCompanyId(nextLong());

		newGroup.setCreatorUserId(nextLong());

		newGroup.setClassNameId(nextLong());

		newGroup.setClassPK(nextLong());

		newGroup.setParentGroupId(nextLong());

		newGroup.setLiveGroupId(nextLong());

		newGroup.setName(randomString());

		newGroup.setDescription(randomString());

		newGroup.setType(nextInt());

		newGroup.setTypeSettings(randomString());

		newGroup.setFriendlyURL(randomString());

		newGroup.setSite(randomBoolean());

		newGroup.setActive(randomBoolean());

		_persistence.update(newGroup, false);

		Group existingGroup = _persistence.findByPrimaryKey(newGroup.getPrimaryKey());

		assertEquals(existingGroup.getGroupId(), newGroup.getGroupId());
		assertEquals(existingGroup.getCompanyId(), newGroup.getCompanyId());
		assertEquals(existingGroup.getCreatorUserId(),
			newGroup.getCreatorUserId());
		assertEquals(existingGroup.getClassNameId(), newGroup.getClassNameId());
		assertEquals(existingGroup.getClassPK(), newGroup.getClassPK());
		assertEquals(existingGroup.getParentGroupId(),
			newGroup.getParentGroupId());
		assertEquals(existingGroup.getLiveGroupId(), newGroup.getLiveGroupId());
		assertEquals(existingGroup.getName(), newGroup.getName());
		assertEquals(existingGroup.getDescription(), newGroup.getDescription());
		assertEquals(existingGroup.getType(), newGroup.getType());
		assertEquals(existingGroup.getTypeSettings(), newGroup.getTypeSettings());
		assertEquals(existingGroup.getFriendlyURL(), newGroup.getFriendlyURL());
		assertEquals(existingGroup.getSite(), newGroup.getSite());
		assertEquals(existingGroup.getActive(), newGroup.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Group newGroup = addGroup();

		Group existingGroup = _persistence.findByPrimaryKey(newGroup.getPrimaryKey());

		assertEquals(existingGroup, newGroup);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchGroupException");
		}
		catch (NoSuchGroupException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Group newGroup = addGroup();

		Group existingGroup = _persistence.fetchByPrimaryKey(newGroup.getPrimaryKey());

		assertEquals(existingGroup, newGroup);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Group missingGroup = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingGroup);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Group newGroup = addGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId",
				newGroup.getGroupId()));

		List<Group> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Group existingGroup = result.get(0);

		assertEquals(existingGroup, newGroup);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", nextLong()));

		List<Group> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Group newGroup = addGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("groupId"));

		Object newGroupId = newGroup.getGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("groupId",
				new Object[] { newGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingGroupId = result.get(0);

		assertEquals(existingGroupId, newGroupId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class,
				Group.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("groupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("groupId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Group newGroup = addGroup();

		_persistence.clearCache();

		GroupModelImpl existingGroupModelImpl = (GroupModelImpl)_persistence.findByPrimaryKey(newGroup.getPrimaryKey());

		assertEquals(existingGroupModelImpl.getLiveGroupId(),
			existingGroupModelImpl.getOriginalLiveGroupId());

		assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingGroupModelImpl.getName(),
				existingGroupModelImpl.getOriginalName()));

		assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingGroupModelImpl.getFriendlyURL(),
				existingGroupModelImpl.getOriginalFriendlyURL()));

		assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		assertEquals(existingGroupModelImpl.getClassNameId(),
			existingGroupModelImpl.getOriginalClassNameId());
		assertEquals(existingGroupModelImpl.getClassPK(),
			existingGroupModelImpl.getOriginalClassPK());

		assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		assertEquals(existingGroupModelImpl.getLiveGroupId(),
			existingGroupModelImpl.getOriginalLiveGroupId());
		assertTrue(Validator.equals(existingGroupModelImpl.getName(),
				existingGroupModelImpl.getOriginalName()));

		assertEquals(existingGroupModelImpl.getCompanyId(),
			existingGroupModelImpl.getOriginalCompanyId());
		assertEquals(existingGroupModelImpl.getClassNameId(),
			existingGroupModelImpl.getOriginalClassNameId());
		assertEquals(existingGroupModelImpl.getLiveGroupId(),
			existingGroupModelImpl.getOriginalLiveGroupId());
		assertTrue(Validator.equals(existingGroupModelImpl.getName(),
				existingGroupModelImpl.getOriginalName()));
	}

	protected Group addGroup() throws Exception {
		long pk = nextLong();

		Group group = _persistence.create(pk);

		group.setCompanyId(nextLong());

		group.setCreatorUserId(nextLong());

		group.setClassNameId(nextLong());

		group.setClassPK(nextLong());

		group.setParentGroupId(nextLong());

		group.setLiveGroupId(nextLong());

		group.setName(randomString());

		group.setDescription(randomString());

		group.setType(nextInt());

		group.setTypeSettings(randomString());

		group.setFriendlyURL(randomString());

		group.setSite(randomBoolean());

		group.setActive(randomBoolean());

		_persistence.update(group, false);

		return group;
	}

	private GroupPersistence _persistence;
}