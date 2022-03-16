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

import com.liferay.commerce.frontend.model.LabelField;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.commerce.notification.web.internal.model.NotificationTemplate;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"clay.data.provider.key=" + CommerceNotificationTemplateClayTable.NAME,
		"clay.data.set.display.name=" + CommerceNotificationTemplateClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDataProvider.class,
		ClayDataSetDisplayView.class
	}
)
public class CommerceNotificationTemplateClayTable
	extends BaseTableClayDataSetDisplayView
	implements ClayDataSetActionProvider,
			   ClayDataSetDataProvider<NotificationTemplate> {

	public static final String NAME = "notification-templates";

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField nameClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("name", "name");

		nameClayTableSchemaField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField("type", "type");

		ClayTableSchemaField enabledClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("enabled", "status");

		enabledClayTableSchemaField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		NotificationTemplate notificationTemplate = (NotificationTemplate)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				PortletURL portletURL = PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(
						httpServletRequest, CommerceChannel.class.getName(),
						PortletProvider.Action.MANAGE)
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildPortletURL();

				long commerceChannelId = ParamUtil.getLong(
					httpServletRequest, "commerceChannelId");

				dropdownItem.setHref(
					portletURL, "mvcRenderCommandName",
					"/commerce_channels/edit_commerce_notification_template",
					"commerceChannelId", String.valueOf(commerceChannelId),
					"commerceNotificationTemplateId",
					String.valueOf(
						notificationTemplate.getNotificationTemplateId()));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.create(
						_portal.getControlPanelPortletURL(
							httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
							PortletRequest.ACTION_PHASE)
					).setActionName(
						"/commerce_channels/edit_commerce_notification_template"
					).setCMD(
						Constants.DELETE
					).setRedirect(
						ParamUtil.getString(
							httpServletRequest, "currentUrl",
							_portal.getCurrentURL(httpServletRequest))
					).setParameter(
						"commerceNotificationTemplateId",
						notificationTemplate.getNotificationTemplateId()
					).buildPortletURL());

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	@Override
	public List<NotificationTemplate> getItems(
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

		List<CommerceNotificationTemplate> commerceNotificationTemplates =
			_commerceNotificationTemplateService.
				getCommerceNotificationTemplates(
					commerceChannel.getGroupId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null);

		List<NotificationTemplate> notificationTemplates = new ArrayList<>();

		for (CommerceNotificationTemplate commerceNotificationTemplate :
				commerceNotificationTemplates) {

			notificationTemplates.add(
				new NotificationTemplate(
					_getEnabled(
						commerceNotificationTemplate, httpServletRequest),
					commerceNotificationTemplate.getName(),
					commerceNotificationTemplate.
						getCommerceNotificationTemplateId(),
					_getType(
						commerceNotificationTemplate,
						themeDisplay.getLocale())));
		}

		return notificationTemplates;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplatesCount(commerceChannel.getGroupId());
	}

	private LabelField _getEnabled(
		CommerceNotificationTemplate commerceNotificationTemplate,
		HttpServletRequest httpServletRequest) {

		if (commerceNotificationTemplate.isEnabled()) {
			return new LabelField(
				"success", LanguageUtil.get(httpServletRequest, "enabled"));
		}

		return new LabelField(
			"danger", LanguageUtil.get(httpServletRequest, "disabled"));
	}

	private String _getType(
		CommerceNotificationTemplate commerceNotificationTemplate,
		Locale locale) {

		CommerceNotificationType commerceNotificationType =
			_commerceNotificationTypeRegistry.getCommerceNotificationType(
				commerceNotificationTemplate.getType());

		if (commerceNotificationType == null) {
			return StringPool.BLANK;
		}

		return commerceNotificationType.getLabel(locale);
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceNotificationTemplateService
		_commerceNotificationTemplateService;

	@Reference
	private CommerceNotificationTypeRegistry _commerceNotificationTypeRegistry;

	@Reference
	private Portal _portal;

}