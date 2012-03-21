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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchActivitySettingException;
import com.liferay.portlet.social.model.SocialActivitySetting;
import com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SocialActivitySettingPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (SocialActivitySettingPersistence)PortalBeanLocatorUtil.locate(SocialActivitySettingPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivitySetting socialActivitySetting = _persistence.create(pk);

		Assert.assertNotNull(socialActivitySetting);

		Assert.assertEquals(socialActivitySetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		_persistence.remove(newSocialActivitySetting);

		SocialActivitySetting existingSocialActivitySetting = _persistence.fetchByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertNull(existingSocialActivitySetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivitySetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivitySetting newSocialActivitySetting = _persistence.create(pk);

		newSocialActivitySetting.setGroupId(ServiceTestUtil.nextLong());

		newSocialActivitySetting.setCompanyId(ServiceTestUtil.nextLong());

		newSocialActivitySetting.setClassNameId(ServiceTestUtil.nextLong());

		newSocialActivitySetting.setActivityType(ServiceTestUtil.nextInt());

		newSocialActivitySetting.setName(ServiceTestUtil.randomString());

		newSocialActivitySetting.setValue(ServiceTestUtil.randomString());

		_persistence.update(newSocialActivitySetting, false);

		SocialActivitySetting existingSocialActivitySetting = _persistence.findByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySetting.getActivitySettingId(),
			newSocialActivitySetting.getActivitySettingId());
		Assert.assertEquals(existingSocialActivitySetting.getGroupId(),
			newSocialActivitySetting.getGroupId());
		Assert.assertEquals(existingSocialActivitySetting.getCompanyId(),
			newSocialActivitySetting.getCompanyId());
		Assert.assertEquals(existingSocialActivitySetting.getClassNameId(),
			newSocialActivitySetting.getClassNameId());
		Assert.assertEquals(existingSocialActivitySetting.getActivityType(),
			newSocialActivitySetting.getActivityType());
		Assert.assertEquals(existingSocialActivitySetting.getName(),
			newSocialActivitySetting.getName());
		Assert.assertEquals(existingSocialActivitySetting.getValue(),
			newSocialActivitySetting.getValue());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		SocialActivitySetting existingSocialActivitySetting = _persistence.findByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySetting,
			newSocialActivitySetting);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivitySettingException");
		}
		catch (NoSuchActivitySettingException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		SocialActivitySetting existingSocialActivitySetting = _persistence.fetchByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySetting,
			newSocialActivitySetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivitySetting missingSocialActivitySetting = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivitySetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activitySettingId",
				newSocialActivitySetting.getActivitySettingId()));

		List<SocialActivitySetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivitySetting existingSocialActivitySetting = result.get(0);

		Assert.assertEquals(existingSocialActivitySetting,
			newSocialActivitySetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activitySettingId",
				ServiceTestUtil.nextLong()));

		List<SocialActivitySetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activitySettingId"));

		Object newActivitySettingId = newSocialActivitySetting.getActivitySettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activitySettingId",
				new Object[] { newActivitySettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivitySettingId = result.get(0);

		Assert.assertEquals(existingActivitySettingId, newActivitySettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivitySetting.class,
				SocialActivitySetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activitySettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activitySettingId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivitySetting newSocialActivitySetting = addSocialActivitySetting();

		_persistence.clearCache();

		SocialActivitySettingModelImpl existingSocialActivitySettingModelImpl = (SocialActivitySettingModelImpl)_persistence.findByPrimaryKey(newSocialActivitySetting.getPrimaryKey());

		Assert.assertEquals(existingSocialActivitySettingModelImpl.getGroupId(),
			existingSocialActivitySettingModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivitySettingModelImpl.getClassNameId(),
			existingSocialActivitySettingModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivitySettingModelImpl.getActivityType(),
			existingSocialActivitySettingModelImpl.getOriginalActivityType());
		Assert.assertTrue(Validator.equals(
				existingSocialActivitySettingModelImpl.getName(),
				existingSocialActivitySettingModelImpl.getOriginalName()));
	}

	protected SocialActivitySetting addSocialActivitySetting()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivitySetting socialActivitySetting = _persistence.create(pk);

		socialActivitySetting.setGroupId(ServiceTestUtil.nextLong());

		socialActivitySetting.setCompanyId(ServiceTestUtil.nextLong());

		socialActivitySetting.setClassNameId(ServiceTestUtil.nextLong());

		socialActivitySetting.setActivityType(ServiceTestUtil.nextInt());

		socialActivitySetting.setName(ServiceTestUtil.randomString());

		socialActivitySetting.setValue(ServiceTestUtil.randomString());

		_persistence.update(socialActivitySetting, false);

		return socialActivitySetting;
	}

	private SocialActivitySettingPersistence _persistence;
}