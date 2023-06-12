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

package com.liferay.fragment.internal.helper;

import com.liferay.fragment.configuration.DefaultInputFragmentEntryConfiguration;
import com.liferay.fragment.helper.DefaultInputFragmentEntryConfigurationProvider;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.LongTextInfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Objects;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = DefaultInputFragmentEntryConfigurationProvider.class)
public class DefaultInputFragmentEntryConfigurationProviderImpl
	implements DefaultInputFragmentEntryConfigurationProvider {

	@Override
	public JSONObject getDefaultInputFragmentEntryKeysJSONObject(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		JSONObject defaultInputFragmentEntryKeysJSONObject =
			_getDefaultInputFragmentEntryKeysJSONObject(group);

		if (defaultInputFragmentEntryKeysJSONObject != null) {
			return defaultInputFragmentEntryKeysJSONObject;
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(
			group.getCompanyId());

		if ((companyGroup != null) &&
			!Objects.equals(companyGroup.getGroupId(), groupId)) {

			defaultInputFragmentEntryKeysJSONObject =
				_getDefaultInputFragmentEntryKeysJSONObject(group);
		}

		if (defaultInputFragmentEntryKeysJSONObject != null) {
			return defaultInputFragmentEntryKeysJSONObject;
		}

		return _defaultInputFragmentEntryKeysJSONObject;
	}

	@Override
	public void updateDefaultInputFragmentEntryKeysJSONObject(
			JSONObject defaultInputFragmentEntryKeysJSONObject)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			DefaultInputFragmentEntryConfiguration.class.getName(),
			StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put(
			"defaultInputFragmentEntryKeys",
			defaultInputFragmentEntryKeysJSONObject.toString());

		configuration.update(properties);
	}

	private JSONObject _getDefaultInputFragmentEntryKeysJSONObject(
		Group group) {

		if (group == null) {
			return null;
		}

		try {
			DefaultInputFragmentEntryConfiguration
				defaultInputFragmentEntryConfiguration =
					_configurationProvider.getGroupConfiguration(
						DefaultInputFragmentEntryConfiguration.class,
						group.getGroupId());

			String defaultInputFragmentEntryKeys =
				defaultInputFragmentEntryConfiguration.
					defaultInputFragmentEntryKeys();

			if (Validator.isNull(defaultInputFragmentEntryKeys)) {
				return null;
			}

			return _jsonFactory.createJSONObject(defaultInputFragmentEntryKeys);
		}
		catch (ConfigurationException | JSONException exception) {
			_log.error(exception);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultInputFragmentEntryConfigurationProviderImpl.class);

	private static final JSONObject _defaultInputFragmentEntryKeysJSONObject =
		JSONUtil.put(
			BooleanInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-checkbox")
		).put(
			DateInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-date-input")
		).put(
			FileInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-file-upload")
		).put(
			HTMLInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-rich-text-input")
		).put(
			LongTextInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-textarea")
		).put(
			MultiselectInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-multiselect-list")
		).put(
			NumberInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-numeric-input")
		).put(
			RelationshipInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-select-from-list")
		).put(
			SelectInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-select-from-list")
		).put(
			TextInfoFieldType.INSTANCE.getName(),
			JSONUtil.put("key", "INPUTS-text-input")
		);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}