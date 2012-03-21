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

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;

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
public class ImagePersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ImagePersistence)PortalBeanLocatorUtil.locate(ImagePersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Image image = _persistence.create(pk);

		Assert.assertNotNull(image);

		Assert.assertEquals(image.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Image newImage = addImage();

		_persistence.remove(newImage);

		Image existingImage = _persistence.fetchByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertNull(existingImage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addImage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Image newImage = _persistence.create(pk);

		newImage.setModifiedDate(ServiceTestUtil.nextDate());

		newImage.setText(ServiceTestUtil.randomString());

		newImage.setType(ServiceTestUtil.randomString());

		newImage.setHeight(ServiceTestUtil.nextInt());

		newImage.setWidth(ServiceTestUtil.nextInt());

		newImage.setSize(ServiceTestUtil.nextInt());

		_persistence.update(newImage, false);

		Image existingImage = _persistence.findByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertEquals(existingImage.getImageId(), newImage.getImageId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingImage.getModifiedDate()),
			Time.getShortTimestamp(newImage.getModifiedDate()));
		Assert.assertEquals(existingImage.getText(), newImage.getText());
		Assert.assertEquals(existingImage.getType(), newImage.getType());
		Assert.assertEquals(existingImage.getHeight(), newImage.getHeight());
		Assert.assertEquals(existingImage.getWidth(), newImage.getWidth());
		Assert.assertEquals(existingImage.getSize(), newImage.getSize());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Image newImage = addImage();

		Image existingImage = _persistence.findByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertEquals(existingImage, newImage);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchImageException");
		}
		catch (NoSuchImageException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Image newImage = addImage();

		Image existingImage = _persistence.fetchByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertEquals(existingImage, newImage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Image missingImage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingImage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Image newImage = addImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId",
				newImage.getImageId()));

		List<Image> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Image existingImage = result.get(0);

		Assert.assertEquals(existingImage, newImage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId",
				ServiceTestUtil.nextLong()));

		List<Image> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Image newImage = addImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("imageId"));

		Object newImageId = newImage.getImageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("imageId",
				new Object[] { newImageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingImageId = result.get(0);

		Assert.assertEquals(existingImageId, newImageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("imageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("imageId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Image addImage() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Image image = _persistence.create(pk);

		image.setModifiedDate(ServiceTestUtil.nextDate());

		image.setText(ServiceTestUtil.randomString());

		image.setType(ServiceTestUtil.randomString());

		image.setHeight(ServiceTestUtil.nextInt());

		image.setWidth(ServiceTestUtil.nextInt());

		image.setSize(ServiceTestUtil.nextInt());

		_persistence.update(image, false);

		return image;
	}

	private ImagePersistence _persistence;
}