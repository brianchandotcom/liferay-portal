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
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Rubén Pulido
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             FragmentEntryConfigurationUtil}
 */
@Deprecated
public class FragmentEntryConfigUtil {

	public static JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.
			getConfigurationDefaultValuesJSONObject(configuration);
	}

	public static JSONObject getConfigurationJSONObject(
			String configuration, String editableValues,
			long[] segmentsExperienceIds)
		throws JSONException {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.getConfigurationJSONObject(
			configuration, editableValues, segmentsExperienceIds);
	}

	public static Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.getContextObjects(
			configurationValuesJSONObject, configuration);
	}

	public static Object getFieldValue(
		FragmentConfigurationField fragmentConfigurationField, String value) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.getFieldValue(
			fragmentConfigurationField, value);
	}

	public static Object getFieldValue(
		String configuration, String editableValues,
		long[] segmentsExperienceIds, String name) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.getFieldValue(
			configuration, editableValues, segmentsExperienceIds, name);
	}

	public static Object getFieldValue(
		String configuration, String fieldName, String value) {

		throw new UnsupportedOperationException();
	}

	public static List<FragmentConfigurationField>
		getFragmentConfigurationFields(String configuration) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.getFragmentConfigurationFields(
			configuration);
	}

	public static JSONObject getSegmentedConfigurationValues(
		long[] segmentsExperienceIds,
		JSONObject configurationValuesJSONObject) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.getSegmentedConfigurationValues(
			segmentsExperienceIds, configurationValuesJSONObject);
	}

	public static boolean isPersonalizationSupported(JSONObject jsonObject) {
		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.isPersonalizationSupported(
			jsonObject);
	}

	public static String translateConfiguration(
		JSONObject jsonObject, ResourceBundle resourceBundle) {

		FragmentEntryConfigurationUtil fragmentEntryConfigurationUtil =
			_serviceTracker.getService();

		return fragmentEntryConfigurationUtil.translateConfiguration(
			jsonObject, resourceBundle);
	}

	private static final ServiceTracker
		<FragmentEntryConfigurationUtil, FragmentEntryConfigurationUtil>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentEntryConfigUtil.class);

		ServiceTracker
			<FragmentEntryConfigurationUtil, FragmentEntryConfigurationUtil>
				serviceTracker = new ServiceTracker<>(
					bundle.getBundleContext(),
					FragmentEntryConfigurationUtil.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}