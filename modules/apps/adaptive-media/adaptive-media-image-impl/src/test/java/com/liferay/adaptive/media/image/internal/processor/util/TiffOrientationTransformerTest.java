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

package com.liferay.adaptive.media.image.internal.processor.util;

import com.liferay.adaptive.media.image.internal.util.RenderedImageUtil;
import com.liferay.portal.image.ImageToolImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.awt.image.RenderedImage;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alicia García
 */
public class TiffOrientationTransformerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_mimeTypes = Mockito.mock(MimeTypes.class);

		_serviceRegistration = bundleContext.registerService(
			MimeTypes.class, _mimeTypes, null);

		ImageToolUtil imageToolUtil = new ImageToolUtil();

		imageToolUtil.setImageTool(_imageTool);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testTransformJPGReturnSameImage()
		throws IOException, PortalException {

		Mockito.when(
			_mimeTypes.getContentType(
				(InputStream)Mockito.anyObject(), Mockito.anyString())
		).thenReturn(
			ContentTypes.IMAGE_JPEG
		);

		String fileName =
			"com/liferay/adaptive/media/image/internal/dependencies/test.jpg";

		RenderedImage renderedImage = TiffOrientationTransformer.transform(
			() -> _getInputStream(fileName));

		Assert.assertArrayEquals(
			_imageTool.getBytes(
				RenderedImageUtil.readImage(_getInputStream(fileName)),
				ContentTypes.IMAGE_JPEG),
			_imageTool.getBytes(renderedImage, ContentTypes.IMAGE_JPEG));
	}

	@Test
	public void testTransformPNGReturnTransformedImage()
		throws IOException, PortalException {

		Mockito.when(
			_mimeTypes.getContentType(
				(InputStream)Mockito.anyObject(), Mockito.anyString())
		).thenReturn(
			ContentTypes.IMAGE_PNG
		);

		String fileName =
			"com/liferay/adaptive/media/image/internal/dependencies/sample.png";

		RenderedImage renderedImage = TiffOrientationTransformer.transform(
			() -> _getInputStream(fileName));

		RenderedImage originalRenderedImage = RenderedImageUtil.readImage(
			_getInputStream(fileName));

		Assert.assertEquals(
			originalRenderedImage.getWidth(), renderedImage.getHeight());
	}

	private InputStream _getInputStream(String fileName) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(fileName);
	}

	private final ImageTool _imageTool = ImageToolImpl.getInstance();
	private MimeTypes _mimeTypes;
	private ServiceRegistration<?> _serviceRegistration;

}