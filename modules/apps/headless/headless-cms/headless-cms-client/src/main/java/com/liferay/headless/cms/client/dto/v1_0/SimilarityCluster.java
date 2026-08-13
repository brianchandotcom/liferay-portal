/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.SimilarityClusterSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class SimilarityCluster implements Cloneable, Serializable {

	public static SimilarityCluster toDTO(String json) {
		return SimilarityClusterSerDes.toDTO(json);
	}

	public SimilarityClusterAsset[] getSimilarityClusterAssets() {
		return similarityClusterAssets;
	}

	public void setSimilarityClusterAssets(
		SimilarityClusterAsset[] similarityClusterAssets) {

		this.similarityClusterAssets = similarityClusterAssets;
	}

	public void setSimilarityClusterAssets(
		UnsafeSupplier<SimilarityClusterAsset[], Exception>
			similarityClusterAssetsUnsafeSupplier) {

		try {
			similarityClusterAssets =
				similarityClusterAssetsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SimilarityClusterAsset[] similarityClusterAssets;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setSize(UnsafeSupplier<Integer, Exception> sizeUnsafeSupplier) {
		try {
			size = sizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer size;

	@Override
	public SimilarityCluster clone() throws CloneNotSupportedException {
		return (SimilarityCluster)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SimilarityCluster)) {
			return false;
		}

		SimilarityCluster similarityCluster = (SimilarityCluster)object;

		return Objects.equals(toString(), similarityCluster.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SimilarityClusterSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1638321847