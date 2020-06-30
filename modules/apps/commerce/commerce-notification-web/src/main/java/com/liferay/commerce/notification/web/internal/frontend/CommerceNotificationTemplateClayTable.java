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

import com.liferay.commerce.frontend.ClayMenuActionItem;
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
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateService;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.commerce.notification.type.CommerceNotificationTypeRegistry;
import com.liferay.commerce.notification.web.internal.model.NotificationTemplate;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
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
		"commerce.data.provider.key=" + CommerceNotificationTemplateClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceNotificationTemplateClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceNotificationTemplateClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider,
			   CommerceDataSetDataProvider<NotificationTemplate> {

	public static final String NAME = "notification-templates";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		try {
			NotificationTemplate notificationTemplate =
				(NotificationTemplate)model;

			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				httpServletRequest, CommerceChannel.class.getName(),
				PortletProvider.Action.MANAGE);

			portletURL.setParameter(
				"mvcRenderCommandName", "editCommerceNotificationTemplate");
			portletURL.setParameter(
				"commerceChannelId", String.valueOf(commerceChannelId));
			portletURL.setParameter(
				"commerceNotificationTemplateId",
				String.valueOf(
					notificationTemplate.getNotificationTemplateId()));

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			ClayDataSetAction clayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, portletURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "edit"), null, false,
				false);

			clayDataSetAction.setTarget(
				ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_SIDE_PANEL);

			clayTableActions.add(clayDataSetAction);

			PortletURL deletePortletURL = _portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.ACTION_PHASE);

			String redirect = ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest));

			deletePortletURL.setParameter(
				ActionRequest.ACTION_NAME, "editCommerceNotificationTemplate");
			deletePortletURL.setParameter(Constants.CMD, Constants.DELETE);
			deletePortletURL.setParameter("redirect", redirect);
			deletePortletURL.setParameter(
				"commerceNotificationTemplateId",
				String.valueOf(
					notificationTemplate.getNotificationTemplateId()));

			ClayDataSetAction deleteClayDataSetAction = new ClayDataSetAction(
				StringPool.BLANK, deletePortletURL.toString(), StringPool.BLANK,
				LanguageUtil.get(httpServletRequest, "delete"), null, false,
				false);

			clayTableActions.add(deleteClayDataSetAction);
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

		return _commerceNotificationTemplateService.
			getCommerceNotificationTemplatesCount(commerceChannel.getGroupId());
	}

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		ClayTableSchemaField nameField = clayTableSchemaBuilder.addField(
			"name", "name");

		nameField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addField("type", "type");

		ClayTableSchemaField enabledField = clayTableSchemaBuilder.addField(
			"enabled", "status");

		enabledField.setContentRenderer("label");

		return clayTableSchemaBuilder.build();
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