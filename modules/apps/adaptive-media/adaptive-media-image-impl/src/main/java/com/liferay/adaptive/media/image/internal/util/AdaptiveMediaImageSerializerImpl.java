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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.image.util.AdaptiveMediaImageSerializer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.InputStream;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AdaptiveMediaImageSerializer.class)
public class AdaptiveMediaImageSerializerImpl
	implements AdaptiveMediaImageSerializer {

	@Override
	public AdaptiveMedia<AdaptiveMediaImageProcessor> deserialize(
		String s, Supplier<InputStream> inputStreamSupplier) {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(s);

			Map<String, String> properties = new HashMap<>();

			JSONObject attributesJSONObject = jsonObject.getJSONObject(
				"attributes");

			Map<String, AdaptiveMediaAttribute<?, ?>>
				allowedAdaptiveMediaAttributes =
					AdaptiveMediaImageAttribute.
						getAllowedAdaptiveMediaAttributes();

			allowedAdaptiveMediaAttributes.forEach(
				(name, attribute) -> {
					if (attributesJSONObject.has(name)) {
						properties.put(
							name, attributesJSONObject.getString(name));
					}
				});

			String uri = jsonObject.getString("uri");

			return new AdaptiveMediaImage(
				inputStreamSupplier,
				AdaptiveMediaImageAttributeMapping.fromProperties(properties),
				URI.create(uri));
		}
		catch (JSONException jsone) {
			throw new AdaptiveMediaRuntimeException(jsone);
		}
	}

	@Override
	public String serialize(AdaptiveMedia<AdaptiveMediaImageProcessor> media) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("uri", media.getURI());

		JSONObject attributesJSONObject = JSONFactoryUtil.createJSONObject();

		Map<String, AdaptiveMediaAttribute<?, ?>>
			allowedAdaptiveMediaAttributes =
				AdaptiveMediaImageAttribute.getAllowedAdaptiveMediaAttributes();

		allowedAdaptiveMediaAttributes.forEach(
			(name, attribute) -> {
				Optional<Object> valueOptional = media.getValueOptional(
					(AdaptiveMediaAttribute)attribute);

				valueOptional.ifPresent(
					value -> attributesJSONObject.put(
						name, String.valueOf(value)));
			});

		jsonObject.put("attributes", attributesJSONObject);

		return jsonObject.toString();
	}

}