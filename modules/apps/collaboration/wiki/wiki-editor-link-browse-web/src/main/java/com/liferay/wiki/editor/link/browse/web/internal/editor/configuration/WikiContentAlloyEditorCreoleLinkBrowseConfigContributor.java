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

package com.liferay.wiki.editor.link.browse.web.internal.editor.configuration;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.item.selector.WikiPageTitleItemSelectorReturnType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"editor.config.key=contentEditor", "editor.name=alloyeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=1000"
	},
	service = EditorConfigContributor.class
)
public class WikiContentAlloyEditorCreoleLinkBrowseConfigContributor
	extends BaseWikiContentAlloyEditorLinkBrowseConfigContributor {

	@Override
	protected ItemSelectorReturnType getItemSelectorReturnType() {
		return new WikiPageTitleItemSelectorReturnType();
	}

}