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

package com.liferay.wsrp.web.internal.util;

import com.liferay.wsrp.model.WSRPConsumer;
import com.liferay.wsrp.util.WSRPConsumerManager;
import com.liferay.wsrp.util.WSRPConsumerManagerFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class WSRPConsumerManagerFactoryUtil {

	public static WSRPConsumerManager getWSRPConsumerManager(
			WSRPConsumer wsrpConsumer)
		throws Exception {

		return _wsrpConsumerManagerFactory.getWSRPConsumerManager(wsrpConsumer);
	}

	public static boolean testWSRPConsumerManager(WSRPConsumer wsrpConsumer) {
		return _wsrpConsumerManagerFactory.testWSRPConsumerManager(
			wsrpConsumer);
	}

	@Reference(unbind = "-")
	protected void setWSRPConsumerManagerFactory(
		WSRPConsumerManagerFactory wsrpConsumerManagerFactory) {

		_wsrpConsumerManagerFactory = wsrpConsumerManagerFactory;
	}

	private static WSRPConsumerManagerFactory _wsrpConsumerManagerFactory;

}