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

import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class InfoItemReference {

	public InfoItemReference(
		String className, InfoItemIdentifier infoItemIdentifier) {

		_className = className;
		_infoItemIdentifier = infoItemIdentifier;
	}

	public InfoItemReference(String className, long classPK) {
		this(className, new ClassPKInfoItemIdentifier(classPK));
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InfoItemReference)) {
			return false;
		}

		InfoItemReference infoItemReference = (InfoItemReference)object;

		if (Objects.equals(_className, infoItemReference._className) &&
			Objects.equals(
				_infoItemIdentifier, infoItemReference._infoItemIdentifier)) {

			return true;
		}

		return false;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		if (_infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)_infoItemIdentifier;

			return classPKInfoItemIdentifier.getClassPK();
		}

		return 0;
	}

	public InfoItemIdentifier getInfoItemIdentifier() {
		return _infoItemIdentifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_className, _infoItemIdentifier);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");
		sb.append(_className);
		sb.append(", _infoItemIdentifier=");
		sb.append(_infoItemIdentifier);
		sb.append("}");

		return sb.toString();
	}

	private final String _className;
	private final InfoItemIdentifier _infoItemIdentifier;

}