/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.SXPBlueprintConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class SXPBlueprintConfiguration implements Cloneable, Serializable {

	public static SXPBlueprintConfiguration toDTO(String json) {
		return SXPBlueprintConfigurationSerDes.toDTO(json);
	}

	public Framework_configuration getFramework_configuration() {
		return framework_configuration;
	}

	public void setFramework_configuration(
		Framework_configuration framework_configuration) {

		this.framework_configuration = framework_configuration;
	}

	public void setFramework_configuration(
		UnsafeSupplier<Framework_configuration, Exception>
			framework_configurationUnsafeSupplier) {

		try {
			framework_configuration =
				framework_configurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Framework_configuration framework_configuration;

	@Override
	public SXPBlueprintConfiguration clone() throws CloneNotSupportedException {
		return (SXPBlueprintConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SXPBlueprintConfiguration)) {
			return false;
		}

		SXPBlueprintConfiguration sxpBlueprintConfiguration =
			(SXPBlueprintConfiguration)object;

		return Objects.equals(toString(), sxpBlueprintConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SXPBlueprintConfigurationSerDes.toJSON(this);
	}

}