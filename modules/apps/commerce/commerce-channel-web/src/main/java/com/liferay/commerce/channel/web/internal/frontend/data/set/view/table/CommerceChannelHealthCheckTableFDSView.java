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

package com.liferay.commerce.channel.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.channel.web.internal.constants.CommerceChannelFDSNames;
import com.liferay.commerce.channel.web.internal.model.HealthCheck;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"fds.data.provider.key=" + CommerceChannelFDSNames.CHANNEL_HEALTH_CHECK,
		"frontend.data.set.name=" + CommerceChannelFDSNames.CHANNEL_HEALTH_CHECK
	},
	service = {FDSActionProvider.class, FDSDataProvider.class, FDSView.class}
)
public class CommerceChannelHealthCheckTableFDSView
	extends BaseTableFDSView
	implements FDSActionProvider, FDSDataProvider<HealthCheck> {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				PortletURL portletURL = PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
						PortletRequest.ACTION_PHASE)
				).setActionName(
					"/commerce_channels/edit_commerce_channel_health_status"
				).setRedirect(
					ParamUtil.getString(
						httpServletRequest, "currentUrl",
						_portal.getCurrentURL(httpServletRequest))
				).setParameter(
					"commerceChannelHealthStatusKey",
					() -> {
						HealthCheck healthCheck = (HealthCheck)model;

						return healthCheck.getKey();
					}
				).buildPortletURL();

				long commerceChannelId = ParamUtil.getLong(
					httpServletRequest, "commerceChannelId");

				portletURL.setParameter(
					"commerceChannelId", String.valueOf(commerceChannelId));

				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", _portal.getLocale(httpServletRequest),
					getClass());

				dropdownItem.setHref(portletURL.toString());
				dropdownItem.setLabel(
					_language.get(
						httpServletRequest, resourceBundle, "fix-issue"));
			}
		).build();
	}

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "name"
		).add(
			"description", "description"
		).build();
	}

	@Override
	public List<HealthCheck> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommerceChannelHealthStatus> commerceChannelHealthStatuses =
			_commerceChannelHealthStatusRegistry.
				getCommerceChannelHealthStatuses();

		List<HealthCheck> healthChecks = new ArrayList<>();

		for (CommerceChannelHealthStatus commerceChannelHealthStatus :
				commerceChannelHealthStatuses) {

			if (commerceChannelHealthStatus.isFixed(
					commerceChannel.getCompanyId(),
					commerceChannel.getCommerceChannelId())) {

				continue;
			}

			healthChecks.add(
				new HealthCheck(
					commerceChannelHealthStatus.getKey(),
					commerceChannelHealthStatus.getName(
						_portal.getLocale(httpServletRequest)),
					commerceChannelHealthStatus.getDescription(
						_portal.getLocale(httpServletRequest))));
		}

		return healthChecks;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		List<CommerceChannelHealthStatus> commerceChannelHealthStatuses =
			_commerceChannelHealthStatusRegistry.
				getCommerceChannelHealthStatuses();

		int healthStatusToFixCount = 0;

		for (CommerceChannelHealthStatus commerceChannelHealthStatus :
				commerceChannelHealthStatuses) {

			if (!commerceChannelHealthStatus.isFixed(
					commerceChannel.getCompanyId(),
					commerceChannel.getCommerceChannelId())) {

				healthStatusToFixCount++;
			}
		}

		return healthStatusToFixCount;
	}

	@Reference
	private CommerceChannelHealthStatusRegistry
		_commerceChannelHealthStatusRegistry;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}