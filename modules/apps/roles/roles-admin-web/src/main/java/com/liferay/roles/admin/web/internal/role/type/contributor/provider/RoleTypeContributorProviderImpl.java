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

package com.liferay.roles.admin.web.internal.role.type.contributor.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = RoleTypeContributorProvider.class)
public class RoleTypeContributorProviderImpl
	implements RoleTypeContributorProvider {

	@Override
	public RoleTypeContributor getRoleTypeContributor(int type) {
		return _roleTypeContributorServiceTrackerMap.getService(type);
	}

	@Override
	public List<RoleTypeContributor> getRoleTypeContributors() {
		return ListUtil.fromCollection(
			_roleTypeContributorServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_roleTypeContributorServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, RoleTypeContributor.class, null,
				(serviceReference, emitter) -> {
					RoleTypeContributor roleTypeContributor =
						_bundleContext.getService(serviceReference);

					emitter.emit(roleTypeContributor.getType());
				});
	}

	@Deactivate
	protected void deactivate() {
		_roleTypeContributorServiceTrackerMap.close();
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<Integer, RoleTypeContributor>
		_roleTypeContributorServiceTrackerMap;

}