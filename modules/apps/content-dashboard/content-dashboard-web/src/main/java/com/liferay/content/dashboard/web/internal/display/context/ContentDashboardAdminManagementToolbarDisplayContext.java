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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González Castellano
 */
public class ContentDashboardAdminManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ContentDashboardAdminManagementToolbarDisplayContext(
		ContentDashboardAdminDisplayContext contentDashboardAdminDisplayContext,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Locale locale,
		UserLocalService userLocalService) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			contentDashboardAdminDisplayContext.getSearchContainer());

		_contentDashboardAdminDisplayContext =
			contentDashboardAdminDisplayContext;
		_groupLocalService = groupLocalService;
		_locale = locale;
		_userLocalService = userLocalService;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("authorIds", (String)null);
		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		clearResultsURL.setParameter("scopeId", (String)null);
		clearResultsURL.setParameter(
			"status", String.valueOf(WorkflowConstants.STATUS_ANY));

		return String.valueOf(clearResultsURL);
	}

	@Override
	public String getDefaultEventHandler() {
		return "contentDashboardManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getFilterDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by") +
						StringPool.TRIPLE_PERIOD);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterAuthorDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by-author"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterStatusDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(request, "filter-by-status"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(getOrderByDropdownItemsLabel());
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		long scopeId = _contentDashboardAdminDisplayContext.getScopeId();
		int status = _contentDashboardAdminDisplayContext.getStatus();

		LabelItemListBuilder.LabelItemListWrapper labelItemListWrapper =
			LabelItemListBuilder.add(
				() -> scopeId > 0,
				labelItem -> {
					PortletURL removeLabelURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					removeLabelURL.setParameter("scopeId", (String)null);

					labelItem.putData(
						"removeLabelURL", removeLabelURL.toString());

					labelItem.setCloseable(true);

					labelItem.setLabel(
						LanguageUtil.get(request, "site-or-asset-library") +
							": " + _getScopeLabel(scopeId));
				}
			).add(
				() -> status != WorkflowConstants.STATUS_ANY,
				labelItem -> {
					PortletURL removeLabelURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					removeLabelURL.setParameter("status", (String)null);

					labelItem.putData(
						"removeLabelURL", removeLabelURL.toString());

					labelItem.setCloseable(true);

					labelItem.setLabel(
						LanguageUtil.get(request, "status") + ": " +
							_getStatusLabel(status));
				}
			);

		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		for (Long authorId : authorIds) {
			labelItemListWrapper.add(
				labelItem -> {
					PortletURL portletURL = PortletURLUtil.clone(
						currentURLObj, liferayPortletResponse);

					Stream<Long> stream = authorIds.stream();

					portletURL.setParameter(
						"authorIds",
						stream.filter(
							id -> id != authorId
						).map(
							String::valueOf
						).collect(
							Collectors.toList()
						).toArray(
							new String[0]
						));

					labelItem.putData(
						"removeLabelURL",
						String.valueOf(portletURL.toString()));

					labelItem.setCloseable(true);
					labelItem.setLabel(
						StringBundler.concat(
							LanguageUtil.get(request, "author"),
							StringPool.COLON,
							LanguageUtil.get(
								request,
								Optional.ofNullable(
									_userLocalService.fetchUser(authorId)
								).map(
									User::getFullName
								).orElse(
									StringPool.BLANK
								))));
				});
		}

		return labelItemListWrapper.build();
	}

	@Override
	public PortletURL getPortletURL() {
		try {
			return PortletURLUtil.clone(currentURLObj, liferayPortletResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return liferayPortletResponse.createRenderURL();
		}
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());
		portletURL.setParameter(
			"scopeId",
			String.valueOf(_contentDashboardAdminDisplayContext.getScopeId()));
		portletURL.setParameter(
			"status",
			String.valueOf(_contentDashboardAdminDisplayContext.getStatus()));

		return String.valueOf(portletURL);
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"title", "modified-date"};
	}

	private List<DropdownItem> _getFilterAuthorDropdownItems() {
		List<Long> authorIds =
			_contentDashboardAdminDisplayContext.getAuthorIds();

		return DropdownItemList.of(
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setActive(authorIds.isEmpty());

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("authorIds", (String)null);

				dropdownItem.setHref(portletURL);

				dropdownItem.setLabel(LanguageUtil.get(request, "all"));

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				if ((authorIds.size() == 1) &&
					authorIds.contains(
						_contentDashboardAdminDisplayContext.getUserId())) {

					dropdownItem.setActive(true);
				}

				dropdownItem.setHref(
					getPortletURL(), "authorIds",
					_contentDashboardAdminDisplayContext.getUserId());
				dropdownItem.setLabel(LanguageUtil.get(request, "me"));

				return dropdownItem;
			},
			() -> {
				DropdownItem dropdownItem = new DropdownItem();

				if (((authorIds.size() == 1) &&
					 !authorIds.contains(
						 _contentDashboardAdminDisplayContext.getUserId())) ||
					(authorIds.size() > 1)) {

					dropdownItem.setActive(true);
				}

				dropdownItem.putData("action", "selectAuthor");
				dropdownItem.putData(
					"dialogTitle", LanguageUtil.get(request, "select-author"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("authorIds", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectAuthorURL",
					String.valueOf(
						_contentDashboardAdminDisplayContext.
							getAuthorItemSelectorURL()));
				dropdownItem.setLabel(
					LanguageUtil.get(request, "author") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			});
	}

	private List<DropdownItem> _getFilterDropdownItems() {
		return DropdownItemList.of(
			() -> {
				long scopeId =
					_contentDashboardAdminDisplayContext.getScopeId();

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setActive(scopeId > 0);

				dropdownItem.putData("action", "selectScope");
				dropdownItem.putData(
					"dialogTitle",
					LanguageUtil.get(request, "select-site-or-asset-library"));

				PortletURL portletURL = getPortletURL();

				portletURL.setParameter("scopeId", (String)null);

				dropdownItem.putData("redirectURL", String.valueOf(portletURL));

				dropdownItem.putData(
					"selectScopeURL",
					String.valueOf(
						_contentDashboardAdminDisplayContext.
							getScopeIdItemSelectorURL()));

				dropdownItem.setLabel(
					LanguageUtil.get(request, "site-or-asset-library") +
						StringPool.TRIPLE_PERIOD);

				return dropdownItem;
			});
	}

	private List<DropdownItem> _getFilterStatusDropdownItems() {
		return new DropdownItemList() {
			{
				Integer curStatus =
					_contentDashboardAdminDisplayContext.getStatus();

				for (int status : _getStatuses()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(curStatus == status);
							dropdownItem.setHref(
								getPortletURL(), "status",
								String.valueOf(status));

							dropdownItem.setLabel(_getStatusLabel(status));
						});
				}
			}
		};
	}

	private String _getScopeLabel(long scopeId) {
		return Optional.ofNullable(
			_groupLocalService.fetchGroup(scopeId)
		).map(
			group -> {
				try {
					return group.getDescriptiveName(_locale);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return StringPool.BLANK;
				}
			}
		).orElse(
			StringPool.BLANK
		);
	}

	private List<Integer> _getStatuses() {
		return Arrays.asList(
			WorkflowConstants.STATUS_ANY, WorkflowConstants.STATUS_DRAFT,
			WorkflowConstants.STATUS_SCHEDULED,
			WorkflowConstants.STATUS_APPROVED);
	}

	private String _getStatusLabel(int status) {
		String label = WorkflowConstants.getStatusLabel(status);

		return LanguageUtil.get(request, label);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardAdminManagementToolbarDisplayContext.class);

	private final ContentDashboardAdminDisplayContext
		_contentDashboardAdminDisplayContext;
	private final GroupLocalService _groupLocalService;
	private final Locale _locale;
	private final UserLocalService _userLocalService;

}