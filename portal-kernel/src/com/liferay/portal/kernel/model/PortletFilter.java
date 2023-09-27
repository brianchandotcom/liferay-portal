/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PortletFilter extends Serializable {

	public String getFilterClass();

	public String getFilterName();

	public Map<String, String> getInitParams();

	public Set<String> getLifecycles();

	public int getOrdinal();

	public PortletApp getPortletApp();

	public void setFilterClass(String filterClass);

	public void setFilterName(String filterName);

	public void setInitParams(Map<String, String> initParams);

	public void setLifecycles(Set<String> lifecycles);

	public void setOrdinal(int ordinal);

	public void setPortletApp(PortletApp portletApp);

}