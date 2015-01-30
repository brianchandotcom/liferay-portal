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

package com.liferay.arquillian.extension;

import com.liferay.arquillian.extension.internal.event.LiferayEventTestRunnerAdaptor;
import com.liferay.arquillian.extension.internal.instanceproducer.ExtensionInstanceProducer;
import com.liferay.arquillian.extension.internal.log.LogAssertionExecuterInArquillian;
import com.liferay.arquillian.extension.internal.log.LogAssertionObserver;
import com.liferay.portal.test.log.LogAssertionExecuter;
import com.liferay.portal.test.util.ClearThreadLocalExecutor;
import com.liferay.portal.test.util.ClearThreadLocalExecutorImpl;
import com.liferay.portal.test.util.DeleteAfterTestRunExecutor;
import com.liferay.portal.test.util.DeleteAfterTestRunExecutorImpl;
import com.liferay.portal.test.util.InitTestLiferayContextExecutor;
import com.liferay.portal.test.util.InitTestLiferayContextExecutorImpl;
import com.liferay.portal.test.util.UniqueStringRandomizerBumperExecutor;
import com.liferay.portal.test.util.UniqueStringRandomizerBumperExecutorImpl;

import org.jboss.arquillian.core.spi.LoadableExtension;

/**
 * @author Cristina González Castellano
 */
public class LiferayTestScenarioExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder extensionBuilder) {
		extensionBuilder.observer(ExtensionInstanceProducer.class);
		extensionBuilder.observer(LiferayEventTestRunnerAdaptor.class);
		extensionBuilder.observer(LogAssertionObserver.class);

		extensionBuilder.service(
			ClearThreadLocalExecutor.class, ClearThreadLocalExecutorImpl.class);
		extensionBuilder.service(
			DeleteAfterTestRunExecutor.class,
			DeleteAfterTestRunExecutorImpl.class);
		extensionBuilder.service(
			InitTestLiferayContextExecutor.class,
			InitTestLiferayContextExecutorImpl.class);
		extensionBuilder.service(
			LogAssertionExecuter.class, LogAssertionExecuterInArquillian.class);
		extensionBuilder.service(
			UniqueStringRandomizerBumperExecutor.class,
			UniqueStringRandomizerBumperExecutorImpl.class);
	}

}