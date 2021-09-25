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
import com.liferay.search.experiences.rest.client.serdes.v1_0.HighlightSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Highlight implements Cloneable, Serializable {

	public static Highlight toDTO(String json) {
		return HighlightSerDes.toDTO(json);
	}

	public Map<String, HighlightField> getFields() {
		return fields;
	}

	public void setFields(Map<String, HighlightField> fields) {
		this.fields = fields;
	}

	public void setFields(
		UnsafeSupplier<Map<String, HighlightField>, Exception>
			fieldsUnsafeSupplier) {

		try {
			fields = fieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, HighlightField> fields;

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

	public String[] getPost_tags() {
		return post_tags;
	}

	public void setPost_tags(String[] post_tags) {
		this.post_tags = post_tags;
	}

	public void setPost_tags(
		UnsafeSupplier<String[], Exception> post_tagsUnsafeSupplier) {

		try {
			post_tags = post_tagsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] post_tags;

	public String[] getPre_tags() {
		return pre_tags;
	}

	public void setPre_tags(String[] pre_tags) {
		this.pre_tags = pre_tags;
	}

	public void setPre_tags(
		UnsafeSupplier<String[], Exception> pre_tagsUnsafeSupplier) {

		try {
			pre_tags = pre_tagsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] pre_tags;

	public Boolean getRequire_field_match() {
		return require_field_match;
	}

	public void setRequire_field_match(Boolean require_field_match) {
		this.require_field_match = require_field_match;
	}

	public void setRequire_field_match(
		UnsafeSupplier<Boolean, Exception> require_field_matchUnsafeSupplier) {

		try {
			require_field_match = require_field_matchUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean require_field_match;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setType(UnsafeSupplier<String, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String type;

	@Override
	public Highlight clone() throws CloneNotSupportedException {
		return (Highlight)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Highlight)) {
			return false;
		}

		Highlight highlight = (Highlight)object;

		return Objects.equals(toString(), highlight.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return HighlightSerDes.toJSON(this);
	}

}