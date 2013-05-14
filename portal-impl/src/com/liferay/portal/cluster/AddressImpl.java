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

package com.liferay.portal.cluster;

import org.jgroups.Address;

/**
 * @author Shuyang Zhou
 */
public class AddressImpl implements com.liferay.portal.kernel.cluster.Address {

	public AddressImpl(Address address) {
		_address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AddressImpl)) {
			return false;
		}

		AddressImpl addressImpl = (AddressImpl)obj;

		if (_address.equals(addressImpl._address)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return _address.toString();
	}

	@Override
	public Address getRealAddress() {
		return _address;
	}

	@Override
	public int hashCode() {
		return _address.hashCode();
	}

	@Override
	public String toString() {
		return _address.toString();
	}

	private static final long serialVersionUID = 7969878022424426497L;

	private Address _address;

}