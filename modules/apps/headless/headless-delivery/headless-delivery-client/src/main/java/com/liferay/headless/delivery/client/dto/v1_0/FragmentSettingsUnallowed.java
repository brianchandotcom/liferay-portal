/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentSettingsUnallowedSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentSettingsUnallowed implements Cloneable {

	public Fragment[] getUnallowedFragments() {
		return unallowedFragments;
	}

	public void setUnallowedFragments(Fragment[] unallowedFragments) {
		this.unallowedFragments = unallowedFragments;
	}

	public void setUnallowedFragments(
		UnsafeSupplier<Fragment[], Exception>
			unallowedFragmentsUnsafeSupplier) {

		try {
			unallowedFragments = unallowedFragmentsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Fragment[] unallowedFragments;

	@Override
	public FragmentSettingsUnallowed clone() throws CloneNotSupportedException {
		return (FragmentSettingsUnallowed)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentSettingsUnallowed)) {
			return false;
		}

		FragmentSettingsUnallowed fragmentSettingsUnallowed =
			(FragmentSettingsUnallowed)object;

		return Objects.equals(toString(), fragmentSettingsUnallowed.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentSettingsUnallowedSerDes.toJSON(this);
	}

}