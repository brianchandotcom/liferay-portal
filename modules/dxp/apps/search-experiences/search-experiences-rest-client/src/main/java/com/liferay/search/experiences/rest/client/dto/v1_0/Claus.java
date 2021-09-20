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
import com.liferay.search.experiences.rest.client.serdes.v1_0.ClausSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Claus implements Cloneable, Serializable {

	public static Claus toDTO(String json) {
		return ClausSerDes.toDTO(json);
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setContext(
		UnsafeSupplier<String, Exception> contextUnsafeSupplier) {

		try {
			context = contextUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String context;

	public String getOccur() {
		return occur;
	}

	public void setOccur(String occur) {
		this.occur = occur;
	}

	public void setOccur(
		UnsafeSupplier<String, Exception> occurUnsafeSupplier) {

		try {
			occur = occurUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String occur;

	public String getQueryJSON() {
		return queryJSON;
	}

	public void setQueryJSON(String queryJSON) {
		this.queryJSON = queryJSON;
	}

	public void setQueryJSON(
		UnsafeSupplier<String, Exception> queryJSONUnsafeSupplier) {

		try {
			queryJSON = queryJSONUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String queryJSON;

	@Override
	public Claus clone() throws CloneNotSupportedException {
		return (Claus)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Claus)) {
			return false;
		}

		Claus claus = (Claus)object;

		return Objects.equals(toString(), claus.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ClausSerDes.toJSON(this);
	}

}