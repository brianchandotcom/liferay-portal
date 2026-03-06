/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.tax.category.web.internal.display.context;

import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.tax.category.web.internal.dao.search.CPTaxCategoryRowChecker;
import com.liferay.commerce.product.tax.category.web.internal.util.CPTaxCategoryUtil;
import com.liferay.commerce.product.util.comparator.CPTaxCategoryCreateDateComparator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPTaxCategoryDisplayContext {

	public CPTaxCategoryDisplayContext(
		CPTaxCategoryService cpTaxCategoryService,
		ModelResourcePermission<CPTaxCategory> modelResourcePermission,
		PortletResourcePermission portletResourcePermission,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_cpTaxCategoryService = cpTaxCategoryService;
		_modelResourcePermission = modelResourcePermission;
		_portletResourcePermission = portletResourcePermission;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		cpRequestHelper = new CPRequestHelper(
			PortalUtil.getHttpServletRequest(renderRequest));
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems(
		CPTaxCategory cpTaxCategory, String currentURL) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		return DropdownItemListBuilder.add(
			() -> hasDeleteCPTaxCategoryPermission(cpTaxCategory),
			dropDownItem -> {
				dropDownItem.setData(
					HashMapBuilder.<String, Object>put(
						"action", "delete"
					).put(
						"confirmationMessage",
						LanguageUtil.get(
							httpServletRequest,
							"are-you-sure-you-want-to-delete-this")
					).put(
						"deleteURL",
						PortletURLBuilder.createActionURL(
							_renderResponse
						).setActionName(
							"/cp_tax_category/edit_cp_tax_category"
						).setCMD(
							Constants.DELETE
						).setRedirect(
							currentURL
						).setParameter(
							"cpTaxCategoryId",
							cpTaxCategory.getCPTaxCategoryId()
						).buildString()
					).build());
				dropDownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).add(
			() -> hasEditCPTaxCategoryPermission(cpTaxCategory),
			dropDownItem -> {
				dropDownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCRenderCommandName(
						"/cp_tax_category/edit_cp_tax_category"
					).setRedirect(
						currentURL
					).setParameter(
						"cpTaxCategoryId", cpTaxCategory.getCPTaxCategoryId()
					).buildString());
				dropDownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).build();
	}

	public CPTaxCategory getCPTaxCategory() throws PortalException {
		if (_cpTaxCategory != null) {
			return _cpTaxCategory;
		}

		long cpTaxCategoryId = ParamUtil.getLong(
			_renderRequest, "cpTaxCategoryId");

		if (cpTaxCategoryId > 0) {
			_cpTaxCategory = _cpTaxCategoryService.getCPTaxCategory(
				cpTaxCategoryId);
		}

		return _cpTaxCategory;
	}

	public List<HeaderActionModel> getHeaderActionModels() {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		HeaderActionModel publishHeaderActionModel = new HeaderActionModel(
			"btn-primary", renderResponse.getNamespace() + "fm", null, null,
			"save");

		headerActionModels.add(publishHeaderActionModel);

		return headerActionModels;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, CPPortletKeys.CP_TAX_CATEGORY, "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, CPPortletKeys.CP_TAX_CATEGORY, "desc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildPortletURL();
	}

	public String getRowURL(CPTaxCategory cpTaxCategory, String currentURL)
		throws Exception {

		if (hasEditCPTaxCategoryPermission(cpTaxCategory)) {
			return PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/cp_tax_category/edit_cp_tax_category"
			).setRedirect(
				currentURL
			).setParameter(
				"cpTaxCategoryId", cpTaxCategory.getCPTaxCategoryId()
			).buildString();
		}

		return StringPool.BLANK;
	}

	public SearchContainer<CPTaxCategory> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null,
			"there-are-no-tax-categories");

		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(
			_getCPTaxCategoryOrderByComparator(
				getOrderByCol(), getOrderByType()));
		_searchContainer.setOrderByType(getOrderByType());

		if (Validator.isBlank(getKeywords())) {
			_searchContainer.setResultsAndTotal(
				() -> _cpTaxCategoryService.getCPTaxCategories(
					_themeDisplay.getCompanyId(), _searchContainer.getStart(),
					_searchContainer.getEnd(),
					_searchContainer.getOrderByComparator()),
				_cpTaxCategoryService.getCPTaxCategoriesCount(
					_themeDisplay.getCompanyId()));
		}
		else {
			_searchContainer.setResultsAndTotal(
				_cpTaxCategoryService.searchCPTaxCategories(
					_themeDisplay.getCompanyId(), getKeywords(),
					_searchContainer.getStart(), _searchContainer.getEnd(),
					CPTaxCategoryUtil.getCPTaxCategorySort(
						getOrderByCol(), getOrderByType())));
		}

		_searchContainer.setRowChecker(_getRowChecker());

		return _searchContainer;
	}

	public boolean hasAddCPTaxCategoriesPermission() {
		return _portletResourcePermission.contains(
			_themeDisplay.getPermissionChecker(), null,
			CPActionKeys.ADD_COMMERCE_PRODUCT_TAX_CATEGORY);
	}

	public boolean hasDeleteCPTaxCategoryPermission(CPTaxCategory cpTaxCategory)
		throws Exception {

		return _modelResourcePermission.contains(
			_themeDisplay.getPermissionChecker(), cpTaxCategory,
			ActionKeys.DELETE);
	}

	public boolean hasEditCPTaxCategoryPermission(CPTaxCategory cpTaxCategory)
		throws Exception {

		return _modelResourcePermission.contains(
			_themeDisplay.getPermissionChecker(), cpTaxCategory,
			ActionKeys.UPDATE);
	}

	public boolean hasViewCPTaxCategoriesPermission() {
		return _portletResourcePermission.contains(
			_themeDisplay.getPermissionChecker(), null,
			CPActionKeys.VIEW_COMMERCE_PRODUCT_TAX_CATEGORIES);
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected final CPRequestHelper cpRequestHelper;

	private OrderByComparator<CPTaxCategory> _getCPTaxCategoryOrderByComparator(
		String orderByCol, String orderByType) {

		OrderByComparator<CPTaxCategory> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			boolean orderByAsc = orderByType.equals("asc");

			orderByComparator = CPTaxCategoryCreateDateComparator.getInstance(
				orderByAsc);
		}

		return orderByComparator;
	}

	private RowChecker _getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new CPTaxCategoryRowChecker(this, _renderResponse);
		}

		return _rowChecker;
	}

	private CPTaxCategory _cpTaxCategory;
	private final CPTaxCategoryService _cpTaxCategoryService;
	private final ModelResourcePermission<CPTaxCategory>
		_modelResourcePermission;
	private String _orderByCol;
	private String _orderByType;
	private final PortletResourcePermission _portletResourcePermission;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RowChecker _rowChecker;
	private SearchContainer<CPTaxCategory> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}