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

package com.liferay.commerce.notification.web.internal.frontend;

import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableDataSetDisplayView;
import com.liferay.commerce.frontend.clay.table.ClayTableSchema;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilder;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaBuilderFactory;
import com.liferay.commerce.frontend.clay.table.ClayTableSchemaField;
import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationQueueEntryService;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.commerce.notification.web.internal.model.NotificationEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceNotificationEntryClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceNotificationEntryClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceNotificationEntryClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider,
			   CommerceDataSetDataProvider<NotificationEntry> {

	public static final String NAME = "notification-entries";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		try {
			NotificationEntry notificationEntry = (NotificationEntry)model;

			PortletURL portletURL = _portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.ACTION_PHASE);

			String redirect = ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest));

			portletURL.setParameter(
				ActionRequest.ACTION_NAME,
				"editCommerceNotificationQueueEntry");
			portletURL.setParameter(Constants.CMD, "resend");
			portletURL.setParameter("redirect", redirect);
			portletURL.setParameter(
				"commerceNotificationQueueEntryId",
				String.valueOf(notificationEntry.getNotificationEntryId()));

			clayTableActions.add(
				new ClayDataSetAction(
					StringPool.BLANK, portletURL.toString(), StringPool.BLANK,
					LanguageUtil.get(httpServletRequest, "resend"), null, false,
					false));

			portletURL.setParameter(Constants.CMD, Constants.DELETE);

			clayTableActions.add(
				new ClayDataSetAction(
					StringPool.BLANK, portletURL.toString(), StringPool.BLANK,
					LanguageUtil.get(httpServletRequest, "delete"), null, false,
					false));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return clayTableActions;
	}

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		return _commerceNotificationQueueEntryService.
			getCommerceNotificationQueueEntriesCount(
				commerceChannel.getGroupId());
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		clayTableSchemaBuilder.addField("from", "from");

		clayTableSchemaBuilder.addField("to", "to");

		clayTableSchemaBuilder.addField("type", "type");

		ClayTableSchemaField enabledField = clayTableSchemaBuilder.addField(
			"sent", "status");

		enabledField.setContentRenderer("label");

		clayTableSchemaBuilder.addField("priority", "priority");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<NotificationEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries =
			_commerceNotificationQueueEntryService.
				getCommerceNotificationQueueEntries(
					commerceChannel.getGroupId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null);

		List<NotificationEntry> notificationEntries = new ArrayList<>();

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry :
				commerceNotificationQueueEntries) {

			CommerceNotificationTemplate commerceNotificationTemplate =
				_commerceNotificationTemplateService.
					getCommerceNotificationTemplate(
						commerceNotificationQueueEntry.
							getCommerceNotificationTemplateId());

			CommerceNotificationType commerceNotificationType =
				_commerceNotificationTypeRegistry.getCommerceNotificationType(
					commerceNotificationTemplate.getType());

			notificationEntries.add(
				new NotificationEntry(
					commerceNotificationQueueEntry.getFromName(),
					commerceNotificationQueueEntry.
						getCommerceNotificationQueueEntryId(),
					commerceNotificationQueueEntry.getPriority(),
					_getSent(
						commerceNotificationQueueEntry, httpServletRequest),
					commerceNotificationQueueEntry.getToName(),
					commerceNotificationType.getLabel(
						themeDisplay.getLocale())));
		}

		return notificationEntries;
	}

	private LabelField _getSent(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		HttpServletRequest httpServletRequest) {

		if (commerceNotificationQueueEntry.isSent()) {
			return new LabelField(
				"success", LanguageUtil.get(httpServletRequest, "sent"));
		}

		return new LabelField(
			"danger", LanguageUtil.get(httpServletRequest, "unsent"));
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceNotificationQueueEntryService
		_commerceNotificationQueueEntryService;

	@Reference
	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

	@Reference
	private CommerceNotificationTypeRegistry _commerceNotificationTypeRegistry;

	@Reference
	private Portal _portal;

}