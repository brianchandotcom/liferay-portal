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

import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsContributor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorOptionsProvider
	extends BaseEditorConfigurationProvider<EditorOptionsContributor> {

	public EditorOptions getEditorOptions(
		String portletName, String editorConfigKey, String editorName,
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		EditorOptions editorOptions = new EditorOptions();

		Iterator<EditorOptionsContributor> iterator = ListUtil.reverseIterator(
			getContributors(portletName, editorConfigKey, editorName));

		while (iterator.hasNext()) {
			EditorOptionsContributor editorOptionsContributor = iterator.next();

			editorOptionsContributor.populateEditorOptions(
				editorOptions, inputEditorTaglibAttributes, themeDisplay,
				requestBackedPortletURLFactory);
		}

		return editorOptions;
	}

	@Override
	protected ServiceTrackerMap<String, List<EditorOptionsContributor>>
		getServiceTrackerMap() {

		return _serviceTrackerMap;
	}

	private static final ServiceReferenceMapper
		<String, EditorOptionsContributor>
			_serviceReferenceMapper = new EditorServiceReferenceMapper<>();
	private static final ServiceTrackerMap
		<String, List<EditorOptionsContributor>> _serviceTrackerMap =
			ServiceTrackerCollections.openMultiValueMap(
				EditorOptionsContributor.class,
				"(|(editor.config.key=*)(editor.name=*)(javax.portlet.name=*)" +
					"(objectClass=" + EditorOptionsContributor.class.getName() +
						"))",
				_serviceReferenceMapper);

}