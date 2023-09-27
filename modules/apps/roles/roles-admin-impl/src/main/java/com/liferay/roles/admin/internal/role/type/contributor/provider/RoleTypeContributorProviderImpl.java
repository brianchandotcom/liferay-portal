/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.role.type.contributor.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.role.type.contributor.provider.RoleTypeContributorProvider;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Drew Brokke
 */
@Component(service = RoleTypeContributorProvider.class)
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