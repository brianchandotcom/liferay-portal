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

package com.liferay.portal.search.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.constants.SearchPortletKeys;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {SearchPortletKeys.SEARCH};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		portletPreferences.setValue(
			"searchScope",
			SearchScopePreference.LET_THE_USER_CHOOSE.getPreferenceString());

		upgradeSearchConfiguration(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeSearchConfiguration(
			PortletPreferences portletPreferences)
		throws Exception {

		String searchConfiguration = portletPreferences.getValue(
			"searchConfiguration", StringPool.BLANK);

		for (String[] classNames : _CLASS_NAMES) {
			searchConfiguration = StringUtil.replace(
				searchConfiguration, classNames[0], classNames[1]);
		}

		portletPreferences.setValue("searchConfiguration", searchConfiguration);
	}

	private static final String[][] _CLASS_NAMES = new String[][] {
		{
			"com.liferay.portlet.bookmarks.model.BookmarksEntry",
			"com.liferay.bookmarks.model.BookmarksEntry"
		},
		{
			"com.liferay.portlet.bookmarks.model.BookmarksFolder",
			"com.liferay.bookmarks.model.BookmarksFolder"
		},
		{
			"com.liferay.portlet.dynamicdatalists.model.DDLRecord",
			"com.liferay.dynamic.data.list.model.DDLRecord"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileEntry",
			"com.liferay.document.library.kernel.model.DLFileEntry"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFolder",
			"com.liferay.document.library.kernel.model.DLFolder"
		},
		{
			"com.liferay.portlet.journal.model.JournalArticle",
			"com.liferay.journal.model.JournalArticle"
		},
		{
			"com.liferay.portlet.journal.model.JournalFolder",
			"com.liferay.journal.model.JournalFolder"
		},
		{
			"com.liferay.portlet.messageboards.model.MBCategory",
			"com.liferay.message.boards.kernel.model.MBCategory"
		},
		{
			"com.liferay.portlet.messageboards.model.MBMessage",
			"com.liferay.message.boards.kernel.model.MBMessage"
		},
		{
			"com.liferay.portlet.wiki.model.WikiPage",
			"com.liferay.wiki.model.WikiPage"
		}
	};

}