/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.theme.PortletDisplay;

/**
 * @author Shuyang Zhou
 */
public class PortletDisplayModel {

	public PortletDisplayModel(PortletDisplay portletDisplay) {
		_id = portletDisplay.getId();
		_instanceId = portletDisplay.getInstanceId();
		_portletName = portletDisplay.getPortletName();
		_resourcePK = portletDisplay.getResourcePK();
		_rootPortletId = portletDisplay.getRootPortletId();
		_title = portletDisplay.getTitle();
	}

	public String getId() {
		return _id;
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getPortletName() {
		return _portletName;
	}

	public String getResourcePK() {
		return _resourcePK;
	}

	public String getRootPortletId() {
		return _rootPortletId;
	}

	public String getTitle() {
		return _title;
	}

	private final String _id;
	private final String _instanceId;
	private final String _portletName;
	private final String _resourcePK;
	private final String _rootPortletId;
	private final String _title;

}