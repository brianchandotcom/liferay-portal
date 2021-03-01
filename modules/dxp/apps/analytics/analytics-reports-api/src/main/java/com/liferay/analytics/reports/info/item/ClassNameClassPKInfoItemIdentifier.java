/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.info.item;

import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.info.item.provider.filter.OptionalPropertyInfoItemServiceFilter;

import java.util.Optional;

/**
 * @author Cristina González
 */
public class ClassNameClassPKInfoItemIdentifier implements InfoItemIdentifier {

	public ClassNameClassPKInfoItemIdentifier(String className, long classPK) {
		_className = className;
		_classPK = classPK;
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
	public void setVersion(String version) {
		_version = version;
	}

	private final String _className;
	private final long _classPK;
	private String _version;

}