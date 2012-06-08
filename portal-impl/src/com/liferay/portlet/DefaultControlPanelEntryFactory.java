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
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultControlPanelEntryFactory {

	public static ControlPanelEntry getInstance() {
		if (_controlPanelEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " +
						PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);
			}

			ClassLoader classLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			try {
				_controlPanelEntry = (ControlPanelEntry)classLoader.loadClass(
					PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS).
						newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _controlPanelEntry.getClass().getName());
		}

		return _controlPanelEntry;
	}

	public static void setInstance(ControlPanelEntry controlPanelEntryFactory) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + controlPanelEntryFactory.getClass().getName());
		}

		_controlPanelEntry = controlPanelEntryFactory;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultControlPanelEntryFactory.class);

	private static ControlPanelEntry _controlPanelEntry = null;

}