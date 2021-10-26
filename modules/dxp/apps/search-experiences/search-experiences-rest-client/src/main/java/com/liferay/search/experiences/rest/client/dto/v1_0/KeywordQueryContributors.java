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
import com.liferay.search.experiences.rest.client.serdes.v1_0.KeywordQueryContributorsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class KeywordQueryContributors implements Cloneable, Serializable {

	public static KeywordQueryContributors toDTO(String json) {
		return KeywordQueryContributorsSerDes.toDTO(json);
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
	public KeywordQueryContributors clone() throws CloneNotSupportedException {
		return (KeywordQueryContributors)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KeywordQueryContributors)) {
			return false;
		}

		KeywordQueryContributors keywordQueryContributors =
			(KeywordQueryContributors)object;

		return Objects.equals(toString(), keywordQueryContributors.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return KeywordQueryContributorsSerDes.toJSON(this);
	}

}