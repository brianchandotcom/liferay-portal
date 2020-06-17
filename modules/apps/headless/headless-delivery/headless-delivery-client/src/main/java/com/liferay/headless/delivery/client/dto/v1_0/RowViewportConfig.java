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
import com.liferay.headless.delivery.client.serdes.v1_0.RowViewportConfigSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RowViewportConfig implements Cloneable {

	public static RowViewportConfig toDTO(String json) {
		return RowViewportConfigSerDes.toDTO(json);
	}

	public RowViewportConfigDefinition getLandscapeMobile() {
		return landscapeMobile;
	}

	public void setLandscapeMobile(
		RowViewportConfigDefinition landscapeMobile) {

		this.landscapeMobile = landscapeMobile;
	}

	public void setLandscapeMobile(
		UnsafeSupplier<RowViewportConfigDefinition, Exception>
			landscapeMobileUnsafeSupplier) {

		try {
			landscapeMobile = landscapeMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RowViewportConfigDefinition landscapeMobile;

	public RowViewportConfigDefinition getPortraitMobile() {
		return portraitMobile;
	}

	public void setPortraitMobile(RowViewportConfigDefinition portraitMobile) {
		this.portraitMobile = portraitMobile;
	}

	public void setPortraitMobile(
		UnsafeSupplier<RowViewportConfigDefinition, Exception>
			portraitMobileUnsafeSupplier) {

		try {
			portraitMobile = portraitMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RowViewportConfigDefinition portraitMobile;

	public RowViewportConfigDefinition getTablet() {
		return tablet;
	}

	public void setTablet(RowViewportConfigDefinition tablet) {
		this.tablet = tablet;
	}

	public void setTablet(
		UnsafeSupplier<RowViewportConfigDefinition, Exception>
			tabletUnsafeSupplier) {

		try {
			tablet = tabletUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RowViewportConfigDefinition tablet;

	@Override
	public RowViewportConfig clone() throws CloneNotSupportedException {
		return (RowViewportConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RowViewportConfig)) {
			return false;
		}

		RowViewportConfig rowViewportConfig = (RowViewportConfig)object;

		return Objects.equals(toString(), rowViewportConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RowViewportConfigSerDes.toJSON(this);
	}

}