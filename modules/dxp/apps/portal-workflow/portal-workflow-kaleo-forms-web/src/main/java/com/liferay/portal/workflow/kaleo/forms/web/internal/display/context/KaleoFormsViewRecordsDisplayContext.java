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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.permission.KaleoProcessPermission;
import com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.helper.KaleoFormsAdminRequestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Inácio Nery
 */
public class KaleoFormsViewRecordsDisplayContext {

	public KaleoFormsViewRecordsDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDLRecordLocalService ddlRecordLocalService)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddlRecordLocalService = ddlRecordLocalService;

		_kaleoProcess = (KaleoProcess)_renderRequest.getAttribute(
			KaleoFormsWebKeys.KALEO_PROCESS);

		_ddlRecordSet = _kaleoProcess.getDDLRecordSet();

		_kaleoFormsAdminRequestHelper = new KaleoFormsAdminRequestHelper(
			renderRequest);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteRecords");
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

	public String getColumnName(DDMFormField formField) {
		LocalizedValue label = formField.getLabel();

		return label.getString(_renderRequest.getLocale());
	}

	public CreationMenu getCreationMenu() throws PortalException {
		if (!hasSubmitPermission()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					_renderResponse.createRenderURL(), "mvcPath",
					"/admin/edit_request.jsp", "redirect",
					PortalUtil.getCurrentURL(
						_kaleoFormsAdminRequestHelper.getRequest()),
					"backURL",
					PortalUtil.getCurrentURL(
						_kaleoFormsAdminRequestHelper.getRequest()),
					"kaleoProcessId",
					String.valueOf(_kaleoProcess.getKaleoProcessId()));

				dropdownItem.setLabel(
					LanguageUtil.format(
						_kaleoFormsAdminRequestHelper.getRequest(),
						"submit-new-x",
						HtmlUtil.escape(
							_kaleoProcess.getName(
								_kaleoFormsAdminRequestHelper.getLocale())),
						false));
			}
		).build();
	}

	public OrderByComparator<DDLRecord> getDDLRecordOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecord> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDLRecordModifiedDateComparator(orderByAsc);
		}
		else {
			orderByComparator = new DDLRecordCreateDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public DDLRecordSet getDDLRecordSet() {
		return _ddlRecordSet;
	}

	public List<DDMFormField> getDDMFormFields() throws PortalException {
		if (_ddmFormFields != null) {
			return _ddmFormFields;
		}

		DDMStructure ddmStructure = _ddlRecordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (_isDDMFormFieldTransient(ddmFormField)) {
				continue;
			}

			ddmFormFields.add(ddmFormField);
		}

		int totalColumns = _TOTAL_COLUMNS;

		if (ddmFormFields.size() < totalColumns) {
			totalColumns = ddmFormFields.size();
		}

		_ddmFormFields = ddmFormFields.subList(0, totalColumns);

		return _ddmFormFields;
	}

	public String getDisplayStyle() {
		return "list";
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

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

	public List<String> getHeaderNames() throws PortalException {
		List<String> headerNames = new ArrayList<>();

		List<DDMFormField> ddmFormFields = getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue label = ddmFormField.getLabel();

			headerNames.add(
				label.getString(_kaleoFormsAdminRequestHelper.getLocale()));
		}

		if (hasSubmitPermission()) {
			headerNames.add("status");
			headerNames.add("modified-date");
			headerNames.add("author");
		}

		headerNames.add(StringPool.BLANK);

		return headerNames;
	}

	public KaleoProcess getKaleoProcess() {
		return _kaleoProcess;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);

				ThemeDisplay themeDisplay = _getThemeDisplay();

				navigationItem.setLabel(
					HtmlUtil.extractText(
						_kaleoProcess.getName(themeDisplay.getLocale())));
			}
		).build();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
			"view-order-by-col", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
			"view-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.create(
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse)
		).setMVCPath(
			"/admin/view_kaleo_process.jsp"
		).setRedirect(
			ParamUtil.getString(_renderRequest, "redirect")
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
				String displayStyle = getDisplayStyle();

				if (Validator.isNotNull(displayStyle)) {
					return displayStyle;
				}

				return null;
			}
		).setParameter(
			"kaleoProcessId", _kaleoProcess.getKaleoProcessId()
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

	public SearchContainer<?> getSearch() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletURL portletURL = PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"displayStyle", getDisplayStyle()
		).buildPortletURL();

		_searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			portletURL, getHeaderNames(),
			LanguageUtil.format(
				_kaleoFormsAdminRequestHelper.getRequest(),
				"no-x-records-were-found",
				HtmlUtil.escape(
					_kaleoProcess.getName(
						_kaleoFormsAdminRequestHelper.getLocale())),
				false));

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(
			getDDLRecordOrderByComparator(getOrderByCol(), getOrderByType()));
		_searchContainer.setOrderByType(getOrderByType());
		_searchContainer.setResultsAndTotal(
			_ddlRecordLocalService.searchDDLRecords(
				_getSearchContext(_searchContainer)));

		User user = _kaleoFormsAdminRequestHelper.getUser();

		if (!user.isDefaultUser()) {
			_searchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_renderResponse));
		}

		return _searchContainer;
	}

	public String getSearchActionURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/admin/view_kaleo_process.jsp"
		).setRedirect(
			ParamUtil.getString(_renderRequest, "redirect")
		).setParameter(
			"kaleoProcessId", _kaleoProcess.getKaleoProcessId()
		).buildString();
	}

	public String getSearchContainerId() {
		return "ddlRecord";
	}

	public String getSortingURL() throws Exception {
		return PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _renderResponse)
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = ParamUtil.getString(
					_renderRequest, "orderByType");

				if (orderByType.equals("asc")) {
					return "desc";
				}

				return "asc";
			}
		).buildString();
	}

	public int getTotalItems() throws Exception {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public boolean hasSubmitPermission() {
		return KaleoProcessPermission.contains(
			_kaleoFormsAdminRequestHelper.getPermissionChecker(), _kaleoProcess,
			ActionKeys.SUBMIT);
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
			dropdownItem -> {
				String orderByCol = "create-date";

				dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
				dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
				dropdownItem.setLabel(
					LanguageUtil.get(
						_kaleoFormsAdminRequestHelper.getRequest(),
						orderByCol));
			}
		).add(
			dropdownItem -> {
				String orderByCol = "modified-date";

				dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
				dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
				dropdownItem.setLabel(
					LanguageUtil.get(
						_kaleoFormsAdminRequestHelper.getRequest(),
						orderByCol));
			}
		).build();
	}

	protected boolean hasResults() throws Exception {
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

	private SearchContext _getSearchContext(
		SearchContainer<DDLRecord> searchContainer) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_kaleoFormsAdminRequestHelper.getRequest());

		searchContext.setAttribute(Field.STATUS, WorkflowConstants.STATUS_ANY);
		searchContext.setAttribute(
			"recordSetId", _ddlRecordSet.getRecordSetId());
		searchContext.setAttribute("recordSetScope", _ddlRecordSet.getScope());
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setKeywords(getKeywords());
		searchContext.setSorts(_getSort());
		searchContext.setStart(searchContainer.getStart());

		return searchContext;
	}

	private Sort _getSort() {
		boolean ascending = false;

		if (Objects.equals("asc", getOrderByType())) {
			ascending = true;
		}

		String fieldName = Field.MODIFIED_DATE;

		if (Objects.equals("create-date", getOrderByCol())) {
			fieldName = Field.CREATE_DATE;
		}

		return new Sort(
			Field.getSortableFieldName(fieldName), Sort.LONG_TYPE, !ascending);
	}

	private ThemeDisplay _getThemeDisplay() {
		return (ThemeDisplay)_renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	private boolean _isDDMFormFieldTransient(DDMFormField ddmFormField) {
		if (Validator.isNull(ddmFormField.getDataType())) {
			return true;
		}

		return false;
	}

	private static final int _TOTAL_COLUMNS = 5;

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSet _ddlRecordSet;
	private List<DDMFormField> _ddmFormFields;
	private final KaleoFormsAdminRequestHelper _kaleoFormsAdminRequestHelper;
	private final KaleoProcess _kaleoProcess;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<DDLRecord> _searchContainer;

}