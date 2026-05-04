/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.dto.v1_0;

import com.liferay.exportimport.rest.client.function.UnsafeSupplier;
import com.liferay.exportimport.rest.client.serdes.v1_0.PortletDataHandlerSettingSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PortletDataHandlerSetting
	extends PortletDataHandlerControl implements Cloneable, Serializable {

	public static PortletDataHandlerSetting toDTO(String json) {
		return PortletDataHandlerSettingSerDes.toDTO(json);
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

	public PortletDataHandlerControl[] getPortletDataHandlerControls() {
		return portletDataHandlerControls;
	}

	public void setPortletDataHandlerControls(
		PortletDataHandlerControl[] portletDataHandlerControls) {

		this.portletDataHandlerControls = portletDataHandlerControls;
	}

	public void setPortletDataHandlerControls(
		UnsafeSupplier<PortletDataHandlerControl[], Exception>
			portletDataHandlerControlsUnsafeSupplier) {

		try {
			portletDataHandlerControls =
				portletDataHandlerControlsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PortletDataHandlerControl[] portletDataHandlerControls;

	@Override
	public PortletDataHandlerSetting clone() throws CloneNotSupportedException {
		return (PortletDataHandlerSetting)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletDataHandlerSetting)) {
			return false;
		}

		PortletDataHandlerSetting portletDataHandlerSetting =
			(PortletDataHandlerSetting)object;

		return Objects.equals(toString(), portletDataHandlerSetting.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PortletDataHandlerSettingSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-359250662