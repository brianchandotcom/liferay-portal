/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.view.list;

import com.liferay.commerce.order.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.list.BaseListFDSView;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFDSNames.PAYMENT_METHODS,
	service = FDSView.class
)
public class CommercePaymentMethodListFDSView extends BaseListFDSView {

	@Override
	public String getDescription() {
		return "description";
	}

	@Override
	public String getThumbnail() {
		return "thumbnail";
	}

	@Override
	public String getTitle() {
		return "title";
	}

}