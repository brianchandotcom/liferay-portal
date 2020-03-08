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