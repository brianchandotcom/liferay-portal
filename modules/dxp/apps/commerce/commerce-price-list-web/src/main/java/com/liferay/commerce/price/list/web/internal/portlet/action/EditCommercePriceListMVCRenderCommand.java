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

package com.liferay.commerce.price.list.web.internal.portlet.action;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.price.list.constants.CommercePriceListPortletKeys;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelService;
import com.liferay.commerce.price.list.web.internal.display.context.CommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePriceListPortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommercePriceList"
	},
	service = MVCRenderCommand.class
)
public class EditCommercePriceListMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		CommercePriceListDisplayContext commercePriceListDisplayContext =
			new CommercePriceListDisplayContext(
				_commercePriceListActionHelper, _commerceCurrencyService,
				_commerceUserSegmentEntryService,
				_commercePriceListUserSegmentEntryRelService,
				_commercePriceListService, httpServletRequest, _itemSelector);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commercePriceListDisplayContext);

		return "/edit_price_list_screen_navigation.jsp";
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceListActionHelper _commercePriceListActionHelper;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommercePriceListUserSegmentEntryRelService
		_commercePriceListUserSegmentEntryRelService;

	@Reference
	private CommerceUserSegmentEntryService _commerceUserSegmentEntryService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

}