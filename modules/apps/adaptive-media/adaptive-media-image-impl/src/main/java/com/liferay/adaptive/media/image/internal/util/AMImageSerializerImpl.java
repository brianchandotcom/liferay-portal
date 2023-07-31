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
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = AMImageSerializer.class)
public class AMImageSerializerImpl implements AMImageSerializer {

	@Override
	public AdaptiveMedia<AMProcessor<FileVersion>> deserialize(
		String s, Supplier<InputStream> inputStreamSupplier) {

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(s);

			Map<String, String> properties = new HashMap<>();

			JSONObject attributesJSONObject = jsonObject.getJSONObject(
				"attributes");

			Map<String, AMAttribute<?, ?>> allowedAMAttributes =
				AMImageAttribute.getAllowedAMAttributes();

			allowedAMAttributes.forEach(
				(name, amAttribute) -> {
					if (attributesJSONObject.has(name)) {
						properties.put(
							name, attributesJSONObject.getString(name));
					}
				});

			String uri = jsonObject.getString("uri");

			return new AMImage(
				inputStreamSupplier,
				AMImageAttributeMapping.fromProperties(properties),
				URI.create(uri));
		}
		catch (JSONException jsonException) {
			throw new AMRuntimeException(jsonException);
		}
	}

	@Override
	public String serialize(
		AdaptiveMedia<AMProcessor<FileVersion>> adaptiveMedia) {

		JSONObject attributesJSONObject = _jsonFactory.createJSONObject();

		Map<String, AMAttribute<?, ?>> allowedAMAttributes =
			AMImageAttribute.getAllowedAMAttributes();

		allowedAMAttributes.forEach(
			(name, amAttribute) -> {
				Object value = adaptiveMedia.getValue((AMAttribute)amAttribute);

				if (value != null) {
					attributesJSONObject.put(name, String.valueOf(value));
				}
			});

		return JSONUtil.put(
			"attributes", attributesJSONObject
		).put(
			"uri", adaptiveMedia.getURI()
		).toString();
	}

	@Reference
	private JSONFactory _jsonFactory;

}