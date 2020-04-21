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

package com.liferay.layout.page.template.internal.upgrade.v3_3_0.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class EditableValuesTransformerUtil {

	public static String getEditableValues(
		String editableValues, long segmentsExperienceId) {

		JSONObject newEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		try {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(editableValues);

			Iterator<String> keysIterator = editableValuesJSONObject.keys();

			while (keysIterator.hasNext()) {
				String editableProcessorKey = keysIterator.next();

				Object editableProcessorObject = editableValuesJSONObject.get(
					editableProcessorKey);

				if (!(editableProcessorObject instanceof JSONObject)) {
					newEditableValuesJSONObject.put(
						editableProcessorKey, editableProcessorObject);

					continue;
				}

				JSONObject editableProcessorJSONObject =
					(JSONObject)editableProcessorObject;

				if (editableProcessorJSONObject.length() <= 0) {
					newEditableValuesJSONObject.put(
						editableProcessorKey,
						JSONFactoryUtil.createJSONObject());

					continue;
				}

				if (Objects.equals(
						editableProcessorKey,
						_KEY_FREE_MARKER_FRAGMENT_ENTRY_PROCESSOR)) {

					newEditableValuesJSONObject.put(
						editableProcessorKey,
						_getFreeMarkerFragmentEntryProcessorJSONObject(
							editableProcessorJSONObject, segmentsExperienceId));

					continue;
				}

				newEditableValuesJSONObject.put(
					editableProcessorKey,
					_getFragmentEntryProcessorJSONObject(
						editableProcessorJSONObject, segmentsExperienceId));
			}
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException, jsonException);
			}
		}

		return newEditableValuesJSONObject.toJSONString();
	}

	private static JSONObject _getFragmentEntryProcessorJSONObject(
		JSONObject editableProcessorJSONObject, long segmentsExperienceId) {

		JSONObject newEditableProcessorJSONObject =
			JSONFactoryUtil.createJSONObject();

		Iterator<String> editableKeysIterator =
			editableProcessorJSONObject.keys();

		while (editableKeysIterator.hasNext()) {
			String editableKey = editableKeysIterator.next();

			JSONObject editableJSONObject =
				editableProcessorJSONObject.getJSONObject(editableKey);

			if (editableJSONObject == null) {
				newEditableProcessorJSONObject.put(
					editableKey, JSONFactoryUtil.createJSONObject());

				continue;
			}

			JSONObject newEditableJSONObject =
				JSONFactoryUtil.createJSONObject();

			Iterator<String> valueKeysIterator = editableJSONObject.keys();

			while (valueKeysIterator.hasNext()) {
				String valueKey = valueKeysIterator.next();

				if (Objects.equals(
						valueKey, _ID_PREFIX + segmentsExperienceId)) {

					JSONObject valueJSONObject =
						editableJSONObject.getJSONObject(valueKey);

					Iterator<String> segmentedValueKeysIterator =
						valueJSONObject.keys();

					while (segmentedValueKeysIterator.hasNext()) {
						String segmentedValueKey =
							segmentedValueKeysIterator.next();

						newEditableJSONObject.put(
							segmentedValueKey,
							valueJSONObject.get(segmentedValueKey));
					}
				}
				else if (!valueKey.startsWith(_ID_PREFIX)) {
					newEditableJSONObject.put(
						valueKey, editableJSONObject.get(valueKey));
				}
			}

			newEditableProcessorJSONObject.put(
				editableKey, newEditableJSONObject);
		}

		return newEditableProcessorJSONObject;
	}

	private static JSONObject _getFreeMarkerFragmentEntryProcessorJSONObject(
		JSONObject jsonObject, long segmentsExperienceId) {

		if (!jsonObject.has(_ID_PREFIX + segmentsExperienceId)) {
			return JSONFactoryUtil.createJSONObject();
		}

		return jsonObject.getJSONObject(_ID_PREFIX + segmentsExperienceId);
	}

	private static final String _ID_PREFIX = "segments-experience-id-";

	private static final String _KEY_FREE_MARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		EditableValuesTransformerUtil.class);

}