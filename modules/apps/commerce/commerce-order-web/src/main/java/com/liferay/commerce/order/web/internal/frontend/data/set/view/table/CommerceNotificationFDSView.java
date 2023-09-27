/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.view.table;

import com.liferay.commerce.order.web.internal.constants.CommerceOrderFDSNames;
import com.liferay.frontend.data.set.view.FDSView;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOrderFDSNames.NOTIFICATIONS,
	service = FDSView.class
)
public class CommerceNotificationFDSView implements FDSView {

	@Override
	public String getContentRenderer() {
		return "emailsList";
	}

	@Override
	public String getLabel() {
		return "emails";
	}

	@Override
	public String getName() {
		return "emailsList";
	}

	@Override
	public String getThumbnail() {
		return "emails";
	}

}