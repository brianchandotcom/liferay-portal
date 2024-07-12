/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.dto.v1_0_0;

import com.example.sample.client.serdes.v1_0_0.ChildSchema1SerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class ChildSchema1 implements Cloneable, Serializable {

	public static ChildSchema1 toDTO(String json) {
		return ChildSchema1SerDes.toDTO(json);
	}

	@Override
	public ChildSchema1 clone() throws CloneNotSupportedException {
		return (ChildSchema1)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChildSchema1)) {
			return false;
		}

		ChildSchema1 childSchema1 = (ChildSchema1)object;

		return Objects.equals(toString(), childSchema1.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ChildSchema1SerDes.toJSON(this);
	}

}