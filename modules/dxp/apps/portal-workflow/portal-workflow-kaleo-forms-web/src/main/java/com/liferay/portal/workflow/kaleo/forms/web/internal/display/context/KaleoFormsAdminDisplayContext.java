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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsActionKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.util.comparator.KaleoProcessCreateDateComparator;
import com.liferay.portal.workflow.kaleo.forms.util.comparator.KaleoProcessModifiedDateComparator;
import com.liferay.portal.workflow.kaleo.forms.web.internal.configuration.KaleoFormsWebConfiguration;
import com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.helper.KaleoFormsAdminRequestHelper;
import com.liferay.portal.workflow.kaleo.forms.web.internal.search.KaleoProcessSearch;
import com.liferay.portal.workflow.kaleo.forms.web.internal.security.permission.resource.KaleoFormsPermission;
import com.liferay.portal.workflow.kaleo.forms.web.internal.util.filter.KaleoDefinitionVersionActivePredicate;
import com.liferay.portal.workflow.kaleo.forms.web.internal.util.filter.KaleoDefinitionVersionScopePredicate;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.util.List;
import java.util.Objects;

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

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_kaleoFormsAdminRequestHelper = new KaleoFormsAdminRequestHelper(
			renderRequest);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteKaleoProcess");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_kaleoFormsAdminRequestHelper.getRequest(), "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getClearResultsURL() throws PortletException {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public CreationMenu getCreationMenu() {
		if (!isShowAddButton()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				HttpServletRequest httpServletRequest =
					_kaleoFormsAdminRequestHelper.getRequest();

				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcPath",
					"/admin/edit_kaleo_process.jsp", "redirect",
					PortalUtil.getCurrentURL(httpServletRequest));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add"));
			}
		).build();
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

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	public KaleoFormsViewRecordsDisplayContext
			getKaleoFormsViewRecordsDisplayContext()
		throws PortalException {

		return new KaleoFormsViewRecordsDisplayContext(
			_renderRequest, _renderResponse, _ddlRecordLocalService);
	}

	public long getKaleoProcessId() {
		if (_kaleoProcessId != null) {
			return _kaleoProcessId;
		}

		KaleoProcess kaleoProcess =
			(KaleoProcess)_httpServletRequest.getAttribute(
				KaleoFormsWebKeys.KALEO_PROCESS);

		_kaleoProcessId = BeanParamUtil.getLong(
			kaleoProcess, _httpServletRequest, "kaleoProcessId");

		return _kaleoProcessId;
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

		kaleoProcessSearch.setOrderByCol(getOrderByCol());
		kaleoProcessSearch.setOrderByComparator(
			getKaleoProcessOrderByComparator(
				getOrderByCol(), getOrderByType()));
		kaleoProcessSearch.setOrderByType(getOrderByType());

		kaleoProcessSearch.setResultsAndTotal(
			() -> KaleoProcessServiceUtil.search(
				_kaleoFormsAdminRequestHelper.getScopeGroupId(), getKeywords(),
				kaleoProcessSearch.getStart(), kaleoProcessSearch.getEnd(),
				kaleoProcessSearch.getOrderByComparator()),
			KaleoProcessServiceUtil.searchCount(
				_kaleoFormsAdminRequestHelper.getScopeGroupId(),
				getKeywords()));

		return kaleoProcessSearch;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(
						_kaleoFormsAdminRequestHelper.getRequest(),
						"processes"));
			}
		).build();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
			"admin-order-by-col", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
			"admin-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/admin/view.jsp"
		).setKeywords(
			() -> {
				String keywords = getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setParameter(
			"delta",
			() -> {
				String delta = ParamUtil.getString(_renderRequest, "delta");

				if (Validator.isNotNull(delta)) {
					return delta;
				}

				return null;
			}
		).setParameter(
			"displayStyle",
			() -> {
				String displayStyle = ParamUtil.getString(
					_renderRequest, "displayStyle");

				if (Validator.isNotNull(displayStyle)) {
					return getDisplayStyle();
				}

				return null;
			}
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).buildPortletURL();
	}

	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	public SearchContainer<?> getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		String emptyResultsMessage = null;

		if (isTabs1Published()) {
			emptyResultsMessage = "there-are-no-published-definitions";
		}
		else {
			emptyResultsMessage = "there-are-no-unpublished-definitions";
		}

		if (isTabs1Published()) {
			SearchContainer<WorkflowDefinition> searchContainer =
				new SearchContainer<>(
					_renderRequest, _getIteratorURL(), null,
					emptyResultsMessage);

			searchContainer.setResultsAndTotal(
				() ->
					WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
						_themeDisplay.getCompanyId(),
						searchContainer.getStart(), searchContainer.getEnd(),
						null),
				WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitionsCount(
					_themeDisplay.getCompanyId()));

			_searchContainer = searchContainer;
		}
		else {
			SearchContainer<KaleoDefinitionVersion> searchContainer =
				new SearchContainer<>(
					_renderRequest, _getIteratorURL(), null,
					emptyResultsMessage);

			List<KaleoDefinitionVersion> kaleoDefinitionVersions =
				_kaleoDefinitionVersionLocalService.
					getLatestKaleoDefinitionVersions(
						_kaleoFormsAdminRequestHelper.getCompanyId(), null,
						WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null);

			kaleoDefinitionVersions = ListUtil.filter(
				kaleoDefinitionVersions,
				new KaleoDefinitionVersionActivePredicate(_getStatus()));

			List<KaleoDefinitionVersion> filteredKaleoDefinitionVersions =
				ListUtil.filter(
					kaleoDefinitionVersions,
					new KaleoDefinitionVersionScopePredicate(
						WorkflowDefinitionConstants.SCOPE_ALL));

			searchContainer.setResultsAndTotal(
				() -> {
					if (filteredKaleoDefinitionVersions.size() >
							(searchContainer.getEnd() -
								searchContainer.getStart())) {

						return ListUtil.subList(
							filteredKaleoDefinitionVersions,
							searchContainer.getStart(),
							searchContainer.getEnd());
					}

					return filteredKaleoDefinitionVersions;
				},
				filteredKaleoDefinitionVersions.size());

			_searchContainer = searchContainer;
		}

		return _searchContainer;
	}

	public String getSearchContainerId() {
		return "kaleoProcess";
	}

	public String getSortingURL() throws Exception {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = ParamUtil.getString(
					_renderRequest, "orderByType");

				if (orderByType.equals("desc")) {
					return "asc";
				}

				return "desc";
			}
		).buildString();
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

	public boolean isTabs1Published() {
		if (_tabs1Published != null) {
			return _tabs1Published;
		}

		_tabs1Published = false;

		if (Objects.equals(_getTabs1(), "published")) {
			_tabs1Published = true;
		}

		return _tabs1Published;
	}

	public boolean isTabs1Unpublished() {
		if (_tabs1Unpublished != null) {
			return _tabs1Unpublished;
		}

		_tabs1Unpublished = false;

		if (Objects.equals(_getTabs1(), "unpublished")) {
			_tabs1Unpublished = true;
		}

		return _tabs1Unpublished;
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref(getPortletURL(), "navigation", "all");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_kaleoFormsAdminRequestHelper.getRequest(), "all"));
			}
		).build();
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			_getOrderByDropdownItem("create-date")
		).add(
			_getOrderByDropdownItem("modified-date")
		).build();
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

	private PortletURL _getIteratorURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/admin/edit_kaleo_process.jsp"
		).setRedirect(
			ParamUtil.getString(_httpServletRequest, "redirect")
		).setTabs1(
			_getTabs1()
		).setParameter(
			"historyKey", "workflow"
		).setParameter(
			"kaleoProcessId", getKaleoProcessId()
		).buildPortletURL();
	}

	private UnsafeConsumer<DropdownItem, Exception> _getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					_kaleoFormsAdminRequestHelper.getRequest(), orderByCol));
		};
	}

	private int _getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = WorkflowConstants.STATUS_DRAFT;

		if (isTabs1Published()) {
			_status = WorkflowConstants.STATUS_APPROVED;
		}

		return _status;
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_httpServletRequest, "tabs1", "published");

		return _tabs1;
	}

	private static final String[] _DISPLAY_VIEWS = {"list"};

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private String _kaleoFormsAdminDisplayStyle;
	private final KaleoFormsAdminRequestHelper _kaleoFormsAdminRequestHelper;
	private final KaleoFormsWebConfiguration _kaleoFormsWebConfiguration;
	private Long _kaleoProcessId;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<?> _searchContainer;
	private Integer _status;
	private final StorageEngine _storageEngine;
	private String _tabs1;
	private Boolean _tabs1Published;
	private Boolean _tabs1Unpublished;
	private final ThemeDisplay _themeDisplay;

}