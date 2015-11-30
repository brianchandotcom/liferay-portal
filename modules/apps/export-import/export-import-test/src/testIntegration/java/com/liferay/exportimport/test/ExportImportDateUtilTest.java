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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.lar.PortletDataContextImpl;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.exportimport.lar.ExportImportDateUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class ExportImportDateUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);

		_layoutSet = _layout.getLayoutSet();

		_portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
			_layout, PortletKeys.EXPORT_IMPORT, null);
	}

	@Test
	public void testGetLastPublishDateFromLastPublishDate() throws Exception {
		PortletDataContext portletDataContext = new PortletDataContextImpl(
			null);

		portletDataContext.setGroupId(_group.getGroupId());

		Date portletDataContextLastPublishDate = new Date();

		updateLastPublishDate(
			portletDataContext,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE,
			portletDataContextLastPublishDate);

		Date portletLastPublishDate = new Date();

		updateLastPublishDate(_portletPreferences, portletLastPublishDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletDataContext, _portletPreferences);

		Assert.assertEquals(portletDataContextLastPublishDate, lastPublishDate);
	}

	@Test
	public void testGetLastPublishDateNotFromLastPublishDate()
		throws Exception {

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			null);

		portletDataContext.setGroupId(_group.getGroupId());

		Date portletDataContextLastPublishDate = new Date();

		updateLastPublishDate(
			portletDataContext, ExportImportDateUtil.RANGE_ALL,
			portletDataContextLastPublishDate);

		Date portletLastPublishDate = new Date();

		updateLastPublishDate(_portletPreferences, portletLastPublishDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletDataContext, _portletPreferences);

		Assert.assertEquals(portletDataContextLastPublishDate, lastPublishDate);
	}

	@Test
	public void testGetLastPublishDateWithoutPorltetLastPublishDate()
		throws Exception {

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			null);

		portletDataContext.setGroupId(_group.getGroupId());

		updateLastPublishDate(
			portletDataContext,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE, new Date());

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletDataContext, _portletPreferences);

		Assert.assertNull(lastPublishDate);
	}

	@Test
	public void testGetLastPublishDateWithoutPortletDataContextLastPublishDate()
		throws Exception {

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			null);

		portletDataContext.setGroupId(_group.getGroupId());

		updateLastPublishDate(
			portletDataContext,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE, null);

		Date portletLastPublishDate = new Date();

		updateLastPublishDate(_portletPreferences, portletLastPublishDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletDataContext, _portletPreferences);

		Assert.assertEquals(portletLastPublishDate, lastPublishDate);
	}

	@Test
	public void testUpdateLastPublishDateFirstPublishLayoutSet()
		throws Exception {

		Date now = new Date();

		Date startDate = new Date(now.getTime() + Time.DAY);
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			_layoutSet.getGroupId(), _layoutSet.isPrivateLayout(), dateRange,
			endDate);

		_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_layoutSet.getLayoutSetId());

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_layoutSet);

		// It should be null, since no update should have happened, because it
		// would result in a gap for contents

		Assert.assertNull(lastPublishDate);
	}

	@Test
	public void testUpdateLastPublishDateFirstPublishPortlet()
		throws Exception {

		Date now = new Date();

		Date startDate = new Date(now.getTime() + Time.DAY);
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			PortletKeys.EXPORT_IMPORT, _portletPreferences, dateRange, endDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_portletPreferences);

		// It should be null, since no update should have happened, because it
		// would result in a gap for contents

		Assert.assertNull(lastPublishDate);
	}

	@Test
	public void testUpdateLastPublishDateOverlappingRangeLayoutSet()
		throws Exception {

		Date now = new Date();

		updateLastPublishDate(_layoutSet, now);

		Date startDate = new Date(now.getTime() - Time.DAY);
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			_layoutSet.getGroupId(), _layoutSet.isPrivateLayout(), dateRange,
			endDate);

		_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_layoutSet.getLayoutSetId());

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_layoutSet);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateOverlappingRangePortlet()
		throws Exception {

		Date now = new Date();

		updateLastPublishDate(_portletPreferences, now);

		Date startDate = new Date(now.getTime() - Time.DAY);
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			PortletKeys.EXPORT_IMPORT, _portletPreferences, dateRange, endDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_portletPreferences);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateRangeBeforeLastPublishDateLayoutSet()
		throws Exception {

		Date now = new Date();

		updateLastPublishDate(_layoutSet, now);

		Date startDate = new Date(now.getTime() - Time.WEEK);
		Date endDate = new Date(now.getTime() - Time.DAY);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			_layoutSet.getGroupId(), _layoutSet.isPrivateLayout(), dateRange,
			endDate);

		_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_layoutSet.getLayoutSetId());

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_layoutSet);

		Assert.assertEquals(now.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateRangeBeforeLastPublishDatePortlet()
		throws Exception {

		Date now = new Date();

		updateLastPublishDate(_portletPreferences, now);

		Date startDate = new Date(now.getTime() - Time.WEEK);
		Date endDate = new Date(now.getTime() - Time.DAY);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			PortletKeys.EXPORT_IMPORT, _portletPreferences, dateRange, endDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_portletPreferences);

		Assert.assertEquals(now.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateWithGapLayoutSet() throws Exception {
		Date now = new Date();

		updateLastPublishDate(_layoutSet, now);

		Date startDate = new Date(now.getTime() + Time.DAY);
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			_layoutSet.getGroupId(), _layoutSet.isPrivateLayout(), dateRange,
			endDate);

		_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_layoutSet.getLayoutSetId());

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_layoutSet);

		Assert.assertEquals(now.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateWithGapPortlet() throws Exception {
		Date now = new Date();

		updateLastPublishDate(_portletPreferences, now);

		Date startDate = new Date(now.getTime() + Time.DAY);
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			PortletKeys.EXPORT_IMPORT, _portletPreferences, dateRange, endDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_portletPreferences);

		Assert.assertEquals(now.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateWithoutGapLayoutSet()
		throws Exception {

		Date now = new Date();

		updateLastPublishDate(_layoutSet, now);

		// Start date is exactly the last publish date

		Date startDate = new Date(now.getTime());
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			_layoutSet.getGroupId(), _layoutSet.isPrivateLayout(), dateRange,
			endDate);

		_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_layoutSet.getLayoutSetId());

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_layoutSet);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());

		updateLastPublishDate(_layoutSet, now);

		// End date is exactly the last publish date

		startDate = new Date(now.getTime() - Time.WEEK);
		endDate = new Date(now.getTime());

		dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			_layoutSet.getGroupId(), _layoutSet.isPrivateLayout(), dateRange,
			endDate);

		_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_layoutSet.getLayoutSetId());

		lastPublishDate = ExportImportDateUtil.getLastPublishDate(_layoutSet);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());
	}

	@Test
	public void testUpdateLastPublishDateWithoutGapPortlet() throws Exception {
		Date now = new Date();

		updateLastPublishDate(_portletPreferences, now);

		// Start date is exactly the last publish date

		Date startDate = new Date(now.getTime());
		Date endDate = new Date(now.getTime() + Time.WEEK);

		DateRange dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			PortletKeys.EXPORT_IMPORT, _portletPreferences, dateRange, endDate);

		Date lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_portletPreferences);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());

		updateLastPublishDate(_portletPreferences, now);

		// End date is exactly the last publish date

		startDate = new Date(now.getTime() - Time.WEEK);
		endDate = new Date(now.getTime());

		dateRange = new DateRange(startDate, endDate);

		ExportImportDateUtil.updateLastPublishDate(
			PortletKeys.EXPORT_IMPORT, _portletPreferences, dateRange, endDate);

		lastPublishDate = ExportImportDateUtil.getLastPublishDate(
			_portletPreferences);

		Assert.assertEquals(endDate.getTime(), lastPublishDate.getTime());
	}

	protected void updateLastPublishDate(
			LayoutSet layoutSet, Date lastPublishDate)
		throws Exception {

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.setProperty(
			"last-publish-date", String.valueOf(lastPublishDate.getTime()));

		LayoutSetLocalServiceUtil.updateSettings(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			settingsProperties.toString());
	}

	protected void updateLastPublishDate(
			PortletDataContext portletDataContext, String range, Date startDate)
		throws Exception {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		if (portletDataContext.getParameterMap() == null) {
			parameterMap = new HashMap<>();

			portletDataContext.setParameterMap(parameterMap);
		}

		parameterMap.put("range", new String[] {range});

		portletDataContext.setStartDate(startDate);
	}

	protected void updateLastPublishDate(
			PortletPreferences portletPreferences, Date lastPublishDate)
		throws Exception {

		portletPreferences.setValue(
			"last-publish-date", String.valueOf(lastPublishDate.getTime()));

		portletPreferences.store();
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private LayoutSet _layoutSet;
	private PortletPreferences _portletPreferences;

}