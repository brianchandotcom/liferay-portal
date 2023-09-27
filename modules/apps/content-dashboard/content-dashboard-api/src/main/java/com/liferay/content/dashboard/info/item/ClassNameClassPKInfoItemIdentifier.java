/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.info.item;

import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.info.item.provider.filter.OptionalPropertyInfoItemServiceFilter;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Cristina González
 */
public class ClassNameClassPKInfoItemIdentifier implements InfoItemIdentifier {

	public ClassNameClassPKInfoItemIdentifier(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ClassNameClassPKInfoItemIdentifier)) {
			return false;
		}

		ClassNameClassPKInfoItemIdentifier classNameClassPKInfoItemIdentifier =
			(ClassNameClassPKInfoItemIdentifier)object;

		if (Objects.equals(
				_className, classNameClassPKInfoItemIdentifier._className) &&
			Objects.equals(
				_classPK, classNameClassPKInfoItemIdentifier._classPK)) {

			return true;
		}

		return false;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	@Override
	public InfoItemServiceFilter getInfoItemServiceFilter() {
		return new OptionalPropertyInfoItemServiceFilter(
			"info.item.identifier",
			ClassNameClassPKInfoItemIdentifier.class.getName());
	}

	@Override
	public String getVersion() {
		return _version;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _className);

		return HashUtil.hash(hashCode, _classPK);
	}

	@Override
	public void setVersion(String version) {
		_version = version;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{className=", _className, ", classPK=", _classPK, "}");
	}

	private final String _className;
	private final long _classPK;
	private String _version;

}