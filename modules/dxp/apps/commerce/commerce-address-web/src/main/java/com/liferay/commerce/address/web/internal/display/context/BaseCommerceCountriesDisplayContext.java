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

package com.liferay.commerce.address.web.internal.display.context;

import com.liferay.commerce.address.web.internal.admin.CountriesCommerceAdminModule;
import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.web.internal.servlet.taglib.ui.CommerceCountryScreenNavigationConstants;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceCountriesDisplayContext<T> {

	public BaseCommerceCountriesDisplayContext(
		ActionHelper actionHelper, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		this.actionHelper = actionHelper;
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;

		_defaultOrderByCol = "priority";
		_defaultOrderByType = "asc";
	}

	public CommerceCountry getCommerceCountry() throws PortalException {
		if (_commerceCountry != null) {
			return _commerceCountry;
		}

		return actionHelper.getCommerceCountry(renderRequest);
	}

	public long getCommerceCountryId() throws PortalException {
		CommerceCountry commerceCountry = getCommerceCountry();

		if (commerceCountry == null) {
			return 0;
		}

		return commerceCountry.getCommerceCountryId();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			_defaultOrderByCol);
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			_defaultOrderByType);
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", CountriesCommerceAdminModule.KEY);

		if (getCommerceCountryId() > 0) {
			portletURL.setParameter(
				"commerceCountryId", String.valueOf(getCommerceCountryId()));
		}

		String delta = ParamUtil.getString(renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("navigation", getNavigation());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(renderResponse);
		}

		return _rowChecker;
	}

	public String getScreenNavigationCategoryKey() {
		return CommerceCountryScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_COUNTRY_DETAILS;
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

	public String getSelectedScreenNavigationCategoryKey() {
		return ParamUtil.getString(
			renderRequest, "screenNavigationCategoryKey",
			getScreenNavigationCategoryKey());
	}

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	protected String getNavigation() {
		return ParamUtil.getString(renderRequest, "navigation");
	}

	protected final ActionHelper actionHelper;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected SearchContainer<T> searchContainer;

	private CommerceCountry _commerceCountry;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private RowChecker _rowChecker;

}