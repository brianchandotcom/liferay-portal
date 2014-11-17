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

package com.liferay.arquillian.extension.transactional.internal.observer;

import com.liferay.arquillian.extension.transactional.internal.util.TransactionalExecutor;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.Test;

/**
 * @author Cristina González
 */
public class TransactionalObserver {

	public void afterTest(@Observes EventContext<After> eventContext)
		throws Throwable {

		TransactionalExecutor transactionalExecutor = _instance.get();

		transactionalExecutor.execute(eventContext);
	}

	public void beforeTest(@Observes EventContext<Before> eventContext)
		throws Throwable {

		TransactionalExecutor transactionalExecutor = _instance.get();

		transactionalExecutor.execute(eventContext);
	}

	public void test(@Observes EventContext<Test> eventContext)
		throws Throwable {

		TransactionalExecutor transactionalExecutor = _instance.get();

		transactionalExecutor.execute(eventContext);
	}

	@Inject
	private Instance<TransactionalExecutor> _instance;

}