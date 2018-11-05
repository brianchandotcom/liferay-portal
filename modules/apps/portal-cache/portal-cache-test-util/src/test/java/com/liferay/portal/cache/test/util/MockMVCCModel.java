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

package com.liferay.portal.cache.test.util;

import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class MockMVCCModel implements MVCCModel, Serializable {

	public MockMVCCModel(long version) {
		_version = version;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MockMVCCModel)) {
			return false;
		}

		MockMVCCModel mockMVCCModel = (MockMVCCModel)object;

		if (_version == mockMVCCModel._version) {
			return true;
		}

		return false;
	}

	@Override
	public long getMvccVersion() {
		return _version;
	}

	@Override
	public int hashCode() {
		return (int)_version;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_version = mvccVersion;
	}

	private long _version;

}