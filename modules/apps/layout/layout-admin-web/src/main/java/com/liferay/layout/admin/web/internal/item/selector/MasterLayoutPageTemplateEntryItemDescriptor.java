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

package com.liferay.layout.admin.web.internal.item.selector;

import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.layout.admin.web.internal.frontend.taglib.clay.servlet.taglib.SelectMasterLayoutVerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Locale;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class MasterLayoutPageTemplateEntryItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public MasterLayoutPageTemplateEntryItemDescriptor(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"name", _layoutPageTemplateEntry.getName()
		).put(
			"plid", String.valueOf(_layoutPageTemplateEntry.getPlid())
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public VerticalCard getVerticalCard(
		RenderRequest renderRequest, RowChecker rowChecker) {

		return new SelectMasterLayoutVerticalCard(
			_layoutPageTemplateEntry, renderRequest);
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;

}