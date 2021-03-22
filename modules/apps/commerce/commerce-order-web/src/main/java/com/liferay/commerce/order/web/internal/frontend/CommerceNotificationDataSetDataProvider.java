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

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.frontend.model.AuthorField;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryLocalService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.web.internal.model.Notification;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_NOTIFICATIONS,
	service = ClayDataSetDataProvider.class
)
public class CommerceNotificationDataSetDataProvider
	implements ClayDataSetDataProvider<Notification> {

	@Override
	public List<Notification> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Notification> notifications = new ArrayList<>();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryLocalService.
				getCommerceNotificationQueueEntries(
					commerceOrder.getGroupId(), CommerceOrder.class.getName(),
					commerceOrder.getCommerceOrderId(), true,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				commerceNotificationQueueEntries) {

			User user = _userLocalService.fetchUser(
				commerceNotificationQueueEntry.getUserId());

			notifications.add(
				new Notification(
					commerceNotificationQueueEntry.
						getCommerceNotificationQueueEntryId(),
					new AuthorField(
						getUserPortraitSrc(user, httpServletRequest),
						user.getEmailAddress(), user.getFullName()),
					getSentDate(
						commerceNotificationQueueEntry, httpServletRequest),
					new LabelField(
						"success",
						getCommerceNotificationTemplateType(
							commerceNotificationQueueEntry)),
					commerceNotificationQueueEntry.getSubject(),
					HtmlUtil.extractText(
						commerceNotificationQueueEntry.getBody()),
					getNotificationPanelURL(
						commerceNotificationQueueEntry.
							getCommerceNotificationQueueEntryId(),
						httpServletRequest)));
		}

		return notifications;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		return _commerceNotificationQueueEntryLocalService.
			getCommerceNotificationQueueEntriesCount(
				commerceOrder.getGroupId(), CommerceOrder.class.getName(),
				commerceOrder.getCommerceOrderId(), true);
	}

	protected String getCommerceNotificationTemplateType(
			CommerceNotificationQueueEntry commerceNotificationQueueEntry)
		throws PortalException {

		CommerceNotificationTemplate commerceNotificationTemplate =
			_commerceNotificationTemplateService.
				getCommerceNotificationTemplate(
					commerceNotificationQueueEntry.
						getCommerceNotificationTemplateId());

		return commerceNotificationTemplate.getType();
	}

	protected String getNotificationPanelURL(
			long commerceNotificationQueueEntryId,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceOrder.class.getName(),
				PortletProvider.Action.MANAGE)
		).setMVCRenderCommandName(
			"/commerce_order/view_commerce_notification_queue_entry"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).build();

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		portletURL.setParameter(
			"commerceNotificationQueueEntryId",
			String.valueOf(commerceNotificationQueueEntryId));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL.toString();
	}

	protected String getSentDate(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		HttpServletRequest httpServletRequest) {

		Date sentDate = commerceNotificationQueueEntry.getSentDate();

		String sentDateDescription = StringPool.BLANK;

		if (sentDate != null) {
			sentDateDescription = LanguageUtil.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - sentDate.getTime(), true);
		}

		return sentDateDescription;
	}

	protected String getUserPortraitSrc(
		User user, HttpServletRequest httpServletRequest) {

		StringBundler sb = new StringBundler(5);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathImage());

		sb.append("/user_portrait?screenName=");
		sb.append(user.getScreenName());
		sb.append("&amp;companyId=");
		sb.append(user.getCompanyId());

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationDataSetDataProvider.class);

	@Reference
	private CommerceNotificationQueueEntryLocalService
		_commerceNotificationQueueEntryLocalService;

	@Reference
	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}