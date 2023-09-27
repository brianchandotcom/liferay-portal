/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.info.item.renderer;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.content.helper.CPContentHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.util.CommerceAccountHelper;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(service = InfoItemRenderer.class)
public class AvailabilityLabelInfoItemRenderer
	implements InfoItemRenderer<CPDefinition> {

	@Override
	public String getKey() {
		return "cpDefinition-availability-label";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "availability");
	}

	@Override
	public void render(
		CPDefinition cpDefinition, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (cpDefinition == null) {
			return;
		}

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/availability_label/page.jsp");

			CPDefinitionInventory cpDefinitionInventory =
				_cpDefinitionInventoryLocalService.
					fetchCPDefinitionInventoryByCPDefinitionId(
						cpDefinition.getCPDefinitionId());

			httpServletRequest.setAttribute(
				"liferay-commerce:availability-label:displayAvailability",
				cpDefinitionInventory.isDisplayAvailability());

			String namespace = (String)httpServletRequest.getAttribute(
				"liferay-commerce:availability-label:namespace");

			if (Validator.isNull(namespace)) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				namespace = portletDisplay.getNamespace();
			}

			httpServletRequest.setAttribute(
				"liferay-commerce:availability-label:namespace", namespace);

			String availabilityLabel = StringPool.BLANK;
			String labelType = "default";

			long groupId = _portal.getScopeGroupId(httpServletRequest);

			CPCatalogEntry cpCatalogEntry =
				_cpDefinitionHelper.getCPCatalogEntry(
					_getCommerceAccountId(groupId, httpServletRequest), groupId,
					cpDefinition.getCPDefinitionId(),
					_portal.getLocale(httpServletRequest));

			CPSku cpSku = _cpContentHelper.getDefaultCPSku(cpCatalogEntry);

			if (cpSku != null) {
				ProductSettingsModel productSettingsModel =
					_productHelper.getProductSettingsModel(
						cpDefinition.getCPDefinitionId());

				if (productSettingsModel.isShowAvailabilityDot()) {
					JSONObject availabilityContentContributorValueJSONObject =
						_cpContentHelper.
							getAvailabilityContentContributorValueJSONObject(
								cpCatalogEntry, httpServletRequest);

					availabilityLabel =
						availabilityContentContributorValueJSONObject.getString(
							CPContentContributorConstants.AVAILABILITY_NAME,
							availabilityLabel);
					labelType =
						availabilityContentContributorValueJSONObject.getString(
							CPContentContributorConstants.
								AVAILABILITY_DISPLAY_TYPE,
							labelType);
				}
			}

			httpServletRequest.setAttribute(
				"liferay-commerce:availability-label:label", availabilityLabel);
			httpServletRequest.setAttribute(
				"liferay-commerce:availability-label:labelType", labelType);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private long _getCommerceAccountId(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		AccountEntry accountEntry =
			_commerceAccountHelper.getCurrentAccountEntry(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(groupId),
				httpServletRequest);

		long commerceAccountId = 0;

		if (accountEntry != null) {
			commerceAccountId = accountEntry.getAccountEntryId();
		}

		return commerceAccountId;
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPContentHelper _cpContentHelper;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ProductHelper _productHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)"
	)
	private ServletContext _servletContext;

}