/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.client.dto.v1_0;

import com.liferay.headless.cmp.client.function.UnsafeSupplier;
import com.liferay.headless.cmp.client.serdes.v1_0.TaskStatisticsSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Carolina Barbosa
 * @generated
 */
@Generated("")
public class TaskStatistics implements Cloneable, Serializable {

	public static TaskStatistics toDTO(String json) {
		return TaskStatisticsSerDes.toDTO(json);
	}

	public Long getBlockedCount() {
		return blockedCount;
	}

	public void setBlockedCount(Long blockedCount) {
		this.blockedCount = blockedCount;
	}

	public void setBlockedCount(
		UnsafeSupplier<Long, Exception> blockedCountUnsafeSupplier) {

		try {
			blockedCount = blockedCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long blockedCount;

	public Long getInProgressCount() {
		return inProgressCount;
	}

	public void setInProgressCount(Long inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public void setInProgressCount(
		UnsafeSupplier<Long, Exception> inProgressCountUnsafeSupplier) {

		try {
			inProgressCount = inProgressCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long inProgressCount;

	public Long getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(Long overdueCount) {
		this.overdueCount = overdueCount;
	}

	public void setOverdueCount(
		UnsafeSupplier<Long, Exception> overdueCountUnsafeSupplier) {

		try {
			overdueCount = overdueCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long overdueCount;

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
	public TaskStatistics clone() throws CloneNotSupportedException {
		return (TaskStatistics)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TaskStatistics)) {
			return false;
		}

		TaskStatistics taskStatistics = (TaskStatistics)object;

		return Objects.equals(toString(), taskStatistics.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TaskStatisticsSerDes.toJSON(this);
	}

}