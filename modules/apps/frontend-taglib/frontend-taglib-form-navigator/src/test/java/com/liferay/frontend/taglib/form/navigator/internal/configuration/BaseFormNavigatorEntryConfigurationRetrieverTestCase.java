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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PropsImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseFormNavigatorEntryConfigurationRetrieverTestCase {

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Before
	public void setUp() throws Exception {
		formNavigatorEntryConfigurationRetriever.setServiceTrackerMap(
			_serviceTrackerMap);
	}

	@After
	public final void tearDown() {
		formNavigatorEntryConfigurationRetriever.deactivate();
	}

	protected void createConfiguration(
		String formNavigatorId, String[] formNavigatorEntryKeys) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"formNavigatorEntryKeys", formNavigatorEntryKeys
		).put(
			"formNavigatorId", formNavigatorId
		).build();

		FormNavigatorEntryConfigurationParser
			formNavigatorEntryConfigurationParser =
				new FormNavigatorEntryConfigurationParser();

		formNavigatorEntryConfigurationParser.activate(properties);

		_serviceTrackerMap.register(
			formNavigatorId, formNavigatorEntryConfigurationParser);
	}

	protected void deleteConfiguration(String formNavigatorId) {
		_serviceTrackerMap.unregister(formNavigatorId);
	}

	protected FormNavigatorEntryConfigurationRetriever
		formNavigatorEntryConfigurationRetriever =
			new FormNavigatorEntryConfigurationRetriever();

	private final MockServiceTrackerMap _serviceTrackerMap =
		new MockServiceTrackerMap();

	private final class MockServiceTrackerMap
		implements ServiceTrackerMap
			<String, List<FormNavigatorEntryConfigurationParser>> {

		@Override
		public void close() {
			_formNavigatorEntryConfigurationParserMap.clear();
		}

		@Override
		public boolean containsKey(String formNavigatorId) {
			return _formNavigatorEntryConfigurationParserMap.containsKey(
				formNavigatorId);
		}

		@Override
		public List<FormNavigatorEntryConfigurationParser> getService(
			String formNavigatorId) {

			return _formNavigatorEntryConfigurationParserMap.get(
				formNavigatorId);
		}

		@Override
		public Set<String> keySet() {
			return _formNavigatorEntryConfigurationParserMap.keySet();
		}

		public void register(
			String formNavigatorId,
			FormNavigatorEntryConfigurationParser
				formNavigatorEntryConfigurationParser) {

			List<FormNavigatorEntryConfigurationParser>
				formNavigatorEntryConfigurationParsers =
					_formNavigatorEntryConfigurationParserMap.computeIfAbsent(
						formNavigatorId, key -> new ArrayList<>());

			formNavigatorEntryConfigurationParsers.add(
				formNavigatorEntryConfigurationParser);
		}

		public void unregister(String formNavigatorId) {
			_formNavigatorEntryConfigurationParserMap.remove(formNavigatorId);
		}

		@Override
		public Collection<List<FormNavigatorEntryConfigurationParser>>
			values() {

			return _formNavigatorEntryConfigurationParserMap.values();
		}

		private final Map<String, List<FormNavigatorEntryConfigurationParser>>
			_formNavigatorEntryConfigurationParserMap = new HashMap<>();

	}

}