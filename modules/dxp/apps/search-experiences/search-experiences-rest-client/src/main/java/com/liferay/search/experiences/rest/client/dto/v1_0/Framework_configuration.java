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
import com.liferay.search.experiences.rest.client.serdes.v1_0.Framework_configurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Framework_configuration implements Cloneable, Serializable {

	public static Framework_configuration toDTO(String json) {
		return Framework_configurationSerDes.toDTO(json);
	}

	public Boolean getApply_indexer_clauses() {
		return apply_indexer_clauses;
	}

	public void setApply_indexer_clauses(Boolean apply_indexer_clauses) {
		this.apply_indexer_clauses = apply_indexer_clauses;
	}

	public void setApply_indexer_clauses(
		UnsafeSupplier<Boolean, Exception>
			apply_indexer_clausesUnsafeSupplier) {

		try {
			apply_indexer_clauses = apply_indexer_clausesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean apply_indexer_clauses;

	@Override
	public Framework_configuration clone() throws CloneNotSupportedException {
		return (Framework_configuration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Framework_configuration)) {
			return false;
		}

		Framework_configuration framework_configuration =
			(Framework_configuration)object;

		return Objects.equals(toString(), framework_configuration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return Framework_configurationSerDes.toJSON(this);
	}

}