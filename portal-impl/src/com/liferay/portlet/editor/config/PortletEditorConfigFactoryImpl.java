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

		populateEditorConfigByPortletNameAndConfigKey(
			portletEditorConfigContributors, portletName, editorConfigKey);

		populateEditorConfigByPortletName(
			portletEditorConfigContributors, portletName);

		populateEditorConfigByEditorConfigKey(
			portletEditorConfigContributors, editorConfigKey);

		return portletEditorConfigContributors;
	}

	protected void populateEditorConfigByEditorConfigKey(
		List<PortletEditorConfigContributor> portletEditorConfigContributors,
		String editorConfigKey) {

		if (Validator.isNull(editorConfigKey)) {
			return;
		}

		List<PortletEditorConfigContributor>
			portletEditorConfigContributorByEditorConfigKey =
				_editorConfigServiceTrackerMap.getService(editorConfigKey);

		if (ListUtil.isNotEmpty(
				portletEditorConfigContributorByEditorConfigKey)) {

			portletEditorConfigContributors.addAll(
				portletEditorConfigContributorByEditorConfigKey);
		}
	}

	protected void populateEditorConfigByPortletName(
		List<PortletEditorConfigContributor> portletEditorConfigContributors,
		String portletName) {

		if (Validator.isNull(portletName)) {
			return;
		}

		List<PortletEditorConfigContributor>
			portletEditorConfigContributorByPortletName =
				_portletConfigServiceTrackerMap.getService(portletName);

		if (ListUtil.isNotEmpty(portletEditorConfigContributorByPortletName)) {
			portletEditorConfigContributors.addAll(
				portletEditorConfigContributorByPortletName);
		}
	}

	protected void populateEditorConfigByPortletNameAndConfigKey(
		List<PortletEditorConfigContributor> portletEditorConfigContributors,
		String portletName, String editorConfigKey) {

		List<PortletEditorConfigContributor>
			portletEditorConfigContributorsByPortletNameAndConfigKey =
				_portletEditorConfigServiceTrackerMap.getService(
					portletName + StringPool.PERIOD + editorConfigKey);

		if (ListUtil.isNotEmpty(
				portletEditorConfigContributorsByPortletNameAndConfigKey)) {

			portletEditorConfigContributors.addAll(
				portletEditorConfigContributorsByPortletNameAndConfigKey);
		}
	}

	private static final
		ServiceTrackerMap<String, List<PortletEditorConfigContributor>>
		_editorConfigServiceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				PortletEditorConfigContributor.class,
				"(&(editor.config.key=*)(!(javax.portlet.name=*)))",
				new ServiceReferenceMapper
					<String, PortletEditorConfigContributor>() {

				@Override
				public void map(
					ServiceReference<PortletEditorConfigContributor>
						serviceReference,
					Emitter<String> emitter) {

					List<String> editorConfigKeys = StringPlus.asList(
						serviceReference.getProperty("editor.config.key"));

					for (String editorConfigKey : editorConfigKeys) {
						emitter.emit(editorConfigKey);
					}
				}

			});

	private static final
		ServiceTrackerMap<String, List<PortletEditorConfigContributor>>
		_portletConfigServiceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				PortletEditorConfigContributor.class,
				"(&(javax.portlet.name=*)(!(editor.config.key=*)))",
				new ServiceReferenceMapper
					<String, PortletEditorConfigContributor>() {

				@Override
				public void map(
					ServiceReference<PortletEditorConfigContributor>
						serviceReference,
					Emitter<String> emitter) {

					List<String> portletNames = StringPlus.asList(
						serviceReference.getProperty("javax.portlet.name"));

					for (String portletName : portletNames) {
						emitter.emit(portletName);
					}
				}

			});

	private static final
		ServiceTrackerMap<String, List<PortletEditorConfigContributor>>
		_portletEditorConfigServiceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				PortletEditorConfigContributor.class,
				"(&(javax.portlet.name=*)(editor.config.key=*))",
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
						for (String editorConfigKey : editorConfigKeys) {
							emitter.emit(
								portletName + StringPool.PERIOD +
									editorConfigKey);
						}
					}
				}

			});

	static {
		_editorConfigServiceTrackerMap.open();
		_portletConfigServiceTrackerMap.open();
		_portletEditorConfigServiceTrackerMap.open();
	}

}