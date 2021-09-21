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
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentViewportSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentViewport implements Cloneable, Serializable {

	public static FragmentViewport toDTO(String json) {
		return FragmentViewportSerDes.toDTO(json);
	}

	public FragmentViewportStyle getFragmentViewportStyle() {
		return fragmentViewportStyle;
	}

	public void setFragmentViewportStyle(
		FragmentViewportStyle fragmentViewportStyle) {

		this.fragmentViewportStyle = fragmentViewportStyle;
	}

	public void setFragmentViewportStyle(
		UnsafeSupplier<FragmentViewportStyle, Exception>
			fragmentViewportStyleUnsafeSupplier) {

		try {
			fragmentViewportStyle = fragmentViewportStyleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentViewportStyle fragmentViewportStyle;

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
	public FragmentViewport clone() throws CloneNotSupportedException {
		return (FragmentViewport)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentViewport)) {
			return false;
		}

		FragmentViewport fragmentViewport = (FragmentViewport)object;

		return Objects.equals(toString(), fragmentViewport.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentViewportSerDes.toJSON(this);
	}

}