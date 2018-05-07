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

package com.liferay.exportimport.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.exportimport.test.util.lar.BaseExportImportTestCase;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Date;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class PortletImportControllerTest extends BaseExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();

		exportImportLayouts(
			new long[] {layout.getLayoutId()}, getImportParameterMap());

		importedGroupLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(),
			layout.isPrivateLayout());
	}

	@Test
	public void testUdatePortletPreferencesPlid0NotNull() throws Exception {
		Date lastPublishDate = new Date(System.currentTimeMillis());

		PortletPreferencesImpl portletPreferencesImpl = setLastPublishDate(
			group, lastPublishDate, null);

		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesImpl.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesImpl.getOwnerType());
		Assert.assertEquals(
			LayoutConstants.DEFAULT_PLID, portletPreferencesImpl.getPlid());

		exportLayouts(
			new long[] {layout.getLayoutId()}, getExportParameterMap());

		importLayouts(getImportParameterMap());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				importedGroup.getCompanyId(), importedGroup.getGroupId(),
				BookmarksPortletKeys.BOOKMARKS);

		Assert.assertEquals(
			Long.valueOf(lastPublishDate.getTime()),
			Long.valueOf(
				portletPreferences.getValue("last-publish-date", null)));
	}

	@Test
	public void testUdatePortletPreferencesPlid1NotNullNotNull()
		throws Exception {

		Date lastPublishDate1 = new Date(System.currentTimeMillis() - 1);
		Date lastPublishDate2 = new Date(System.currentTimeMillis());

		PortletPreferencesImpl portletPreferencesImpl1 = setLastPublishDate(
			group, lastPublishDate1, layout);

		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesImpl1.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesImpl1.getOwnerType());
		Assert.assertEquals(
			layout.getPlid(), portletPreferencesImpl1.getPlid());

		PortletPreferencesImpl portletPreferencesImpl2 = setLastPublishDate(
			importedGroup, lastPublishDate2, importedGroupLayout);

		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesImpl2.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesImpl2.getOwnerType());
		Assert.assertEquals(
			importedGroupLayout.getPlid(), portletPreferencesImpl2.getPlid());

		exportLayouts(
			new long[] {layout.getLayoutId()}, getExportParameterMap());

		importLayouts(getImportParameterMap());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				importedGroupLayout, BookmarksPortletKeys.BOOKMARKS);

		Assert.assertEquals(
			Long.valueOf(lastPublishDate2.getTime()),
			Long.valueOf(
				portletPreferences.getValue("last-publish-date", null)));
	}

	@Test
	public void testUdatePortletPreferencesPlid1NotNullNull() throws Exception {
		Date lastPublishDate = new Date(System.currentTimeMillis());

		PortletPreferencesImpl portletPreferencesImpl = setLastPublishDate(
			group, lastPublishDate, layout);

		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesImpl.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesImpl.getOwnerType());
		Assert.assertEquals(layout.getPlid(), portletPreferencesImpl.getPlid());

		exportLayouts(
			new long[] {layout.getLayoutId()}, getExportParameterMap());

		importLayouts(getImportParameterMap());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				importedGroupLayout, BookmarksPortletKeys.BOOKMARKS);

		Assert.assertNull(
			portletPreferences.getValue("last-publish-date", null));
	}

	@Test
	public void testUdatePortletPreferencesPlid1NullNotNull() throws Exception {
		Date lastPublishDate = new Date(System.currentTimeMillis());

		PortletPreferencesImpl portletPreferencesImpl = setLastPublishDate(
			importedGroup, lastPublishDate, importedGroupLayout);

		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			portletPreferencesImpl.getOwnerId());
		Assert.assertEquals(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			portletPreferencesImpl.getOwnerType());
		Assert.assertEquals(
			importedGroupLayout.getPlid(), portletPreferencesImpl.getPlid());

		exportLayouts(
			new long[] {layout.getLayoutId()}, getExportParameterMap());

		importLayouts(getImportParameterMap());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				importedGroupLayout, BookmarksPortletKeys.BOOKMARKS);

		Assert.assertEquals(
			Long.valueOf(lastPublishDate.getTime()),
			Long.valueOf(
				portletPreferences.getValue("last-publish-date", null)));
	}

	protected PortletPreferencesImpl setLastPublishDate(
			Group group, Date lastPublishDate, Layout layout)
		throws Exception {

		PortletPreferences portletPreferences = null;

		if (layout == null) {
			portletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					group.getCompanyId(), group.getGroupId(),
					BookmarksPortletKeys.BOOKMARKS);
		}
		else {
			portletPreferences =
				PortletPreferencesFactoryUtil.getStrictPortletSetup(
					layout, BookmarksPortletKeys.BOOKMARKS);
		}

		portletPreferences.setValue(
			"last-publish-date", String.valueOf(lastPublishDate.getTime()));

		portletPreferences.store();

		return (PortletPreferencesImpl)portletPreferences;
	}

	protected Layout importedGroupLayout;

}