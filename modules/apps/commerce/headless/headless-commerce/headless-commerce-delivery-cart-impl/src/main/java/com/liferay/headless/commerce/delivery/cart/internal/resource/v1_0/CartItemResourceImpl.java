/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.internal.resource.v1_0;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.exception.NoSuchOrderItemException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter.CartItemDTOConverterContext;
import com.liferay.headless.commerce.delivery.cart.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartItemResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cart-item.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = CartItemResource.class
)
public class CartItemResourceImpl extends BaseCartItemResourceImpl {

	@Override
	public void deleteCartItem(Long cartItemId) throws Exception {
		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceContext commerceContext = _commerceContextFactory.create(
			contextCompany.getCompanyId(), commerceOrder.getGroupId(),
			contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
			commerceOrder.getCommerceAccountId());

		_commerceOrderItemService.deleteCommerceOrderItem(
			cartItemId, commerceContext);
	}

	@Override
	public void deleteCartItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.
				fetchCommerceOrderItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderItem == null) {
			throw new NoSuchOrderItemException(
				"Unable to find order item with external reference code " +
					externalReferenceCode);
		}

		deleteCartItem(commerceOrderItem.getCommerceOrderItemId());
	}

	@Override
	public Page<CartItem> getCartByExternalReferenceCodeItemsPage(
			String externalReferenceCode, Long skuId, Pagination pagination)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			_filterCartItems(
				transform(
					_commerceOrderItemService.getCommerceOrderItems(
						commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					commerceOrderItem -> {
						if ((skuId != null) &&
							!Objects.equals(
								commerceOrderItem.getCPInstanceId(), skuId)) {

							return null;
						}

						return _toCartItem(
							commerceOrder.getCommerceAccountId(),
							commerceOrderItem);
					})));
	}

	@Override
	public CartItem getCartItem(Long cartItemId) throws Exception {
		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		return _toCartItem(
			commerceOrder.getCommerceAccountId(), commerceOrderItem);
	}

	@Override
	public CartItem getCartItemByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.
				fetchCommerceOrderItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderItem == null) {
			throw new NoSuchOrderItemException(
				"Unable to find order item with external reference code " +
					externalReferenceCode);
		}

		return getCartItem(commerceOrderItem.getCommerceOrderItemId());
	}

	@NestedField(parentClass = Cart.class, value = "cartItems")
	@Override
	public Page<CartItem> getCartItemsPage(
			@NestedFieldId("id") Long cartId, Long skuId, Pagination pagination)
		throws Exception {

		if (cartId == 0) {
			return Page.of(Collections.emptyList());
		}

		return Page.of(
			_filterCartItems(
				transform(
					_commerceOrderItemService.getCommerceOrderItems(
						cartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					commerceOrderItem -> {
						if ((skuId != null) &&
							!Objects.equals(
								commerceOrderItem.getCPInstanceId(), skuId)) {

							return null;
						}

						CommerceOrder commerceOrder =
							commerceOrderItem.getCommerceOrder();

						return _toCartItem(
							commerceOrder.getCommerceAccountId(),
							commerceOrderItem);
					})));
	}

	@Override
	public CartItem patchCartItem(Long cartItemId, CartItem cartItem)
		throws Exception {

		return super.patchCartItem(cartItemId, cartItem);
	}

	@Override
	public CartItem patchCartItemByExternalReferenceCode(
			String externalReferenceCode, CartItem cartItem)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.
				fetchCommerceOrderItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderItem == null) {
			throw new NoSuchOrderItemException(
				"Unable to find order item with external reference code " +
					externalReferenceCode);
		}

		return patchCartItem(
			commerceOrderItem.getCommerceOrderItemId(), cartItem);
	}

	@Override
	public CartItem postCartByExternalReferenceCodeItem(
			String externalReferenceCode, CartItem cartItem)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return _toCartItem(
			commerceOrder.getCommerceAccountId(),
			_updateCartItem(cartItem, commerceOrder));
	}

	@Override
	public CartItem postCartItem(Long cartId, CartItem cartItem)
		throws Exception {

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			cartId);

		return _toCartItem(
			commerceOrder.getCommerceAccountId(),
			_updateCartItem(cartItem, commerceOrder));
	}

	@Override
	public CartItem putCartItem(Long cartItemId, CartItem cartItem)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(cartItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		commerceOrderItem = _commerceOrderItemService.updateCommerceOrderItem(
			commerceOrderItem.getCommerceOrderItemId(), cartItem.getQuantity(),
			_commerceContextFactory.create(
				contextCompany.getCompanyId(), commerceOrder.getGroupId(),
				contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
				commerceOrder.getCommerceAccountId()),
			_serviceContextHelper.getServiceContext(
				commerceOrder.getGroupId()));

		long shippingAddressId = GetterUtil.getLong(
			cartItem.getShippingAddressId());

		if (shippingAddressId == 0) {
			CommerceAddress commerceAddress =
				_commerceAddressService.
					fetchCommerceAddressByExternalReferenceCode(
						cartItem.getShippingAddressExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (commerceAddress != null) {
				shippingAddressId = commerceAddress.getCommerceAddressId();
			}
			else {
				shippingAddressId = commerceOrderItem.getShippingAddressId();
			}
		}

		commerceOrderItem =
			_commerceOrderItemService.updateCommerceOrderItemInfo(
				commerceOrderItem.getCommerceOrderItemId(), shippingAddressId,
				cartItem.getDeliveryGroup(),
				commerceOrderItem.getPrintedNote());

		return _toCartItem(
			commerceOrder.getCommerceAccountId(), commerceOrderItem);
	}

	@Override
	public CartItem putCartItemByExternalReferenceCode(
			String externalReferenceCode, CartItem cartItem)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.
				fetchCommerceOrderItemByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrderItem == null) {
			throw new NoSuchOrderItemException(
				"Unable to find order item with external reference code " +
					externalReferenceCode);
		}

		return putCartItem(
			commerceOrderItem.getCommerceOrderItemId(), cartItem);
	}

	private List<CartItem> _filterCartItems(List<CartItem> cartItems) {
		Map<Long, CartItem> cartItemsMap = new HashMap<>();

		for (CartItem cartItem : cartItems) {
			cartItemsMap.put(cartItem.getId(), cartItem);
		}

		for (CartItem cartItem : cartItems) {
			Long parentCartItemId = cartItem.getParentCartItemId();

			if (parentCartItemId == null) {
				continue;
			}

			CartItem parentCartItem = cartItemsMap.get(parentCartItemId);

			if (parentCartItem == null) {
				continue;
			}

			CartItem[] parentCartItemCartItems = parentCartItem.getCartItems();

			parentCartItem.setCartItems(
				() -> {
					if (parentCartItemCartItems == null) {
						return new CartItem[] {cartItem};
					}

					return ArrayUtil.append(parentCartItemCartItems, cartItem);
				});

			cartItemsMap.remove(cartItem.getId());
		}

		return new ArrayList(cartItemsMap.values());
	}

	private CartItem _toCartItem(
			long commerceAccountId, CommerceOrderItem commerceOrderItem)
		throws Exception {

		return _orderItemDTOConverter.toDTO(
			new CartItemDTOConverterContext(
				commerceAccountId, commerceOrderItem.getCommerceOrderItemId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private CommerceOrderItem _updateCartItem(
			CartItem cartItem, CommerceOrder commerceOrder)
		throws Exception {

		SkuUnitOfMeasure skuUnitOfMeasure = cartItem.getSkuUnitOfMeasure();
		String skuUnitOfMeasureKey = StringPool.BLANK;

		if (skuUnitOfMeasure != null) {
			skuUnitOfMeasureKey = skuUnitOfMeasure.getKey();
		}

		long replacedSkuId = GetterUtil.getLong(cartItem.getReplacedSkuId());

		if (replacedSkuId == 0) {
			CPInstance replacedSku =
				_cpInstanceService.fetchCPInstanceByExternalReferenceCode(
					cartItem.getReplacedSkuExternalReferenceCode(),
					contextCompany.getCompanyId());

			if (replacedSku != null) {
				replacedSkuId = replacedSku.getCPInstanceId();
			}
		}

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.addOrUpdateCommerceOrderItem(
				commerceOrder.getCommerceOrderId(), cartItem.getSkuId(),
				cartItem.getOptions(),
				BigDecimal.valueOf(GetterUtil.get(cartItem.getQuantity(), 1)),
				replacedSkuId, BigDecimal.ZERO, skuUnitOfMeasureKey,
				_commerceContextFactory.create(
					contextCompany.getCompanyId(), commerceOrder.getGroupId(),
					contextUser.getUserId(), commerceOrder.getCommerceOrderId(),
					commerceOrder.getCommerceAccountId()),
				_serviceContextHelper.getServiceContext(
					commerceOrder.getGroupId()));

		long shippingAddressId = GetterUtil.getLong(
			cartItem.getShippingAddressId());

		if (shippingAddressId == 0) {
			CommerceAddress commerceAddress =
				_commerceAddressService.
					fetchCommerceAddressByExternalReferenceCode(
						cartItem.getShippingAddressExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (commerceAddress != null) {
				shippingAddressId = commerceAddress.getCommerceAddressId();
			}
			else {
				shippingAddressId = commerceOrderItem.getShippingAddressId();
			}
		}

		return _commerceOrderItemService.updateCommerceOrderItemInfo(
			commerceOrderItem.getCommerceOrderItemId(), shippingAddressId,
			commerceOrderItem.getDeliveryGroup(),
			commerceOrderItem.getPrintedNote());
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference(target = DTOConverterConstants.CART_ITEM_DTO_CONVERTER)
	private DTOConverter<CommerceOrderItem, CartItem> _orderItemDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}