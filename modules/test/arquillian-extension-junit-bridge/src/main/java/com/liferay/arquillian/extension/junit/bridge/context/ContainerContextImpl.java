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

package com.liferay.arquillian.extension.junit.bridge.context;

import java.lang.annotation.Annotation;

import org.jboss.arquillian.container.spi.context.ContainerContext;
import org.jboss.arquillian.container.spi.context.annotation.ContainerScoped;
import org.jboss.arquillian.core.spi.HashObjectStore;
import org.jboss.arquillian.core.spi.context.AbstractContext;
import org.jboss.arquillian.core.spi.context.ObjectStore;

/**
 * @author Matthew Tambara
 */
public class ContainerContextImpl
	extends AbstractContext<String> implements ContainerContext {

	@Override
	public Class<? extends Annotation> getScope() {
		return ContainerScoped.class;
	}

	@Override
	protected ObjectStore createNewObjectStore() {
		return new HashObjectStore();
	}

}