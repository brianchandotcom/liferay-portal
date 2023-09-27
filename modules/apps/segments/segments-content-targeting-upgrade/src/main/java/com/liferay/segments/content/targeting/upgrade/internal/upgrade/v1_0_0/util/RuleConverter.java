/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.content.targeting.upgrade.internal.upgrade.v1_0_0.util;

import com.liferay.segments.criteria.Criteria;

/**
 * @author Eduardo García
 */
public interface RuleConverter {

	public void convert(long companyId, Criteria criteria, String typeSettings);

}