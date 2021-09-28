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
import com.liferay.search.experiences.rest.client.serdes.v1_0.QueryConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class QueryConfiguration implements Cloneable, Serializable {

	public static QueryConfiguration toDTO(String json) {
		return QueryConfigurationSerDes.toDTO(json);
	}

	public Boolean getApplyIndexerClauses() {
		return applyIndexerClauses;
	}

	public void setApplyIndexerClauses(Boolean applyIndexerClauses) {
		this.applyIndexerClauses = applyIndexerClauses;
	}

	public void setApplyIndexerClauses(
		UnsafeSupplier<Boolean, Exception> applyIndexerClausesUnsafeSupplier) {

		try {
			applyIndexerClauses = applyIndexerClausesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean applyIndexerClauses;

	public QueryEntry[] getQueryEntries() {
		return queryEntries;
	}

	public void setQueryEntries(QueryEntry[] queryEntries) {
		this.queryEntries = queryEntries;
	}

	public void setQueryEntries(
		UnsafeSupplier<QueryEntry[], Exception> queryEntriesUnsafeSupplier) {

		try {
			queryEntries = queryEntriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected QueryEntry[] queryEntries;

	@Override
	public QueryConfiguration clone() throws CloneNotSupportedException {
		return (QueryConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof QueryConfiguration)) {
			return false;
		}

		QueryConfiguration queryConfiguration = (QueryConfiguration)object;

		return Objects.equals(toString(), queryConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return QueryConfigurationSerDes.toJSON(this);
	}

}