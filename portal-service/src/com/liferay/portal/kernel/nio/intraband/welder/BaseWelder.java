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

package com.liferay.portal.kernel.nio.intraband.welder;

import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseWelder implements Welder {

	public BaseWelder() {

		// This assignment has to stay in constructor. Because we need to tell
		// the difference between a constructor created welder and
		// deserialization created welder. Only the constructor created welder
		// needs a force set to server. The deserialization created welder
		// remains what the original creator set it to be.

		server = true;
	}

	public synchronized void destroy() throws IOException {
		if (state != State.WELDED) {
			throw new IllegalStateException(
				"Can not destroy a welder at state " + state);
		}

		registrationReference.cancelRegistration();

		doDestroy();

		state = State.DESTROYED;
	}

	public synchronized RegistrationReference weld(IntraBand intraBand)
		throws IOException {

		if (state != State.CREATED) {
			throw new IllegalStateException(
				"Can not weld a welder at state " + state);
		}

		if (server) {
			registrationReference = weldServer(intraBand);
		}
		else {
			registrationReference = weldClient(intraBand);
		}

		state = State.WELDED;

		return registrationReference;
	}

	protected abstract void doDestroy() throws IOException;

	protected abstract RegistrationReference weldClient(IntraBand intraBand)
		throws IOException;

	protected abstract RegistrationReference weldServer(IntraBand intraBand)
		throws IOException;

	protected transient RegistrationReference registrationReference;
	protected final transient boolean server;
	protected transient State state = State.CREATED;

	protected static enum State {

		CREATED, DESTROYED, WELDED

	}

	private void readObject(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		objectInputStream.defaultReadObject();

		state = State.CREATED;
	}

}