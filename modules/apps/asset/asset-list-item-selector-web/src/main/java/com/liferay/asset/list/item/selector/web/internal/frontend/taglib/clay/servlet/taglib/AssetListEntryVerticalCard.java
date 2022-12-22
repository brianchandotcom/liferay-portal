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

package com.liferay.asset.list.item.selector.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.item.selector.web.internal.display.context.AssetListEntryItemSelectorDisplayContext;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetListEntryVerticalCard extends BaseVerticalCard {

	public AssetListEntryVerticalCard(
		AssetListEntry assetListEntry,
		AssetListEntryItemSelectorDisplayContext
			assetListEntryItemSelectorDisplayContext,
		RenderRequest renderRequest, RowChecker rowChecker) {

		super(null, renderRequest, rowChecker);

		_assetListEntry = assetListEntry;
		_assetListEntryItemSelectorDisplayContext =
			assetListEntryItemSelectorDisplayContext;
	}

	@Override
	public String getCssClass() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		if (_assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_DYNAMIC) {

			return "bolt";
		}

		return "list";
	}

	@Override
	public String getInputValue() {
		return null;
	}

	@Override
	public List<LabelItem> getLabels() {
		int assetListEntrySegmentsEntryRelsCount =
			_assetListEntryItemSelectorDisplayContext.
				getAssetListEntrySegmentsEntryRelsCount(_assetListEntry);

		if (assetListEntrySegmentsEntryRelsCount > 0) {
			return LabelItemListBuilder.add(
				labelItem -> {
					labelItem.setDisplayType("info");
					labelItem.setLabel(
						LanguageUtil.format(
							themeDisplay.getLocale(), "x-variations",
							new String[] {
								String.valueOf(
									assetListEntrySegmentsEntryRelsCount)
							}));
				}
			).build();
		}

		return LabelItemListBuilder.add(
			labelItem -> labelItem.setLabel(
				LanguageUtil.get(themeDisplay.getLocale(), "no-variations"))
		).build();
	}

	@Override
	public String getStickerIcon() {
		return "filter";
	}

	@Override
	public String getSubtitle() {
		String type = _assetListEntryItemSelectorDisplayContext.getType(
			_assetListEntry, themeDisplay.getLocale());

		String subtype = _assetListEntryItemSelectorDisplayContext.getSubtype(
			_assetListEntry);

		if (Validator.isNull(subtype)) {
			return type;
		}

		return type + " - " + subtype;
	}

	@Override
	public String getTitle() {
		return _assetListEntry.getTitle();
	}

	@Override
	public Boolean isFlushHorizontal() {
		return true;
	}

	private final AssetListEntry _assetListEntry;
	private final AssetListEntryItemSelectorDisplayContext
		_assetListEntryItemSelectorDisplayContext;

}