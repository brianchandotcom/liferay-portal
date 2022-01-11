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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.change.tracking.web.internal.scheduler.ScheduledPublishInfo;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewScheduledDisplayContext
	extends BasePublicationsDisplayContext {

	public ViewScheduledDisplayContext(
		CTCollectionService ctCollectionService,
		HttpServletRequest httpServletRequest, Language language,
		PublishScheduler publishScheduler, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(httpServletRequest);

		_ctCollectionService = ctCollectionService;
		_httpServletRequest = httpServletRequest;
		_language = language;
		_publishScheduler = publishScheduler;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Date getPublishingDate(long ctCollectionId) throws PortalException {
		Map<Long, Date> publishingDateMap = _getPublishingDateMap();

		return publishingDateMap.get(ctCollectionId);
	}

	public SearchContainer<CTCollection> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTCollection> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse), null,
			_language.get(
				_httpServletRequest, "no-publication-has-been-scheduled-yet"));

		searchContainer.setDeltaConfigurable(false);
		searchContainer.setId("scheduled");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		List<CTCollection> ctCollections =
			_ctCollectionService.getCTCollections(
				_themeDisplay.getCompanyId(),
				new int[] {WorkflowConstants.STATUS_SCHEDULED},
				displayTerms.getKeywords(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, _getOrderByComparator());

		if (Objects.equals(getOrderByCol(), "publishing")) {
			ctCollections = new ArrayList<>(ctCollections);

			ctCollections.sort(
				(c1, c2) -> {
					try {
						Date date1 = getPublishingDate(c1.getCtCollectionId());
						Date date2 = getPublishingDate(c2.getCtCollectionId());

						if (date1.before(date2)) {
							if (Objects.equals(getOrderByType(), "asc")) {
								return 1;
							}

							return -1;
						}

						if (date1.after(date2)) {
							if (Objects.equals(getOrderByType(), "asc")) {
								return -1;
							}

							return 1;
						}
					}
					catch (PortalException portalException) {
						log.error(portalException, portalException);
					}

					return 0;
				});
		}

		List<CTCollection> sortedCTCollections = ctCollections;

		searchContainer.setResultsAndTotal(
			() -> {
				int end = searchContainer.getEnd();

				if (end > sortedCTCollections.size()) {
					end = sortedCTCollections.size();
				}

				return sortedCTCollections.subList(
					searchContainer.getStart(), end);
			},
			sortedCTCollections.size());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public List<NavigationItem> getViewNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(_renderResponse.createRenderURL());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "ongoing"));
			}
		).add(
			() -> PropsValues.SCHEDULER_ENABLED,
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_scheduled");
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "scheduled"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_history");
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "history"));
			}
		).build();
	}

	@Override
	protected String getDefaultOrderByCol() {
		return "name";
	}

	@Override
	protected String getPortalPreferencesPrefix() {
		return "scheduled";
	}

	private OrderByComparator<CTCollection> _getOrderByComparator() {
		OrderByComparator<CTCollection> orderByComparator = null;

		if (Objects.equals(getOrderByCol(), "modified-date")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"CTCollection", "modifiedDate",
				Objects.equals(getOrderByType(), "asc"));
		}
		else if (Objects.equals(getOrderByCol(), "name")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"CTCollection", getOrderByCol(),
				Objects.equals(getOrderByType(), "asc"));
		}

		return orderByComparator;
	}

	private Map<Long, Date> _getPublishingDateMap() throws PortalException {
		if (_publishingDateMap != null) {
			return _publishingDateMap;
		}

		Map<Long, Date> publishingDateMap = new HashMap<>();

		for (ScheduledPublishInfo scheduledPublishInfo :
				_publishScheduler.getScheduledPublishInfos()) {

			CTCollection ctCollection = scheduledPublishInfo.getCTCollection();

			publishingDateMap.put(
				ctCollection.getCtCollectionId(),
				scheduledPublishInfo.getStartDate());
		}

		_publishingDateMap = publishingDateMap;

		return _publishingDateMap;
	}

	private final CTCollectionService _ctCollectionService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private Map<Long, Date> _publishingDateMap;
	private final PublishScheduler _publishScheduler;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTCollection> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}