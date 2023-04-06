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

package com.liferay.commerce.payment.internal.upgrade.v1_4_0;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Arrays;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentEntryUpgradeProcess extends UpgradeProcess {

	public CommercePaymentEntryUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			CommercePaymentEntry.class.getName(), Arrays.asList(_PERMISSIONS),
			true);

		_resourceActionLocalService.checkResourceActions(
			CommercePaymentEntryAudit.class.getName(),
			Arrays.asList(_PERMISSIONS), true);
	}

	private static final String[] _PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private final ResourceActionLocalService _resourceActionLocalService;

}