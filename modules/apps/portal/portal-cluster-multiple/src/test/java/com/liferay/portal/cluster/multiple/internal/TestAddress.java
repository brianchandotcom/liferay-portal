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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.portal.kernel.cluster.Address;

/**
 * @author Tina Tian
 */
public class TestAddress implements Address, Comparable<TestAddress> {

	public TestAddress(int address) {
		_address = address;
	}

	@Override
	public int compareTo(TestAddress testAddress) {
		return Integer.compare(_address, testAddress._address);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TestAddress)) {
			return false;
		}

		TestAddress testAddress = (TestAddress)obj;

		if (_address == testAddress._address) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return String.valueOf(_address);
	}

	@Override
	public Object getRealAddress() {
		return _address;
	}

	@Override
	public int hashCode() {
		return _address;
	}

	private final int _address;

}