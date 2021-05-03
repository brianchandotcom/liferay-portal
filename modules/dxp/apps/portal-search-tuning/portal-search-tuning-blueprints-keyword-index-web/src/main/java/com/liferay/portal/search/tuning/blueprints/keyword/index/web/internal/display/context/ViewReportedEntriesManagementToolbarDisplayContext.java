/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordEntryFields;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class ViewReportedEntriesManagementToolbarDisplayContext
	extends ViewEntriesManagementToolbarDisplayContext {

	public ViewReportedEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<KeywordEntry> searchContainer, String displayStyle) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer, displayStyle, KeywordEntryStatus.REPORTED);
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(
						getOrderByCol(), KeywordEntryFields.CONTENT_RAW));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol",
					KeywordEntryFields.CONTENT_RAW);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "content"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(
						getOrderByCol(), KeywordEntryFields.HIT_COUNT));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol",
					KeywordEntryFields.HIT_COUNT);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "hitcount"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(
						getOrderByCol(), KeywordEntryFields.REPORT_COUNT));
				dropdownItem.setHref(
					getCurrentSortingURL(), "orderByCol",
					KeywordEntryFields.REPORT_COUNT);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "reportcount"));
			}
		).build();
	}

}