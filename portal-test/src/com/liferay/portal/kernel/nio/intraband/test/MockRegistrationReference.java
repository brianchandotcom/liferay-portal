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

package com.liferay.portal.kernel.nio.intraband.test;

import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;

import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * @author Shuyang Zhou
 */
public class MockRegistrationReference implements RegistrationReference {

	public MockRegistrationReference(Intraband intraband) {
		this(intraband, null, null);
	}

	public MockRegistrationReference(
		ScatteringByteChannel scatteringByteChannel,
		GatheringByteChannel gatheringByteChannel) {

		this(null, scatteringByteChannel, gatheringByteChannel);
	}

	@Override
	public void cancelRegistration() {
		_cancelled = true;
	}

	public GatheringByteChannel getGatheringByteChannel() {
		return _gatheringByteChannel;
	}

	@Override
	public Intraband getIntraband() {
		return _intraband;
	}

	public ScatteringByteChannel getScatteringByteChannel() {
		return _scatteringByteChannel;
	}

	@Override
	public boolean isValid() {
		return !_cancelled;
	}

	private MockRegistrationReference(
		Intraband intraband, ScatteringByteChannel scatteringByteChannel,
		GatheringByteChannel gatherByteChannel) {

		_intraband = intraband;
		_scatteringByteChannel = scatteringByteChannel;
		_gatheringByteChannel = gatherByteChannel;
	}

	private boolean _cancelled;
	private final GatheringByteChannel _gatheringByteChannel;
	private final Intraband _intraband;
	private final ScatteringByteChannel _scatteringByteChannel;

}