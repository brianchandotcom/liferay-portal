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

package com.liferay.commerce.product.content.web.internal.display.context;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.web.configuration.CPCompareContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.util.CPCompareUtil;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCompareContentMiniDisplayContext {

	public CPCompareContentMiniDisplayContext(
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionService cpDefinitionService,
			LayoutLocalService layoutLocalService, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		_cpDefinitionHelper = cpDefinitionHelper;
		_cpDefinitionService = cpDefinitionService;
		_layoutLocalService = layoutLocalService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpCompareContentMiniPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPCompareContentMiniPortletInstanceConfiguration.class);

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		_cpDefinitionIds = CPCompareUtil.getCPDefinitionIds(httpServletRequest);
	}

	public String getClearCompareProductsURL() {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "clearCompareProducts");

		String redirect = PortalUtil.getCurrentURL(_renderRequest);

		portletURL.setParameter("redirect", redirect);

		return portletURL.toString();
	}

	public String getCompareProductsURL() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout curLayout = themeDisplay.getLayout();

		long plid = PortalUtil.getPlidFromPortletId(
			themeDisplay.getScopeGroupId(), curLayout.isPrivateLayout(),
			CPPortletKeys.CP_COMPARE_CONTENT_WEB);

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return StringPool.BLANK;
		}

		return PortalUtil.getLayoutURL(layout, themeDisplay);
	}

	public List<CPDefinition> getCPDefinitions() throws PortalException {
		List<CPDefinition> cpDefinitions = new ArrayList<>();

		for (Long cpDefinitionId : _cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.fetchCPDefinition(
				cpDefinitionId);

			if (cpDefinition != null) {
				cpDefinitions.add(cpDefinition);
			}
		}

		if (cpDefinitions.size() > getProductsLimit()) {
			return cpDefinitions.subList(0, getProductsLimit());
		}

		return cpDefinitions;
	}

	public String getDeleteCompareProductURL(long cpDefinitionId) {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteCompareProduct");

		String redirect = PortalUtil.getCurrentURL(_renderRequest);

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	public String getDisplayStyle() {
		return _cpCompareContentMiniPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		return _cpCompareContentMiniPortletInstanceConfiguration.
			displayStyleGroupId();
	}

	public String getProductFriendlyURL(long cpDefinitionId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _cpDefinitionHelper.getFriendlyURL(cpDefinitionId, themeDisplay);
	}

	public int getProductsLimit() {
		return
			_cpCompareContentMiniPortletInstanceConfiguration.productsLimit();
	}

	private final CPCompareContentMiniPortletInstanceConfiguration
		_cpCompareContentMiniPortletInstanceConfiguration;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final List<Long> _cpDefinitionIds;
	private final CPDefinitionService _cpDefinitionService;
	private final LayoutLocalService _layoutLocalService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}