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

package com.liferay.portal.kernel.resiliency.spi;

import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.Welder;

/**
 * @author Shuyang Zhou
 */
public class MockWelder implements Welder {

	@Override
	public void destroy() {
	}

	public boolean isClientWelded() {
		return _clientWelded;
	}

	public boolean isServerWelded() {
		return _SERVER_WELDED;
	}

	@Override
	public RegistrationReference weld(Intraband intraband) {
		_clientWelded = true;

		return new MockRegistrationReference(intraband);
	}

	private static final boolean _SERVER_WELDED = false;

	private boolean _clientWelded;

}