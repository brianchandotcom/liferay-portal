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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.portal.spring.bean.LiferayBeanFactory;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContext extends ClassPathXmlApplicationContext {

	public ModuleApplicationContext(
		Bundle bundle, ClassLoader classLoader, String[] configLocations) {

		super(configLocations, false, null);

		this.bundle = bundle;

		setClassLoader(classLoader);
	}

	public BundleContext getBundleContext() {
		return bundle.getBundleContext();
	}

	@Override
	public Resource[] getResources(String locationPattern) {
		Enumeration<URL> enumeration = bundle.findEntries(
			locationPattern, "*.xml", false);

		List<Resource> resources = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			resources.add(new UrlResource(enumeration.nextElement()));
		}

		return resources.toArray(new Resource[resources.size()]);
	}

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		return new LiferayBeanFactory(getInternalParentBeanFactory());
	}

	protected final Bundle bundle;

}