/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.test.util.search;

import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author André de Oliveira
 */
public interface UserGroupBlueprint {

	public long getCompanyId();

	public String getDescription();

	public String getName();

	public ServiceContext getServiceContext();

	public long getUserId();

	public interface UserGroupBlueprintBuilder {

		public UserGroupBlueprint build();

		public UserGroupBlueprintBuilder companyId(long companyId);

		public UserGroupBlueprintBuilder description(String description);

		public UserGroupBlueprintBuilder name(String name);

		public UserGroupBlueprintBuilder serviceContext(
			ServiceContext serviceContext);

		public UserGroupBlueprintBuilder userId(long userId);

	}

}