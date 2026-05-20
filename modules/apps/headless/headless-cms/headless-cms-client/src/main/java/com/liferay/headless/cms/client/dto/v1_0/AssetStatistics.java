/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.AssetStatisticsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class AssetStatistics implements Cloneable, Serializable {

	public static AssetStatistics toDTO(String json) {
		return AssetStatisticsSerDes.toDTO(json);
	}

	public Long getExpiredCount() {
		return expiredCount;
	}

	public void setExpiredCount(Long expiredCount) {
		this.expiredCount = expiredCount;
	}

	public void setExpiredCount(
		UnsafeSupplier<Long, Exception> expiredCountUnsafeSupplier) {

		try {
			expiredCount = expiredCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expiredCount;

	public Long getExpiringSoonCount() {
		return expiringSoonCount;
	}

	public void setExpiringSoonCount(Long expiringSoonCount) {
		this.expiringSoonCount = expiringSoonCount;
	}

	public void setExpiringSoonCount(
		UnsafeSupplier<Long, Exception> expiringSoonCountUnsafeSupplier) {

		try {
			expiringSoonCount = expiringSoonCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expiringSoonCount;

	public Long getInDraftCount() {
		return inDraftCount;
	}

	public void setInDraftCount(Long inDraftCount) {
		this.inDraftCount = inDraftCount;
	}

	public void setInDraftCount(
		UnsafeSupplier<Long, Exception> inDraftCountUnsafeSupplier) {

		try {
			inDraftCount = inDraftCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long inDraftCount;

	public Long getReviewDateOverdueCount() {
		return reviewDateOverdueCount;
	}

	public void setReviewDateOverdueCount(Long reviewDateOverdueCount) {
		this.reviewDateOverdueCount = reviewDateOverdueCount;
	}

	public void setReviewDateOverdueCount(
		UnsafeSupplier<Long, Exception> reviewDateOverdueCountUnsafeSupplier) {

		try {
			reviewDateOverdueCount = reviewDateOverdueCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long reviewDateOverdueCount;

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
	public AssetStatistics clone() throws CloneNotSupportedException {
		return (AssetStatistics)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetStatistics)) {
			return false;
		}

		AssetStatistics assetStatistics = (AssetStatistics)object;

		return Objects.equals(toString(), assetStatistics.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AssetStatisticsSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-941353405