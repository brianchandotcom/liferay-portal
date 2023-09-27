/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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