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
import com.liferay.headless.delivery.client.serdes.v1_0.CustomCSSViewportSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CustomCSSViewport implements Cloneable, Serializable {

	public static CustomCSSViewport toDTO(String json) {
		return CustomCSSViewportSerDes.toDTO(json);
	}

	public String getCustomCSS() {
		return customCSS;
	}

	public void setCustomCSS(String customCSS) {
		this.customCSS = customCSS;
	}

	public void setCustomCSS(
		UnsafeSupplier<String, Exception> customCSSUnsafeSupplier) {

		try {
			customCSS = customCSSUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String customCSS;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	@Override
	public CustomCSSViewport clone() throws CloneNotSupportedException {
		return (CustomCSSViewport)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CustomCSSViewport)) {
			return false;
		}

		CustomCSSViewport customCSSViewport = (CustomCSSViewport)object;

		return Objects.equals(toString(), customCSSViewport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CustomCSSViewportSerDes.toJSON(this);
	}

}