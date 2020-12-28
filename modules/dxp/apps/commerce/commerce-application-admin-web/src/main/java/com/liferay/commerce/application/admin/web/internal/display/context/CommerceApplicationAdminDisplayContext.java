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

package com.liferay.commerce.application.admin.web.internal.display.context;

import com.liferay.commerce.application.admin.web.internal.display.context.util.CommerceApplicationAdminRequestHelper;
import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.service.CommerceApplicationBrandService;
import com.liferay.commerce.application.service.CommerceApplicationModelService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceApplicationAdminDisplayContext {

	public CommerceApplicationAdminDisplayContext(
		ModelResourcePermission<CommerceApplicationBrand>
			commerceApplicationBrandModelResourcePermission,
		CommerceApplicationBrandService commerceApplicationBrandService,
		ModelResourcePermission<CommerceApplicationModel>
			commerceApplicationModelModelResourcePermission,
		CommerceApplicationModelService commerceApplicationModelService,
		HttpServletRequest httpServletRequest,
		UserFileUploadsConfiguration userFileUploadsConfiguration) {

		_commerceApplicationBrandModelResourcePermission =
			commerceApplicationBrandModelResourcePermission;
		_commerceApplicationBrandService = commerceApplicationBrandService;
		_commerceApplicationModelModelResourcePermission =
			commerceApplicationModelModelResourcePermission;
		_commerceApplicationModelService = commerceApplicationModelService;
		_userFileUploadsConfiguration = userFileUploadsConfiguration;

		_commerceApplicationAdminRequestHelper =
			new CommerceApplicationAdminRequestHelper(httpServletRequest);
	}

	public CommerceApplicationBrand getCommerceApplicationBrand()
		throws PortalException {

		long commerceApplicationBrandId = ParamUtil.getLong(
			_commerceApplicationAdminRequestHelper.getRequest(),
			"commerceApplicationBrandId");

		if (commerceApplicationBrandId > 0) {
			return _commerceApplicationBrandService.getCommerceApplicationBrand(
				commerceApplicationBrandId);
		}

		return null;
	}

	public long getCommerceApplicationBrandId() throws PortalException {
		CommerceApplicationBrand commerceApplicationBrand =
			getCommerceApplicationBrand();

		if (commerceApplicationBrand == null) {
			return 0;
		}

		return commerceApplicationBrand.getCommerceApplicationBrandId();
	}

	public SearchContainer<CommerceApplicationBrand>
			getCommerceApplicationBrandSearchContainer()
		throws PortalException {

		if (_commerceApplicationBrandSearchContainer != null) {
			return _commerceApplicationBrandSearchContainer;
		}

		_commerceApplicationBrandSearchContainer = new SearchContainer<>(
			_commerceApplicationAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_commerceApplicationBrandSearchContainer.setEmptyResultsMessage(
			"no-brands-were-found");

		_commerceApplicationBrandSearchContainer.setOrderByCol(getOrderByCol());
		_commerceApplicationBrandSearchContainer.setOrderByType(
			getOrderByType());
		_commerceApplicationBrandSearchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceApplicationBrandService.getCommerceApplicationBrandsCount(
				_commerceApplicationAdminRequestHelper.getCompanyId());

		_commerceApplicationBrandSearchContainer.setTotal(total);

		List<CommerceApplicationBrand> results =
			_commerceApplicationBrandService.getCommerceApplicationBrands(
				_commerceApplicationAdminRequestHelper.getCompanyId(),
				_commerceApplicationBrandSearchContainer.getStart(),
				_commerceApplicationBrandSearchContainer.getEnd());

		_commerceApplicationBrandSearchContainer.setResults(results);

		return _commerceApplicationBrandSearchContainer;
	}

	public CommerceApplicationModel getCommerceApplicationModel()
		throws PortalException {

		long commerceApplicationModelId = ParamUtil.getLong(
			_commerceApplicationAdminRequestHelper.getRequest(),
			"commerceApplicationModelId");

		if (commerceApplicationModelId > 0) {
			return _commerceApplicationModelService.getCommerceApplicationModel(
				commerceApplicationModelId);
		}

		return null;
	}

	public long getCommerceApplicationModelId() throws PortalException {
		CommerceApplicationModel commerceApplicationModel =
			getCommerceApplicationModel();

		if (commerceApplicationModel == null) {
			return 0L;
		}

		return commerceApplicationModel.getCommerceApplicationModelId();
	}

	public SearchContainer<CommerceApplicationModel>
			getCommerceApplicationModelSearchContainer()
		throws PortalException {

		if (_commerceApplicationModelSearchContainer != null) {
			return _commerceApplicationModelSearchContainer;
		}

		_commerceApplicationModelSearchContainer = new SearchContainer<>(
			_commerceApplicationAdminRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		_commerceApplicationModelSearchContainer.setEmptyResultsMessage(
			"no-models-were-found");

		_commerceApplicationModelSearchContainer.setOrderByCol(getOrderByCol());
		_commerceApplicationModelSearchContainer.setOrderByType(
			getOrderByType());
		_commerceApplicationModelSearchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceApplicationModelService.getCommerceApplicationModelsCount(
				_commerceApplicationAdminRequestHelper.getCompanyId());

		_commerceApplicationModelSearchContainer.setTotal(total);

		List<CommerceApplicationModel> results =
			_commerceApplicationModelService.getCommerceApplicationModels(
				getCommerceApplicationBrandId(),
				_commerceApplicationModelSearchContainer.getStart(),
				_commerceApplicationModelSearchContainer.getEnd());

		_commerceApplicationModelSearchContainer.setResults(results);

		return _commerceApplicationModelSearchContainer;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_commerceApplicationAdminRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_commerceApplicationAdminRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "name");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_commerceApplicationAdminRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceApplicationAdminRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				_commerceApplicationAdminRequestHelper.getRequest());

		String backURL = ParamUtil.getString(httpServletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter("backURL", backURL);
		}

		String redirect = ParamUtil.getString(
			_commerceApplicationAdminRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(
			_commerceApplicationAdminRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_commerceApplicationAdminRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		portletURL.setParameter(
			"commerceApplicationBrandId",
			String.valueOf(getCommerceApplicationBrandId()));

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker == null) {
			_rowChecker = new EmptyOnClickRowChecker(
				_commerceApplicationAdminRequestHelper.
					getLiferayPortletResponse());
		}

		return _rowChecker;
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration() {
		return _userFileUploadsConfiguration;
	}

	public boolean hasBrandPermissions(String actionId) {
		PortletResourcePermission portletResourcePermission =
			_commerceApplicationBrandModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_commerceApplicationAdminRequestHelper.getPermissionChecker(), null,
			actionId);
	}

	public boolean hasCommerceApplicationBrandPermissions(
			long commerceApplicationBrandId, String actionId)
		throws PortalException {

		return _commerceApplicationBrandModelResourcePermission.contains(
			_commerceApplicationAdminRequestHelper.getPermissionChecker(),
			commerceApplicationBrandId, actionId);
	}

	public boolean hasCommerceApplicationModelPermissions(
			long commerceApplicationModelId, String actionId)
		throws PortalException {

		return _commerceApplicationModelModelResourcePermission.contains(
			_commerceApplicationAdminRequestHelper.getPermissionChecker(),
			commerceApplicationModelId, actionId);
	}

	public boolean hasModelPermissions(String actionId) {
		PortletResourcePermission portletResourcePermission =
			_commerceApplicationModelModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_commerceApplicationAdminRequestHelper.getPermissionChecker(), null,
			actionId);
	}

	private final CommerceApplicationAdminRequestHelper
		_commerceApplicationAdminRequestHelper;
	private final ModelResourcePermission<CommerceApplicationBrand>
		_commerceApplicationBrandModelResourcePermission;
	private SearchContainer<CommerceApplicationBrand>
		_commerceApplicationBrandSearchContainer;
	private final CommerceApplicationBrandService
		_commerceApplicationBrandService;
	private final ModelResourcePermission<CommerceApplicationModel>
		_commerceApplicationModelModelResourcePermission;
	private SearchContainer<CommerceApplicationModel>
		_commerceApplicationModelSearchContainer;
	private final CommerceApplicationModelService
		_commerceApplicationModelService;
	private String _keywords;
	private RowChecker _rowChecker;
	private final UserFileUploadsConfiguration _userFileUploadsConfiguration;

}