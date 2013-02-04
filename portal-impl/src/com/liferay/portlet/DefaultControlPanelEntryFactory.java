/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DefaultControlPanelEntryFactory {

	public static ControlPanelEntry getInstance() {
		return _controlPanelEntry;
	}

	public static void setInstance(ControlPanelEntry controlPanelEntry) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(controlPanelEntry));
		}

		if (controlPanelEntry == null) {
			controlPanelEntry = _createControlPanelEntry();
		}

		_controlPanelEntry = controlPanelEntry;
	}

	public void afterPropertiesSet() {
		_controlPanelEntry = _createControlPanelEntry();
	}

	private static ControlPanelEntry _createControlPanelEntry() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " +
					PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);
		}

		ControlPanelEntry controlPanelEntry = null;

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		try {
			controlPanelEntry =
				(ControlPanelEntry)InstanceFactory.newInstance(
					classLoader, PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return controlPanelEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultControlPanelEntryFactory.class);

	private static volatile ControlPanelEntry _controlPanelEntry;

}