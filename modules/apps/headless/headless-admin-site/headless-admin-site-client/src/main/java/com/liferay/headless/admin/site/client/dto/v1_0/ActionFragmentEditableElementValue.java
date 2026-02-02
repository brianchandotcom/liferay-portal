/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.ActionFragmentEditableElementValueSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ActionFragmentEditableElementValue
	extends FragmentEditableElementValue implements Cloneable, Serializable {

	public static ActionFragmentEditableElementValue toDTO(String json) {
		return ActionFragmentEditableElementValueSerDes.toDTO(json);
	}

	public ActionInteraction getErrorActionInteraction() {
		return errorActionInteraction;
	}

	public void setErrorActionInteraction(
		ActionInteraction errorActionInteraction) {

		this.errorActionInteraction = errorActionInteraction;
	}

	public void setErrorActionInteraction(
		UnsafeSupplier<ActionInteraction, Exception>
			errorActionInteractionUnsafeSupplier) {

		try {
			errorActionInteraction = errorActionInteractionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ActionInteraction errorActionInteraction;

	public FragmentMappedValue getFragmentMappedValue() {
		return fragmentMappedValue;
	}

	public void setFragmentMappedValue(
		FragmentMappedValue fragmentMappedValue) {

		this.fragmentMappedValue = fragmentMappedValue;
	}

	public void setFragmentMappedValue(
		UnsafeSupplier<FragmentMappedValue, Exception>
			fragmentMappedValueUnsafeSupplier) {

		try {
			fragmentMappedValue = fragmentMappedValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentMappedValue fragmentMappedValue;

	public ActionInteraction getSuccessActionInteraction() {
		return successActionInteraction;
	}

	public void setSuccessActionInteraction(
		ActionInteraction successActionInteraction) {

		this.successActionInteraction = successActionInteraction;
	}

	public void setSuccessActionInteraction(
		UnsafeSupplier<ActionInteraction, Exception>
			successActionInteractionUnsafeSupplier) {

		try {
			successActionInteraction =
				successActionInteractionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ActionInteraction successActionInteraction;

	public TextFragmentValue getTextFragmentValue() {
		return textFragmentValue;
	}

	public void setTextFragmentValue(TextFragmentValue textFragmentValue) {
		this.textFragmentValue = textFragmentValue;
	}

	public void setTextFragmentValue(
		UnsafeSupplier<TextFragmentValue, Exception>
			textFragmentValueUnsafeSupplier) {

		try {
			textFragmentValue = textFragmentValueUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected TextFragmentValue textFragmentValue;

	@Override
	public ActionFragmentEditableElementValue clone()
		throws CloneNotSupportedException {

		return (ActionFragmentEditableElementValue)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ActionFragmentEditableElementValue)) {
			return false;
		}

		ActionFragmentEditableElementValue actionFragmentEditableElementValue =
			(ActionFragmentEditableElementValue)object;

		return Objects.equals(
			toString(), actionFragmentEditableElementValue.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ActionFragmentEditableElementValueSerDes.toJSON(this);
	}

}