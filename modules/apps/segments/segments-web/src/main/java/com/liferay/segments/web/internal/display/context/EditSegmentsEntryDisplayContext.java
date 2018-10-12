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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.odata.retriever.UserODataRetriever;
import com.liferay.segments.service.SegmentsEntryRelService;
import com.liferay.segments.service.SegmentsEntryService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class EditSegmentsEntryDisplayContext {

	public EditSegmentsEntryDisplayContext(
		HttpServletRequest request, RenderRequest renderRequest,
		RenderResponse renderResponse,
		OrganizationLocalService organizationLocalService,
		SegmentsEntryService segmentsEntryService,
		SegmentsEntryRelService segmentsEntryRelService,
		UserLocalService userLocalService,
		UserODataRetriever userODataRetriever) {

		_request = request;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_organizationLocalService = organizationLocalService;
		_segmentsEntryService = segmentsEntryService;
		_segmentsEntryRelService = segmentsEntryRelService;
		_userLocalService = userLocalService;
		_userODataRetriever = userODataRetriever;

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry == null) ||
			Validator.isNotNull(segmentsEntry.getCriteria())) {

			return new DropdownItemList();
		}

		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							if (SegmentsConstants.TYPE_ORGANIZATIONS.equals(
									segmentsEntry.getType())) {

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

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						String activeTab = ParamUtil.getString(
							_request, "tabs1", "details");

						navigationItem.setActive(activeTab.equals("details"));

						navigationItem.setHref(_getDetailsURL());
						navigationItem.setLabel(
							LanguageUtil.get(_request, "details"));
					});

				SegmentsEntry segmentsEntry = getSegmentsEntry();

				if (segmentsEntry != null) {
					add(
						navigationItem -> {
							String type = segmentsEntry.getType();

							String activeTab = ParamUtil.getString(
								_request, "tabs1", "details");

							navigationItem.setActive(activeTab.equals(type));

							if (type.equals(
									SegmentsConstants.TYPE_ORGANIZATIONS)) {

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
			_renderRequest, getPortletURL(SegmentsConstants.TYPE_ORGANIZATIONS),
			null, "there-are-no-organizations-in-this-segment");

		organizationSearchContainer.setId("segmentsEntryOrganizations");
		organizationSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		int total = _segmentsEntryRelService.getSegmentsEntryRelsCount(
			getSegmentsEntryId());

		organizationSearchContainer.setTotal(total);

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelService.getSegmentsEntryRels(getSegmentsEntryId());

		Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

		List<Organization> organizations = stream.map(
			segmentsEntryRel -> _organizationLocalService.fetchOrganization(
				segmentsEntryRel.getClassPK())
		).collect(
			Collectors.toList()
		);

		organizationSearchContainer.setResults(organizations);

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

	public long getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(_request, "segmentsEntryId");

		return _segmentsEntryId;
	}

	public String getSegmentsEntryName(Locale locale) throws PortalException {
		if (_segmentsEntryName != null) {
			return _segmentsEntryName;
		}

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return StringPool.BLANK;
		}

		_segmentsEntryName = segmentsEntry.getName(locale);

		return _segmentsEntryName;
	}

	public SearchContainer getUserSearchContainer() throws PortalException {
		if (_userSearchContainer != null) {
			return _userSearchContainer;
		}

		SearchContainer userSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(SegmentsConstants.TYPE_USERS), null,
			null);

		userSearchContainer.setId("segmentsEntryUsers");

		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if (segmentsEntry == null) {
			return userSearchContainer;
		}

		int total = 0;
		List<User> users = null;

		if (Validator.isNotNull(segmentsEntry.getCriteria())) {
			userSearchContainer.setEmptyResultsMessage(
				"no-users-were-found-that-matched-the-segment-criteria");

			try {
				users = _userODataRetriever.getUsers(
					segmentsEntry.getCompanyId(), segmentsEntry.getCriteria(),
					_themeDisplay.getLocale(), userSearchContainer.getStart(),
					userSearchContainer.getEnd());

				total = users.size();
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to retrieve users with the criteria " +
						segmentsEntry.getCriteria(),
					pe);
			}
		}
		else {
			userSearchContainer.setEmptyResultsMessage(
				"no-users-have-been-assigned-to-this-segment");

			userSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));

			List<SegmentsEntryRel> segmentsEntryRels =
				_segmentsEntryRelService.getSegmentsEntryRels(
					getSegmentsEntryId(), userSearchContainer.getStart(),
					userSearchContainer.getEnd(),
					userSearchContainer.getOrderByComparator());

			Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

			users = stream.map(
				segmentsEntryRel -> _userLocalService.fetchUser(
					segmentsEntryRel.getClassPK())
			).collect(
				Collectors.toList()
			);

			total = _segmentsEntryRelService.getSegmentsEntryRelsCount(
				getSegmentsEntryId());
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

	public boolean isSelectable() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry != null) &&
			Validator.isNotNull(segmentsEntry.getCriteria())) {

			return false;
		}

		return true;
	}

	public boolean showCreationMenu() throws PortalException {
		SegmentsEntry segmentsEntry = getSegmentsEntry();

		if ((segmentsEntry != null) &&
			Validator.isNotNull(segmentsEntry.getCriteria())) {

			return false;
		}

		return true;
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
	private SegmentsEntry _segmentsEntry;
	private Long _segmentsEntryId;
	private String _segmentsEntryName;
	private final SegmentsEntryRelService _segmentsEntryRelService;
	private final SegmentsEntryService _segmentsEntryService;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;
	private final UserODataRetriever _userODataRetriever;
	private SearchContainer _userSearchContainer;

}