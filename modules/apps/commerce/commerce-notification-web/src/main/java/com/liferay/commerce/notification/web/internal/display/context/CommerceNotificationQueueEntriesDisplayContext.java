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

package com.liferay.commerce.notification.web.internal.display.context;

import com.liferay.commerce.frontend.ClayCreationMenu;
import com.liferay.commerce.frontend.ClayCreationMenuActionItem;
import com.liferay.commerce.notification.web.internal.display.context.util.CommerceNotificationsRequestHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationQueueEntriesDisplayContext {

	public CommerceNotificationQueueEntriesDisplayContext(
		CommerceChannelLocalService commerceChannelLocalService,
		HttpServletRequest httpServletRequest) {

		_commerceChannelLocalService = commerceChannelLocalService;

		_commerceNotificationsRequestHelper =
			new CommerceNotificationsRequestHelper(httpServletRequest);
	}

	public String getAddNotificationTemplateURL() throws Exception {
		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			_commerceNotificationsRequestHelper.getRequest(),
			CommerceChannel.class.getName(), PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceNotificationTemplate");
		portletURL.setParameter(
			"commerceChannelId", String.valueOf(getCommerceChannelId()));

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public CommerceChannel getCommerceChannel() throws PortalException {
		long commerceChannelId = ParamUtil.getLong(
			_commerceNotificationsRequestHelper.getRequest(),
			"commerceChannelId");

		if (commerceChannelId > 0) {
			return _commerceChannelLocalService.getCommerceChannel(
				commerceChannelId);
		}

		return null;
	}

	public long getCommerceChannelId() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		if (commerceChannel == null) {
			return 0;
		}

		return commerceChannel.getCommerceChannelId();
	}

	public ClayCreationMenu getNotificationTemplateClayCreationMenu()
		throws Exception {

		ClayCreationMenu clayCreationMenu = new ClayCreationMenu();

		clayCreationMenu.addClayCreationMenuActionItem(
			getAddNotificationTemplateURL(),
			LanguageUtil.get(
				_commerceNotificationsRequestHelper.getRequest(),
				"add-notification-template"),
			ClayCreationMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_SIDE_PANEL);

		return clayCreationMenu;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceNotificationsRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String delta = ParamUtil.getString(
			_commerceNotificationsRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		return portletURL;
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CommerceNotificationsRequestHelper
		_commerceNotificationsRequestHelper;

}