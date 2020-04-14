/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.DisplayPageTemplateSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DisplayPageTemplate implements Cloneable {

	public String getContentSubtypeName() {
		return contentSubtypeName;
	}

	public void setContentSubtypeName(String contentSubtypeName) {
		this.contentSubtypeName = contentSubtypeName;
	}

	public void setContentSubtypeName(
		UnsafeSupplier<String, Exception> contentSubtypeNameUnsafeSupplier) {

		try {
			contentSubtypeName = contentSubtypeNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contentSubtypeName;

	public String getContentTypeClassName() {
		return contentTypeClassName;
	}

	public void setContentTypeClassName(String contentTypeClassName) {
		this.contentTypeClassName = contentTypeClassName;
	}

	public void setContentTypeClassName(
		UnsafeSupplier<String, Exception> contentTypeClassNameUnsafeSupplier) {

		try {
			contentTypeClassName = contentTypeClassNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contentTypeClassName;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String key;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	@Override
	public DisplayPageTemplate clone() throws CloneNotSupportedException {
		return (DisplayPageTemplate)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageTemplate)) {
			return false;
		}

		DisplayPageTemplate displayPageTemplate = (DisplayPageTemplate)object;

		return Objects.equals(toString(), displayPageTemplate.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageTemplateSerDes.toJSON(this);
	}

}