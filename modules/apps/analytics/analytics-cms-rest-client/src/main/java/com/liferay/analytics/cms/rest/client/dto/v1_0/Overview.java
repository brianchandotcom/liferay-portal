/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.OverviewSerDes;

import java.io.Serializable;

import java.util.Objects;

import jakarta.annotation.Generated;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class Overview implements Cloneable, Serializable {

	public static Overview toDTO(String json) {
		return OverviewSerDes.toDTO(json);
	}

	public Long getCategoriesCount() {
		return categoriesCount;
	}

	public void setCategoriesCount(Long categoriesCount) {
		this.categoriesCount = categoriesCount;
	}

	public void setCategoriesCount(
		UnsafeSupplier<Long, Exception> categoriesCountUnsafeSupplier) {

		try {
			categoriesCount = categoriesCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long categoriesCount;

	public Long getTagsCount() {
		return tagsCount;
	}

	public void setTagsCount(Long tagsCount) {
		this.tagsCount = tagsCount;
	}

	public void setTagsCount(
		UnsafeSupplier<Long, Exception> tagsCountUnsafeSupplier) {

		try {
			tagsCount = tagsCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long tagsCount;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalCount(
		UnsafeSupplier<Long, Exception> totalCountUnsafeSupplier) {

		try {
			totalCount = totalCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long totalCount;

	public Trend getTrend() {
		return trend;
	}

	public void setTrend(Trend trend) {
		this.trend = trend;
	}

	public void setTrend(UnsafeSupplier<Trend, Exception> trendUnsafeSupplier) {
		try {
			trend = trendUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Trend trend;

	public Long getVocabulariesCount() {
		return vocabulariesCount;
	}

	public void setVocabulariesCount(Long vocabulariesCount) {
		this.vocabulariesCount = vocabulariesCount;
	}

	public void setVocabulariesCount(
		UnsafeSupplier<Long, Exception> vocabulariesCountUnsafeSupplier) {

		try {
			vocabulariesCount = vocabulariesCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long vocabulariesCount;

	@Override
	public Overview clone() throws CloneNotSupportedException {
		return (Overview)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Overview)) {
			return false;
		}

		Overview overview = (Overview)object;

		return Objects.equals(toString(), overview.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return OverviewSerDes.toJSON(this);
	}

}