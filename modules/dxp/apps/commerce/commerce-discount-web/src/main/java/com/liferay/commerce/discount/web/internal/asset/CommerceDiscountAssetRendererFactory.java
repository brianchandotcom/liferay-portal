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

package com.liferay.commerce.discount.web.internal.asset;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CommerceDiscountPortletKeys.COMMERCE_DISCOUNT,
	service = AssetRendererFactory.class
)
public class CommerceDiscountAssetRendererFactory
	extends BaseAssetRendererFactory<CommerceDiscount> {

	public static final String TYPE = "commerce-discount";

	public CommerceDiscountAssetRendererFactory() {
		setCategorizable(false);
		setClassName(CommerceDiscount.class.getName());
		setPortletId(CommerceDiscountPortletKeys.COMMERCE_DISCOUNT);
	}

	@Override
	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException {

		return null;
	}

	@Override
	public AssetRenderer<CommerceDiscount> getAssetRenderer(
			long classPK, int type)
		throws PortalException {

		CommerceDiscount commerceDiscount =
			_commerceDiscountLocalService.getCommerceDiscount(classPK);

		CommerceDiscountAssetRenderer commerceDiscountAssetRenderer =
			new CommerceDiscountAssetRenderer(commerceDiscount);

		commerceDiscountAssetRenderer.setAssetRendererType(type);
		commerceDiscountAssetRenderer.setServletContext(_servletContext);

		return commerceDiscountAssetRenderer;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.discount.web)"
	)
	private ServletContext _servletContext;

}