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

package com.liferay.portal.cache.internal.dao.orm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class EmptyResult implements Externalizable {

	public EmptyResult() {
	}

	public EmptyResult(Object[] args) {
		_args = args;
	}

	public boolean matches(Object[] args) {
		if (args.length != _args.length) {
			return false;
		}

		for (int i = 0; i < _args.length; i++) {
			if (!Objects.equals(args[i], _args[i])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_args = (Object[])objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeObject(_args);
	}

	private Object[] _args;

}