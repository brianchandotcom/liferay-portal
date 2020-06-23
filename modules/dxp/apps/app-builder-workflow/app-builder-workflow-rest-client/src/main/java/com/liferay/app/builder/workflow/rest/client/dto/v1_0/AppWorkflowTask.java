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
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTaskSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowTask implements Cloneable {

	public static AppWorkflowTask toDTO(String json) {
		return AppWorkflowTaskSerDes.toDTO(json);
	}

	public AppWorkflowDataLayoutLink[] getAppWorkflowDataLayoutLinks() {
		return appWorkflowDataLayoutLinks;
	}

	public void setAppWorkflowDataLayoutLinks(
		AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks) {

		this.appWorkflowDataLayoutLinks = appWorkflowDataLayoutLinks;
	}

	public void setAppWorkflowDataLayoutLinks(
		UnsafeSupplier<AppWorkflowDataLayoutLink[], Exception>
			appWorkflowDataLayoutLinksUnsafeSupplier) {

		try {
			appWorkflowDataLayoutLinks =
				appWorkflowDataLayoutLinksUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks;

	public AppWorkflowTransition[] getAppWorkflowTransitions() {
		return appWorkflowTransitions;
	}

	public void setAppWorkflowTransitions(
		AppWorkflowTransition[] appWorkflowTransitions) {

		this.appWorkflowTransitions = appWorkflowTransitions;
	}

	public void setAppWorkflowTransitions(
		UnsafeSupplier<AppWorkflowTransition[], Exception>
			appWorkflowTransitionsUnsafeSupplier) {

		try {
			appWorkflowTransitions = appWorkflowTransitionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowTransition[] appWorkflowTransitions;

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

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public void setRoleIds(
		UnsafeSupplier<Long[], Exception> roleIdsUnsafeSupplier) {

		try {
			roleIds = roleIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] roleIds;

	@Override
	public AppWorkflowTask clone() throws CloneNotSupportedException {
		return (AppWorkflowTask)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowTask)) {
			return false;
		}

		AppWorkflowTask appWorkflowTask = (AppWorkflowTask)object;

		return Objects.equals(toString(), appWorkflowTask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowTaskSerDes.toJSON(this);
	}

}