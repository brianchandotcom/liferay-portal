/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.client.dto.v1_0;

import com.liferay.analytics.cms.rest.client.function.UnsafeSupplier;
import com.liferay.analytics.cms.rest.client.serdes.v1_0.PerformanceAssetConsumptionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 * @generated
 */
@Generated("")
public class PerformanceAssetConsumption implements Cloneable, Serializable {

	public static PerformanceAssetConsumption toDTO(String json) {
		return PerformanceAssetConsumptionSerDes.toDTO(json);
	}

	public PerformanceAssetConsumptionItem[]
		getPerformanceAssetConsumptionItems() {

		return performanceAssetConsumptionItems;
	}

	public void setPerformanceAssetConsumptionItems(
		PerformanceAssetConsumptionItem[] performanceAssetConsumptionItems) {

		this.performanceAssetConsumptionItems =
			performanceAssetConsumptionItems;
	}

	public void setPerformanceAssetConsumptionItems(
		UnsafeSupplier<PerformanceAssetConsumptionItem[], Exception>
			performanceAssetConsumptionItemsUnsafeSupplier) {

		try {
			performanceAssetConsumptionItems =
				performanceAssetConsumptionItemsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PerformanceAssetConsumptionItem[]
		performanceAssetConsumptionItems;

	public Long getPerformanceAssetConsumptionItemsCount() {
		return performanceAssetConsumptionItemsCount;
	}

	public void setPerformanceAssetConsumptionItemsCount(
		Long performanceAssetConsumptionItemsCount) {

		this.performanceAssetConsumptionItemsCount =
			performanceAssetConsumptionItemsCount;
	}

	public void setPerformanceAssetConsumptionItemsCount(
		UnsafeSupplier<Long, Exception>
			performanceAssetConsumptionItemsCountUnsafeSupplier) {

		try {
			performanceAssetConsumptionItemsCount =
				performanceAssetConsumptionItemsCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long performanceAssetConsumptionItemsCount;

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
	public PerformanceAssetConsumption clone()
		throws CloneNotSupportedException {

		return (PerformanceAssetConsumption)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PerformanceAssetConsumption)) {
			return false;
		}

		PerformanceAssetConsumption performanceAssetConsumption =
			(PerformanceAssetConsumption)object;

		return Objects.equals(
			toString(), performanceAssetConsumption.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PerformanceAssetConsumptionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:2091823173