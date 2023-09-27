/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMImageAttributeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAllPublicAttributesAreSupported() {
		Collection<AMAttribute<?, ?>> publicAMAttributes = Arrays.asList(
			AMAttribute.getConfigurationUuidAMAttribute(),
			AMAttribute.getContentLengthAMAttribute(),
			AMAttribute.getContentTypeAMAttribute(),
			AMAttribute.getFileNameAMAttribute(),
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT,
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		Map<String, AMAttribute<?, ?>> allowedAMAttributesMap =
			AMImageAttribute.getAllowedAMAttributes();

		Collection<AMAttribute<?, ?>> allowedAMAttributes =
			allowedAMAttributesMap.values();

		Assert.assertTrue(allowedAMAttributes.containsAll(publicAMAttributes));
	}

	@Test(expected = AMRuntimeException.AMAttributeFormatException.class)
	public void testImageHeightFailsForNonintegers() {
		AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.convert("xyz");
	}

	@Test
	public void testImageHeightRecognizesIntegers() {
		int result = AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.convert("42");

		Assert.assertEquals(42, result);
	}

	@Test(expected = AMRuntimeException.AMAttributeFormatException.class)
	public void testImageWidthFailsForNonintegers() {
		AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.convert("xyz");
	}

	@Test
	public void testImageWidthRecognizesIntegers() {
		int result = AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.convert("42");

		Assert.assertEquals(42, result);
	}

}