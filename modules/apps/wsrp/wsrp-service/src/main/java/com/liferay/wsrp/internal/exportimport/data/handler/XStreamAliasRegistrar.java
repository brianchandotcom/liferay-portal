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

package com.liferay.wsrp.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.xstream.XStreamAliasRegistryUtil;
import com.liferay.wsrp.model.impl.WSRPConsumerImpl;
import com.liferay.wsrp.model.impl.WSRPConsumerPortletImpl;
import com.liferay.wsrp.model.impl.WSRPProducerImpl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true)
public class XStreamAliasRegistrar {

	@Activate
	protected void activate() {
		XStreamAliasRegistryUtil.register(
			WSRPConsumerImpl.class, "WSRPConsumer");
		XStreamAliasRegistryUtil.register(
			WSRPConsumerPortletImpl.class, "WSRPConsumerPortlet");
		XStreamAliasRegistryUtil.register(
			WSRPProducerImpl.class, "WSRPProducer");
	}

}