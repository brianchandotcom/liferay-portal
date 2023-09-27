/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.jmx.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class MBean implements Serializable {

	public MBean(ObjectName objectName) {
		_objectName = objectName;

		_domainName = objectName.getDomain();
		_mBeanName = objectName.getKeyPropertyListString();
		_loaded = false;
		_mBeanInfo = null;
	}

	public MBean(ObjectName objectName, MBeanInfo mBeanInfo) {
		_objectName = objectName;
		_mBeanInfo = mBeanInfo;

		_domainName = objectName.getDomain();
		_mBeanName = objectName.getKeyPropertyListString();
		_loaded = true;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBean)) {
			return false;
		}

		MBean mBean = (MBean)object;

		if (Objects.equals(_domainName, mBean._domainName) &&
			Objects.equals(_mBeanName, mBean._mBeanName)) {

			return true;
		}

		return false;
	}

	public String getDomainName() {
		return _domainName;
	}

	public MBeanInfo getMBeanInfo() {
		return _mBeanInfo;
	}

	public String getMBeanName() {
		return _mBeanName;
	}

	public ObjectName getObjectName() {
		return _objectName;
	}

	public List<String> getPath() {
		if (_path == null) {
			String[] parts = StringUtil.split(_mBeanName);

			_path = new ArrayList<>(parts.length);

			for (String part : parts) {
				String[] kvp = StringUtil.split(part, CharPool.EQUAL);

				if (kvp.length != 2) {
					_log.error("Invalid MBean name syntax " + _mBeanName);
				}
				else {
					_path.add(kvp[1]);
				}
			}
		}

		return _path;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _domainName);

		return HashUtil.hash(hashCode, _mBeanName);
	}

	public boolean isLoaded() {
		return _loaded;
	}

	private static final Log _log = LogFactoryUtil.getLog(MBean.class);

	private final String _domainName;
	private final boolean _loaded;
	private final MBeanInfo _mBeanInfo;
	private final String _mBeanName;
	private final ObjectName _objectName;
	private List<String> _path;

}