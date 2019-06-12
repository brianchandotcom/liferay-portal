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

package com.liferay.wiki.editor.configuration.internal;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.wiki.configuration.WikiFileUploadConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	configurationPid = "com.liferay.wiki.configuration.WikiFileUploadConfiguration",
	property = {
		"editor.config.key=contentEditor", "editor.name=alloyeditor_creole",
		"editor.name=ckeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=100"
	},
	service = EditorConfigContributor.class
)
public class WikiAttachmentImageCreoleEditorConfigContributor
	extends BaseWikiAttachmentImageEditorConfigContributor {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wikiFileUploadConfiguration = ConfigurableUtil.createConfigurable(
			WikiFileUploadConfiguration.class, properties);
	}

	@Override
	protected String getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String itemSelectedEventName, long wikiPageResourcePrimKey,
		ThemeDisplay themeDisplay) {

		if (wikiPageResourcePrimKey == 0) {
			PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, itemSelectedEventName,
				getURLItemSelectorCriterion());

			return itemSelectorURL.toString();
		}

		ItemSelectorCriterion attachmentItemSelectorCriterion =
			getWikiAttachmentItemSelectorCriterion(
				wikiPageResourcePrimKey, new FileEntryItemSelectorReturnType());

		ItemSelectorCriterion uploadItemSelectorCriterion =
			getUploadItemSelectorCriterion(
				wikiPageResourcePrimKey, themeDisplay,
				requestBackedPortletURLFactory);

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, itemSelectedEventName,
			attachmentItemSelectorCriterion, getURLItemSelectorCriterion(),
			uploadItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Override
	protected WikiFileUploadConfiguration getWikiFileUploadConfiguration() {
		return _wikiFileUploadConfiguration;
	}

	@Reference(unbind = "-")
	protected void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected void setWikiFileUploadConfiguration(
		WikiFileUploadConfiguration wikiFileUploadConfiguration) {

		_wikiFileUploadConfiguration = wikiFileUploadConfiguration;
	}

	private ItemSelector _itemSelector;
	private WikiFileUploadConfiguration _wikiFileUploadConfiguration;

}