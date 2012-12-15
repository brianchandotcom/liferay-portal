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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;

import java.io.IOException;

/**
 * @author Shuyang Zhou
 */
public class MockWelder implements Welder {

	public boolean isClientWelded() {
		return _clientWelded;
	}

	public boolean isServerWelded() {
		return _serverWelded;
	}

	public RegistrationReference weldClient(IntraBand intraBand)
		throws IOException {

		_clientWelded = true;

		return new MockRegistrationReference(intraBand);
	}

	public RegistrationReference weldServer(IntraBand intraBand)
		throws IOException {

		_serverWelded = true;

		return new MockRegistrationReference(intraBand);
	}

	private boolean _clientWelded;
	private boolean _serverWelded;

}