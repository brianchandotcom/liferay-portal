/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.fragment.renderer;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = FragmentRenderer.class)
public class InfoBoxFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "commerce-order";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				StringUtil.read(
					getClass(),
					"/com/liferay/commerce/order/content/web/internal" +
						"/fragment/renderer/info_box/dependencies" +
							"/configuration.json"));

			return _fragmentEntryConfigurationParser.translateConfiguration(
				jsonObject, resourceBundle);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "info-box");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		return FeatureFlagManagerUtil.isEnabled("COMMERCE-9410");
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		String field = _getConfigurationValue(
			fragmentRendererContext, fragmentEntryLink, "field");

		boolean readOnly = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(),
				fragmentRendererContext.getLocale(), "readOnly"));

		if (!readOnly && ArrayUtil.contains(_READ_ONLY_FIELDS, field)) {
			_printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"the-info-box-component-is-not-correctly-configured");

			return;
		}

		httpServletRequest.setAttribute(
			"liferay-commerce:info-box:buttonStyle",
			_getConfigurationValue(
				fragmentRendererContext, fragmentEntryLink, "buttonStyle"));
		httpServletRequest.setAttribute(
			"liferay-commerce:info-box:field", field);

		CommerceOrder commerceOrder = null;

		InfoItemReference infoItemReference =
			(InfoItemReference)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_REFERENCE);

		if (infoItemReference != null) {
			try {
				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

				commerceOrder = _commerceOrderService.getCommerceOrder(
					classPKInfoItemIdentifier.getClassPK());
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}

				return;
			}
		}

		if (commerceOrder == null) {
			Object infoItem = httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM);

			if ((infoItem == null) || !(infoItem instanceof CommerceOrder)) {
				if (_isEditMode(httpServletRequest)) {
					httpServletRequest.setAttribute(
						"liferay-commerce:info-box:fieldValue",
						_getFieldLabel(fragmentEntryLink, field));
				}

				return;
			}

			commerceOrder = (CommerceOrder)infoItem;
		}

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/fragment/renderer/info_box/page.jsp");

			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:additionalProps",
				_getAdditionalProps(commerceOrder, field));
			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:commerceOrderId",
				commerceOrder.getCommerceOrderId());
			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:fieldValue",
				_getFieldValue(
					commerceOrder, field, fragmentRendererContext.getLocale()));
			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:fieldValueType",
				_getEditableFieldValueType(field));
			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:hasPermission",
				_commerceOrderModelResourcePermission.contains(
					PermissionThreadLocal.getPermissionChecker(), commerceOrder,
					ActionKeys.UPDATE));

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:label",
				_language.get(
					fragmentRendererContext.getLocale(),
					_getConfigurationValue(
						fragmentRendererContext, fragmentEntryLink, "label")));

			String namespace = (String)httpServletRequest.getAttribute(
				"liferay-commerce:info-box:namespace");

			if (Validator.isNull(namespace)) {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				namespace = portletDisplay.getNamespace();
			}

			if (Validator.isNull(namespace)) {
				namespace = StringUtil.randomId() + StringPool.UNDERLINE;
			}

			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:namespace", namespace);

			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:open", commerceOrder.isOpen());
			httpServletRequest.setAttribute(
				"liferay-commerce:info-box:readOnly", readOnly);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private Map<String, Object> _getAdditionalProps(
			CommerceOrder commerceOrder, String field)
		throws PortalException {

		Map<String, Object> additionalProps = new HashMap<>();

		if (field.equals("shippingMethod")) {
			CommerceShippingMethod commerceShippingMethod =
				commerceOrder.getCommerceShippingMethod();

			if (commerceShippingMethod != null) {
				if (Validator.isNull(commerceOrder.getShippingOptionName())) {
					additionalProps.put(
						"value", commerceShippingMethod.getEngineKey());
				}
				else {
					additionalProps.put(
						"value",
						commerceShippingMethod.getEngineKey() + "#" +
							commerceOrder.getShippingOptionName());
				}
			}
		}

		return additionalProps;
	}

	private String _getConfigurationValue(
		FragmentRendererContext fragmentRendererContext,
		FragmentEntryLink fragmentEntryLink, String name) {

		return GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(),
				fragmentRendererContext.getLocale(), name));
	}

	private String _getEditableFieldValueType(String field) {
		if (field.equals("requestedDeliveryDate")) {
			return "date";
		}

		return "text";
	}

	private String _getFieldLabel(
		FragmentEntryLink fragmentEntryLink, String field) {

		try {
			JSONObject configurationJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getConfiguration());

			JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
				"fieldSets");

			JSONArray fieldsJSONArray = fieldSetsJSONArray.getJSONObject(
				0
			).getJSONArray(
				"fields"
			);

			JSONObject typeOptionsJSONObject = fieldsJSONArray.getJSONObject(
				0
			).getJSONObject(
				"typeOptions"
			);

			JSONArray validValuesJSONArray = typeOptionsJSONObject.getJSONArray(
				"validValues");

			for (Object validValueObject : validValuesJSONArray) {
				JSONObject validValueJSONObject = (JSONObject)validValueObject;

				String value = validValueJSONObject.getString("value");

				if (value.equals(field)) {
					return validValueJSONObject.getString("label");
				}
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return StringPool.BLANK;
	}

	private String _getFieldValue(
			CommerceOrder commerceOrder, String field, Locale locale)
		throws PortalException {

		if (field.equals("accountInfo")) {
			AccountEntry accountEntry = commerceOrder.getAccountEntry();

			if (accountEntry == null) {
				return StringPool.BLANK;
			}

			return StringBundler.concat(
				accountEntry.getName(), StringPool.NEW_LINE, StringPool.POUND,
				accountEntry.getAccountEntryId());
		}
		else if (field.equals("channelName")) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceOrder.getGroupId());

			return commerceChannel.getName();
		}
		else if (field.equals("orderDate")) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				DateTimeFormatterBuilder.getLocalizedDateTimePattern(
					FormatStyle.SHORT, FormatStyle.SHORT,
					IsoChronology.INSTANCE, locale),
				locale);

			return dateFormat.format(commerceOrder.getOrderDate());
		}
		else if (field.equals("orderType")) {
			CommerceOrderType commerceOrderType =
				_commerceOrderTypeService.fetchCommerceOrderType(
					commerceOrder.getCommerceOrderTypeId());

			if (commerceOrderType != null) {
				return commerceOrderType.getName(locale);
			}
		}
		else if (field.equals("purchaseOrderNumber")) {
			return commerceOrder.getPurchaseOrderNumber();
		}
		else if (field.equals("requestedDeliveryDate")) {
			if (commerceOrder.getRequestedDeliveryDate() == null) {
				return StringPool.BLANK;
			}

			return String.valueOf(commerceOrder.getRequestedDeliveryDate());
		}
		else if (field.equals("shippingMethod")) {
			CommerceShippingMethod commerceShippingMethod =
				commerceOrder.getCommerceShippingMethod();

			if (commerceShippingMethod != null) {
				if (Validator.isNull(commerceOrder.getShippingOptionName())) {
					return commerceShippingMethod.getName(locale);
				}

				CommerceShippingEngine commerceShippingEngine =
					_commerceShippingEngineRegistry.getCommerceShippingEngine(
						commerceShippingMethod.getEngineKey());

				return StringBundler.concat(
					commerceShippingMethod.getName(locale), " - ",
					commerceShippingEngine.getCommerceShippingOptionLabel(
						commerceOrder.getShippingOptionName(), locale));
			}
		}

		return StringPool.BLANK;
	}

	private boolean _isEditMode(HttpServletRequest httpServletRequest) {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return true;
		}

		return false;
	}

	private void _printPortletMessageInfo(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String message) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			StringBundler sb = new StringBundler(3);

			sb.append("<div class=\"portlet-msg-info\">");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			sb.append(themeDisplay.translate(message));

			sb.append("</div>");

			printWriter.write(sb.toString());
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}
	}

	private static final String[] _READ_ONLY_FIELDS = {
		"accountInfo", "channelName", "orderDate", "orderType"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		InfoBoxFragmentRenderer.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.order.content.web)"
	)
	private ServletContext _servletContext;

}