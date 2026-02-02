/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.URLActionInteractionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class URLActionInteraction
	extends ActionInteraction implements Cloneable, Serializable {

	public static URLActionInteraction toDTO(String json) {
		return URLActionInteractionSerDes.toDTO(json);
	}

	public FragmentInlineValue getFragmentInlineValue() {
		return fragmentInlineValue;
	}

	public void setFragmentInlineValue(
		FragmentInlineValue fragmentInlineValue) {

		this.fragmentInlineValue = fragmentInlineValue;
	}

	public void setFragmentInlineValue(
		UnsafeSupplier<FragmentInlineValue, Exception>
			fragmentInlineValueUnsafeSupplier) {

		try {
			fragmentInlineValue = fragmentInlineValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue fragmentInlineValue;

	@Override
	public URLActionInteraction clone() throws CloneNotSupportedException {
		return (URLActionInteraction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof URLActionInteraction)) {
			return false;
		}

		URLActionInteraction urlActionInteraction =
			(URLActionInteraction)object;

		return Objects.equals(toString(), urlActionInteraction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return URLActionInteractionSerDes.toJSON(this);
	}

}