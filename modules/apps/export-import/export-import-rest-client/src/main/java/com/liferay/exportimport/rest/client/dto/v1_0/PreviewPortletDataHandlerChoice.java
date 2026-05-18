/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PreviewPortletDataHandlerChoiceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PreviewPortletDataHandlerChoice
	extends PreviewPortletDataHandlerControl
	implements Cloneable, Serializable {

	public static PreviewPortletDataHandlerChoice toDTO(String json) {
		return PreviewPortletDataHandlerChoiceSerDes.toDTO(json);
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
	public PreviewPortletDataHandlerChoice clone()
		throws CloneNotSupportedException {

		return (PreviewPortletDataHandlerChoice)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PreviewPortletDataHandlerChoice)) {
			return false;
		}

		PreviewPortletDataHandlerChoice previewPortletDataHandlerChoice =
			(PreviewPortletDataHandlerChoice)object;

		return Objects.equals(
			toString(), previewPortletDataHandlerChoice.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PreviewPortletDataHandlerChoiceSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-904745517