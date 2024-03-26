/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotState;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;

import java.util.List;

import org.opensearch.client.opensearch._types.ShardStatistics;
import org.opensearch.client.opensearch.snapshot.SnapshotInfo;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class SnapshotInfoConverter {

	public static SnapshotDetails convert(SnapshotInfo snapshotInfo) {
		SnapshotDetails snapshotDetails = new SnapshotDetails(
			snapshotInfo.snapshot(), snapshotInfo.uuid());

		List<String> indices = snapshotInfo.indices();

		if (ListUtil.isNotEmpty(indices)) {
			snapshotDetails.setIndexNames(indices.toArray(new String[0]));
		}

		snapshotDetails.setSnapshotState(
			_convertSnapshotState(snapshotInfo.state()));

		ShardStatistics shardStatistics = snapshotInfo.shards();

		if (shardStatistics != null) {
			snapshotDetails.setSuccessfulShards(
				ConversionUtil.toInt(shardStatistics.successful()));

			snapshotDetails.setTotalShards(
				ConversionUtil.toInt(shardStatistics.total()));
		}

		return snapshotDetails;
	}

	private static SnapshotState _convertSnapshotState(String snapshotState) {
		if (snapshotState.equals("FAILED")) {
			return SnapshotState.FAILED;
		}
		else if (snapshotState.equals("INCOMPATIBLE")) {
			return SnapshotState.INCOMPATIBLE;
		}
		else if (snapshotState.equals("IN_PROGRESS")) {
			return SnapshotState.IN_PROGRESS;
		}
		else if (snapshotState.equals("PARTIAL")) {
			return SnapshotState.PARTIAL;
		}
		else if (snapshotState.equals("SUCCESS")) {
			return SnapshotState.SUCCESS;
		}

		throw new IllegalArgumentException(
			"Invalid snapshot state " + snapshotState);
	}

}