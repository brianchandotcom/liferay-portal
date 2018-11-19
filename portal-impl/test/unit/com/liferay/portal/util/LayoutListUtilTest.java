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

package com.liferay.portal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.ConfigurationImpl;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.LayoutImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.StopWatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author László Csontos
 */
public class LayoutListUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		LocalizationUtil localizationUtil = new LocalizationUtil();

		localizationUtil.setLocalization(new LocalizationImpl());

		ReflectionTestUtil.setFieldValue(
			PropsUtil.class, "_configuration",
			new ConfigurationImpl(
				PropsUtil.class.getClassLoader(), PropsFiles.PORTAL, 0, null) {

				@Override
				public String get(String key) {
					return StringPool.BLANK;
				}

				@Override
				public String[] getArray(String key) {
					return new String[0];
				}

			});
	}

	@Before
	public void setUp() throws Exception {
		addLayouts(0, 0);

		ReflectionTestUtil.setFieldValue(
			LayoutLocalServiceUtil.class, "_service",
			new LayoutLocalServiceWrapper(null) {

				@Override
				public List<Layout> getLayouts(
					long groupId, boolean privateLayout) {

					return _layouts;
				}

			});

		Class<?> clazz = getClass();

		String json = StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/portal/util/dependencies/layout_list_util.json");

		_layoutListUtilStrings = ArrayUtil.toStringArray(
			new JSONArrayImpl(json));

		_layoutListUtilStrings = ArrayUtil.subset(
			_layoutListUtilStrings, 1, _layoutListUtilStrings.length);
	}

	@After
	public void tearDown() {
		_layouts.clear();
	}

	@Test
	public void testGetLayoutDescriptions() {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		List<LayoutDescription> layoutDescriptions =
			LayoutListUtil.getLayoutDescriptions(0, false, "ROOT", null);

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug(
				"Retrieved layout descriptions in " + stopWatch.getTime() +
					" ms");
		}

		List<String> expectedLayoutDescriptionStrings = new ArrayList<>(
			_layoutListUtilStrings.length);

		for (String layoutListUtilString : _layoutListUtilStrings) {
			String[] layoutDescriptionStringParts = StringUtil.split(
				layoutListUtilString, StringPool.PIPE);

			String plid = layoutDescriptionStringParts[3];
			String name = layoutDescriptionStringParts[4];
			String depth = layoutDescriptionStringParts[6];

			expectedLayoutDescriptionStrings.add(plid + name + depth);
		}

		List<String> actualLayoutDescriptionStrings = new ArrayList<>(
			layoutDescriptions.size() - 1);

		Iterator<LayoutDescription> iterator = layoutDescriptions.listIterator(
			1);

		while (iterator.hasNext()) {
			LayoutDescription layoutDescription = iterator.next();

			actualLayoutDescriptionStrings.add(
				layoutDescription.getPlid() + layoutDescription.getName() +
					layoutDescription.getDepth());
		}

		Assert.assertEquals(
			expectedLayoutDescriptionStrings, actualLayoutDescriptionStrings);
	}

	protected void addLayouts(int depth, long parentLayoutId) {
		if (depth >= _DEPTH) {
			return;
		}

		Layout layout = createLayout(++_plid, parentLayoutId);

		_layouts.add(layout);

		for (int i = 0; i < _NODES; i++) {
			Layout childLayout = createLayout(++_plid, layout.getPlid());

			_layouts.add(childLayout);

			addLayouts(depth + 1, childLayout.getPlid());
		}
	}

	protected Layout createLayout(long plid, long parentLayoutId) {
		Layout layout = new MockLayoutImpl();

		layout.setPrimaryKey(plid);
		layout.setLayoutId(plid);
		layout.setParentLayoutId(parentLayoutId);

		return layout;
	}

	private static final int _DEPTH = 3;

	private static final int _NODES = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutListUtilTest.class);

	private String[] _layoutListUtilStrings;
	private final List<Layout> _layouts = new ArrayList<>();
	private long _plid;

	private static class MockLayoutImpl extends LayoutImpl {

		@Override
		public String getName() {
			return toString();
		}

		@Override
		public String getName(Locale locale) {
			return toString();
		}

		@Override
		public String toString() {
			return getParentLayoutId() + "/" + getPlid();
		}

	}

}