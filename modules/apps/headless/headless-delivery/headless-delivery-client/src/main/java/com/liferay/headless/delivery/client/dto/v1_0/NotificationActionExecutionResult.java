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
import com.liferay.headless.delivery.client.serdes.v1_0.NotificationActionExecutionResultSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class NotificationActionExecutionResult
	implements Cloneable, Serializable {

	public static NotificationActionExecutionResult toDTO(String json) {
		return NotificationActionExecutionResultSerDes.toDTO(json);
	}

	public Boolean getReload() {
		return reload;
	}

	public void setReload(Boolean reload) {
		this.reload = reload;
	}

	public void setReload(
		UnsafeSupplier<Boolean, Exception> reloadUnsafeSupplier) {

		try {
			reload = reloadUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean reload;

	public FragmentInlineValue getText() {
		return text;
	}

	public void setText(FragmentInlineValue text) {
		this.text = text;
	}

	public void setText(
		UnsafeSupplier<FragmentInlineValue, Exception> textUnsafeSupplier) {

		try {
			text = textUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue text;

	@Override
	public NotificationActionExecutionResult clone()
		throws CloneNotSupportedException {

		return (NotificationActionExecutionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationActionExecutionResult)) {
			return false;
		}

		NotificationActionExecutionResult notificationActionExecutionResult =
			(NotificationActionExecutionResult)object;

		return Objects.equals(
			toString(), notificationActionExecutionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return NotificationActionExecutionResultSerDes.toJSON(this);
	}

}