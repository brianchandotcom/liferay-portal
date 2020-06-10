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

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTransitionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowTransition implements Cloneable {

	public static AppWorkflowTransition toDTO(String json) {
		return AppWorkflowTransitionSerDes.toDTO(json);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public void setPrimary(
		UnsafeSupplier<Boolean, Exception> primaryUnsafeSupplier) {

		try {
			primary = primaryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean primary;

	public String getTransitionTo() {
		return transitionTo;
	}

	public void setTransitionTo(String transitionTo) {
		this.transitionTo = transitionTo;
	}

	public void setTransitionTo(
		UnsafeSupplier<String, Exception> transitionToUnsafeSupplier) {

		try {
			transitionTo = transitionToUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String transitionTo;

	@Override
	public AppWorkflowTransition clone() throws CloneNotSupportedException {
		return (AppWorkflowTransition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowTransition)) {
			return false;
		}

		AppWorkflowTransition appWorkflowTransition =
			(AppWorkflowTransition)object;

		return Objects.equals(toString(), appWorkflowTransition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowTransitionSerDes.toJSON(this);
	}

}