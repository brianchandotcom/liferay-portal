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

package com.liferay.users.admin.internal.instance.lifecycle;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class IndexDefaultServiceAccountPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!StartupHelperUtil.isUpgrading()) {
			return;
		}

		User defaultServiceAccountUser = _userLocalService.fetchDefaultUser(
			company.getCompanyId(), UserConstants.TYPE_SERVICE_ACCOUNT);

		if (defaultServiceAccountUser == null) {
			return;
		}

		try {
			StartupHelperUtil.setUpgrading(false);

			if (_indexerRegistry.getIndexer(Contact.class) == null) {
				Dictionary<String, ?> serviceProperties = new Hashtable<>(
					Collections.singletonMap(
						"indexer.class.name",
						_contactModelSearchConfigurator.getClassName()));

				_serviceRegistrations.add(
					_bundleContext.registerService(
						(Class<Indexer<?>>)(Class<?>)Indexer.class,
						_contactIndexer, serviceProperties));
			}

			if (_indexerRegistry.getIndexer(User.class) == null) {
				Dictionary<String, ?> serviceProperties = new Hashtable<>(
					Collections.singletonMap(
						"indexer.class.name",
						_userModelSearchConfigurator.getClassName()));

				_serviceRegistrations.add(
					_bundleContext.registerService(
						(Class<Indexer<?>>)(Class<?>)Indexer.class,
						_userIndexer, serviceProperties));
			}

			_userIndexer.reindex(defaultServiceAccountUser);
		}
		finally {
			StartupHelperUtil.setUpgrading(true);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Indexer<?>> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.Contact)"
	)
	private Indexer<Contact> _contactIndexer;

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.Contact)"
	)
	private ModelSearchConfigurator _contactModelSearchConfigurator;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private RoleLocalService _roleLocalService;

	private final List<ServiceRegistration<Indexer<?>>> _serviceRegistrations =
		new ArrayList<>();

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.User)"
	)
	private Indexer<User> _userIndexer;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.User)"
	)
	private ModelSearchConfigurator _userModelSearchConfigurator;

}