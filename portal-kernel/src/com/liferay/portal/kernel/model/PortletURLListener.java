/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PortletURLListener extends Serializable {

	public String getListenerClass();

	public int getOrdinal();

	public PortletApp getPortletApp();

	public void setListenerClass(String listenerClass);

	public void setOrdinal(int ordinal);

	public void setPortletApp(PortletApp portletApp);

}