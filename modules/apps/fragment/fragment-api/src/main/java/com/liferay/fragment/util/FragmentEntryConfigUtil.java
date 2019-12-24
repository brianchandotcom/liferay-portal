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

package com.liferay.fragment.util;

import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author     Rubén Pulido
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             FragmentEntryConfigurationParser}
 */
@Deprecated
public class FragmentEntryConfigUtil {

	public static JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.
			getConfigurationDefaultValuesJSONObject(configuration);
	}

	public static JSONObject getConfigurationJSONObject(
			String configuration, String editableValues,
			long[] segmentsExperienceIds)
		throws JSONException {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.getConfigurationJSONObject(
			configuration, editableValues, segmentsExperienceIds);
	}

	public static Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.getContextObjects(
			configurationValuesJSONObject, configuration);
	}

	public static Object getFieldValue(
		FragmentConfigurationField fragmentConfigurationField, String value) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.getFieldValue(
			fragmentConfigurationField, value);
	}

	public static Object getFieldValue(
		String configuration, String editableValues,
		long[] segmentsExperienceIds, String name) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.getFieldValue(
			configuration, editableValues, segmentsExperienceIds, name);
	}

	public static Object getFieldValue(
		String configuration, String fieldName, String value) {

		throw new UnsupportedOperationException();
	}

	public static List<FragmentConfigurationField>
		getFragmentConfigurationFields(String configuration) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.getFragmentConfigurationFields(
			configuration);
	}

	public static JSONObject getSegmentedConfigurationValues(
		long[] segmentsExperienceIds,
		JSONObject configurationValuesJSONObject) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.getSegmentedConfigurationValues(
			segmentsExperienceIds, configurationValuesJSONObject);
	}

	public static boolean isPersonalizationSupported(JSONObject jsonObject) {
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.isPersonalizationSupported(
			jsonObject);
	}

	public static String translateConfiguration(
		JSONObject jsonObject, ResourceBundle resourceBundle) {

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			_serviceTracker.getService();

		return fragmentEntryConfigurationParser.translateConfiguration(
			jsonObject, resourceBundle);
	}

	private static final ServiceTracker
		<FragmentEntryConfigurationParser, FragmentEntryConfigurationParser>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentEntryConfigUtil.class);

		ServiceTracker
			<FragmentEntryConfigurationParser, FragmentEntryConfigurationParser>
				serviceTracker = new ServiceTracker<>(
					bundle.getBundleContext(),
					FragmentEntryConfigurationParser.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}