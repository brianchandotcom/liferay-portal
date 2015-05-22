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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public abstract class BaseEditorConfigurationProvider<T> {

	protected static String getKey(
		String portletName, String editorConfigKey, String editorName) {

		if (Validator.isNull(portletName)) {
			portletName = "null";
		}

		if (Validator.isNull(editorConfigKey)) {
			editorConfigKey = "null";
		}

		if (Validator.isNull(editorName)) {
			editorName = "null";
		}

		StringBundler sb = new StringBundler();

		sb.append(portletName);
		sb.append(StringPool.PERIOD);
		sb.append(editorConfigKey);
		sb.append(StringPool.PERIOD);
		sb.append(editorName);

		return sb.toString();
	}

	protected List<T> getContributors(
		String portletName, String editorConfigKey, String editorName) {

		List<T> contributors = new ArrayList<>();

		populateContributor(
			contributors, getKey(portletName, editorConfigKey, editorName));

		populateContributor(
			contributors, getKey(portletName, editorConfigKey, null));

		populateContributor(
			contributors, getKey(null, editorConfigKey, editorName));

		populateContributor(
			contributors, getKey(portletName, null, editorName));

		populateContributor(contributors, getKey(null, editorConfigKey, null));

		populateContributor(contributors, getKey(portletName, null, null));

		populateContributor(contributors, getKey(null, null, editorName));

		return contributors;
	}

	protected abstract ServiceTrackerMap<String, List<T>>
		getServiceTrackerMap();

	protected void populateContributor(List<T> contributors, String key) {
		List<T> curContributors = getServiceTrackerMap().getService(key);

		if (ListUtil.isNotEmpty(curContributors)) {
			contributors.addAll(curContributors);
		}
	}

	protected static class EditorServiceReferenceMapper<T>
		implements ServiceReferenceMapper<String, T> {

		@Override
		public void map(
			ServiceReference<T> serviceReference, Emitter<String> emitter) {

			List<String> portletNames = StringPlus.asList(
				serviceReference.getProperty("javax.portlet.name"));

			if (portletNames.isEmpty()) {
				portletNames.add(StringPool.BLANK);
			}

			List<String> editorConfigKeys = StringPlus.asList(
				serviceReference.getProperty("editor.config.key"));

			if (editorConfigKeys.isEmpty()) {
				editorConfigKeys.add(StringPool.BLANK);
			}

			List<String> editorNames = StringPlus.asList(
				serviceReference.getProperty("editor.name"));

			if (editorNames.isEmpty()) {
				editorNames.add(StringPool.BLANK);
			}

			for (String portletName : portletNames) {
				for (String editorConfigKey : editorConfigKeys) {
					for (String editorName : editorNames) {
						emitter.emit(
							getKey(portletName, editorConfigKey, editorName));
					}
				}
			}
		}

	}

}