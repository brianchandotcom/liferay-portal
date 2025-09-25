/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static SnapshotDetails convert(SnapshotInfo snapshotInfo) {
		SnapshotId snapshotId = snapshotInfo.snapshotId();

		SnapshotDetails snapshotDetails = new SnapshotDetails(
			snapshotId.getName(), snapshotId.getUUID());

		List<String> indices = snapshotInfo.indices();

		if (ListUtil.isNotEmpty(indices)) {
			snapshotDetails.setIndexNames(indices.toArray(new String[0]));
		}

		snapshotDetails.setSnapshotState(convert(snapshotInfo.state()));
		snapshotDetails.setSuccessfulShards(snapshotInfo.successfulShards());
		snapshotDetails.setTotalShards(snapshotInfo.totalShards());

		return snapshotDetails;
	}

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

}