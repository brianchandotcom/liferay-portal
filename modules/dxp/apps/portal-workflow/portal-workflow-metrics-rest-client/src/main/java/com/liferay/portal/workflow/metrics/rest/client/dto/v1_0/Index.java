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

package com.liferay.portal.workflow.metrics.rest.client.dto.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.function.UnsafeSupplier;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.IndexSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class Index implements Cloneable, Serializable {

	public static Index toDTO(String json) {
		return IndexSerDes.toDTO(json);
	}

	public Group getGroup() {
		return group;
	}

	public String getGroupAsString() {
		if (group == null) {
			return null;
		}

		return group.toString();
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setGroup(UnsafeSupplier<Group, Exception> groupUnsafeSupplier) {
		try {
			group = groupUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Group group;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String key;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLabel(
		UnsafeSupplier<String, Exception> labelUnsafeSupplier) {

		try {
			label = labelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String label;

	@Override
	public Index clone() throws CloneNotSupportedException {
		return (Index)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Index)) {
			return false;
		}

		Index index = (Index)object;

		return Objects.equals(toString(), index.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return IndexSerDes.toJSON(this);
	}

	public static enum Group {

		ALL("All"), METRIC("Metric"), SLA("SLA");

		public static Group create(String value) {
			for (Group group : values()) {
				if (Objects.equals(group.getValue(), value) ||
					Objects.equals(group.name(), value)) {

					return group;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Group(String value) {
			_value = value;
		}

		private final String _value;

	}

}