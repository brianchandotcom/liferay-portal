/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentImageSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentImage implements Cloneable, Serializable {

	public static FragmentImage toDTO(String json) {
		return FragmentImageSerDes.toDTO(json);
	}

	public Map<String, String> getDescription_i18n() {
		return description_i18n;
	}

	public void setDescription_i18n(Map<String, String> description_i18n) {
		this.description_i18n = description_i18n;
	}

	public void setDescription_i18n(
		UnsafeSupplier<Map<String, String>, Exception>
			description_i18nUnsafeSupplier) {

		try {
			description_i18n = description_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> description_i18n;

	public FragmentImageValue getFragmentImageValue() {
		return fragmentImageValue;
	}

	public void setFragmentImageValue(FragmentImageValue fragmentImageValue) {
		this.fragmentImageValue = fragmentImageValue;
	}

	public void setFragmentImageValue(
		UnsafeSupplier<FragmentImageValue, Exception>
			fragmentImageValueUnsafeSupplier) {

		try {
			fragmentImageValue = fragmentImageValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentImageValue fragmentImageValue;

	public FragmentImageViewport[] getFragmentImageViewports() {
		return fragmentImageViewports;
	}

	public void setFragmentImageViewports(
		FragmentImageViewport[] fragmentImageViewports) {

		this.fragmentImageViewports = fragmentImageViewports;
	}

	public void setFragmentImageViewports(
		UnsafeSupplier<FragmentImageViewport[], Exception>
			fragmentImageViewportsUnsafeSupplier) {

		try {
			fragmentImageViewports = fragmentImageViewportsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentImageViewport[] fragmentImageViewports;

	public Boolean getLazyLoading() {
		return lazyLoading;
	}

	public void setLazyLoading(Boolean lazyLoading) {
		this.lazyLoading = lazyLoading;
	}

	public void setLazyLoading(
		UnsafeSupplier<Boolean, Exception> lazyLoadingUnsafeSupplier) {

		try {
			lazyLoading = lazyLoadingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean lazyLoading;

	@Override
	public FragmentImage clone() throws CloneNotSupportedException {
		return (FragmentImage)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentImage)) {
			return false;
		}

		FragmentImage fragmentImage = (FragmentImage)object;

		return Objects.equals(toString(), fragmentImage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentImageSerDes.toJSON(this);
	}

}