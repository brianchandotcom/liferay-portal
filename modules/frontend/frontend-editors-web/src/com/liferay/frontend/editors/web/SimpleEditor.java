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

package com.liferay.frontend.editors.web;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.editor.Editor;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Roberto Díaz
 */
@Component(service = Editor.class)
public class SimpleEditor extends BaseEditor {

	@Override
	public String getName() {
		return "simple";
	}

	@Reference
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	@Override
	public void setItemSelectorAttribute(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:input-editor:itemSelector", _itemSelector);
	}

	@Override
	protected String getJspPath() {
		return "/editors/simple.jsp";
	}

	private ItemSelector _itemSelector;

}