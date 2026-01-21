/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.LinkFragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class LinkFragmentEditableElementValue
	extends FragmentEditableElementValue implements Cloneable, Serializable {

	public static LinkFragmentEditableElementValue toDTO(String json) {
		return LinkFragmentEditableElementValueSerDes.toDTO(json);
	}

	public FragmentLinkTextValue getFragmentLinkTextValue() {
		return fragmentLinkTextValue;
	}

	public void setFragmentLinkTextValue(
		FragmentLinkTextValue fragmentLinkTextValue) {

		this.fragmentLinkTextValue = fragmentLinkTextValue;
	}

	public void setFragmentLinkTextValue(
		UnsafeSupplier<FragmentLinkTextValue, Exception>
			fragmentLinkTextValueUnsafeSupplier) {

		try {
			fragmentLinkTextValue = fragmentLinkTextValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentLinkTextValue fragmentLinkTextValue;

	@Override
	public LinkFragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (LinkFragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LinkFragmentEditableElementValue)) {
			return false;
		}

		LinkFragmentEditableElementValue linkFragmentEditableElementValue =
			(LinkFragmentEditableElementValue)object;

		return Objects.equals(
			toString(), linkFragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LinkFragmentEditableElementValueSerDes.toJSON(this);
	}

}