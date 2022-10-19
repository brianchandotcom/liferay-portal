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

package com.liferay.commerce.order.content.web.internal.importer.type;

import com.liferay.commerce.configuration.CommerceOrderImporterTypeConfiguration;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.exception.CommerceOrderImporterTypeException;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.content.web.internal.importer.type.util.CommerceOrderImporterTypeUtil;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItemImpl;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceOrderImporterTypeConfiguration",
	immediate = true,
	property = "commerce.order.importer.type.key=" + CommerceWishListsCommerceOrderImporterTypeImpl.KEY,
	service = CommerceOrderImporterType.class
)
public class CommerceWishListsCommerceOrderImporterTypeImpl
	implements CommerceOrderImporterType {

	public static final String KEY = "wish-lists";

	@Override
	public Object getCommerceOrderImporterItem(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceWishListId = ParamUtil.getLong(
			httpServletRequest, getCommerceOrderImporterItemParamName());

		if (commerceWishListId > 0) {
			return _commerceWishListService.getCommerceWishList(
				commerceWishListId);
		}

		return null;
	}

	@Override
	public String getCommerceOrderImporterItemParamName() {
		return "commerceWishListId";
	}

	@Override
	public List<CommerceOrderImporterItem> getCommerceOrderImporterItems(
			CommerceOrder commerceOrder, Object object)
		throws Exception {

		if ((object == null) || !(object instanceof CommerceWishList)) {
			throw new CommerceOrderImporterTypeException();
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		return CommerceOrderImporterTypeUtil.getCommerceOrderImporterItems(
			_commerceContextFactory, commerceOrder,
			_getCommerceOrderImporterItemImpls(
				commerceChannel.getGroupId(), (CommerceWishList)object),
			_commerceOrderItemService, _commerceOrderPriceCalculation,
			_commerceOrderService, _userLocalService);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.format(resourceBundle, "import-from-x", KEY);
	}

	@Override
	public boolean isActive(CommerceOrder commerceOrder)
		throws PortalException {

		return _commerceOrderImporterTypeConfiguration.enabled();
	}

	@Override
	public void render(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/pending_commerce_orders/importer_type/commerce_wish_lists.jsp");
	}

	@Override
	public void renderCommerceOrderPreview(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/pending_commerce_orders/importer_type/common/preview.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceOrderImporterTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceOrderImporterTypeConfiguration.class, properties);
	}

	private CommerceOrderImporterItemImpl[] _getCommerceOrderImporterItemImpls(
			long commerceChannelGroupId, CommerceWishList commerceWishList)
		throws Exception {

		return TransformUtil.transformToArray(
			_commerceWishListItemService.getCommerceWishListItems(
				commerceWishList.getCommerceWishListId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null),
			commerceWishListItem -> _toCommerceOrderImporterItemImpl(
				commerceChannelGroupId, commerceWishListItem),
			CommerceOrderImporterItemImpl.class);
	}

	private CommerceOrderImporterItemImpl _toCommerceOrderImporterItemImpl(
			long commerceChannelGroupId,
			CommerceWishListItem commerceWishListItem)
		throws Exception {

		CommerceOrderImporterItemImpl commerceOrderImporterItemImpl =
			new CommerceOrderImporterItemImpl();

		CPInstance cpInstance = _cpInstanceLocalService.fetchCProductInstance(
			commerceWishListItem.getCProductId(),
			commerceWishListItem.getCPInstanceUuid());

		if (cpInstance == null) {
			CPDefinition cpDefinition = commerceWishListItem.getCPDefinition();

			commerceOrderImporterItemImpl.setNameMap(cpDefinition.getNameMap());

			commerceOrderImporterItemImpl.setErrorMessages(
				new String[] {"the-product-is-no-longer-available"});
			commerceOrderImporterItemImpl.setQuantity(1);
		}
		else {
			CPInstance firstAvailableReplacementCPInstance =
				_cpInstanceHelper.fetchFirstAvailableReplacementCPInstance(
					commerceChannelGroupId, cpInstance.getCPInstanceId());

			if (firstAvailableReplacementCPInstance != null) {
				commerceOrderImporterItemImpl.setReplacingSKU(
					cpInstance.getSku());

				cpInstance = firstAvailableReplacementCPInstance;
			}

			commerceOrderImporterItemImpl.setCPInstanceId(
				cpInstance.getCPInstanceId());
			commerceOrderImporterItemImpl.setSku(cpInstance.getSku());

			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			commerceOrderImporterItemImpl.setCPDefinitionId(
				cpDefinition.getCPDefinitionId());
			commerceOrderImporterItemImpl.setNameMap(cpDefinition.getNameMap());

			commerceOrderImporterItemImpl.setQuantity(
				_cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance));
		}

		String json = commerceWishListItem.getJson();

		if (Validator.isNull(json)) {
			json = "[]";
		}

		commerceOrderImporterItemImpl.setJSON(json);

		return commerceOrderImporterItemImpl;
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	private volatile CommerceOrderImporterTypeConfiguration
		_commerceOrderImporterTypeConfiguration;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private CPDefinitionInventoryEngine _cpDefinitionInventoryEngine;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}