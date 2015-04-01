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

package com.liferay.portal.editor.config;

import com.liferay.portal.kernel.editor.config.EditorConfig;
import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.editor.config.EditorConfigFactory;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorConfigFactoryImpl implements EditorConfigFactory {

	@Override
	public EditorConfig getEditorConfig(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		List<EditorConfigContributor> editorConfigContributors =
			getEditorConfigContributors(
				portletName, editorConfigKey, editorName);

		return new EditorConfigImpl(
			editorConfigContributors, inputEditorTaglibAttributes, themeDisplay,
			liferayPortletResponse);
	}

	protected List<EditorConfigContributor> getEditorConfigContributors(
		String portletName, String editorConfigKey, String editorName) {

		List<EditorConfigContributor> editorConfigContributors =
			new ArrayList<>();

		populateEditorConfigContributor(
			editorConfigContributors,
			_getKey(portletName, editorConfigKey, editorName));

		populateEditorConfigContributor(
			editorConfigContributors,
			_getKey(portletName, editorConfigKey, null));

		populateEditorConfigContributor(
			editorConfigContributors,
			_getKey(null, editorConfigKey, editorName));

		populateEditorConfigContributor(
			editorConfigContributors, _getKey(portletName, null, editorName));

		populateEditorConfigContributor(
			editorConfigContributors, _getKey(null, editorConfigKey, null));

		populateEditorConfigContributor(
			editorConfigContributors, _getKey(portletName, null, null));

		populateEditorConfigContributor(
			editorConfigContributors, _getKey(null, null, editorName));

		return editorConfigContributors;
	}

	protected void populateEditorConfigContributor(
		List<EditorConfigContributor> editorConfigContributors, String key) {

		List<EditorConfigContributor> curEditorConfigContributors =
			_serviceTrackerMap.getService(key);

		if (ListUtil.isNotEmpty(curEditorConfigContributors)) {
			editorConfigContributors.addAll(curEditorConfigContributors);
		}
	}

	private static String _getKey(
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

	private static final ServiceReferenceMapper<String, EditorConfigContributor>
		_serviceReferenceMapper =
			new ServiceReferenceMapper<String, EditorConfigContributor>() {

				@Override
				public void map(
					ServiceReference<EditorConfigContributor> serviceReference,
					Emitter<String> emitter) {

					List<String> portletNames = StringPlus.asList(
						serviceReference.getProperty("javax.portlet.name"));

					if (portletNames.isEmpty()) {
						portletNames.add(StringPool.BLANK);
					}

					for (String portletName : portletNames) {
						List<String> editorConfigKeys = StringPlus.asList(
							serviceReference.getProperty("editor.config.key"));

						if (editorConfigKeys.isEmpty()) {
							editorConfigKeys.add(StringPool.BLANK);
						}

						for (String editorConfigKey : editorConfigKeys) {
							List<String> editorNames = StringPlus.asList(
								serviceReference.getProperty("editor.name"));

							if (editorNames.isEmpty()) {
								editorNames.add(StringPool.BLANK);
							}

							for (String editorName : editorNames) {
								emitter.emit(
									_getKey(
										portletName, editorConfigKey,
										editorName));
							}
						}
					}
				}

			};

	private static final ServiceTrackerMap
		<String, List<EditorConfigContributor>> _serviceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				EditorConfigContributor.class,
				"(|(editor.config.key=*)(editor.name=*)(javax.portlet.name=*))",
				_serviceReferenceMapper);

	static {
		_serviceTrackerMap.open();
	}

}