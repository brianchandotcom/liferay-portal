/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.ProcessProgressSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class ProcessProgress implements Cloneable, Serializable {

	public static ProcessProgress toDTO(String json) {
		return ProcessProgressSerDes.toDTO(json);
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public void setPercentage(
		UnsafeSupplier<Integer, Exception> percentageUnsafeSupplier) {

		try {
			percentage = percentageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer percentage;

	@Override
	public ProcessProgress clone() throws CloneNotSupportedException {
		return (ProcessProgress)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProcessProgress)) {
			return false;
		}

		ProcessProgress processProgress = (ProcessProgress)object;

		return Objects.equals(toString(), processProgress.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ProcessProgressSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:199481261