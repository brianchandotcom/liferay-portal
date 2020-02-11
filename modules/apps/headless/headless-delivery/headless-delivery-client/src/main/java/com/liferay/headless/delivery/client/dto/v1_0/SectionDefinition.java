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
import com.liferay.headless.delivery.client.serdes.v1_0.SectionDefinitionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SectionDefinition {

	public String getBackgroundColorCssClass() {
		return backgroundColorCssClass;
	}

	public void setBackgroundColorCssClass(String backgroundColorCssClass) {
		this.backgroundColorCssClass = backgroundColorCssClass;
	}

	public void setBackgroundColorCssClass(
		UnsafeSupplier<String, Exception>
			backgroundColorCssClassUnsafeSupplier) {

		try {
			backgroundColorCssClass =
				backgroundColorCssClassUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String backgroundColorCssClass;

	public FragmentImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(FragmentImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void setBackgroundImage(
		UnsafeSupplier<FragmentImage, Exception>
			backgroundImageUnsafeSupplier) {

		try {
			backgroundImage = backgroundImageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentImage backgroundImage;

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public void setLayout(
		UnsafeSupplier<Layout, Exception> layoutUnsafeSupplier) {

		try {
			layout = layoutUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Layout layout;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SectionDefinition)) {
			return false;
		}

		SectionDefinition sectionDefinition = (SectionDefinition)object;

		return Objects.equals(toString(), sectionDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SectionDefinitionSerDes.toJSON(this);
	}

}