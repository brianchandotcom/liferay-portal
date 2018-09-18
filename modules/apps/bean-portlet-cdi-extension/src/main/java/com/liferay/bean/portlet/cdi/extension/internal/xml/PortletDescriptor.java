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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.bean.portlet.cdi.extension.internal.BeanApp;
import com.liferay.bean.portlet.cdi.extension.internal.BeanFilter;
import com.liferay.bean.portlet.cdi.extension.internal.BeanPortlet;

import java.util.List;

/**
 * @author Neil Griffin
 */
public class PortletDescriptor {

	public PortletDescriptor(
		BeanApp beanApp, List<BeanFilter> beanFilters,
		List<BeanPortlet> beanPortlets) {

		_beanApp = beanApp;
		_beanFilters = beanFilters;
		_beanPortlets = beanPortlets;
	}

	public BeanApp getBeanApp() {
		return _beanApp;
	}

	public List<BeanFilter> getBeanFilters() {
		return _beanFilters;
	}

	public List<BeanPortlet> getBeanPortlets() {
		return _beanPortlets;
	}

	private final BeanApp _beanApp;
	private final List<BeanFilter> _beanFilters;
	private final List<BeanPortlet> _beanPortlets;

}