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

package com.liferay.headless.admin.content.client.dto.v1_0;

import com.liferay.headless.admin.content.client.function.UnsafeSupplier;
import com.liferay.headless.admin.content.client.serdes.v1_0.VersionInformationSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class VersionInformation implements Cloneable {

	public static VersionInformation toDTO(String json) {
		return VersionInformationSerDes.toDTO(json);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status status;

	public Double getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Double versionNumber) {
		this.versionNumber = versionNumber;
	}

	public void setVersionNumber(
		UnsafeSupplier<Double, Exception> versionNumberUnsafeSupplier) {

		try {
			versionNumber = versionNumberUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double versionNumber;

	@Override
	public VersionInformation clone() throws CloneNotSupportedException {
		return (VersionInformation)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VersionInformation)) {
			return false;
		}

		VersionInformation versionInformation = (VersionInformation)object;

		return Objects.equals(toString(), versionInformation.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return VersionInformationSerDes.toJSON(this);
	}

}