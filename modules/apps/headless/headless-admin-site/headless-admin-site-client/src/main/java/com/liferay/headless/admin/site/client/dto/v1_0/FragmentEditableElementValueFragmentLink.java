/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.FragmentEditableElementValueFragmentLinkSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentEditableElementValueFragmentLink
	implements Cloneable, Serializable {

	public static FragmentEditableElementValueFragmentLink toDTO(String json) {
		return FragmentEditableElementValueFragmentLinkSerDes.toDTO(json);
	}

	public FragmentLink getFragmentLink() {
		return fragmentLink;
	}

	public void setFragmentLink(FragmentLink fragmentLink) {
		this.fragmentLink = fragmentLink;
	}

	public void setFragmentLink(
		UnsafeSupplier<FragmentLink, Exception> fragmentLinkUnsafeSupplier) {

		try {
			fragmentLink = fragmentLinkUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentLink fragmentLink;

	public Prefix getPrefix() {
		return prefix;
	}

	public String getPrefixAsString() {
		if (prefix == null) {
			return null;
		}

		return prefix.toString();
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	public void setPrefix(
		UnsafeSupplier<Prefix, Exception> prefixUnsafeSupplier) {

		try {
			prefix = prefixUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Prefix prefix;

	@Override
	public FragmentEditableElementValueFragmentLink clone()
		throws CloneNotSupportedException {

		return (FragmentEditableElementValueFragmentLink)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentEditableElementValueFragmentLink)) {
			return false;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				(FragmentEditableElementValueFragmentLink)object;

		return Objects.equals(
			toString(), fragmentEditableElementValueFragmentLink.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentEditableElementValueFragmentLinkSerDes.toJSON(this);
	}

	public static enum Prefix {

		EMAIL("Email"), NONE("None"), PHONE("Phone");

		public static Prefix create(String value) {
			for (Prefix prefix : values()) {
				if (Objects.equals(prefix.getValue(), value) ||
					Objects.equals(prefix.name(), value)) {

					return prefix;
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

		private Prefix(String value) {
			_value = value;
		}

		private final String _value;

	}

}