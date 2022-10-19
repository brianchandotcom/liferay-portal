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

package com.liferay.portal.scheduler.quartz.internal.portal.profile;

import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;
import com.liferay.portal.scheduler.quartz.internal.QuartzSchedulerEngine;
import com.liferay.portal.scheduler.quartz.internal.QuartzSchemaManager;
import com.liferay.portal.scheduler.quartz.internal.QuartzTriggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	protected void activate(ComponentContext componentContext) {
		List<String> supportedPortalProfileNames = null;

		if (GetterUtil.getBoolean(_props.get(PropsKeys.SCHEDULER_ENABLED))) {
			supportedPortalProfileNames = new ArrayList<>();

			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_CE);
			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}
		else {
			supportedPortalProfileNames = Collections.emptyList();

			BundleContext bundleContext = componentContext.getBundleContext();

			_schedulerEngineServiceRegistration = bundleContext.registerService(
				SchedulerEngine.class,
				ProxyFactory.newDummyInstance(SchedulerEngine.class),
				MapUtil.singletonDictionary(
					"scheduler.engine.proxy", Boolean.FALSE));

			_triggerFactoryServiceRegistration = bundleContext.registerService(
				TriggerFactory.class,
				ProxyFactory.newDummyInstance(TriggerFactory.class),
				new HashMapDictionary<>());
		}

		init(
			componentContext, supportedPortalProfileNames,
			QuartzSchedulerEngine.class.getName(),
			QuartzSchemaManager.class.getName(),
			QuartzTriggerFactory.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		if (_triggerFactoryServiceRegistration != null) {
			_triggerFactoryServiceRegistration.unregister();
		}

		if (_schedulerEngineServiceRegistration != null) {
			_schedulerEngineServiceRegistration.unregister();
		}
	}

	@Reference
	private Props _props;

	private ServiceRegistration<SchedulerEngine>
		_schedulerEngineServiceRegistration;
	private ServiceRegistration<TriggerFactory>
		_triggerFactoryServiceRegistration;

}