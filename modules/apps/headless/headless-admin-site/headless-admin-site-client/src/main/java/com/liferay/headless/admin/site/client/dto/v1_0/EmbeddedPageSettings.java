/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.EmbeddedPageSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class EmbeddedPageSettings
	extends PageSettings implements Cloneable, Serializable {

	public static EmbeddedPageSettings toDTO(String json) {
		return EmbeddedPageSettingsSerDes.toDTO(json);
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public void setPageURL(
		UnsafeSupplier<String, Exception> pageURLUnsafeSupplier) {

		try {
			pageURL = pageURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String pageURL;

	@Override
	public EmbeddedPageSettings clone() throws CloneNotSupportedException {
		return (EmbeddedPageSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EmbeddedPageSettings)) {
			return false;
		}

		EmbeddedPageSettings embeddedPageSettings =
			(EmbeddedPageSettings)object;

		return Objects.equals(toString(), embeddedPageSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return EmbeddedPageSettingsSerDes.toJSON(this);
	}

}