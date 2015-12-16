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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mika Koivisto
 */
public class ProtectedObjectInputStream extends ObjectInputStream {

	public ProtectedObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	protected Class<?> doResolveClass(ObjectStreamClass osc)
		throws ClassNotFoundException, IOException {

		return super.resolveClass(osc);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass osc)
		throws ClassNotFoundException, IOException {

		if (_restrictedClassNames.contains(osc.getName())) {
			throw new InvalidClassException(
				"Trying to load restricted class: " + osc.getName());
		}

		return doResolveClass(osc);
	}

	private static final Set<String> _restrictedClassNames;

	static {
		String[] restrictedClassNames = StringUtil.split(
			System.getProperty(
				ProtectedObjectInputStream.class.getName() +
					".restricted.class.names"));

		_restrictedClassNames = new HashSet<>(
			Arrays.asList(restrictedClassNames));
	}

}