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

package com.liferay.commerce.channel.web.internal.portlet;

import com.liferay.commerce.channel.web.internal.display.context.CommerceChannelDisplayContext;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatusRegistry;
import com.liferay.commerce.product.channel.CommerceChannelTypeRegistry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Channels",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = {CommerceChannelsPortlet.class, Portlet.class}
)
public class CommerceChannelsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		CommerceChannelDisplayContext commerceChannelDisplayContext =
			new CommerceChannelDisplayContext(
				_commerceChannelHealthStatusRegistry,
				_commerceChannelModelResourcePermission,
				_commerceChannelService, _commerceChannelTypeRegistry,
				_commerceCurrencyService, _configurationProvider,
				_cpTaxCategoryLocalService, _dlAppLocalService,
				_portal.getHttpServletRequest(renderRequest), _itemSelector,
				_portal, _workflowDefinitionLinkLocalService,
				_workflowDefinitionManager);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceChannelDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CommerceChannelHealthStatusRegistry
		_commerceChannelHealthStatusRegistry;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceChannelTypeRegistry _commerceChannelTypeRegistry;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}