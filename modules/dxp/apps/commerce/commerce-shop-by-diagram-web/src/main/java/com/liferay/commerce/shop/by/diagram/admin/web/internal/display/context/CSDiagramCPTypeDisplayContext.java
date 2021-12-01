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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.util.CSDiagramSettingUtil;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingLocalService;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramTypeRegistry;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Sbarra
 */
public class CSDiagramCPTypeDisplayContext {

	public CSDiagramCPTypeDisplayContext(
		CommerceAccountHelper commerceAccountHelper,
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceProductViewPermission commerceProductViewPermission,
		CSDiagramSettingLocalService csDiagramSettingLocalService,
		CSDiagramTypeRegistry csDiagramTypeRegistry, DLURLHelper dlURLHelper,
		HttpServletRequest httpServletRequest, Portal portal) {

		_commerceAccountHelper = commerceAccountHelper;
		_commerceChannelLocalService = commerceChannelLocalService;
		_commerceProductViewPermission = commerceProductViewPermission;
		_csDiagramSettingLocalService = csDiagramSettingLocalService;
		_csDiagramTypeRegistry = csDiagramTypeRegistry;
		_dlURLHelper = dlURLHelper;
		_httpServletRequest = httpServletRequest;
		_portal = portal;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getCSDiagramMappedProductsAPIURL(
		CPCatalogEntry cpCatalogEntry) {

		return "/o/headless-commerce-admin-catalog/v1.0/products/" +
			cpCatalogEntry.getCProductId() + "/mapped-products";
	}

	public CSDiagramSetting getCSDiagramSetting(long cpDefinitionId)
		throws PortalException {

		long commerceAccountId = 0;

		RenderRequest renderRequest =
			(RenderRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		CommerceAccount commerceAccount =
			_commerceAccountHelper.getCurrentCommerceAccount(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						_portal.getScopeGroupId(renderRequest)),
				_portal.getHttpServletRequest(renderRequest));

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		_commerceProductViewPermission.check(
			cpRequestHelper.getPermissionChecker(), commerceAccountId,
			cpDefinitionId);

		return _csDiagramSettingLocalService.
			fetchCSDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	public CSDiagramType getCSDiagramType(String type) {
		return _csDiagramTypeRegistry.getCSDiagramType(type);
	}

	public String getImageURL(long cpDefinitionId) throws Exception {
		return CSDiagramSettingUtil.getImageURL(
			getCSDiagramSetting(cpDefinitionId), _dlURLHelper);
	}

	protected final CPRequestHelper cpRequestHelper;

	private final CommerceAccountHelper _commerceAccountHelper;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceProductViewPermission _commerceProductViewPermission;
	private final CSDiagramSettingLocalService _csDiagramSettingLocalService;
	private final CSDiagramTypeRegistry _csDiagramTypeRegistry;
	private final DLURLHelper _dlURLHelper;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}