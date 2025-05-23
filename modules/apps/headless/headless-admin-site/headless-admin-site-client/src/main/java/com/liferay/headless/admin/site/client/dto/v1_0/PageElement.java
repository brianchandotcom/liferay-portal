/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.PageElementSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class PageElement implements Cloneable, Serializable {

	public static PageElement toDTO(String json) {
		return PageElementSerDes.toDTO(json);
	}

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public PageElementDefinition getPageElementDefinition() {
		return pageElementDefinition;
	}

	public void setPageElementDefinition(
		PageElementDefinition pageElementDefinition) {

		this.pageElementDefinition = pageElementDefinition;
	}

	public void setPageElementDefinition(
		UnsafeSupplier<PageElementDefinition, Exception>
			pageElementDefinitionUnsafeSupplier) {

		try {
			pageElementDefinition = pageElementDefinitionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PageElementDefinition pageElementDefinition;

	public PageElement[] getPageElements() {
		return pageElements;
	}

	public void setPageElements(PageElement[] pageElements) {
		this.pageElements = pageElements;
	}

	public void setPageElements(
		UnsafeSupplier<PageElement[], Exception> pageElementsUnsafeSupplier) {

		try {
			pageElements = pageElementsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PageElement[] pageElements;

	public String getParentExternalReferenceCode() {
		return parentExternalReferenceCode;
	}

	public void setParentExternalReferenceCode(
		String parentExternalReferenceCode) {

		this.parentExternalReferenceCode = parentExternalReferenceCode;
	}

	public void setParentExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			parentExternalReferenceCodeUnsafeSupplier) {

		try {
			parentExternalReferenceCode =
				parentExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String parentExternalReferenceCode;

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void setPosition(
		UnsafeSupplier<Integer, Exception> positionUnsafeSupplier) {

		try {
			position = positionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer position;

	@Override
	public PageElement clone() throws CloneNotSupportedException {
		return (PageElement)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageElement)) {
			return false;
		}

		PageElement pageElement = (PageElement)object;

		return Objects.equals(toString(), pageElement.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageElementSerDes.toJSON(this);
	}

}