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
public class PortletEditorConfigFactoryImpl
	implements PortletEditorConfigFactory {

	@Override
	public PortletEditorConfig getPortletEditorConfig(
		String portletName, String editorConfigKey, String editorImpl,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		List<PortletEditorConfigContributor> portletEditorConfigContributors =
			getPortletEditorConfigContributors(
				portletName, editorConfigKey, editorImpl);

		return new PortletEditorConfigImpl(
			portletEditorConfigContributors, inputEditorTaglibAttributes,
			themeDisplay, liferayPortletResponse);
	}

	protected List<PortletEditorConfigContributor>
		getPortletEditorConfigContributors(
			String portletName, String editorConfigKey, String editorImpl) {

		List<PortletEditorConfigContributor> portletEditorConfigContributors =
			new ArrayList<>();

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(portletName, editorConfigKey, editorImpl));

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(portletName, editorConfigKey, null));

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(null, editorConfigKey, editorImpl));

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(portletName, null, editorImpl));

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(null, editorConfigKey, null));

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(portletName, null, null));

		populateEditorConfigContributor(
			portletEditorConfigContributors,
			_getKey(null, null, editorImpl));

		return portletEditorConfigContributors;
	}

	protected void populateEditorConfigContributor(
		List<PortletEditorConfigContributor> portletEditorConfigContributors,
		String key) {

		List<PortletEditorConfigContributor>
			curPortletEditorConfigContributors =
				_editorConfigServiceTrackerMap.getService(key);

		if (ListUtil.isNotEmpty(curPortletEditorConfigContributors)) {
			portletEditorConfigContributors.addAll(
				curPortletEditorConfigContributors);
		}
	}

	private static String _getKey(
		String portletName, String editorConfigKey, String editorImpl) {

		if (Validator.isNull(portletName)) {
			portletName = "null";
		}

		if (Validator.isNull(editorConfigKey)) {
			editorConfigKey = "null";
		}

		if (Validator.isNull(editorImpl)) {
			editorImpl = "null";
		}

		StringBundler sb = new StringBundler();

		sb.append(portletName);
		sb.append(StringPool.PERIOD);
		sb.append(editorConfigKey);
		sb.append(StringPool.PERIOD);
		sb.append(editorImpl);

		return sb.toString();
	}

	private static final
		ServiceTrackerMap<String, List<PortletEditorConfigContributor>>
		_editorConfigServiceTrackerMap =
			ServiceTrackerCollections.multiValueMap(
				PortletEditorConfigContributor.class,
				"(|(javax.portlet.name=*)(editor.config.key=*)(editor.impl=*))",
				new ServiceReferenceMapper
					<String, PortletEditorConfigContributor>() {

				@Override
				public void map(
					ServiceReference<PortletEditorConfigContributor>
						serviceReference,
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
							List<String> editorImpls = StringPlus.asList(
								serviceReference.getProperty("editor.impl"));

							if (editorImpls.isEmpty()) {
								editorImpls.add(StringPool.BLANK);
							}

							for (String editorImpl : editorImpls) {
								emitter.emit(
									_getKey(
										portletName, editorConfigKey,
										editorImpl));
							}
						}
					}
				}

			});

	static {
		_editorConfigServiceTrackerMap.open();
	}

}