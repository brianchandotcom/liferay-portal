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

package com.liferay.wiki.editor.configuration;

/**
 * @author Roberto Díaz
 */

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.wiki.constants.WikiPortletKeys;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
@Component(
	property = {
		"editor.name=ckeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking=:Integer=100"
	},
	service = EditorConfigContributor.class
)
public class WikiCKEditorCreoleConfigContributor
	extends WikiBaseEditorConfigContributor {

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		this.itemSelector = itemSelector;
	}

	@Override
	protected List<ItemSelectorReturnType>
		getUploadItemSelectorCriterionDesiredReturnTypes() {

		return _desiredUploadItemSelectorReturnTypes;
	}

	@Override
	protected List<ItemSelectorReturnType>
		getWikiAttachmentItemSelectorCriterionDesiredReturnTypes() {

		return _desiredWikiAttachmentItemSelectorReturnTypes;
	}

	private static final List<ItemSelectorReturnType>
		_desiredUploadItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new ItemSelectorReturnType[] {
					new UploadableFileReturnType()
				}));
	private static final List<ItemSelectorReturnType>
		_desiredWikiAttachmentItemSelectorReturnTypes =
			Collections.unmodifiableList(
				ListUtil.fromArray(
					new ItemSelectorReturnType[] {
						new UploadableFileReturnType(),
						new FileEntryItemSelectorReturnType()
					}));

}