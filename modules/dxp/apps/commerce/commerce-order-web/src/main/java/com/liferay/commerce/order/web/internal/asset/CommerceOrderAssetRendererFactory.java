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

package com.liferay.commerce.order.web.internal.asset;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER},
	service = AssetRendererFactory.class
)
public class CommerceOrderAssetRendererFactory
	extends BaseAssetRendererFactory<CommerceOrder> {

	public static final String TYPE = "commerce-order";

	public CommerceOrderAssetRendererFactory() {
		setCategorizable(false);
		setClassName(CommerceOrder.class.getName());
		setPortletId(CommercePortletKeys.COMMERCE_ORDER);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<CommerceOrder> getAssetRenderer(long classPK, int type)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(classPK);

		CommerceOrderAssetRenderer commerceOrderAssetRenderer =
			new CommerceOrderAssetRenderer(commerceOrder);

		commerceOrderAssetRenderer.setAssetRendererType(type);
		commerceOrderAssetRenderer.setServletContext(_servletContext);

		return commerceOrderAssetRenderer;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.order.web)"
	)
	private ServletContext _servletContext;

}