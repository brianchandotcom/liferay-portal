/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.instance.lifecycle;

import com.liferay.commerce.helper.CommerceSAPHelper;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class CommerceServicePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User user = _userLocalService.getGuestUser(company.getCompanyId());

		_commerceSAPHelper.addCommerceDefaultSAPEntries(
			company.getCompanyId(), user.getUserId());
	}

	@Reference
	private CommerceSAPHelper _commerceSAPHelper;

	@Reference
	private UserLocalService _userLocalService;

}