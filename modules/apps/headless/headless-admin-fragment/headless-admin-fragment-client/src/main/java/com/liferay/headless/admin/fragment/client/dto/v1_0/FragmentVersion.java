/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.dto.v1_0;

import com.liferay.headless.admin.fragment.client.function.UnsafeSupplier;
import com.liferay.headless.admin.fragment.client.serdes.v1_0.FragmentVersionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentVersion implements Cloneable, Serializable {

	public static FragmentVersion toDTO(String json) {
		return FragmentVersionSerDes.toDTO(json);
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public void setConfiguration(
		UnsafeSupplier<String, Exception> configurationUnsafeSupplier) {

		try {
			configuration = configurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String configuration;

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setCss(UnsafeSupplier<String, Exception> cssUnsafeSupplier) {
		try {
			css = cssUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String css;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void setHtml(UnsafeSupplier<String, Exception> htmlUnsafeSupplier) {
		try {
			html = htmlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String html;

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public void setJs(UnsafeSupplier<String, Exception> jsUnsafeSupplier) {
		try {
			js = jsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String js;

	public Status getStatus() {
		return status;
	}

	public String getStatusAsString() {
		if (status == null) {
			return null;
		}

		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status status;

	@Override
	public FragmentVersion clone() throws CloneNotSupportedException {
		return (FragmentVersion)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentVersion)) {
			return false;
		}

		FragmentVersion fragmentVersion = (FragmentVersion)object;

		return Objects.equals(toString(), fragmentVersion.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentVersionSerDes.toJSON(this);
	}

	public static enum Status {

		APPROVED("Approved"), DRAFT("Draft");

		public static Status create(String value) {
			for (Status status : values()) {
				if (Objects.equals(status.getValue(), value) ||
					Objects.equals(status.name(), value)) {

					return status;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Status(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:913635407