/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PortletDataHandlerChoiceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PortletDataHandlerChoice
	extends PortletDataHandlerControl implements Cloneable, Serializable {

	public static PortletDataHandlerChoice toDTO(String json) {
		return PortletDataHandlerChoiceSerDes.toDTO(json);
	}

	public Choice[] getChoices() {
		return choices;
	}

	public void setChoices(Choice[] choices) {
		this.choices = choices;
	}

	public void setChoices(
		UnsafeSupplier<Choice[], Exception> choicesUnsafeSupplier) {

		try {
			choices = choicesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Choice[] choices;

	public String getDefaultChoice() {
		return defaultChoice;
	}

	public void setDefaultChoice(String defaultChoice) {
		this.defaultChoice = defaultChoice;
	}

	public void setDefaultChoice(
		UnsafeSupplier<String, Exception> defaultChoiceUnsafeSupplier) {

		try {
			defaultChoice = defaultChoiceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String defaultChoice;

	@Override
	public PortletDataHandlerChoice clone() throws CloneNotSupportedException {
		return (PortletDataHandlerChoice)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletDataHandlerChoice)) {
			return false;
		}

		PortletDataHandlerChoice portletDataHandlerChoice =
			(PortletDataHandlerChoice)object;

		return Objects.equals(toString(), portletDataHandlerChoice.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PortletDataHandlerChoiceSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:864044645