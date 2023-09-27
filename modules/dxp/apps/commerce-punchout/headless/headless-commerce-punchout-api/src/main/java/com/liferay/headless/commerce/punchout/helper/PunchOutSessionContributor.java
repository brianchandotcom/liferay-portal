/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.punchout.helper;

import java.util.HashMap;

/**
 * @author Jaclyn Ong
 */
public interface PunchOutSessionContributor {

	public HashMap<String, Object> getPunchOutSessionAttributes(
		PunchOutContext punchOutContext);

}