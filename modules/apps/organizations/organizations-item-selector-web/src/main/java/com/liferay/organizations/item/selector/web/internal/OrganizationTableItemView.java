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

package com.liferay.organizations.item.selector.web.internal;

import com.liferay.item.selector.TableItemView;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class OrganizationTableItemView implements TableItemView {

	public OrganizationTableItemView(Organization organization) {
		_organization = organization;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray("name", "path", "type");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setName(HtmlUtil.escape(_organization.getName()));
		nameTextSearchEntry.setCssClass(
			"entry entry-selector table-cell-expand table-cell-minw-200");

		searchEntries.add(nameTextSearchEntry);

		TextSearchEntry pathTextSearchEntry = new TextSearchEntry();

		pathTextSearchEntry.setName(_getPath(_organization));
		pathTextSearchEntry.setCssClass(
			"table-cell-expand-smaller table-cell-minw-150");

		searchEntries.add(pathTextSearchEntry);

		TextSearchEntry typeTextSearchEntry = new TextSearchEntry();

		typeTextSearchEntry.setName(
			LanguageUtil.get(locale, _organization.getType()));
		typeTextSearchEntry.setCssClass(
			"table-cell-expand-smaller table-cell-minw-150");

		searchEntries.add(typeTextSearchEntry);

		return searchEntries;
	}

	private String _getPath(Organization organization) {
		List<Organization> organizations = new ArrayList<>();

		while (organization.getParentOrganizationId() !=
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			organization = OrganizationLocalServiceUtil.fetchOrganization(
				organization.getParentOrganizationId());

			if (organization == null) {
				break;
			}

			organizations.add(organization);
		}

		if (organizations.isEmpty()) {
			return StringPool.BLANK;
		}

		int size = organizations.size();

		StringBundler sb = new StringBundler(((size - 1) * 4) + 1);

		organization = organizations.get(size - 1);

		sb.append(organization.getName());

		for (int i = size - 2; i >= 0; i--) {
			organization = organizations.get(i);

			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
			sb.append(organization.getName());
		}

		return sb.toString();
	}

	private final Organization _organization;

}