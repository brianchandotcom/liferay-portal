/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.net.URI;

import java.util.Collections;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AMImageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAttributeDelegatesOnMapping() {
		AMImageAttributeMapping amImageAttributeMapping = Mockito.mock(
			AMImageAttributeMapping.class);

		AMImage amImage = new AMImage(
			() -> null, amImageAttributeMapping, URI.create("/"));

		amImage.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);

		Mockito.verify(
			amImageAttributeMapping
		).getValue(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT
		);
	}

	@Test
	public void testGetInputStreamDelegatesOnSupplier() {
		InputStream inputStream = Mockito.mock(InputStream.class);

		Supplier<InputStream> supplier = () -> inputStream;

		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(Collections.emptyMap());

		AMImage amImage = new AMImage(
			supplier, amImageAttributeMapping, URI.create("/"));

		Assert.assertEquals(inputStream, amImage.getInputStream());
	}

}