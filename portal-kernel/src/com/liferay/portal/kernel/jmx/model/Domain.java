/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.jmx.model;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class Domain implements Serializable {

	public Domain(String domainName) {
		_domainName = domainName;

		_loaded = false;
		_mBeans = null;
	}

	public Domain(String domainName, List<MBean> mBeans) {
		_domainName = domainName;
		_mBeans = mBeans;

		_loaded = true;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Domain)) {
			return false;
		}

		Domain domain = (Domain)object;

		if (Objects.equals(_domainName, domain._domainName)) {
			return true;
		}

		return false;
	}

	public String getDomainName() {
		return _domainName;
	}

	public List<MBean> getMBeans() {
		return _mBeans;
	}

	@Override
	public int hashCode() {
		return _domainName.hashCode();
	}

	public boolean isLoaded() {
		return _loaded;
	}

	private final String _domainName;
	private final boolean _loaded;
	private final List<MBean> _mBeans;

}