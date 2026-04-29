/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.SystemPropertiesSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class SystemProperties implements Cloneable, Serializable {

	public static SystemProperties toDTO(String json) {
		return SystemPropertiesSerDes.toDTO(json);
	}

	public CollaboratorBrief getCollaboratorBrief() {
		return collaboratorBrief;
	}

	public void setCollaboratorBrief(CollaboratorBrief collaboratorBrief) {
		this.collaboratorBrief = collaboratorBrief;
	}

	public void setCollaboratorBrief(
		UnsafeSupplier<CollaboratorBrief, Exception>
			collaboratorBriefUnsafeSupplier) {

		try {
			collaboratorBrief = collaboratorBriefUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollaboratorBrief collaboratorBrief;

	@Override
	public SystemProperties clone() throws CloneNotSupportedException {
		return (SystemProperties)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SystemProperties)) {
			return false;
		}

		SystemProperties systemProperties = (SystemProperties)object;

		return Objects.equals(toString(), systemProperties.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SystemPropertiesSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:658045654