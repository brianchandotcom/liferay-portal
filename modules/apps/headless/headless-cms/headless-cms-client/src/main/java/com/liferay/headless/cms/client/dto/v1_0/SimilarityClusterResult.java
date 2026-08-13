/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.SimilarityClusterResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class SimilarityClusterResult implements Cloneable, Serializable {

	public static SimilarityClusterResult toDTO(String json) {
		return SimilarityClusterResultSerDes.toDTO(json);
	}

	public SimilarityCluster[] getSimilarityClusters() {
		return similarityClusters;
	}

	public void setSimilarityClusters(SimilarityCluster[] similarityClusters) {
		this.similarityClusters = similarityClusters;
	}

	public void setSimilarityClusters(
		UnsafeSupplier<SimilarityCluster[], Exception>
			similarityClustersUnsafeSupplier) {

		try {
			similarityClusters = similarityClustersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SimilarityCluster[] similarityClusters;

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

	@Override
	public SimilarityClusterResult clone() throws CloneNotSupportedException {
		return (SimilarityClusterResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SimilarityClusterResult)) {
			return false;
		}

		SimilarityClusterResult similarityClusterResult =
			(SimilarityClusterResult)object;

		return Objects.equals(toString(), similarityClusterResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SimilarityClusterResultSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1869469088