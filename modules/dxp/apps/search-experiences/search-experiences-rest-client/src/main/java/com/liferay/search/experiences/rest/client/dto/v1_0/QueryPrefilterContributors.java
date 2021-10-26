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
import com.liferay.search.experiences.rest.client.serdes.v1_0.QueryPrefilterContributorsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class QueryPrefilterContributors implements Cloneable, Serializable {

	public static QueryPrefilterContributors toDTO(String json) {
		return QueryPrefilterContributorsSerDes.toDTO(json);
	}

	public String[] getClassNames() {
		return classNames;
	}

	public void setClassNames(String[] classNames) {
		this.classNames = classNames;
	}

	public void setClassNames(
		UnsafeSupplier<String[], Exception> classNamesUnsafeSupplier) {

		try {
			classNames = classNamesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] classNames;

	@Override
	public QueryPrefilterContributors clone()
		throws CloneNotSupportedException {

		return (QueryPrefilterContributors)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof QueryPrefilterContributors)) {
			return false;
		}

		QueryPrefilterContributors queryPrefilterContributors =
			(QueryPrefilterContributors)object;

		return Objects.equals(
			toString(), queryPrefilterContributors.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return QueryPrefilterContributorsSerDes.toJSON(this);
	}

}