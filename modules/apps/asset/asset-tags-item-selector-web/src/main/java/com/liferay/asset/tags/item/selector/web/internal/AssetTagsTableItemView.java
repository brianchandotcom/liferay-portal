/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.tags.item.selector.criterion.AssetTagsItemSelectorCriterion;
import com.liferay.item.selector.TableItemView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsTableItemView implements TableItemView {

	public AssetTagsTableItemView(
		AssetTag assetTag,
		AssetTagsItemSelectorCriterion assetTagsItemSelectorCriterion) {

		_assetTag = assetTag;
		_assetTagsItemSelectorCriterion = assetTagsItemSelectorCriterion;
	}

	@Override
	public List<String> getHeaderNames() {
		if (_assetTagsItemSelectorCriterion.isMultiSelection()) {
			return ListUtil.fromArray("name", "site");
		}

		return ListUtil.fromArray("name");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setCssClass(
			"entry entry-selector table-cell-smaller table-cell-minw-80");
		nameTextSearchEntry.setName(HtmlUtil.escape(_assetTag.getName()));

		searchEntries.add(nameTextSearchEntry);

		if (_assetTagsItemSelectorCriterion.isMultiSelection()) {
			TextSearchEntry scopeTextSearchEntry = new TextSearchEntry();

			scopeTextSearchEntry.setCssClass(
				"table-cell-expand-smaller table-cell-minw-150");
			scopeTextSearchEntry.setName(
				HtmlUtil.escape(_getGroupDescriptiveName(locale)));

			searchEntries.add(scopeTextSearchEntry);
		}

		return searchEntries;
	}

	private String _getGroupDescriptiveName(Locale locale) {
		try {
			Group group = GroupLocalServiceUtil.fetchGroup(
				_assetTag.getGroupId());

			return group.getDescriptiveName(locale);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetTagsTableItemView.class);

	private final AssetTag _assetTag;
	private final AssetTagsItemSelectorCriterion
		_assetTagsItemSelectorCriterion;

}