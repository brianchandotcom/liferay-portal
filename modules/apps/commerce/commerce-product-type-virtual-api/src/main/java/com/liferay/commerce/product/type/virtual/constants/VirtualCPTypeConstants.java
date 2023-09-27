/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.constants;

import com.liferay.commerce.constants.CommerceOrderConstants;

/**
 * @author Andrea Di Giorgi
 */
public class VirtualCPTypeConstants {

	public static final int[] ACTIVATION_STATUSES = {
		CommerceOrderConstants.ORDER_STATUS_COMPLETED,
		CommerceOrderConstants.ORDER_STATUS_PENDING,
		CommerceOrderConstants.ORDER_STATUS_PROCESSING
	};

	public static final String NAME = "virtual";

}