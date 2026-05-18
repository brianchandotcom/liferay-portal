/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FormRelationshipConfigSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FormRelationshipConfig implements Cloneable, Serializable {

	public static FormRelationshipConfig toDTO(String json) {
		return FormRelationshipConfigSerDes.toDTO(json);
	}

	public FragmentInlineValue getButtonLabelFragmentInlineValue() {
		return buttonLabelFragmentInlineValue;
	}

	public void setButtonLabelFragmentInlineValue(
		FragmentInlineValue buttonLabelFragmentInlineValue) {

		this.buttonLabelFragmentInlineValue = buttonLabelFragmentInlineValue;
	}

	public void setButtonLabelFragmentInlineValue(
		UnsafeSupplier<FragmentInlineValue, Exception>
			buttonLabelFragmentInlineValueUnsafeSupplier) {

		try {
			buttonLabelFragmentInlineValue =
				buttonLabelFragmentInlineValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue buttonLabelFragmentInlineValue;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentType(
		UnsafeSupplier<String, Exception> contentTypeUnsafeSupplier) {

		try {
			contentType = contentTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contentType;

	@Override
	public FormRelationshipConfig clone() throws CloneNotSupportedException {
		return (FormRelationshipConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FormRelationshipConfig)) {
			return false;
		}

		FormRelationshipConfig formRelationshipConfig =
			(FormRelationshipConfig)object;

		return Objects.equals(toString(), formRelationshipConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FormRelationshipConfigSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1881854694