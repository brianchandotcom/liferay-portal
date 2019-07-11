/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotState;

import java.util.List;

import org.elasticsearch.snapshots.SnapshotId;
import org.elasticsearch.snapshots.SnapshotInfo;

/**
 * @author Michael C. Han
 */
public class SnapshotInfoConverter {

	public static SnapshotState convert(
		org.elasticsearch.snapshots.SnapshotState snapshotState) {

		if (snapshotState.value() == 0) {
			return SnapshotState.IN_PROGRESS;
		}
		else if (snapshotState.value() == 1) {
			return SnapshotState.SUCCESS;
		}
		else if (snapshotState.value() == 2) {
			return SnapshotState.FAILED;
		}
		else if (snapshotState.value() == 3) {
			return SnapshotState.PARTIAL;
		}
		else if (snapshotState.value() == 4) {
			return SnapshotState.INCOMPATIBLE;
		}

		throw new IllegalArgumentException(
			"Invalid value for snapshot state: " + snapshotState);
	}

	public static SnapshotDetails convert(SnapshotInfo snapshotInfo) {
		SnapshotId snapshotId = snapshotInfo.snapshotId();

		SnapshotDetails snapshotDetails = new SnapshotDetails(
			snapshotId.getName(), snapshotId.getUUID());

		List<String> indices = snapshotInfo.indices();

		if (ListUtil.isNotEmpty(indices)) {
			snapshotDetails.setIndexNames(indices.toArray(new String[0]));
		}

		SnapshotState snapshotState = convert(snapshotInfo.state());

		snapshotDetails.setSnapshotState(snapshotState);

		snapshotDetails.setSuccessfulShards(snapshotInfo.successfulShards());
		snapshotDetails.setTotalShards(snapshotInfo.totalShards());

		return snapshotDetails;
	}

}