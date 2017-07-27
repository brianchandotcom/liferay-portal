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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author     Brian Wing Shun Chan
 * @author     Shuyang Zhou
 * @author     Peter Fellwock
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class DefaultControlPanelEntryFactory {

	public static ControlPanelEntry getInstance() {
		return ServiceTrackerHolder._serviceTracker.getService();
	}

	private static class ServiceTrackerHolder {

		private static final
			ServiceTracker<ControlPanelEntry, ControlPanelEntry>
				_serviceTracker;

		static {
			Registry registry = RegistryUtil.getRegistry();

			Filter filter = registry.getFilter(
				"(&(!(javax.portlet.name=*))(objectClass=" +
					ControlPanelEntry.class.getName() + "))");

			_serviceTracker = registry.trackServices(filter);

			_serviceTracker.open();
		}

	}

}