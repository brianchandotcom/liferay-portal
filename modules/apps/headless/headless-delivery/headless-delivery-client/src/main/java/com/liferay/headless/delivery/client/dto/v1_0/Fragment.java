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
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Fragment {

	public String getFragmentCollectionName() {
		return fragmentCollectionName;
	}

	public void setFragmentCollectionName(String fragmentCollectionName) {
		this.fragmentCollectionName = fragmentCollectionName;
	}

	public void setFragmentCollectionName(
		UnsafeSupplier<String, Exception>
			fragmentCollectionNameUnsafeSupplier) {

		try {
			fragmentCollectionName = fragmentCollectionNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fragmentCollectionName;

	public String getFragmentName() {
		return fragmentName;
	}

	public void setFragmentName(String fragmentName) {
		this.fragmentName = fragmentName;
	}

	public void setFragmentName(
		UnsafeSupplier<String, Exception> fragmentNameUnsafeSupplier) {

		try {
			fragmentName = fragmentNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String fragmentName;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Fragment)) {
			return false;
		}

		Fragment fragment = (Fragment)object;

		return Objects.equals(toString(), fragment.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentSerDes.toJSON(this);
	}

}