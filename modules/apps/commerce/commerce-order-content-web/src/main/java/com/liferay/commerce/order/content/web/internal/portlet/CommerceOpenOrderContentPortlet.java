/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.portlet;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.order.content.web.internal.display.context.CommerceOrderContentDisplayContext;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.commerce.order.status.CommerceOrderStatusRegistry;
import com.liferay.commerce.payment.method.CommercePaymentMethodRegistry;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.percentage.PercentageFormatter;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderNoteService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.term.service.CommerceTermEntryService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-open-order-content",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Open Carts",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/pending_commerce_orders/view.jsp",
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class CommerceOpenOrderContentPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			CommerceOrderContentDisplayContext
				commerceOrderContentDisplayContext =
					new CommerceOrderContentDisplayContext(
						_commerceAddressService, _commerceChannelLocalService,
						_commerceOrderEngine, _commerceOrderHttpHelper,
						_commerceOrderImporterTypeRegistry,
						_commerceOrderNoteService,
						_commerceOrderPriceCalculation, _commerceOrderService,
						_commerceOrderStatusRegistry, _commerceOrderTypeService,
						_commercePaymentMethodGroupRelLocalService,
						_commercePaymentMethodRegistry,
						_commerceTermEntryService, _configurationProvider,
						_dlAppLocalService,
						_portal.getHttpServletRequest(renderRequest),
						_itemSelector, _modelResourcePermission,
						_percentageFormatter, _portletResourcePermission);

			CommerceOrder commerceOrder = _getCommerceOrder(renderRequest);

			if (commerceOrder != null) {
				List<String> errorMessages = new ArrayList<>();

				List<CommerceOrderValidatorResult>
					commerceOrderValidatorResults =
						_commerceOrderValidatorRegistry.validate(
							renderRequest.getLocale(), commerceOrder);

				for (CommerceOrderValidatorResult commerceOrderValidatorResult :
						commerceOrderValidatorResults) {

					errorMessages.add(
						commerceOrderValidatorResult.getLocalizedMessage());
				}

				renderRequest.setAttribute(
					CommerceWebKeys.COMMERCE_ORDER_ERROR_MESSAGES,
					errorMessages);
			}

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceOrderContentDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		super.render(renderRequest, renderResponse);
	}

	private CommerceOrder _getCommerceOrder(PortletRequest portletRequest) {
		String commerceOrderUuid = ParamUtil.getString(
			portletRequest, "commerceOrderUuid");

		try {
			if (Validator.isNotNull(commerceOrderUuid)) {
				long groupId =
					_commerceChannelLocalService.
						getCommerceChannelGroupIdBySiteGroupId(
							_portal.getScopeGroupId(portletRequest));

				return _commerceOrderService.getCommerceOrderByUuidAndGroupId(
					commerceOrderUuid, groupId);
			}

			return _commerceOrderHttpHelper.getCurrentCommerceOrder(
				_portal.getHttpServletRequest(portletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOpenOrderContentPortlet.class);

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderImporterTypeRegistry
		_commerceOrderImporterTypeRegistry;

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

	@Reference
	private CommerceOrderPriceCalculation _commerceOrderPriceCalculation;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderStatusRegistry _commerceOrderStatusRegistry;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommerceOrderValidatorRegistry _commerceOrderValidatorRegistry;

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommercePaymentMethodRegistry _commercePaymentMethodRegistry;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private CommerceTermEntryService _commerceTermEntryService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder> _modelResourcePermission;

	@Reference
	private PercentageFormatter _percentageFormatter;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceOrderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}