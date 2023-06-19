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
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentFieldActionSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentFieldAction implements Cloneable, Serializable {

	public static FragmentFieldAction toDTO(String json) {
		return FragmentFieldActionSerDes.toDTO(json);
	}

	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	public void setAction(
		UnsafeSupplier<Object, Exception> actionUnsafeSupplier) {

		try {
			action = actionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object action;

	public ActionExecutionResult getOnError() {
		return onError;
	}

	public void setOnError(ActionExecutionResult onError) {
		this.onError = onError;
	}

	public void setOnError(
		UnsafeSupplier<ActionExecutionResult, Exception>
			onErrorUnsafeSupplier) {

		try {
			onError = onErrorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ActionExecutionResult onError;

	public ActionExecutionResult getOnSuccess() {
		return onSuccess;
	}

	public void setOnSuccess(ActionExecutionResult onSuccess) {
		this.onSuccess = onSuccess;
	}

	public void setOnSuccess(
		UnsafeSupplier<ActionExecutionResult, Exception>
			onSuccessUnsafeSupplier) {

		try {
			onSuccess = onSuccessUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ActionExecutionResult onSuccess;

	public Object getText() {
		return text;
	}

	public void setText(Object text) {
		this.text = text;
	}

	public void setText(UnsafeSupplier<Object, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object text;

	@Override
	public FragmentFieldAction clone() throws CloneNotSupportedException {
		return (FragmentFieldAction)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentFieldAction)) {
			return false;
		}

		FragmentFieldAction fragmentFieldAction = (FragmentFieldAction)object;

		return Objects.equals(toString(), fragmentFieldAction.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentFieldActionSerDes.toJSON(this);
	}

}