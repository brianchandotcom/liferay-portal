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
import com.liferay.search.experiences.rest.client.serdes.v1_0.DocumentSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Document implements Cloneable, Serializable {

	public static Document toDTO(String json) {
		return DocumentSerDes.toDTO(json);
	}

	public Map<String, DocumentField> getDocumentFields() {
		return documentFields;
	}

	public void setDocumentFields(Map<String, DocumentField> documentFields) {
		this.documentFields = documentFields;
	}

	public void setDocumentFields(
		UnsafeSupplier<Map<String, DocumentField>, Exception>
			documentFieldsUnsafeSupplier) {

		try {
			documentFields = documentFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, DocumentField> documentFields;

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public void setExplanation(
		UnsafeSupplier<String, Exception> explanationUnsafeSupplier) {

		try {
			explanation = explanationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String explanation;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setScore(
		UnsafeSupplier<Double, Exception> scoreUnsafeSupplier) {

		try {
			score = scoreUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double score;

	@Override
	public Document clone() throws CloneNotSupportedException {
		return (Document)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Document)) {
			return false;
		}

		Document document = (Document)object;

		return Objects.equals(toString(), document.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DocumentSerDes.toJSON(this);
	}

}