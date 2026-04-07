/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.serdes.v1_0.FormStepPageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormStepPageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static FormStepPageElementDefinition toDTO(String json) {
		return FormStepPageElementDefinitionSerDes.toDTO(json);
	}

	@Override
	public FormStepPageElementDefinition clone()
		throws CloneNotSupportedException {

		return (FormStepPageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormStepPageElementDefinition)) {
			return false;
		}

		FormStepPageElementDefinition formStepPageElementDefinition =
			(FormStepPageElementDefinition)object;

		return Objects.equals(
			toString(), formStepPageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormStepPageElementDefinitionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:2010746568