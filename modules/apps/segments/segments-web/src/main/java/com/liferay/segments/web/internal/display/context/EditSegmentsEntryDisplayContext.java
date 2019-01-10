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

package com.liferay.segments.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributorRegistry;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class EditSegmentsEntryDisplayContext {

	public EditSegmentsEntryDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse,
		SegmentsCriteriaContributorRegistry segmentsCriteriaContributorRegistry,
		SegmentsEntryProvider segmentsEntryProvider,
		OrganizationLocalService organizationLocalService,
		SegmentsEntryService segmentsEntryService,
		UserLocalService userLocalService) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_segmentsCriteriaContributorRegistry =
			segmentsCriteriaContributorRegistry;
		_segmentsEntryProvider = segmentsEntryProvider;
		_organizationLocalService = organizationLocalService;
		_segmentsEntryService = segmentsEntryService;
		_userLocalService = userLocalService;
	}

	public List<DropdownItem> getActionDropdownItems() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry == null) || isCriteriaConfigured()) {
			return new DropdownItemList();
		}

		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							String type = segmentsEntry.getType();

							if (type.equals(Organization.class.getName())) {
								dropdownItem.putData(
									"action",
									"deleteSegmentsEntryOrganizations");
							}
							else {
								dropdownItem.putData(
									"action", "deleteSegmentsEntryUsers");
							}

							dropdownItem.setIcon("times");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "delete"));
							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public Criteria getCriteria() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry == null) ||
			(segmentsEntry.getCriteriaObj() == null)) {

			return new Criteria();
		}

		return segmentsEntry.getCriteriaObj();
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		String currentTab = ParamUtil.getString(_request, "tabs1", "details");

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(currentTab.equals("details"));

						navigationItem.setHref(_getDetailsURL());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "details"));
					});

				SegmentsEntry segmentsEntry = getSegmentsEntry();

				if (segmentsEntry != null) {
					add(
						navigationItem -> {
							String type = segmentsEntry.getType();

							navigationItem.setActive(
								!currentTab.equals("details"));

							if (type.equals(Organization.class.getName())) {
								navigationItem.setHref(_getOrganizationsURL());
								navigationItem.setLabel(
									LanguageUtil.get(
										_request, "organizations"));
							}
							else {
								navigationItem.setHref(_getUsersURL());
								navigationItem.setLabel(
									LanguageUtil.get(_request, "users"));
							}
						});
				}
			}
		};
	}

	public SearchContainer getOrganizationSearchContainer()
		throws PortalException {

		if (_organizationSearchContainer != null) {
			return _organizationSearchContainer;
		}

		SearchContainer organizationSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL("organizations"), null,
			"no-organizations-have-been-assigned-to-this-segment");

		organizationSearchContainer.setId("segmentsEntryOrganizations");

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return organizationSearchContainer;
		}

		if (!isCriteriaConfigured()) {
			organizationSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));
		}

		int total = 0;
		List<Organization> organizations = null;

		try {
			total = _segmentsEntryProvider.getSegmentsEntryClassPKsCount(
				segmentsEntry.getSegmentsEntryId());

			long[] segmentsEntryClassPKs =
				_segmentsEntryProvider.getSegmentsEntryClassPKs(
					segmentsEntry.getSegmentsEntryId(),
					organizationSearchContainer.getStart(),
					organizationSearchContainer.getEnd());

			LongStream segmentsEntryClassPKsStream = Arrays.stream(
				segmentsEntryClassPKs);

			organizations = segmentsEntryClassPKsStream.boxed(
			).map(
				organizationId -> _organizationLocalService.fetchOrganization(
					organizationId)
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to retrieve organizations for segment " + segmentsEntry,
				pe);
		}

		organizationSearchContainer.setResults(organizations);
		organizationSearchContainer.setTotal(total);

		_organizationSearchContainer = organizationSearchContainer;

		return _organizationSearchContainer;
	}

	public int getOrganizationTotalItems() throws PortalException {
		SearchContainer<?> organizationSearchContainer =
			getOrganizationSearchContainer();

		return organizationSearchContainer.getTotal();
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(_redirect)) {
			PortletURL portletURL = _renderResponse.createRenderURL();

			_redirect = portletURL.toString();
		}

		return _redirect;
	}

	public List<SegmentsCriteriaContributor> getSegmentsCriteriaContributors()
		throws PortalException {

		return _segmentsCriteriaContributorRegistry.
			getSegmentsCriteriaContributors(getType());
	}

	public SegmentsEntry getSegmentsEntry() throws PortalException {
		if (_segmentsEntry != null) {
			return _segmentsEntry;
		}

		long segmentsEntryId = getSegmentsEntryId();

		if (segmentsEntryId > 0) {
			_segmentsEntry = _segmentsEntryService.getSegmentsEntry(
				segmentsEntryId);
		}

		return _segmentsEntry;
	}

	public int getSegmentsEntryClassPKsCount() throws PortalException {
		if (_segmentsEntryClassPKsCount != null) {
			return _segmentsEntryClassPKsCount;
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return 0;
		}

		return _segmentsEntryProvider.getSegmentsEntryClassPKsCount(
			segmentsEntry.getSegmentsEntryId());
	}

	public long getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(_request, "segmentsEntryId");

		return _segmentsEntryId;
	}

	public String getTitle(Locale locale) throws PortalException {
		if (_title != null) {
			return _title;
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			_title = segmentsEntry.getName(locale);
		}
		else {
			String type = ResourceActionsUtil.getModelResource(
				locale, getType());

			_title = LanguageUtil.format(
				_request, "new-x-segment", type, false);
		}

		return _title;
	}

	public String getType() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry != null) {
			return segmentsEntry.getType();
		}

		return ParamUtil.getString(_request, "type", User.class.getName());
	}

	public SearchContainer getUserSearchContainer() throws PortalException {
		if (_userSearchContainer != null) {
			return _userSearchContainer;
		}

		SearchContainer userSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL("users"), null,
			"no-users-have-been-assigned-to-this-segment");

		userSearchContainer.setId("segmentsEntryUsers");

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return userSearchContainer;
		}

		if (!isCriteriaConfigured()) {
			userSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));
		}

		int total = 0;
		List<User> users = null;

		try {
			total = _segmentsEntryProvider.getSegmentsEntryClassPKsCount(
				segmentsEntry.getSegmentsEntryId());

			long[] segmentsEntryClassPKs =
				_segmentsEntryProvider.getSegmentsEntryClassPKs(
					segmentsEntry.getSegmentsEntryId(),
					userSearchContainer.getStart(),
					userSearchContainer.getEnd());

			LongStream segmentsEntryClassPKsStream = Arrays.stream(
				segmentsEntryClassPKs);

			users = segmentsEntryClassPKsStream.boxed(
			).map(
				userId -> _userLocalService.fetchUser(userId)
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to retrieve users for segment " + segmentsEntry, pe);
		}

		userSearchContainer.setResults(users);
		userSearchContainer.setTotal(total);

		_userSearchContainer = userSearchContainer;

		return _userSearchContainer;
	}

	public int getUserTotalItems() throws PortalException {
		SearchContainer<?> userSearchContainer = getUserSearchContainer();

		return userSearchContainer.getTotal();
	}

	public boolean isCriteriaConfigured() throws PortalException {
		Criteria criteria = getCriteria();

		return MapUtil.isNotEmpty(criteria.getCriteria());
	}

	public boolean isSelectable() throws PortalException {
		return !isCriteriaConfigured();
	}

	public boolean showCreationMenu() throws PortalException {
		return !isCriteriaConfigured();
	}

	protected PortletURL getPortletURL(String tabs1) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editSegmentsEntry");
		portletURL.setParameter("tabs1", tabs1);

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		return portletURL;
	}

	private String _getDetailsURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editSegmentsEntry");
		portletURL.setParameter("tabs1", "details");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"segmentsEntryId", String.valueOf(getSegmentsEntryId()));

		return portletURL.toString();
	}

	private String _getOrganizationsURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editSegmentsEntryOrganizations");
		portletURL.setParameter("tabs1", "organizations");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"segmentsEntryId", String.valueOf(getSegmentsEntryId()));

		return portletURL.toString();
	}

	private String _getUsersURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editSegmentsEntryUsers");
		portletURL.setParameter("tabs1", "users");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"segmentsEntryId", String.valueOf(getSegmentsEntryId()));

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSegmentsEntryDisplayContext.class);

	private String _displayStyle;
	private final OrganizationLocalService _organizationLocalService;
	private SearchContainer _organizationSearchContainer;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final SegmentsCriteriaContributorRegistry
		_segmentsCriteriaContributorRegistry;
	private SegmentsEntry _segmentsEntry;
	private Integer _segmentsEntryClassPKsCount;
	private Long _segmentsEntryId;
	private final SegmentsEntryProvider _segmentsEntryProvider;
	private final SegmentsEntryService _segmentsEntryService;
	private String _title;
	private final UserLocalService _userLocalService;
	private SearchContainer _userSearchContainer;

}