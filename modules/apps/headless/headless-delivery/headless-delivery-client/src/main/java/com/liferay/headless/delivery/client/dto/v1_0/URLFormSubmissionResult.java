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
import com.liferay.headless.delivery.client.serdes.v1_0.URLFormSubmissionResultSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class URLFormSubmissionResult implements Cloneable, Serializable {

	public static URLFormSubmissionResult toDTO(String json) {
		return URLFormSubmissionResultSerDes.toDTO(json);
	}

	public FragmentInlineValue getUrl() {
		return url;
	}

	public void setUrl(FragmentInlineValue url) {
		this.url = url;
	}

	public void setUrl(
		UnsafeSupplier<FragmentInlineValue, Exception> urlUnsafeSupplier) {

		try {
			url = urlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue url;

	@Override
	public URLFormSubmissionResult clone() throws CloneNotSupportedException {
		return (URLFormSubmissionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof URLFormSubmissionResult)) {
			return false;
		}

		URLFormSubmissionResult urlFormSubmissionResult =
			(URLFormSubmissionResult)object;

		return Objects.equals(toString(), urlFormSubmissionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return URLFormSubmissionResultSerDes.toJSON(this);
	}

}