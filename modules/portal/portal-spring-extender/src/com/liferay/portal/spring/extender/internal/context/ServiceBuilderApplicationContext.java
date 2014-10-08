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

import org.eclipse.gemini.blueprint.context.support.OsgiBundleXmlApplicationContext;
import org.eclipse.gemini.blueprint.io.OsgiBundleResourcePatternResolver;

import org.osgi.framework.Bundle;

import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author Miguel Pastor
 */
public class ServiceBuilderApplicationContext
	extends OsgiBundleXmlApplicationContext {

	public ServiceBuilderApplicationContext(
		Bundle bundle, String[] configLocations) {

		super(configLocations);

		_bundle = bundle;
	}

	@Override
	protected ResourcePatternResolver createResourcePatternResolver() {
		return new OsgiBundleResourcePatternResolver(_bundle);
	}

	private final Bundle _bundle;

}