/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.dto.v1_0;

import com.liferay.portal.tools.rest.builder.test.client.function.UnsafeSupplier;
import com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0.ExternalTestEntity3SerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class ExternalTestEntity3 implements Cloneable, Serializable {

	public static ExternalTestEntity3 toDTO(String json) {
		return ExternalTestEntity3SerDes.toDTO(json);
	}

	public String getProperty3() {
		return property3;
	}

	public void setProperty3(String property3) {
		this.property3 = property3;
	}

	public void setProperty3(
		UnsafeSupplier<String, Exception> property3UnsafeSupplier) {

		try {
			property3 = property3UnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String property3;

	@Override
	public ExternalTestEntity3 clone() throws CloneNotSupportedException {
		return (ExternalTestEntity3)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ExternalTestEntity3)) {
			return false;
		}

		ExternalTestEntity3 externalTestEntity3 = (ExternalTestEntity3)object;

		return Objects.equals(toString(), externalTestEntity3.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ExternalTestEntity3SerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:926761284