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

package com.liferay.bean.portlet.cdi.extension.internal;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class BeanPortletDefaultImpl extends BaseBeanPortletImpl {

	public BeanPortletDefaultImpl(
		String portletName, String displayCategory,
		Map<String, String> liferayConfiguration) {

		_portletName = portletName;
		_displayCategory = displayCategory;
		_liferayConfiguration = liferayConfiguration;
	}

	@Override
	public String getDisplayCategory() {
		return _displayCategory;
	}

	@Override
	public Map<String, String> getLiferayConfiguration() {
		return _liferayConfiguration;
	}

	@Override
	public String getPortletClassName() {
		return null;
	}

	@Override
	public Set<PortletDependency> getPortletDependencies() {
		return Collections.emptySet();
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public String getResourceBundle() {
		return null;
	}

	@Override
	public Dictionary<String, Object> toDictionary(BeanApp beanApp) {
		Dictionary<String, Object> dictionary = super.toDictionary(beanApp);

		dictionary.put("javax.portlet.info.title", _portletName);

		return dictionary;
	}

	private final String _displayCategory;
	private final Map<String, String> _liferayConfiguration;
	private final String _portletName;

}