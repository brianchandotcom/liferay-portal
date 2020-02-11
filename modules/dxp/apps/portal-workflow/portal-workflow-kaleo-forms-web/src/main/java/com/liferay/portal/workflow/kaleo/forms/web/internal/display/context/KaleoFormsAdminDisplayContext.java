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

package com.liferay.portal.workflow.kaleo.forms.web.internal.display.context;

import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsActionKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.util.comparator.KaleoProcessCreateDateComparator;
import com.liferay.portal.workflow.kaleo.forms.util.comparator.KaleoProcessModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.forms.web.internal.configuration.KaleoFormsWebConfiguration;
import com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.util.KaleoFormsAdminRequestHelper;
import com.liferay.portal.workflow.kaleo.forms.web.internal.search.KaleoProcessSearch;
import com.liferay.portal.workflow.kaleo.forms.web.internal.security.permission.resource.KaleoFormsPermission;
import com.liferay.portal.workflow.kaleo.forms.web.internal.util.KaleoDefinitionVersionActivePredicate;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class KaleoFormsAdminDisplayContext {

	public KaleoFormsAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDLRecordLocalService ddlRecordLocalService,
		DDMDisplayRegistry ddmDisplayRegistry,
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService,
		KaleoFormsWebConfiguration kaleoFormsWebConfiguration,
		StorageEngine storageEngine) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddmDisplayRegistry = ddmDisplayRegistry;
		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
		_kaleoFormsWebConfiguration = kaleoFormsWebConfiguration;
		_storageEngine = storageEngine;

		_kaleoFormsAdminRequestHelper = new KaleoFormsAdminRequestHelper(
			renderRequest);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteKaleoProcess");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_kaleoFormsAdminRequestHelper.getRequest(),
								"delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		if (!isShowAddButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				HttpServletRequest httpServletRequest =
					_kaleoFormsAdminRequestHelper.getRequest();

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_renderResponse.createRenderURL(), "mvcPath",
							"/admin/edit_kaleo_process.jsp", "redirect",
							PortalUtil.getCurrentURL(httpServletRequest));

						dropdownItem.setLabel(
							LanguageUtil.get(httpServletRequest, "add"));
					});
			}
		};
	}

	public DDMDisplay getDDMDisplay() {
		return _ddmDisplayRegistry.getDDMDisplay(
			_kaleoFormsAdminRequestHelper.getPortletId());
	}

	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws StorageException {

		return _storageEngine.getDDMFormValues(ddmStorageId);
	}

	public String getDisplayStyle() {
		if (_kaleoFormsAdminDisplayStyle != null) {
			return _kaleoFormsAdminDisplayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		_kaleoFormsAdminDisplayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle");

		if (Validator.isNull(_kaleoFormsAdminDisplayStyle)) {
			_kaleoFormsAdminDisplayStyle = portalPreferences.getValue(
				KaleoFormsPortletKeys.KALEO_FORMS_ADMIN, "display-style",
				_kaleoFormsWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(
					getDisplayViews(), _kaleoFormsAdminDisplayStyle)) {

			portalPreferences.setValue(
				KaleoFormsPortletKeys.KALEO_FORMS_ADMIN, "display-style",
				_kaleoFormsAdminDisplayStyle);
		}

		if (!ArrayUtil.contains(
				getDisplayViews(), _kaleoFormsAdminDisplayStyle)) {

			_kaleoFormsAdminDisplayStyle = getDisplayViews()[0];
		}

		return _kaleoFormsAdminDisplayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			_kaleoFormsAdminRequestHelper.getRequest();

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								httpServletRequest, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(httpServletRequest, "order-by"));
					});
			}
		};
	}

	public KaleoFormsViewRecordsDisplayContext
			getKaleoFormsViewRecordsDisplayContext()
		throws PortalException {

		return new KaleoFormsViewRecordsDisplayContext(
			_renderRequest, _renderResponse, _ddlRecordLocalService);
	}

	public OrderByComparator<KaleoProcess> getKaleoProcessOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<KaleoProcess> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new KaleoProcessCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new KaleoProcessModifiedDateComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public KaleoProcessSearch getKaleoProcessSearch() {
		KaleoProcessSearch kaleoProcessSearch = new KaleoProcessSearch(
			_renderRequest, getPortletURL());

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<KaleoProcess> orderByComparator =
			getKaleoProcessOrderByComparator(orderByCol, orderByType);

		kaleoProcessSearch.setOrderByCol(orderByCol);
		kaleoProcessSearch.setOrderByComparator(orderByComparator);
		kaleoProcessSearch.setOrderByType(orderByType);

		setKaleoProcessSearchResults(kaleoProcessSearch);
		setKaleoProcessSearchTotal(kaleoProcessSearch);

		return kaleoProcessSearch;
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(
								_kaleoFormsAdminRequestHelper.getRequest(),
								"processes"));
					});
			}
		};
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "asc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = getPortletURL();

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "kaleoProcess";
	}

	public List<KaleoDefinitionVersion> getSearchContainerResults(
		SearchContainer<KaleoDefinitionVersion> searchContainer, int status) {

		List<KaleoDefinitionVersion> kaleoDefinitionVersions =
			_kaleoDefinitionVersionLocalService.
				getLatestKaleoDefinitionVersions(
					_kaleoFormsAdminRequestHelper.getCompanyId(), null,
					WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		kaleoDefinitionVersions = ListUtil.filter(
			kaleoDefinitionVersions,
			new KaleoDefinitionVersionActivePredicate(status));

		searchContainer.setTotal(kaleoDefinitionVersions.size());

		if (kaleoDefinitionVersions.size() >
				(searchContainer.getEnd() - searchContainer.getStart())) {

			kaleoDefinitionVersions = ListUtil.subList(
				kaleoDefinitionVersions, searchContainer.getStart(),
				searchContainer.getEnd());
		}

		return kaleoDefinitionVersions;
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getKaleoProcessSearch();

		return searchContainer.getTotal();
	}

	public boolean isShowAddButton() {
		return KaleoFormsPermission.contains(
			_kaleoFormsAdminRequestHelper.getPermissionChecker(),
			_kaleoFormsAdminRequestHelper.getScopeGroupId(),
			KaleoFormsActionKeys.ADD_PROCESS);
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_kaleoFormsAdminRequestHelper.getRequest(),
								"all"));
					});
			}
		};
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected UnsafeConsumer<DropdownItem, Exception> getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_kaleoFormsAdminRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(getOrderByDropdownItem("create-date"));
				add(getOrderByDropdownItem("modified-date"));
			}
		};
	}

	protected boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected void setKaleoProcessSearchResults(
		KaleoProcessSearch kaleoProcessSearch) {

		List<KaleoProcess> kaleoProcesses = KaleoProcessServiceUtil.search(
			_kaleoFormsAdminRequestHelper.getScopeGroupId(), getKeywords(),
			kaleoProcessSearch.getStart(), kaleoProcessSearch.getEnd(),
			kaleoProcessSearch.getOrderByComparator());

		kaleoProcessSearch.setResults(kaleoProcesses);
	}

	protected void setKaleoProcessSearchTotal(
		KaleoProcessSearch kaleoProcessSearch) {

		int total = KaleoProcessServiceUtil.searchCount(
			_kaleoFormsAdminRequestHelper.getScopeGroupId(), getKeywords());

		kaleoProcessSearch.setTotal(total);
	}

	private static final String[] _DISPLAY_VIEWS = {"list"};

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private String _kaleoFormsAdminDisplayStyle;
	private final KaleoFormsAdminRequestHelper _kaleoFormsAdminRequestHelper;
	private final KaleoFormsWebConfiguration _kaleoFormsWebConfiguration;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;

}