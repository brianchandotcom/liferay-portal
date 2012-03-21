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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;

import com.liferay.portlet.softwarecatalog.NoSuchLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;

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
public class SCLicensePersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (SCLicensePersistence)PortalBeanLocatorUtil.locate(SCLicensePersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCLicense scLicense = _persistence.create(pk);

		Assert.assertNotNull(scLicense);

		Assert.assertEquals(scLicense.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCLicense newSCLicense = addSCLicense();

		_persistence.remove(newSCLicense);

		SCLicense existingSCLicense = _persistence.fetchByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertNull(existingSCLicense);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCLicense();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCLicense newSCLicense = _persistence.create(pk);

		newSCLicense.setName(ServiceTestUtil.randomString());

		newSCLicense.setUrl(ServiceTestUtil.randomString());

		newSCLicense.setOpenSource(ServiceTestUtil.randomBoolean());

		newSCLicense.setActive(ServiceTestUtil.randomBoolean());

		newSCLicense.setRecommended(ServiceTestUtil.randomBoolean());

		_persistence.update(newSCLicense, false);

		SCLicense existingSCLicense = _persistence.findByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertEquals(existingSCLicense.getLicenseId(),
			newSCLicense.getLicenseId());
		Assert.assertEquals(existingSCLicense.getName(), newSCLicense.getName());
		Assert.assertEquals(existingSCLicense.getUrl(), newSCLicense.getUrl());
		Assert.assertEquals(existingSCLicense.getOpenSource(),
			newSCLicense.getOpenSource());
		Assert.assertEquals(existingSCLicense.getActive(),
			newSCLicense.getActive());
		Assert.assertEquals(existingSCLicense.getRecommended(),
			newSCLicense.getRecommended());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCLicense newSCLicense = addSCLicense();

		SCLicense existingSCLicense = _persistence.findByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertEquals(existingSCLicense, newSCLicense);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchLicenseException");
		}
		catch (NoSuchLicenseException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCLicense newSCLicense = addSCLicense();

		SCLicense existingSCLicense = _persistence.fetchByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertEquals(existingSCLicense, newSCLicense);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCLicense missingSCLicense = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCLicense);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCLicense newSCLicense = addSCLicense();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				SCLicense.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("licenseId",
				newSCLicense.getLicenseId()));

		List<SCLicense> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCLicense existingSCLicense = result.get(0);

		Assert.assertEquals(existingSCLicense, newSCLicense);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				SCLicense.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("licenseId",
				ServiceTestUtil.nextLong()));

		List<SCLicense> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCLicense newSCLicense = addSCLicense();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				SCLicense.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("licenseId"));

		Object newLicenseId = newSCLicense.getLicenseId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("licenseId",
				new Object[] { newLicenseId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLicenseId = result.get(0);

		Assert.assertEquals(existingLicenseId, newLicenseId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				SCLicense.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("licenseId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("licenseId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SCLicense addSCLicense() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCLicense scLicense = _persistence.create(pk);

		scLicense.setName(ServiceTestUtil.randomString());

		scLicense.setUrl(ServiceTestUtil.randomString());

		scLicense.setOpenSource(ServiceTestUtil.randomBoolean());

		scLicense.setActive(ServiceTestUtil.randomBoolean());

		scLicense.setRecommended(ServiceTestUtil.randomBoolean());

		_persistence.update(scLicense, false);

		return scLicense;
	}

	private SCLicensePersistence _persistence;
}