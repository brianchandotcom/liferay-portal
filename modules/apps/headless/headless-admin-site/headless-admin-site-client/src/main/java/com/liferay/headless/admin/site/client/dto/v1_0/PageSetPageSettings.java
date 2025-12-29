/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.serdes.v1_0.PageSetPageSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class PageSetPageSettings
	extends PageSettings implements Cloneable, Serializable {

	public static PageSetPageSettings toDTO(String json) {
		return PageSetPageSettingsSerDes.toDTO(json);
	}

	@Override
	public PageSetPageSettings clone() throws CloneNotSupportedException {
		return (PageSetPageSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageSetPageSettings)) {
			return false;
		}

		PageSetPageSettings pageSetPageSettings = (PageSetPageSettings)object;

		return Objects.equals(toString(), pageSetPageSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageSetPageSettingsSerDes.toJSON(this);
	}

}