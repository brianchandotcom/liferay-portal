/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class GetSnapshotsResponse implements SnapshotResponse {

	public void addSnapshotInfo(SnapshotDetails snapshotDetails) {
		_snapshotDetails.add(snapshotDetails);
	}

	public List<SnapshotDetails> getSnapshotDetails() {
		return _snapshotDetails;
	}

	private final List<SnapshotDetails> _snapshotDetails = new ArrayList<>();

}