/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.entry.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Collections;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryUtil {

	public static JSONObject getPayloadJSONObject(
			DTOConverterRegistry dtoConverterRegistry, JSONFactory jsonFactory,
			String objectActionTriggerKey, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, ObjectEntry originalObjectEntry, User user)
		throws PortalException {

		return JSONUtil.put(
			"classPK", objectEntry.getObjectEntryId()
		).put(
			"objectActionTriggerKey", objectActionTriggerKey
		).put(
			"objectEntry",
			HashMapBuilder.putAll(
				objectEntry.getModelAttributes()
			).put(
				"creator", user.getFullName()
			).put(
				"id", objectEntry.getObjectEntryId()
			).put(
				"values", objectEntry.getValues()
			).build()
		).put(
			"objectEntryDTO" + objectDefinition.getShortName(),
			_toDTO(dtoConverterRegistry, jsonFactory, objectEntry, user)
		).put(
			"originalObjectEntry",
			() -> {
				if (originalObjectEntry == null) {
					return null;
				}

				return HashMapBuilder.putAll(
					originalObjectEntry.getModelAttributes()
				).put(
					"values", originalObjectEntry.getValues()
				).build();
			}
		).put(
			"originalObjectEntryDTO" + objectDefinition.getShortName(),
			() -> {
				if (originalObjectEntry == null) {
					return null;
				}

				return _toDTO(
					dtoConverterRegistry, jsonFactory, originalObjectEntry,
					user);
			}
		);
	}

	private static Map<String, Object> _toDTO(
			DTOConverterRegistry dtoConverterRegistry, JSONFactory jsonFactory,
			ObjectEntry objectEntry, User user)
		throws PortalException {

		DTOConverter<ObjectEntry, ?> dtoConverter =
			(DTOConverter<ObjectEntry, ?>)dtoConverterRegistry.getDTOConverter(
				ObjectEntry.class.getName());

		if (dtoConverter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DTO converter found for " +
						ObjectEntry.class.getName());
			}

			return objectEntry.getModelAttributes();
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), dtoConverterRegistry, null,
				user.getLocale(), null, user);

		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				jsonFactory.looseSerializeDeep(
					dtoConverter.toDTO(
						defaultDTOConverterContext, objectEntry)));

			return jsonObject.toMap();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return objectEntry.getModelAttributes();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryUtil.class);

}