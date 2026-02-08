/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.GridPageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class GridPageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static GridPageElementDefinition toDTO(String json) {
		return GridPageElementDefinitionSerDes.toDTO(json);
	}

	public BackgroundImageValue getBackgroundImageValue() {
		return backgroundImageValue;
	}

	public void setBackgroundImageValue(
		BackgroundImageValue backgroundImageValue) {

		this.backgroundImageValue = backgroundImageValue;
	}

	public void setBackgroundImageValue(
		UnsafeSupplier<BackgroundImageValue, Exception>
			backgroundImageValueUnsafeSupplier) {

		try {
			backgroundImageValue = backgroundImageValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BackgroundImageValue backgroundImageValue;

	public String[] getCssClasses() {
		return cssClasses;
	}

	public void setCssClasses(String[] cssClasses) {
		this.cssClasses = cssClasses;
	}

	public void setCssClasses(
		UnsafeSupplier<String[], Exception> cssClassesUnsafeSupplier) {

		try {
			cssClasses = cssClassesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] cssClasses;

	public GridViewport[] getGridViewports() {
		return gridViewports;
	}

	public void setGridViewports(GridViewport[] gridViewports) {
		this.gridViewports = gridViewports;
	}

	public void setGridViewports(
		UnsafeSupplier<GridViewport[], Exception> gridViewportsUnsafeSupplier) {

		try {
			gridViewports = gridViewportsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected GridViewport[] gridViewports;

	public Boolean getGutters() {
		return gutters;
	}

	public void setGutters(Boolean gutters) {
		this.gutters = gutters;
	}

	public void setGutters(
		UnsafeSupplier<Boolean, Exception> guttersUnsafeSupplier) {

		try {
			gutters = guttersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean gutters;

	public Boolean getIndexed() {
		return indexed;
	}

	public void setIndexed(Boolean indexed) {
		this.indexed = indexed;
	}

	public void setIndexed(
		UnsafeSupplier<Boolean, Exception> indexedUnsafeSupplier) {

		try {
			indexed = indexedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean indexed;

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

	public Integer getNumberOfModules() {
		return numberOfModules;
	}

	public void setNumberOfModules(Integer numberOfModules) {
		this.numberOfModules = numberOfModules;
	}

	public void setNumberOfModules(
		UnsafeSupplier<Integer, Exception> numberOfModulesUnsafeSupplier) {

		try {
			numberOfModules = numberOfModulesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer numberOfModules;

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

	@Override
	public GridPageElementDefinition clone() throws CloneNotSupportedException {
		return (GridPageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GridPageElementDefinition)) {
			return false;
		}

		GridPageElementDefinition gridPageElementDefinition =
			(GridPageElementDefinition)object;

		return Objects.equals(toString(), gridPageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return GridPageElementDefinitionSerDes.toJSON(this);
	}

}