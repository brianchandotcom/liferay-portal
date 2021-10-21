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
import com.liferay.search.experiences.rest.client.serdes.v1_0.RescoreSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Rescore implements Cloneable, Serializable {

	public static Rescore toDTO(String json) {
		return RescoreSerDes.toDTO(json);
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public void setQuery(
		UnsafeSupplier<Object, Exception> queryUnsafeSupplier) {

		try {
			query = queryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object query;

	public Float getQueryWeight() {
		return queryWeight;
	}

	public void setQueryWeight(Float queryWeight) {
		this.queryWeight = queryWeight;
	}

	public void setQueryWeight(
		UnsafeSupplier<Float, Exception> queryWeightUnsafeSupplier) {

		try {
			queryWeight = queryWeightUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float queryWeight;

	public Float getRescoreQueryWeight() {
		return rescoreQueryWeight;
	}

	public void setRescoreQueryWeight(Float rescoreQueryWeight) {
		this.rescoreQueryWeight = rescoreQueryWeight;
	}

	public void setRescoreQueryWeight(
		UnsafeSupplier<Float, Exception> rescoreQueryWeightUnsafeSupplier) {

		try {
			rescoreQueryWeight = rescoreQueryWeightUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Float rescoreQueryWeight;

	public String getScoreMode() {
		return scoreMode;
	}

	public void setScoreMode(String scoreMode) {
		this.scoreMode = scoreMode;
	}

	public void setScoreMode(
		UnsafeSupplier<String, Exception> scoreModeUnsafeSupplier) {

		try {
			scoreMode = scoreModeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String scoreMode;

	public Integer getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(Integer windowSize) {
		this.windowSize = windowSize;
	}

	public void setWindowSize(
		UnsafeSupplier<Integer, Exception> windowSizeUnsafeSupplier) {

		try {
			windowSize = windowSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer windowSize;

	@Override
	public Rescore clone() throws CloneNotSupportedException {
		return (Rescore)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Rescore)) {
			return false;
		}

		Rescore rescore = (Rescore)object;

		return Objects.equals(toString(), rescore.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RescoreSerDes.toJSON(this);
	}

}