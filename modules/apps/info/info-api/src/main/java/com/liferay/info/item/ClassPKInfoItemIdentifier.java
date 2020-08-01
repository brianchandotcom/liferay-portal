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

package com.liferay.info.item;

import com.liferay.petra.string.StringBundler;

/**
 * @author Jorge Ferrer
 */
public class ClassPKInfoItemIdentifier extends InfoItemIdentifier {

	public ClassPKInfoItemIdentifier(long classPK) {
		_classPK = classPK;
	}

	public long getClassPK() {
		return _classPK;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{type=");
		sb.append(ClassPKInfoItemIdentifier.class.getName());
		sb.append(", classPK=");
		sb.append(_classPK);
		sb.append("}");

		return sb.toString();
	}

	private final long _classPK;

}