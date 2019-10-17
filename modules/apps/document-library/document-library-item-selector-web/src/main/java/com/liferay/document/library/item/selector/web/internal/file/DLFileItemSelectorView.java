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

package com.liferay.document.library.item.selector.web.internal.file;

import com.liferay.document.library.item.selector.web.internal.BaseDLItemSelectorView;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorViewConstants;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.DownloadURLItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_FILE_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.order:Integer=100"
	},
	service = ItemSelectorView.class
)
public class DLFileItemSelectorView
	extends BaseDLItemSelectorView<FileItemSelectorCriterion> {

	@Override
	public Class<FileItemSelectorCriterion> getItemSelectorCriterionClass() {
		return FileItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new DownloadURLItemSelectorReturnType(),
				new FileEntryItemSelectorReturnType(),
				new URLItemSelectorReturnType()));

}