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

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public abstract class BaseBeanPortletImpl implements BeanPortlet {

	@Override
	public void addBeanMethod(BeanMethod beanMethod) {

		// TODO

	}

	@Override
	public void addLiferayConfiguration(
		Map<String, String> liferayConfiguration) {

		// TODO

	}

	@Override
	public void addLiferayConfiguration(String name, String value) {

		// TODO

	}

	@Override
	public void addPortletDependency(PortletDependency portletDependency) {

		// TODO

	}

	@Override
	public BeanApp getBeanApp() {

		// TODO

		return null;
	}

	@Override
	public List<BeanMethod> getBeanMethods(MethodType methodType) {

		// TODO

		return null;
	}

	@Override
	public Dictionary<String, Object> toDictionary(String portletId) {

		// TODO

		return null;
	}

	protected Map<String, String> getLiferayConfiguration() {

		// TODO

		return null;
	}

	protected Set<String> getLiferayPortletModes() {

		// TODO

		return null;
	}

	protected List<PortletDependency> getPortletDependencies() {
		 // TODO

		return null;
	}

	protected String getPublicRenderParameterNamespaceURI(String id) {

		// TODO

		return null;
	}

}