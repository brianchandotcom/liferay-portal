/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.serdes.v1_0.LinkToURLPageSpecificationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class LinkToURLPageSpecification
	extends PageSpecification implements Cloneable, Serializable {

	public static LinkToURLPageSpecification toDTO(String json) {
		return LinkToURLPageSpecificationSerDes.toDTO(json);
	}

	@Override
	public LinkToURLPageSpecification clone()
		throws CloneNotSupportedException {

		return (LinkToURLPageSpecification)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LinkToURLPageSpecification)) {
			return false;
		}

		LinkToURLPageSpecification linkToURLPageSpecification =
			(LinkToURLPageSpecification)object;

		return Objects.equals(
			toString(), linkToURLPageSpecification.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LinkToURLPageSpecificationSerDes.toJSON(this);
	}

}