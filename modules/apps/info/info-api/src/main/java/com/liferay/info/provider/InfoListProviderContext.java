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

package com.liferay.info.provider;

import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 * @deprecated As of Mueller (7.2.x), moved to {@link
 *             com.liferay.info.list.provider.InfoListProviderContext}
 */
@Deprecated
@ProviderType
public interface InfoListProviderContext {

	public Company getCompany();

	public Optional<Group> getGroupOptional();

	public Optional<InfoDisplayObjectProvider>
		getInfoDisplayObjectProviderOptional();

	public Optional<Layout> getLayoutOptional();

	public User getUser();

}