/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.info.item;

import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.info.item.provider.filter.OptionalPropertyInfoItemServiceFilter;

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
	public String getVersion() {
		return _version;
	}

	@Override
	public void setVersion(String version) {
		_version = version;
	}

	private final String _className;
	private final long _classPK;
	private String _version;

}