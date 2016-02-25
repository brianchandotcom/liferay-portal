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

package com.liferay.portal.kernel.nio.intraband.proxy;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.nio.intraband.BaseAsyncDatagramReceiveHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

/**
 * @author Shuyang Zhou
 */
public class IntrabandProxyDatagramReceiveHandler
	extends BaseAsyncDatagramReceiveHandler {

	@Override
	protected void doReceive(
		RegistrationReference registrationReference, Datagram datagram) {

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		String skeletonId = deserializer.readString();

		IntrabandProxySkeleton intrabandProxySkeleton =
			IntrabandProxySkeletonRegistryUtil.get(skeletonId);

		if (intrabandProxySkeleton == null) {
			throw new IllegalStateException(
				"Unable to find skeleton with ID " + skeletonId);
		}

		intrabandProxySkeleton.dispatch(
			registrationReference, datagram, deserializer);
	}

}