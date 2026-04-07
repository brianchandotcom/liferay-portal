/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.VideoFragmentConfigurationFieldValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class VideoFragmentConfigurationFieldValue
	extends FragmentConfigurationFieldValue implements Cloneable, Serializable {

	public static VideoFragmentConfigurationFieldValue toDTO(String json) {
		return VideoFragmentConfigurationFieldValueSerDes.toDTO(json);
	}

	public VideoValue getValue() {
		return value;
	}

	public void setValue(VideoValue value) {
		this.value = value;
	}

	public void setValue(
		UnsafeSupplier<VideoValue, Exception> valueUnsafeSupplier) {

		try {
			value = valueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected VideoValue value;

	public Map<String, VideoValue> getValue_i18n() {
		return value_i18n;
	}

	public void setValue_i18n(Map<String, VideoValue> value_i18n) {
		this.value_i18n = value_i18n;
	}

	public void setValue_i18n(
		UnsafeSupplier<Map<String, VideoValue>, Exception>
			value_i18nUnsafeSupplier) {

		try {
			value_i18n = value_i18nUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, VideoValue> value_i18n;

	@Override
	public VideoFragmentConfigurationFieldValue clone()
		throws CloneNotSupportedException {

		return (VideoFragmentConfigurationFieldValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VideoFragmentConfigurationFieldValue)) {
			return false;
		}

		VideoFragmentConfigurationFieldValue
			videoFragmentConfigurationFieldValue =
				(VideoFragmentConfigurationFieldValue)object;

		return Objects.equals(
			toString(), videoFragmentConfigurationFieldValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return VideoFragmentConfigurationFieldValueSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:940700594