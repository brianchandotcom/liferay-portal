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

package com.liferay.commerce.product.content.web.internal.portlet;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.commerce.media.CommerceCatalogDefaultImage;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.display.context.CPPublisherDisplayContext;
import com.liferay.commerce.product.content.web.internal.helper.CPPublisherWebHelper;
import com.liferay.commerce.product.data.source.CPDataSourceRegistry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-product-publisher",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.header-portlet-css=/product_publisher/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=false",
		"javax.portlet.display-name=Product Publisher",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/product_publisher/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_PUBLISHER_WEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = {CPPublisherPortlet.class, Portlet.class}
)
public class CPPublisherPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			CPPublisherDisplayContext cpPublisherDisplayContext =
				new CPPublisherDisplayContext(
					_amImageHTMLTagFactory, _commerceCatalogDefaultImage,
					_commerceMediaResolver, _cpAttachmentFileEntryLocalService,
					_cpContentListEntryRendererRegistry,
					_cpContentListRendererRegistry, _cpDataSourceRegistry,
					_cpDefinitionHelper, _cpDefinitionLocalService,
					_cpFriendlyURL, _cpPublisherWebHelper,
					_cpTypeServicesTracker, _friendlyURLEntryLocalService,
					_portal.getHttpServletRequest(renderRequest), _portal);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpPublisherDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPPublisherPortlet.class);

	@Reference
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private CommerceCatalogDefaultImage _commerceCatalogDefaultImage;

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;

	@Reference
	private CPContentListRendererRegistry _cpContentListRendererRegistry;

	@Reference
	private CPDataSourceRegistry _cpDataSourceRegistry;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private CPPublisherWebHelper _cpPublisherWebHelper;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}