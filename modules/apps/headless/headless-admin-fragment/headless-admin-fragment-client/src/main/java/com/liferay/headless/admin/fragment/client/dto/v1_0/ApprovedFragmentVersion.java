/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.dto.v1_0;

import com.liferay.headless.admin.fragment.client.function.UnsafeSupplier;
import com.liferay.headless.admin.fragment.client.serdes.v1_0.ApprovedFragmentVersionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ApprovedFragmentVersion
	extends FragmentVersion implements Cloneable, Serializable {

	public static ApprovedFragmentVersion toDTO(String json) {
		return ApprovedFragmentVersionSerDes.toDTO(json);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setConfiguration(
		UnsafeSupplier<Configuration, Exception> configurationUnsafeSupplier) {

		try {
			configuration = configurationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Configuration configuration;

	@Override
	public ApprovedFragmentVersion clone() throws CloneNotSupportedException {
		return (ApprovedFragmentVersion)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ApprovedFragmentVersion)) {
			return false;
		}

		ApprovedFragmentVersion approvedFragmentVersion =
			(ApprovedFragmentVersion)object;

		return Objects.equals(toString(), approvedFragmentVersion.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ApprovedFragmentVersionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:405516409