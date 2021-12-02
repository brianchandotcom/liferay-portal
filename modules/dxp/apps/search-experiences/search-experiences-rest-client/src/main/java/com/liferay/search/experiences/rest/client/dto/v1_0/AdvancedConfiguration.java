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
import com.liferay.search.experiences.rest.client.serdes.v1_0.AdvancedConfigurationSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class AdvancedConfiguration implements Cloneable, Serializable {

	public static AdvancedConfiguration toDTO(String json) {
		return AdvancedConfigurationSerDes.toDTO(json);
	}

	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public void setExcludes(
		UnsafeSupplier<String[], Exception> excludesUnsafeSupplier) {

		try {
			excludes = excludesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] excludes;

	public Boolean getFetchSource() {
		return fetchSource;
	}

	public void setFetchSource(Boolean fetchSource) {
		this.fetchSource = fetchSource;
	}

	public void setFetchSource(
		UnsafeSupplier<Boolean, Exception> fetchSourceUnsafeSupplier) {

		try {
			fetchSource = fetchSourceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean fetchSource;

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	public void setIncludes(
		UnsafeSupplier<String[], Exception> includesUnsafeSupplier) {

		try {
			includes = includesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] includes;

	@Override
	public AdvancedConfiguration clone() throws CloneNotSupportedException {
		return (AdvancedConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AdvancedConfiguration)) {
			return false;
		}

		AdvancedConfiguration advancedConfiguration =
			(AdvancedConfiguration)object;

		return Objects.equals(toString(), advancedConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AdvancedConfigurationSerDes.toJSON(this);
	}

}