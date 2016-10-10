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

package com.liferay.counter.service.persistence.impl;

import com.liferay.portal.kernel.io.BigEndianCodec;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class MultiDataCenterCounterFinderImpl extends CounterFinderImpl {

	public MultiDataCenterCounterFinderImpl(
		int dataCenterCount, int dataCenterDeploymentId) {

		_multiDataCenterBits = getBits(dataCenterCount - 1);

		if (_multiDataCenterBits > _BYTE_SHIFTS_MAX) {
			throw new IllegalArgumentException(
				"Unable to shift more than 8 bits");
		}

		if (getBits(dataCenterDeploymentId) > _multiDataCenterBits) {
			throw new IllegalArgumentException(
				"Invalid data center count " + dataCenterCount +
					" or data center deployment ID " + dataCenterDeploymentId);
		}

		int bits = _BYTE_SHIFTS_MAX - _multiDataCenterBits;

		_mostSignificantByte = (byte)(dataCenterDeploymentId << bits);
	}

	@Override
	public long increment(String name, int size) {
		return getMultiClusterSafeValue(super.increment(name, size));
	}

	protected static int getBits(int value) {
		if (value == 0) {
			return 0;
		}

		return 32 - Integer.numberOfLeadingZeros(value);
	}

	protected long getMultiClusterSafeValue(long value) {
		byte[] bytes = new byte[8];

		BigEndianCodec.putLong(bytes, 0, value);

		int modifiedLeftMostByte = bytes[0] >>> _multiDataCenterBits;

		bytes[0] = (byte)(modifiedLeftMostByte + _mostSignificantByte);

		return BigEndianCodec.getLong(bytes, 0);
	}

	private static final int _BYTE_SHIFTS_MAX = 7;

	private final byte _mostSignificantByte;
	private final int _multiDataCenterBits;

}