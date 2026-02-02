/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.serdes.v1_0.LinkToPagePageSpecificationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class LinkToPagePageSpecification
	extends PageSpecification implements Cloneable, Serializable {

	public static LinkToPagePageSpecification toDTO(String json) {
		return LinkToPagePageSpecificationSerDes.toDTO(json);
	}

	@Override
	public LinkToPagePageSpecification clone()
		throws CloneNotSupportedException {

		return (LinkToPagePageSpecification)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LinkToPagePageSpecification)) {
			return false;
		}

		LinkToPagePageSpecification linkToPagePageSpecification =
			(LinkToPagePageSpecification)object;

		return Objects.equals(
			toString(), linkToPagePageSpecification.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LinkToPagePageSpecificationSerDes.toJSON(this);
	}

}