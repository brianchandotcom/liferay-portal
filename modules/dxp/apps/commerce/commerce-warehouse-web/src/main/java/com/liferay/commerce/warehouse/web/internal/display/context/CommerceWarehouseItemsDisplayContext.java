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

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.service.CommerceWarehouseItemService;
import com.liferay.commerce.service.CommerceWarehouseService;
import com.liferay.commerce.util.comparator.CommerceWarehouseNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceWarehouseItemsDisplayContext {

	public CommerceWarehouseItemsDisplayContext(
		CommerceWarehouseItemService commerceWarehouseItemService,
		CommerceWarehouseService commerceWarehouseService,
		CPInstanceService cpInstanceService,
		HttpServletRequest httpServletRequest, Portal portal) {

		_commerceWarehouseItemService = commerceWarehouseItemService;
		_commerceWarehouseService = commerceWarehouseService;
		_cpInstanceService = cpInstanceService;
		_portal = portal;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getBackURL() throws PortalException {
		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		String lifecycle = (String)renderRequest.getAttribute(
			LiferayPortletRequest.LIFECYCLE_PHASE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CPPortletKeys.CP_DEFINITIONS, lifecycle);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");

		CPInstance cpInstance = getCPInstance();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS);

		return portletURL.toString();
	}

	public CommerceWarehouseItem getCommerceWarehouseItem(
			CommerceWarehouse commerceWarehouse)
		throws PortalException {

		CPInstance cpInstance = getCPInstance();

		return _commerceWarehouseItemService.fetchCommerceWarehouseItem(
			commerceWarehouse.getCommerceWarehouseId(),
			cpInstance.getCPInstanceId());
	}

	public List<CommerceWarehouse> getCommerceWarehouses()
		throws PortalException {

		List<CommerceWarehouse> commerceWarehouses = _getCommerceWarehouses();

		if (!commerceWarehouses.isEmpty()) {
			return commerceWarehouses;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			cpRequestHelper.getRenderRequest());

		CommerceWarehouse commerceWarehouse =
			_commerceWarehouseService.getDefaultCommerceWarehouse(
				serviceContext);

		return Collections.singletonList(commerceWarehouse);
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance == null) {
			long cpInstanceId = ParamUtil.getLong(
				cpRequestHelper.getRenderRequest(), "cpInstanceId");

			_cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);
		}

		return _cpInstance;
	}

	public String getUpdateCommerceWarehouseItemTaglibOnClick(
		long commerceWarehouseId, long commerceWarehouseItemId, int index) {

		RenderResponse renderResponse = cpRequestHelper.getRenderResponse();

		StringBundler sb = new StringBundler(10);

		sb.append(renderResponse.getNamespace());
		sb.append("updateCommerceWarehouseItem");
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(commerceWarehouseId);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(commerceWarehouseItemId);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(index);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	protected final CPRequestHelper cpRequestHelper;

	private List<CommerceWarehouse> _getCommerceWarehouses() {
		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commerceWarehouseService.getCommerceWarehouses(
			themeDisplay.getScopeGroupId(), true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new CommerceWarehouseNameComparator(true));
	}

	private final CommerceWarehouseItemService _commerceWarehouseItemService;
	private final CommerceWarehouseService _commerceWarehouseService;
	private CPInstance _cpInstance;
	private final CPInstanceService _cpInstanceService;
	private final Portal _portal;

}