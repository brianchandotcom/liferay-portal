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

package com.liferay.asset.publisher.web.internal.upgrade.v1_0_1;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;

import java.util.Objects;

import javax.portlet.PortletPreferences;

/**
 * @author Cristina Rodríguez
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	public UpgradePortletPreferences(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			AssetPublisherPortletKeys.ASSET_PUBLISHER + "_INSTANCE_%"
		};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		if (ArrayUtil.isNotEmpty(assetEntryXmls)) {
			upgradeTypes(assetEntryXmls);

			portletPreferences.setValues("assetEntryXml", assetEntryXmls);
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeTypes(String[] assetEntryXmls) throws Exception {
		for (int i = 0; i < assetEntryXmls.length; i++) {
			String assetEntry = assetEntryXmls[i];

			Document document = _saxReader.read(assetEntry);

			Element rootElement = document.getRootElement();

			Element assetEntryTypeElement = rootElement.element(
				"asset-entry-type");

			if (assetEntryTypeElement == null) {
				continue;
			}

			String assetEntryType = assetEntryTypeElement.getText();

			String newAssetEntryType = assetEntryType;

			for (String[] classNames : _CLASS_NAMES) {
				newAssetEntryType = StringUtil.replace(
					newAssetEntryType, classNames[0], classNames[1]);
			}

			if (Objects.equals(assetEntryType, newAssetEntryType)) {
				continue;
			}

			rootElement.remove(assetEntryTypeElement);

			assetEntryTypeElement.setText(newAssetEntryType);

			rootElement.add(assetEntryTypeElement);

			document.setRootElement(rootElement);

			assetEntryXmls[i] = document.formattedString(StringPool.BLANK);
		}
	}

	private static final String[][] _CLASS_NAMES = {
		{
			"com.liferay.portlet.blogs.model.BlogsEntry",
			"com.liferay.blogs.model.BlogsEntry"
		},
		{
			"com.liferay.portlet.bookmarks.model.BookmarksEntry",
			"com.liferay.bookmarks.model.BookmarksEntry"
		},
		{
			"com.liferay.portlet.bookmarks.model.BookmarksFolder",
			"com.liferay.bookmarks.model.BookmarksFolder"
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
			"com.liferay.portlet.messageboards.model.MBMessage",
			"com.liferay.message.boards.model.MBMessage"
		},
		{
			"com.liferay.portlet.wiki.model.WikiPage",
			"com.liferay.wiki.model.WikiPage"
		}
	};

	private final SAXReader _saxReader;

}