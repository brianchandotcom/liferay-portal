/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view;

import java.util.List;

/**
 * @author Marco Leo
 */
public interface FDSViewContextContributorRegistry {

	public List<FDSViewContextContributor> getFDSViewContextContributors(
		String fdsViewName);

}