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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.fragment.web.internal.constants.FragmentTypeConstants;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.fragment.web.internal.util.FragmentPortletUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentDisplayContext {

	public FragmentDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = httpServletRequest;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)
				_httpServletRequest.getAttribute(
					FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(),
							"mvcRenderCommandName",
							"/fragment/edit_fragment_collection", "redirect",
							_themeDisplay.getURLCurrent());
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "collection"));
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "openImportView");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "import"));
					});
			}
		};
	}

	public List<DropdownItem> getCollectionsDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "exportCollections");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "export"));
					});

				if (FragmentPermission.contains(
						_themeDisplay.getPermissionChecker(),
						_themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "openImportView");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "import"));
						});

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteCollections");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						});
				}
			}
		};
	}

	public SearchContainer getContributedFragmentEntriesSearchContainer() {
		if (_contributedFragmentEntriesSearchContainer != null) {
			return _contributedFragmentEntriesSearchContainer;
		}

		SearchContainer contributedFragmentEntriesSearchContainer =
			new SearchContainer(
				_renderRequest, _getPortletURL(), null,
				"there-are-no-fragments");

		contributedFragmentEntriesSearchContainer.setId(
			"fragmentEntries" + getFragmentCollectionKey());

		FragmentCollectionContributor fragmentCollectionContributor =
			_getFragmentCollectionContributor();

		List<FragmentEntry> fragmentEntries =
			fragmentCollectionContributor.getFragmentEntries(
				_themeDisplay.getLocale());

		contributedFragmentEntriesSearchContainer.setResults(
			ListUtil.subList(
				fragmentEntries,
				contributedFragmentEntriesSearchContainer.getStart(),
				contributedFragmentEntriesSearchContainer.getEnd()));
		contributedFragmentEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));
		contributedFragmentEntriesSearchContainer.setTotal(
			fragmentEntries.size());

		_contributedFragmentEntriesSearchContainer =
			contributedFragmentEntriesSearchContainer;

		return _contributedFragmentEntriesSearchContainer;
	}

	public FragmentCollection getFragmentCollection() {
		if (_fragmentCollection != null) {
			return _fragmentCollection;
		}

		_fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				getFragmentCollectionId());

		return _fragmentCollection;
	}

	public FragmentCollectionContributor getFragmentCollectionContributor() {
		return _fragmentCollectionContributorTracker.
			getFragmentCollectionContributor(getFragmentCollectionKey());
	}

	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors(Locale locale) {

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		Collections.sort(
			fragmentCollectionContributors,
			new FragmentCollectionContributorNameComparator(locale));

		return fragmentCollectionContributors;
	}

	public long getFragmentCollectionId() {
		if (Validator.isNotNull(_fragmentCollectionId)) {
			return _fragmentCollectionId;
		}

		_fragmentCollectionId = ParamUtil.getLong(
			_httpServletRequest, "fragmentCollectionId",
			_getDefaultFragmentCollectionId());

		return _fragmentCollectionId;
	}

	public String getFragmentCollectionKey() {
		if (_fragmentCollectionKey != null) {
			return _fragmentCollectionKey;
		}

		_fragmentCollectionKey = ParamUtil.getString(
			_httpServletRequest, "fragmentCollectionKey",
			_getDefaultFragmentCollectionKey());

		return _fragmentCollectionKey;
	}

	public String getFragmentCollectionName() throws PortalException {
		if (isSelectedFragmentCollectionContributor()) {
			FragmentCollectionContributor fragmentCollectionContributor =
				_getFragmentCollectionContributor();

			return fragmentCollectionContributor.getName(
				_themeDisplay.getLocale());
		}

		FragmentCollection fragmentCollection = getFragmentCollection();

		if (fragmentCollection == null) {
			return StringPool.BLANK;
		}

		String fragmentCollectionName = fragmentCollection.getName();

		if (!_isScopeGroup()) {
			Group group = GroupLocalServiceUtil.getGroup(
				fragmentCollection.getGroupId());

			fragmentCollectionName = StringUtil.appendParentheticalSuffix(
				fragmentCollectionName, getGroupName(group.getGroupId()));
		}

		return HtmlUtil.escape(fragmentCollectionName);
	}

	public Map<String, Object> getFragmentCollectionsViewContext()
		throws Exception {

		LiferayPortletURL deleteFragmentCollectionURL =
			_renderResponse.createActionURL();

		deleteFragmentCollectionURL.setCopyCurrentRenderParameters(false);
		deleteFragmentCollectionURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/delete_fragment_collection");

		LiferayPortletURL exportFragmentCollectionsURL =
			(LiferayPortletURL)_renderResponse.createResourceURL();

		exportFragmentCollectionsURL.setCopyCurrentRenderParameters(false);
		exportFragmentCollectionsURL.setResourceID(
			"/fragment/export_fragment_collections");

		PortletURL viewExportFragmentCollectionsURL =
			_renderResponse.createRenderURL();

		viewExportFragmentCollectionsURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_collections");
		viewExportFragmentCollectionsURL.setParameter(
			"includeGlobalFragmentCollections", Boolean.TRUE.toString());
		viewExportFragmentCollectionsURL.setWindowState(
			LiferayWindowState.POP_UP);

		PortletURL viewDeleteFragmentCollectionsURL =
			_renderResponse.createRenderURL();

		viewDeleteFragmentCollectionsURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_collections");
		viewDeleteFragmentCollectionsURL.setWindowState(
			LiferayWindowState.POP_UP);

		PortletURL viewImportURL = _renderResponse.createRenderURL();

		viewImportURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_import");
		viewImportURL.setWindowState(LiferayWindowState.POP_UP);

		return HashMapBuilder.<String, Object>put(
			"deleteFragmentCollectionURL",
			deleteFragmentCollectionURL.toString()
		).put(
			"exportFragmentCollectionsURL",
			exportFragmentCollectionsURL.toString()
		).put(
			"viewExportFragmentCollectionsURL",
			viewExportFragmentCollectionsURL.toString()
		).put(
			"viewDeleteFragmentCollectionsURL",
			viewDeleteFragmentCollectionsURL.toString()
		).put(
			"viewImportURL", viewImportURL.toString()
		).build();
	}

	public SearchContainer getFragmentEntriesSearchContainer() {
		if (_fragmentEntriesSearchContainer != null) {
			return _fragmentEntriesSearchContainer;
		}

		SearchContainer fragmentEntriesSearchContainer = new SearchContainer(
			_renderRequest, _getPortletURL(), null, "there-are-no-fragments");

		fragmentEntriesSearchContainer.setId(
			"fragmentEntries" + getFragmentCollectionId());

		fragmentEntriesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		OrderByComparator<FragmentEntry> orderByComparator =
			FragmentPortletUtil.getFragmentEntryOrderByComparator(
				_getOrderByCol(), getOrderByType());

		fragmentEntriesSearchContainer.setOrderByCol(_getOrderByCol());
		fragmentEntriesSearchContainer.setOrderByComparator(orderByComparator);
		fragmentEntriesSearchContainer.setOrderByType(getOrderByType());

		List<FragmentEntry> fragmentEntries = null;
		int fragmentEntriesCount = 0;

		FragmentCollection fragmentCollection = getFragmentCollection();

		int status = WorkflowConstants.STATUS_ANY;

		if (fragmentCollection.getGroupId() !=
				_themeDisplay.getScopeGroupId()) {

			status = WorkflowConstants.STATUS_APPROVED;
		}

		if (isSearch()) {
			fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntriesByNameAndStatus(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					_getKeywords(), status,
					fragmentEntriesSearchContainer.getStart(),
					fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentEntriesCountByNameAndStatus(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					_getKeywords(), status);
		}
		else {
			fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntriesByStatus(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(), status,
					fragmentEntriesSearchContainer.getStart(),
					fragmentEntriesSearchContainer.getEnd(), orderByComparator);

			fragmentEntriesCount =
				FragmentEntryServiceUtil.getFragmentEntriesCountByStatus(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(), status);
		}

		fragmentEntriesSearchContainer.setResults(fragmentEntries);
		fragmentEntriesSearchContainer.setTotal(fragmentEntriesCount);

		_fragmentEntriesSearchContainer = fragmentEntriesSearchContainer;

		return _fragmentEntriesSearchContainer;
	}

	public String getFragmentType() {
		if (_isScopeGroup()) {
			return FragmentTypeConstants.BASIC_FRAGMENT_TYPE;
		}

		return FragmentTypeConstants.INHERITED_FRAGMENT_TYPE;
	}

	public String getGroupName(long groupId) throws PortalException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return group.getDescriptiveName(_themeDisplay.getLocale());
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public List<NavigationItem> getNavigationItems() {
		if (!_isShowResourcesTab()) {
			return Collections.emptyList();
		}

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(_getTabs1(), "fragments"));
						navigationItem.setHref(
							_getPortletURL(), "tabs1", "fragments");
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "fragments"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(_getTabs1(), "resources"));
						navigationItem.setHref(
							_getPortletURL(), "tabs1", "resources");
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "resources"));
					});
			}
		};
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public String getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/fragment/view");

		if (getFragmentCollectionId() > 0) {
			portletURL.setParameter(
				"fragmentCollectionId",
				String.valueOf(getFragmentCollectionId()));
		}

		return portletURL.toString();
	}

	public boolean hasUpdatePermission() {
		if (_updatePermission != null) {
			return _updatePermission;
		}

		_updatePermission = false;

		if (FragmentPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) &&
			_isScopeGroup()) {

			_updatePermission = true;
		}

		return _updatePermission;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(_getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isSelectedFragmentCollectionContributor() {
		if (Validator.isNotNull(getFragmentCollectionKey())) {
			return true;
		}

		return false;
	}

	public boolean isViewResources() {
		if (Objects.equals(_getTabs1(), "resources") && _isScopeGroup()) {
			return true;
		}

		return false;
	}

	public boolean showFragmentCollectionActions() {
		if (!isSelectedFragmentCollectionContributor()) {
			return true;
		}

		return false;
	}

	private long _getDefaultFragmentCollectionId() {
		if (Validator.isNotNull(_getSelectedFragmentCollectionKey())) {
			return 0;
		}

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_themeDisplay.getScopeGroupId(), 0, 1);

		if (ListUtil.isNotEmpty(fragmentCollections)) {
			FragmentCollection fragmentCollection = fragmentCollections.get(0);

			return fragmentCollection.getFragmentCollectionId();
		}

		fragmentCollections =
			FragmentCollectionLocalServiceUtil.getFragmentCollections(
				_themeDisplay.getCompanyGroupId(), 0, 1);

		if (ListUtil.isNotEmpty(fragmentCollections)) {
			FragmentCollection fragmentCollection = fragmentCollections.get(0);

			return fragmentCollection.getFragmentCollectionId();
		}

		return 0;
	}

	private String _getDefaultFragmentCollectionKey() {
		if ((_getSelectedFragmentCollectionId() > 0) ||
			(_getDefaultFragmentCollectionId() > 0)) {

			return StringPool.BLANK;
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		if (ListUtil.isEmpty(fragmentCollectionContributors)) {
			return StringPool.BLANK;
		}

		FragmentCollectionContributor fragmentCollectionContributor =
			fragmentCollectionContributors.get(0);

		return fragmentCollectionContributor.getFragmentCollectionKey();
	}

	private FragmentCollectionContributor _getFragmentCollectionContributor() {
		return _fragmentCollectionContributorTracker.
			getFragmentCollectionContributor(getFragmentCollectionKey());
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/fragment/view");

		long fragmentCollectionId = getFragmentCollectionId();

		if (fragmentCollectionId > 0) {
			portletURL.setParameter(
				"fragmentCollectionId", String.valueOf(fragmentCollectionId));
		}

		String fragmentCollectionKey = getFragmentCollectionKey();

		if (Validator.isNotNull(fragmentCollectionKey)) {
			portletURL.setParameter(
				"fragmentCollectionKey", fragmentCollectionKey);
		}

		String keywords = _getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = _getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	private long _getSelectedFragmentCollectionId() {
		return ParamUtil.getLong(_httpServletRequest, "fragmentCollectionId");
	}

	private String _getSelectedFragmentCollectionKey() {
		return ParamUtil.getString(
			_httpServletRequest, "fragmentCollectionKey");
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_httpServletRequest, "tabs1", "fragments");

		return _tabs1;
	}

	private boolean _isScopeGroup() {
		FragmentCollection fragmentCollection = getFragmentCollection();

		if (fragmentCollection.getGroupId() ==
				_themeDisplay.getScopeGroupId()) {

			return true;
		}

		return false;
	}

	private boolean _isShowResourcesTab() {
		if (isSelectedFragmentCollectionContributor()) {
			return false;
		}

		if (!_isScopeGroup()) {
			return false;
		}

		return true;
	}

	private SearchContainer _contributedFragmentEntriesSearchContainer;
	private FragmentCollection _fragmentCollection;
	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private Long _fragmentCollectionId;
	private String _fragmentCollectionKey;
	private SearchContainer _fragmentEntriesSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;
	private Boolean _updatePermission;

}