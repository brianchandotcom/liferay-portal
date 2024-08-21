/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.layout.display.page;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.BaseLayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = LayoutDisplayPageProvider.class)
public class CommerceOrderLayoutDisplayPageProvider
	extends BaseLayoutDisplayPageProvider<CommerceOrder> {

	@Override
	public String getClassName() {
		return CommerceOrder.class.getName();
	}

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_COMMERCE_ORDER;
	}

	@Override
	public LayoutDisplayPageObjectProvider<CommerceOrder>
		getLayoutDisplayPageObjectProvider(CommerceOrder commerceOrder) {

		long groupId = commerceOrder.getGroupId();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			groupId = serviceContext.getScopeGroupId();
		}

		return new CommerceOrderLayoutDisplayPageObjectProvider(
			commerceOrder, groupId);
	}

	@Override
	public LayoutDisplayPageObjectProvider<CommerceOrder>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return null;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(
				classPKInfoItemIdentifier.getClassPK());

		if (commerceOrder == null) {
			return null;
		}

		long groupId = commerceOrder.getGroupId();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			groupId = serviceContext.getScopeGroupId();
		}

		return new CommerceOrderLayoutDisplayPageObjectProvider(
			commerceOrder, groupId);
	}

	@Override
	public LayoutDisplayPageObjectProvider<CommerceOrder>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(
				Long.valueOf(urlTitle));

		if (commerceOrder == null) {
			return null;
		}

		return new CommerceOrderLayoutDisplayPageObjectProvider(
			commerceOrder, groupId);
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}