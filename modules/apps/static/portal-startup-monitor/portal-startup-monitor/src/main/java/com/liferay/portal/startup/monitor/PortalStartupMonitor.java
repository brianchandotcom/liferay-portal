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

package com.liferay.portal.startup.monitor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.ThreadUtil;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true, service = {})
public class PortalStartupMonitor {

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		target = ModuleServiceLifecycle.PORTAL_INITIALIZED
	)
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {

		_componentContext.disableComponent(
			PortalStartupMonitor.class.getName());
	}

	public void unsetModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		_thread = new Thread("Portal Startup Monitoring Thread") {

			@Override
			public void run() {
				StringBundler sb = new StringBundler(4);

				while (true) {
					try {
						Thread.sleep(_SLEEP);
					}
					catch (InterruptedException ie) {
						break;
					}

					sb.append("Thread dump for portal startup after waited ");
					sb.append(_SLEEP);
					sb.append("ms:\n");
					sb.append(ThreadUtil.threadDump());

					System.out.println(sb.toString());

					sb.setIndex(0);
				}
			}

		};

		_thread.setDaemon(true);

		_thread.start();
	}

	@Deactivate
	protected void deactivate() {
		_thread.interrupt();
	}

	private static final long _SLEEP = 600000;

	private ComponentContext _componentContext;
	private Thread _thread;

}