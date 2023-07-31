/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.adaptive.media.image.internal.configuration.AMImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AMImage;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.util.AMImageSerializer;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.InputStream;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() {
		_amImageSerializer = new AMImageSerializerImpl();

		ReflectionTestUtil.setFieldValue(
			_amImageSerializer, "_jsonFactory", new JSONFactoryImpl());
	}

	@Test
	public void testDeserialize() throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"attributes",
			JSONUtil.put(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(), "200"
			).put(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(), "300"
			)
		).put(
			"uri", "http://localhost"
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			_amImageSerializer.deserialize(
				jsonObject.toString(), () -> inputStream);

		Assert.assertEquals(
			new URI("http://localhost"), adaptiveMedia.getURI());
		Assert.assertEquals(
			"200",
			String.valueOf(
				adaptiveMedia.getValue(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT)));
		Assert.assertEquals(
			"300",
			String.valueOf(
				adaptiveMedia.getValue(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH)));
	}

	@Test(expected = AMRuntimeException.class)
	public void testDeserializeInvalidString() throws Exception {
		String invalidString = RandomTestUtil.randomString();

		InputStream inputStream = Mockito.mock(InputStream.class);

		_amImageSerializer.deserialize(invalidString, () -> inputStream);
	}

	@Test
	public void testDeserializeWithEmptyAttributes() throws Exception {
		JSONObject jsonObject = JSONUtil.put(
			"attributes", JSONFactoryUtil.createJSONObject()
		).put(
			"uri", "http://localhost"
		);

		InputStream inputStream = Mockito.mock(InputStream.class);

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia =
			_amImageSerializer.deserialize(
				jsonObject.toString(), () -> inputStream);

		Assert.assertEquals(
			new URI("http://localhost"), adaptiveMedia.getURI());
		Assert.assertNull(
			adaptiveMedia.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertNull(
			adaptiveMedia.getValue(AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test
	public void testSerialize() throws Exception {
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia = new AMImage(
			() -> null,
			AMImageAttributeMapping.fromProperties(
				HashMapBuilder.put(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(), "200"
				).put(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(), "300"
				).build()),
			new URI("http://localhost"));

		String serialize = _amImageSerializer.serialize(adaptiveMedia);

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

		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia = new AMImage(
			() -> null, AMImageAttributeMapping.fromProperties(properties),
			new URI("http://localhost"));

		String serialize = _amImageSerializer.serialize(adaptiveMedia);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(serialize);

		JSONObject attributesJSONObject = jsonObject.getJSONObject(
			"attributes");

		Assert.assertEquals(0, attributesJSONObject.length());
	}

	private AMImageSerializer _amImageSerializer;

}