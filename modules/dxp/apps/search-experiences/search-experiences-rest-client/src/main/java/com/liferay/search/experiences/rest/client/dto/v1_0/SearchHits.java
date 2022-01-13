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
import com.liferay.search.experiences.rest.client.serdes.v1_0.SearchHitsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class SearchHits implements Cloneable, Serializable {

	public static SearchHits toDTO(String json) {
		return SearchHitsSerDes.toDTO(json);
	}

	public Hit[] getHits() {
		return hits;
	}

	public void setHits(Hit[] hits) {
		this.hits = hits;
	}

	public void setHits(UnsafeSupplier<Hit[], Exception> hitsUnsafeSupplier) {
		try {
			hits = hitsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Hit[] hits;

	public Float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}

	public void setMaxScore(
		UnsafeSupplier<Float, Exception> maxScoreUnsafeSupplier) {

		try {
			maxScore = maxScoreUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float maxScore;

	public Long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(Long totalHits) {
		this.totalHits = totalHits;
	}

	public void setTotalHits(
		UnsafeSupplier<Long, Exception> totalHitsUnsafeSupplier) {

		try {
			totalHits = totalHitsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long totalHits;

	@Override
	public SearchHits clone() throws CloneNotSupportedException {
		return (SearchHits)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchHits)) {
			return false;
		}

		SearchHits searchHits = (SearchHits)object;

		return Objects.equals(toString(), searchHits.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchHitsSerDes.toJSON(this);
	}

}