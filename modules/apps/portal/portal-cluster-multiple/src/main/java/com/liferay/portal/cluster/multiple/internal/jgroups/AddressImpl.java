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

package com.liferay.portal.cluster.multiple.internal.jgroups;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.jgroups.Address;
import org.jgroups.util.Util;

/**
 * @author Shuyang Zhou
 */
public class AddressImpl
	implements com.liferay.portal.kernel.cluster.Address, Externalizable {

	public AddressImpl() {
	}

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

		return false;
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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_address = Util.readAddress(objectInput);
	}

	@Override
	public String toString() {
		return _address.toString();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		Util.writeAddress(_address, objectOutput);
	}

	private static final long serialVersionUID = 7969878022424426497L;

	private Address _address;

}