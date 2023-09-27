/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.rules.engine.drools.test;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class UserProfile {

	public int getAge() {
		return _age;
	}

	public String getAgeGroup() {
		return _ageGroup;
	}

	public void setAge(int age) {
		_age = age;
	}

	public void setAgeGroup(String ageGroup) {
		_ageGroup = ageGroup;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{age=", _age, ", _ageGroup=", _ageGroup, "}");
	}

	private int _age;
	private String _ageGroup;

}