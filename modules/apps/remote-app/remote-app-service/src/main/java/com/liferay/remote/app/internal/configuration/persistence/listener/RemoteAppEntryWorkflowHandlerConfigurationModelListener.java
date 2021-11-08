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

package com.liferay.remote.app.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.remote.app.internal.workflow.RemoteAppEntryWorkflowHandler;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.remote.app.internal.configuration.RemoteAppEntryWorkflowConfiguration",
	service = ConfigurationModelListener.class
)
public class RemoteAppEntryWorkflowHandlerConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(
		String pid, Dictionary<String, Object> properties) {

		boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));

		if (enabled && (_serviceRegistration != null)) {
			_serviceRegistration = _bundleContext.registerService(
				(Class<WorkflowHandler<RemoteAppEntry>>)
					(Class<?>)WorkflowHandler.class,
				new RemoteAppEntryWorkflowHandler(_remoteAppEntryLocalService),
				MapUtil.singletonDictionary(
					"model.class.name", RemoteAppEntry.class.getName()));
		}
		else if (!enabled && (_serviceRegistration != null)) {
			_serviceRegistration.unregister();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	private ServiceRegistration<WorkflowHandler<RemoteAppEntry>>
		_serviceRegistration;

}