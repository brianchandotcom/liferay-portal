/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletFilter;

import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletFilterImpl implements PortletFilter {

	public PortletFilterImpl(
		String filterName, String filterClass, int ordinal,
		Set<String> lifecycles, Map<String, String> initParams,
		PortletApp portletApp) {

		_filterName = filterName;
		_filterClass = filterClass;
		_ordinal = ordinal;
		_lifecycles = lifecycles;
		_initParams = initParams;
		_portletApp = portletApp;
	}

	public PortletFilterImpl(
		String filterName, String filterClass, Set<String> lifecycles,
		Map<String, String> initParams, PortletApp portletApp) {

		this(filterName, filterClass, 0, lifecycles, initParams, portletApp);
	}

	@Override
	public String getFilterClass() {
		return _filterClass;
	}

	@Override
	public String getFilterName() {
		return _filterName;
	}

	@Override
	public Map<String, String> getInitParams() {
		return _initParams;
	}

	@Override
	public Set<String> getLifecycles() {
		return _lifecycles;
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
	public void setFilterClass(String filterClass) {
		_filterClass = filterClass;
	}

	@Override
	public void setFilterName(String filterName) {
		_filterName = filterName;
	}

	@Override
	public void setInitParams(Map<String, String> initParams) {
		_initParams = initParams;
	}

	@Override
	public void setLifecycles(Set<String> lifecycles) {
		_lifecycles = lifecycles;
	}

	@Override
	public void setOrdinal(int ordinal) {
		_ordinal = ordinal;
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	private String _filterClass;
	private String _filterName;
	private Map<String, String> _initParams;
	private Set<String> _lifecycles;
	private int _ordinal;
	private PortletApp _portletApp;

}