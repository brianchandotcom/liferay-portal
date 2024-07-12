/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.dto.v1_0_0;

import com.example.sample.client.function.UnsafeSupplier;
import com.example.sample.client.serdes.v1_0_0.ChildSchema2SerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class ChildSchema2 implements Cloneable, Serializable {

	public static ChildSchema2 toDTO(String json) {
		return ChildSchema2SerDes.toDTO(json);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText(UnsafeSupplier<String, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String text;

	@Override
	public ChildSchema2 clone() throws CloneNotSupportedException {
		return (ChildSchema2)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChildSchema2)) {
			return false;
		}

		ChildSchema2 childSchema2 = (ChildSchema2)object;

		return Objects.equals(toString(), childSchema2.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ChildSchema2SerDes.toJSON(this);
	}

}