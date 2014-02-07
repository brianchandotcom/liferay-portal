/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.germinus.easyconf.ConfigurationSerializer;

import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.StopWatch;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author László Csontos
 */
@PrepareForTest( {
	ConfigurationSerializer.class, LayoutLocalServiceUtil.class,
	LocalizationUtil.class, PropsUtil.class
})
@RunWith(PowerMockRunner.class)
public class LayoutListUtilTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		mockStatic(ConfigurationSerializer.class);

		when(
			ConfigurationSerializer.getSerializer()
		).thenReturn(
			null
		);

		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(Mockito.anyString())
		).thenReturn(
			StringPool.BLANK
		);

		when(
			PropsUtil.getArray(Mockito.anyString())
		).thenReturn(
			new String[0]
		);

		mockStatic(LayoutLocalServiceUtil.class);

		addLayouts(0, 0);

		when(
			LayoutLocalServiceUtil.getLayouts(
				Mockito.anyLong(), Mockito.anyBoolean())
		).thenReturn(
			_layouts
		);

		mockStatic(LocalizationUtil.class);

		when(
			LocalizationUtil.getLocalization()
		).thenReturn(
			new LocalizationImpl()
		);

		String jsonData = ContentUtil.get(
			"com/liferay/portal/util/dependencies/layout_view_list.json");

		_expectedLayoutViewArray = ArrayUtil.toStringArray(
			new JSONArrayImpl(jsonData));

		_expectedLayoutViewArray = ArrayUtil.subset(
			_expectedLayoutViewArray, 1, _expectedLayoutViewArray.length);
	}

	@AfterClass
	public static void tearDownClass() {
		_layouts.clear();
	}

	@Test
	public void testGetLayoutView() throws PortalException, SystemException {
		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		List<LayoutDescription> layoutDescriptions =
			LayoutListUtil.getLayoutDescriptions(0, false, "ROOT", null);

		if (_log.isDebugEnabled()) {
			stopWatch.stop();

			_log.debug("Runtime :" + stopWatch.getTime());
		}

		List<String> actualLayoutViewList = new ArrayList<String>(
			layoutDescriptions.size() - 1);

		for (Iterator<LayoutDescription> itr =
			layoutDescriptions.listIterator(1); itr.hasNext();) {

			LayoutDescription layoutDescription = itr.next();

			actualLayoutViewList.add(
					String.valueOf(layoutDescription.getPlid()) +
					String.valueOf(layoutDescription.getName()) +
					String.valueOf(layoutDescription.getDepth()));
		}

		List<String> expectedLayoutViewList = new ArrayList<String>(
			_expectedLayoutViewArray.length);

		for (String expectedLayoutView : _expectedLayoutViewArray) {
			String[] nodeValues = StringUtil.split(
				expectedLayoutView, StringPool.PIPE);

			String plid = nodeValues[3];
			String name = nodeValues[4];
			String depth = nodeValues[6];

			expectedLayoutViewList.add(plid + name + depth);
		}

		Assert.assertEquals(expectedLayoutViewList, actualLayoutViewList);
	}

	protected static void addLayouts(int depth, long parentLayoutId) {
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

	protected static Layout createLayout(long plid, long parentLayoutId) {
		Layout layout = new MockLayoutImpl();

		layout.setLayoutId(plid);
		layout.setParentLayoutId(parentLayoutId);
		layout.setPrimaryKey(plid);

		return layout;
	}

	private static final int _DEPTH = 3;

	private static final int _NODES = 10;

	private static Log _log = LogFactoryUtil.getLog(LayoutListUtilTest.class);

	private static String[] _expectedLayoutViewArray;
	private static List<Layout> _layouts = new ArrayList<Layout>();
	private static long _plid = 0;

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