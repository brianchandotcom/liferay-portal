/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.info.item.provider.filter.OptionalPropertyInfoItemServiceFilter;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public abstract class BaseInfoItemIdentifier implements InfoItemIdentifier {

	@Override
	public String getVersion() {
		return _version;
	}

	@Override
	public void setVersion(String version) {
		_version = version;
	}

	protected static InfoItemServiceFilter getInfoItemServiceFilter(
		Class<? extends InfoItemIdentifier> infoItemServiceFilterClass) {

		return new OptionalPropertyInfoItemServiceFilter(
			"info.item.identifier", infoItemServiceFilterClass.getName());
	}

	private String _version;

}