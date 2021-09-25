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

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.HighlightFieldSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class HighlightField implements Cloneable, Serializable {

	public static HighlightField toDTO(String json) {
		return HighlightFieldSerDes.toDTO(json);
	}

	public Integer getFragment_offset() {
		return fragment_offset;
	}

	public void setFragment_offset(Integer fragment_offset) {
		this.fragment_offset = fragment_offset;
	}

	public void setFragment_offset(
		UnsafeSupplier<Integer, Exception> fragment_offsetUnsafeSupplier) {

		try {
			fragment_offset = fragment_offsetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer fragment_offset;

	public Integer getFragment_size() {
		return fragment_size;
	}

	public void setFragment_size(Integer fragment_size) {
		this.fragment_size = fragment_size;
	}

	public void setFragment_size(
		UnsafeSupplier<Integer, Exception> fragment_sizeUnsafeSupplier) {

		try {
			fragment_size = fragment_sizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer fragment_size;

	public Integer getNumber_of_fragments() {
		return number_of_fragments;
	}

	public void setNumber_of_fragments(Integer number_of_fragments) {
		this.number_of_fragments = number_of_fragments;
	}

	public void setNumber_of_fragments(
		UnsafeSupplier<Integer, Exception> number_of_fragmentsUnsafeSupplier) {

		try {
			number_of_fragments = number_of_fragmentsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer number_of_fragments;

	@Override
	public HighlightField clone() throws CloneNotSupportedException {
		return (HighlightField)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HighlightField)) {
			return false;
		}

		HighlightField highlightField = (HighlightField)object;

		return Objects.equals(toString(), highlightField.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return HighlightFieldSerDes.toJSON(this);
	}

}