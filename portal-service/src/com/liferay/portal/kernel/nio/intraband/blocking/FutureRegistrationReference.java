/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
class FutureRegistrationReference implements RegistrationReference {

	@Override
	public void cancelRegistration() {
		readFuture.cancel(true);
		writeFuture.cancel(true);
	}

	@Override
	public Intraband getIntraband() {
		return intraband;
	}

	@Override
	public boolean isValid() {
		if (!readFuture.isDone() && !writeFuture.isDone()) {
			return true;
		}

		return false;
	}

	protected FutureRegistrationReference(
		Intraband intraband, ChannelContext channelContext,
		Future<Void> readFuture, Future<Void> writeFuture) {

		this.intraband = intraband;
		this.channelContext = channelContext;
		this.readFuture = readFuture;
		this.writeFuture = writeFuture;
	}

	protected final ChannelContext channelContext;
	protected final Intraband intraband;
	protected final Future<Void> readFuture;
	protected final Future<Void> writeFuture;

}