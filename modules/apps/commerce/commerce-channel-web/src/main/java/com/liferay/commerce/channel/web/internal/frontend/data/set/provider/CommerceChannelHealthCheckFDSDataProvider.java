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

package com.liferay.commerce.channel.web.internal.frontend.data.set.provider;

import com.liferay.commerce.channel.web.internal.constants.CommerceChannelFDSNames;
import com.liferay.commerce.channel.web.internal.model.HealthCheck;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "fds.data.provider.key=" + CommerceChannelFDSNames.CHANNEL_HEALTH_CHECK,
	service = FDSDataProvider.class
)
public class CommerceChannelHealthCheckFDSDataProvider
	implements FDSDataProvider<HealthCheck> {

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
	private Portal _portal;

}