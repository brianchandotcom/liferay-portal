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
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = DefaultInputFragmentEntryConfigurationProvider.class)
public class DefaultInputFragmentEntryConfigurationProviderImpl
	implements DefaultInputFragmentEntryConfigurationProvider {

	@Override
	public Map<String, String> getDefaultInputFragmentEntryKeys(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		Map<String, String> defaultInputFragmentEntryKeysMap =
			_getDefaultInputFragmentEntryKeys(group);

		if (defaultInputFragmentEntryKeysMap != null) {
			return defaultInputFragmentEntryKeysMap;
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(
			group.getCompanyId());

		if ((companyGroup != null) &&
			!Objects.equals(companyGroup.getGroupId(), groupId)) {

			defaultInputFragmentEntryKeysMap =
				_getDefaultInputFragmentEntryKeys(group);
		}

		if (defaultInputFragmentEntryKeysMap != null) {
			return defaultInputFragmentEntryKeysMap;
		}

		return _defaultInputFragmentEntryKeys;
	}

	private Map<String, String> _getDefaultInputFragmentEntryKeys(Group group) {
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

			return JSONUtil.toStringMap(
				_jsonFactory.createJSONObject(defaultInputFragmentEntryKeys));
		}
		catch (ConfigurationException | JSONException exception) {
			_log.error(exception);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultInputFragmentEntryConfigurationProviderImpl.class);

	private static final Map<String, String> _defaultInputFragmentEntryKeys =
		HashMapBuilder.put(
			BooleanInfoFieldType.INSTANCE.getName(), "INPUTS-checkbox"
		).put(
			DateInfoFieldType.INSTANCE.getName(), "INPUTS-date-input"
		).put(
			FileInfoFieldType.INSTANCE.getName(), "INPUTS-file-upload"
		).put(
			HTMLInfoFieldType.INSTANCE.getName(), "INPUTS-rich-text-input"
		).put(
			MultiselectInfoFieldType.INSTANCE.getName(),
			"INPUTS-multiselect-list"
		).put(
			NumberInfoFieldType.INSTANCE.getName(), "INPUTS-numeric-input"
		).put(
			RelationshipInfoFieldType.INSTANCE.getName(),
			"INPUTS-select-from-list"
		).put(
			SelectInfoFieldType.INSTANCE.getName(), "INPUTS-select-from-list"
		).put(
			TextInfoFieldType.INSTANCE.getName(), "INPUTS-text-input"
		).build();

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}