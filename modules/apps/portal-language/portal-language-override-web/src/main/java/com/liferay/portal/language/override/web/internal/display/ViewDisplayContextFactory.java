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

package com.liferay.portal.language.override.web.internal.display;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringParser;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.PLOEntryLocalService;
import com.liferay.portal.language.override.web.internal.dto.PLOItemDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Drew Brokke
 */
public class ViewDisplayContextFactory {

	public ViewDisplayContextFactory(
		PLOEntryLocalService ploEntryLocalService, Portal portal) {

		_ploEntryLocalService = ploEntryLocalService;
		_portal = portal;
	}

	public ViewDisplayContext create(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ViewDisplayContext viewDisplayContext = new ViewDisplayContext();

		Set<Locale> companyAvailableLocales =
			LanguageUtil.getCompanyAvailableLocales(
				_portal.getCompanyId(renderRequest));

		viewDisplayContext.setAvailableLocales(
			companyAvailableLocales.toArray(new Locale[0]));

		viewDisplayContext.setDisplayStyle(
			ParamUtil.getString(renderRequest, "displayStyle", "descriptive"));

		SearchContainer<PLOItemDTO> searchContainer = _createSearchContainer(
			renderRequest, renderResponse);

		viewDisplayContext.setSearchContainer(searchContainer);

		viewDisplayContext.setSelectedLanguageId(
			ParamUtil.getString(
				renderRequest, "selectedLanguageId",
				LanguageUtil.getLanguageId(_portal.getLocale(renderRequest))));

		return viewDisplayContext;
	}

	private SearchContainer<PLOItemDTO> _createSearchContainer(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		SearchContainer<PLOItemDTO> searchContainer = new SearchContainer<>(
			liferayPortletRequest,
			PortletURLUtil.getCurrent(
				liferayPortletRequest, liferayPortletResponse),
			Arrays.asList("key", "value"), "no-language-entries-were-found");

		searchContainer.setId("portalLanguageOverrideEntries");

		String orderByCol = ParamUtil.getString(
			liferayPortletRequest, "orderByCol", "name");

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		searchContainer.setOrderByType(orderByType);

		_setResults(renderRequest, searchContainer);

		return searchContainer;
	}

	private void _setResults(
		RenderRequest renderRequest,
		SearchContainer<PLOItemDTO> searchContainer) {

		List<PLOItemDTO> ploItemDTOs = new ArrayList<>();

		List<PLOEntry> ploEntries = _ploEntryLocalService.getPLOEntries(
			_portal.getCompanyId(renderRequest));

		Stream<PLOEntry> ploEntryStream = ploEntries.stream();

		Map<String, List<PLOEntry>> ploEntryMap = ploEntryStream.collect(
			Collectors.groupingBy(PLOEntry::getKey));

		Predicate<String> keyMatchPredicate = s -> true;
		Predicate<String> valueMatchPredicate = s -> true;

		if (searchContainer.isSearch()) {
			String keywords = ParamUtil.getString(renderRequest, "keywords");

			Pattern keyPattern = Pattern.compile(
				".*" + StringParser.escapeRegex(keywords) + ".*",
				Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);

			keyMatchPredicate = keyPattern.asPredicate();

			Pattern valuePattern = Pattern.compile(
				".*\\b" + StringParser.escapeRegex(keywords) + ".*",
				Pattern.CASE_INSENSITIVE + Pattern.UNICODE_CASE);

			valueMatchPredicate = valuePattern.asPredicate();
		}

		String selectedLanguageId = ParamUtil.getString(
			renderRequest, "selectedLanguageId");

		Locale locale = LocaleUtil.fromLanguageId(
			selectedLanguageId, true, true);

		String filter = ParamUtil.getString(renderRequest, "navigation", "all");

		if (filter.equals("override")) {
			for (Map.Entry<String, List<PLOEntry>> entry :
					ploEntryMap.entrySet()) {

				String key = entry.getKey();

				String value = LanguageUtil.get(locale, key, StringPool.BLANK);

				if (keyMatchPredicate.test(key) ||
					valueMatchPredicate.test(value)) {

					PLOItemDTO ploItemDTO = new PLOItemDTO(key, value);

					ploItemDTO.setOverride(true);

					for (PLOEntry ploEntry : entry.getValue()) {
						ploItemDTO.addOverrideLanguage(
							ploEntry.getLanguageId());

						if (Objects.equals(
								selectedLanguageId, ploEntry.getLanguageId())) {

							ploItemDTO.setOverrideSelectedLanguageId(true);
						}
					}

					ploItemDTOs.add(ploItemDTO);
				}
			}
		}
		else {
			ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
				locale);

			for (String key : resourceBundle.keySet()) {
				String value = ResourceBundleUtil.getString(
					resourceBundle, key);

				if (keyMatchPredicate.test(key) ||
					valueMatchPredicate.test(value)) {

					PLOItemDTO ploItemDTO = new PLOItemDTO(key, value);

					if (ploEntryMap.containsKey(key)) {
						ploItemDTO.setOverride(true);

						for (PLOEntry ploEntry : ploEntryMap.get(key)) {
							ploItemDTO.addOverrideLanguage(
								ploEntry.getLanguageId());

							if (Objects.equals(
									selectedLanguageId,
									ploEntry.getLanguageId())) {

								ploItemDTO.setOverrideSelectedLanguageId(true);
							}
						}
					}

					ploItemDTOs.add(ploItemDTO);
				}
			}
		}

		searchContainer.setTotal(ploItemDTOs.size());

		// Sorting

		Comparator<PLOItemDTO> comparator = Comparator.comparing(
			PLOItemDTO::getKey);

		if (Objects.equals(searchContainer.getOrderByType(), "desc")) {
			comparator = comparator.reversed();
		}

		ploItemDTOs.sort(comparator);

		// Pagination

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			searchContainer.getStart(), searchContainer.getEnd(),
			searchContainer.getTotal());

		searchContainer.setResults(
			ploItemDTOs.subList(startAndEnd[0], startAndEnd[1]));
	}

	private final PLOEntryLocalService _ploEntryLocalService;
	private final Portal _portal;

}