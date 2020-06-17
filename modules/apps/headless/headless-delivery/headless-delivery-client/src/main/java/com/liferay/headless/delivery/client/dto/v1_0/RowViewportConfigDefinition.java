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
import com.liferay.headless.delivery.client.serdes.v1_0.RowViewportConfigDefinitionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RowViewportConfigDefinition implements Cloneable {

	public static RowViewportConfigDefinition toDTO(String json) {
		return RowViewportConfigDefinitionSerDes.toDTO(json);
	}

	public Integer getModulesPerRow() {
		return modulesPerRow;
	}

	public void setModulesPerRow(Integer modulesPerRow) {
		this.modulesPerRow = modulesPerRow;
	}

	public void setModulesPerRow(
		UnsafeSupplier<Integer, Exception> modulesPerRowUnsafeSupplier) {

		try {
			modulesPerRow = modulesPerRowUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer modulesPerRow;

	public Boolean getReverseOrder() {
		return reverseOrder;
	}

	public void setReverseOrder(Boolean reverseOrder) {
		this.reverseOrder = reverseOrder;
	}

	public void setReverseOrder(
		UnsafeSupplier<Boolean, Exception> reverseOrderUnsafeSupplier) {

		try {
			reverseOrder = reverseOrderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean reverseOrder;

	public String getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public void setVerticalAlignment(
		UnsafeSupplier<String, Exception> verticalAlignmentUnsafeSupplier) {

		try {
			verticalAlignment = verticalAlignmentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String verticalAlignment;

	@Override
	public RowViewportConfigDefinition clone()
		throws CloneNotSupportedException {

		return (RowViewportConfigDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RowViewportConfigDefinition)) {
			return false;
		}

		RowViewportConfigDefinition rowViewportConfigDefinition =
			(RowViewportConfigDefinition)object;

		return Objects.equals(
			toString(), rowViewportConfigDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RowViewportConfigDefinitionSerDes.toJSON(this);
	}

}