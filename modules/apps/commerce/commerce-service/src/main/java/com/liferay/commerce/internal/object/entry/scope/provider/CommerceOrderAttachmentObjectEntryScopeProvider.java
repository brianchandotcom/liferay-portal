/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.entry.scope.provider;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.object.entry.scope.provider.ObjectEntryScopeProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "object.definition.external.reference.code=L_COMMERCE_ORDER_ATTACHMENT",
	service = ObjectEntryScopeProvider.class
)
public class CommerceOrderAttachmentObjectEntryScopeProvider
	implements ObjectEntryScopeProvider {

	@Override
	public String getGroupId(User user) throws Exception {
		long commerceOrderId = 0;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			commerceOrderId = GetterUtil.getLong(
				serviceContext.getAttribute("commerceOrderId"));
		}

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		if (commerceOrder.getCompanyId() != user.getCompanyId()) {
			throw new NoSuchOrderException(
				StringBundler.concat(
					"Unable to find order with ID ", commerceOrderId,
					" and company ", user.getCompanyId()));
		}

		return String.valueOf(commerceOrder.getGroupId());
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}