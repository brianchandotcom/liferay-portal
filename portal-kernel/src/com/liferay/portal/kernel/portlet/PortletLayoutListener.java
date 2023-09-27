/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Brian Wing Shun Chan
 */
public interface PortletLayoutListener {

	public void onAddToLayout(String portletId, long plid)
		throws PortletLayoutListenerException;

	public void onMoveInLayout(String portletId, long plid)
		throws PortletLayoutListenerException;

	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException;

	public void onSetup(String portletId, long plid)
		throws PortletLayoutListenerException;

	public void updatePropertiesOnRemoveFromLayout(
			String portletId, UnicodeProperties typeSettingsUnicodeProperties)
		throws PortletLayoutListenerException;

}