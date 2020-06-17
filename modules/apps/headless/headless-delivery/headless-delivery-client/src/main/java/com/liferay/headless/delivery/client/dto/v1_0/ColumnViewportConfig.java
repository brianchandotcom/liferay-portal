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
import com.liferay.headless.delivery.client.serdes.v1_0.ColumnViewportConfigSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ColumnViewportConfig implements Cloneable {

	public static ColumnViewportConfig toDTO(String json) {
		return ColumnViewportConfigSerDes.toDTO(json);
	}

	public ColumnViewportConfigDefinition getLandscapeMobile() {
		return landscapeMobile;
	}

	public void setLandscapeMobile(
		ColumnViewportConfigDefinition landscapeMobile) {

		this.landscapeMobile = landscapeMobile;
	}

	public void setLandscapeMobile(
		UnsafeSupplier<ColumnViewportConfigDefinition, Exception>
			landscapeMobileUnsafeSupplier) {

		try {
			landscapeMobile = landscapeMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ColumnViewportConfigDefinition landscapeMobile;

	public ColumnViewportConfigDefinition getPortraitMobile() {
		return portraitMobile;
	}

	public void setPortraitMobile(
		ColumnViewportConfigDefinition portraitMobile) {

		this.portraitMobile = portraitMobile;
	}

	public void setPortraitMobile(
		UnsafeSupplier<ColumnViewportConfigDefinition, Exception>
			portraitMobileUnsafeSupplier) {

		try {
			portraitMobile = portraitMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ColumnViewportConfigDefinition portraitMobile;

	public ColumnViewportConfigDefinition getTablet() {
		return tablet;
	}

	public void setTablet(ColumnViewportConfigDefinition tablet) {
		this.tablet = tablet;
	}

	public void setTablet(
		UnsafeSupplier<ColumnViewportConfigDefinition, Exception>
			tabletUnsafeSupplier) {

		try {
			tablet = tabletUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ColumnViewportConfigDefinition tablet;

	@Override
	public ColumnViewportConfig clone() throws CloneNotSupportedException {
		return (ColumnViewportConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ColumnViewportConfig)) {
			return false;
		}

		ColumnViewportConfig columnViewportConfig =
			(ColumnViewportConfig)object;

		return Objects.equals(toString(), columnViewportConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ColumnViewportConfigSerDes.toJSON(this);
	}

}