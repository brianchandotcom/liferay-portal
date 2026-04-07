/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TemplateListStyleSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TemplateListStyle
	extends CollectionDisplayListStyle implements Cloneable, Serializable {

	public static TemplateListStyle toDTO(String json) {
		return TemplateListStyleSerDes.toDTO(json);
	}

	public String getListItemStyleClassName() {
		return listItemStyleClassName;
	}

	public void setListItemStyleClassName(String listItemStyleClassName) {
		this.listItemStyleClassName = listItemStyleClassName;
	}

	public void setListItemStyleClassName(
		UnsafeSupplier<String, Exception>
			listItemStyleClassNameUnsafeSupplier) {

		try {
			listItemStyleClassName = listItemStyleClassNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String listItemStyleClassName;

	public String getListStyleClassName() {
		return listStyleClassName;
	}

	public void setListStyleClassName(String listStyleClassName) {
		this.listStyleClassName = listStyleClassName;
	}

	public void setListStyleClassName(
		UnsafeSupplier<String, Exception> listStyleClassNameUnsafeSupplier) {

		try {
			listStyleClassName = listStyleClassNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String listStyleClassName;

	public String getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(String templateKey) {
		this.templateKey = templateKey;
	}

	public void setTemplateKey(
		UnsafeSupplier<String, Exception> templateKeyUnsafeSupplier) {

		try {
			templateKey = templateKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String templateKey;

	@Override
	public TemplateListStyle clone() throws CloneNotSupportedException {
		return (TemplateListStyle)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TemplateListStyle)) {
			return false;
		}

		TemplateListStyle templateListStyle = (TemplateListStyle)object;

		return Objects.equals(toString(), templateListStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TemplateListStyleSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:24809866