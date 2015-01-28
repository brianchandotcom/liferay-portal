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

package com.liferay.arquillian.extension.internal.instanceproducer;

import com.liferay.arquillian.extension.internal.log.LogAssertionAppenderArquillian;
import com.liferay.arquillian.extension.internal.log.LogAssertionExecuterInArquillian;
import com.liferay.arquillian.extension.internal.log.LogAssertionHandlerArquillian;
import com.liferay.portal.test.log.LogAssertionExecuter;
import com.liferay.portal.test.util.ClearThreadLocalExecutor;
import com.liferay.portal.test.util.DeleteAfterTestRunExecutor;
import com.liferay.portal.test.util.InitTestLiferayContextExecutor;
import com.liferay.portal.test.util.UniqueStringRandomizerBumperExecutor;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;

/**
 * @author Cristina González
 */
public class ExtensionInstanceProducer {

	public void createInstanceProducer(
		@Observes ArquillianDescriptor arquillianDescriptor) {

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		_clearThreadLocalExecutorInstanceProducer.set(
			serviceLoader.onlyOne(ClearThreadLocalExecutor.class));

		_deleteAfterTestExecutorInstanceProducer.set(
			serviceLoader.onlyOne(DeleteAfterTestRunExecutor.class));

		InitTestLiferayContextExecutor initTestLiferayContextExecutor =
			serviceLoader.onlyOne(InitTestLiferayContextExecutor.class);

		_initLiferayContextInstanceProducer.set(initTestLiferayContextExecutor);

		Injector injector = _injectorInstance.get();

		injector.inject(initTestLiferayContextExecutor);

		LogAssertionExecuter logAssertionExecuter =
			new LogAssertionExecuterInArquillian();

		_logAssertionExecuterInstanceProducer.set(logAssertionExecuter);

		LogAssertionAppenderArquillian logAssertionAppenderArquillian =
			new LogAssertionAppenderArquillian();

		_logAssertionAppenderArquillianInstanceProducer.set(
			logAssertionAppenderArquillian);

		LogAssertionHandlerArquillian logAssertionHandlerArquillian =
			new LogAssertionHandlerArquillian();

		_logAssertionHandlerArquillianInstanceProducer.set(
			logAssertionHandlerArquillian);

		injector.inject(logAssertionExecuter);

		injector.inject(logAssertionAppenderArquillian);

		injector.inject(logAssertionHandlerArquillian);

		_uniqueStringRandomizerBumperExecutorInstanceProducer.set(
			serviceLoader.onlyOne(UniqueStringRandomizerBumperExecutor.class));
	}

	@ApplicationScoped
	@Inject
	private InstanceProducer<ClearThreadLocalExecutor>
		_clearThreadLocalExecutorInstanceProducer;

	@ApplicationScoped
	@Inject
	private InstanceProducer<DeleteAfterTestRunExecutor>
		_deleteAfterTestExecutorInstanceProducer;

	@ApplicationScoped
	@Inject
	private InstanceProducer<InitTestLiferayContextExecutor>
		_initLiferayContextInstanceProducer;

	@Inject
	private Instance<Injector> _injectorInstance;

	@ApplicationScoped
	@Inject
	private InstanceProducer<LogAssertionAppenderArquillian>
		_logAssertionAppenderArquillianInstanceProducer;

	@ApplicationScoped
	@Inject
	private InstanceProducer<LogAssertionExecuter>
		_logAssertionExecuterInstanceProducer;

	@ApplicationScoped
	@Inject
	private InstanceProducer<LogAssertionHandlerArquillian>
		_logAssertionHandlerArquillianInstanceProducer;

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

	@ApplicationScoped
	@Inject
	private InstanceProducer<UniqueStringRandomizerBumperExecutor>
		_uniqueStringRandomizerBumperExecutorInstanceProducer;

}