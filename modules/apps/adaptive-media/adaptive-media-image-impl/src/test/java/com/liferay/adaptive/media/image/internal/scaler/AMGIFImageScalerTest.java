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

package com.liferay.adaptive.media.image.internal.scaler;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.AMImageConfigurationEntryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class AMGIFImageScalerTest {

	@Test
	public void testGetResizeFitArgumentWithBlankMaxHeight() {
		AMGIFImageScaler amGIFImageScaler = new AMGIFImageScaler();

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", ""
		).put(
			"max-width", "100"
		).build();

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		Assert.assertEquals(
			"100x_",
			_getResizeFitValues(amGIFImageScaler, amImageConfigurationEntry));
	}

	@Test
	public void testGetResizeFitArgumentWithBlankMaxWidth() {
		AMGIFImageScaler amGIFImageScaler = new AMGIFImageScaler();

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", ""
		).build();

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		Assert.assertEquals(
			"_x100",
			_getResizeFitValues(amGIFImageScaler, amImageConfigurationEntry));
	}

	@Test
	public void testGetResizeFitArgumentWithMaxWidthAndMaxHeight() {
		AMGIFImageScaler amGIFImageScaler = new AMGIFImageScaler();

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "200"
		).build();

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		Assert.assertEquals(
			"200x100",
			_getResizeFitValues(amGIFImageScaler, amImageConfigurationEntry));
	}

	@Test
	public void testGetResizeFitArgumentWithOnlyMaxHeight() {
		AMGIFImageScaler amGIFImageScaler = new AMGIFImageScaler();

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).build();

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		Assert.assertEquals(
			"_x100",
			_getResizeFitValues(amGIFImageScaler, amImageConfigurationEntry));
	}

	@Test
	public void testGetResizeFitArgumentWithZeroMaxHeight() {
		AMGIFImageScaler amGIFImageScaler = new AMGIFImageScaler();

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "0"
		).put(
			"max-width", "100"
		).build();

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		Assert.assertEquals(
			"100x_",
			_getResizeFitValues(amGIFImageScaler, amImageConfigurationEntry));
	}

	@Test
	public void testGetResizeFitArgumentWithZeroMaxWidth() {
		AMGIFImageScaler amGIFImageScaler = new AMGIFImageScaler();

		Map<String, String> properties = HashMapBuilder.put(
			"max-height", "100"
		).put(
			"max-width", "0"
		).build();

		AMImageConfigurationEntry amImageConfigurationEntry =
			new AMImageConfigurationEntryImpl(
				"test", "desc", "12345", properties, true);

		Assert.assertEquals(
			"_x100",
			_getResizeFitValues(amGIFImageScaler, amImageConfigurationEntry));
	}

	private String _getResizeFitValues(
		AMGIFImageScaler amGIFImageScaler,
		AMImageConfigurationEntry amImageConfigurationEntry) {

		return ReflectionTestUtil.invoke(
			amGIFImageScaler, "_getResizeFitValues",
			new Class<?>[] {AMImageConfigurationEntry.class},
			amImageConfigurationEntry);
	}

}