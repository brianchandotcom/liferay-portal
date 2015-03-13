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

package com.liferay.portlet.editor.config;

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;
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

/**
 * @author Sergio González
 */
public class PortletEditorConfigFactoryImpl
	implements PortletEditorConfigFactory {

	public PortletEditorConfig getPortletEditorConfig(
		String portletName, String editorConfigKey, ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		List<PortletEditorConfigContributor> portletEditorConfigContributors =
			getPortletEditorConfigContributors(portletName, editorConfigKey);

		return new PortletEditorConfigImpl(
			portletEditorConfigContributors, themeDisplay,
			liferayPortletResponse);
	}

	protected List<PortletEditorConfigContributor>
		getPortletEditorConfigContributors(
			String portletName, String editorConfigKey) {

		List<PortletEditorConfigContributor> portletEditorConfigContributors =
			new ArrayList<>();

		List<PortletEditorConfigContributor>
			portletEditorConfigContributorsByPortletName =
				_serviceTrackerMap.getService(_getKey(portletName, null));

		if (ListUtil.isNotEmpty(portletEditorConfigContributorsByPortletName)) {
			portletEditorConfigContributors.addAll(
				portletEditorConfigContributorsByPortletName);
		}

		if (Validator.isNull(editorConfigKey)) {
			return portletEditorConfigContributors;
		}

		List<PortletEditorConfigContributor>
			portletEditorConfigContributorsByConfigKey =
				_serviceTrackerMap.getService(
					_getKey(portletName, editorConfigKey));

		if (ListUtil.isNotEmpty(portletEditorConfigContributorsByConfigKey)) {
			portletEditorConfigContributors.addAll(
				portletEditorConfigContributorsByConfigKey);
		}

		return portletEditorConfigContributors;
	}

	private static String _getKey(String portletName, String editorConfigKey) {
		if (Validator.isNull(editorConfigKey)) {
			return portletName;
		}

		return portletName.concat(StringPool.PERIOD).concat(editorConfigKey);
	}

	private static final
		ServiceTrackerMap<String, List<PortletEditorConfigContributor>>
		_serviceTrackerMap = ServiceTrackerCollections.multiValueMap(
			PortletEditorConfigContributor.class, "(javax.portlet.name=*)",
			new ServiceReferenceMapper
				<String, PortletEditorConfigContributor>() {

			@Override
			public void map(
				ServiceReference<PortletEditorConfigContributor>
					serviceReference,
				Emitter<String> emitter) {

				List<String> portletNames = StringPlus.asList(
					serviceReference.getProperty("javax.portlet.name"));
				List<String> editorConfigKeys = StringPlus.asList(
					serviceReference.getProperty("editor.config.key"));

				for (String portletName : portletNames) {
					if (editorConfigKeys.isEmpty()) {
						emitter.emit(_getKey(portletName, null));
					}
					else {
						for (String editorConfigKey : editorConfigKeys) {
							emitter.emit(_getKey(portletName, editorConfigKey));
						}
					}
				}
			}

		});

	static {
		_serviceTrackerMap.open();
	}

}