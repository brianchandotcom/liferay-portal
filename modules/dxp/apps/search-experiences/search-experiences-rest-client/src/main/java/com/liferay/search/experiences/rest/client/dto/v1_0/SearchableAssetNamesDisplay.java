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
import com.liferay.search.experiences.rest.client.serdes.v1_0.SearchableAssetNamesDisplaySerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class SearchableAssetNamesDisplay implements Cloneable, Serializable {

	public static SearchableAssetNamesDisplay toDTO(String json) {
		return SearchableAssetNamesDisplaySerDes.toDTO(json);
	}

	public SearchableAssetDisplay[] getSearchableAssetDisplays() {
		return searchableAssetDisplays;
	}

	public void setSearchableAssetDisplays(
		SearchableAssetDisplay[] searchableAssetDisplays) {

		this.searchableAssetDisplays = searchableAssetDisplays;
	}

	public void setSearchableAssetDisplays(
		UnsafeSupplier<SearchableAssetDisplay[], Exception>
			searchableAssetDisplaysUnsafeSupplier) {

		try {
			searchableAssetDisplays =
				searchableAssetDisplaysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SearchableAssetDisplay[] searchableAssetDisplays;

	@Override
	public SearchableAssetNamesDisplay clone()
		throws CloneNotSupportedException {

		return (SearchableAssetNamesDisplay)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchableAssetNamesDisplay)) {
			return false;
		}

		SearchableAssetNamesDisplay searchableAssetNamesDisplay =
			(SearchableAssetNamesDisplay)object;

		return Objects.equals(
			toString(), searchableAssetNamesDisplay.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchableAssetNamesDisplaySerDes.toJSON(this);
	}

}