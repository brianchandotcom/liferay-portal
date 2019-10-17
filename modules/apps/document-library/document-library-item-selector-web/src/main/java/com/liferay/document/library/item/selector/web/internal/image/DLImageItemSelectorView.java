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

package com.liferay.document.library.item.selector.web.internal.image;

import com.liferay.document.library.item.selector.web.internal.BaseDLItemSelectorView;
import com.liferay.document.library.item.selector.web.internal.configuration.DLImageItemSelectorViewConfiguration;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorViewConstants;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.DownloadURLItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.document.library.item.selector.web.internal.configuration.DLImageItemSelectorViewConfiguration",
	property = {
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_IMAGE_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.order:Integer=100"
	},
	service = ItemSelectorView.class
)
public class DLImageItemSelectorView
	extends BaseDLItemSelectorView<ImageItemSelectorCriterion> {

	@Override
	public String[] getExtensions() {
		return _dlImageItemSelectorViewConfiguration.validExtensions();
	}

	@Override
	public Class<ImageItemSelectorCriterion> getItemSelectorCriterionClass() {
		return ImageItemSelectorCriterion.class;
	}

	@Override
	public String[] getMimeTypes() {
		return PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlImageItemSelectorViewConfiguration =
			ConfigurableUtil.createConfigurable(
				DLImageItemSelectorViewConfiguration.class, properties);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.unmodifiableList(
			ListUtil.fromArray(
				new DownloadFileEntryItemSelectorReturnType(),
				new DownloadURLItemSelectorReturnType(),
				new FileEntryItemSelectorReturnType(),
				new URLItemSelectorReturnType()));

	private volatile DLImageItemSelectorViewConfiguration
		_dlImageItemSelectorViewConfiguration;

}