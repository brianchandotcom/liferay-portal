/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0.ChildTestObject1SerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ChildTestObject1 implements Cloneable, Serializable {

	public static ChildTestObject1 toDTO(String json) {
		return ChildTestObject1SerDes.toDTO(json);
	}

	@Override
	public ChildTestObject1 clone() throws CloneNotSupportedException {
		return (ChildTestObject1)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChildTestObject1)) {
			return false;
		}

		ChildTestObject1 childTestObject1 = (ChildTestObject1)object;

		return Objects.equals(toString(), childTestObject1.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ChildTestObject1SerDes.toJSON(this);
	}

}