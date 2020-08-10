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

package com.liferay.commerce.channel.web.internal.frontend;

import com.liferay.commerce.channel.web.internal.model.HealthCheck;
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
import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
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
	immediate = true,
	property = {
		"commerce.data.provider.key=" + CommerceChannelHealthCheckClayTable.NAME,
		"commerce.data.set.display.name=" + CommerceChannelHealthCheckClayTable.NAME
	},
	service = {
		ClayDataSetActionProvider.class, ClayDataSetDisplayView.class,
		CommerceDataSetDataProvider.class
	}
)
public class CommerceChannelHealthCheckClayTable
	extends ClayTableDataSetDisplayView
	implements ClayDataSetActionProvider,
			   CommerceDataSetDataProvider<HealthCheck> {

	public static final String NAME = "channel-health-check";

	@Override
	public List<ClayDataSetAction> clayDataSetActions(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<ClayDataSetAction> clayTableActions = new ArrayList<>();

		try {
			HealthCheck healthCheck = (HealthCheck)model;

			PortletURL portletURL = _portal.getControlPanelPortletURL(
				httpServletRequest, CPPortletKeys.COMMERCE_CHANNELS,
				PortletRequest.ACTION_PHASE);

			String redirect = ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest));

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "editCommerceChannelHealthStatus");
			portletURL.setParameter("redirect", redirect);
			portletURL.setParameter(
				"commerceChannelHealthStatusKey", healthCheck.getKey());

			long commerceChannelId = ParamUtil.getLong(
				httpServletRequest, "commerceChannelId");

			portletURL.setParameter(
				"commerceChannelId", String.valueOf(commerceChannelId));

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", _portal.getLocale(httpServletRequest),
				getClass());

			clayTableActions.add(
				new ClayDataSetAction(
					StringPool.BLANK, portletURL.toString(), StringPool.BLANK,
					LanguageUtil.get(
						httpServletRequest, resourceBundle, "fix-issue"),
					null, false, false));
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

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.clayTableSchemaBuilder();

		clayTableSchemaBuilder.addField("name", "name");

		clayTableSchemaBuilder.addField("description", "description");

		return clayTableSchemaBuilder.build();
	}

	@Override
	public List<HealthCheck> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
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
					HtmlUtil.escape(
						commerceChannelHealthStatus.getName(
							_portal.getLocale(httpServletRequest))),
					HtmlUtil.escape(
						commerceChannelHealthStatus.getDescription(
							_portal.getLocale(httpServletRequest)))));
		}

		return healthChecks;
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

	@Reference
	private CommerceChannelHealthStatusRegistry
		_commerceChannelHealthStatusRegistry;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private Portal _portal;

}