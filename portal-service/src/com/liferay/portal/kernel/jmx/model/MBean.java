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

package com.liferay.portal.kernel.jmx.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HashCode;
import com.liferay.portal.kernel.util.HashCodeFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBean)) {
			return false;
		}

		MBean mBean = (MBean)obj;

		if (Validator.equals(_domainName, mBean._domainName) &&
			Validator.equals(_mBeanName, mBean._mBeanName)) {

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

			_path = new ArrayList<String>(parts.length);

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
		HashCode hashCode = HashCodeFactoryUtil.getHashCode();

		hashCode.append(_domainName);
		hashCode.append(_mBeanName);

		return hashCode.toHashCode();
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