/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletURLListener;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLListenerImpl implements PortletURLListener {

	public PortletURLListenerImpl(
		String listenerClass, int ordinal, PortletApp portletApp) {

		_listenerClass = listenerClass;
		_ordinal = ordinal;
		_portletApp = portletApp;
	}

	@Override
	public String getListenerClass() {
		return _listenerClass;
	}

	@Override
	public int getOrdinal() {
		return _ordinal;
	}

	@Override
	public PortletApp getPortletApp() {
		return _portletApp;
	}

	@Override
	public void setListenerClass(String listenerClass) {
		_listenerClass = listenerClass;
	}

	@Override
	public void setOrdinal(int ordinal) {
		_ordinal = ordinal;
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	private String _listenerClass;
	private int _ordinal;
	private PortletApp _portletApp;

}