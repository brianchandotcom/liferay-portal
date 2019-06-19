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

package com.liferay.change.tracking.rest.client.dto.v1_0;

import com.liferay.change.tracking.rest.client.function.UnsafeSupplier;
import com.liferay.change.tracking.rest.client.serdes.v1_0.ProcessUserSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class ProcessUser {

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		try {
			userId = userIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long userId;

	public String getUserInitials() {
		return userInitials;
	}

	public void setUserInitials(String userInitials) {
		this.userInitials = userInitials;
	}

	public void setUserInitials(
		UnsafeSupplier<String, Exception> userInitialsUnsafeSupplier) {

		try {
			userInitials = userInitialsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String userInitials;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserName(
		UnsafeSupplier<String, Exception> userNameUnsafeSupplier) {

		try {
			userName = userNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String userName;

	public String getUserPortraitURL() {
		return userPortraitURL;
	}

	public void setUserPortraitURL(String userPortraitURL) {
		this.userPortraitURL = userPortraitURL;
	}

	public void setUserPortraitURL(
		UnsafeSupplier<String, Exception> userPortraitURLUnsafeSupplier) {

		try {
			userPortraitURL = userPortraitURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String userPortraitURL;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProcessUser)) {
			return false;
		}

		ProcessUser processUser = (ProcessUser)object;

		return Objects.equals(toString(), processUser.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ProcessUserSerDes.toJSON(this);
	}

}