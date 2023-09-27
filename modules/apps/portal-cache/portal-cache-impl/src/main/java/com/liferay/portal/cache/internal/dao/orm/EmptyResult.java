/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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