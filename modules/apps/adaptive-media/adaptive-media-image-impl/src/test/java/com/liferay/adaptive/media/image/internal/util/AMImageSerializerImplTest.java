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

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.util.AMImageSerializer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Sergio González
 */
public class AMImageSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeserialize() throws Exception {
		JSONObject jsonObject = JSONUtil.put("uri", "http://localhost");

		jsonObject.put(
			"attributes",
			JSONUtil.put(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(), "200"
			).put(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(), "300"
			));

		AMImageSerializer amImageSerializer = new AMImageSerializerImpl();

		InputStream inputStream = Mockito.mock(InputStream.class);

		AdaptiveMedia<AMImageProcessor> adaptiveMedia =
			amImageSerializer.deserialize(
				jsonObject.toString(), () -> inputStream);

		Assert.assertEquals(
			new URI("http://localhost"), adaptiveMedia.getURI());

		Optional<Integer> heightValueOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);

		Assert.assertEquals("200", String.valueOf(heightValueOptional.get()));

		Optional<Integer> widthValueOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		Assert.assertEquals("300", String.valueOf(widthValueOptional.get()));
	}

	@Test(expected = AMRuntimeException.class)
	public void testDeserializeInvalidString() throws Exception {
		String invalidString = RandomTestUtil.randomString();

		AMImageSerializer amImageSerializer = new AMImageSerializerImpl();

		InputStream inputStream = Mockito.mock(InputStream.class);

		amImageSerializer.deserialize(invalidString, () -> inputStream);
	}

	@Test
	public void testDeserializeWithEmptyAttributes() throws Exception {
		JSONObject jsonObject = JSONUtil.put("uri", "http://localhost");

		JSONObject attributesJSONObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("attributes", attributesJSONObject);

		AMImageSerializer amImageSerializer = new AMImageSerializerImpl();

		InputStream inputStream = Mockito.mock(InputStream.class);

		AdaptiveMedia<AMImageProcessor> adaptiveMedia =
			amImageSerializer.deserialize(
				jsonObject.toString(), () -> inputStream);

		Assert.assertEquals(
			new URI("http://localhost"), adaptiveMedia.getURI());

		Optional<Integer> heightValueOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);

		Assert.assertFalse(heightValueOptional.isPresent());

		Optional<Integer> widthValueOptional = adaptiveMedia.getValueOptional(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		Assert.assertFalse(widthValueOptional.isPresent());
	}

	@Test
	public void testSerialize() throws Exception {
		AdaptiveMedia<AMImageProcessor> adaptiveMedia = new AMImage(
			() -> null,
			AMImageAttributeMapping.fromProperties(
				HashMapBuilder.put(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(), "200"
				).put(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(), "300"
				).build()),
			new URI("http://localhost"));

		AMImageSerializer amImageSerializer = new AMImageSerializerImpl();

		String serialize = amImageSerializer.serialize(adaptiveMedia);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(serialize);

		Assert.assertEquals("http://localhost", jsonObject.getString("uri"));

		JSONObject attributesJSONObject = jsonObject.getJSONObject(
			"attributes");

		Assert.assertEquals(
			"200",
			attributesJSONObject.getString(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName()));
		Assert.assertEquals(
			"300",
			attributesJSONObject.getString(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName()));

		AMAttribute<?, Long> contentLengthAMAttribute =
			AMAttribute.getContentLengthAMAttribute();

		Assert.assertEquals(
			StringPool.BLANK,
			attributesJSONObject.getString(contentLengthAMAttribute.getName()));
	}

	@Test
	public void testSerializeAdaptiveMediaWithEmptyAttributes()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		AdaptiveMedia<AMImageProcessor> adaptiveMedia = new AMImage(
			() -> null, AMImageAttributeMapping.fromProperties(properties),
			new URI("http://localhost"));

		AMImageSerializer amImageSerializer = new AMImageSerializerImpl();

		String serialize = amImageSerializer.serialize(adaptiveMedia);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(serialize);

		JSONObject attributesJSONObject = jsonObject.getJSONObject(
			"attributes");

		Assert.assertEquals(0, attributesJSONObject.length());
	}

}