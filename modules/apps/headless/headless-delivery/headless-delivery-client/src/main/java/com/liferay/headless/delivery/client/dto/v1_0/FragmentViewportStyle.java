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
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentViewportStyleSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentViewportStyle implements Cloneable, Serializable {

	public static FragmentViewportStyle toDTO(String json) {
		return FragmentViewportStyleSerDes.toDTO(json);
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public void setHidden(
		UnsafeSupplier<Boolean, Exception> hiddenUnsafeSupplier) {

		try {
			hidden = hiddenUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean hidden;

	public String getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	public void setMarginBottom(
		UnsafeSupplier<String, Exception> marginBottomUnsafeSupplier) {

		try {
			marginBottom = marginBottomUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String marginBottom;

	public String getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	public void setMarginLeft(
		UnsafeSupplier<String, Exception> marginLeftUnsafeSupplier) {

		try {
			marginLeft = marginLeftUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String marginLeft;

	public String getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	public void setMarginRight(
		UnsafeSupplier<String, Exception> marginRightUnsafeSupplier) {

		try {
			marginRight = marginRightUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String marginRight;

	public String getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public void setMarginTop(
		UnsafeSupplier<String, Exception> marginTopUnsafeSupplier) {

		try {
			marginTop = marginTopUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String marginTop;

	public String getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(String paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public void setPaddingBottom(
		UnsafeSupplier<String, Exception> paddingBottomUnsafeSupplier) {

		try {
			paddingBottom = paddingBottomUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String paddingBottom;

	public String getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public void setPaddingLeft(
		UnsafeSupplier<String, Exception> paddingLeftUnsafeSupplier) {

		try {
			paddingLeft = paddingLeftUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String paddingLeft;

	public String getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(String paddingRight) {
		this.paddingRight = paddingRight;
	}

	public void setPaddingRight(
		UnsafeSupplier<String, Exception> paddingRightUnsafeSupplier) {

		try {
			paddingRight = paddingRightUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String paddingRight;

	public String getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(String paddingTop) {
		this.paddingTop = paddingTop;
	}

	public void setPaddingTop(
		UnsafeSupplier<String, Exception> paddingTopUnsafeSupplier) {

		try {
			paddingTop = paddingTopUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String paddingTop;

	@Override
	public FragmentViewportStyle clone() throws CloneNotSupportedException {
		return (FragmentViewportStyle)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentViewportStyle)) {
			return false;
		}

		FragmentViewportStyle fragmentViewportStyle =
			(FragmentViewportStyle)object;

		return Objects.equals(toString(), fragmentViewportStyle.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentViewportStyleSerDes.toJSON(this);
	}

}