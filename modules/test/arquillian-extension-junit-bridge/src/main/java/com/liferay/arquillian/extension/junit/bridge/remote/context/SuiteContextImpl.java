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

package com.liferay.arquillian.extension.junit.bridge.remote.context;

import java.lang.annotation.Annotation;

import org.jboss.arquillian.core.spi.HashObjectStore;
import org.jboss.arquillian.core.spi.context.AbstractContext;
import org.jboss.arquillian.core.spi.context.ObjectStore;
import org.jboss.arquillian.test.spi.annotation.SuiteScoped;
import org.jboss.arquillian.test.spi.context.SuiteContext;

/**
 * @author Matthew Tambara
 */
public class SuiteContextImpl
	extends AbstractContext<String> implements SuiteContext {

	@Override
	public void activate() {
		super.activate(_SUITE_CONTEXT_ID);
	}

	@Override
	public void destroy() {
		super.destroy(_SUITE_CONTEXT_ID);
	}

	@Override
	public Class<? extends Annotation> getScope() {
		return SuiteScoped.class;
	}

	@Override
	protected ObjectStore createNewObjectStore() {
		return new HashObjectStore();
	}

	private static final String _SUITE_CONTEXT_ID = "suite";

}