/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PreviewPortletDataHandlerSettingSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PreviewPortletDataHandlerSetting
	extends PreviewPortletDataHandlerControl
	implements Cloneable, Serializable {

	public static PreviewPortletDataHandlerSetting toDTO(String json) {
		return PreviewPortletDataHandlerSettingSerDes.toDTO(json);
	}

	public Boolean getDefaultState() {
		return defaultState;
	}

	public void setDefaultState(Boolean defaultState) {
		this.defaultState = defaultState;
	}

	public void setDefaultState(
		UnsafeSupplier<Boolean, Exception> defaultStateUnsafeSupplier) {

		try {
			defaultState = defaultStateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean defaultState;

	public PreviewPortletDataHandlerControl[]
		getPreviewPortletDataHandlerControls() {

		return previewPortletDataHandlerControls;
	}

	public void setPreviewPortletDataHandlerControls(
		PreviewPortletDataHandlerControl[] previewPortletDataHandlerControls) {

		this.previewPortletDataHandlerControls =
			previewPortletDataHandlerControls;
	}

	public void setPreviewPortletDataHandlerControls(
		UnsafeSupplier<PreviewPortletDataHandlerControl[], Exception>
			previewPortletDataHandlerControlsUnsafeSupplier) {

		try {
			previewPortletDataHandlerControls =
				previewPortletDataHandlerControlsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PreviewPortletDataHandlerControl[]
		previewPortletDataHandlerControls;

	@Override
	public PreviewPortletDataHandlerSetting clone()
		throws CloneNotSupportedException {

		return (PreviewPortletDataHandlerSetting)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PreviewPortletDataHandlerSetting)) {
			return false;
		}

		PreviewPortletDataHandlerSetting previewPortletDataHandlerSetting =
			(PreviewPortletDataHandlerSetting)object;

		return Objects.equals(
			toString(), previewPortletDataHandlerSetting.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PreviewPortletDataHandlerSettingSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1074218424