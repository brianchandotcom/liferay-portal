/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.blocking;

import com.liferay.portal.kernel.nio.intraband.ChannelContext;
import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.io.IOException;

import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
class FutureRegistrationReference implements RegistrationReference {

	public void cancelRegistration() {
		readFuture.cancel(true);
		writeFuture.cancel(true);
	}

	public void closeChannels() throws IOException {

		// Order matters, don't reverse it

		gatheringByteChannel.close();
		scatteringByteChannel.close();
	}

	public IntraBand getIntraBand() {
		return intraBand;
	}

	public boolean isValid() {
		if (!readFuture.isDone() && !writeFuture.isDone()) {
			return true;
		}

		return false;
	}

	FutureRegistrationReference(
		IntraBand intraBand, ChannelContext channelContext,
		Future<Void> readFuture, Future<Void> writeFuture,
		ScatteringByteChannel scatteringByteChannel,
		GatheringByteChannel gatheringByteChannel) {

		this.intraBand = intraBand;
		this.channelContext = channelContext;
		this.readFuture = readFuture;
		this.writeFuture = writeFuture;
		this.scatteringByteChannel = scatteringByteChannel;
		this.gatheringByteChannel = gatheringByteChannel;
	}

	final ChannelContext channelContext;
	final GatheringByteChannel gatheringByteChannel;
	final IntraBand intraBand;
	final Future<Void> readFuture;
	final ScatteringByteChannel scatteringByteChannel;
	final Future<Void> writeFuture;

}