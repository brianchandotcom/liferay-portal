/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.contributor;

/**
 * Invoked during permission checking, this interface dynamically alters roles
 * calculated from persisted assignment and inheritance. Implementations must
 * maximize efficiency to avoid potentially dramatic performance degredation.
 *
 * @author Raymond Augé
 */
public interface RoleContributor {

	public void contribute(RoleCollection roleCollection);

}