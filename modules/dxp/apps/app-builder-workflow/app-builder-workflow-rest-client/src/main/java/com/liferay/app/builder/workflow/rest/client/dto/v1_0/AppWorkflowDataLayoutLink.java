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
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowDataLayoutLinkSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowDataLayoutLink implements Cloneable {

	public static AppWorkflowDataLayoutLink toDTO(String json) {
		return AppWorkflowDataLayoutLinkSerDes.toDTO(json);
	}

	public Long getDataLayoutId() {
		return dataLayoutId;
	}

	public void setDataLayoutId(Long dataLayoutId) {
		this.dataLayoutId = dataLayoutId;
	}

	public void setDataLayoutId(
		UnsafeSupplier<Long, Exception> dataLayoutIdUnsafeSupplier) {

		try {
			dataLayoutId = dataLayoutIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long dataLayoutId;

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setReadOnly(
		UnsafeSupplier<Boolean, Exception> readOnlyUnsafeSupplier) {

		try {
			readOnly = readOnlyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean readOnly;

	@Override
	public AppWorkflowDataLayoutLink clone() throws CloneNotSupportedException {
		return (AppWorkflowDataLayoutLink)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowDataLayoutLink)) {
			return false;
		}

		AppWorkflowDataLayoutLink appWorkflowDataLayoutLink =
			(AppWorkflowDataLayoutLink)object;

		return Objects.equals(toString(), appWorkflowDataLayoutLink.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowDataLayoutLinkSerDes.toJSON(this);
	}

}