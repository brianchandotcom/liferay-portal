/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	property = "javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
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