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

package com.liferay.arquillian.extension.deleteafterrun.internal.container;

import com.liferay.arquillian.extension.deleteafterrun.internal.instanceproducer.ExtensionInstanceProducer;
import com.liferay.arquillian.extension.deleteafterrun.internal.observer.DeleteAfterTestRunObserver;
import com.liferay.portal.kernel.test.rule.executor.DeleteAfterTestRunExecutor;
import com.liferay.portal.kernel.test.rule.executor.DeleteAfterTestRunExecutorImpl;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;

/**
 * @author Cristina González Castellano
 */
public class DeleteAfterTestRunRemoteExtension
	implements RemoteLoadableExtension {

	@Override
	public void register(ExtensionBuilder extensionBuilder) {
		extensionBuilder.observer(DeleteAfterTestRunObserver.class);
		extensionBuilder.observer(ExtensionInstanceProducer.class);

		extensionBuilder.service(
			DeleteAfterTestRunExecutor.class,
			DeleteAfterTestRunExecutorImpl.class);
	}

}