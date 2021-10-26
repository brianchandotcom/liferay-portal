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
import com.liferay.search.experiences.rest.client.serdes.v1_0.ModelPrefilterContributorsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class ModelPrefilterContributors implements Cloneable, Serializable {

	public static ModelPrefilterContributors toDTO(String json) {
		return ModelPrefilterContributorsSerDes.toDTO(json);
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
	public ModelPrefilterContributors clone()
		throws CloneNotSupportedException {

		return (ModelPrefilterContributors)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ModelPrefilterContributors)) {
			return false;
		}

		ModelPrefilterContributors modelPrefilterContributors =
			(ModelPrefilterContributors)object;

		return Objects.equals(
			toString(), modelPrefilterContributors.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ModelPrefilterContributorsSerDes.toJSON(this);
	}

}