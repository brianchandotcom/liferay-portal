/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.LinkToPagePageSettingsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class LinkToPagePageSettings
	extends PageSettings implements Cloneable, Serializable {

	public static LinkToPagePageSettings toDTO(String json) {
		return LinkToPagePageSettingsSerDes.toDTO(json);
	}

	public String getLinkToPageExternalReferenceCode() {
		return linkToPageExternalReferenceCode;
	}

	public void setLinkToPageExternalReferenceCode(
		String linkToPageExternalReferenceCode) {

		this.linkToPageExternalReferenceCode = linkToPageExternalReferenceCode;
	}

	public void setLinkToPageExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			linkToPageExternalReferenceCodeUnsafeSupplier) {

		try {
			linkToPageExternalReferenceCode =
				linkToPageExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String linkToPageExternalReferenceCode;

	@Override
	public LinkToPagePageSettings clone() throws CloneNotSupportedException {
		return (LinkToPagePageSettings)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LinkToPagePageSettings)) {
			return false;
		}

		LinkToPagePageSettings linkToPagePageSettings =
			(LinkToPagePageSettings)object;

		return Objects.equals(toString(), linkToPagePageSettings.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LinkToPagePageSettingsSerDes.toJSON(this);
	}

}