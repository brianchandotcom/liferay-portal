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

package com.liferay.counter.service.util;

import com.liferay.portal.kernel.io.BigEndianCodec;

/**
 * @author Michael C. Han
 */
public class MultiDataCenterCounterIncrementerImpl
	implements MultiDataCenterCounterIncrementer {

	public long getMultiClusterSafeValue(long value) {
		if (_multiDataCenterBits == 0) {
			return value;
		}

		byte[] bytes = new byte[8];

		BigEndianCodec.putLong(bytes, 0, value);

		bytes[0] =
			(byte)((bytes[0] >>> _multiDataCenterBits) +
			(byte)(_multiDataCenterDeploymentId <<
				(_MAXIMUM_BYTE_SHIFTS - _multiDataCenterBits)));

		return BigEndianCodec.getLong(bytes, 0);
	}

	public void initialize(int dataCenterCount, int dataCenterDeploymentId) {
		if (dataCenterCount <= 1) {
			return;
		}

		_multiDataCenterBits = getNumberBits(dataCenterCount);

		if (_multiDataCenterBits > _MAXIMUM_BYTE_SHIFTS) {
			throw new IllegalArgumentException("Cannot shift more than 8 bits");
		}

		int numberBits = getNumberBits(dataCenterDeploymentId);

		if (numberBits > _multiDataCenterBits) {
			throw new IllegalArgumentException(
				"Invalid data center count (" + dataCenterCount +
					") or data center deployment id (" +
					dataCenterDeploymentId +
					"). Please consult the appropriate documentation.");
		}

		_multiDataCenterDeploymentId = (byte)dataCenterDeploymentId;
	}

	protected static int getNumberBits(int value) {
		if (value == 0) {
			return 0;
		}

		return 32 - Integer.numberOfLeadingZeros(value - 1);
	}

	private static final int _MAXIMUM_BYTE_SHIFTS = 7;

	private int _multiDataCenterBits;
	private byte _multiDataCenterDeploymentId;

}
