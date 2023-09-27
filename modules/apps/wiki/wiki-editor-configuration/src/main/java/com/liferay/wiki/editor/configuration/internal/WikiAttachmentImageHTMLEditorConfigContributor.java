/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		"editor.config.key=contentEditor", "editor.name=alloyeditor",
		"editor.name=ckeditor", "javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=100"
	},
	service = EditorConfigContributor.class
)
public class WikiAttachmentImageHTMLEditorConfigContributor
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

		ItemSelectorCriterion imageItemSelectorCriterion =
			getImageItemSelectorCriterion(
				new FileEntryItemSelectorReturnType());

		if (wikiPageResourcePrimKey == 0) {
			return String.valueOf(
				_itemSelector.getItemSelectorURL(
					requestBackedPortletURLFactory, itemSelectedEventName,
					imageItemSelectorCriterion, getURLItemSelectorCriterion()));
		}

		ItemSelectorCriterion attachmentItemSelectorCriterion =
			getWikiAttachmentItemSelectorCriterion(
				wikiPageResourcePrimKey, new FileEntryItemSelectorReturnType());

		ItemSelectorCriterion uploadItemSelectorCriterion =
			getUploadItemSelectorCriterion(
				wikiPageResourcePrimKey, themeDisplay,
				requestBackedPortletURLFactory);

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, itemSelectedEventName,
				attachmentItemSelectorCriterion, imageItemSelectorCriterion,
				getURLItemSelectorCriterion(), uploadItemSelectorCriterion));
	}

	@Override
	protected WikiFileUploadConfiguration getWikiFileUploadConfiguration() {
		return _wikiFileUploadConfiguration;
	}

	protected void setWikiFileUploadConfiguration(
		WikiFileUploadConfiguration wikiFileUploadConfiguration) {

		_wikiFileUploadConfiguration = wikiFileUploadConfiguration;
	}

	@Reference
	private ItemSelector _itemSelector;

	private volatile WikiFileUploadConfiguration _wikiFileUploadConfiguration;

}