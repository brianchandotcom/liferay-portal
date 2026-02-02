/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.NoneActionInteractionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class NoneActionInteraction
	extends ActionInteraction implements Cloneable, Serializable {

	public static NoneActionInteraction toDTO(String json) {
		return NoneActionInteractionSerDes.toDTO(json);
	}

	public Boolean getReload() {
		return reload;
	}

	public void setReload(Boolean reload) {
		this.reload = reload;
	}

	public void setReload(
		UnsafeSupplier<Boolean, Exception> reloadUnsafeSupplier) {

		try {
			reload = reloadUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean reload;

	@Override
	public NoneActionInteraction clone() throws CloneNotSupportedException {
		return (NoneActionInteraction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NoneActionInteraction)) {
			return false;
		}

		NoneActionInteraction noneActionInteraction =
			(NoneActionInteraction)object;

		return Objects.equals(toString(), noneActionInteraction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return NoneActionInteractionSerDes.toJSON(this);
	}

}