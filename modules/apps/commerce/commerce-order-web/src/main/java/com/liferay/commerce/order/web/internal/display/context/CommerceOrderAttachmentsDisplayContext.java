/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderAttachmentAdminFDSActionContributor;
import com.liferay.commerce.order.web.internal.display.context.helper.CommerceOrderRequestHelper;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Tancredi Covioli
 */
public class CommerceOrderAttachmentsDisplayContext {

	public CommerceOrderAttachmentsDisplayContext(
		CommerceOrder commerceOrder,
		List<CommerceOrderAttachmentAdminFDSActionContributor>
			commerceOrderAttachmentAdminFDSActionContributors,
		HttpServletRequest httpServletRequest, Language language) {

		_commerceOrder = commerceOrder;
		_commerceOrderAttachmentAdminFDSActionContributors =
			commerceOrderAttachmentAdminFDSActionContributors;
		_httpServletRequest = httpServletRequest;
		_language = language;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			httpServletRequest);
	}

	public String getAPIURL() {
		return _getBaseAPIURL();
	}

	public Map<String, Object> getAdditionalProps() {
		Map<String, Object> additionalProps =
			HashMapBuilder.<String, Object>put(
				"commerceOrderId", _commerceOrder.getCommerceOrderId()
			).build();

		for (CommerceOrderAttachmentAdminFDSActionContributor
				commerceOrderAttachmentAdminFDSActionContributor :
					_commerceOrderAttachmentAdminFDSActionContributors) {

			additionalProps.putAll(
				commerceOrderAttachmentAdminFDSActionContributor.
					getAdditionalProps(_commerceOrder, _httpServletRequest));
		}

		return additionalProps;
	}

	public long getCommerceOrderId() {
		return _commerceOrder.getCommerceOrderId();
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_commerceOrderRequestHelper.getLiferayPortletResponse()
					).setMVCRenderCommandName(
						"/commerce_order/edit_commerce_order_attachment"
					).setParameter(
						"commerceOrderId", _commerceOrder.getCommerceOrderId()
					).setWindowState(
						LiferayWindowState.POP_UP
					).buildString());
				dropdownItem.setLabel(
					_language.get(_httpServletRequest, "add-attachment"));
				dropdownItem.setTarget("sidePanel");
			}
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			FDSActionDropdownItemList.of(
				FDSActionDropdownItemBuilder.setHref(
					_getEditURL()
				).setIcon(
					"pencil"
				).setLabel(
					_language.get(_httpServletRequest, "edit")
				).setPermissionKey(
					"update"
				).setTarget(
					"sidePanel"
				).build(
					"edit"
				),
				FDSActionDropdownItemBuilder.setHref(
					StringPool.POUND
				).setIcon(
					"download"
				).setLabel(
					_language.get(_httpServletRequest, "download")
				).build(
					"download"
				),
				FDSActionDropdownItemBuilder.setHref(
					StringPool.POUND
				).setIcon(
					"trash"
				).setLabel(
					_language.get(_httpServletRequest, "delete")
				).setPermissionKey(
					"delete"
				).build(
					"delete"
				));

		for (CommerceOrderAttachmentAdminFDSActionContributor
				commerceOrderAttachmentAdminFDSActionContributor :
					_commerceOrderAttachmentAdminFDSActionContributors) {

			fdsActionDropdownItems.addAll(
				commerceOrderAttachmentAdminFDSActionContributor.
					getFDSActionDropdownItems(
						_commerceOrder, _httpServletRequest));
		}

		return fdsActionDropdownItems;
	}

	private String _getBaseAPIURL() {
		return StringBundler.concat(
			Portal.PATH_MODULE, "/headless-commerce-admin-order/v1.0/orders/",
			_commerceOrder.getCommerceOrderId(), "/attachments");
	}

	private String _getEditURL() {
		return PortletURLBuilder.createRenderURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order/edit_commerce_order_attachment"
		).setParameter(
			"commerceOrderAttachmentId", "{id}"
		).setParameter(
			"commerceOrderId", _commerceOrder.getCommerceOrderId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private final CommerceOrder _commerceOrder;
	private final List<CommerceOrderAttachmentAdminFDSActionContributor>
		_commerceOrderAttachmentAdminFDSActionContributors;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;

}