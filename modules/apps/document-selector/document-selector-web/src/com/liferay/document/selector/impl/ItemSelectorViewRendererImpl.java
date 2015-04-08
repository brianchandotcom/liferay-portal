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

package com.liferay.document.selector.impl;

import com.liferay.document.selector.ItemSelectorCriterion;
import com.liferay.document.selector.ItemSelectorView;
import com.liferay.document.selector.ItemSelectorViewRenderer;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Iván Zaera
 */
public class ItemSelectorViewRendererImpl implements ItemSelectorViewRenderer {

	public ItemSelectorViewRendererImpl(
		ItemSelectorView<ItemSelectorCriterion> itemSelectorView,
		ItemSelectorCriterion itemSelectorCriterion) {

		_itemSelectorView = itemSelectorView;
		_itemSelectorCriterion = itemSelectorCriterion;
	}

	public ItemSelectorCriterion getItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	public ItemSelectorView<ItemSelectorCriterion> getItemSelectorView() {
		return _itemSelectorView;
	}

	public void renderHTML(PageContext pageContext, String itemSelectedCallback)
		throws IOException, ServletException {

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		_itemSelectorView.renderHTML(
			pageContext.getRequest(), pipingServletResponse,
			_itemSelectorCriterion, itemSelectedCallback);

		Writer writer = pageContext.getOut();

		StringBundler sb = unsyncStringWriter.getStringBundler();

		writer.write(sb.toString());
	}

	private final ItemSelectorCriterion _itemSelectorCriterion;
	private final ItemSelectorView<ItemSelectorCriterion> _itemSelectorView;

}