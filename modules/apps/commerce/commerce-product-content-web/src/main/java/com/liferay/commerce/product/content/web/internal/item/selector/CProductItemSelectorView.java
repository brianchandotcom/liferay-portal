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

package com.liferay.commerce.product.content.web.internal.item.selector;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	enabled = false, property = "item.selector.view.order:Integer=600",
	service = ItemSelectorView.class
)
public class CProductItemSelectorView
	implements InfoItemSelectorView,
			   ItemSelectorView<InfoItemItemSelectorCriterion> {

	@Override
	public String getClassName() {
		return CProduct.class.getName();
	}

	@Override
	public Class<InfoItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "products");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, infoItemItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new CProductsItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemItemSelectorReturnType());

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private class CProductItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public CProductItemDescriptor(
			CProduct cProduct, HttpServletRequest httpServletRequest) {

			_cProduct = cProduct;
			_httpServletRequest = httpServletRequest;
		}

		@Override
		public String getIcon() {
			return null;
		}

		@Override
		public String getImageURL() {
			CPDefinition cpDefinition = _getCpDefinition();

			try {
				return cpDefinition.getDefaultImageFileURL();
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		@Override
		public Date getModifiedDate() {
			return _cProduct.getModifiedDate();
		}

		@Override
		public String getPayload() {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			CPDefinition cpDefinition = _getCpDefinition();

			return JSONUtil.put(
				"className", CProduct.class.getName()
			).put(
				"classNameId", _portal.getClassNameId(CProduct.class.getName())
			).put(
				"classPK", _cProduct.getCProductId()
			).put(
				"title", cpDefinition.getName(themeDisplay.getLanguageId())
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					themeDisplay.getLocale(), CProduct.class.getName())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			Date modifiedDate = _cProduct.getModifiedDate();

			String modifiedDateDescription = _language.getTimeDescription(
				locale, System.currentTimeMillis() - modifiedDate.getTime(),
				true);

			return _language.format(
				locale, "x-ago-by-x",
				new Object[] {
					modifiedDateDescription,
					HtmlUtil.escape(_cProduct.getUserName())
				});
		}

		@Override
		public String getTitle(Locale locale) {
			CPDefinition cpDefinition = _getCpDefinition();

			return cpDefinition.getName(locale.getLanguage());
		}

		@Override
		public long getUserId() {
			return _cProduct.getUserId();
		}

		@Override
		public String getUserName() {
			return _cProduct.getUserName();
		}

		private CPDefinition _getCpDefinition() {
			try {
				return _cpDefinitionLocalService.getCPDefinition(
					_cProduct.getPublishedCPDefinitionId());
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		private final CProduct _cProduct;
		private HttpServletRequest _httpServletRequest;

	}

	private class CProductsItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<CProduct> {

		public CProductsItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;
		}

		@Override
		public ItemDescriptor getItemDescriptor(CProduct cProduct) {
			return new CProductItemDescriptor(cProduct, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoItemItemSelectorReturnType();
		}

		@Override
		public SearchContainer<CProduct> getSearchContainer() {
			SearchContainer<CProduct> entriesSearchContainer =
				new SearchContainer<>(
					(PortletRequest)_httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST),
					_portletURL, null, "no-entries-were-found");

			entriesSearchContainer.setTotal(
				_cProductLocalService.getCProductsCount());

			List<CProduct> cProducts = _cProductLocalService.getCProducts(
				entriesSearchContainer.getStart(),
				entriesSearchContainer.getEnd());

			entriesSearchContainer.setResults(cProducts);

			return entriesSearchContainer;
		}

		private HttpServletRequest _httpServletRequest;
		private final PortletURL _portletURL;

	}

}