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

package com.liferay.arquillian.extension.persistence.internal.observer;

import com.liferay.portal.kernel.test.rule.executor.PersistenceTestInitializer;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.Before;

/**
 * @author Cristina González
 */
public class PersistenceTestObserver {

	public void afterTest(@Observes EventContext<After> eventContext)
		throws Throwable {

		PersistenceTestInitializer persistenceTestInitializer = _instance.get();

		persistenceTestInitializer.release(_modelListeners);
	}

	public void beforeTest(@Observes EventContext<Before> eventContext)
		throws Throwable {

		PersistenceTestInitializer persistenceTestInitializer = _instance.get();

		_modelListeners = persistenceTestInitializer.init();
	}

	@Inject
	private Instance<PersistenceTestInitializer> _instance;

	private Object _modelListeners;

}