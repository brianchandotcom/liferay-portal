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

package com.liferay.arquillian.extension.persistence;

import com.liferay.arquillian.extension.internal.descriptor.SpringDescriptor;
import com.liferay.arquillian.extension.internal.descriptor.SpringDescriptorImpl;
import com.liferay.arquillian.extension.persistence.internal.descriptor.PersistenceTestSpringDescriptorImpl;
import com.liferay.arquillian.extension.persistence.internal.instanceproducer.ExtensionInstanceProducer;
import com.liferay.arquillian.extension.persistence.internal.observer.PersistenceTestObserver;
import com.liferay.portal.test.util.InitPersistenceTest;
import com.liferay.portal.test.util.InitPersistenceTestImpl;

import org.jboss.arquillian.core.spi.LoadableExtension;

/**
 * @author Cristina González
 */
public class PersistenceTestScenarioExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {
		builder.observer(ExtensionInstanceProducer.class);
		builder.observer(PersistenceTestObserver.class);

		builder.override(
			SpringDescriptor.class, SpringDescriptorImpl.class,
			PersistenceTestSpringDescriptorImpl.class);
		builder.service(
			InitPersistenceTest.class, InitPersistenceTestImpl.class);
	}

}