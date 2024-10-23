/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.checkout.web.internal.frontend.data.set.provider;

import com.liferay.commerce.checkout.web.internal.constants.CommerceCheckoutFDSNames;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = "fds.data.provider.key=" + CommerceCheckoutFDSNames.DELIVERY_GROUPS,
	service = FDSActionProvider.class
)
public class CommerceCheckoutDeliveryGroupFDSActionProvider
	implements FDSActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setData(
					HashMapBuilder.<String, Object>put(
						"action", "view"
					).put(
						"title",
						_language.get(httpServletRequest, "delivery-group")
					).put(
						"viewDeliveryGroupURL",
						_getViewDeliveryGroupURL(themeDisplay)
					).build());

				dropdownItem.setHref(StringPool.BLANK);
				dropdownItem.setLabel(
					_language.get(httpServletRequest, Constants.VIEW));
				dropdownItem.setTarget("link");
			}
		).build();
	}

	private String _getViewDeliveryGroupURL(ThemeDisplay themeDisplay)
		throws PortalException {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				themeDisplay.getRequest(), portletDisplay.getId(),
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_checkout/view_commerce_checkout_delivery_group"
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException);
		}

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCheckoutDeliveryGroupFDSActionProvider.class);

	@Reference
	private Language _language;

}