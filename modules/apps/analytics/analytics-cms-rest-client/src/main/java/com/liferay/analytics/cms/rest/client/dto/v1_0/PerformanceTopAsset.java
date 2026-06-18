/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.PerformanceTopAssetSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class PerformanceTopAsset implements Cloneable, Serializable {

	public static PerformanceTopAsset toDTO(String json) {
		return PerformanceTopAssetSerDes.toDTO(json);
	}

	public Long getLastPage() {
		return lastPage;
	}

	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;
	}

	public void setLastPage(
		UnsafeSupplier<Long, Exception> lastPageUnsafeSupplier) {

		try {
			lastPage = lastPageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long lastPage;

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public void setPage(UnsafeSupplier<Long, Exception> pageUnsafeSupplier) {
		try {
			page = pageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long page;

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageSize(
		UnsafeSupplier<Long, Exception> pageSizeUnsafeSupplier) {

		try {
			pageSize = pageSizeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long pageSize;

	public PerformanceTopAssetItem[] getPerformanceTopAssetItems() {
		return performanceTopAssetItems;
	}

	public void setPerformanceTopAssetItems(
		PerformanceTopAssetItem[] performanceTopAssetItems) {

		this.performanceTopAssetItems = performanceTopAssetItems;
	}

	public void setPerformanceTopAssetItems(
		UnsafeSupplier<PerformanceTopAssetItem[], Exception>
			performanceTopAssetItemsUnsafeSupplier) {

		try {
			performanceTopAssetItems =
				performanceTopAssetItemsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PerformanceTopAssetItem[] performanceTopAssetItems;

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
	public PerformanceTopAsset clone() throws CloneNotSupportedException {
		return (PerformanceTopAsset)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceTopAsset)) {
			return false;
		}

		PerformanceTopAsset performanceTopAsset = (PerformanceTopAsset)object;

		return Objects.equals(toString(), performanceTopAsset.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PerformanceTopAssetSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:2054094997