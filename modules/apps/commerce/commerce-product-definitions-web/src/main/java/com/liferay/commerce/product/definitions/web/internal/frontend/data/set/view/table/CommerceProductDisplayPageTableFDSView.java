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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.internal.constants.CommerceProductFDSNames;
import com.liferay.commerce.product.definitions.web.internal.model.ProductDisplayPage;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"fds.data.provider.key=" + CommerceProductFDSNames.PRODUCT_DISPLAY_PAGES,
		"frontend.data.set.name=" + CommerceProductFDSNames.PRODUCT_DISPLAY_PAGES
	},
	service = {FDSActionProvider.class, FDSDataProvider.class, FDSView.class}
)
public class CommerceProductDisplayPageTableFDSView
	extends BaseTableFDSView
	implements FDSActionProvider, FDSDataProvider<ProductDisplayPage> {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		ProductDisplayPage productDisplayPage = (ProductDisplayPage)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductDisplayPageEditURL(
						httpServletRequest,
						productDisplayPage.getProductDisplayPageId()));
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductDisplayPageDeleteURL(
						httpServletRequest,
						productDisplayPage.getProductDisplayPageId()));
				dropdownItem.setLabel(
					_language.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"productName", "product-name",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"layout", "layout"
		).build();
	}

	@Override
	public List<ProductDisplayPage> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<ProductDisplayPage> productDisplayPages = new ArrayList<>();

		BaseModelSearchResult<CPDisplayLayout>
			cpDisplayLayoutBaseModelSearchResult =
				_cpDisplayLayoutService.searchCPDisplayLayout(
					commerceChannel.getCompanyId(),
					commerceChannel.getSiteGroupId(),
					CPDefinition.class.getName(), fdsKeywords.getKeywords(),
					fdsPagination.getStartPosition(),
					fdsPagination.getEndPosition(), sort);

		for (CPDisplayLayout cpDisplayLayout :
				cpDisplayLayoutBaseModelSearchResult.getBaseModels()) {

			productDisplayPages.add(
				new ProductDisplayPage(
					_getLayout(cpDisplayLayout, themeDisplay.getLanguageId()),
					cpDisplayLayout.getCPDisplayLayoutId(),
					_getProductName(
						cpDisplayLayout, themeDisplay.getLanguageId())));
		}

		return productDisplayPages;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		BaseModelSearchResult<CPDisplayLayout>
			cpDisplayLayoutBaseModelSearchResult =
				_cpDisplayLayoutService.searchCPDisplayLayout(
					commerceChannel.getCompanyId(),
					commerceChannel.getSiteGroupId(),
					CPDefinition.class.getName(), fdsKeywords.getKeywords(), 0,
					0, null);

		return cpDisplayLayoutBaseModelSearchResult.getLength();
	}

	private String _getLayout(
		CPDisplayLayout cpDisplayLayout, String languageId) {

		Layout layout = cpDisplayLayout.fetchLayout();

		if (layout == null) {
			return StringPool.BLANK;
		}

		return layout.getName(languageId);
	}

	private String _getProductDisplayPageDeleteURL(
		HttpServletRequest httpServletRequest, long productDisplayPageId) {

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_channels/edit_cp_definition_cp_display_layout"
		).setCMD(
			Constants.DELETE
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"cpDisplayLayoutId", productDisplayPageId
		).buildString();
	}

	private String _getProductDisplayPageEditURL(
			HttpServletRequest httpServletRequest, long productDisplayPageId)
		throws Exception {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceChannel.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/commerce_channels/edit_cp_definition_cp_display_layout"
		).buildPortletURL();

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		portletURL.setParameter(
			"commerceChannelId", String.valueOf(commerceChannelId));

		portletURL.setParameter(
			"cpDisplayLayoutId", String.valueOf(productDisplayPageId));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	private String _getProductName(
		CPDisplayLayout cpDisplayLayout, String languageId) {

		CPDefinition cpDefinition = cpDisplayLayout.fetchCPDefinition();

		if (cpDefinition == null) {
			return StringPool.BLANK;
		}

		return cpDefinition.getName(languageId);
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CPDisplayLayoutService _cpDisplayLayoutService;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}