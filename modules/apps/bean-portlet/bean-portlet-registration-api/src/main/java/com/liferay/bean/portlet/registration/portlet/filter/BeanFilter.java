/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet.filter;

import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

import javax.portlet.filter.PortletFilter;

/**
 * @author Neil Griffin
 */
public interface BeanFilter {

	public Class<? extends PortletFilter> getFilterClass();

	public String getFilterName();

	public Map<String, String> getInitParams();

	public Set<String> getLifecycles();

	public int getOrdinal();

	public Set<String> getPortletNames();

	public Dictionary<String, Object> toDictionary();

}