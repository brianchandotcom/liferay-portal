/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public InfoItemIdentifier getInfoItemIdentifier() {
		return _infoItemIdentifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_className, _infoItemIdentifier);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{className=", _className, ", _infoItemIdentifier=",
			_infoItemIdentifier, "}");
	}

	private final String _className;
	private final InfoItemIdentifier _infoItemIdentifier;

}