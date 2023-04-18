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
import com.liferay.headless.delivery.client.serdes.v1_0.ParentSitePageSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentSitePage implements Cloneable, Serializable {

	public static ParentSitePage toDTO(String json) {
		return ParentSitePageSerDes.toDTO(json);
	}

	public String getFriendlyUrlPath() {
		return friendlyUrlPath;
	}

	public void setFriendlyUrlPath(String friendlyUrlPath) {
		this.friendlyUrlPath = friendlyUrlPath;
	}

	public void setFriendlyUrlPath(
		UnsafeSupplier<String, Exception> friendlyUrlPathUnsafeSupplier) {

		try {
			friendlyUrlPath = friendlyUrlPathUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String friendlyUrlPath;

	@Override
	public ParentSitePage clone() throws CloneNotSupportedException {
		return (ParentSitePage)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ParentSitePage)) {
			return false;
		}

		ParentSitePage parentSitePage = (ParentSitePage)object;

		return Objects.equals(toString(), parentSitePage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ParentSitePageSerDes.toJSON(this);
	}

}