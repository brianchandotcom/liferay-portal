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

package com.liferay.portal.rules.engine.drools.test;

import com.liferay.portal.kernel.util.StringBundler;

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
		StringBundler sb = new StringBundler(5);

		sb.append("{age=");
		sb.append(_age);
		sb.append(", _ageGroup=");
		sb.append(_ageGroup);
		sb.append("}");

		return sb.toString();
	}

	private int _age;
	private String _ageGroup;

}