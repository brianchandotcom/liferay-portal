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
import com.liferay.arquillian.extension.transactional.internal.util.TransactionalExecutorImpl;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

/**
 * @author Cristina González
 */
public class TransactionalExecutorInstanceProducer {

	public void createTransactionalUtil(@Observes BeforeClass beforeClass) {
		_instanceProducer.set(new TransactionalExecutorImpl());
	}

	@ApplicationScoped
	@Inject
	private InstanceProducer<TransactionalExecutor> _instanceProducer;

}