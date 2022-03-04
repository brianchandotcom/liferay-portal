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

package com.liferay.content.dashboard.web.internal.info.item.provider.util;

import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.info.item.provider.filter.OptionalPropertyInfoItemServiceFilter;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;
import java.util.Optional;

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
	public Optional<String> getVersionOptional() {
		return Optional.ofNullable(_version);
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