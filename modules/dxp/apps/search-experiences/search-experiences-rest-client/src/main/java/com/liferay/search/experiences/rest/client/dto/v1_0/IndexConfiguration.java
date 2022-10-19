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
import com.liferay.search.experiences.rest.client.serdes.v1_0.IndexConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class IndexConfiguration implements Cloneable, Serializable {

	public static IndexConfiguration toDTO(String json) {
		return IndexConfigurationSerDes.toDTO(json);
	}

	public Boolean getExternal() {
		return external;
	}

	public void setExternal(Boolean external) {
		this.external = external;
	}

	public void setExternal(
		UnsafeSupplier<Boolean, Exception> externalUnsafeSupplier) {

		try {
			external = externalUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean external;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setIndexName(
		UnsafeSupplier<String, Exception> indexNameUnsafeSupplier) {

		try {
			indexName = indexNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String indexName;

	@Override
	public IndexConfiguration clone() throws CloneNotSupportedException {
		return (IndexConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IndexConfiguration)) {
			return false;
		}

		IndexConfiguration indexConfiguration = (IndexConfiguration)object;

		return Objects.equals(toString(), indexConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return IndexConfigurationSerDes.toJSON(this);
	}

}