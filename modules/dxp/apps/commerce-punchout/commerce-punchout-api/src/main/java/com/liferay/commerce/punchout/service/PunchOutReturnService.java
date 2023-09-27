/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.service;

import com.liferay.commerce.model.CommerceOrder;

/**
 * @author Jaclyn Ong
 */
public interface PunchOutReturnService {

	public String returnToPunchOutVendor(
			CommerceOrder commerceOrder, String url)
		throws Exception;

}