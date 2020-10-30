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
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentImageConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentImageConfiguration implements Cloneable, Serializable {

	public static FragmentImageConfiguration toDTO(String json) {
		return FragmentImageConfigurationSerDes.toDTO(json);
	}

	public String getLandscapeMobile() {
		return landscapeMobile;
	}

	public void setLandscapeMobile(String landscapeMobile) {
		this.landscapeMobile = landscapeMobile;
	}

	public void setLandscapeMobile(
		UnsafeSupplier<String, Exception> landscapeMobileUnsafeSupplier) {

		try {
			landscapeMobile = landscapeMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String landscapeMobile;

	public String getPortraitMobile() {
		return portraitMobile;
	}

	public void setPortraitMobile(String portraitMobile) {
		this.portraitMobile = portraitMobile;
	}

	public void setPortraitMobile(
		UnsafeSupplier<String, Exception> portraitMobileUnsafeSupplier) {

		try {
			portraitMobile = portraitMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String portraitMobile;

	public String getTablet() {
		return tablet;
	}

	public void setTablet(String tablet) {
		this.tablet = tablet;
	}

	public void setTablet(
		UnsafeSupplier<String, Exception> tabletUnsafeSupplier) {

		try {
			tablet = tabletUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String tablet;

	@Override
	public FragmentImageConfiguration clone()
		throws CloneNotSupportedException {

		return (FragmentImageConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentImageConfiguration)) {
			return false;
		}

		FragmentImageConfiguration fragmentImageConfiguration =
			(FragmentImageConfiguration)object;

		return Objects.equals(
			toString(), fragmentImageConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentImageConfigurationSerDes.toJSON(this);
	}

}