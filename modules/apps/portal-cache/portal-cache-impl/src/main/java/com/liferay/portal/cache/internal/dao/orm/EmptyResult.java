/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Preston Crary
 */
public class EmptyResult implements Externalizable {

	public EmptyResult() {
	}

	public EmptyResult(Object[] args) {
		_args = _stripPagination(args);
	}

	public boolean matches(Object[] args) {
		args = _stripPagination(args);

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

	private Object[] _stripPagination(Object[] args) {
		if ((args.length >= 3) &&
			(args[args.length - 1] instanceof OrderByComparator) &&
			(args[args.length - 2] instanceof Integer) &&
			(args[args.length - 3] instanceof Integer)) {

			int start = (Integer)args[args.length - 3];
			int end = (Integer)args[args.length - 2];

			if (start == end) {
				if (start == QueryUtil.ALL_POS) {
					return Arrays.copyOf(args, args.length - 3);
				}

				// Account for a empty page

				args = Arrays.copyOf(args, args.length - 1);

				args[args.length - 1] = 0;
				args[args.length - 2] = 0;

				return args;
			}

			return Arrays.copyOf(args, args.length - 2);
		}

		return args;
	}

	private Object[] _args;

}